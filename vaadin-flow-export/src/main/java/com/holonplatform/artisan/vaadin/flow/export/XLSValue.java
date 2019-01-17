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
package com.holonplatform.artisan.vaadin.flow.export;

import java.io.Serializable;
import java.util.Optional;

import com.holonplatform.core.temporal.TemporalType;

/**
 * Represents an Excel value, tipycally used to set a sheet cell value.
 *
 * @param <T> Value type
 * 
 * @since 1.0.0
 */
public interface XLSValue<T> extends Serializable {

	/**
	 * Get the value, if available.
	 * @return The optional value
	 */
	Optional<T> getValue();

	/**
	 * Get the excel data type.
	 * @return The data type
	 */
	XLSDataType getDataType();

	/**
	 * Get the temporal type for date/time values.
	 * @return Optional temporal type
	 */
	Optional<TemporalType> getTemporalType();

	/**
	 * Get the data format to use.
	 * @return Optional data format
	 */
	Optional<String> getDataFormat();

}
