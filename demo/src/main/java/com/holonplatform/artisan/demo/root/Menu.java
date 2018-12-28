package com.holonplatform.artisan.demo.root;

import com.holonplatform.artisan.demo.window.HomePage;
import com.holonplatform.artisan.demo.window.WindowPage;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
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
		setSpacing(false);

		Button btnHome = new Button("Home");
		btnHome.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
		btnHome.addClickListener(event -> getUI().get().navigate(HomePage.class));
		btnHome.setWidth("100%");

		Button btnWindow = new Button("Window");
		btnWindow.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btnWindow.addClickListener(event -> getUI().get().navigate(WindowPage.class));
		btnWindow.setWidth("100%");

		Image img = new Image("https://holon-platform.com/contrib/themes/Holon-theme/dist/img/pittogramma_platform.svg",
				"Holon Java Platform");
		img.setWidth("70%");

		Label lblArtisan = new Label();
		lblArtisan.getElement().setProperty("innerHTML", "Artisan Demo");
		lblArtisan.getStyle().set("color", "white").set("font-size", "xx-large");

		VerticalLayout vl = new VerticalLayout();
		vl.setWidth("280px");
		vl.setHeight("100%");
		vl.getStyle().set("background-color", "black");
		vl.add(img);
		vl.add(lblArtisan);
		vl.add(btnHome);
		vl.add(btnWindow);

		vl.setHorizontalComponentAlignment(Alignment.CENTER, img);
		vl.setHorizontalComponentAlignment(Alignment.CENTER, lblArtisan);

		add(vl);
	}

}
