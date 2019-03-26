/*
 * Copyright 2016-2017 Axioma srl.
 * 
 * Licensed under the Commercial Holon Platform Module License Version 1 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * https://docs.holon-platform.com/license/chpml_v1.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.holonplatform.artisan.vaadin.flow.export.xls;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Priority;

import com.holonplatform.artisan.vaadin.flow.export.xls.internal.DefaultPropertyXLSValueProviderRegistry;
import com.holonplatform.core.Context;
import com.holonplatform.core.config.ConfigProperty;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;

/**
 * A registry to register {@link PropertyXLSValueProvider}s bound to a condition and provide a suitable
 * {@link PropertyXLSValueProvider}.
 * <p>
 * The registry supports {@link PropertyXLSValueProvider}s priority declaration using {@link Priority} annotation on
 * provider class (where less priority value means higher priority order), to select and return the most suitable
 * provider where more than one provider matches the condition to which is bound at registration time.
 * </p>
 * 
 * @since 5.0.0
 */
public interface PropertyXLSValueProviderRegistry {

	/**
	 * Default {@link Context} resource key
	 */
	static final String CONTEXT_KEY = PropertyXLSValueProviderRegistry.class.getName();

	/**
	 * Bind a {@link PropertyXLSValueProvider} to a property {@link Predicate} <code>condition</code>
	 * @param <T> Property base type
	 * @param condition The condition which has to be satisfied to provide the provider (not null)
	 * @param provider The {@link PropertyXLSValueProvider} to register (not null)
	 */
	<T> void register(Predicate<Property<? extends T>> condition, PropertyXLSValueProvider<? super T> provider);

	/**
	 * Bind a {@link PropertyXLSValueProvider} to the given property. The provider will be provided when the property to
	 * render is the same as the given property.
	 * @param <T> Property base type
	 * @param property The property (not null)
	 * @param provider The {@link PropertyXLSValueProvider} to register (not null)
	 */
	default <T> void forProperty(Property<? extends T> property, PropertyXLSValueProvider<? super T> provider) {
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		register(p -> property.equals(p), provider);
	}

	/**
	 * Bind a {@link PropertyXLSValueProvider} to the given property configuration value. The provider will be provided
	 * when the property has the given <code>configurationProperty</code> and its value equals to given
	 * <code>value</code>.
	 * @param <T> Property base type
	 * @param <C> Configuration property type
	 * @param configurationProperty The configuration property to check (not null)
	 * @param value The configuration property value to check (may be null)
	 * @param provider The {@link PropertyXLSValueProvider} to register (not null)
	 */
	default <T, C> void forPropertyConfiguration(ConfigProperty<C> configurationProperty, C value,
			PropertyXLSValueProvider<? super T> provider) {
		ObjectUtils.argumentNotNull(configurationProperty, "Configuration property must be not null");
		register(p -> p.getConfiguration().getParameter(configurationProperty).map(v -> Objects.equals(v, value))
				.orElse(Boolean.FALSE), provider);
	}

	/**
	 * Gets the {@link PropertyXLSValueProvider} to use with given <code>property</code> according to registered
	 * providers.
	 * @param <T> Property base type
	 * @param property The property
	 * @return The {@link PropertyXLSValueProvider}, or an empty Optional if no provider is available for given property
	 */
	<T> Optional<PropertyXLSValueProvider<T>> getProvider(Property<T> property);

	// Builder

	/**
	 * Create a default instance of {@link PropertyXLSValueProviderRegistry}.
	 * @param loadDefaults <code>true</code> to load default {@link PropertyXLSValueProvider}s from
	 *        <code>META-INF/services</code> using
	 *        <code>com.holonplatform.artisan.vaadin.flow.export.PropertyXLSValueProvider</code> files using default
	 *        ClassLoader. Every default provider will be registered using an always <code>true</code> condition.
	 * @return PropertyXLSValueProviderRegistry instance
	 */
	static PropertyXLSValueProviderRegistry create(boolean loadDefaults) {
		return new DefaultPropertyXLSValueProviderRegistry(loadDefaults);
	}

	// Accessor

	/**
	 * Gets the current {@link PropertyXLSValueProviderRegistry} instance.
	 * @return The {@link Context}-bound PropertyXLSValueProviderRegistry instance, if available using
	 *         {@link #CONTEXT_KEY} as context key, or the default instance for the default ClassLoader obtained through
	 *         {@link #getDefault()}.
	 */
	static PropertyXLSValueProviderRegistry get() {
		return Context.get().resource(CONTEXT_KEY, PropertyXLSValueProviderRegistry.class).orElse(getDefault());
	}

	// Defaults

	/**
	 * Return the default {@link PropertyXLSValueProviderRegistry} using given <code>classLoader</code>.
	 * <p>
	 * The default registry is inited loading {@link PropertyXLSValueProvider}s using fully qualified name of its
	 * implementation class name to a <code>com.holonplatform.artisan.vaadin.flow.export.PropertyXLSValueProvider</code>
	 * file in the <code>META-INF/services</code> directory.
	 * </p>
	 * @param classLoader ClassLoader to use
	 * @return Default PropertyXLSValueProviderRegistry
	 */
	static PropertyXLSValueProviderRegistry getDefault(ClassLoader classLoader) {
		return DefaultPropertyXLSValueProviderRegistry.getDefault(classLoader);
	}

	/**
	 * Return the default {@link PropertyXLSValueProviderRegistry} using default {@link ClassLoader}.
	 * <p>
	 * The default registry is inited loading {@link PropertyXLSValueProvider}s using fully qualified name of its
	 * implementation class name to a <code>com.holonplatform.artisan.vaadin.flow.export.PropertyXLSValueProvider</code>
	 * file in the <code>META-INF/services</code> directory.
	 * </p>
	 * @return Default PropertyXLSValueProviderRegistry
	 */
	static PropertyXLSValueProviderRegistry getDefault() {
		return DefaultPropertyXLSValueProviderRegistry.getDefault(null);
	}

}
