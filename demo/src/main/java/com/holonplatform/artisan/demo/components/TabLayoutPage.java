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
	}

}
