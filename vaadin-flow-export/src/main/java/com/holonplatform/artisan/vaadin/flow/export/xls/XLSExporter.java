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
package com.holonplatform.artisan.vaadin.flow.export.xls;

import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.holonplatform.artisan.core.exceptions.InterruptedOperationException;
import com.holonplatform.artisan.core.operation.OperationProgress;
import com.holonplatform.artisan.core.operation.OperationProgressCallback;
import com.holonplatform.artisan.vaadin.flow.export.exceptions.ExportException;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration;
import com.holonplatform.artisan.vaadin.flow.export.xls.internal.DefaultXLSExporter;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.i18n.LocalizationContext;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.property.PropertyBox;
import com.holonplatform.core.property.PropertySet;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.QuerySortOrder;

/**
 * XLS format data exporter.
 * <p>
 * The exporter uses a {@link PropertyBox} item type {@link DataProvider} as data source. The {@link XLSConfiguration}
 * type can be used to provide the export configuration.
 * </p>
 *
 * @since 1.0.0
 */
public interface XLSExporter {

	public static final Localizable DEFAULT_SHEET_NAME = Localizable.of("export",
			"holon.artisan.export.xls.default.sheet.name");

	/**
	 * Export the data to the provided {@link OutputStream}.
	 * @param outputStream The {@link OutputStream} to use to write the exported data (not null)
	 * @throws InterruptedOperationException If the export was interrupted
	 * @throws ExportException If an error occurred
	 */
	default void export(OutputStream outputStream) throws ExportException {
		export(outputStream, (total, completed) -> OperationProgress.PROCEED);
	}

	/**
	 * Export the data to the provided {@link OutputStream}.
	 * @param outputStream The {@link OutputStream} to use to write the exported data (not null)
	 * @param exportProgressCallback The callback function to invoke when the export progress changes (not null)
	 * @throws InterruptedOperationException If the export was interrupted
	 * @throws ExportException If an error occurred
	 */
	void export(OutputStream outputStream, OperationProgressCallback exportProgressCallback) throws ExportException;

	/**
	 * Get a {@link XLSExporter} using given {@link DataProvider} as export data source.
	 * @param dataSource The export data source (not null)
	 * @param propertySet The property set to use (not null)
	 * @return A new {@link XLSExporter} builder
	 */
	static Builder builder(DataProvider<PropertyBox, ?> dataSource, PropertySet<?> propertySet) {
		return new DefaultXLSExporter.DefaultBuilder(dataSource, propertySet);
	}

	/**
	 * Get a {@link XLSExporter} using given {@link DataProvider} supplier as export data source.
	 * @param <P> Property type
	 * @param dataSource The export data source provider (not null)
	 * @param properties The property set to use (not null)
	 * @return A new {@link XLSExporter} builder
	 */
	@SuppressWarnings("rawtypes")
	static <P extends Property> Builder builder(Supplier<DataProvider<PropertyBox, ?>> dataSource,
			Iterable<P> properties) {
		return builder(dataSource, PropertySet.of(properties));
	}

	/**
	 * Get a {@link XLSExporter} using given {@link DataProvider} supplier as export data source.
	 * @param dataSource The export data source provider (not null)
	 * @param properties The property set to use (not null)
	 * @return A new {@link XLSExporter} builder
	 */
	static Builder builder(Supplier<DataProvider<PropertyBox, ?>> dataSource, Property<?>... properties) {
		return builder(dataSource, PropertySet.of(properties));
	}

	/**
	 * XLSExporter builder.
	 */
	public interface Builder {

		/**
		 * Set the XLS configuration.
		 * @param configuration The configuration to set
		 * @return this
		 */
		Builder configuration(XLSConfiguration configuration);

		/**
		 * Set the function to use to obtain the header text for a property.
		 * @param columnHeaderProvider The column header provider function.
		 * @return this
		 */
		Builder columnHeaderProvider(Function<Property<?>, Optional<String>> columnHeaderProvider);

		/**
		 * Set the {@link PropertyXLSValueProviderRegistry} to use.
		 * @param registry The registry to set
		 * @return this
		 */
		Builder registry(PropertyXLSValueProviderRegistry registry);

		/**
		 * Set the {@link LocalizationContext} to use.
		 * @param localizationContext The {@link LocalizationContext} to set
		 * @return this
		 */
		Builder localizationContext(LocalizationContext localizationContext);

		/**
		 * Set an additional query sorts provider.
		 * @param querySortsProvider The query sorts provider to set
		 * @return this
		 */
		Builder querySortsProvider(Supplier<List<QuerySortOrder>> querySortsProvider);

		/**
		 * Build the exporter.
		 * @return The {@link XLSExporter}
		 */
		XLSExporter build();

	}
}
