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

import java.util.Optional;

import com.holonplatform.artisan.core.internal.DecodableMode;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSNumberGroupSeparator;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration;
import com.holonplatform.core.i18n.Localizable;

/**
 * Default {@link XLSPropertyConfiguration} implementation.
 *
 * @since 1.0.0
 */
public class DefaultXLSPropertyConfiguration implements XLSPropertyConfiguration {

	private static final long serialVersionUID = -7058713135533211604L;

	private Localizable header;
	private XLSCellConfiguration headerConfiguration = DefaultXLSCellConfiguration.DEFAULT_HEADER_CONFIGURATION;
	private XLSCellConfiguration cellConfiguration = XLSCellConfiguration.builder().build();
	private XLSNumberGroupSeparator numberGroupSeparator = XLSNumberGroupSeparator.DEFAULT;
	private Integer numberDecimals = null;
	private DecodableMode decodableMode;

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration#getHeader()
	 */
	@Override
	public Optional<Localizable> getHeader() {
		return Optional.ofNullable(header);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration#getHeaderConfiguration()
	 */
	@Override
	public XLSCellConfiguration getHeaderConfiguration() {
		return headerConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration#getCellConfiguration()
	 */
	@Override
	public XLSCellConfiguration getCellConfiguration() {
		return cellConfiguration;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration#getNumberGroupSeparator()
	 */
	@Override
	public XLSNumberGroupSeparator getNumberGroupSeparator() {
		return numberGroupSeparator;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration#getNumberDecimals()
	 */
	@Override
	public Optional<Integer> getNumberDecimals() {
		return Optional.ofNullable(numberDecimals);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration#getDecodableMode()
	 */
	@Override
	public Optional<DecodableMode> getDecodableMode() {
		return Optional.ofNullable(decodableMode);
	}

	public void setHeader(Localizable header) {
		this.header = header;
	}

	public void setHeaderConfiguration(XLSCellConfiguration headerConfiguration) {
		this.headerConfiguration = (headerConfiguration != null) ? headerConfiguration
				: DefaultXLSCellConfiguration.DEFAULT_HEADER_CONFIGURATION;
	}

	public void setCellConfiguration(XLSCellConfiguration cellConfiguration) {
		this.cellConfiguration = (cellConfiguration != null) ? cellConfiguration
				: XLSCellConfiguration.builder().build();
	}

	public void setNumberGroupSeparator(XLSNumberGroupSeparator numberGroupSeparator) {
		this.numberGroupSeparator = (numberGroupSeparator != null) ? numberGroupSeparator
				: XLSNumberGroupSeparator.DEFAULT;
	}

	public void setNumberDecimals(Integer numberDecimals) {
		this.numberDecimals = numberDecimals;
	}

	public void setDecodableMode(DecodableMode decodableMode) {
		this.decodableMode = decodableMode;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration#cloneConfiguration()
	 */
	@Override
	public Builder cloneConfiguration() {
		final Builder builder = XLSPropertyConfiguration.builder();
		getHeader().ifPresent(h -> builder.header(h));
		builder.headerConfiguration(getHeaderConfiguration());
		builder.cellConfiguration(getCellConfiguration());
		builder.numberGroupSeparator(getNumberGroupSeparator());
		getNumberDecimals().ifPresent(d -> builder.numberDecimals(d));
		getDecodableMode().ifPresent(m -> builder.decodableMode(m));
		return builder;
	}

	public static class DefaultBuilder implements Builder {

		private final DefaultXLSPropertyConfiguration instance = new DefaultXLSPropertyConfiguration();

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration.Builder#header(com.
		 * holonplatform.core.i18n.Localizable)
		 */
		@Override
		public Builder header(Localizable header) {
			instance.setHeader(header);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration.Builder#headerConfiguration(
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration)
		 */
		@Override
		public Builder headerConfiguration(XLSCellConfiguration headerConfiguration) {
			instance.setHeaderConfiguration(headerConfiguration);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration.Builder#cellConfiguration(
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration)
		 */
		@Override
		public Builder cellConfiguration(XLSCellConfiguration cellConfiguration) {
			instance.setCellConfiguration(cellConfiguration);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration.Builder#numberGroupSeparator
		 * (com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSNumberGroupSeparator)
		 */
		@Override
		public Builder numberGroupSeparator(XLSNumberGroupSeparator numberGroupSeparator) {
			instance.setNumberGroupSeparator(numberGroupSeparator);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration.Builder#numberDecimals(java.
		 * lang.Integer)
		 */
		@Override
		public Builder numberDecimals(Integer numberDecimals) {
			instance.setNumberDecimals(numberDecimals);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration.Builder#decodableMode(com.
		 * holonplatform.artisan.core.internal.DecodableMode)
		 */
		@Override
		public Builder decodableMode(DecodableMode decodableMode) {
			instance.setDecodableMode(decodableMode);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration.Builder#build()
		 */
		@Override
		public XLSPropertyConfiguration build() {
			return instance;
		}

	}

}
