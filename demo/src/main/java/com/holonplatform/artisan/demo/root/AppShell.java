package com.holonplatform.artisan.demo.root;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.Theme;

@Theme("demo-theme")
@PageTitle("Artisan Demo")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class AppShell implements AppShellConfigurator {

	private static final long serialVersionUID = 4554219448624638343L;

}
