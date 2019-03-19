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

import com.holonplatform.core.i18n.Localizable;

/**
 * An {@link InputFilter} with filter operator support.
 *
 * @param <T> Value type
 *
 * @since 1.0.0
 */
public interface OperatorInputFilter<T> extends InputFilter<T> {

	/**
	 * Get the current selected operator.
	 * @return The filter operator
	 */
	Operator getOperator();

	/**
	 * Filter operators.
	 */
	public enum Operator {

		EQUAL("=", "Equals", "com.holonplatform.artisan.input.filter.operator.equal"),

		NOT_EQUAL("<>", "Not equals", "com.holonplatform.artisan.input.filter.operator.not_equal"),

		GREATER_THAN(">", "Greater than", "com.holonplatform.artisan.input.filter.operator.greater_than"),

		GREATER_OR_EQUAL(">=", "Greater or equal", "com.holonplatform.artisan.input.filter.operator.greater_or_equal"),

		LESS_THAN("<", "Less than", "com.holonplatform.artisan.input.filter.operator.less_than"),

		LESS_OR_EQUAL("<=", "Less or equal", "com.holonplatform.artisan.input.filter.operator.lass_or_equal"),

		CONTAINS("~", "Contains", "com.holonplatform.artisan.input.filter.operator.contains"),

		STARTS_WITH("^", "Starts with", "com.holonplatform.artisan.input.filter.operator.starts_with"),

		EMPTY("{}", "Is null or empty", "com.holonplatform.artisan.input.filter.operator.empty"),

		NOT_EMPTY("*", "Is not null nor empty", "com.holonplatform.artisan.input.filter.operator.not_empty");

		private final String symbol;
		private final Localizable caption;

		private Operator(String symbol, String defaultCaption, String captionMessageCode) {
			this(symbol, Localizable.of(defaultCaption, captionMessageCode));
		}

		private Operator(String symbol, Localizable caption) {
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

	}

}
