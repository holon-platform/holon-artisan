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
package com.holonplatform.artisan.vaadin.flow.app.layout.routing;

import com.holonplatform.artisan.core.utils.Obj;
import com.holonplatform.artisan.vaadin.flow.app.layout.AppLayoutVariant;
import com.holonplatform.artisan.vaadin.flow.app.layout.ApplicationLayout;
import com.holonplatform.artisan.vaadin.flow.app.layout.components.AppLayout;
import com.holonplatform.artisan.vaadin.flow.app.layout.events.AppLayoutNarrowStateChangeListener;
import com.holonplatform.artisan.vaadin.flow.app.layout.events.ApplicationContentChangeListener;
import com.holonplatform.core.Registration;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.RouterLayout;

/**
 * A {@link RouterLayout} which uses an {@link AppLayout} as application layout component.
 * 
 * @since 1.0.2
 */
public class AppRouterLayout extends Composite<Div> implements ApplicationLayout, RouterLayout {

	private static final long serialVersionUID = -6642606449786472863L;

	private final ApplicationLayout applicationLayout;

	public AppRouterLayout() {
		this(new AppLayout());
	}

	public <A extends Component & ApplicationLayout> AppRouterLayout(A appLayout) {
		super();
		Obj.argumentNotNull(appLayout, "AppLayout must be not null");
		this.applicationLayout = appLayout;
		getContent().setId("application-router-layout");
		getContent().setSizeFull();
		getContent().add(appLayout);
	}

	protected ApplicationLayout getApplicationLayout() {
		return applicationLayout;
	}

	@Override
	public void showRouterLayoutContent(HasElement content) {
		setContent(content);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		// after navigation listener to close the drawer if not persistent
		getUI().ifPresent(ui -> ui.addAfterNavigationListener(event -> {
			closeDrawerIfNotPersistent();
		}));
	}

	@Override
	public void setResponsiveWidth(String width) {
		getApplicationLayout().setResponsiveWidth(width);
	}

	@Override
	public void setContent(HasElement content) {
		getApplicationLayout().setContent(content);
	}

	@Override
	public void toggleDrawer() {
		getApplicationLayout().toggleDrawer();
	}

	@Override
	public void openDrawer() {
		getApplicationLayout().openDrawer();
	}

	@Override
	public void closeDrawer() {
		getApplicationLayout().closeDrawer();
	}

	@Override
	public void closeDrawerIfNotPersistent() {
		getApplicationLayout().closeDrawerIfNotPersistent();
	}

	@Override
	public void setDrawerContent(Component component) {
		getApplicationLayout().setDrawerContent(component);
	}

	@Override
	public HasComponents getHeaderTitle() {
		return getApplicationLayout().getHeaderTitle();
	}

	@Override
	public HasComponents getHeaderContextActions() {
		return getApplicationLayout().getHeaderContextActions();
	}

	@Override
	public HasComponents getHeaderActions() {
		return getApplicationLayout().getHeaderActions();
	}

	@Override
	public void addThemeVariants(AppLayoutVariant... variants) {
		getApplicationLayout().addThemeVariants(variants);
	}

	@Override
	public void removeThemeVariants(AppLayoutVariant... variants) {
		getApplicationLayout().removeThemeVariants(variants);
	}

	@Override
	public void addThemeName(String themeName) {
		getApplicationLayout().addThemeName(themeName);
	}

	@Override
	public boolean removeThemeName(String themeName) {
		return getApplicationLayout().removeThemeName(themeName);
	}

	@Override
	public void setThemeName(String themeName) {
		getApplicationLayout().setThemeName(themeName);
	}

	@Override
	public String getThemeName() {
		return getApplicationLayout().getThemeName();
	}

	@Override
	public ThemeList getThemeNames() {
		return getApplicationLayout().getThemeNames();
	}

	@Override
	public void setThemeName(String themeName, boolean set) {
		getApplicationLayout().setThemeName(themeName, set);
	}

	@Override
	public boolean hasThemeName(String themeName) {
		return getApplicationLayout().hasThemeName(themeName);
	}

	@Override
	public void addThemeNames(String... themeNames) {
		getApplicationLayout().addThemeNames(themeNames);
	}

	@Override
	public void removeThemeNames(String... themeNames) {
		getApplicationLayout().removeThemeNames(themeNames);
	}

	@Override
	public Registration addApplicationContentChangeListener(ApplicationContentChangeListener listener) {
		return getApplicationLayout().addApplicationContentChangeListener(listener);
	}

	@Override
	public Registration addAppLayoutNarrowStateChangeListener(AppLayoutNarrowStateChangeListener listener) {
		return getApplicationLayout().addAppLayoutNarrowStateChangeListener(listener);
	}

}
