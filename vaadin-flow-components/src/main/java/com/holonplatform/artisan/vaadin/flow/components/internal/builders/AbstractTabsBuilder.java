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
package com.holonplatform.artisan.vaadin.flow.components.internal.builders;

import java.util.function.Consumer;

import com.holonplatform.artisan.vaadin.flow.components.TabLayout;
import com.holonplatform.artisan.vaadin.flow.components.TabLayout.SelectedTabChangeListener;
import com.holonplatform.artisan.vaadin.flow.components.TabLayout.Tab;
import com.holonplatform.artisan.vaadin.flow.components.TabLayout.TabContent;
import com.holonplatform.artisan.vaadin.flow.components.builders.TabsBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.DefaultTabLayout;
import com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.DefaultComponentConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.DefaultHasSizeConfigurator;
import com.holonplatform.vaadin.flow.internal.components.builders.DefaultHasStyleConfigurator;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.ThemableLayout;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.Element;

/**
 * Default {@link TabsBuilder} implementation.
 * 
 * @param <L> Layout type
 *
 * @since 1.0.0
 */
public abstract class AbstractTabsBuilder<L extends Component & FlexComponent<L> & ThemableLayout>
		implements TabsBuilder {

	private final DefaultTabLayout instance;

	private final L layout;

	private final ComponentConfigurator<?> componentConfigurator;
	private final HasSizeConfigurator<?> sizeConfigurator;
	private final HasStyleConfigurator<?> styleConfigurator;

	private int selectTabIndex = -1;
	private Tab selectTab = null;

	public AbstractTabsBuilder(L layout, Orientation orientation) {
		super();
		this.layout = layout;
		this.instance = new DefaultTabLayout(layout, orientation);
		this.layout.addClassName("h-tabs");
		this.componentConfigurator = new DefaultComponentConfigurator(layout);
		this.sizeConfigurator = new DefaultHasSizeConfigurator(layout);
		this.styleConfigurator = new DefaultHasStyleConfigurator(layout);
	}

	/**
	 * Get the {@link TabLayout} instance.
	 * @return the instance
	 */
	protected DefaultTabLayout getInstance() {
		return instance;
	}

	/**
	 * Get the layout.
	 * @return the layout
	 */
	protected L getLayout() {
		return layout;
	}

	/**
	 * Set the tab to select.
	 * @param selectTab the tab to select
	 */
	void setSelectTab(Tab selectTab) {
		this.selectTab = selectTab;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#id(java.lang.String)
	 */
	@Override
	public TabsBuilder id(String id) {
		this.componentConfigurator.id(id);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#visible(boolean)
	 */
	@Override
	public TabsBuilder visible(boolean visible) {
		this.componentConfigurator.visible(visible);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withAttachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public TabsBuilder withAttachListener(ComponentEventListener<AttachEvent> listener) {
		this.componentConfigurator.withAttachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#withDetachListener(com.vaadin.flow.
	 * component.ComponentEventListener)
	 */
	@Override
	public TabsBuilder withDetachListener(ComponentEventListener<DetachEvent> listener) {
		this.componentConfigurator.withDetachListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withThemeName(java.lang.String)
	 */
	@Override
	public TabsBuilder withThemeName(String themeName) {
		this.componentConfigurator.withThemeName(themeName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener)
	 */
	@Override
	public TabsBuilder withEventListener(String eventType, DomEventListener listener) {
		this.componentConfigurator.withEventListener(eventType, listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasElementConfigurator#withEventListener(java.lang.String,
	 * com.vaadin.flow.dom.DomEventListener, java.lang.String)
	 */
	@Override
	public TabsBuilder withEventListener(String eventType, DomEventListener listener, String filter) {
		this.componentConfigurator.withEventListener(eventType, listener, filter);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator#elementConfiguration(java.util.function.
	 * Consumer)
	 */
	@Override
	public TabsBuilder elementConfiguration(Consumer<Element> element) {
		this.componentConfigurator.elementConfiguration(element);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#width(java.lang.String)
	 */
	@Override
	public TabsBuilder width(String width) {
		this.sizeConfigurator.width(width);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#height(java.lang.String)
	 */
	@Override
	public TabsBuilder height(String height) {
		this.sizeConfigurator.height(height);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minWidth(java.lang.String)
	 */
	@Override
	public TabsBuilder minWidth(String minWidth) {
		this.sizeConfigurator.minWidth(minWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxWidth(java.lang.String)
	 */
	@Override
	public TabsBuilder maxWidth(String maxWidth) {
		this.sizeConfigurator.maxWidth(maxWidth);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#minHeight(java.lang.String)
	 */
	@Override
	public TabsBuilder minHeight(String minHeight) {
		this.sizeConfigurator.minHeight(minHeight);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator#maxHeight(java.lang.String)
	 */
	@Override
	public TabsBuilder maxHeight(String maxHeight) {
		this.sizeConfigurator.maxHeight(maxHeight);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleNames(java.lang.String[])
	 */
	@Override
	public TabsBuilder styleNames(String... styleNames) {
		this.styleConfigurator.styleNames(styleNames);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator#styleName(java.lang.String)
	 */
	@Override
	public TabsBuilder styleName(String styleName) {
		this.styleConfigurator.styleName(styleName);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.HasThemeVariantConfigurator#withThemeVariants(java.lang.Enum[])
	 */
	@Override
	public TabsBuilder withThemeVariants(TabsVariant... variants) {
		getInstance().addThemeVariants(variants);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ThemableLayoutConfigurator#margin(boolean)
	 */
	@Override
	public TabsBuilder margin(boolean margin) {
		getLayout().setMargin(margin);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ThemableLayoutConfigurator#padding(boolean)
	 */
	@Override
	public TabsBuilder padding(boolean padding) {
		getLayout().setPadding(padding);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.vaadin.flow.components.builders.ThemableLayoutConfigurator#spacing(boolean)
	 */
	@Override
	public TabsBuilder spacing(boolean spacing) {
		getLayout().setSpacing(spacing);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.vaadin.flow.components.builders.ThemableLayoutConfigurator#boxSizing(com.vaadin.flow.component.
	 * orderedlayout.BoxSizing)
	 */
	@Override
	public TabsBuilder boxSizing(BoxSizing boxSizing) {
		getLayout().setBoxSizing(boxSizing);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.builders.TabLayoutBuilder#withTab(com.holonplatform.artisan.
	 * vaadin.flow.components.TabLayout.TabContent)
	 */
	@Override
	public TabBuilder withTab(TabContent content) {
		return new DefaultTabBuilder(this, content);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.TabLayoutBuilder#flexGrowForTabs(double)
	 */
	@Override
	public TabsBuilder flexGrowForTabs(double flexGrow) {
		getInstance().setFlexGrowForEnclosedTabs(flexGrow);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.TabLayoutBuilder#selectedTabIndex(int)
	 */
	@Override
	public TabsBuilder selectedTabIndex(int index) {
		this.selectTabIndex = index;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.builders.TabLayoutBuilder#withSelectedTabChangeListener(com.
	 * holonplatform.artisan.vaadin.flow.components.TabLayout.SelectedTabChangeListener)
	 */
	@Override
	public TabsBuilder withSelectedTabChangeListener(SelectedTabChangeListener listener) {
		getInstance().addSelectedTabChangeListener(listener);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.builders.TabLayoutBuilder#build()
	 */
	@Override
	public TabLayout build() {
		int index = selectTabIndex;
		if (index < 0 && selectTab != null) {
			index = getInstance().getTabIndex(selectTab).orElse(-1);
		}
		instance.build(index);
		return instance;
	}

}
