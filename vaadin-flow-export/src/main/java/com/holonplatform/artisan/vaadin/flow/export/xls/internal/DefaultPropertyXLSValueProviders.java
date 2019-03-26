/*
 * Copyright 2016-2019 Axioma srl.
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
package com.holonplatform.artisan.vaadin.flow.export.xls.internal;

import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.WeakHashMap;

import com.holonplatform.artisan.vaadin.flow.export.xls.PropertyXLSValueProvider;
import com.holonplatform.core.internal.Logger;
import com.holonplatform.core.internal.property.PropertyLogger;
import com.holonplatform.core.internal.utils.ClassUtils;

/**
 * Class to manage default {@link PropertyXLSValueProvider}s obtained using standard Java extensions loader from
 * <code>MET-INF/services</code>.
 * 
 * @since 5.0.0
 */
@SuppressWarnings("rawtypes")
public final class DefaultPropertyXLSValueProviders implements Serializable {

	private static final long serialVersionUID = 5665531723656457977L;

	/**
	 * Logger
	 */
	private static final Logger LOGGER = PropertyLogger.create();

	/**
	 * Default providers by ClassLoader
	 */
	private static final Map<ClassLoader, List<PropertyXLSValueProvider>> PROVIDERS = new WeakHashMap<>();

	/**
	 * Return the default {@link PropertyXLSValueProvider}s using given <code>classLoader</code>.
	 * <p>
	 * The default {@link PropertyXLSValueProvider}s are loaded using fully qualified name of its implementation class
	 * name to a <code>com.holonplatform.artisan.vaadin.flow.export.PropertyXLSValueProvider</code> file in the
	 * <code>META-INF/services</code> directory.
	 * </p>
	 * @param classLoader ClassLoader to use, or <code>null</code> for the default ClassLoader
	 * @return Default PropertyXLSValueProviders, or an empty List if none
	 */
	public static List<PropertyXLSValueProvider> getDefaultProviders(ClassLoader classLoader) {
		return ensureInited((classLoader != null) ? classLoader : ClassUtils.getDefaultClassLoader());
	}

	/**
	 * Ensure the default {@link PropertyXLSValueProvider} list is inited loading instances from
	 * <code>META-INF/services</code>.
	 * @param classLoader ClassLoader to use
	 * @return Default PropertyXLSValueProviders
	 */
	private static synchronized List<PropertyXLSValueProvider> ensureInited(final ClassLoader classLoader) {
		if (!PROVIDERS.containsKey(classLoader)) {

			LOGGER.debug(() -> "Load PropertyXLSValueProviders for classloader [" + classLoader
					+ "] using ServiceLoader with service name: " + PropertyXLSValueProvider.class.getName());

			final List<PropertyXLSValueProvider> result = new LinkedList<>();
			// load from META-INF/services
			Iterable<PropertyXLSValueProvider> providers = AccessController
					.doPrivileged(new PrivilegedAction<Iterable<PropertyXLSValueProvider>>() {
						@Override
						public Iterable<PropertyXLSValueProvider> run() {
							return ServiceLoader.load(PropertyXLSValueProvider.class, classLoader);
						}
					});
			providers.forEach(pr -> {
				result.add(pr);

				LOGGER.debug(() -> "Loaded and registered PropertyXLSValueProvider [" + pr + "] for classloader ["
						+ classLoader + "]");
			});
			PROVIDERS.put(classLoader, result);
		}
		return PROVIDERS.get(classLoader);
	}

	private DefaultPropertyXLSValueProviders() {
	}

}
