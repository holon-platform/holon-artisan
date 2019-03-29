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
package com.holonplatform.artisan.vaadin.flow.components.internal;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.holonplatform.artisan.core.utils.Obj;
import com.holonplatform.artisan.vaadin.flow.components.TabLayout.Tab;
import com.holonplatform.artisan.vaadin.flow.components.TabLayout.TabContent;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.vaadin.flow.i18n.LocalizationProvider;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.dom.Element;

/**
 * Default {@link Tab} implementation.
 *
 * @since 1.0.0
 */
public class DefaultTab implements Tab, HasSize {

	private static final long serialVersionUID = 4834201818158111473L;

	private final com.vaadin.flow.component.tabs.Tab tab;
	private final TabContent content;

	private double contentFlexGrow = -1;

	private Consumer<Tab> tabSelectedConsumer;

	private boolean scrollable = false;

	private BiConsumer<com.vaadin.flow.component.tabs.Tab, Boolean> tabEnableChangeCallback;
	private BiConsumer<com.vaadin.flow.component.tabs.Tab, Boolean> tabVisibleChangeCallback;

	/**
	 * Constructor.
	 * @param tab Tab reference (not null)
	 * @param content Tab content provider (not null)
	 */
	public DefaultTab(com.vaadin.flow.component.tabs.Tab tab, TabContent content) {
		super();
		Obj.argumentNotNull(tab, "Tab must be not null");
		Obj.argumentNotNull(content, "Tab content be not null");
		this.tab = tab;
		this.content = content;
	}

	/**
	 * Get the tab content, if available.
	 * @return Optional tab content
	 */
	Optional<Component> getContent() {
		final Component c = content.getContent(this);
		if (c != null && isScrollable()) {
			final Div wrapper = new Div();
			wrapper.setSizeFull();
			wrapper.getStyle().set("overflow", "auto");
			wrapper.add(c);
			return Optional.of(wrapper);
		}
		return Optional.ofNullable(c);
	}

	/**
	 * Set the tab enable change callback.
	 * @param tabEnableChangeCallback the callback to set
	 */
	void setTabEnableChangeCallback(BiConsumer<com.vaadin.flow.component.tabs.Tab, Boolean> tabEnableChangeCallback) {
		this.tabEnableChangeCallback = tabEnableChangeCallback;
	}

	/**
	 * Set the tab visible change callback.
	 * @param tabVisibleChangeCallback the callback to set
	 */
	void setTabVisibleChangeCallback(BiConsumer<com.vaadin.flow.component.tabs.Tab, Boolean> tabVisibleChangeCallback) {
		this.tabVisibleChangeCallback = tabVisibleChangeCallback;
	}

	/**
	 * Get the tab enable change callback, if available.
	 * @return Optional tab enable change callback
	 */
	protected Optional<BiConsumer<com.vaadin.flow.component.tabs.Tab, Boolean>> getTabEnableChangeCallback() {
		return Optional.ofNullable(tabEnableChangeCallback);
	}

	/**
	 * Get the tab visible change callback, if available.
	 * @return Optional tab visible change callback
	 */
	protected Optional<BiConsumer<com.vaadin.flow.component.tabs.Tab, Boolean>> getTabVisibleChangeCallback() {
		return Optional.ofNullable(tabVisibleChangeCallback);
	}

	/**
	 * Get the tab reference.
	 * @return the tab reference
	 */
	protected com.vaadin.flow.component.tabs.Tab getTab() {
		return tab;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.TabLayout.Tab#getLabel()
	 */
	@Override
	public String getLabel() {
		return getTab().getLabel();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.TabLayout.Tab#setLabel(com.holonplatform.core.i18n.Localizable)
	 */
	@Override
	public void setLabel(Localizable label) {
		if (label != null) {
			LocalizationProvider.localize(label).ifPresent(m -> getTab().setLabel(m));
		} else {
			getTab().setLabel(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.TabLayout.Tab#isVisible()
	 */
	@Override
	public boolean isVisible() {
		return getTab().isVisible();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.TabLayout.Tab#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		getTab().setVisible(visible);
		getTabVisibleChangeCallback().ifPresent(c -> c.accept(getTab(), visible));
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.TabLayout.Tab#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return getTab().isEnabled();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.TabLayout.Tab#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		getTab().setEnabled(enabled);
		getTabEnableChangeCallback().ifPresent(c -> c.accept(getTab(), enabled));
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.TabLayout.Tab#isSelected()
	 */
	@Override
	public boolean isSelected() {
		return getTab().isSelected();
	}

	/**
	 * Set the tab content flex grow ratio.
	 * @param contentFlexGrow The tab content flex grow
	 */
	public void setContentFlexGrow(double contentFlexGrow) {
		if (contentFlexGrow >= -1) {
			this.contentFlexGrow = contentFlexGrow;
		}
	}

	/**
	 * Get the tab content flex grow ratio.
	 * @return The tab content flex grow
	 */
	public double getContentFlexGrow() {
		return contentFlexGrow;
	}

	/**
	 * Sets the flex grow property of this tab. The flex grow property specifies what amount of the available space
	 * inside the layout the component should take up, proportionally to the other components.
	 * @param flexGrow the proportion of the available space the tab should take up
	 */
	public void setFlexGrow(double flexGrow) {
		getTab().setFlexGrow(flexGrow);
	}

	/**
	 * Gets the flex grow property of this tab.
	 * @return the flex grow property, or 0 if none was set
	 */
	public double getFlexGrow() {
		return getTab().getFlexGrow();
	}

	/**
	 * Adds the given components as children of the tab.
	 * @param components the components to add
	 */
	public void addTabComponent(Component... components) {
		getTab().add(components);
	}

	/**
	 * Get the tab selection consumer, if available.
	 * @return Optional tab selection consumer
	 */
	public Optional<Consumer<Tab>> getTabSelectedConsumer() {
		return Optional.ofNullable(tabSelectedConsumer);
	}

	/**
	 * Set the tab selection consumer.
	 * @param tabSelectedConsumer the tab selection consumer to set
	 */
	public void setTabSelectedConsumer(Consumer<Tab> tabSelectedConsumer) {
		this.tabSelectedConsumer = tabSelectedConsumer;
	}

	/**
	 * Get whether to wrap the tab content in a <em>scrollable</em> (i.e. with overflow auto) element.
	 * @return whether the tab is scrollable
	 */
	public boolean isScrollable() {
		return scrollable;
	}

	/**
	 * Set whether to wrap the tab content in a <em>scrollable</em> (i.e. with overflow auto) element.
	 * @param scrollable whether the tab is scrollable
	 */
	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
		if (scrollable) {
			setContentFlexGrow(1);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.vaadin.flow.component.HasElement#getElement()
	 */
	@Override
	public Element getElement() {
		return getTab().getElement();
	}

}
