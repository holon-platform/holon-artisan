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
import static com.holonplatform.artisan.demo.model.Product.DATE;
import static com.holonplatform.artisan.demo.model.Product.DESCRIPTION;
import static com.holonplatform.artisan.demo.model.Product.ID;
import static com.holonplatform.artisan.demo.model.Product.PRODUCT;
import static com.holonplatform.artisan.demo.model.Product.TARGET;
import static com.holonplatform.artisan.demo.model.Product.UNIT_PRICE;
import static com.holonplatform.artisan.demo.model.Product.WITHDRAWN;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.holonplatform.artisan.demo.root.Menu;
import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterComponent;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.core.property.SetPathProperty;
import com.holonplatform.vaadin.flow.components.Components;
import com.holonplatform.vaadin.flow.components.PropertyListing;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "filters2", layout = Menu.class)
public class InputFilterGroupPage extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private static final SetPathProperty<Long> MULTI = SetPathProperty.create("multi", Long.class);

	private final PropertyListing listing;
	private final InputFilterComponent filters;

	@Autowired
	public InputFilterGroupPage(Datastore datastore) {
		super();
		setSizeFull();

		final CheckboxGroup<Long> rbg = new CheckboxGroup<>();
		rbg.setLabel("Multi");
		rbg.setItems(1L, 2L, 3L, 4L, 5L);
		final InputFilter<Set<Long>> mf = InputFilter.from(rbg, value -> {
			if (value != null && !value.isEmpty()) {
				return ID.in(value);
			}
			return null;
		});

		// filters
		filters = InputFilterComponent.formLayout(ID, DESCRIPTION, CATEGORY, UNIT_PRICE, WITHDRAWN, DATE, MULTI)
				.initializer(l -> {
					l.setWidth("100%");
					l.setResponsiveSteps(new ResponsiveStep("0px", 1), new ResponsiveStep("500px", 2),
							new ResponsiveStep("800px", 3));
				}).withPostProcessor((p, i) -> {
					if (DATE == p) {
						i.hasEnabled().ifPresent(e -> e.setEnabled(false));
					}
				}).withValueChangeListener(CATEGORY, evt -> {
					if (evt.getValue() != null && !evt.getValue().trim().equals("")) {
						evt.getInputGroup().getInputFilter(DATE).ifPresent(i -> {
							i.hasEnabled().ifPresent(e -> e.setEnabled(true));
							i.getFilterOperatorSelect()
									.ifPresent(select -> select.setValue(InputFilterOperator.BETWEEN));
							i.getInputFilter().ifPresent(input -> input.setValue(LocalDate.now()));
							i.getBetweenInputFilter().ifPresent(input -> input.setValue(LocalDate.now().plusDays(1)));
						});
					} else {
						evt.getInputGroup().getInputFilter(DATE).ifPresent(i -> {
							i.clear();
							i.hasEnabled().ifPresent(e -> e.setEnabled(false));
						});
					}
				}).bind(MULTI, mf).build();

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
