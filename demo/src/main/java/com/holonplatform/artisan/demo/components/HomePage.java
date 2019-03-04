/*
 * Copyright 2016-2018 Axioma srl.
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
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = Menu.class)
public class HomePage extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public HomePage() {
		super();
		add(new H1("Welcome to Holon Artisan Demo"));

		Label lblText = new Label();
		lblText.getElement().setProperty("innerHTML",
				"Welcome to the <b>Artisan Demo Project</b> of the Holon Platform.</br> Here you can find all custom components developed by our team "
						+ "to give you a set of new useful objects to improve your web apps...");
		add(lblText);
		add(new H3("Check out platform website:"));
		add(new Anchor("https://holon-platform.com", "https://holon-platform.com"));
		add(new H5("Holon Platform on GitHub:"));
		add(new Anchor("https://github.com/holon-platform", "https://github.com/holon-platform"));

	}
}
