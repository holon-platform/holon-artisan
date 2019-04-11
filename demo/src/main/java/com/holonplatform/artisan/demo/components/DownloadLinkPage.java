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
package com.holonplatform.artisan.demo.components;

import java.util.Arrays;
import java.util.List;

import com.holonplatform.artisan.demo.root.HasViewActions;
import com.holonplatform.artisan.demo.root.HasViewTitle;
import com.holonplatform.artisan.demo.root.Menu;
import com.holonplatform.artisan.vaadin.flow.app.layout.ApplicationLayout;
import com.holonplatform.artisan.vaadin.flow.components.DownloadLink;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.vaadin.flow.components.Components;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "downloadlink", layout = Menu.class)
public class DownloadLinkPage extends VerticalLayout implements HasViewTitle, HasViewActions {

	private static final long serialVersionUID = -8222042647811796443L;

	public DownloadLinkPage() {
		super();

		DownloadLink dl = DownloadLink.builder(os -> {
			os.write("Test".getBytes());
		}).fileName("test.txt").text("Download").build();
		add(dl.getComponent());
	}

	@Override
	public List<Component> getActions(ApplicationLayout applicationLayout) {
		return Arrays.asList(Components.button().icon(VaadinIcon.ARROW_RIGHT).text("Open").onClick(e -> {
			applicationLayout.setDrawerOpened(true);
		}).build(), Components.button().icon(VaadinIcon.ARROW_LEFT).text("Close").onClick(e -> {
			applicationLayout.setDrawerOpened(false);
		}).build());
	}

	@Override
	public Localizable getTitle() {
		return Localizable.of("Download link");
	}

}
