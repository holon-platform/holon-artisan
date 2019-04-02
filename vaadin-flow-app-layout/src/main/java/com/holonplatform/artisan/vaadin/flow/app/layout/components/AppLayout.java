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

import com.vaadin.flow.component.Component;
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
public class AppLayout extends PolymerTemplate<TemplateModel> implements HasStyle {

	private static final long serialVersionUID = 8796069540985099702L;

	private final HorizontalLayout appBarElementWrapper = new HorizontalLayout();
	private final HorizontalLayout appBarElementContainer = new HorizontalLayout();
	private final HorizontalLayout titleWrapper = new HorizontalLayout();
	private final Div menuElements;
	private final Div contentHolder;

	@Id("drawer")
	private AppDrawer drawer;

	@Id("app-bar-elements")
	private Div appBarElements;

	public AppLayout() {
		super();
		getStyle().set("width", "100%").set("height", "100%"); // TODO move in html

		titleWrapper.setHeight("100%");
		titleWrapper.setAlignItems(FlexComponent.Alignment.CENTER);
		titleWrapper.setPadding(false);
		titleWrapper.setMargin(false);
		titleWrapper.getElement().getStyle().set("flex", "1 1 100px").set("overflow", "hidden");
		titleWrapper.setWidth("0px");

		HorizontalLayout appBarContentHolder = new HorizontalLayout(titleWrapper, appBarElementWrapper);
		appBarContentHolder.setSizeFull();
		appBarContentHolder.setSpacing(false);
		appBarContentHolder.getElement().setAttribute("slot", "app-bar-content");

		menuElements = new Div();
		menuElements.setHeight("100%");
		menuElements.getElement().setAttribute("slot", "drawer-content");

		contentHolder = new Div();
		contentHolder.setHeight("100%");
		contentHolder.setWidth("100%");
		contentHolder.getElement().setAttribute("slot", "application-content");

		getElement().appendChild(appBarContentHolder.getElement(), menuElements.getElement(),
				contentHolder.getElement());
	}

	public AppDrawer getDrawer() {
		return drawer;
	}

	public void setContent(HasElement content) {
		this.contentHolder.getElement().removeAllChildren();
		if (content != null) {
			this.contentHolder.getElement().appendChild(content.getElement());
		}
	}

	public void setTitleComponent(Component component) {
		titleWrapper.removeAll();
		titleWrapper.add(component);
	}

	public void setAppBar(Component component) {
		appBarElementContainer.removeAll();
		appBarElementContainer.add(component);
	}

	public void setAppMenu(Component component) {
		menuElements.removeAll();
		menuElements.add(component);
	}

	public void setResponsiveWidth(String width) {
		getElement().setProperty("responsiveWidth", width);
	}

	public void toggleDrawer() {
		getDrawer().getElement().callFunction("toggle");
	}

	public void openDrawer() {
		getDrawer().getElement().callFunction("open");
	}

	public void closeDrawerIfNotPersistent() {
		getElement().callFunction("closeIfNotPersistent");
	}

	public void closeDrawer() {
		getDrawer().getElement().callFunction("close");
	}

}
