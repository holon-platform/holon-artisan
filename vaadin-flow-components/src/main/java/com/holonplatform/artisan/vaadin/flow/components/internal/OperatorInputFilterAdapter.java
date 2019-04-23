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
package com.holonplatform.artisan.vaadin.flow.components.internal;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.holonplatform.artisan.core.utils.Obj;
import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.artisan.vaadin.flow.components.builders.OperatorInputFilterConfigurator.FilterOperatorChangeEvent;
import com.holonplatform.artisan.vaadin.flow.components.builders.OperatorInputFilterConfigurator.FilterOperatorChangeListener;
import com.holonplatform.artisan.vaadin.flow.components.builders.OperatorInputFilterConfigurator.FilterOperatorSelectConfigurator;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultFilterOperatorSelectConfigurator;
import com.holonplatform.artisan.vaadin.flow.components.templates.HolonInputFilter;
import com.holonplatform.core.Registration;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.vaadin.flow.components.HasLabel;
import com.holonplatform.vaadin.flow.components.HasPlaceholder;
import com.holonplatform.vaadin.flow.components.HasTitle;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.events.InvalidChangeEventNotifier;
import com.holonplatform.vaadin.flow.components.support.InputAdaptersContainer;
import com.holonplatform.vaadin.flow.i18n.LocalizationProvider;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.value.HasValueChangeMode;

/**
 * {@link InputFilter} with operator support adapter.
 * 
 * @param <T> Value type
 *
 * @since 1.0.0
 */
@HtmlImport("frontend://com-holonplatform-operator-input-filter-styles.html")
public class OperatorInputFilterAdapter<T> extends CustomField<T> implements InputFilter<T>, HasStyle, HasTheme {

	private static final long serialVersionUID = 3088317891629157757L;

	private final Property<? super T> property;
	private final FilterOperatorSelect operatorSelect;

	private Input<T> input;

	private Supplier<Boolean> ignoreCaseSupplier = () -> false;

	private final List<FilterOperatorChangeListener<T>> filterOperatorChangeListeners = new LinkedList<>();

	private final InputAdaptersContainer<T> adapters = InputAdaptersContainer.create();

	public OperatorInputFilterAdapter(Property<? super T> property, InputFilterOperator... operators) {
		super();
		Obj.argumentNotNull(property, "Property must be not null");
		this.property = property;
		// config
		getElement().setAttribute("operator-input-filter", "");
		// operator select
		this.operatorSelect = new FilterOperatorSelect(operators);
		this.operatorSelect.getElement().setAttribute("operator-input-filter-select", "");
		this.operatorSelect.addValueChangeListener(e -> {
			// check operator
			getInput().ifPresent(i -> i.setReadOnly((e.getValue() != null
					&& (e.getValue() == InputFilterOperator.EMPTY || e.getValue() == InputFilterOperator.NOT_EMPTY))));
			// fire listeners
			getInput().ifPresent(i -> {
				final FilterOperatorChangeEvent<T> event = new DefaultFilterOperatorChangeEvent<>(e.isFromClient(),
						this, i, e.getOldValue(), e.getValue());
				filterOperatorChangeListeners.forEach(l -> l.filterOperatorChanged(event));
			});
		});
	}

	/**
	 * Build the component.
	 * @param input The input component to use (not null)
	 */
	public void build(Input<T> input) {
		Obj.argumentNotNull(input, "Input must be not null");
		this.input = input;
		// ensure no children
		getChildren().forEach(c -> remove(c));
		// add components
		final HolonInputFilter filter = new HolonInputFilter();
		filter.setOperatorComponent(getOperatorSelect());
		filter.setInputComponent(input.getComponent());
		filter.getElement().getStyle().set("flex-grow", "1");
		add(filter);
	}

	/**
	 * Get the query filter property.
	 * @return the property
	 */
	protected Property<? super T> getProperty() {
		return property;
	}

	/**
	 * Get the {@link Input} reference.
	 * @return Optional input reference
	 */
	protected Optional<Input<T>> getInput() {
		return Optional.ofNullable(input);
	}

	/**
	 * Get the filter operator select.
	 * @return the filter operator select
	 */
	protected FilterOperatorSelect getOperatorSelect() {
		return operatorSelect;
	}

	@Override
	public Optional<QueryFilter> getFilter() {
		return Optional.ofNullable(
				InputFilterOperator.getQueryFilter(getInput().map(i -> i.getValue()).orElseGet(() -> getValue()),
						getProperty(), getFilterOperator().orElse(null), isIgnoreCase()));
	}

	/**
	 * Get the filter ignore case mode.
	 * @return the filter ignore case mode
	 */
	protected boolean isIgnoreCase() {
		final Boolean ignoreCase = ignoreCaseSupplier.get();
		return (ignoreCase != null) ? ignoreCase : false;
	}

	/**
	 * Get the current filter operator, if available.
	 * @return Optional filter operator
	 */
	protected Optional<InputFilterOperator> getFilterOperator() {
		if (getOperatorSelect().isVisible()) {
			return Optional.ofNullable(getOperatorSelect().getValue());
		}
		return getOperatorSelect().getDefaultOperator();
	}

	@Override
	public void reset() {
		InputFilter.super.reset();
		// reset filter operator
		if (getOperatorSelect().isVisible()) {
			getOperatorSelect().clear();
		}
	}

	@Override
	public Component getComponent() {
		return this;
	}

	/**
	 * Get a {@link FilterOperatorSelectConfigurator} to configure the filter operator select.
	 * @return A new {@link FilterOperatorSelectConfigurator}
	 */
	public FilterOperatorSelectConfigurator getFilterOperatorSelectConfigurator() {
		return new DefaultFilterOperatorSelectConfigurator(getOperatorSelect());
	}

	/**
	 * A filter operator change listener.
	 * @param listener The listener to add (not null)
	 */
	public void addFilterOperatorChangeListener(FilterOperatorChangeListener<T> listener) {
		Obj.argumentNotNull(listener, "FilterOperatorChangeListener must be not null");
		this.filterOperatorChangeListeners.add(listener);
	}

	/**
	 * Get the supplier for the filter ignore case mode.
	 * @return the ignore case supplier
	 */
	public Supplier<Boolean> getIgnoreCaseSupplier() {
		return ignoreCaseSupplier;
	}

	/**
	 * Set the supplier for the filter ignore case mode.
	 * @param ignoreCaseSupplier the ignore case supplier to set (not null)
	 */
	public void setIgnoreCaseSupplier(Supplier<Boolean> ignoreCaseSupplier) {
		Obj.argumentNotNull(ignoreCaseSupplier, "Ignore case supplier must be not null");
		this.ignoreCaseSupplier = ignoreCaseSupplier;
	}

	/**
	 * Get the default operator, if available.
	 * @return Optional default operator
	 */
	public Optional<InputFilterOperator> getDefaultOperator() {
		return getOperatorSelect().getDefaultOperator();
	}

	/**
	 * Set the default operator.
	 * @param defaultOperator the default operator to set
	 */
	public void setDefaultOperator(InputFilterOperator defaultOperator) {
		getOperatorSelect().setDefaultOperator(defaultOperator);
	}

	/**
	 * Get whether the filter operator select is visible.
	 * @return Whether the filter operator select is visible
	 */
	public boolean isOperatorSelectVisible() {
		return getOperatorSelect().isVisible();
	}

	/**
	 * Set whether the filter operator select is visible.
	 * @param visible whether the filter operator select is visible
	 */
	public void setOperatorSelectVisible(boolean visible) {
		getOperatorSelect().setVisible(visible);
	}

	@Override
	public boolean isRequired() {
		return isRequiredIndicatorVisible();
	}

	@Override
	public void setRequired(boolean required) {
		setRequiredIndicatorVisible(required);
	}

	@Override
	public void focus() {
		getInput().ifPresent(i -> i.focus());
	}

	@Override
	public Registration addValueChangeListener(
			com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener<T, com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent<T>> listener) {
		Obj.argumentNotNull(listener, "ValueChangeListener must be not null");
		final com.vaadin.flow.shared.Registration r = super.addValueChangeListener(
				e -> listener.valueChange(com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent
						.create(this, e.getOldValue(), e.getValue(), e.isFromClient())));
		return () -> r.remove();
	}

	@Override
	protected T generateModelValue() {
		return getInput().map(i -> i.getValue()).orElse(null);
	}

	@Override
	protected void setPresentationValue(T newPresentationValue) {
		getInput().ifPresent(i -> i.setValue(newPresentationValue));
	}

	@Override
	public <A> Optional<A> as(Class<A> type) {
		Obj.argumentNotNull(type, "Type must be not null");
		final Optional<A> adapter = adapters.getAs(this, type);
		if (adapter.isPresent()) {
			return adapter;
		}
		return getInput().flatMap(i -> i.as(type));
	}

	/**
	 * Set the adapter for given type.
	 * @param <A> Adapter type
	 * @param type Adapter type (not null)
	 * @param adapter Adapter function
	 */
	public <A> void setAdapter(Class<A> type, Function<Input<T>, A> adapter) {
		adapters.setAdapter(type, adapter);
	}

	@Override
	public Optional<HasLabel> hasLabel() {
		return Optional.of(HasLabel.create(() -> getLabel(), l -> setLabel(l)));
	}

	@Override
	public Optional<HasValidation> hasValidation() {
		return Optional.of(this);
	}

	@Override
	public Optional<HasPlaceholder> hasPlaceholder() {
		return getInput().flatMap(i -> i.hasPlaceholder());
	}

	@Override
	public Optional<HasValueChangeMode> hasValueChangeMode() {
		return getInput().flatMap(i -> i.hasValueChangeMode());
	}

	@Override
	public Optional<HasTitle> hasTitle() {
		return getInput().flatMap(i -> i.hasTitle());
	}

	@Override
	public Optional<InvalidChangeEventNotifier> hasInvalidChangeEventNotifier() {
		return getInput().flatMap(i -> i.hasInvalidChangeEventNotifier());
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
	}

	@Override
	public boolean isReadOnly() {
		return super.isReadOnly();
	}

	@Override
	public void clear() {
		InputFilter.super.clear();
	}

	private static class DefaultFilterOperatorChangeEvent<T> implements FilterOperatorChangeEvent<T> {

		private static final long serialVersionUID = 8212171204337291572L;

		private final boolean fromClient;
		private final InputFilter<T> source;
		private final Input<T> input;
		private final InputFilterOperator oldValue;
		private final InputFilterOperator value;

		public DefaultFilterOperatorChangeEvent(boolean fromClient, InputFilter<T> source, Input<T> input,
				InputFilterOperator oldValue, InputFilterOperator value) {
			super();
			this.fromClient = fromClient;
			this.source = source;
			this.input = input;
			this.oldValue = oldValue;
			this.value = value;
		}

		@Override
		public boolean isFromClient() {
			return fromClient;
		}

		@Override
		public InputFilter<T> getSource() {
			return source;
		}

		@Override
		public Input<T> getInput() {
			return input;
		}

		@Override
		public InputFilterOperator getOldValue() {
			return oldValue;
		}

		@Override
		public InputFilterOperator getValue() {
			return value;
		}

	}

	class FilterOperatorSelect extends Select<InputFilterOperator> implements HasTheme {

		private static final long serialVersionUID = -8923120517061581962L;

		private final List<InputFilterOperator> operators;

		private InputFilterOperator defaultOperator;

		public FilterOperatorSelect(InputFilterOperator... operators) {
			super(operators);
			this.operators = Arrays.asList(operators);
			this.defaultOperator = this.operators.isEmpty() ? null : this.operators.get(0);

			// configuration
			setEmptySelectionAllowed(false);

			// default width
			setWidth("4.3em");

			// labels
			setItemLabelGenerator(operator -> operator.getSymbol());

			// captions
			setTextRenderer(
					operator -> LocalizationProvider.localize(operator.getCaption()).orElse(operator.getSymbol()));

			// init
			if (defaultOperator != null) {
				setValue(defaultOperator);
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.vaadin.flow.component.HasValue#clear()
		 */
		@Override
		public void clear() {
			setValue(getDefaultOperator().orElse(getEmptyValue()));
		}

		/**
		 * Get the default operator, if available.
		 * @return Optional default operator
		 */
		public Optional<InputFilterOperator> getDefaultOperator() {
			return Optional.ofNullable(defaultOperator);
		}

		/**
		 * Set the default operator.
		 * @param defaultOperator the default operator to set
		 */
		public void setDefaultOperator(InputFilterOperator defaultOperator) {
			if (defaultOperator == null || !operators.contains(defaultOperator)) {
				if (!operators.isEmpty()) {
					this.defaultOperator = operators.get(0);
				}
			} else {
				this.defaultOperator = defaultOperator;
			}
		}

	}

}
