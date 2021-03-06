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
package com.holonplatform.artisan.vaadin.flow.components.internal.support;

import com.holonplatform.core.property.Property;

/**
 * A registry to handle {@link InputFilterPropertyConfiguration}s.
 * 
 * @since 1.0.0
 */
public interface InputFilterPropertyConfigurationRegistry {

	/**
	 * Get the {@link InputFilterPropertyConfiguration} bound to given property.
	 * @param <T> Property type
	 * @param property The property for which to obtain the configuration (not null)
	 * @return The {@link InputFilterPropertyConfiguration} bound to given property
	 */
	<T> InputFilterPropertyConfiguration<T> get(Property<T> property);

	/**
	 * Create a new {@link InputFilterPropertyConfigurationRegistry}.
	 * @return A new {@link InputFilterPropertyConfigurationRegistry}
	 */
	static InputFilterPropertyConfigurationRegistry create() {
		return new DefaultInputFilterPropertyConfigurationRegistry();
	}

}
