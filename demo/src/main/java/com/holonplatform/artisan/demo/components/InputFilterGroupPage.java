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

import static com.holonplatform.artisan.demo.model.Product.DESCRIPTION;
import static com.holonplatform.artisan.demo.model.Product.ID;
import static com.holonplatform.artisan.demo.model.Product.PRODUCT;
import static com.holonplatform.artisan.demo.model.Product.TARGET;

import org.springframework.beans.factory.annotation.Autowired;

import com.holonplatform.artisan.demo.root.Menu;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterComponent;
import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.vaadin.flow.components.Components;
import com.holonplatform.vaadin.flow.components.PropertyListing;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "filters2", layout = Menu.class)
public class InputFilterGroupPage extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final PropertyListing listing;
	private final InputFilterComponent filters;

	@Autowired
	public InputFilterGroupPage(Datastore datastore) {
		super();
		setSizeFull();

		// filters
		filters = InputFilterComponent.formLayout(PRODUCT).initializer(l -> {
			l.setWidth("100%");
			l.setResponsiveSteps(new ResponsiveStep("0px", 1), new ResponsiveStep("500px", 2),
					new ResponsiveStep("800px", 3));
		}).build();
		add(filters.getComponent());

		// listing
		listing = Components.listing.properties(PRODUCT).dataSource(datastore, TARGET)
				// filters
				.withQueryConfigurationProvider(() -> filters.getFilter().orElse(null))
				// sorts
				.withQuerySort(ID.asc()).withDefaultQuerySort(DESCRIPTION.asc())
				// size
				.fullWidth()
				//
				.build();

		// actions
		final HorizontalLayout actions = Components
				.hl().spacing().fullWidth().add(Components.button().text("Refresh").icon(VaadinIcon.REFRESH)
						.onClick(e -> listing.refresh()).build())
				.add(Components.button().text("Reset").icon(VaadinIcon.CLOSE_CIRCLE_O).onClick(e -> {
					filters.reset();
					listing.refresh();
				}).build()).build();

		add(filters.getComponent());
		add(actions);
		add(listing.getComponent());
		setFlexGrow(1, listing.getComponent());

	}

}
