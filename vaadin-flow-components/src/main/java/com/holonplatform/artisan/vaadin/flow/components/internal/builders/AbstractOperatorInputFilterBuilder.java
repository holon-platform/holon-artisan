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

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.artisan.vaadin.flow.components.builders.OperatorInputFilterBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.OperatorInputFilterConfigurator;
import com.holonplatform.artisan.vaadin.flow.components.internal.OperatorInputFilterAdapter;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.Property;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasEnabledConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasLabelConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.Element;

/**
 * Base {@link OperatorInputFilterConfigurator} implementation.
 *
 * @param <T> Value type
 * @param <B> Concrete builder type
 * 
 * @since 1.0.0
 */
public abstract class AbstractOperatorInputFilterBuilder<T, B extends OperatorInputFilterBuilder<T, B>>
		implements OperatorInputFilterBuilder<T, B> {

	private final OperatorInputFilterAdapter<T> component;

	private final BaseComponentConfigurator componentConfigurator;
	private final BaseHasEnabledConfigurator enabledConfigurator;
	private final BaseHasSizeConfigurator sizeConfigurator;
	private final BaseHasStyleConfigurator styleConfigurator;
	private final BaseHasLabelConfigurator labelConfigurator;

	private boolean deferredLocalizationEnabled;

	private Consumer<FilterOperatorSelectConfigurator> filterOperatorSelectConfiguration;

	public AbstractOperatorInputFilterBuilder(Property<? super T> property, InputFilterOperator... operators) {
		super();
		this.component = new OperatorInputFilterAdapter<>(property, operators);
		this.componentConfigurator = ComponentConfigurator.create(component);
		this.enabledConfigurator = HasEnabledConfigurator.create(component);
		this.sizeConfigurator = HasSizeConfigurator.create(component);
		this.styleConfigurator = HasStyleConfigurator.create(component);
		this.labelConfigurator = HasLabelConfigurator.create(component, l -> component.setLabel(l), this);
	}

	/**
	 * Get the operator filter component.
	 * @return The component
	 */
	protected OperatorInputFilterAdapter<T> getComponent() {
		return component;
	}

	/**
	 * Get the actual configurator.
	 * @return the actual configurator
	 */
	protected abstract B getConfigurator();

	/**
	 * Build the {@link Input} instance.
	 * @return The {@link Input} instance
	 */
	protected abstract Input<T> buildInput();

	/**
	 * Build the additional {@link Input} instance, if supported.
	 * @return Optional additional {@link Input} instance
	 */
	protected abstract Optional<Input<T>> buildAdditionalInput();

	/**
	 * Get the ignore case mode supplier.
	 * @return the ignore case mode supplie
	 */
	protected Supplier<Boolean> getIgnoreCaseSupplier() {
		return () -> false;
	}

	@Override
	public boolean isDeferredLocalizationEnabled() {
		return deferredLocalizationEnabled;
	}

	@Override
	public B withDeferredLocalization(boolean deferredLocalization) {
		this.deferredLocalizationEnabled = deferredLocalization;
		return getConfigurator();
	}

	@Override
	public B styleNames(String... styleNames) {
		styleConfigurator.styleNames(styleNames);
		return getConfigurator();
	}

	@Override
	public B styleName(String styleName) {
		styleConfigurator.styleName(styleName);
		return getConfigurator();
	}

	@Override
	public B id(String id) {
		componentConfigurator.id(id);
		return getConfigurator();
	}

	@Override
	public B visible(boolean visible) {
		componentConfigurator.visible(visible);
		return getConfigurator();
	}

	@Override
	public B elementConfiguration(Consumer<Element> element) {
		componentConfigurator.elementConfiguration(element);
		return getConfigurator();
	}

	@Override
	public B withAttachListener(ComponentEventListener<AttachEvent> listener) {
		componentConfigurator.withAttachListener(listener);
		return getConfigurator();
	}

	@Override
	public B withDetachListener(ComponentEventListener<DetachEvent> listener) {
		componentConfigurator.withDetachListener(listener);
		return getConfigurator();
	}

	@Override
	public B enabled(boolean enabled) {
		enabledConfigurator.enabled(enabled);
		return getConfigurator();
	}

	@Override
	public B width(String width) {
		sizeConfigurator.width(width);
		return getConfigurator();
	}

	@Override
	public B height(String height) {
		sizeConfigurator.height(height);
		return getConfigurator();
	}

	@Override
	public B minWidth(String minWidth) {
		sizeConfigurator.minWidth(minWidth);
		return getConfigurator();
	}

	@Override
	public B maxWidth(String maxWidth) {
		sizeConfigurator.maxWidth(maxWidth);
		return getConfigurator();
	}

	@Override
	public B minHeight(String minHeight) {
		sizeConfigurator.minHeight(minHeight);
		return getConfigurator();
	}

	@Override
	public B maxHeight(String maxHeight) {
		sizeConfigurator.maxHeight(maxHeight);
		return getConfigurator();
	}

	@Override
	public B withThemeName(String themeName) {
		componentConfigurator.withThemeName(themeName);
		return getConfigurator();
	}

	@Override
	public B withEventListener(String eventType, DomEventListener listener) {
		componentConfigurator.withEventListener(eventType, listener);
		return getConfigurator();
	}

	@Override
	public B withEventListener(String eventType, DomEventListener listener, String filter) {
		componentConfigurator.withEventListener(eventType, listener, filter);
		return getConfigurator();
	}

	@Override
	public B filterOperatorSelectConfiguration(Consumer<FilterOperatorSelectConfigurator> configuration) {
		this.filterOperatorSelectConfiguration = configuration;
		return getConfigurator();
	}

	@Override
	public B operatorSelectionAllowed(boolean operatorSelectionAllowed) {
		getComponent().setVisible(operatorSelectionAllowed);
		return getConfigurator();
	}

	@Override
	public B defaultOperator(InputFilterOperator operator) {
		getComponent().setDefaultOperator(operator);
		return getConfigurator();
	}

	@Override
	public B withFilterOperatorChangeListener(FilterOperatorChangeListener<T> listener) {
		getComponent().addFilterOperatorChangeListener(listener);
		return getConfigurator();
	}

	@Override
	public B readOnly(boolean readOnly) {
		getComponent().setReadOnly(readOnly);
		return getConfigurator();
	}

	@Override
	public B required(boolean required) {
		getComponent().setRequired(required);
		return getConfigurator();
	}

	@Override
	public B required() {
		return required(true);
	}

	@Override
	public B label(Localizable label) {
		labelConfigurator.label(label);
		return getConfigurator();
	}

	@Override
	public InputFilter<T> build() {
		if (filterOperatorSelectConfiguration != null) {
			filterOperatorSelectConfiguration.accept(getComponent().getFilterOperatorSelectConfigurator());
		}
		getComponent().build(buildInput(), buildAdditionalInput().orElse(null));
		getComponent().setIgnoreCaseSupplier(getIgnoreCaseSupplier());
		return getComponent();
	}

}
