package com.holonplatform.artisan.demo.root;

import java.util.Collections;

import com.holonplatform.artisan.demo.components.DownloadLinkPage;
import com.holonplatform.artisan.demo.components.ExportPage;
import com.holonplatform.artisan.demo.components.HomePage;
import com.holonplatform.artisan.demo.components.InputFiltersPage;
import com.holonplatform.artisan.demo.components.OperationProgressDialogPage;
import com.holonplatform.artisan.demo.components.TabLayoutPage;
import com.holonplatform.artisan.demo.components.WindowPage;
import com.holonplatform.artisan.vaadin.flow.app.layout.components.AppRouterLayout;
import com.holonplatform.vaadin.flow.components.Components;
import com.holonplatform.vaadin.flow.i18n.LocalizationProvider;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.ParentLayout;

@ParentLayout(MainLayout.class)
public class Menu extends AppRouterLayout {

	private static final long serialVersionUID = 1L;

	private final Div title;

	public Menu() {
		super();

		final Icon logo = VaadinIcon.CUBES.create();
		logo.setColor("#0000ff");
		getNavbarStart().add(logo);

		this.title = Components.label().text("Demo").styleName("app-title").build();
		getNavbarStart().add(title);

		getNavbarEnd().add(Components.button().icon(VaadinIcon.BELL)
				.withThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_PRIMARY)
				.onClick(
						event -> Notification.show("Overlay: " + isOverlay() + " - Drawer opened: " + isDrawerOpened()))
				.build());

		addToDrawer(getMenuContent());

		addApplicationContentChangeListener(event -> {
			// title
			title.setText(event.getContent().filter(c -> c instanceof HasViewTitle)
					.map(c -> ((HasViewTitle) c).getTitle()).flatMap(t -> LocalizationProvider.localize(t)).orElse(""));
			// actions
			event.getApplicationLayout().getNavbarContent().removeAll();
			event.getContent().filter(c -> c instanceof HasViewActions).map(c -> ((HasViewActions) c).getActions(this))
					.orElse(Collections.emptyList())
					.forEach(a -> event.getApplicationLayout().getNavbarContent().add(a));
		});

		addOverlayStateChangeEventListener(evt -> {
			System.err.println("-------> Overlay: " + evt.isOverlay());
		});
	}

	private Component getMenuContent() {

		final VerticalLayout vl = new VerticalLayout();

		Button btn = new Button("Home");
		btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
		btn.addClickListener(event -> getUI().get().navigate(HomePage.class));
		btn.setWidth("100%");
		vl.add(btn);

		btn = new Button("Window");
		btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn.addClickListener(event -> getUI().get().navigate(WindowPage.class));
		btn.setWidth("100%");
		vl.add(btn);

		btn = new Button("Operation progress dialog");
		btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn.addClickListener(event -> getUI().get().navigate(OperationProgressDialogPage.class));
		btn.setWidth("100%");
		vl.add(btn);

		btn = new Button("Tabs");
		btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn.addClickListener(event -> getUI().get().navigate(TabLayoutPage.class));
		btn.setWidth("100%");
		vl.add(btn);

		btn = new Button("Export");
		btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn.addClickListener(event -> getUI().get().navigate(ExportPage.class));
		btn.setWidth("100%");
		vl.add(btn);

		btn = new Button("Input filters");
		btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn.addClickListener(event -> getUI().get().navigate(InputFiltersPage.class));
		btn.setWidth("100%");
		vl.add(btn);

		btn = new Button("Download link");
		btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		btn.addClickListener(event -> getUI().get().navigate(DownloadLinkPage.class));
		btn.setWidth("100%");
		vl.add(btn);

		return vl;
	}

}
