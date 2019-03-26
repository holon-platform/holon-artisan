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
package com.holonplatform.artisan.vaadin.flow.components;

import com.holonplatform.artisan.vaadin.flow.components.builders.InputFilterComponentBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.DefaultInputFilterComponent;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.vaadin.flow.components.Composable;
import com.holonplatform.vaadin.flow.components.builders.FormLayoutBuilder;
import com.holonplatform.vaadin.flow.components.builders.HorizontalLayoutBuilder;
import com.holonplatform.vaadin.flow.components.builders.VerticalLayoutBuilder;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * A {@link InputFilterGroup} which provides an UI component to display the input filter elements, which can be composed
 * using a {@link Composer}.
 *
 * @since 1.0.0
 */
public interface InputFilterComponent extends InputFilterGroup, Composable {

	/**
	 * Get a builder to create a {@link InputFilterComponent} using given property set.
	 * @param <C> Content element type
	 * @param <P> Property type
	 * @param content The component content, where the {@link InputFilter}s will be composed using the configured
	 *        {@link Composer} (not null)
	 * @param properties The property set (not null)
	 * @return A new {@link InputFilterComponentBuilder}
	 */
	@SuppressWarnings("rawtypes")
	static <C extends Component, P extends Property> InputFilterComponentBuilder<C> builder(C content,
			Iterable<P> properties) {
		return new DefaultInputFilterComponent.DefaultBuilder<>(content, properties);
	}

	/**
	 * Get a builder to create a {@link InputFilterComponent} using given property set.
	 * @param <C> Content element type
	 * @param content The component content, where the {@link InputFilter}s will be composed using the configured
	 *        {@link Composer} (not null)
	 * @param properties The property set (not null)
	 * @return A new {@link InputFilterComponentBuilder}
	 */
	static <C extends Component> InputFilterComponentBuilder<C> builder(C content, Property<?>... properties) {
		return builder(content, PropertySet.of(properties));
	}

	/**
	 * Get a builder to create a {@link InputFilterComponent} using given property set and a {@link FormLayout} as
	 * content layout.
	 * <p>
	 * A default composer is configured using {@link Composable#componentContainerComposer()}. Use
	 * {@link InputFilterComponentBuilder#composer(com.holonplatform.vaadin.flow.components.Composable.Composer)} to
	 * provide a custom components composer.
	 * </p>
	 * @param <P> Property type
	 * @param properties The property set (not null)
	 * @return A {@link InputFilterComponent} builder
	 */
	@SuppressWarnings("rawtypes")
	static <P extends Property> InputFilterComponentBuilder<FormLayout> formLayout(Iterable<P> properties) {
		return builder(FormLayoutBuilder.create().build(), properties)
				.composer(Composable.componentContainerComposer());
	}

	/**
	 * Get a builder to create a {@link InputFilterComponent} using given property set and a {@link FormLayout} as
	 * content layout.
	 * <p>
	 * A default composer is configured using {@link Composable#componentContainerComposer()}. Use
	 * {@link InputFilterComponentBuilder#composer(com.holonplatform.vaadin.flow.components.Composable.Composer)} to
	 * provide a custom components composer.
	 * </p>
	 * @param properties The property set (not null)
	 * @return A {@link InputFilterComponent} builder
	 */
	static InputFilterComponentBuilder<FormLayout> formLayout(Property<?>... properties) {
		return builder(FormLayoutBuilder.create().build(), properties)
				.composer(Composable.componentContainerComposer());
	}

	/**
	 * Get a builder to create a {@link InputFilterComponent} using given property set and a {@link VerticalLayout} as
	 * content layout.
	 * <p>
	 * A default composer is configured using {@link Composable#componentContainerComposer()}. Use
	 * {@link InputFilterComponentBuilder#composer(com.holonplatform.vaadin.flow.components.Composable.Composer)} to
	 * provide a custom components composer.
	 * </p>
	 * @param <P> Property type
	 * @param properties The property set (not null)
	 * @return A {@link InputFilterComponent} builder
	 */
	@SuppressWarnings("rawtypes")
	static <P extends Property> InputFilterComponentBuilder<VerticalLayout> verticalLayout(Iterable<P> properties) {
		return builder(VerticalLayoutBuilder.create().build(), properties)
				.composer(Composable.componentContainerComposer());
	}

	/**
	 * Get a builder to create a {@link InputFilterComponent} using given property set and a {@link VerticalLayout} as
	 * content layout.
	 * <p>
	 * A default composer is configured using {@link Composable#componentContainerComposer()}. Use
	 * {@link InputFilterComponentBuilder#composer(com.holonplatform.vaadin.flow.components.Composable.Composer)} to
	 * provide a custom components composer.
	 * </p>
	 * @param properties The property set (not null)
	 * @return A {@link InputFilterComponent} builder
	 */
	static InputFilterComponentBuilder<VerticalLayout> verticalLayout(Property<?>... properties) {
		return builder(VerticalLayoutBuilder.create().build(), properties)
				.composer(Composable.componentContainerComposer());
	}

	/**
	 * Get a builder to create a {@link InputFilterComponent} using given property set and a {@link HorizontalLayout} as
	 * content layout.
	 * <p>
	 * A default composer is configured using {@link Composable#componentContainerComposer()}. Use
	 * {@link InputFilterComponentBuilder#composer(com.holonplatform.vaadin.flow.components.Composable.Composer)} to
	 * provide a custom components composer.
	 * </p>
	 * @param <P> Property type
	 * @param properties The property set (not null)
	 * @return A {@link InputFilterComponent} builder
	 */
	@SuppressWarnings("rawtypes")
	static <P extends Property> InputFilterComponentBuilder<HorizontalLayout> horizontalLayout(Iterable<P> properties) {
		return builder(HorizontalLayoutBuilder.create().build(), properties)
				.composer(Composable.componentContainerComposer());
	}

	/**
	 * Get a builder to create a {@link InputFilterComponent} using given property set and a {@link HorizontalLayout} as
	 * content layout.
	 * <p>
	 * A default composer is configured using {@link Composable#componentContainerComposer()}. Use
	 * {@link InputFilterComponentBuilder#composer(com.holonplatform.vaadin.flow.components.Composable.Composer)} to
	 * provide a custom components composer.
	 * </p>
	 * @param properties The property set (not null)
	 * @return A {@link InputFilterComponent} builder
	 */
	static InputFilterComponentBuilder<HorizontalLayout> horizontalLayout(Property<?>... properties) {
		return builder(HorizontalLayoutBuilder.create().build(), properties)
				.composer(Composable.componentContainerComposer());
	}

}
