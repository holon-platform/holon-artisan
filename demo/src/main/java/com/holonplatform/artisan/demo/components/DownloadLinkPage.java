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

import com.holonplatform.artisan.demo.root.Menu;
import com.holonplatform.artisan.vaadin.flow.components.DownloadLink;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "downloadlink", layout = Menu.class)
public class DownloadLinkPage extends VerticalLayout {

	private static final long serialVersionUID = -8222042647811796443L;

	public DownloadLinkPage() {
		super();

		DownloadLink dl = DownloadLink.builder(os -> {
			os.write("Test".getBytes());
		}).fileName("test.txt").text("Download").build();
		add(dl.getComponent());
	}

}
