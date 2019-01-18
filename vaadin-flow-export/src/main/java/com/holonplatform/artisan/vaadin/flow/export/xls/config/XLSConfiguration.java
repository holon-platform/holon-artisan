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
import java.util.List;
import java.util.Optional;

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
	 * Get whether the cell should be auto-sized by default.
	 * @return Whether the cell should be auto-sized by default
	 */
	boolean isShrinkToFitByDefault();

	/**
	 * Get the default header cells configuration.
	 * @return the default header cells configuration
	 */
	XLSCellConfiguration getDefaultHeaderConfiguration();

}
