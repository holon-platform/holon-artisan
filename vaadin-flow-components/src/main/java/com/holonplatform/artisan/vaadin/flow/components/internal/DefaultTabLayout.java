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
package com.holonplatform.artisan.vaadin.flow.components.internal;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.holonplatform.artisan.vaadin.flow.components.TabLayout;
import com.holonplatform.core.Registration;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.component.tabs.TabsVariant;

/**
 * Default {@link TabLayout} implementation.
 *
 * @since 1.0.0
 */
public class DefaultTabLayout implements TabLayout {

	private static final long serialVersionUID = -7624742437749684392L;

	private final Tabs tabsComponent;

	private final List<DefaultTab> tabs = new LinkedList<>();

	private final TabsContent<?> layout;

	private boolean built = false;

	private boolean fireSelectionChangeEvent = true;

	/**
	 * Constructor.
	 * @param <L> Layout type
	 * @param layout Layout component (not null)
	 * @param orientation Tabs orientation
	 */
	public <L extends Component & FlexComponent<L>> DefaultTabLayout(L layout, Orientation orientation) {
		super();
		ObjectUtils.argumentNotNull(layout, "Layout must be not null");
		// layout
		this.layout = new TabsContent<>(layout);
		// tabs
		this.tabsComponent = new Tabs();
		this.tabsComponent.setWidth("100%");
		if (orientation != null) {
			this.tabsComponent.setOrientation(orientation);
		}
		this.tabsComponent.addSelectedChangeListener(event -> {
			onTabSelectionChange(event.getSource().getSelectedIndex());
		});
		this.layout.getFlexComponent().add(this.tabsComponent);
	}

	/**
	 * Get the Tabs component.
	 * @return the tabs component
	 */
	protected Tabs getTabsComponent() {
		return tabsComponent;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.HasComponent#getComponent()
	 */
	@Override
	public Component getComponent() {
		return layout.getComponent();
	}

	/**
	 * Adds theme variants to the tabs component.
	 * @param variants The theme variants to add
	 */
	public void addThemeVariants(TabsVariant... variants) {
		getTabsComponent().addThemeVariants(variants);
	}

	/**
	 * Sets the flex grow property of all enclosed tabs. The flex grow property specifies what amount of the available
	 * space inside the layout the component should take up, proportionally to the other components.
	 * @param flexGrow the proportion of the available space the enclosed tabs should take up
	 * @throws IllegalArgumentException if {@code flexGrow} is negative
	 */
	public void setFlexGrowForEnclosedTabs(double flexGrow) {
		getTabsComponent().setFlexGrowForEnclosedTabs(flexGrow);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.TabLayout#getTabs()
	 */
	@Override
	public List<Tab> getTabs() {
		return Collections.unmodifiableList(tabs);
	}

	/**
	 * Get the index of given tab, if available.
	 * @param tab The tab
	 * @return Optional tab index
	 */
	public Optional<Integer> getTabIndex(Tab tab) {
		if (tab != null) {
			int idx = tabs.indexOf(tab);
			if (idx > -1) {
				return Optional.of(idx);
			}
		}
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.TabLayout#getTabAt(int)
	 */
	@Override
	public Optional<Tab> getTabAt(int index) {
		if (index >= 0 && index < tabs.size()) {
			return Optional.ofNullable(tabs.get(index));
		}
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.TabLayout#getSelectedTab()
	 */
	@Override
	public Optional<Tab> getSelectedTab() {
		return getSelectedTabIndex().flatMap(i -> getTabAt(i));
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.TabLayout#getSelectedTabIndex()
	 */
	@Override
	public Optional<Integer> getSelectedTabIndex() {
		int idx = getTabsComponent().getSelectedIndex();
		if (idx > -1) {
			return Optional.of(idx);
		}
		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.TabLayout#setSelectedTab(com.holonplatform.artisan.vaadin.flow.
	 * components.TabLayout.Tab)
	 */
	@Override
	public boolean setSelectedTab(Tab tab) {
		if (tab != null) {
			return getTabIndex(tab).map(i -> setSelectedTabIndex(i)).orElse(false);
		}
		setSelectedTabIndex(-1);
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.TabLayout#setSelectedTabIndex(int)
	 */
	@Override
	public boolean setSelectedTabIndex(int index) {
		if (index < 0) {
			getTabsComponent().setSelectedIndex(index);
		} else {
			return getTabAt(index).filter(t -> t.isEnabled() && t.isVisible()).map(t -> {
				getTabsComponent().setSelectedIndex(index);
				return true;
			}).orElse(false);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.TabLayout#addSelectedTabChangeListener(com.holonplatform.artisan
	 * .vaadin.flow.components.TabLayout.SelectedTabChangeListener)
	 */
	@Override
	public Registration addSelectedTabChangeListener(SelectedTabChangeListener listener) {
		ObjectUtils.argumentNotNull(listener, "Listener must be not null");
		final com.vaadin.flow.shared.Registration r = getTabsComponent().addSelectedChangeListener(event -> {
			if (fireSelectionChangeEvent) {
				listener.onSelectedTabChange(new DefaultSelectedTabChangeEvent(this, event.isFromClient()));
			}
		});
		return () -> r.remove();
	}

	/**
	 * Build the component.
	 * @param selectTabIndex The index of the tab to select, if <code>-1</code> the first tab will be selected, if
	 *        available
	 */
	public void build(int selectTabIndex) {
		this.built = true;
		// reset selection
		try {
			fireSelectionChangeEvent = false;
			getTabsComponent().setSelectedIndex(-1);
		} finally {
			fireSelectionChangeEvent = true;
		}
		// select tab
		if (selectTabIndex < 0 || selectTabIndex >= tabs.size()) {
			setSelectedTabIndex(getFirstSelectableTabIndex());
		} else {
			setSelectedTabIndex(selectTabIndex);
		}
	}

	/**
	 * Get the index of the first selectable (enabled and visible) tab, if available.
	 * @return the index of the first selectable tab, or <code>-1</code> if none
	 */
	private int getFirstSelectableTabIndex() {
		for (int i = 0; i < tabs.size(); i++) {
			final Tab tab = tabs.get(i);
			if (tab.isEnabled() && tab.isVisible()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Get the given tab index is selectable (valid and the tab is enabled and visible).
	 * @param index Tab index
	 * @return whether the given tab index is selectable
	 */
	private boolean isSelectableTabIndex(int index) {
		return getTabAt(index).filter(t -> t.isEnabled() && t.isVisible()).isPresent();
	}

	/**
	 * Add a new tab.
	 * @param content The tab content provider (not null)
	 * @return The new tab reference
	 */
	public DefaultTab addTab(TabContent content) {
		ObjectUtils.argumentNotNull(content, "Tab content provider must be not null");
		final com.vaadin.flow.component.tabs.Tab tab = new com.vaadin.flow.component.tabs.Tab();
		final DefaultTab tabReference = new DefaultTab(tab, content);
		getTabsComponent().add(tab);
		tabs.add(tabReference);
		tabReference.setTabEnableChangeCallback((t, v) -> onTabEnableChange(t, v));
		tabReference.setTabVisibleChangeCallback((t, v) -> onTabVisibleChange(t, v));
		return tabReference;
	}

	/**
	 * Get the tab at given index, if available.
	 * @param index The tab index
	 * @return Optional tab
	 */
	protected Optional<DefaultTab> getTabAtIndex(int index) {
		if (index >= 0 && index < tabs.size()) {
			return Optional.ofNullable(tabs.get(index));
		}
		return Optional.empty();
	}

	/**
	 * Should be invoked when selected tab changes.
	 * @param index Selected index, <code>-1</code> if none
	 */
	protected void onTabSelectionChange(int index) {
		if (built) {
			// remove current content
			if (layout.getFlexComponent().getComponentCount() > 1) {
				layout.getFlexComponent().remove(layout.getFlexComponent().getComponentAt(1));
			}
			if (index > -1) {
				// get current content
				getTabAtIndex(index).filter(t -> t.isEnabled() && t.isVisible()).ifPresent(t -> {
					t.getContent().ifPresent(c -> {
						// add content
						layout.getFlexComponent().add(c);
						// check flex grow
						if (t.getContentFlexGrow() > -1) {
							layout.getFlexComponent().setFlexGrow(t.getContentFlexGrow(), c);
						}
						// check selection consumer
						t.getTabSelectedConsumer().ifPresent(tsc -> tsc.accept(t));
					});
				});
			}
		}
	}

	/**
	 * Should be invoked when a tab enable state changes.
	 * @param tab The tab
	 * @param enable Whether the tab is enabled
	 */
	protected void onTabEnableChange(com.vaadin.flow.component.tabs.Tab tab, boolean enable) {
		if (built && !enable) {
			unselectTab(tab);
		}
	}

	/**
	 * Should be invoked when a tab visible state changes.
	 * @param tab The tab
	 * @param visible Whether the tab is visible
	 */
	protected void onTabVisibleChange(com.vaadin.flow.component.tabs.Tab tab, boolean visible) {
		if (built && !visible) {
			unselectTab(tab);
		}
	}

	/**
	 * Unselect given tab, if it was selected.
	 * @param tab The tab to unselect
	 */
	private void unselectTab(com.vaadin.flow.component.tabs.Tab tab) {
		int tabIndex = getTabsComponent().indexOf(tab);
		if (tabIndex > -1 && getTabsComponent().getSelectedIndex() == tabIndex) {
			int newIndex = getTabsComponent().getSelectedIndex() - 1;
			if (newIndex >= 0 && isSelectableTabIndex(newIndex)) {
				getTabsComponent().setSelectedIndex(newIndex);
			} else {
				getTabsComponent().setSelectedIndex(getFirstSelectableTabIndex());
			}
		}
	}

	// -------

	private static class TabsContent<L extends Component & FlexComponent<L>> implements Serializable {

		private static final long serialVersionUID = 7185961023873772306L;

		private final L layout;

		public TabsContent(L layout) {
			super();
			this.layout = layout;
		}

		Component getComponent() {
			return layout;
		}

		FlexComponent<?> getFlexComponent() {
			return layout;
		}

	}

	private static class DefaultSelectedTabChangeEvent implements SelectedTabChangeEvent {

		private static final long serialVersionUID = -1130404426721653490L;

		private final TabLayout tabLayout;
		private final boolean fromClient;

		public DefaultSelectedTabChangeEvent(TabLayout tabLayout, boolean fromClient) {
			super();
			this.tabLayout = tabLayout;
			this.fromClient = fromClient;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.components.TabLayout.SelectedTabChangeEvent#getTabLayout()
		 */
		@Override
		public TabLayout getTabLayout() {
			return tabLayout;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.components.TabLayout.SelectedTabChangeEvent#isFromClient()
		 */
		@Override
		public boolean isFromClient() {
			return fromClient;
		}

	}

}
