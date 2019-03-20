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
package com.holonplatform.artisan.vaadin.flow.components.builders;

import java.util.Set;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultEnumMultiOptionInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultEnumSingleOptionInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultEnumSingleSelectInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.utils.ComponentUtils;
import com.holonplatform.core.config.ConfigProperty;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.builders.DeferrableLocalizationConfigurator;
import com.holonplatform.vaadin.flow.components.builders.FocusableConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasAutofocusConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasEnabledConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasItemCaptionConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasLabelConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasPlaceholderConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator;
import com.holonplatform.vaadin.flow.components.builders.ItemSetConfigurator;
import com.holonplatform.vaadin.flow.internal.utils.CollectionUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;

/**
 * Enumeration type {@link InputFilter} builder.
 * 
 * @param <T> Enumeration type
 * @param <V> Input value type
 * @param <B> Concrete builder type
 *
 * @since 1.0.0
 */
public interface EnumInputFilterBuilder<T extends Enum<T>, V, B extends EnumInputFilterBuilder<T, V, B>>
		extends InputFilterBuilder<V, ValueChangeEvent<V>, B>, ItemSetConfigurator<B>, HasItemCaptionConfigurator<T, B>,
		HasSizeConfigurator<B>, HasStyleConfigurator<B>, HasLabelConfigurator<B>, HasEnabledConfigurator<B>,
		DeferrableLocalizationConfigurator<B> {

	/**
	 * Set the items data provider using a {@link ListDataProvider}.
	 * @param dataProvider The data provider to set
	 * @return this
	 */
	B dataSource(ListDataProvider<T> dataProvider);

	/**
	 * Set the items which acts as data source.
	 * @param items The items to set
	 * @return this
	 */
	default B items(Iterable<T> items) {
		return dataSource(DataProvider.ofCollection(CollectionUtils.iterableAsSet(items)));
	}

	/**
	 * Set the items which acts as data source.
	 * @param items The items to set
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	default B items(T... items) {
		return dataSource(DataProvider.ofItems(items));
	}

	// -------

	/**
	 * Enumeration {@link InputFilter} rendering modes.
	 */
	public enum EnumFilterMode {

		/**
		 * The enumeration {@link InputFilter} is rendered using a <em>select</em>.
		 * <p>
		 * A single value can be selected.
		 * </p>
		 */
		SINGLE_SELECT,

		/**
		 * The enumeration {@link InputFilter} is rendered using a <em>radio button group</em>.
		 * <p>
		 * A single value can be selected.
		 * </p>
		 */
		SINGLE_OPTION,

		/**
		 * The enumeration {@link InputFilter} is rendered using a <em>checkbox group</em>.
		 * <p>
		 * Multiple values can be selected.
		 * </p>
		 */
		MULTI_OPTION;

	}

	/**
	 * A configuration value which can be used in {@link Property} configuration to specify the default
	 * {@link EnumFilterMode}.
	 */
	public static final ConfigProperty<EnumFilterMode> PROPERTY_ENUM_FILTER_MODE = ConfigProperty.create(
			"com.holonplatform.artisan.vaadin.flow.components.input.filter.enum.filter.mode", EnumFilterMode.class);

	/**
	 * Enumeration type {@link InputFilter} builder using a single select as input component.
	 *
	 * @param <T> Enumeration type
	 *
	 * @since 1.0.0
	 */
	public interface EnumSingleSelectInputFilterBuilder<T extends Enum<T>>
			extends EnumInputFilterBuilder<T, T, EnumSingleSelectInputFilterBuilder<T>>,
			HasPlaceholderConfigurator<EnumSingleSelectInputFilterBuilder<T>>,
			HasAutofocusConfigurator<EnumSingleSelectInputFilterBuilder<T>>,
			FocusableConfigurator<Component, EnumSingleSelectInputFilterBuilder<T>> {

	}

	/**
	 * Enumeration type {@link InputFilter} builder using a radio button group as input component.
	 *
	 * @param <T> Enumeration type
	 *
	 * @since 1.0.0
	 */
	public interface EnumSingleOptionInputFilterBuilder<T extends Enum<T>>
			extends EnumInputFilterBuilder<T, T, EnumSingleOptionInputFilterBuilder<T>> {

	}

	/**
	 * Enumeration type {@link InputFilter} builder using a checkbox group as input component.
	 *
	 * @param <T> Enumeration type
	 *
	 * @since 1.0.0
	 */
	public interface EnumMultiOptionInputFilterBuilder<T extends Enum<T>>
			extends EnumInputFilterBuilder<T, Set<T>, EnumMultiOptionInputFilterBuilder<T>> {

	}

	// ------- statics

	/**
	 * Get a new {@link EnumSingleSelectInputFilterBuilder} to create an enumeration type {@link InputFilter} using a
	 * single select as input component.
	 * @param <T> Enumeration type
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link EnumSingleSelectInputFilterBuilder}
	 */
	static <T extends Enum<T>> EnumSingleSelectInputFilterBuilder<T> singleSelect(Property<T> property) {
		return new DefaultEnumSingleSelectInputFilterBuilder<>(property);
	}

	/**
	 * Get a new {@link EnumSingleOptionInputFilterBuilder} to create an enumeration type {@link InputFilter} using a
	 * radio button group as input component.
	 * @param <T> Enumeration type
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link EnumSingleOptionInputFilterBuilder}
	 */
	static <T extends Enum<T>> EnumSingleOptionInputFilterBuilder<T> singleOption(Property<T> property) {
		return new DefaultEnumSingleOptionInputFilterBuilder<>(property);
	}

	/**
	 * Get a new {@link EnumMultiOptionInputFilterBuilder} to create an enumeration type {@link InputFilter} using a
	 * checkbox group as input component.
	 * @param <T> Enumeration type
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link EnumMultiOptionInputFilterBuilder}
	 */
	static <T extends Enum<T>> EnumMultiOptionInputFilterBuilder<T> multiOption(Property<T> property) {
		return new DefaultEnumMultiOptionInputFilterBuilder<>(property);
	}

	/**
	 * Create an enumeration type {@link InputFilter} using given property and given rendering mode.
	 * @param <T> Enumeration type
	 * @param property The property to use as filter expression (not null)
	 * @param mode The rendering mode
	 * @return A new {@link InputFilter}
	 */
	static <T extends Enum<T>> InputFilter<T> create(Property<T> property, EnumFilterMode mode) {
		ObjectUtils.argumentNotNull(mode, "Enum filter mode must be not null");
		switch (mode) {
		case MULTI_OPTION:
			return ComponentUtils.asSingleValueInputFilter(multiOption(property).build());
		case SINGLE_OPTION:
			return singleOption(property).build();
		case SINGLE_SELECT:
		default:
			return singleSelect(property).build();
		}
	}

	/**
	 * Create an enumeration type {@link InputFilter} using given property and the rendering mode specified in property
	 * configuration using {@link #PROPERTY_ENUM_FILTER_MODE}. If the configuration value is not available, the
	 * {@link EnumFilterMode#SINGLE_SELECT} is used by default.
	 * @param <T> Enumeration type
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link InputFilter}
	 */
	static <T extends Enum<T>> InputFilter<T> create(Property<T> property) {
		return create(property,
				property.getConfiguration().getParameter(PROPERTY_ENUM_FILTER_MODE, EnumFilterMode.SINGLE_SELECT));
	}

}
