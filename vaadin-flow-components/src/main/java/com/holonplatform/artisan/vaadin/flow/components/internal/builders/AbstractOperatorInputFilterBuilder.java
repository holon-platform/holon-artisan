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
import com.holonplatform.vaadin.flow.components.builders.HasLabelConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.AbstractLocalizableComponentConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.DefaultHasLabelConfigurator;
import com.vaadin.flow.component.HasEnabled;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;

/**
 * Base {@link OperatorInputFilterConfigurator} implementation.
 *
 * @param <T> Value type
 * @param <B> Concrete builder type
 * 
 * @since 1.0.0
 */
public abstract class AbstractOperatorInputFilterBuilder<T, B extends OperatorInputFilterBuilder<T, B>>
		extends AbstractLocalizableComponentConfigurator<OperatorInputFilterAdapter<T>, B>
		implements OperatorInputFilterBuilder<T, B> {

	private final HasLabelConfigurator<?> labelConfigurator;

	private Consumer<FilterOperatorSelectConfigurator> filterOperatorSelectConfiguration;

	public AbstractOperatorInputFilterBuilder(Property<? super T> property, InputFilterOperator... operators) {
		super(new OperatorInputFilterAdapter<>(property, operators));
		this.labelConfigurator = new DefaultHasLabelConfigurator<>(getComponent(), l -> getComponent().setLabel(l),
				this);
	}

	@Override
	protected Optional<HasSize> hasSize() {
		return Optional.of(getComponent());
	}

	@Override
	protected Optional<HasStyle> hasStyle() {
		return Optional.of(getComponent());
	}

	@Override
	protected Optional<HasEnabled> hasEnabled() {
		return Optional.of(getComponent());
	}

	/**
	 * Build the {@link Input} instance.
	 * @return The {@link Input} instance
	 */
	protected abstract Input<T> buildInput();

	/**
	 * Get the ignore case mode supplier.
	 * @return the ignore case mode supplie
	 */
	protected Supplier<Boolean> getIgnoreCaseSupplier() {
		return () -> false;
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
		final Input<T> input = buildInput();
		getComponent().build(input);
		getComponent().setIgnoreCaseSupplier(getIgnoreCaseSupplier());
		return getComponent();
	}

}
