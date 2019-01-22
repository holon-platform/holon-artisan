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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.holonplatform.artisan.vaadin.flow.export.BooleanExportMode;
import com.holonplatform.artisan.vaadin.flow.export.xls.internal.config.DefaultXLSConfiguration;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.Property;

/**
 * Export configuration.
 *
 * @since 1.0.0
 */
public interface XLSConfiguration extends Serializable {

	/**
	 * Get the properties to export and their order.
	 * @return The properties to export
	 */
	List<Property<?>> getProperties();

	/**
	 * Get the property configuration.
	 * @param property The property for which to obtain the configuration.
	 * @return Optional property configuration
	 */
	Optional<XLSPropertyConfiguration> getPropertyConfiguration(Property<?> property);

	/**
	 * Get whether to provide a <em>total</em> footer for given property, with the sum of its values.
	 * @param property The property to check
	 * @return Whether to provide a <em>total</em> footer for given property
	 */
	boolean hasTotalFooter(Property<?> property);

	/**
	 * Get the properties for which to provide a <em>total</em> footer, with the sum of the values.
	 * @return The properties for which to provide a <em>total</em> footer, empty if none
	 */
	Set<Property<?>> getTotalFooterProperties();

	/**
	 * Get the file version to use.
	 * @return The file version
	 */
	XLSFileVersion getFileVersion();

	/**
	 * Get the export sheet name.
	 * @return The export sheet name
	 */
	Optional<Localizable> getSheetName();

	/**
	 * Get the optional title.
	 * @return Optional title
	 */
	Optional<Localizable> getTitle();

	/**
	 * Get the title font size.
	 * @return The title font size, {@link XLSFontSize#AUTOMATIC} if not configured
	 */
	XLSFontSize getTitleFontSize();

	/**
	 * Get the title font color.
	 * @return The title font color, {@link XLSColor#AUTOMATIC} if not configured
	 */
	XLSColor getTitleFontColor();

	/**
	 * Get the default font size.
	 * @return The default font size, {@link XLSFontSize#AUTOMATIC} if not configured
	 */
	XLSFontSize getDefaultFontSize();

	/**
	 * Get whether the text should be wrapped (i.e. to make all content visible within a cell by displaying it on
	 * multiple lines) by default.
	 * @return Whether the text should be wrapped by default
	 */
	boolean isWrapByDefault();

	/**
	 * Get whether the cells should be auto-sized by default.
	 * @return Whether the cells should be auto-sized by default
	 */
	boolean isShrinkToFitByDefault();

	/**
	 * Get the default boolean values export mode.
	 * @return the default boolean values export mode
	 */
	BooleanExportMode getDefaultBooleanExportMode();

	/**
	 * Get the header cells configuration.
	 * @return the header cells configuration
	 */
	XLSCellConfiguration getHeaderConfiguration();

	/**
	 * Get the total cells configuration.
	 * @return the total cells configuration
	 */
	XLSCellConfiguration getTotalConfiguration();

	/**
	 * Clone this configuration.
	 * @return Cloned configuration builder
	 */
	Builder cloneConfiguration();

	/**
	 * Get a builder to create a new {@link XLSConfiguration}.
	 * @return A {@link XLSConfiguration} builder
	 */
	static Builder builder() {
		return new DefaultXLSConfiguration.DefaultBuilder();
	}

	/**
	 * {@link XLSConfiguration} builder.
	 */
	public interface Builder {

		/**
		 * Set the properties to export and their order.
		 * @param properties the properties to export
		 * @return this
		 */
		Builder properties(List<Property<?>> properties);

		/**
		 * Set the properties to export and their order.
		 * @param properties the properties to export
		 * @return this
		 */
		default Builder properties(Property<?>... properties) {
			return properties(Arrays.asList(properties));
		}

		/**
		 * Set the configuration for given property.
		 * @param property The property (not null)
		 * @param configuration The property configuration
		 * @return this
		 */
		Builder propertyConfiguration(Property<?> property, XLSPropertyConfiguration configuration);

		/**
		 * Add a property for which to provide a total footer.
		 * @param property The property for which to provide a total footer (not null)
		 * @return this
		 */
		Builder withTotalProperty(Property<?> property);

		/**
		 * Set the export file version.
		 * @param fileVersion the file version to set
		 * @return this
		 */
		Builder fileVersion(XLSFileVersion fileVersion);

		/**
		 * Set the export sheet name.
		 * @param sheetName the sheet name to set
		 * @return this
		 */
		Builder sheetName(Localizable sheetName);

		/**
		 * Set the export sheet name.
		 * @param sheetName The sheet name
		 * @return this
		 */
		default Builder sheetName(String sheetName) {
			return sheetName((sheetName == null) ? null : Localizable.builder().message(sheetName).build());
		}

		/**
		 * Set the export sheet name.
		 * @param defaultName Default sheet name if no translation is available for given <code>messageCode</code>
		 * @param messageCode Sheet name translation message key
		 * @param arguments Optional translation arguments
		 * @return this
		 */
		default Builder sheetName(String defaultName, String messageCode, Object... arguments) {
			return sheetName(Localizable.builder().message((defaultName == null) ? "" : defaultName)
					.messageCode(messageCode).messageArguments(arguments).build());
		}

		/**
		 * Set the title text.
		 * @param title the title to set
		 * @return this
		 */
		Builder title(Localizable title);

		/**
		 * Set the title text.
		 * @param title The title
		 * @return this
		 */
		default Builder title(String title) {
			return title((title == null) ? null : Localizable.builder().message(title).build());
		}

		/**
		 * Set the title text.
		 * @param defaultTitle Default title text if no translation is available for given <code>messageCode</code>
		 * @param messageCode Title text translation message key
		 * @param arguments Optional translation arguments
		 * @return this
		 */
		default Builder title(String defaultTitle, String messageCode, Object... arguments) {
			return title(Localizable.builder().message((defaultTitle == null) ? "" : defaultTitle)
					.messageCode(messageCode).messageArguments(arguments).build());
		}

		/**
		 * Set the title font size.
		 * @param titleFontSize the title font size to set
		 * @return this
		 */
		Builder titleFontSize(XLSFontSize titleFontSize);

		/**
		 * Set the title font color.
		 * @param titleFontColor the title font color to set
		 * @return this
		 */
		Builder titleFontColor(XLSColor titleFontColor);

		/**
		 * Set the default font size.
		 * @param defaultFontSize the default font size to set
		 * @return this
		 */
		Builder defaultFontSize(XLSFontSize defaultFontSize);

		/**
		 * Set whether to wrap text (i.e. to make all content visible within a cell by displaying it on multiple lines)
		 * by default.
		 * @param wrapByDefault Whether to wrap text by default
		 * @return this
		 */
		Builder wrapByDefault(boolean wrapByDefault);

		/**
		 * Set whether the cells should be auto-sized by default
		 * @param shrinkToFitByDefault Whether the cells should be auto-sized by default
		 * @return this
		 */
		Builder shrinkToFitByDefault(boolean shrinkToFitByDefault);

		/**
		 * Set the default boolean export mode.
		 * @param defaultBooleanExportMode the default {@link BooleanExportMode} to set
		 * @return this
		 */
		Builder defaultBooleanExportMode(BooleanExportMode defaultBooleanExportMode);

		/**
		 * Set the header cells configuration.
		 * @param headerConfiguration the header cells configuration to set
		 * @return this
		 */
		Builder headerConfiguration(XLSCellConfiguration headerConfiguration);

		/**
		 * Set the total cells configuration.
		 * @param totalConfiguration the total cells configuration to set
		 * @return this
		 */
		Builder totalConfiguration(XLSCellConfiguration totalConfiguration);

		/**
		 * Build the {@link XLSConfiguration}.
		 * @return The {@link XLSConfiguration} instance
		 */
		XLSConfiguration build();

	}

}
