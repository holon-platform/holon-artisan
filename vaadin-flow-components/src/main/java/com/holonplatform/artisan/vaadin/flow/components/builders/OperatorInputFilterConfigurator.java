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

import java.io.Serializable;
import java.util.EventListener;
import java.util.function.Consumer;

import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeEvent;
import com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator;
import com.holonplatform.vaadin.flow.components.builders.DeferrableLocalizationConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasEnabledConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasLabelConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator;
import com.holonplatform.vaadin.flow.components.builders.InputConfigurator;

/**
 * {@link InputFilter} with operator selection support configurator.
 *
 * @param <T> Value type
 * @param <E> Value change event type
 * @param <B> Concrete builder type
 * 
 * @since 1.0.0
 */
public interface OperatorInputFilterConfigurator<T, E extends ValueChangeEvent<T>, B extends OperatorInputFilterConfigurator<T, E, B>>
		extends InputConfigurator<T, E, B>, DeferrableLocalizationConfigurator<B>, HasEnabledConfigurator<B>,
		HasSizeConfigurator<B>, HasStyleConfigurator<B>, HasLabelConfigurator<B> {

	/**
	 * Configure the filter operator select using a {@link FilterOperatorSelectConfigurator}.
	 * @param configuration The {@link FilterOperatorSelectConfigurator} consumer (not null)
	 * @return this
	 */
	B filterOperatorSelectConfiguration(Consumer<FilterOperatorSelectConfigurator> configuration);

	/**
	 * Set whether the operator selection is allowed.
	 * <p>
	 * If the operator selection is not allowed, the default filter operator will always be used.
	 * </p>
	 * @param operatorSelectionAllowed Whether the operator selection is allowed
	 * @return this
	 */
	B operatorSelectionAllowed(boolean operatorSelectionAllowed);

	/**
	 * Set the default filter operator.
	 * @param operator The default filter operator
	 * @return this
	 */
	B defaultOperator(InputFilterOperator operator);

	/**
	 * Add a {@link FilterOperatorChangeListener} to listener to filter operator change events.
	 * @param listener The listener to add (not null)
	 * @return this
	 */
	B withFilterOperatorChangeListener(FilterOperatorChangeListener<T> listener);

	// ------- sub types

	/**
	 * Filter operator select configurator.
	 */
	public interface FilterOperatorSelectConfigurator extends ComponentConfigurator<FilterOperatorSelectConfigurator>,
			HasSizeConfigurator<FilterOperatorSelectConfigurator>,
			HasStyleConfigurator<FilterOperatorSelectConfigurator> {

	}

	/**
	 * A listener for filter operator change events.
	 *
	 * @param <T> Value type
	 */
	@FunctionalInterface
	public interface FilterOperatorChangeListener<T> extends EventListener, Serializable {

		/**
		 * Invoked when this listener receives a filter operator change event from an event source to which it has been
		 * added.
		 * @param event the received event, not <code>null</code>
		 */
		void filterOperatorChanged(FilterOperatorChangeEvent<T> event);
	}

	/**
	 * Filter operator change event.
	 *
	 * @param <T> Value type
	 */
	public interface FilterOperatorChangeEvent<T> extends Serializable {

		/**
		 * Checks if this event originated from the client side.
		 * @return <code>true</code> if the event originated from the client side, <code>false</code> otherwise
		 */
		boolean isFromClient();

		/**
		 * Get the event source.
		 * @return The event source
		 */
		InputFilter<T> getSource();

		/**
		 * Get the value input.
		 * @return the value input
		 */
		Input<T> getInput();

		/**
		 * Get the filter operator value before this value change event occurred.
		 * @return the previous value
		 */
		InputFilterOperator getOldValue();

		/**
		 * Get the new filter operator value that triggered this event.
		 * @return the new value
		 */
		InputFilterOperator getValue();

	}

}
