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
package com.holonplatform.artisan.demo.components;

import static com.holonplatform.artisan.demo.model.Product.CATEGORY;
import static com.holonplatform.artisan.demo.model.Product.DESCRIPTION;
import static com.holonplatform.artisan.demo.model.Product.ID;
import static com.holonplatform.artisan.demo.model.Product.PRODUCT;
import static com.holonplatform.artisan.demo.model.Product.TARGET;
import static com.holonplatform.artisan.demo.model.Product.UNIT_PRICE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.holonplatform.artisan.core.exceptions.OperationExecutionException;
import com.holonplatform.artisan.demo.root.HasViewActions;
import com.holonplatform.artisan.demo.root.HasViewTitle;
import com.holonplatform.artisan.demo.root.Menu;
import com.holonplatform.artisan.demo.servlet.FileDownloadServlet;
import com.holonplatform.artisan.vaadin.flow.app.layout.ApplicationLayout;
import com.holonplatform.artisan.vaadin.flow.components.OperationProgressDialog;
import com.holonplatform.artisan.vaadin.flow.export.xls.PropertyXLSValueProviderRegistry;
import com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter;
import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.vaadin.flow.components.Components;
import com.holonplatform.vaadin.flow.components.Input;
import com.holonplatform.vaadin.flow.components.PropertyListing;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "export", layout = Menu.class)
public class ExportPage extends VerticalLayout implements HasViewTitle, HasViewActions {

	private static final long serialVersionUID = 1L;

	private final Input<String> fltCategory;
	private final PropertyListing listing;

	@Autowired
	public ExportPage(Datastore datastore) {
		super();
		setSizeFull();

		fltCategory = Input.singleSelect(String.class).items(
				datastore.query(TARGET).filter(CATEGORY.isNotNull()).sort(CATEGORY.asc()).distinct().list(CATEGORY))
				.placeholder("Category").build();

		listing = Components.listing.properties(PRODUCT).dataSource(datastore, TARGET)
				// fixed filter
				.withQueryFilter(UNIT_PRICE.goe(20d))
				// dynamic filters
				.withQueryConfigurationProvider(() -> {
					if (!fltCategory.isEmpty()) {
						return CATEGORY.eq(fltCategory.getValue());
					}
					return null;
				})
				// sorts
				.withQuerySort(ID.asc()).withDefaultQuerySort(DESCRIPTION.asc())
				// size full
				.fullSize()
				// atios column
				.withComponentColumn(item -> Components.button().text("(" + item.getValue(ID) + ")").build())
				.displayAsFirst().flexGrow(0).width("90px").add()
				//
				.build();
		add(listing.getComponent());
		setFlexGrow(1, listing.getComponent());

		fltCategory.addValueChangeListener(e -> listing.refresh());
	}

	private PropertySet<?> getExportProperties() {
		return PropertySet.of(listing.getVisibleColumns().stream()
				.filter(p -> !HasElement.class.isAssignableFrom(p.getType())).collect(Collectors.toList()));
		// return PRODUCT;
	}

	private void export() {
		try {
			final XLSExporter exporter = XLSExporter.builder(listing.getDataProvider(), getExportProperties())
					.columnHeaderProvider(p -> listing.getColumnHeader(p))
					// .querySortsProvider(querySortsProvider) // TODO from listing
					// .localizationContext(LocalizationContext.require())
					.registry(PropertyXLSValueProviderRegistry.get())
					// .configuration(XLSConfiguration.builder().title("Export title").build()) // TODO config from UI
					.build();

			final File file = File.createTempFile("export", ".xlsx");

			OperationProgressDialog.builder(callback -> {
				try (OutputStream out = new FileOutputStream(file)) {
					exporter.export(out, callback);
				} catch (Exception e) {
					throw new OperationExecutionException(e);
				}
				return file.getName();
			}).abortable(true).text("Exporting...").execute(fileName -> {
				// download file
				UI.getCurrent().getPage()
						.open(FileDownloadServlet.build().fileName(fileName)
								.fileType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
								.removeAfterDowload().build());
			});

		} catch (IOException e) {
			e.printStackTrace();
			Notification.show("ERROR: " + e.getMessage());
		}
	}

	@Override
	public List<Component> getActions(ApplicationLayout applicationLayout) {
		return Arrays.asList(fltCategory.getComponent(),
				Components.button().text("Export").onClick(e -> export()).build());
	}

	@Override
	public Localizable getTitle() {
		return Localizable.of("Export");
	}

}
