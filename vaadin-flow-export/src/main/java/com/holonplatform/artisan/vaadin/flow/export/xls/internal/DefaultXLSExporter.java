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
import java.util.function.Supplier;

import com.holonplatform.artisan.vaadin.flow.export.ExportProgressCallback;
import com.holonplatform.artisan.vaadin.flow.export.exceptions.ExportException;
import com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.PropertyBox;
import com.vaadin.flow.data.provider.DataProvider;

/**
 * Default {@link XLSExporter} implementation.
 *
 * @since 1.0.0
 */
public class DefaultXLSExporter implements XLSExporter {

	private final Supplier<DataProvider<PropertyBox, ?>> dataSourceSupplier;

	private Supplier<XLSConfiguration> configurationSupplier = () -> null;

	/**
	 * Constructor.
	 * @param dataSourceSupplier The {@link DataProvider} supplier (not null)
	 */
	public DefaultXLSExporter(Supplier<DataProvider<PropertyBox, ?>> dataSourceSupplier) {
		super();
		ObjectUtils.argumentNotNull(dataSourceSupplier, "The DataProvider supplier must be not null");
		this.dataSourceSupplier = dataSourceSupplier;
	}

	/**
	 * Set the {@link XLSConfiguration} supplier,
	 * @param configurationSupplier the configuration supplier to set (not null)
	 */
	protected void setConfigurationSupplier(Supplier<XLSConfiguration> configurationSupplier) {
		ObjectUtils.argumentNotNull(configurationSupplier, "The configuration supplier must be not null");
		this.configurationSupplier = configurationSupplier;
	}

	/**
	 * Get the {@link DataProvider} to use as export data source.
	 * @return Optional {@link DataProvider}
	 */
	protected Optional<DataProvider<PropertyBox, ?>> getDataProvider() {
		return Optional.ofNullable(dataSourceSupplier.get());
	}

	/**
	 * Get the export configuration.
	 * @return Optional export configuration
	 */
	protected Optional<XLSConfiguration> getConfiguration() {
		return Optional.ofNullable(configurationSupplier.get());
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
		 * @param dataSourceSupplier The {@link DataProvider} supplier (not null)
		 */
		public DefaultBuilder(Supplier<DataProvider<PropertyBox, ?>> dataSourceSupplier) {
			super();
			this.exporter = new DefaultXLSExporter(dataSourceSupplier);
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter.Builder#configuration(java.util.function.
		 * Supplier)
		 */
		@Override
		public Builder configuration(Supplier<XLSConfiguration> configuration) {
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
