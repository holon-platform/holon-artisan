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
package com.holonplatform.artisan.demo.components;

import static com.holonplatform.artisan.demo.model.Product.ITEM1;
import static com.holonplatform.artisan.demo.model.Product.ITEM2;
import static com.holonplatform.artisan.demo.model.Product.ITEM3;
import static com.holonplatform.artisan.demo.model.Product.ITEM4;
import static com.holonplatform.artisan.demo.model.Product.ITEM5;
import static com.holonplatform.artisan.demo.model.Product.PRODUCT;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.holonplatform.artisan.core.exceptions.OperationExecutionException;
import com.holonplatform.artisan.demo.root.Menu;
import com.holonplatform.artisan.vaadin.flow.components.OperationProgressDialog;
import com.holonplatform.artisan.vaadin.flow.export.xls.PropertyXLSValueProviderRegistry;
import com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration;
import com.holonplatform.vaadin.flow.components.Components;
import com.holonplatform.vaadin.flow.components.PropertyListing;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "export", layout = Menu.class)
public class ExportPage extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final PropertyListing listing;

	public ExportPage() {
		super();
		setSizeFull();

		final HorizontalLayout top = new HorizontalLayout();
		top.setWidth("100%");
		top.add(Components.button().text("Export").onClick(e -> export()).build());
		add(top);

		listing = Components.listing.properties(PRODUCT).items(ITEM1, ITEM2, ITEM3, ITEM4, ITEM5).fullSize().build();
		add(listing.getComponent());
		setFlexGrow(1, listing.getComponent());
	}

	private void export() {
		try {
			final XLSExporter exporter = XLSExporter.builder(listing.getDataProvider(), PRODUCT)
					.columnHeaderProvider(p -> listing.getColumnHeader(p))
					// .localizationContext(LocalizationContext.require())
					.registry(PropertyXLSValueProviderRegistry.get())
					.configuration(XLSConfiguration.builder().title("Export title").build()) // TODO config from UI
					.build();

			final File file = File.createTempFile("export", null);

			OperationProgressDialog.builder(callback -> {
				try (OutputStream out = new FileOutputStream(file)) {
					exporter.export(out, callback);
				} catch (Exception e) {
					throw new OperationExecutionException(e);
				}
				return file.getName();
			}).abortable(true).text("Exporting...").execute(fileName -> {
				// TODO download file
				Notification.show("Exported: " + fileName);
			});

		} catch (IOException e) {
			e.printStackTrace();
			Notification.show("ERROR: " + e.getMessage());
		}
	}

}
