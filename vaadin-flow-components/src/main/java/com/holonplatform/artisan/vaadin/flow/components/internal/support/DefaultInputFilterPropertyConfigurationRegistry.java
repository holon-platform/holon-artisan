/*
 * Copyright 2016-2018 Axioma srl.
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
package com.holonplatform.artisan.vaadin.flow.components.internal.support;

import java.util.HashMap;
import java.util.Map;

import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;

/**
 * Default {@link InputFilterPropertyConfigurationRegistry} implementation.
 * 
 * @since 1.0.0
 */
public class DefaultInputFilterPropertyConfigurationRegistry implements InputFilterPropertyConfigurationRegistry {

	private final Map<Property<?>, InputFilterPropertyConfiguration<?>> configurations = new HashMap<>();

	@SuppressWarnings("unchecked")
	@Override
	public <T> InputFilterPropertyConfiguration<T> get(Property<T> property) {
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		return (InputFilterPropertyConfiguration<T>) configurations.computeIfAbsent(property,
				p -> InputFilterPropertyConfiguration.create(p));
	}

}
