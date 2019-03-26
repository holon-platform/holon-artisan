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

import static com.holonplatform.artisan.demo.model.Product.ITEM1;
import static com.holonplatform.artisan.demo.model.Product.ITEM2;
import static com.holonplatform.artisan.demo.model.Product.ITEM3;
import static com.holonplatform.artisan.demo.model.Product.ITEM4;
import static com.holonplatform.artisan.demo.model.Product.ITEM5;
import static com.holonplatform.artisan.demo.model.Product.PRODUCT;

import com.holonplatform.artisan.demo.root.Menu;
import com.holonplatform.artisan.vaadin.flow.components.TabLayout;
import com.holonplatform.vaadin.flow.components.Components;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.Route;

@Route(value = "tabs7", layout = Menu.class)
public class Tabs7Page extends Div {

	private static final long serialVersionUID = 1L;

	public Tabs7Page() {
		super();
		setSizeFull();
		add(TabLayout.builder().fullSize()
				// tab 1
				.withTab(Components.listing.properties(PRODUCT).items(ITEM1, ITEM2, ITEM3).fullSize().build())
				.label("Products 1").flexGrow(1).withComponent(VaadinIcon.CALENDAR_BRIEFCASE.create()).add()
				// tab 1
				.withTab(Components.listing.properties(PRODUCT).items(ITEM4, ITEM5).fullSize().build())
				.label("Products 2").flexGrow(1).withComponent(VaadinIcon.FEMALE.create()).add()
				// theme
				.withThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS).build().getComponent());
	}

}
