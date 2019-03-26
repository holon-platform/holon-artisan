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

import java.io.Serializable;
import java.util.Optional;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.property.PropertyRenderer;

/**
 * Configuration for a {@link Property} bound to a {@link InputFilter}.
 * 
 * @param <T> Property type
 *
 * @since 1.0.0
 */
public interface InputFilterPropertyConfiguration<T> extends Serializable {

	/**
	 * Get the property.
	 * @return the property.
	 */
	Property<T> getProperty();

	/**
	 * Get whether the property is hidden.
	 * @return whether the property is hidden
	 */
	boolean isHidden();

	/**
	 * Set whether the property is hidden.
	 * @param hidden whether the property is hidden
	 */
	public void setHidden(boolean hidden);

	/**
	 * Get the property renderer, if available.
	 * @return Optional property renderer
	 */
	Optional<PropertyRenderer<InputFilter<T>, T>> getRenderer();

	/**
	 * Set the property renderer.
	 * @param renderer The property renderer to set (may be null)
	 */
	void setRenderer(PropertyRenderer<InputFilter<T>, T> renderer);

	/**
	 * Create a new {@link InputFilterPropertyConfiguration} for given property.
	 * @param <T> Property type
	 * @param property The property (not null)
	 * @return A new {@link InputFilterPropertyConfiguration}
	 */
	static <T> InputFilterPropertyConfiguration<T> create(Property<T> property) {
		return new DefaultInputFilterPropertyConfiguration<>(property);
	}

}
