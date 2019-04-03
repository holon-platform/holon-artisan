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
import com.holonplatform.artisan.vaadin.flow.app.layout.events.AppLayoutNarrowStateChangeEvent;

/**
 * Default {@link AppLayoutNarrowStateChangeEvent} implementation.
 *
 * @since 1.0.2
 */
public class DefaultAppLayoutNarrowStateChangeEvent implements AppLayoutNarrowStateChangeEvent {

	private static final long serialVersionUID = -1275277951317879819L;

	private final ApplicationLayout applicationLayout;
	private final boolean narrow;

	public DefaultAppLayoutNarrowStateChangeEvent(ApplicationLayout applicationLayout, boolean narrow) {
		super();
		this.applicationLayout = applicationLayout;
		this.narrow = narrow;
	}

	@Override
	public ApplicationLayout getApplicationLayout() {
		return applicationLayout;
	}

	@Override
	public boolean isNarrow() {
		return narrow;
	}

}
