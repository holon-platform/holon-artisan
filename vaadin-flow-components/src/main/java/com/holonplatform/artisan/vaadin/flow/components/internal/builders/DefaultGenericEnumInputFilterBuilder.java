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
import com.holonplatform.artisan.vaadin.flow.components.InputFilter.EnumFilterMode;
import com.holonplatform.artisan.vaadin.flow.components.builders.EnumInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.EnumInputFilterBuilder.GenericEnumInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.utils.ComponentUtils;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.builders.OptionsMultiSelectConfigurator.OptionsMultiSelectInputBuilder;
import com.holonplatform.vaadin.flow.internal.components.EnumItemCaptionGenerator;
import com.holonplatform.vaadin.flow.internal.components.events.DefaultValueChangeEvent;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.Element;

/**
 * Default {@link GenericEnumInputFilterBuilder} implementation.
 * 
 * @param <T> Enumeration type
 *
 * @since 1.0.0
 */
public class DefaultGenericEnumInputFilterBuilder<T extends Enum<T>> implements GenericEnumInputFilterBuilder<T> {

	private final EnumInputFilterBuilder<T, T, ?> inputFilterBuilder;

	public DefaultGenericEnumInputFilterBuilder(Property<T> property, EnumFilterMode mode) {
		super();
		ObjectUtils.argumentNotNull(mode, "Filter mode must be not null");
		switch (mode) {
		case MULTI_OPTION:
			inputFilterBuilder = new EnumMultiOptionInputFilterAdapterBuilder<>(property);
			break;
		case SINGLE_OPTION:
			inputFilterBuilder = EnumInputFilterBuilder.singleOption(property);
			break;
		case SINGLE_SELECT:
		default:
			inputFilterBuilder = EnumInputFilterBuilder.singleSelect(property);
			break;
		}
	}

	@Override
	public InputFilter<T> build() {
		return inputFilterBuilder.build();
	}

	@Override
	public GenericEnumInputFilterBuilder<T> dataSource(ListDataProvider<T> dataProvider) {
		inputFilterBuilder.dataSource(dataProvider);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> readOnly(boolean readOnly) {
		inputFilterBuilder.readOnly();
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> withValueChangeListener(
			ValueChangeListener<T, ValueChangeEvent<T>> listener) {
		inputFilterBuilder.withValueChangeListener(listener);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> required(boolean required) {
		inputFilterBuilder.required(required);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> id(String id) {
		inputFilterBuilder.id(id);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> visible(boolean visible) {
		inputFilterBuilder.visible(visible);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> elementConfiguration(Consumer<Element> element) {
		inputFilterBuilder.elementConfiguration(element);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> withAttachListener(ComponentEventListener<AttachEvent> listener) {
		inputFilterBuilder.withAttachListener(listener);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> withDetachListener(ComponentEventListener<DetachEvent> listener) {
		inputFilterBuilder.withDetachListener(listener);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> withThemeName(String themeName) {
		inputFilterBuilder.withThemeName(themeName);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> withEventListener(String eventType, DomEventListener listener) {
		inputFilterBuilder.withEventListener(eventType, listener);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> withEventListener(String eventType, DomEventListener listener,
			String filter) {
		inputFilterBuilder.withEventListener(eventType, listener, filter);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> itemCaptionGenerator(ItemCaptionGenerator<T> itemCaptionGenerator) {
		inputFilterBuilder.itemCaptionGenerator(itemCaptionGenerator);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> itemCaption(T item, Localizable caption) {
		inputFilterBuilder.itemCaption(item, caption);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> width(String width) {
		inputFilterBuilder.width(width);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> height(String height) {
		inputFilterBuilder.height(height);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> minWidth(String minWidth) {
		inputFilterBuilder.minWidth(minWidth);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> maxWidth(String maxWidth) {
		inputFilterBuilder.maxWidth(maxWidth);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> minHeight(String minHeight) {
		inputFilterBuilder.minHeight(minHeight);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> maxHeight(String maxHeight) {
		inputFilterBuilder.maxHeight(maxHeight);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> styleNames(String... styleNames) {
		inputFilterBuilder.styleNames(styleNames);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> styleName(String styleName) {
		inputFilterBuilder.styleName(styleName);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> label(Localizable label) {
		inputFilterBuilder.label(label);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> enabled(boolean enabled) {
		inputFilterBuilder.enabled(enabled);
		return this;
	}

	@Override
	public GenericEnumInputFilterBuilder<T> withDeferredLocalization(boolean deferredLocalization) {
		inputFilterBuilder.withDeferredLocalization(deferredLocalization);
		return this;
	}

	@Override
	public boolean isDeferredLocalizationEnabled() {
		return inputFilterBuilder.isDeferredLocalizationEnabled();
	}

	private class EnumMultiOptionInputFilterAdapterBuilder<E extends Enum<E>>
			implements EnumInputFilterBuilder<E, E, EnumMultiOptionInputFilterAdapterBuilder<E>> {

		private final Property<E> queryProperty;

		private final OptionsMultiSelectInputBuilder<E, E> inputBuilder;

		public EnumMultiOptionInputFilterAdapterBuilder(Property<E> property) {
			super();
			ObjectUtils.argumentNotNull(property, "Property must be not null");
			this.queryProperty = property;
			@SuppressWarnings("unchecked")
			final Class<E> enumType = (Class<E>) property.getType();
			inputBuilder = Input.multiOptionSelect(enumType).items(enumType.getEnumConstants())
					.itemCaptionGenerator(new EnumItemCaptionGenerator<>()).label(property);
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.components.builders.InputFilterBuilder#build()
		 */
		@Override
		public InputFilter<E> build() {
			return ComponentUtils.asSingleValueInputFilter(InputFilter.from(inputBuilder.build(), value -> {
				if (value != null && !value.isEmpty()) {
					return QueryFilter.in(queryProperty, value);
				}
				return null;
			}));
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.InputConfigurator#readOnly(boolean)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> readOnly(boolean readOnly) {
			inputBuilder.readOnly(readOnly);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.InputConfigurator#withValueChangeListener(com.
		 * holonplatform. vaadin.flow.components.ValueHolder.ValueChangeListener)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> withValueChangeListener(
				ValueChangeListener<E, ValueChangeEvent<E>> listener) {
			ObjectUtils.argumentNotNull(listener, "ValueChangeListener must be not null");
			inputBuilder.withValueChangeListener(e -> {
				final E value = (e.getValue() != null && !e.getValue().isEmpty()) ? e.getValue().iterator().next()
						: null;
				final E oldValue = (e.getOldValue() != null && !e.getOldValue().isEmpty())
						? e.getOldValue().iterator().next()
						: null;
				listener.valueChange(new DefaultValueChangeEvent<>(null, oldValue, value, e.isUserOriginated()));
			});
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.InputConfigurator#required(boolean)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> required(boolean required) {
			inputBuilder.required(required);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#id(java.lang.String)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> id(String id) {
			inputBuilder.id(id);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#visible(boolean)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> visible(boolean visible) {
			inputBuilder.visible(visible);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#elementConfiguration(java.util.
		 * function. Consumer)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> elementConfiguration(Consumer<Element> element) {
			inputBuilder.elementConfiguration(element);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withAttachListener(com.vaadin.flow.
		 * component.ComponentEventListener)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> withAttachListener(
				ComponentEventListener<AttachEvent> listener) {
			inputBuilder.withAttachListener(listener);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withDetachListener(com.vaadin.flow.
		 * component.ComponentEventListener)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> withDetachListener(
				ComponentEventListener<DetachEvent> listener) {
			inputBuilder.withDetachListener(listener);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withThemeName(java.lang.String)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> withThemeName(String themeName) {
			inputBuilder.withThemeName(themeName);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
		 * com.vaadin.flow.dom.DomEventListener)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> withEventListener(String eventType,
				DomEventListener listener) {
			inputBuilder.withEventListener(eventType, listener);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
		 * com.vaadin.flow.dom.DomEventListener, java.lang.String)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> withEventListener(String eventType,
				DomEventListener listener, String filter) {
			inputBuilder.withEventListener(eventType, listener, filter);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.vaadin.flow.components.builders.SingleSelectableInputConfigurator#dataSource(com.vaadin.
		 * flow. data.provider.ListDataProvider)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> dataSource(ListDataProvider<E> dataProvider) {
			inputBuilder.dataSource(dataProvider);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleNames(java.lang.String[])
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> styleNames(String... styleNames) {
			inputBuilder.styleNames(styleNames);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleName(java.lang.String)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> styleName(String styleName) {
			inputBuilder.styleName(styleName);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.HasEnabledConfigurator#enabled(boolean)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> enabled(boolean enabled) {
			inputBuilder.enabled(enabled);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.DeferrableLocalizationConfigurator#
		 * withDeferredLocalization( boolean)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> withDeferredLocalization(boolean deferredLocalization) {
			inputBuilder.withDeferredLocalization(deferredLocalization);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.vaadin.flow.components.builders.HasDeferrableLocalization#isDeferredLocalizationEnabled()
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
		public EnumMultiOptionInputFilterAdapterBuilder<E> itemCaptionGenerator(
				ItemCaptionGenerator<E> itemCaptionGenerator) {
			inputBuilder.itemCaptionGenerator(itemCaptionGenerator);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.vaadin.flow.components.builders.HasItemCaptionConfigurator#itemCaption(java.lang.Object,
		 * com.holonplatform.core.i18n.Localizable)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> itemCaption(E item, Localizable caption) {
			inputBuilder.itemCaption(item, caption);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#width(java.lang.String)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> width(String width) {
			// ignored
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#height(java.lang.String)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> height(String height) {
			// ignored
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minWidth(java.lang.String)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> minWidth(String minWidth) {
			// ignored
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxWidth(java.lang.String)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> maxWidth(String maxWidth) {
			// ignored
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minHeight(java.lang.String)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> minHeight(String minHeight) {
			// ignored
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxHeight(java.lang.String)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> maxHeight(String maxHeight) {
			// ignored
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.vaadin.flow.components.builders.HasLabelConfigurator#label(com.holonplatform.core.i18n.
		 * Localizable)
		 */
		@Override
		public EnumMultiOptionInputFilterAdapterBuilder<E> label(Localizable label) {
			inputBuilder.label(label);
			return this;
		}

	}

}
