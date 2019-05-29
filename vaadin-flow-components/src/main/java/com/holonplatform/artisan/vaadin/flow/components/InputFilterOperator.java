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
package com.holonplatform.artisan.vaadin.flow.components;

import com.holonplatform.artisan.core.utils.Obj;
import com.holonplatform.artisan.vaadin.flow.components.internal.utils.ComponentUtils;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.core.query.StringFunction.Lower;

/**
 * {@link InputFilter} operator enumeration.
 *
 * @since 1.0.0
 */
public enum InputFilterOperator {

	EQUAL("=", "Equals", "com.holonplatform.artisan.input.filter.operator.equal"),

	NOT_EQUAL("<>", "Not equals", "com.holonplatform.artisan.input.filter.operator.not_equal"),

	GREATER_THAN(">", "Greater than", "com.holonplatform.artisan.input.filter.operator.greater_than"),

	GREATER_OR_EQUAL(">=", "Greater or equal", "com.holonplatform.artisan.input.filter.operator.greater_or_equal"),

	LESS_THAN("<", "Less than", "com.holonplatform.artisan.input.filter.operator.less_than"),

	LESS_OR_EQUAL("<=", "Less or equal", "com.holonplatform.artisan.input.filter.operator.lass_or_equal"),

	CONTAINS("~", "Contains", "com.holonplatform.artisan.input.filter.operator.contains"),

	STARTS_WITH("^", "Starts with", "com.holonplatform.artisan.input.filter.operator.starts_with"),

	EMPTY("{}", "Is null", "com.holonplatform.artisan.input.filter.operator.empty"),

	NOT_EMPTY("*", "Is not null", "com.holonplatform.artisan.input.filter.operator.not_empty"),

	BETWEEN("[..]", "Between", "com.holonplatform.artisan.input.filter.operator.between");

	private final String symbol;
	private final Localizable caption;

	private InputFilterOperator(String symbol, String defaultCaption, String captionMessageCode) {
		this(symbol, Localizable.of(defaultCaption, captionMessageCode));
	}

	private InputFilterOperator(String symbol, Localizable caption) {
		this.symbol = symbol;
		this.caption = caption;
	}

	/**
	 * Get the operator symbol.
	 * @return the operator symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Get the operator caption.
	 * @return the operator caption
	 */
	public Localizable getCaption() {
		return caption;
	}

	/**
	 * Get the {@link QueryFilter} according to given value, property and operator.
	 * @param <T> Value type
	 * @param value The input value
	 * @param betweenValue The between range end value, if filter operator is {@link InputFilterOperator#BETWEEN}
	 * @param property The property to use
	 * @param operator The filter operator
	 * @param ignoreCase Whether to ignore case
	 * @return The {@link QueryFilter}, or <code>null</code> if value is <code>null</code> or empty
	 */
	public static <T> QueryFilter getQueryFilter(T value, T betweenValue, Property<? super T> property,
			InputFilterOperator operator, boolean ignoreCase) {
		switch (operator) {
		case EMPTY: {
			return QueryFilter.isNull(property);
		}
		case NOT_EMPTY: {
			return QueryFilter.isNotNull(property);
		}
		case EQUAL: {
			if (!ComponentUtils.isEmptyValue(value)) {
				if (Obj.isString(property.getType()) && ignoreCase) {
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
				if (Obj.isString(property.getType()) && ignoreCase) {
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
			if (!ComponentUtils.isEmptyValue(value) && Obj.isString(property.getType())) {
				@SuppressWarnings("unchecked")
				final Property<String> stringProperty = (Property<String>) property;
				return QueryFilter.contains(stringProperty, ComponentUtils.trimValueAsString(value), ignoreCase);
			}
			return null;
		}
		case STARTS_WITH: {
			if (!ComponentUtils.isEmptyValue(value) && Obj.isString(property.getType())) {
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
		case BETWEEN: {
			if (!ComponentUtils.isEmptyValue(value) && !ComponentUtils.isEmptyValue(betweenValue)) {
				return QueryFilter.between(property, value, betweenValue);
			} else if (!ComponentUtils.isEmptyValue(value) && ComponentUtils.isEmptyValue(betweenValue)) {
				return QueryFilter.goe(property, value);
			} else if (ComponentUtils.isEmptyValue(value) && !ComponentUtils.isEmptyValue(betweenValue)) {
				return QueryFilter.loe(property, betweenValue);
			}
			return null;
		}
		default:
			break;
		}
		return null;
	}

}
