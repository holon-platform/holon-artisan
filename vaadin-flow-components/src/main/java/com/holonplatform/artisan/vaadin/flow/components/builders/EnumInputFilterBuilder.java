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

import java.util.Set;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.InputFilter.EnumFilterMode;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultEnumMultiOptionInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultEnumSingleOptionInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultEnumSingleSelectInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultGenericEnumInputFilterBuilder;
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

	/**
	 * Generic Enumeration type {@link InputFilter} builder.
	 * 
	 * @param <T> Enumeration type
	 *
	 * @since 1.0.0
	 */
	public interface GenericEnumInputFilterBuilder<T extends Enum<T>>
			extends EnumInputFilterBuilder<T, T, GenericEnumInputFilterBuilder<T>> {

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
	 * Get a generic builder to create an enumeration type {@link InputFilter}, using given property and given rendering
	 * mode.
	 * <p>
	 * For specific builders according to rendering mode, use:
	 * <ul>
	 * <li>{@link #singleSelect(Property)}</li>
	 * <li>{@link #singleOption(Property)}</li>
	 * <li>{@link #multiOption(Property)}</li>
	 * </ul>
	 * @param <T> Enumeration type
	 * @param property The property to use as filter expression (not null)
	 * @param mode The rendering mode (not null)
	 * @return A new {@link GenericEnumInputFilterBuilder}
	 */
	static <T extends Enum<T>> GenericEnumInputFilterBuilder<T> create(Property<T> property, EnumFilterMode mode) {
		return new DefaultGenericEnumInputFilterBuilder<>(property, mode);
	}

	/**
	 * Get a generic builder to create an enumeration type {@link InputFilter}, using given property and the rendering
	 * mode specified in property configuration using {@link InputFilter#PROPERTY_ENUM_FILTER_MODE}. If the
	 * configuration value is not available, the {@link EnumFilterMode#SINGLE_SELECT} is used by default.
	 * <p>
	 * For specific builders according to rendering mode, use:
	 * <ul>
	 * <li>{@link #singleSelect(Property)}</li>
	 * <li>{@link #singleOption(Property)}</li>
	 * <li>{@link #multiOption(Property)}</li>
	 * </ul>
	 * @param <T> Enumeration type
	 * @param property The property to use as filter expression (not null)
	 * @return A new {@link GenericEnumInputFilterBuilder}
	 */
	static <T extends Enum<T>> GenericEnumInputFilterBuilder<T> create(Property<T> property) {
		return create(property, property.getConfiguration().getParameter(InputFilter.PROPERTY_ENUM_FILTER_MODE,
				EnumFilterMode.SINGLE_SELECT));
	}

}
