package com.holonplatform.artisan.demo.root;

import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(value = Lumo.class, variant = Lumo.LIGHT)
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@HtmlImport("frontend://styles/shared-styles.html")
public class MainLayout extends Div implements RouterLayout {

	private static final long serialVersionUID = 1L;

	public MainLayout() {
		super();
		setSizeFull();
	}

}
