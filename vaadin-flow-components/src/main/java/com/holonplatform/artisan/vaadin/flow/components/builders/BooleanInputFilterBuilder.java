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
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultBooleanInputFilterBuilder;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.builders.DeferrableLocalizationConfigurator;
import com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasAutofocusConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasEnabledConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasLabelConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator;
import com.vaadin.flow.component.Component;

/**
 * Boolean type {@link InputFilter} builder.
 *
 * @since 1.0.0
 */
public interface BooleanInputFilterBuilder
		extends InputFilterBuilder<Boolean, ValueChangeEvent<Boolean>, BooleanInputFilterBuilder>,
		HasSizeConfigurator<BooleanInputFilterBuilder>, HasStyleConfigurator<BooleanInputFilterBuilder>,
		HasEnabledConfigurator<BooleanInputFilterBuilder>, HasAutofocusConfigurator<BooleanInputFilterBuilder>,
		FocusableConfigurator<Component, BooleanInputFilterBuilder>, HasLabelConfigurator<BooleanInputFilterBuilder>,
		DeferrableLocalizationConfigurator<BooleanInputFilterBuilder> {

	public static final Localizable DEFAULT_EMPTY_VALUE_CAPTION = Localizable.of("All",
			"com.holonplatform.artisan.vaadin.flow.components.input.filter.boolean.empty.value.caption");

	/**
	 * Set the caption for the empty value.
	 * @param caption The caption to set
	 * @return this
	 */
	BooleanInputFilterBuilder emptyValueCaption(Localizable caption);

	/**
	 * Set the caption for the empty value.
	 * @param caption The caption to set
	 * @return this
	 */
	default BooleanInputFilterBuilder emptyValueCaption(String caption) {
		return emptyValueCaption((caption == null) ? null : Localizable.builder().message(caption).build());
	}

	/**
	 * Set the caption for the empty value using a localizable <code>messageCode</code>.
	 * @param defaultCaption Default caption if no translation is available for given <code>messageCode</code>
	 * @param messageCode The caption translation message key
	 * @param arguments Optional translation arguments
	 * @return this
	 */
	default BooleanInputFilterBuilder emptyValueCaption(String defaultCaption, String messageCode,
			Object... arguments) {
		return emptyValueCaption(Localizable.builder().message((defaultCaption == null) ? "" : defaultCaption)
				.messageCode(messageCode).messageArguments(arguments).build());
	}

	/**
	 * Set the caption for the <code>true</code> value.
	 * @param caption The caption to set (not null)
	 * @return this
	 */
	BooleanInputFilterBuilder trueValueCaption(Localizable caption);

	/**
	 * Set the caption for the <code>true</code> value.
	 * @param caption The caption to set (not null)
	 * @return this
	 */
	default BooleanInputFilterBuilder trueValueCaption(String caption) {
		return trueValueCaption((caption == null) ? null : Localizable.builder().message(caption).build());
	}

	/**
	 * Set the caption for the <code>true</code> value using a localizable <code>messageCode</code>.
	 * @param defaultCaption Default caption if no translation is available for given <code>messageCode</code>
	 * @param messageCode The caption translation message key
	 * @param arguments Optional translation arguments
	 * @return this
	 */
	default BooleanInputFilterBuilder trueValueCaption(String defaultCaption, String messageCode, Object... arguments) {
		return trueValueCaption(Localizable.builder().message((defaultCaption == null) ? "" : defaultCaption)
				.messageCode(messageCode).messageArguments(arguments).build());
	}

	/**
	 * Set the caption for the <code>false</code> value.
	 * @param caption The caption to set (not null)
	 * @return this
	 */
	BooleanInputFilterBuilder falseValueCaption(Localizable caption);

	/**
	 * Set the caption for the <code>false</code> value.
	 * @param caption The caption to set (not null)
	 * @return this
	 */
	default BooleanInputFilterBuilder falseValueCaption(String caption) {
		return falseValueCaption((caption == null) ? null : Localizable.builder().message(caption).build());
	}

	/**
	 * Set the caption for the <code>false</code> value using a localizable <code>messageCode</code>.
	 * @param defaultCaption Default caption if no translation is available for given <code>messageCode</code>
	 * @param messageCode The caption translation message key
	 * @param arguments Optional translation arguments
	 * @return this
	 */
	default BooleanInputFilterBuilder falseValueCaption(String defaultCaption, String messageCode,
			Object... arguments) {
		return falseValueCaption(Localizable.builder().message((defaultCaption == null) ? "" : defaultCaption)
				.messageCode(messageCode).messageArguments(arguments).build());
	}

	/**
	 * Create a new {@link BooleanInputFilterBuilder}.
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link BooleanInputFilterBuilder}
	 */
	static BooleanInputFilterBuilder create(Property<Boolean> property) {
		return new DefaultBooleanInputFilterBuilder(property);
	}

}
