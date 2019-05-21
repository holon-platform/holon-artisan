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
package com.holonplatform.artisan.vaadin.flow.export.xls;

import javax.annotation.Priority;

import com.holonplatform.core.property.Property;

/**
 * Provides a {@link XLSValue} representation for a {@link Property} bound value.
 * <p>
 * PropertyXLSValueProviders are managed by a {@link PropertyXLSValueProviderRegistry}, which handles the providers
 * registration and returns a suitable provider for a {@link Property}, relying on the conditions with which the
 * providers were registered.
 * </p>
 * 
 * @param <T> Value type
 *
 * @since 1.0.0
 * 
 * @see PropertyXLSValueProviderRegistry
 */
public interface PropertyXLSValueProvider<T> {

	/**
	 * Default {@link PropertyXLSValueProvider} priority if not specified using the {@link Priority} annotation.
	 */
	public static final int DEFAULT_PRIORITY = 10000;

	/**
	 * Get the {@link XLSValue} representation for given property bound value.
	 * @param context The property export context
	 * @param value The value to export
	 * @return The {@link XLSValue} representation of the property value
	 */
	XLSValue<?> provide(XLSPropertyValueContext<T> context, T value);

}
