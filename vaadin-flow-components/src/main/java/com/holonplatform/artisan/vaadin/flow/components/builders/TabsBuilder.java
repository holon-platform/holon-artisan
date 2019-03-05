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
package com.holonplatform.artisan.vaadin.flow.components.builders;

import java.util.function.Consumer;

import com.holonplatform.artisan.vaadin.flow.components.TabLayout;
import com.holonplatform.artisan.vaadin.flow.components.TabLayout.SelectedTabChangeListener;
import com.holonplatform.artisan.vaadin.flow.components.TabLayout.Tab;
import com.holonplatform.artisan.vaadin.flow.components.TabLayout.TabContent;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.vaadin.flow.components.HasComponent;
import com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasEnabledConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasLabelConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasThemeVariantConfigurator;
import com.holonplatform.vaadin.flow.components.builders.ThemableLayoutConfigurator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.tabs.TabsVariant;

/**
 * {@link TabLayout} builder.
 *
 * @since 1.0.0
 */
public interface TabsBuilder
		extends ComponentConfigurator<TabsBuilder>, HasSizeConfigurator<TabsBuilder>, HasStyleConfigurator<TabsBuilder>,
		HasThemeVariantConfigurator<TabsVariant, TabsBuilder>, ThemableLayoutConfigurator<TabsBuilder> {

	/**
	 * Add a tab.
	 * <p>
	 * The {@link TabBuilder#add()} method can be used after tab configuration to add the tab and return to the parent
	 * builder.
	 * </p>
	 * @param content The tab content provider (not null)
	 * @return A builder to configure the tab
	 */
	TabBuilder withTab(TabContent content);

	/**
	 * Add a tab.
	 * <p>
	 * The {@link TabBuilder#add()} method can be used after tab configuration to add the tab and return to the parent
	 * builder.
	 * </p>
	 * @param component The tab content {@link Component}
	 * @return A builder to configure the tab
	 */
	default TabBuilder withTab(Component component) {
		return withTab(tab -> component);
	}

	/**
	 * Add a tab.
	 * <p>
	 * The {@link TabBuilder#add()} method can be used after tab configuration to add the tab and return to the parent
	 * builder.
	 * </p>
	 * @param component The tab content {@link Component} provider
	 * @return A builder to configure the tab
	 */
	default TabBuilder withTab(HasComponent component) {
		ObjectUtils.argumentNotNull(component, "The component provider must be not null");
		return withTab(tab -> component.getComponent());
	}

	/**
	 * Sets the flex grow property of all enclosed tabs. The flex grow property specifies what amount of the available
	 * space inside the layout the component should take up, proportionally to the other components.
	 * <p>
	 * For example, if all components have a flex grow property value set to 1, the remaining space in the layout will
	 * be distributed equally to all components inside the layout. If you set a flex grow property of one component to
	 * 2, that component will take twice the available space as the other components, and so on.
	 * </p>
	 * <p>
	 * Setting to flex grow property value 0 disables the expansion of the component. Negative values are not allowed.
	 * <p>
	 * @param flexGrow the proportion of the available space the enclosed tabs should take up
	 * @return this
	 */
	TabsBuilder flexGrowForTabs(double flexGrow);

	/**
	 * Set the tab index to select.
	 * @param index The tab index
	 * @return this
	 */
	TabsBuilder selectedTabIndex(int index);

	/**
	 * Add a listener for tab change events.
	 * @param listener The listener to add (not null)
	 * @return this
	 */
	TabsBuilder withSelectedTabChangeListener(SelectedTabChangeListener listener);

	/**
	 * Build the {@link TabLayout}.
	 * @return A new {@link TabLayout} instance
	 */
	TabLayout build();

	/**
	 * Tab builder.
	 */
	public interface TabBuilder extends HasSizeConfigurator<TabBuilder>, HasEnabledConfigurator<TabBuilder>,
			HasLabelConfigurator<TabBuilder> {

		/**
		 * Add given component to the tab.
		 * @param component The component to add
		 * @return this
		 */
		TabBuilder withComponent(Component component);

		/**
		 * Sets the whether the tab is visible.
		 * @param visible Whether the tab is visible
		 * @return this
		 */
		TabBuilder visible(boolean visible);

		/**
		 * Set the flex grow property for the tab content. This is relative to the parent tab layout.
		 * @param flexGrow the proportion of the available space the content should take up
		 * @return this
		 */
		TabBuilder flexGrow(double flexGrow);

		/**
		 * Sets the flex grow property of this tab. The flex grow property specifies what amount of the available space
		 * inside the layout the component should take up, proportionally to the other components.
		 * @param flexGrow the proportion of the available space the tab should take up
		 * @return this
		 */
		TabBuilder tabFlexGrow(double flexGrow);

		/**
		 * Set this tab as the selected tab.
		 * @return this
		 */
		TabBuilder selected();

		/**
		 * Set a consumer to be invoked each time this tab is selected.
		 * @param onTabSelected The tab selection consumer
		 * @return this
		 */
		TabBuilder onTabSelected(Consumer<Tab> onTabSelected);

		/**
		 * Add the tab.
		 * @return The parent builder
		 */
		TabsBuilder add();

	}

}
