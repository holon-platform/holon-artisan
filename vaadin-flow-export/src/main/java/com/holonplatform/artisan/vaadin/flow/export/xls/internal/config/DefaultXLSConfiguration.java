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
package com.holonplatform.artisan.vaadin.flow.export.xls.internal.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSColor;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSFileVersion;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSFontSize;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;

/**
 * Default {@link XLSConfiguration} implementation.
 *
 * @since 1.0.0
 */
public class DefaultXLSConfiguration implements XLSConfiguration {

	private static final long serialVersionUID = -1540021139154788221L;

	private List<Property<?>> properties = Collections.emptyList();
	private final Map<Property<?>, XLSPropertyConfiguration> propertyConfigurations = new HashMap<>();
	private Set<Property<?>> totalProperties = new HashSet<>();
	private XLSFileVersion fileVersion = XLSFileVersion.XLSX;
	private Localizable sheetName;
	private Localizable title;
	private XLSFontSize titleFontSize = XLSFontSize.AUTOMATIC;
	private XLSColor titleFontColor = XLSColor.AUTOMATIC;
	private XLSFontSize defaultFontSize = XLSFontSize.AUTOMATIC;
	private boolean wrapByDefault = false;
	private boolean shrinkToFitByDefault = false;
	private XLSCellConfiguration defaultHeaderConfiguration = XLSCellConfiguration.builder().build();

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration#getProperties()
	 */
	@Override
	public List<Property<?>> getProperties() {
		return properties;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration#getPropertyConfiguration(com.
	 * holonplatform.core.property.Property)
	 */
	@Override
	public Optional<XLSPropertyConfiguration> getPropertyConfiguration(Property<?> property) {
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		return Optional.ofNullable(propertyConfigurations.get(property));
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration#hasTotalFooter(com.holonplatform.core.
	 * property.Property)
	 */
	@Override
	public boolean hasTotalFooter(Property<?> property) {
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		return totalProperties.contains(property);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration#getFileVersion()
	 */
	@Override
	public XLSFileVersion getFileVersion() {
		return fileVersion;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration#getSheetName()
	 */
	@Override
	public Optional<Localizable> getSheetName() {
		return Optional.ofNullable(sheetName);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration#getTitle()
	 */
	@Override
	public Optional<Localizable> getTitle() {
		return Optional.ofNullable(title);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration#getTitleFontSize()
	 */
	@Override
	public XLSFontSize getTitleFontSize() {
		return titleFontSize;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration#getTitleFontColor()
	 */
	@Override
	public XLSColor getTitleFontColor() {
		return titleFontColor;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration#getDefaultFontSize()
	 */
	@Override
	public XLSFontSize getDefaultFontSize() {
		return defaultFontSize;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration#isWrapByDefault()
	 */
	@Override
	public boolean isWrapByDefault() {
		return wrapByDefault;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration#isShrinkToFitByDefault()
	 */
	@Override
	public boolean isShrinkToFitByDefault() {
		return shrinkToFitByDefault;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration#getDefaultHeaderConfiguration()
	 */
	@Override
	public XLSCellConfiguration getDefaultHeaderConfiguration() {
		return defaultHeaderConfiguration;
	}

}
