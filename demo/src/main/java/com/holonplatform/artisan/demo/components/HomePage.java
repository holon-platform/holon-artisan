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

import java.util.Arrays;
import java.util.List;

import com.holonplatform.artisan.demo.root.HasViewActions;
import com.holonplatform.artisan.demo.root.HasViewTitle;
import com.holonplatform.artisan.demo.root.Menu;
import com.holonplatform.artisan.vaadin.flow.app.layout.AppLayoutVariant;
import com.holonplatform.artisan.vaadin.flow.app.layout.ApplicationLayout;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.vaadin.flow.components.Components;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = Menu.class)
public class HomePage extends VerticalLayout implements HasViewTitle, HasViewActions {

	private static final long serialVersionUID = 1L;

	public HomePage() {
		super();
		add(new H1("Welcome to Holon Artisan Demo"));

		NativeLabel lblText = new NativeLabel();
		lblText.getElement().setProperty("innerHTML",
				"Welcome to the <b>Artisan Demo Project</b> of the Holon Platform.</br> Here you can find all custom components developed by our team "
						+ "to give you a set of new useful objects to improve your web apps...");
		add(lblText);
		add(new H3("Check out platform website:"));
		add(new Anchor("https://holon-platform.com", "https://holon-platform.com"));
		add(new H5("Holon Platform on GitHub:"));
		add(new Anchor("https://github.com/holon-platform", "https://github.com/holon-platform"));

	}

	@Override
	public List<Component> getActions(ApplicationLayout applicationLayout) {
		return Arrays.asList(Components.button().icon(VaadinIcon.PADDING_RIGHT).text("Normal")
				.withThemeVariants(ButtonVariant.LUMO_ICON).onClick(e -> {
					applicationLayout.removeThemeVariants(AppLayoutVariant.SMALL);
				}).build(), Components.button().icon(VaadinIcon.PADDING_LEFT).text("Small")
						.withThemeVariants(ButtonVariant.LUMO_ICON).onClick(e -> {
							applicationLayout.addThemeVariants(AppLayoutVariant.SMALL);
						}).build());
	}

	@Override
	public Localizable getTitle() {
		return Localizable.of("Demo");
	}
}
