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
package com.holonplatform.artisan.vaadin.flow.export.xls.config;

import java.io.Serializable;
import java.util.Optional;

import com.holonplatform.artisan.core.internal.DecodableMode;
import com.holonplatform.core.i18n.Localizable;

/**
 * Configuration of a property to export.
 *
 * @since 1.0.0
 */
public interface XLSPropertyConfiguration extends Serializable {

	/**
	 * Get the header localizable text.
	 * @return the header localizable text
	 */
	Localizable getHeader();

	/**
	 * Get the header cell configuration.
	 * @return the header cell configuration
	 */
	XLSCellConfiguration getHeaderConfiguration();

	/**
	 * Get the cell configuration.
	 * @return the cell configuration
	 */
	XLSCellConfiguration getCellConfiguration();

	/**
	 * Get the group separator mode for numbers.
	 * @return The group separator mode
	 */
	XLSNumberGroupSeparator getNumberGroupSeparator();

	/**
	 * Get the number decimal positions to show.
	 * @return Optional number decimal positions
	 */
	Optional<Integer> getNumberDecimals();

	/**
	 * Get the {@link DecodableMode} to use to decode decodable data types.
	 * @return The {@link DecodableMode}
	 */
	DecodableMode getDecodableMode();

}
