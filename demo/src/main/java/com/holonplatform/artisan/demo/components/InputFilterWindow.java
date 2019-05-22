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
import static com.holonplatform.artisan.demo.model.Product.UNIT_PRICE;
import static com.holonplatform.artisan.demo.model.Product.WITHDRAWN;

import com.holonplatform.artisan.vaadin.flow.components.InputFilterComponent;
import com.holonplatform.artisan.vaadin.flow.components.internal.DefaultWindow;
import com.holonplatform.vaadin.flow.components.Components;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class InputFilterWindow extends DefaultWindow {

	private static final long serialVersionUID = 1L;

	public InputFilterWindow() {
		super();
		setSizeFull();
		setTitle("Window filters");
		setClosable(true);

		add(Components.h3().text("Form input filter").build());
		// vertical filters
		final InputFilterComponent filters1 = InputFilterComponent
				.formLayout(ID, DESCRIPTION, CATEGORY, UNIT_PRICE, WITHDRAWN).build();

		add(filters1.getComponent());
		add(new Hr());

		add(Components.h3().text("Horizontal input filter").build());
		// vertical filters
		final InputFilterComponent filters2 = InputFilterComponent.horizontalLayout(ID, DESCRIPTION, CATEGORY).build();
		((HorizontalLayout) filters2.getComponent()).setSpacing(true);

		add(filters2.getComponent());
		add(new Hr());

		add(Components.h3().text("Vertical input filter").build());
		// vertical filters
		final InputFilterComponent filters3 = InputFilterComponent
				.verticalLayout(ID, DESCRIPTION, CATEGORY, UNIT_PRICE, WITHDRAWN).build();

		add(filters3.getComponent());

	}
}
