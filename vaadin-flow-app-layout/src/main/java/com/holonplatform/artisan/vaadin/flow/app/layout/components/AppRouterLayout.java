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

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.holonplatform.artisan.core.utils.Obj;
import com.holonplatform.artisan.vaadin.flow.app.layout.AppLayoutVariant;
import com.holonplatform.artisan.vaadin.flow.app.layout.ApplicationLayout;
import com.holonplatform.artisan.vaadin.flow.app.layout.internal.DefaultApplicationContentChangeEvent;
import com.holonplatform.artisan.vaadin.flow.app.layout.internal.DefaultOveralyStateChangeEvent;
import com.holonplatform.core.Registration;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.PropertyChangeEvent;

@HtmlImport("frontend://com/holonplatform/artisan/vaadin/flow/app/layout/app-layout-styles.html")
public class AppRouterLayout extends AppLayout implements ApplicationLayout {

	private static final long serialVersionUID = 8726407799332318387L;

	private final HorizontalLayout navbarStart;
	private final HorizontalLayout navbarContent;
	private final HorizontalLayout navbarEnd;

	private final List<ApplicationContentChangeListener> applicationContentChangeListeners = new LinkedList<>();
	private final List<OverlayStateChangeEventListener> overlayStateChangeEventListeners = new LinkedList<>();

	private boolean autoCloseDrawer = true;
	
	public AppRouterLayout() {
		this(true);
	}

	public AppRouterLayout(boolean touchOptimized) {
		super();
		getElement().setAttribute("app-router-layout", "");

		getElement().addPropertyChangeListener("overlay", evt -> onOverlayChangeEvent(evt));

		final DrawerToggle toggle = new DrawerToggle();
		toggle.getElement().setAttribute("app-router-layout-toggle", "");
		toggle.getElement().setAttribute("hide-when-not-overlay", "");
		addToNavbar(toggle);

		navbarStart = createNavbarSlot();
		navbarStart.getElement().setAttribute("part", "navbar-start");
		navbarContent = createNavbarSlot();
		navbarContent.getElement().setAttribute("part", "navbar-content");
		navbarContent.addClassName("navbar-content");
		navbarEnd = createNavbarSlot();
		navbarEnd.getElement().setAttribute("part", "navbar-end");

		addToNavbar(navbarStart);
		addToNavbar(touchOptimized, navbarContent);
		addToNavbar(navbarEnd);

	}

	private static HorizontalLayout createNavbarSlot() {
		final HorizontalLayout slot = new HorizontalLayout();
		slot.addClassName("navbar-slot");
		slot.setMargin(false);
		slot.setPadding(false);
		slot.setSpacing(true);
		slot.setAlignItems(Alignment.CENTER);
		return slot;
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		getUI().ifPresent(ui -> ui.addAfterNavigationListener(event -> {
			if (isAutoCloseDrawer()) {
				if (isOverlay() && isDrawerOpened()) {
					setDrawerOpened(false);
				}
			}
		}));
	}

	@Override
	public boolean isAutoCloseDrawer() {
		return autoCloseDrawer;
	}

	@Override
	public void setAutoCloseDrawer(boolean autoCloseDrawer) {
		this.autoCloseDrawer = autoCloseDrawer;
	}

	@Override
	public void setContent(Component content) {
		super.setContent(content);
		// fire listeners
		final ApplicationContentChangeEvent event = new DefaultApplicationContentChangeEvent(this, content);
		applicationContentChangeListeners.forEach(l -> l.applicationContentChange(event));
	}

	@Override
	public HasComponents getNavbarStart() {
		return navbarStart;
	}

	@Override
	public HasComponents getNavbarContent() {
		return navbarContent;
	}

	@Override
	public HasComponents getNavbarEnd() {
		return navbarEnd;
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

	@Override
	public Set<AppLayoutVariant> getThemeVariants() {
		return getThemeNames().stream().map(n -> AppLayoutVariant.fromThemeName(n)).filter(v -> v.isPresent())
				.map(v -> v.get()).collect(Collectors.toSet());
	}

	@Override
	public Registration addApplicationContentChangeListener(ApplicationContentChangeListener listener) {
		Obj.argumentNotNull(listener, "Listener must be not null");
		applicationContentChangeListeners.add(listener);
		return () -> applicationContentChangeListeners.remove(listener);
	}

	@Override
	public Registration addOverlayStateChangeEventListener(OverlayStateChangeEventListener listener) {
		Obj.argumentNotNull(listener, "Listener must be not null");
		overlayStateChangeEventListeners.add(listener);
		return () -> overlayStateChangeEventListeners.remove(listener);
	}

	/**
	 * Invoked when the overlay property value changes.
	 * @param event Property value change event
	 */
	protected void onOverlayChangeEvent(PropertyChangeEvent event) {
		final boolean overlay = getBooleanValue(event.getValue());
		// ensure drawer opened when not overlay
		/*// TODO check
		if (isAutoCloseDrawer() && !overlay) {
			if (!isDrawerOpened()) {
				setDrawerOpened(true);
			}
		} */
		// fire listeners
		final OverlayStateChangeEvent evt = new DefaultOveralyStateChangeEvent(this, overlay);
		overlayStateChangeEventListeners.forEach(l -> l.overlayStateChange(evt));
	}

	private static boolean getBooleanValue(Serializable value) {
		if (value != null) {
			if (Obj.isBoolean(value.getClass())) {
				return (Boolean) value;
			}
			if (Obj.isString(value.getClass())) {
				return ((String) value).trim().equalsIgnoreCase("true");
			}
		}
		return false;
	}

}
