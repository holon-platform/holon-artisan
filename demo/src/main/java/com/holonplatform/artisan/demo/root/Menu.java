package com.holonplatform.artisan.demo.root;

import com.holonplatform.artisan.demo.window.HomePage;
import com.holonplatform.artisan.demo.window.WindowPage;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.RouterLayout;

@ParentLayout(MainLayout.class)
public class Menu extends HorizontalLayout implements RouterLayout {

	private static final long serialVersionUID = 1L;

	public Menu() {
		super();

		setSizeFull();

		Button btnHome = new Button("Home");
		btnHome.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
		btnHome.addClickListener(event -> getUI().get().navigate(HomePage.class));
		btnHome.setWidth("100%");

		Button btnWindow = new Button("Window");
		btnWindow.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
		btnWindow.addClickListener(event -> getUI().get().navigate(WindowPage.class));
		btnWindow.setWidth("100%");

		VerticalLayout vl = new VerticalLayout();
		vl.setWidth("180px");
		vl.setHeight("100%");
		vl.add(btnHome);
		vl.add(btnWindow);

		add(vl);
	}

}
