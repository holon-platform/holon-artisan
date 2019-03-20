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
package com.holonplatform.artisan.vaadin.flow.components.internal.builders;

import java.util.Set;
import java.util.function.Consumer;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.builders.EnumInputFilterBuilder.EnumMultiOptionInputFilterBuilder;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.builders.OptionsMultiSelectConfigurator.OptionsMultiSelectInputBuilder;
import com.holonplatform.vaadin.flow.internal.components.EnumItemCaptionGenerator;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.Element;

/**
 * Default {@link EnumMultiOptionInputFilterBuilder} implementation.
 * 
 * @param <T> Enumeration type
 *
 * @since 1.0.0
 */
public class DefaultEnumMultiOptionInputFilterBuilder<T extends Enum<T>>
		implements EnumMultiOptionInputFilterBuilder<T> {

	private final Property<T> property;

	private final OptionsMultiSelectInputBuilder<T, T> inputBuilder;

	public DefaultEnumMultiOptionInputFilterBuilder(Property<T> property) {
		super();
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		this.property = property;

		@SuppressWarnings("unchecked")
		final Class<T> enumType = (Class<T>) property.getType();
		inputBuilder = Input.multiOptionSelect(enumType).items(enumType.getEnumConstants())
				.itemCaptionGenerator(new EnumItemCaptionGenerator<>()).label(property);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.InputFilterBuilder#build()
	 */
	@Override
	public InputFilter<Set<T>> build() {
		return InputFilter.from(inputBuilder.build(), value -> {
			if (value != null && !value.isEmpty()) {
				return QueryFilter.in(property, value);
			}
			return null;
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.InputConfigurator#readOnly(boolean)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> readOnly(boolean readOnly) {
		inputBuilder.readOnly(readOnly);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.InputConfigurator#withValueChangeListener(com.holonplatform.
	 * vaadin.flow.components.ValueHolder.ValueChangeListener)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> withValueChangeListener(
			ValueChangeListener<Set<T>, ValueChangeEvent<Set<T>>> listener) {
		inputBuilder.withValueChangeListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.InputConfigurator#required(boolean)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> required(boolean required) {
		inputBuilder.required(required);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#id(java.lang.String)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> id(String id) {
		inputBuilder.id(id);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#visible(boolean)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> visible(boolean visible) {
		inputBuilder.visible(visible);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#elementConfiguration(java.util.function.
	 * Consumer)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> elementConfiguration(Consumer<Element> element) {
		inputBuilder.elementConfiguration(element);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withAttachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> withAttachListener(ComponentEventListener<AttachEvent> listener) {
		inputBuilder.withAttachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withDetachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> withDetachListener(ComponentEventListener<DetachEvent> listener) {
		inputBuilder.withDetachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withThemeName(java.lang.String)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> withThemeName(String themeName) {
		inputBuilder.withThemeName(themeName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> withEventListener(String eventType, DomEventListener listener) {
		inputBuilder.withEventListener(eventType, listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener, java.lang.String)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> withEventListener(String eventType, DomEventListener listener,
			String filter) {
		inputBuilder.withEventListener(eventType, listener, filter);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.SingleSelectableInputConfigurator#dataSource(com.vaadin.flow.
	 * data.provider.ListDataProvider)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> dataSource(ListDataProvider<T> dataProvider) {
		inputBuilder.dataSource(dataProvider);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleNames(java.lang.String[])
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> styleNames(String... styleNames) {
		inputBuilder.styleNames(styleNames);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleName(java.lang.String)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> styleName(String styleName) {
		inputBuilder.styleName(styleName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasEnabledConfigurator#enabled(boolean)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> enabled(boolean enabled) {
		inputBuilder.enabled(enabled);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.DeferrableLocalizationConfigurator#withDeferredLocalization(
	 * boolean)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> withDeferredLocalization(boolean deferredLocalization) {
		inputBuilder.withDeferredLocalization(deferredLocalization);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasDeferrableLocalization#isDeferredLocalizationEnabled()
	 */
	@Override
	public boolean isDeferredLocalizationEnabled() {
		return inputBuilder.isDeferredLocalizationEnabled();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasItemCaptionConfigurator#itemCaptionGenerator(com.
	 * holonplatform.vaadin.flow.components.builders.ItemSetConfigurator.ItemCaptionGenerator)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> itemCaptionGenerator(ItemCaptionGenerator<T> itemCaptionGenerator) {
		inputBuilder.itemCaptionGenerator(itemCaptionGenerator);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasItemCaptionConfigurator#itemCaption(java.lang.Object,
	 * com.holonplatform.core.i18n.Localizable)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> itemCaption(T item, Localizable caption) {
		inputBuilder.itemCaption(item, caption);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#width(java.lang.String)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> width(String width) {
		// ignored
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#height(java.lang.String)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> height(String height) {
		// ignored
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minWidth(java.lang.String)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> minWidth(String minWidth) {
		// ignored
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxWidth(java.lang.String)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> maxWidth(String maxWidth) {
		// ignored
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minHeight(java.lang.String)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> minHeight(String minHeight) {
		// ignored
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxHeight(java.lang.String)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> maxHeight(String maxHeight) {
		// ignored
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasLabelConfigurator#label(com.holonplatform.core.i18n.
	 * Localizable)
	 */
	@Override
	public EnumMultiOptionInputFilterBuilder<T> label(Localizable label) {
		inputBuilder.label(label);
		return this;
	}

}
