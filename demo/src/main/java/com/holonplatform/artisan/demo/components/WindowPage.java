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
import com.holonplatform.artisan.vaadin.flow.components.Window;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "window", layout = Menu.class)
public class WindowPage extends SplitLayout {

	private static final long serialVersionUID = 1L;

	private final Label codeLabel;

	private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore "
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
			+ " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor...";

	public WindowPage() {
		super();
		setSizeFull();

		codeLabel = new Label();
		codeLabel.getStyle().set("font-family", "Courier New");

		// message window
		Button btnMessage = new Button("Message window");
		btnMessage.addClickListener(event -> {
			Window.builder().content(new Label("This is a message window.")).build().open();
			setCodeLabel("<b>Window.builder()</b>.content(new Label(\"This is a message window.\")).build().open();");
		});

		// attach/detach listener window
		Button btnListener = new Button("Attach/detach listeners window");
		btnListener.addClickListener(event -> {
			Window.builder()
					.withAttachListener(evt -> Notification.show("Window attach listener", 2500, Position.BOTTOM_END))
					.withDetachListener(evt -> Notification.show("Window detach listener", 2500, Position.BOTTOM_END))
					.content(new Label("Click outside or press 'Esc' to fire detach listener")).build().open();
			setCodeLabel(
					"Window.builder()<b>.withAttachListener(evt -> Notification.show(\"Window attach listener\", 2500, Position.BOTTOM_END))"
							+ ".withDetachListener(evt -> Notification.show(\"Window detach listener\", 2500, Position.BOTTOM_END))</b>"
							+ ".content(new Label(\"Click outside or press 'Esc' to fire detach listener\")).build().open();");
		});

		// closable window
		Button btnClosable = new Button("Closable window");
		btnClosable.addClickListener(event -> {
			Window.builder().closable(true).closeOnEsc(false).closeOnOutsideClick(false)
					.content(new Div(new H3("Closable window:"),
							new Label("this window can only be closed with the 'close' button.")))
					.build().open();
			setCodeLabel("Window.builder()<b>.closable(true)</b>.closeOnEsc(false).closeOnOutsideClick(false)"
					+ ".content(new Div(new H3(\"Closable window:\"), new Label(\"this window can only be closed with the 'close' button.\"))).build().open();");
		});

		// resizable window
		Button btnResizable = new Button("Resizable window");
		btnResizable.addClickListener(event -> {
			Window.builder().resizable(true).content(new Div(new H3("Resizable window:"),
					new Label("this window can be maximized/minimized with proper buttons."))).build().open();
			setCodeLabel(
					"Window.builder()<b>.resizable(true)</b>.content(new Div(new H3(\"Resizable window:\"),new Label(\"this window can be "
							+ "maximized/minimized with proper buttons.\"))).build().open();");
		});

		// window with title
		Button btnTitle = new Button("Window with title");
		btnTitle.addClickListener(event -> {
			Window.builder().title("This is the window title").content(new TextField("Name")).build().open();
			setCodeLabel(
					"Window.builder()<b>.title(\"This is the window title\")</b>.content(new TextField(\"Name\")).build().open();");
		});

		// form layout window
		Button btnFormLayout = new Button("Form layout window");
		btnFormLayout.addClickListener(event -> {
			FormLayout formLayout = new FormLayout(new TextField("Name"), new TextField("Surname"),
					new DatePicker("Birthdate:"), new Checkbox("Enabled"));

			Window.builder().title("Form layout", "holon-artisan-window-form-test").content(formLayout)
					.withFooterComponent(new Button("Save")).build().open();
			setCodeLabel("Window.builder().title(\"Form layout\", \"holon-artisan-window-form-test\", null)"
					+ "<b>.content(formLayout)</b>.withFooterComponent(new Button(\"Save\")).build().open();");
		});

		// full size window
		Button btnFullsize = new Button("Full size window");
		btnFullsize.addClickListener(event -> {
			Window.builder().fullSize().closable(true).content(new Div(new Span(LOREM_IPSUM))).build().open();
			setCodeLabel(
					"Window.builder()<b>.fullSize()</b>.content(new Div(new Span(\"Lorem ipsum dolor sit amet, consectetur adipiscing elit...\"))).build().open();");
		});

		// components in header window
		Button btnHeader = new Button("Components in header window");
		btnHeader.addClickListener(event -> {
			Window.builder().closable(true).resizable(true).withHeaderComponent(new Label("Label in header"))
					.withHeaderComponent(new Button("Button"))
					.content(new Label("Window with multiple components in header")).build().open();
			setCodeLabel(
					"Window.builder().closable(true).resizable(true)<b>.withHeaderComponent(new Label(\"Label in header\"))"
							+ ".withHeaderComponent(new Button(\"Button\"))</b>.content(new Label(\"Window with multiple components in header\")).build().open();");
		});

		// components in footer window
		Button btnFooter = new Button("Components in footer window");
		btnFooter.addClickListener(event -> {
			Window.builder().withFooterComponent(new Button("Button 1")).withFooterComponent(new Button("Button 2"))
					.content(new Label("Window with multiple components in footer")).build().open();
			setCodeLabel(
					"Window.builder()<b>.withFooterComponent(new Button(\"Button 1\")).withFooterComponent(new Button(\"Button 2\"))</b>"
							+ ".content(new Label(\"Window with multiple components in footer\")).build().open();");
		});

		// complex window
		Button btnComplex = new Button("Complex window");

		Button btnSave = new Button("Save");
		btnSave.getStyle().set("background-color", "darkorange");
		btnSave.getStyle().set("color", "white");
		btnSave.addClickListener(event -> {
			Notification.show("Button save click event", 2000, Position.MIDDLE);

		});

		VerticalLayout content = new VerticalLayout();
		content.add(new Paragraph(LOREM_IPSUM));
		content.add(new TextField("Name:"));
		content.add(new TextField("Surname:"));

		btnComplex.addClickListener(event -> {
			Window.builder().closable(true).id("window-id-X")
					.withAttachListener(evt -> Notification.show("Window attach listener", 2500, Position.BOTTOM_END))
					.withDetachListener(evt -> Notification.show("Window detach listener", 2500, Position.BOTTOM_END))
					.closeOnOutsideClick(false).closeOnEsc(false).title("Complex Window")
					.withHeaderComponent(new Label("Label added to header")).withFooterComponent(btnSave).width("700px")
					.content(content).build().open();
			setCodeLabel("Window.builder().closable(true).id(\"window-id-X\").withAttachListener(evt -> "
					+ "Notification.show(\"Window attach listener\", 2500, Position.BOTTOM_END)).withDetachListener(evt -> "
					+ "Notification.show(\"Window detach listener\", 2500, Position.BOTTOM_END)).closeOnOutsideClick(false)"
					+ ".closeOnEsc(false).title(\"Complex Window\").withHeaderComponent(new Label(\"Label added to header\"))"
					+ ".withFooterComponent(btnSave).width(\"700px\").content(content).build().open();");
		});

		// first Div
		Div firstDiv = new Div();
		firstDiv.setSizeFull();
		firstDiv.getStyle().set("background-color", "#f5f5f5");
		firstDiv.getStyle().set("padding-left", "20px");
		firstDiv.add(new H1("Window Page:"));

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setWidth("100%");
		buttons.getElement().getStyle().set("flex-wrap", "wrap");
		buttons.add(btnMessage, btnTitle, btnListener, btnClosable, btnResizable, btnFullsize, btnHeader, btnFooter,
				btnFormLayout, btnComplex);

		firstDiv.add(buttons);

		// second Div
		Div secondDiv = new Div();
		secondDiv.setSizeFull();
		secondDiv.getStyle().set("padding-left", "20px");
		secondDiv.add(new H1("Source:"), codeLabel);

		addToPrimary(firstDiv);
		addToSecondary(secondDiv);
	}

	private void setCodeLabel(String content) {
		codeLabel.getElement().setProperty("innerHTML", content);
	}
}
