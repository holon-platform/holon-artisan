package com.holonplatform.artisan.demo.root;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(Lumo.class)
@PageTitle("Artisan Demo")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@CssImport(value = "./styles/input-filter-field-styles.css", themeFor = "vaadin-custom-field")
@CssImport(value = "./styles/input-filter-container-styles.css", themeFor = "vaadin-form-layout")
@CssImport(value = "./styles/window-styles.css", themeFor = "vaadin-dialog-overlay")
public class MainLayout extends Div implements RouterLayout {

	private static final long serialVersionUID = 1L;

	public MainLayout() {
		super();
		setSizeFull();
		getStyle().set("overflow", "hidden");
	}

}
