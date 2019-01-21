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

	/**
	 * Set the properties to export and their order.
	 * @param properties the properties to set
	 */
	protected void setProperties(List<Property<?>> properties) {
		this.properties = (properties != null) ? properties : Collections.emptyList();
	}

	/**
	 * Set the configuration for given property.
	 * @param property The property (not null)
	 * @param configuration The property configuration
	 */
	protected void setPropertyConfiguration(Property<?> property, XLSPropertyConfiguration configuration) {
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		this.propertyConfigurations.put(property, configuration);
	}

	/**
	 * Add a property for which to provide a total footer.
	 * @param property The property for which to provide a total footer (not null)
	 */
	protected void addTotalProperty(Property<?> property) {
		ObjectUtils.argumentNotNull(property, "Property must be not null");
		this.totalProperties.add(property);
	}

	/**
	 * Set the export file version.
	 * @param fileVersion the file version to set
	 */
	protected void setFileVersion(XLSFileVersion fileVersion) {
		this.fileVersion = (fileVersion != null) ? fileVersion : XLSFileVersion.XLSX;
	}

	/**
	 * Set the export sheet name.
	 * @param sheetName the sheet name to set
	 */
	protected void setSheetName(Localizable sheetName) {
		this.sheetName = sheetName;
	}

	/**
	 * Set the title text.
	 * @param title the title to set
	 */
	protected void setTitle(Localizable title) {
		this.title = title;
	}

	/**
	 * Set the title font size.
	 * @param titleFontSize the title font size to set
	 */
	protected void setTitleFontSize(XLSFontSize titleFontSize) {
		this.titleFontSize = (titleFontSize != null) ? titleFontSize : XLSFontSize.AUTOMATIC;
	}

	/**
	 * Set the title font color.
	 * @param titleFontColor the title font color to set
	 */
	protected void setTitleFontColor(XLSColor titleFontColor) {
		this.titleFontColor = (titleFontColor != null) ? titleFontColor : XLSColor.AUTOMATIC;
	}

	/**
	 * Set the default font size.
	 * @param defaultFontSize the default font size to set
	 */
	protected void setDefaultFontSize(XLSFontSize defaultFontSize) {
		this.defaultFontSize = (defaultFontSize != null) ? defaultFontSize : XLSFontSize.AUTOMATIC;
	}

	/**
	 * Set whether to wrap text (i.e. to make all content visible within a cell by displaying it on multiple lines) by
	 * default.
	 * @param wrapByDefault Whether to wrap text by default
	 */
	protected void setWrapByDefault(boolean wrapByDefault) {
		this.wrapByDefault = wrapByDefault;
	}

	/**
	 * Set whether the cells should be auto-sized by default
	 * @param shrinkToFitByDefault Whether the cells should be auto-sized by default
	 */
	protected void setShrinkToFitByDefault(boolean shrinkToFitByDefault) {
		this.shrinkToFitByDefault = shrinkToFitByDefault;
	}

	/**
	 * Set the default header cells configuration.
	 * @param defaultHeaderConfiguration the default header cells configuration to set
	 */
	protected void setDefaultHeaderConfiguration(XLSCellConfiguration defaultHeaderConfiguration) {
		this.defaultHeaderConfiguration = (defaultHeaderConfiguration != null) ? defaultHeaderConfiguration
				: XLSCellConfiguration.builder().build();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration#cloneConfiguration()
	 */
	@Override
	public Builder cloneConfiguration() {
		final Builder builder = XLSConfiguration.builder();
		builder.properties(getProperties());
		this.propertyConfigurations.entrySet().forEach(e -> {
			builder.propertyConfiguration(e.getKey(), e.getValue());
		});
		this.totalProperties.forEach(p -> builder.withTotalProperty(p));
		builder.fileVersion(getFileVersion());
		getSheetName().ifPresent(n -> builder.sheetName(n));
		getTitle().ifPresent(t -> builder.title(t));
		builder.titleFontSize(getTitleFontSize());
		builder.titleFontColor(getTitleFontColor());
		builder.defaultFontSize(getDefaultFontSize());
		builder.wrapByDefault(isWrapByDefault());
		builder.shrinkToFitByDefault(isShrinkToFitByDefault());
		builder.defaultHeaderConfiguration(getDefaultHeaderConfiguration());
		return builder;
	}

	public static class DefaultBuilder implements Builder {

		private final DefaultXLSConfiguration configuration = new DefaultXLSConfiguration();

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration.Builder#properties(java.util.List)
		 */
		@Override
		public Builder properties(List<Property<?>> properties) {
			this.configuration.setProperties(properties);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration.Builder#propertyConfiguration(com.
		 * holonplatform.core.property.Property,
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration)
		 */
		@Override
		public Builder propertyConfiguration(Property<?> property, XLSPropertyConfiguration configuration) {
			this.configuration.setPropertyConfiguration(property, configuration);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration.Builder#withTotalProperty(com.
		 * holonplatform.core.property.Property)
		 */
		@Override
		public Builder withTotalProperty(Property<?> property) {
			this.configuration.addTotalProperty(property);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration.Builder#fileVersion(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSFileVersion)
		 */
		@Override
		public Builder fileVersion(XLSFileVersion fileVersion) {
			this.configuration.setFileVersion(fileVersion);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration.Builder#sheetName(com.holonplatform.
		 * core.i18n.Localizable)
		 */
		@Override
		public Builder sheetName(Localizable sheetName) {
			this.configuration.setSheetName(sheetName);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration.Builder#title(com.holonplatform.core
		 * .i18n.Localizable)
		 */
		@Override
		public Builder title(Localizable title) {
			this.configuration.setTitle(title);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration.Builder#titleFontSize(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSFontSize)
		 */
		@Override
		public Builder titleFontSize(XLSFontSize titleFontSize) {
			this.configuration.setTitleFontSize(titleFontSize);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration.Builder#titleFontColor(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSColor)
		 */
		@Override
		public Builder titleFontColor(XLSColor titleFontColor) {
			this.configuration.setTitleFontColor(titleFontColor);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration.Builder#defaultFontSize(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSFontSize)
		 */
		@Override
		public Builder defaultFontSize(XLSFontSize defaultFontSize) {
			this.configuration.setDefaultFontSize(defaultFontSize);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration.Builder#wrapByDefault(boolean)
		 */
		@Override
		public Builder wrapByDefault(boolean wrapByDefault) {
			this.configuration.setWrapByDefault(wrapByDefault);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration.Builder#shrinkToFitByDefault(
		 * boolean)
		 */
		@Override
		public Builder shrinkToFitByDefault(boolean shrinkToFitByDefault) {
			this.configuration.setShrinkToFitByDefault(shrinkToFitByDefault);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration.Builder#defaultHeaderConfiguration(
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration)
		 */
		@Override
		public Builder defaultHeaderConfiguration(XLSCellConfiguration defaultHeaderConfiguration) {
			this.configuration.setDefaultHeaderConfiguration(defaultHeaderConfiguration);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration.Builder#build()
		 */
		@Override
		public XLSConfiguration build() {
			return configuration;
		}

	}

}
