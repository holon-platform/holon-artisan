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

import java.util.function.Function;
import java.util.function.Supplier;

import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.query.QueryFilter;

/**
 * Default function to provide a {@link QueryFilter} using a a {@link Property} and an {@link InputFilterOperator}.
 * 
 * @param <T> Value type
 *
 * @since 1.0.0
 */
public class DefaultOperatorQueryFilterProvider<T> implements Function<T, QueryFilter> {

	private final Property<? super T> property;
	private final Supplier<InputFilterOperator> operatorSupplier;
	private final Supplier<Boolean> ignoreCaseSupplier;

	/**
	 * Constructor.
	 * @param property The property (not null)
	 * @param operatorSupplier The operator supplier (not null)
	 */
	public DefaultOperatorQueryFilterProvider(Property<? super T> property,
			Supplier<InputFilterOperator> operatorSupplier) {
		this(property, operatorSupplier, () -> false);
	}

	/**
	 * Constructor.
	 * @param property The property (not null)
	 * @param operatorSupplier The operator supplier (not null)
	 * @param ignoreCaseSupplier The ignore case supplier (not null)
	 */
	public DefaultOperatorQueryFilterProvider(Property<? super T> property,
			Supplier<InputFilterOperator> operatorSupplier, Supplier<Boolean> ignoreCaseSupplier) {
		super();
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		ObjectUtils.argumentNotNull(operatorSupplier, "Operator supplier must be not null");
		ObjectUtils.argumentNotNull(ignoreCaseSupplier, "Ignore case supplier must be not null");
		this.property = property;
		this.operatorSupplier = operatorSupplier;
		this.ignoreCaseSupplier = ignoreCaseSupplier;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.function.Function#apply(java.lang.Object)
	 */
	@Override
	public QueryFilter apply(T t) {
		final Boolean ignoreCase = ignoreCaseSupplier.get();
		return InputFilterOperator.getQueryFilter(t, property, operatorSupplier.get(),
				(ignoreCase != null) ? ignoreCase : false);
	}

}
