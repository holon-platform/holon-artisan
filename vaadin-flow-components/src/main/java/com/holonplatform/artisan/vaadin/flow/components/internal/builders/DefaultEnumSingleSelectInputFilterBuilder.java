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
package com.holonplatform.artisan.vaadin.flow.components.internal.builders;

import java.util.function.Consumer;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.builders.EnumInputFilterBuilder.EnumSingleSelectInputFilterBuilder;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.builders.FilterableSingleSelectConfigurator.FilterableSingleSelectInputBuilder;
import com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasAutofocusConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasLabelConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasPlaceholderConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator;
import com.holonplatform.vaadin.flow.components.builders.InputBuilder;
import com.holonplatform.vaadin.flow.components.builders.ShortcutConfigurator;
import com.holonplatform.vaadin.flow.components.builders.SingleSelectConfigurator.SingleSelectInputBuilder;
import com.holonplatform.vaadin.flow.components.builders.SingleSelectableInputConfigurator;
import com.holonplatform.vaadin.flow.device.DeviceInfo;
import com.holonplatform.vaadin.flow.internal.components.EnumItemCaptionGenerator;
import com.holonplatform.vaadin.flow.internal.components.builders.DelegatedShortcutConfigurator;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.Element;

/**
 * Default {@link EnumSingleSelectInputFilterBuilder} implementation.
 * 
 * @param <T> Enumeration type
 *
 * @since 1.0.0
 */
public class DefaultEnumSingleSelectInputFilterBuilder<T extends Enum<T>>
		implements EnumSingleSelectInputFilterBuilder<T> {

	private final Property<T> property;

	private final InputBuilder<T, ValueChangeEvent<T>, ? extends Input<T>, ?, ?, ?> inputBuilder;
	private final SingleSelectableInputConfigurator<T, T, ?> singleSelectableConfigurator;
	private final HasSizeConfigurator<?> sizeConfigurator;
	private final HasPlaceholderConfigurator<?> placeholderConfigurator;
	private final HasLabelConfigurator<?> labelConfigurator;
	private final HasAutofocusConfigurator<?> autofocusConfigurator;
	private final FocusableConfigurator<Component, ?> focusableConfigurator;

	public DefaultEnumSingleSelectInputFilterBuilder(Property<T> property) {
		super();
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		this.property = property;

		final boolean mobile = DeviceInfo.get().map(di -> di.isMobile()).orElse(false);

		@SuppressWarnings("unchecked")
		final Class<T> enumType = (Class<T>) property.getType();

		if (mobile) {
			final SingleSelectInputBuilder<T, T> builder = Input.singleSimpleSelect(enumType)
					.items(enumType.getEnumConstants()).itemCaptionGenerator(new EnumItemCaptionGenerator<>())
					.emptySelectionAllowed(true);
			this.inputBuilder = builder;
			this.singleSelectableConfigurator = builder;
			this.sizeConfigurator = builder;
			this.placeholderConfigurator = builder;
			this.labelConfigurator = builder;
			this.autofocusConfigurator = builder;
			this.focusableConfigurator = builder;
		} else {
			final FilterableSingleSelectInputBuilder<T, T> builder = Input.singleSelect(enumType)
					.items(enumType.getEnumConstants()).itemCaptionGenerator(new EnumItemCaptionGenerator<>());
			this.inputBuilder = builder;
			this.singleSelectableConfigurator = builder;
			this.sizeConfigurator = builder;
			this.placeholderConfigurator = builder;
			this.labelConfigurator = builder;
			this.autofocusConfigurator = builder;
			this.focusableConfigurator = builder;
		}

		this.labelConfigurator.label(property);

	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.InputFilterBuilder#build()
	 */
	@Override
	public InputFilter<T> build() {
		return InputFilter.from(inputBuilder.build(), value -> {
			if (value != null) {
				return QueryFilter.eq(property, value);
			}
			return null;
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.InputConfigurator#readOnly(boolean)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> readOnly(boolean readOnly) {
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
	public EnumSingleSelectInputFilterBuilder<T> withValueChangeListener(
			ValueChangeListener<T, ValueChangeEvent<T>> listener) {
		inputBuilder.withValueChangeListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.InputConfigurator#required(boolean)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> required(boolean required) {
		inputBuilder.required(required);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#id(java.lang.String)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> id(String id) {
		inputBuilder.id(id);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#visible(boolean)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> visible(boolean visible) {
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
	public EnumSingleSelectInputFilterBuilder<T> elementConfiguration(Consumer<Element> element) {
		inputBuilder.elementConfiguration(element);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withAttachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> withAttachListener(ComponentEventListener<AttachEvent> listener) {
		inputBuilder.withAttachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withDetachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> withDetachListener(ComponentEventListener<DetachEvent> listener) {
		inputBuilder.withDetachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withThemeName(java.lang.String)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> withThemeName(String themeName) {
		inputBuilder.withThemeName(themeName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> withEventListener(String eventType, DomEventListener listener) {
		inputBuilder.withEventListener(eventType, listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener, java.lang.String)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> withEventListener(String eventType, DomEventListener listener,
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
	public EnumSingleSelectInputFilterBuilder<T> dataSource(ListDataProvider<T> dataProvider) {
		singleSelectableConfigurator.dataSource(dataProvider);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleNames(java.lang.String[])
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> styleNames(String... styleNames) {
		singleSelectableConfigurator.styleNames(styleNames);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleName(java.lang.String)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> styleName(String styleName) {
		singleSelectableConfigurator.styleName(styleName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasEnabledConfigurator#enabled(boolean)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> enabled(boolean enabled) {
		singleSelectableConfigurator.enabled(enabled);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.DeferrableLocalizationConfigurator#withDeferredLocalization(
	 * boolean)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> withDeferredLocalization(boolean deferredLocalization) {
		singleSelectableConfigurator.withDeferredLocalization(deferredLocalization);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasDeferrableLocalization#isDeferredLocalizationEnabled()
	 */
	@Override
	public boolean isDeferredLocalizationEnabled() {
		return singleSelectableConfigurator.isDeferredLocalizationEnabled();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasItemCaptionConfigurator#itemCaptionGenerator(com.
	 * holonplatform.vaadin.flow.components.builders.ItemSetConfigurator.ItemCaptionGenerator)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> itemCaptionGenerator(ItemCaptionGenerator<T> itemCaptionGenerator) {
		singleSelectableConfigurator.itemCaptionGenerator(itemCaptionGenerator);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasItemCaptionConfigurator#itemCaption(java.lang.Object,
	 * com.holonplatform.core.i18n.Localizable)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> itemCaption(T item, Localizable caption) {
		singleSelectableConfigurator.itemCaption(item, caption);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#width(java.lang.String)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> width(String width) {
		sizeConfigurator.width(width);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#height(java.lang.String)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> height(String height) {
		sizeConfigurator.height(height);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minWidth(java.lang.String)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> minWidth(String minWidth) {
		sizeConfigurator.minWidth(minWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxWidth(java.lang.String)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> maxWidth(String maxWidth) {
		sizeConfigurator.maxWidth(maxWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minHeight(java.lang.String)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> minHeight(String minHeight) {
		sizeConfigurator.minHeight(minHeight);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxHeight(java.lang.String)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> maxHeight(String maxHeight) {
		sizeConfigurator.maxHeight(maxHeight);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasLabelConfigurator#label(com.holonplatform.core.i18n.
	 * Localizable)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> label(Localizable label) {
		labelConfigurator.label(label);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.HasPlaceholderConfigurator#placeholder(com.holonplatform.core.
	 * i18n.Localizable)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> placeholder(Localizable placeholder) {
		placeholderConfigurator.placeholder(placeholder);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasAutofocusConfigurator#autofocus(boolean)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> autofocus(boolean autofocus) {
		autofocusConfigurator.autofocus(autofocus);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#tabIndex(int)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> tabIndex(int tabIndex) {
		focusableConfigurator.tabIndex(tabIndex);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#withFocusListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> withFocusListener(
			ComponentEventListener<FocusEvent<Component>> listener) {
		focusableConfigurator.withFocusListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#withBlurListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public EnumSingleSelectInputFilterBuilder<T> withBlurListener(
			ComponentEventListener<BlurEvent<Component>> listener) {
		focusableConfigurator.withBlurListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#withFocusShortcut(com.vaadin.flow.
	 * component.Key)
	 */
	@Override
	public ShortcutConfigurator<EnumSingleSelectInputFilterBuilder<T>> withFocusShortcut(Key key) {
		return new DelegatedShortcutConfigurator<>(focusableConfigurator.withFocusShortcut(key), this);
	}

}
