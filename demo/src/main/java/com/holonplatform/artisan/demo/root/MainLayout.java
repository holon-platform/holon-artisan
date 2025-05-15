package com.holonplatform.artisan.demo.root;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.RouterLayout;

public class MainLayout extends Div implements RouterLayout {

	private static final long serialVersionUID = 1L;

	public MainLayout() {
		super();
		setSizeFull();
		getStyle().set("overflow", "hidden");
	}

}
