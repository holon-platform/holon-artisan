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

import java.util.Optional;

import com.holonplatform.artisan.core.utils.Obj;
import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.property.PropertyRenderer;

/**
 * Default {@link InputFilterPropertyConfiguration} implementation.
 *
 * @param <T> Property type
 * 
 * @since 1.0.0
 */
public class DefaultInputFilterPropertyConfiguration<T> implements InputFilterPropertyConfiguration<T> {

	private static final long serialVersionUID = 1946458796719975474L;

	private final Property<T> property;

	private boolean hidden;

	private PropertyRenderer<InputFilter<T>, T> renderer;

	public DefaultInputFilterPropertyConfiguration(Property<T> property) {
		super();
		Obj.argumentNotNull(property, "Property must be not null");
		this.property = property;
	}

	@Override
	public Property<T> getProperty() {
		return property;
	}

	@Override
	public boolean isHidden() {
		return hidden;
	}

	@Override
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	@Override
	public Optional<PropertyRenderer<InputFilter<T>, T>> getRenderer() {
		return Optional.ofNullable(renderer);
	}

	@Override
	public void setRenderer(PropertyRenderer<InputFilter<T>, T> renderer) {
		this.renderer = renderer;
	}

}
