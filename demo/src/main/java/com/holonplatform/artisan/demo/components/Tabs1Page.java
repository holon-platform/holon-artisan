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
import com.holonplatform.artisan.vaadin.flow.components.TabLayout;
import com.holonplatform.vaadin.flow.components.Components;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route(value = "tabs1", layout = Menu.class)
public class Tabs1Page extends Div {

	private static final long serialVersionUID = 1L;

	public Tabs1Page() {
		super();
		add(TabLayout.builder().fullWidth()
				// tab 1
				.withTab(Components.label().text("Tab one").build()).label("Tab 1").add()
				// tab 1
				.withTab(Components.label().text("Tab two").build()).label("Tab 2").add()
				// selected
				.build().getComponent());
	}

}
