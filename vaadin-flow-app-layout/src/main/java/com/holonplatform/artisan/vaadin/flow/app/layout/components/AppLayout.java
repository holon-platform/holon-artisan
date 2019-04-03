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
package com.holonplatform.artisan.vaadin.flow.app.layout.components;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.holonplatform.artisan.vaadin.flow.app.layout.AppLayoutVariant;
import com.holonplatform.artisan.vaadin.flow.app.layout.ApplicationLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("holon-app-layout")
@HtmlImport("frontend://src/com/holonplatform/artisan/vaadin/flow/app/layout/app-layout.html")
@HtmlImport("frontend://src/com/holonplatform/artisan/vaadin/flow/app/layout/app-layout-styles.html")
public class AppLayout extends PolymerTemplate<TemplateModel> implements ApplicationLayout, HasStyle {

	private static final long serialVersionUID = 8796069540985099702L;

	private final HorizontalLayout titleSlot;
	private final HorizontalLayout contextActionsSlot;
	private final HorizontalLayout actionsSlot;

	@Id("drawer")
	private AppDrawer drawer;

	private final Div drawerContent;
	private final Div applicationContent;

	public AppLayout() {
		super();

		titleSlot = new HorizontalLayout();
		titleSlot.setPadding(false);
		titleSlot.setMargin(false);
		titleSlot.setHeightFull();
		titleSlot.setAlignItems(FlexComponent.Alignment.CENTER);
		titleSlot.getElement().setAttribute("slot", "header-title");

		actionsSlot = new HorizontalLayout();
		actionsSlot.setPadding(false);
		actionsSlot.setMargin(false);
		actionsSlot.setHeightFull();
		actionsSlot.setAlignItems(FlexComponent.Alignment.CENTER);
		actionsSlot.getElement().setAttribute("slot", "header-actions");

		contextActionsSlot = new HorizontalLayout();
		contextActionsSlot.setPadding(false);
		contextActionsSlot.setMargin(false);
		contextActionsSlot.setHeightFull();
		contextActionsSlot.setAlignItems(FlexComponent.Alignment.CENTER);
		contextActionsSlot.getElement().setAttribute("slot", "header-context-actions");

		drawerContent = new Div();
		drawerContent.setHeight("100%");
		drawerContent.getElement().setAttribute("slot", "drawer-content");

		applicationContent = new Div();
		applicationContent.setHeight("100%");
		applicationContent.setWidth("100%");
		applicationContent.getElement().setAttribute("slot", "application-content");

		getElement().appendChild(titleSlot.getElement(), actionsSlot.getElement(), contextActionsSlot.getElement(),
				drawerContent.getElement(), applicationContent.getElement());
	}

	protected AppDrawer getDrawer() {
		return drawer;
	}

	@Override
	public void setContent(HasElement content) {
		this.applicationContent.getElement().removeAllChildren();
		if (content != null) {
			this.applicationContent.getElement().appendChild(content.getElement());
		}
	}

	@Override
	public void setResponsiveWidth(String width) {
		getElement().setProperty("responsiveWidth", width);
	}

	@Override
	public void toggleDrawer() {
		getDrawer().getElement().callFunction("toggle");
	}

	@Override
	public void openDrawer() {
		getDrawer().getElement().callFunction("open");
	}

	@Override
	public void closeDrawer() {
		getDrawer().getElement().callFunction("close");
	}

	@Override
	public void closeDrawerIfNotPersistent() {
		getElement().callFunction("closeIfNotPersistent");
	}

	@Override
	public void setDrawerContent(Component component) {
		drawerContent.removeAll();
		if (component != null) {
			drawerContent.add(component);
		}
	}

	@Override
	public HasComponents getHeaderTitle() {
		return titleSlot;
	}

	@Override
	public HasComponents getHeaderContextActions() {
		return contextActionsSlot;
	}

	@Override
	public HasComponents getHeaderActions() {
		return actionsSlot;
	}

	@Override
	public void addThemeVariants(AppLayoutVariant... variants) {
		getThemeNames().addAll(Stream.of(variants).map(AppLayoutVariant::getVariantName).collect(Collectors.toList()));
	}

	@Override
	public void removeThemeVariants(AppLayoutVariant... variants) {
		getThemeNames()
				.removeAll(Stream.of(variants).map(AppLayoutVariant::getVariantName).collect(Collectors.toList()));
	}

}
