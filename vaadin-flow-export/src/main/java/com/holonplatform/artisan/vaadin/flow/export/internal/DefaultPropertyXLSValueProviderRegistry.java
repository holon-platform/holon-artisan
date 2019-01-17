/*
 * Copyright 2016-2019 Axioma srl.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.holonplatform.artisan.vaadin.flow.export.internal;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;

import javax.annotation.Priority;

import com.holonplatform.artisan.core.ArtisanLogger;
import com.holonplatform.artisan.vaadin.flow.export.PropertyXLSValueProvider;
import com.holonplatform.artisan.vaadin.flow.export.PropertyXLSValueProviderRegistry;
import com.holonplatform.core.internal.Logger;
import com.holonplatform.core.internal.utils.ClassUtils;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;

/**
 * Default {@link PropertyXLSValueProviderRegistry} implementation.
 *
 * @since 1.0.0
 */
public class DefaultPropertyXLSValueProviderRegistry implements PropertyXLSValueProviderRegistry {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = ArtisanLogger.create();

	/**
	 * Default registry map by ClassLoader
	 */
	private static final Map<ClassLoader, PropertyXLSValueProviderRegistry> DEFAULT_INSTANCES = new WeakHashMap<>();

	/**
	 * Return the default {@link PropertyXLSValueProviderRegistry} using given <code>classLoader</code>.
	 * <p>
	 * The default registry is inited loading {@link PropertyXLSValueProvider}s using fully qualified name of its
	 * implementation class name to a <code>com.holonplatform.artisan.vaadin.flow.export.PropertyXLSValueProvider</code>
	 * file in the <code>META-INF/services</code> directory.
	 * </p>
	 * @param classLoader ClassLoader to use, or <code>null</code> for the default ClassLoader
	 * @return Default {@link PropertyXLSValueProviderRegistry}
	 */
	public static PropertyXLSValueProviderRegistry getDefault(ClassLoader classLoader) {
		return ensureInited((classLoader != null) ? classLoader : ClassUtils.getDefaultClassLoader());
	}

	/**
	 * Ensure the default PropertyXLSValueProviderRegistry instance is inited.
	 * @param classLoader ClassLoader to use
	 * @return Default PropertyXLSValueProviderRegistry
	 */
	private static synchronized PropertyXLSValueProviderRegistry ensureInited(final ClassLoader classLoader) {
		if (!DEFAULT_INSTANCES.containsKey(classLoader)) {
			DEFAULT_INSTANCES.put(classLoader, new DefaultPropertyXLSValueProviderRegistry(true, classLoader));
		}
		return DEFAULT_INSTANCES.get(classLoader);
	}

	@SuppressWarnings("rawtypes")
	private static final Comparator<PropertyXLSValueProvider> PRIORITY_COMPARATOR = Comparator.comparingInt(
			p -> p.getClass().isAnnotationPresent(Priority.class) ? p.getClass().getAnnotation(Priority.class).value()
					: PropertyXLSValueProvider.DEFAULT_PRIORITY);

	/**
	 * Providers
	 */
	@SuppressWarnings("rawtypes")
	protected final ConcurrentMap<Predicate, PropertyXLSValueProvider> providers = new ConcurrentHashMap<>(8, 0.9f, 1);

	/**
	 * Construct a new {@link PropertyXLSValueProviderRegistry}.
	 * @param loadDefaults <code>true</code> to load default {@link PropertyXLSValueProvider}s from
	 *        <code>com.holonplatform.artisan.vaadin.flow.export.PropertyXLSValueProvider</code> files using default
	 *        ClassLoader. Every default provider will be registered using an always <code>true</code> condition.
	 */
	public DefaultPropertyXLSValueProviderRegistry(boolean loadDefaults) {
		this(loadDefaults, ClassUtils.getDefaultClassLoader());
	}

	/**
	 * Construct a new {@link PropertyXLSValueProviderRegistry}.
	 * @param loadDefaults <code>true</code> to load default {@link PropertyXLSValueProvider}s from
	 *        <code>com.holonplatform.artisan.vaadin.flow.export.PropertyXLSValueProvider</code> files. Every default
	 *        provider will be registered using an always <code>true</code> condition.
	 * @param classLoader ClassLoader to use to load default providers, if <code>loadDefaults</code> is true
	 */
	@SuppressWarnings("unchecked")
	public DefaultPropertyXLSValueProviderRegistry(boolean loadDefaults, ClassLoader classLoader) {
		super();
		if (loadDefaults) {
			DefaultPropertyXLSValueProviders.getDefaultProviders(classLoader).forEach(pr -> register(p -> true, pr));
		}
	}

	@Override
	public <T> void register(Predicate<Property<? extends T>> condition, PropertyXLSValueProvider<? super T> provider) {
		ObjectUtils.argumentNotNull(condition, "Condition Predicate must be not null");
		ObjectUtils.argumentNotNull(provider, "PropertyXLSValueProvider must be not null");

		PropertyXLSValueProvider<?> rp = providers.putIfAbsent(condition, provider);

		if (rp == null) {
			LOGGER.debug(() -> "Registered PropertyXLSValueProvider [" + provider + "] bound to condition [" + condition
					+ "]");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> Optional<PropertyXLSValueProvider<T>> getProvider(Property<T> property) {
		ObjectUtils.argumentNotNull(property, "Property must be not null");

		LOGGER.debug(() -> "Get PropertyXLSValueProvider for property [" + property + "]");

		final LinkedList<PropertyXLSValueProvider> candidates = new LinkedList<>();

		for (Entry<Predicate, PropertyXLSValueProvider> entry : providers.entrySet()) {
			if (entry.getKey().test(property)) {
				candidates.add(entry.getValue());
			}
		}

		if (!candidates.isEmpty()) {
			if (candidates.size() > 1) {
				// sort by priority
				candidates.sort(PRIORITY_COMPARATOR);

				LOGGER.debug(() -> "Get PropertyXLSValueProvider for property [" + property
						+ "] - return first of candidates: [" + candidates + "]");
			}
			return Optional.of(candidates.getFirst());
		}

		LOGGER.debug(() -> "No PropertyXLSValueProvider available for property [" + property + "]");

		return Optional.empty();
	}

	@Override
	public String toString() {
		return "DefaultPropertyXLSValueProviderRegistry [provider=" + providers + "]";
	}

}
