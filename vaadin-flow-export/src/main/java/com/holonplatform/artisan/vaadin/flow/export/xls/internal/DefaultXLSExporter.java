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

import java.io.OutputStream;
import java.util.Optional;

import com.holonplatform.artisan.vaadin.flow.export.ExportProgressCallback;
import com.holonplatform.artisan.vaadin.flow.export.exceptions.ExportException;
import com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.PropertyBox;
import com.holonplatform.core.property.PropertySet;
import com.vaadin.flow.data.provider.DataProvider;

/**
 * Default {@link XLSExporter} implementation.
 *
 * @since 1.0.0
 */
public class DefaultXLSExporter implements XLSExporter {

	private final DataProvider<PropertyBox, ?> dataSource;
	private final PropertySet<?> propertySet;

	private XLSConfiguration configuration;

	/**
	 * Constructor.
	 * @param dataSource The {@link DataProvider} to use as data source (not null)
	 * @param propertySet The property set to use (not null)
	 */
	public DefaultXLSExporter(DataProvider<PropertyBox, ?> dataSource, PropertySet<?> propertySet) {
		super();
		ObjectUtils.argumentNotNull(dataSource, "The DataProvider must be not null");
		ObjectUtils.argumentNotNull(propertySet, "The PropertySet must be not null");
		this.dataSource = dataSource;
		this.propertySet = propertySet;
	}

	/**
	 * Set the {@link XLSConfiguration} to use.
	 * @param configuration the configuration to set
	 */
	protected void setConfigurationSupplier(XLSConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Get the {@link DataProvider} to use as export data source.
	 * @return The {@link DataProvider}
	 */
	protected DataProvider<PropertyBox, ?> getDataProvider() {
		return dataSource;
	}

	/**
	 * Get the property set.
	 * @return the property set
	 */
	protected PropertySet<?> getPropertySet() {
		return propertySet;
	}

	/**
	 * Get the export configuration.
	 * @return Optional export configuration
	 */
	protected Optional<XLSConfiguration> getConfiguration() {
		return Optional.ofNullable(configuration);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter#export(java.io.OutputStream,
	 * com.holonplatform.artisan.vaadin.flow.export.ExportProgressCallback)
	 */
	@Override
	public void export(OutputStream outputStream, ExportProgressCallback exportProgressCallback)
			throws ExportException {
		ObjectUtils.argumentNotNull(outputStream, "The data output stream must be not null");
		ObjectUtils.argumentNotNull(exportProgressCallback, "The export progres callback must be not null");

		// TODO
	}

	/**
	 * Default {@link Builder} implementation.
	 */
	public static class DefaultBuilder implements Builder {

		private final DefaultXLSExporter exporter;

		/**
		 * Constructor.
		 * @param dataSource The {@link DataProvider} to use as data source (not null)
		 * @param propertySet The property set to use (not null)
		 */
		public DefaultBuilder(DataProvider<PropertyBox, ?> dataSource, PropertySet<?> propertySet) {
			super();
			this.exporter = new DefaultXLSExporter(dataSource, propertySet);
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter.Builder#configuration(com.holonplatform.artisan.
		 * vaadin.flow.export.xls.config.XLSConfiguration)
		 */
		@Override
		public Builder configuration(XLSConfiguration configuration) {
			this.exporter.setConfigurationSupplier(configuration);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter.Builder#build()
		 */
		@Override
		public XLSExporter build() {
			return this.exporter;
		}

	}

}
