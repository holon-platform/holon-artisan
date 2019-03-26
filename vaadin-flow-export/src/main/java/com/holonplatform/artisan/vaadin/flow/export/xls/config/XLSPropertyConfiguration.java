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
package com.holonplatform.artisan.vaadin.flow.export.xls.config;

import java.io.Serializable;
import java.util.Optional;

import com.holonplatform.artisan.core.internal.DecodableMode;
import com.holonplatform.artisan.vaadin.flow.export.BooleanExportMode;
import com.holonplatform.artisan.vaadin.flow.export.xls.internal.config.DefaultXLSPropertyConfiguration;
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
	Optional<Localizable> getHeader();

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
	 * Get the boolean values export mode.
	 * @return the boolean values export mode
	 */
	BooleanExportMode getBooleanExportMode();

	/**
	 * When the boolean export mode is {@link BooleanExportMode#TEXT}, get the text to use for the <code>true</code>
	 * value.
	 * @return The text to use to export the boolean <code>true</code> value
	 */
	Optional<String> getBooleanTextForTrue();

	/**
	 * When the boolean export mode is {@link BooleanExportMode#TEXT}, get the text to use for the <code>false</code>
	 * value.
	 * @return The text to use to export the boolean <code>false</code> value
	 */
	Optional<String> getBooleanTextForFalse();

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
	Optional<DecodableMode> getDecodableMode();

	/**
	 * Clone this configuration.
	 * @return Cloned configuration builder
	 */
	Builder cloneConfiguration();

	/**
	 * Get a builder to create a new {@link XLSPropertyConfiguration}.
	 * @return A {@link XLSPropertyConfiguration} builder
	 */
	static Builder builder() {
		return new DefaultXLSPropertyConfiguration.DefaultBuilder();
	}

	/**
	 * {@link XLSPropertyConfiguration} builder.
	 */
	public interface Builder {

		/**
		 * Set the header text.
		 * @param header The header text
		 * @return this
		 */
		Builder header(Localizable header);

		/**
		 * Set the header text.
		 * @param header The header text
		 * @return this
		 */
		default Builder header(String header) {
			return header(Localizable.builder().message(header).build());
		}

		/**
		 * Set the header cell configuration.
		 * @param headerConfiguration The header cell configuration
		 * @return this
		 */
		Builder headerConfiguration(XLSCellConfiguration headerConfiguration);

		/**
		 * Set the cell configuration.
		 * @param cellConfiguration The cell configuration
		 * @return this
		 */
		Builder cellConfiguration(XLSCellConfiguration cellConfiguration);

		/**
		 * Set whether to use the group separator for numeric data types.
		 * @param numberGroupSeparator The group separator mode
		 * @return this
		 */
		Builder numberGroupSeparator(XLSNumberGroupSeparator numberGroupSeparator);

		/**
		 * Set the decimal positions to display for numeric data types.
		 * @param numberDecimals the decimal positions
		 * @return this
		 */
		Builder numberDecimals(Integer numberDecimals);

		/**
		 * Set the decodable mode for decodable data types.
		 * @param decodableMode The decodable mode
		 * @return this
		 */
		Builder decodableMode(DecodableMode decodableMode);

		/**
		 * Set the boolean values export mode.
		 * @param booleanExportMode The boolean export mode
		 * @return this
		 */
		Builder booleanExportMode(BooleanExportMode booleanExportMode);

		/**
		 * When the boolean export mode is {@link BooleanExportMode#TEXT}, set the text to use for the <code>true</code>
		 * value.
		 * @param booleanTextForTrue The text to use to export the boolean <code>true</code> value
		 * @return this
		 */
		Builder booleanTextForTrue(String booleanTextForTrue);

		/**
		 * When the boolean export mode is {@link BooleanExportMode#TEXT}, set the text to use for the
		 * <code>false</code> value.
		 * @param booleanTextForFalse The text to use to export the boolean <code>false</code> value
		 * @return this
		 */
		Builder booleanTextForFalse(String booleanTextForFalse);

		/**
		 * Build the {@link XLSPropertyConfiguration}.
		 * @return The {@link XLSPropertyConfiguration} instance
		 */
		XLSPropertyConfiguration build();

	}

}
