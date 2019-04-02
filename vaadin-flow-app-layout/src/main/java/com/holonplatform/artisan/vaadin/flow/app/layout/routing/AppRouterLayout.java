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
package com.holonplatform.artisan.vaadin.flow.app.layout.routing;

import com.holonplatform.artisan.core.utils.Obj;
import com.holonplatform.artisan.vaadin.flow.app.layout.components.AppLayout;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.RouterLayout;

public class AppRouterLayout extends Composite<Div> implements RouterLayout {

	private static final long serialVersionUID = -6642606449786472863L;

	private final AppLayout appLayout;

	public AppRouterLayout() {
		this(new AppLayout());
	}

	public AppRouterLayout(AppLayout appLayout) {
		super();
		Obj.argumentNotNull(appLayout, "AppLayout must be not null");
		this.appLayout = appLayout;
		getContent().setId("application-router-layout");
		getContent().setSizeFull();
		getContent().add(appLayout);
	}

	@Override
	public void showRouterLayoutContent(HasElement content) {
		appLayout.setContent(content);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		// after navigation listener to close the drawer if not persistent
		getUI().ifPresent(ui -> ui.addAfterNavigationListener(event -> {
			appLayout.closeDrawerIfNotPersistent();
		}));
	}

}
