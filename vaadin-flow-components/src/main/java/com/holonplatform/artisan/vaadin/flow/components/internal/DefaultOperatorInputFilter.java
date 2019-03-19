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
package com.holonplatform.artisan.vaadin.flow.components.internal;

import java.util.Optional;
import java.util.function.Supplier;

import com.holonplatform.artisan.vaadin.flow.components.OperatorInputFilter;
import com.holonplatform.artisan.vaadin.flow.components.OperatorInputFilter.Operator;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.internal.utils.TypeUtils;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.core.query.StringFunction.Lower;
import com.holonplatform.vaadin.flow.components.Input;

/**
 * Default {@link OperatorInputFilter} implementation.
 *
 * @param <T> Value type
 *
 * @since 1.0.0
 */
public class DefaultOperatorInputFilter<T> extends InputFilterAdapter<T> {

	private static final long serialVersionUID = -5174883934039181312L;

	private Runnable resetCallback;

	/**
	 * Constructor.
	 * @param input The {@link Input} component (not null)
	 * @param property The {@link Property} to use for the {@link QueryFilter} (not null)
	 * @param operatorSupplier The filter {@link Operator} supplier (not null)
	 */
	public DefaultOperatorInputFilter(Input<T> input, Property<? super T> property,
			Supplier<Operator> operatorSupplier) {
		this(input, property, operatorSupplier, () -> false);
	}

	/**
	 * Constructor.
	 * @param input The {@link Input} component (not null)
	 * @param property The {@link Property} to use for the {@link QueryFilter} (not null)
	 * @param operatorSupplier The filter {@link Operator} supplier (not null)
	 * @param ignoreCaseSupplier The ignore case mode supplier (not null)
	 */
	public DefaultOperatorInputFilter(Input<T> input, Property<? super T> property, Supplier<Operator> operatorSupplier,
			Supplier<Boolean> ignoreCaseSupplier) {
		super(input,
				value -> getFilterUsingOperator(value, property, operatorSupplier.get(), ignoreCaseSupplier.get()));
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		ObjectUtils.argumentNotNull(operatorSupplier, "Operator supplier must be not null");
		ObjectUtils.argumentNotNull(ignoreCaseSupplier, "Ignore case supplier must be not null");
	}

	/**
	 * Get the callback to invoke when a {@link #reset()} is performed.
	 * @return the reset callback, if available
	 */
	public Optional<Runnable> getResetCallback() {
		return Optional.ofNullable(resetCallback);
	}

	/**
	 * Set the callback to invoke when a {@link #reset()} is performed.
	 * @param resetCallback the reset callback to set
	 */
	public void setResetCallback(Runnable resetCallback) {
		this.resetCallback = resetCallback;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.InputFilter#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		// callback
		getResetCallback().ifPresent(c -> c.run());
	}

	/**
	 * Get the {@link QueryFilter} according to given value, property and operator.
	 * @param <T> Value type
	 * @param value The input value
	 * @param property The property to use
	 * @param operator The filter operator
	 * @param ignoreCase Whether to ignore case
	 * @return The {@link QueryFilter}, or <code>null</code> if value is <code>null</code> or empty
	 */
	private static <T> QueryFilter getFilterUsingOperator(T value, Property<? super T> property, Operator operator,
			boolean ignoreCase) {
		if (!ComponentUtils.isEmptyValue(value)) {
			switch (operator) {
			case EMPTY: {
				return QueryFilter.isNull(property);
			}
			case NOT_EMPTY: {
				return QueryFilter.isNotNull(property);
			}
			case EQUAL: {
				if (!ComponentUtils.isEmptyValue(value)) {
					if (TypeUtils.isString(property.getType()) && ignoreCase) {
						@SuppressWarnings("unchecked")
						final Property<String> stringProperty = (Property<String>) property;
						return QueryFilter.eq(Lower.create(stringProperty),
								ComponentUtils.trimValueAsString(value).toLowerCase());
					}
					return QueryFilter.eq(property, ComponentUtils.trimValue(value));
				}
				return null;
			}
			case NOT_EQUAL: {
				if (!ComponentUtils.isEmptyValue(value)) {
					if (TypeUtils.isString(property.getType()) && ignoreCase) {
						@SuppressWarnings("unchecked")
						final Property<String> stringProperty = (Property<String>) property;
						return QueryFilter.neq(Lower.create(stringProperty),
								ComponentUtils.trimValueAsString(value).toLowerCase());
					}
					return QueryFilter.neq(property, ComponentUtils.trimValue(value));
				}
				return null;
			}
			case CONTAINS: {
				if (!ComponentUtils.isEmptyValue(value) && TypeUtils.isString(property.getType())) {
					@SuppressWarnings("unchecked")
					final Property<String> stringProperty = (Property<String>) property;
					return QueryFilter.contains(stringProperty, ComponentUtils.trimValueAsString(value), ignoreCase);
				}
				return null;
			}
			case STARTS_WITH: {
				if (!ComponentUtils.isEmptyValue(value) && TypeUtils.isString(property.getType())) {
					@SuppressWarnings("unchecked")
					final Property<String> stringProperty = (Property<String>) property;
					return QueryFilter.startsWith(stringProperty, ComponentUtils.trimValueAsString(value), ignoreCase);
				}
				return null;
			}
			case GREATER_OR_EQUAL: {
				return ComponentUtils.isEmptyValue(value) ? null : QueryFilter.goe(property, value);
			}
			case GREATER_THAN: {
				return ComponentUtils.isEmptyValue(value) ? null : QueryFilter.gt(property, value);
			}
			case LESS_OR_EQUAL: {
				return ComponentUtils.isEmptyValue(value) ? null : QueryFilter.loe(property, value);
			}
			case LESS_THAN: {
				return ComponentUtils.isEmptyValue(value) ? null : QueryFilter.lt(property, value);
			}
			default:
				break;
			}
		}
		return null;
	}

}
