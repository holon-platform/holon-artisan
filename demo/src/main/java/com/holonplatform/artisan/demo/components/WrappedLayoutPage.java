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
import com.holonplatform.vaadin.flow.components.Components;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "wrapped-layout", layout = Menu.class)
public class WrappedLayoutPage extends Div {

	private static final long serialVersionUID = 1L;

	public WrappedLayoutPage() {
		super();
		setSizeFull();

		VerticalLayout vl = new VerticalLayout();
		vl.setWidth("100%");
		vl.add(Components.h5().text("This is a 'wrapped' Horizontal Layout").build());
		vl.add(Components.hl().spacing().fullWidth().wrap().add(Components.button().text("Button one").build(),
				Components.button().text("Button two").build(), Components.button().text("Button three").build(),
				Components.button().text("Button one").build(), Components.button().text("Button two").build(),
				Components.button().text("Button three").build(), Components.button().text("Button one").build(),
				Components.button().text("Button two").build(), Components.button().text("Button three").build(),
				Components.button().text("Button one").build(), Components.button().text("Button two").build(),
				Components.button().text("Button three").build(), Components.button().text("Button one").build(),
				Components.button().text("Button two").build(), Components.button().text("Button three").build(),
				Components.button().text("Button one").build(), Components.button().text("Button two").build(),
				Components.button().text("Button three").build(), Components.button().text("Button one").build(),
				Components.button().text("Button two").build(), Components.button().text("Button three").build(),
				Components.button().text("Button one").build(), Components.button().text("Button two").build(),
				Components.button().text("Button three").build(), Components.button().text("Button one").build(),
				Components.button().text("Button two").build(), Components.button().text("Button three").build())
				.build());

		vl.add(Components.h5().text("This is a 'NOT-wrapped' Horizontal Layout").build());
		vl.add(Components.hl().spacing().fullWidth().wrap(false).add(Components.button().text("Button one").build(),
				Components.button().text("Button two").build(), Components.button().text("Button three").build(),
				Components.button().text("Button one").build(), Components.button().text("Button two").build(),
				Components.button().text("Button three").build(), Components.button().text("Button one").build(),
				Components.button().text("Button two").build(), Components.button().text("Button three").build(),
				Components.button().text("Button one").build(), Components.button().text("Button two").build(),
				Components.button().text("Button three").build(), Components.button().text("Button one").build(),
				Components.button().text("Button two").build(), Components.button().text("Button three").build(),
				Components.button().text("Button one").build(), Components.button().text("Button two").build(),
				Components.button().text("Button three").build(), Components.button().text("Button one").build(),
				Components.button().text("Button two").build(), Components.button().text("Button three").build(),
				Components.button().text("Button one").build(), Components.button().text("Button two").build(),
				Components.button().text("Button three").build(), Components.button().text("Button one").build(),
				Components.button().text("Button two").build(), Components.button().text("Button three").build())
				.build());

		add(vl);
	}

}
