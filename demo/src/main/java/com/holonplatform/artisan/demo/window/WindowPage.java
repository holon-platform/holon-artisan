/*
 * Copyright 2016-2018 Axioma srl.
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
package com.holonplatform.artisan.demo.window;

import com.holonplatform.artisan.demo.root.Menu;
import com.holonplatform.artisan.vaadin.flow.components.Window;
import com.holonplatform.artisan.vaadin.flow.components.internal.WindowVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "window", layout = Menu.class)
public class WindowPage extends Div {

	private static final long serialVersionUID = 1L;

	public WindowPage() {

		setSizeFull();

		addClassName("page-background");

		Button btnForm = new Button("Window form");
		btnForm.addClickListener(event -> {
			Window.builder().content(new Label("Form!")).build().open();
		});

		Button btnNewOrder = new Button("Test window");
		btnNewOrder.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);

		Button btnSave = new Button("Save");

		btnNewOrder.addClickListener(event -> {
			Window.builder().closable(true).resizable(true).id("window-id-X")
					.withAttachListener(evt -> Notification.show("Window attach listener", 2500, Position.BOTTOM_END))
					.withDetachListener(evt -> Notification.show("Window detach listener", 2500, Position.BOTTOM_END))
					.content(new TextField("ciao")).closeOnOutsideClick(false).closeOnEsc(false)
					.title("Test Holon Window").styleNames("custom-wnd")
					.withThemeVariants(WindowVariant.FULL_WIDTH, WindowVariant.FULL_HEIGHT)

					.withHeaderComponent(new Label("Header!")).withFooterComponent(btnSave)
					.withFooterComponent(new Button("Close")).content(new TextField("ciao"))
					.content(new Div(new Span(
							"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore "
									+ "et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut "
									+ "aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum"
									+ "dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia "
									+ "deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod "
									+ "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco "
									+ "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit "
									+ "esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa "
									+ "qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
									+ "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco "
									+ "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore "
									+ "eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est "
									+ "laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
									+ " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor ")))
					.build().open();
		});

		add(new H1("Window Page:"));

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setWidth("100%");
		buttons.getElement().getStyle().set("flex-wrap", "wrap");
		buttons.add(btnForm, btnNewOrder);

		add(buttons);
	}
}
