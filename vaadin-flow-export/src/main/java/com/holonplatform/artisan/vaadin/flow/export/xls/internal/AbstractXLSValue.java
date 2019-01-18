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
package com.holonplatform.artisan.vaadin.flow.export.xls.internal;

import java.util.Optional;

import com.holonplatform.artisan.vaadin.flow.export.xls.XLSValue;
import com.holonplatform.core.temporal.TemporalType;

/**
 * Base {@link XLSValue} class.
 *
 * @param <T> Value type
 * 
 * @since 1.0.0
 */
public abstract class AbstractXLSValue<T> implements XLSValue<T> {

	private static final long serialVersionUID = 548397409492562060L;

	private final T value;
	private final TemporalType temporalType;
	private final String dataFormat;

	/**
	 * Constructor.
	 * @param value The value
	 * @param temporalType Temporal type
	 * @param dataFormat Data format string
	 */
	public AbstractXLSValue(T value, TemporalType temporalType, String dataFormat) {
		super();
		this.value = value;
		this.temporalType = temporalType;
		this.dataFormat = dataFormat;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.XLSValue#getValue()
	 */
	@Override
	public Optional<T> getValue() {
		return Optional.ofNullable(value);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.XLSValue#getTemporalType()
	 */
	@Override
	public Optional<TemporalType> getTemporalType() {
		return Optional.ofNullable(temporalType);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.XLSValue#getDataFormat()
	 */
	@Override
	public Optional<String> getDataFormat() {
		return Optional.ofNullable(dataFormat);
	}

}
