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

import com.holonplatform.artisan.vaadin.flow.components.TabLayout;
import com.holonplatform.vaadin.flow.components.Components;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tabs.Orientation;

/**
 * Horizontal {@link TabLayout} builder.
 *
 * @since 1.0.0
 */
public class HorizontalTabsBuilder extends AbstractTabsBuilder<HorizontalLayout> {

	public HorizontalTabsBuilder() {
		super(Components.hl().withoutMargin().withoutPadding().withoutSpacing().fullWidth().build(),
				Orientation.HORIZONTAL);
	}

}
