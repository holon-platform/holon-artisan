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
package com.holonplatform.artisan.vaadin.flow.app.layout.internal;

import com.holonplatform.artisan.vaadin.flow.app.layout.ApplicationLayout;
import com.holonplatform.artisan.vaadin.flow.app.layout.ApplicationLayout.DrawerStateChangeEvent;

/**
 * Default {@link DrawerStateChangeEvent} implementation.
 *
 * @since 1.0.2
 */
public class DefaultDrawerStateChangeEvent implements DrawerStateChangeEvent {

	private static final long serialVersionUID = -1275277951317879819L;

	private final ApplicationLayout applicationLayout;
	private final boolean overlay;
	private final boolean collapsed;

	public DefaultDrawerStateChangeEvent(ApplicationLayout applicationLayout, boolean overlay,
			boolean collapsed) {
		super();
		this.applicationLayout = applicationLayout;
		this.overlay = overlay;
		this.collapsed = collapsed;
	}

	@Override
	public ApplicationLayout getApplicationLayout() {
		return applicationLayout;
	}

	@Override
	public boolean isOverlay() {
		return overlay;
	}

	@Override
	public boolean isCollapsed() {
		return collapsed;
	}

}
