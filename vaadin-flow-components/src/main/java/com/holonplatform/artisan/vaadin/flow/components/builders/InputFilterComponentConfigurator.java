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
package com.holonplatform.artisan.vaadin.flow.components.builders;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterComponent;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterGroup;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator;
import com.holonplatform.vaadin.flow.components.builders.ComposableConfigurator;
import com.holonplatform.vaadin.flow.i18n.LocalizationProvider;
import com.vaadin.flow.component.HasElement;

/**
 * {@link InputFilterComponent} configurator.
 * 
 * @param <E> Component element type
 * @param <C> Concrete configurator type
 *
 * @since 1.0.0
 */
public interface InputFilterComponentConfigurator<E extends HasElement, C extends InputFilterComponentConfigurator<E, C>>
		extends ComposableConfigurator<E, InputFilter<?>, InputFilterGroup, C>, ComponentConfigurator<C> {

	/**
	 * Set the localizable caption for the component bound to given property. By default, the caption is obtained from
	 * {@link Property} itself (which is {@link Localizable}).
	 * @param property Property for which to set the caption (not null)
	 * @param caption Localizable caption
	 * @return this
	 * @see LocalizationProvider
	 */
	C propertyCaption(Property<?> property, Localizable caption);

	/**
	 * Sets the caption for the component bound to given property.
	 * @param property Property for which to set the caption (not null)
	 * @param caption The caption text to set
	 * @return this
	 */
	default C propertyCaption(Property<?> property, String caption) {
		return propertyCaption(property, (caption == null) ? null : Localizable.builder().message(caption).build());
	}

	/**
	 * Sets the caption for the component bound to given property using a localizable <code>messageCode</code>.
	 * @param property Property for which to set the caption (not null)
	 * @param defaultCaption Default caption text if no translation is available for given <code>messageCode</code>
	 * @param messageCode Caption translation message key
	 * @param arguments Optional translation arguments
	 * @return this
	 * @see LocalizationProvider
	 */
	default C propertyCaption(Property<?> property, String defaultCaption, String messageCode, Object... arguments) {
		return propertyCaption(property, Localizable.builder().message((defaultCaption == null) ? "" : defaultCaption)
				.messageCode(messageCode).messageArguments(arguments).build());
	}

}
