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
package com.holonplatform.artisan.vaadin.flow.app.layout;

import java.io.Serializable;
import java.util.EventListener;
import java.util.Optional;
import java.util.Set;

import com.holonplatform.core.Registration;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasTheme;

/**
 * Application layout API.
 *
 * @since 1.0.2
 */
public interface ApplicationLayout extends HasTheme {

	/**
	 * Set the application layout content.
	 * @param content The content to set in the application layout viewport (may be null)
	 */
	void setContent(Component content);

	/**
	 * Add one or more components to the drawer slot.
	 * @param components The components to add
	 */
	void addToDrawer(Component... components);

	/**
	 * Remove one or more components from the application layout (any slot).
	 * @param components The components to remove
	 */
	void remove(Component... components);

	/**
	 * Get the application header <em>title</em> slot.
	 * @return The application header <em>title</em> slot {@link HasComponents} reference
	 */
	HasComponents getHeaderTitle();

	/**
	 * Get the application header <em>context actions</em> slot.
	 * @return The application header <em>context actions</em> slot {@link HasComponents} reference
	 */
	HasComponents getHeaderContextActions();

	/**
	 * Get the application header <em>actions</em> slot.
	 * @return The application header <em>actions</em> slot {@link HasComponents} reference
	 */
	HasComponents getHeaderActions();

	/**
	 * Adds theme variants to the component.
	 * @param variants theme variants to add
	 */
	void addThemeVariants(AppLayoutVariant... variants);

	/**
	 * Removes theme variants from the component.
	 * @param variants theme variants to remove
	 */
	void removeThemeVariants(AppLayoutVariant... variants);

	/**
	 * Get the theme variants.
	 * @return the theme variants, empty if none
	 */
	Set<AppLayoutVariant> getThemeVariants();

	/**
	 * Get whether the app layout drawer is opened.
	 * @return whether the app layout drawer is opened
	 */
	boolean isDrawerOpened();

	/**
	 * Set the app layout drawer opened or closed.
	 * @param drawerOpened whether the app layout drawer is opened
	 */
	void setDrawerOpened(boolean drawerOpened);

	/**
	 * Get whether the app layout is in overlay mode, i.e. the drawer opens as an overlay on the app layout.
	 * @return whether the app layout is in overlay mode
	 */
	boolean isOverlay();

	/**
	 * Get the whether to automatically close the app layout drawer after a routing event if the app layout is in
	 * overlay mode.
	 * <p>
	 * Default is <code>true</code>.
	 * </p>
	 * @return whether to automatically close the app layout drawer after a routing event
	 */
	boolean isAutoCloseDrawer();

	/**
	 * Set the whether to automatically close the app layout drawer after a routing event if the app layout is in
	 * overlay mode.
	 * <p>
	 * Default is <code>true</code>.
	 * </p>
	 * @param autoCloseDrawer whether to automatically close the app layout drawer after a routing event
	 */
	void setAutoCloseDrawer(boolean autoCloseDrawer);

	/**
	 * Add an {@link ApplicationContentChangeListener} to listen for application content changes.
	 * @param listener The listener to add (not null)
	 * @return Listener handler
	 */
	Registration addApplicationContentChangeListener(ApplicationContentChangeListener listener);

	/**
	 * Add an {@link OverlayStateChangeEventListener} to listen for overlay state changes.
	 * @param listener The listener to add (not null)
	 * @return Listener handler
	 */
	Registration addOverlayStateChangeEventListener(OverlayStateChangeEventListener listener);

	// ------- events

	/**
	 * A listener to listen for application content change events.
	 */
	@FunctionalInterface
	public interface ApplicationContentChangeListener extends EventListener, Serializable {

		/**
		 * Invoked when the application content changes.
		 * @param event The application content change event
		 */
		void applicationContentChange(ApplicationContentChangeEvent event);

	}

	/**
	 * Application content change event.
	 */
	public interface ApplicationContentChangeEvent extends Serializable {

		/**
		 * Get the {@link ApplicationLayout} which triggered the event.
		 * @return Event source
		 */
		ApplicationLayout getApplicationLayout();

		/**
		 * Get the new application layout content, if available.
		 * @return Optional application layout content
		 */
		Optional<Component> getContent();

	}

	/**
	 * A listener to listen for app layout overlay state change events.
	 */
	@FunctionalInterface
	public interface OverlayStateChangeEventListener extends EventListener, Serializable {

		/**
		 * Invoked when the app layout overlay state changes.
		 * @param event The app layout overlay state change event
		 */
		void overlayStateChange(OverlayStateChangeEvent event);

	}

	/**
	 * App layout overlay state change event.
	 */
	public interface OverlayStateChangeEvent extends Serializable {

		/**
		 * Get the {@link ApplicationLayout} which triggered the event.
		 * @return Event source
		 */
		ApplicationLayout getApplicationLayout();

		/**
		 * Get whether the app layout is in <em>overlay</em> state, i.e. the drawer is hidden by default and opens as an
		 * overlay.
		 * @return Whether the app layout is in <em>overlay</em> state
		 */
		boolean isOverlay();

	}

}
