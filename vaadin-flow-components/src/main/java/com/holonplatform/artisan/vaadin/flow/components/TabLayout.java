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
package com.holonplatform.artisan.vaadin.flow.components;

import java.io.Serializable;
import java.util.EventListener;
import java.util.List;
import java.util.Optional;

import com.holonplatform.artisan.vaadin.flow.components.builders.TabsBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.HorizontalTabsBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.VerticalTabsBuilder;
import com.holonplatform.core.Registration;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.vaadin.flow.components.HasComponent;
import com.holonplatform.vaadin.flow.i18n.LocalizationProvider;
import com.vaadin.flow.component.Component;

/**
 * A component which provides tabbed content.
 * <p>
 * The component includes a tabs bar (either in vertical or horizontal mode) and the tab content area, which is used to
 * show the currently selected tab content.
 * </p>
 * <p>
 * This component is a {@link HasComponent}, the {@link HasComponent#getComponent()} method should be used to obtain the
 * concrete {@link Component}.
 * </p>
 * 
 * @since 1.0.0
 */
public interface TabLayout extends HasComponent {

	/**
	 * Get all the tabs.
	 * @return The tabs list, in the display order. An empty list if none.
	 */
	List<Tab> getTabs();

	/**
	 * Get the first tab, if avaialble.
	 * @return Optional first tab
	 */
	default Optional<Tab> getFirstTab() {
		return getTabs().stream().findFirst();
	}

	/**
	 * Get the tab at given index (0-based), if available.
	 * @param index The index of the tab
	 * @return Optional tab, empty if no tab is available at given index
	 */
	Optional<Tab> getTabAt(int index);

	/**
	 * Get the selected tab, if any.
	 * @return Optional selected tab
	 */
	Optional<Tab> getSelectedTab();

	/**
	 * Get the selected tab index, if any.
	 * @return Optional selected tab index
	 */
	Optional<Integer> getSelectedTabIndex();

	/**
	 * Selects the given tab.
	 * <p>
	 * The tab can be selected if it's visible and enabled.
	 * </p>
	 * @param tab The tab to select, <code>null</code> to unselect all
	 * @return <code>true</code> if the tab was selected, <code>false</code> otherwise
	 * @throws IllegalArgumentException If the given tab is not part of this layout
	 */
	boolean setSelectedTab(Tab tab);

	/**
	 * Selects the tab at given index, if available.
	 * <p>
	 * The tab can be selected if it's visible and enabled.
	 * </p>
	 * @return <code>true</code> if the tab was selected, <code>false</code> otherwise
	 * @param index The index of the tab to select
	 */
	boolean setSelectedTabIndex(int index);

	/**
	 * Add a listener for tab change events.
	 * @param listener The listener to add (not null)
	 * @return a handle that can be used for removing the listener
	 */
	Registration addSelectedTabChangeListener(SelectedTabChangeListener listener);

	// -------

	/**
	 * Get a builder to create a {@link TabLayout}.
	 * <p>
	 * Alias for {@link #horizontal()}.
	 * </p>
	 * @return A new {@link TabsBuilder}
	 */
	static TabsBuilder builder() {
		return horizontal();
	}

	/**
	 * Get a builder to create a horizontal {@link TabLayout}.
	 * @return A new horizontal {@link TabsBuilder}
	 */
	static TabsBuilder horizontal() {
		return new HorizontalTabsBuilder();
	}

	/**
	 * Get a builder to create a vertical {@link TabLayout}.
	 * @return A new vertical {@link TabsBuilder}
	 */
	static TabsBuilder vertical() {
		return new VerticalTabsBuilder();
	}

	/**
	 * Function to provide the content of a tab.
	 */
	@FunctionalInterface
	public interface TabContent extends Serializable {

		/**
		 * Get the content if the tab.
		 * <p>
		 * This method is invoked by the tabs container each time the bound tab is selected.
		 * </p>
		 * @param tab The tab for which to provide the content
		 * @return The tab content {@link Component}
		 */
		Component getContent(Tab tab);

	}

	/**
	 * Tab representation.
	 * <p>
	 * Each tab is composed by a tab selector with label support and the tab content {@link Component}.
	 * </p>
	 */
	public interface Tab extends Serializable {

		/**
		 * Get the label of the tab.
		 * @return the tab label
		 */
		String getLabel();

		/**
		 * Set the {@link Localizable} label of the tab.
		 * @param label Localizable tab label
		 * @see LocalizationProvider
		 */
		void setLabel(Localizable label);

		/**
		 * Set the label of the tab.
		 * @param label The tab label to set
		 */
		default void setLabel(String label) {
			setLabel((label == null) ? null : Localizable.builder().message(label).build());
		}

		/**
		 * Set the label of the tab using a localizable <code>messageCode</code>.
		 * @param defaultLabel Default tab label if no translation is available for given <code>messageCode</code>
		 * @param messageCode Tab label translation message key
		 * @param arguments Optional translation arguments
		 * @see LocalizationProvider
		 */
		default void setLabel(String defaultLabel, String messageCode, Object... arguments) {
			setLabel(Localizable.builder().message((defaultLabel == null) ? "" : defaultLabel).messageCode(messageCode)
					.messageArguments(arguments).build());
		}

		/**
		 * Get whether the tab is visible.
		 * @return whether the tab is visible
		 */
		boolean isVisible();

		/**
		 * Set whether the tab is visible.
		 * @param visible <code>true</code> to show the tab, <code>false</code> to hide it
		 */
		void setVisible(boolean visible);

		/**
		 * Get whether the tab is enabled.
		 * @return whether the tab is enabled
		 */
		boolean isEnabled();

		/**
		 * Set whether the tab is enabled.
		 * @param enabled <code>true</code> to enable the tab, <code>false</code> to disable it
		 */
		void setEnabled(boolean enabled);

		/**
		 * Get whether this tab is the selected tab.
		 * @return whether this tab is the selected tab
		 */
		boolean isSelected();

	}

	/**
	 * Listener for tab selection change events.
	 */
	@FunctionalInterface
	public interface SelectedTabChangeListener extends EventListener, Serializable {

		/**
		 * Tab selection changed.
		 * @param event The tab selection change event
		 */
		void onSelectedTabChange(SelectedTabChangeEvent event);

	}

	/**
	 * Event to provide selected tab change information.
	 */
	public interface SelectedTabChangeEvent extends Serializable {

		/**
		 * Get the parent tab layout.
		 * @return the tab layout
		 */
		TabLayout getTabLayout();

		/**
		 * Get the selected tab, if any.
		 * @return Optional selected tab
		 */
		default Optional<Tab> getSelectedTab() {
			return getTabLayout().getSelectedTab();
		}

		/**
		 * Checks if this event originated from the client side.
		 * @return <code>true</code> if the event originated from the client side, <code>false</code> otherwise
		 */
		boolean isFromClient();

	}

	// ------- builder

}
