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

import com.vaadin.flow.component.textfield.TextField;

import com.holonplatform.artisan.demo.root.Menu;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value = "tabs", layout = Menu.class)
public class TabLayoutPage extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public TabLayoutPage() {
		super();
		add(new RouterLink("Standard tabs", Tabs1Page.class));
		add(new RouterLink("Tabs flex grow and selection", Tabs2Page.class));
		add(new RouterLink("Full size tabs", Tabs3Page.class));
		add(new RouterLink("Disabled tabs", Tabs4Page.class));
		add(new RouterLink("Hidden tabs", Tabs5Page.class));
		add(new RouterLink("Show/hide tabs", Tabs6Page.class));
		add(new RouterLink("Tab icons", Tabs7Page.class));
		add(new RouterLink("Tab content provider", Tabs8Page.class));
		add(new RouterLink("Tab content selection", Tabs9Page.class));
		add(new RouterLink("Scrollable tab content", Tabs10Page.class));
		add(new RouterLink("Vertical tab content", Tabs11Page.class));
TextField textField = new TextField("Text field");
add(textField);
	}

}
