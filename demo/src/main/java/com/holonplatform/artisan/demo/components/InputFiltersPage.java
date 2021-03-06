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

import com.holonplatform.artisan.demo.root.Menu;
import com.holonplatform.vaadin.flow.components.Components;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value = "filters", layout = Menu.class)
public class InputFiltersPage extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public InputFiltersPage() {
		super();
		add(new RouterLink("Input filter components", InputFilterComponentsPage.class));
		add(new RouterLink("Input filter group", InputFilterGroupPage.class));
		add(new RouterLink("Input filter vertical", InputFilterVerticalPage.class));
		add(new RouterLink("Input filter horizontal", InputFilterHorizontalPage.class));
		add(Components.button().text("Input filter window").withThemeVariants(ButtonVariant.LUMO_TERTIARY)
				.onClick(evt -> {
					InputFilterWindow wnd = new InputFilterWindow();
					wnd.open();
				}).build());
	}

}
