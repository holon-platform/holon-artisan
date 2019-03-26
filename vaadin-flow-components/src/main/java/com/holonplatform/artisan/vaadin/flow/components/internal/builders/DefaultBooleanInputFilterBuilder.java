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
import java.util.function.Function;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.builders.BooleanInputFilterBuilder;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.i18n.LocalizationContext;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.builders.ItemSetConfigurator.ItemCaptionGenerator;
import com.holonplatform.vaadin.flow.components.builders.ShortcutConfigurator;
import com.holonplatform.vaadin.flow.components.builders.SingleSelectConfigurator.SingleSelectInputBuilder;
import com.holonplatform.vaadin.flow.i18n.LocalizationProvider;
import com.holonplatform.vaadin.flow.internal.components.builders.DelegatedShortcutConfigurator;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.BlurNotifier.BlurEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.FocusNotifier.FocusEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.Element;

/**
 * Default {@link BooleanInputFilterBuilder} implementation.
 *
 * @since 1.0.0
 */
public class DefaultBooleanInputFilterBuilder implements BooleanInputFilterBuilder {

	private final Property<Boolean> property;

	private final SingleSelectInputBuilder<Boolean, Boolean> inputBuilder;

	private Localizable emptyCaption;
	private Localizable trueCaption;
	private Localizable falseCaption;

	public DefaultBooleanInputFilterBuilder(Property<Boolean> property) {
		super();
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		this.property = property;
		this.inputBuilder = Input.singleSimpleSelect(Boolean.class).items(Boolean.TRUE, Boolean.FALSE)
				.emptySelectionAllowed(true).label(property);
	}

	private static final class SelectItemCaptionGenerator
			implements Function<Boolean, String>, ItemCaptionGenerator<Boolean> {

		private static final long serialVersionUID = -4586990924642326428L;

		private final Localizable emptyCaption;
		private final Localizable trueCaption;
		private final Localizable falseCaption;

		public SelectItemCaptionGenerator(Localizable emptyCaption, Localizable trueCaption, Localizable falseCaption) {
			super();
			this.emptyCaption = emptyCaption;
			this.trueCaption = trueCaption;
			this.falseCaption = falseCaption;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.function.Function#apply(java.lang.Object)
		 */
		@Override
		public String apply(Boolean value) {
			if (value == null) {
				if (emptyCaption != null) {
					return LocalizationProvider.localize(emptyCaption).orElse("");
				} else {
					return "";
				}
			}
			if (value && trueCaption != null) {
				return LocalizationProvider.localize(trueCaption).orElse(String.valueOf(value));
			}
			if (!value && falseCaption != null) {
				return LocalizationProvider.localize(falseCaption).orElse(String.valueOf(value));
			}
			return LocalizationContext.getCurrent().filter(lc -> lc.isLocalized())
					.flatMap(lc -> lc.getDefaultBooleanLocalization(value))
					.flatMap(l -> LocalizationProvider.localize(l)).orElse(String.valueOf(value));
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.vaadin.flow.components.builders.ItemSetConfigurator.ItemCaptionGenerator#getItemCaption(
		 * java.lang.Object)
		 */
		@Override
		public String getItemCaption(Boolean item) {
			return apply(item);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.InputFilterBuilder#build()
	 */
	@Override
	public InputFilter<Boolean> build() {
		inputBuilder.itemLabelGenerator(new SelectItemCaptionGenerator(emptyCaption, trueCaption, falseCaption));
		inputBuilder.itemCaptionGenerator(new SelectItemCaptionGenerator(
				(emptyCaption != null) ? emptyCaption : DEFAULT_EMPTY_VALUE_CAPTION, trueCaption, falseCaption));
		inputBuilder.emptySelectionCaption((emptyCaption != null) ? emptyCaption : DEFAULT_EMPTY_VALUE_CAPTION);
		return InputFilter.from(inputBuilder.build(), value -> {
			if (value != null) {
				return QueryFilter.eq(property, value);
			}
			return null;
		});
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.BooleanInputFilterBuilder#emptyValueCaption(com.
	 * holonplatform.core.i18n.Localizable)
	 */
	@Override
	public BooleanInputFilterBuilder emptyValueCaption(Localizable caption) {
		this.emptyCaption = caption;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.BooleanInputFilterBuilder#trueValueCaption(com.
	 * holonplatform.core.i18n.Localizable)
	 */
	@Override
	public BooleanInputFilterBuilder trueValueCaption(Localizable caption) {
		this.trueCaption = caption;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.BooleanInputFilterBuilder#falseValueCaption(com.
	 * holonplatform.core.i18n.Localizable)
	 */
	@Override
	public BooleanInputFilterBuilder falseValueCaption(Localizable caption) {
		this.falseCaption = caption;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.InputConfigurator#readOnly(boolean)
	 */
	@Override
	public BooleanInputFilterBuilder readOnly(boolean readOnly) {
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
	public BooleanInputFilterBuilder withValueChangeListener(
			ValueChangeListener<Boolean, ValueChangeEvent<Boolean>> listener) {
		inputBuilder.withValueChangeListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.InputConfigurator#required(boolean)
	 */
	@Override
	public BooleanInputFilterBuilder required(boolean required) {
		inputBuilder.required(required);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#id(java.lang.String)
	 */
	@Override
	public BooleanInputFilterBuilder id(String id) {
		inputBuilder.id(id);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#visible(boolean)
	 */
	@Override
	public BooleanInputFilterBuilder visible(boolean visible) {
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
	public BooleanInputFilterBuilder elementConfiguration(Consumer<Element> element) {
		inputBuilder.elementConfiguration(element);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withAttachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public BooleanInputFilterBuilder withAttachListener(ComponentEventListener<AttachEvent> listener) {
		inputBuilder.withAttachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withDetachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public BooleanInputFilterBuilder withDetachListener(ComponentEventListener<DetachEvent> listener) {
		inputBuilder.withDetachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withThemeName(java.lang.String)
	 */
	@Override
	public BooleanInputFilterBuilder withThemeName(String themeName) {
		inputBuilder.withThemeName(themeName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener)
	 */
	@Override
	public BooleanInputFilterBuilder withEventListener(String eventType, DomEventListener listener) {
		inputBuilder.withEventListener(eventType, listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener, java.lang.String)
	 */
	@Override
	public BooleanInputFilterBuilder withEventListener(String eventType, DomEventListener listener, String filter) {
		inputBuilder.withEventListener(eventType, listener, filter);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#width(java.lang.String)
	 */
	@Override
	public BooleanInputFilterBuilder width(String width) {
		inputBuilder.width(width);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#height(java.lang.String)
	 */
	@Override
	public BooleanInputFilterBuilder height(String height) {
		inputBuilder.height(height);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minWidth(java.lang.String)
	 */
	@Override
	public BooleanInputFilterBuilder minWidth(String minWidth) {
		inputBuilder.minWidth(minWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxWidth(java.lang.String)
	 */
	@Override
	public BooleanInputFilterBuilder maxWidth(String maxWidth) {
		inputBuilder.maxWidth(maxWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minHeight(java.lang.String)
	 */
	@Override
	public BooleanInputFilterBuilder minHeight(String minHeight) {
		inputBuilder.minHeight(minHeight);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxHeight(java.lang.String)
	 */
	@Override
	public BooleanInputFilterBuilder maxHeight(String maxHeight) {
		inputBuilder.maxHeight(maxHeight);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleNames(java.lang.String[])
	 */
	@Override
	public BooleanInputFilterBuilder styleNames(String... styleNames) {
		inputBuilder.styleNames(styleNames);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleName(java.lang.String)
	 */
	@Override
	public BooleanInputFilterBuilder styleName(String styleName) {
		inputBuilder.styleName(styleName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasEnabledConfigurator#enabled(boolean)
	 */
	@Override
	public BooleanInputFilterBuilder enabled(boolean enabled) {
		inputBuilder.enabled(enabled);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasAutofocusConfigurator#autofocus(boolean)
	 */
	@Override
	public BooleanInputFilterBuilder autofocus(boolean autofocus) {
		inputBuilder.autofocus(autofocus);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#tabIndex(int)
	 */
	@Override
	public BooleanInputFilterBuilder tabIndex(int tabIndex) {
		inputBuilder.tabIndex(tabIndex);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#withFocusListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public BooleanInputFilterBuilder withFocusListener(ComponentEventListener<FocusEvent<Component>> listener) {
		inputBuilder.withFocusListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#withBlurListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public BooleanInputFilterBuilder withBlurListener(ComponentEventListener<BlurEvent<Component>> listener) {
		inputBuilder.withBlurListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator#withFocusShortcut(com.vaadin.flow.
	 * component.Key)
	 */
	@Override
	public ShortcutConfigurator<BooleanInputFilterBuilder> withFocusShortcut(Key key) {
		return new DelegatedShortcutConfigurator<>(inputBuilder.withFocusShortcut(key), this);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasLabelConfigurator#label(com.holonplatform.core.i18n.
	 * Localizable)
	 */
	@Override
	public BooleanInputFilterBuilder label(Localizable label) {
		inputBuilder.label(label);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.DeferrableLocalizationConfigurator#withDeferredLocalization(
	 * boolean)
	 */
	@Override
	public BooleanInputFilterBuilder withDeferredLocalization(boolean deferredLocalization) {
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

}
