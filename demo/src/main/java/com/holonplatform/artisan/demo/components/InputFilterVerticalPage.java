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
import static com.holonplatform.artisan.demo.model.Product.WITHDRAWN;

import org.springframework.beans.factory.annotation.Autowired;

import com.holonplatform.artisan.demo.root.Menu;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterComponent;
import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.vaadin.flow.components.Components;
import com.holonplatform.vaadin.flow.components.PropertyListing;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "filters3", layout = Menu.class)
public class InputFilterVerticalPage extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	@Autowired
	public InputFilterVerticalPage(Datastore datastore) {
		super();

		add(Components.h3().text("Vertical input filter with table").build());
		// vertical filters
		final InputFilterComponent filters = InputFilterComponent
				.verticalLayout(ID, DESCRIPTION, CATEGORY, UNIT_PRICE, WITHDRAWN).build();

		// listing
		PropertyListing listing = Components.listing.properties(PRODUCT).dataSource(datastore, TARGET)
				// filters
				.withQueryConfigurationProvider(() -> filters.getFilter().orElse(null))
				// sorts
				.withQuerySort(ID.asc()).withDefaultQuerySort(DESCRIPTION.asc())
				// size
				.fullWidth().build();

		// actions
		HorizontalLayout actions = Components
				.hl().spacing().add(Components.button().text("Refresh").icon(VaadinIcon.REFRESH)
						.onClick(e -> listing.refresh()).build())
				.add(Components.button().text("Reset").icon(VaadinIcon.CLOSE_CIRCLE_O).onClick(e -> {
					filters.reset();
					listing.refresh();
				}).build()).build();
		VerticalLayout vl = Components.vl().sizeUndefined().withoutPadding().withoutMargin()
				.add(filters.getComponent(), actions).build();
		HorizontalLayout hl = Components.hl().fullWidth().build();
		hl.add(vl);

		hl.add(listing.getComponent());
		hl.setFlexGrow(1, listing);
		add(hl);
	}
}
