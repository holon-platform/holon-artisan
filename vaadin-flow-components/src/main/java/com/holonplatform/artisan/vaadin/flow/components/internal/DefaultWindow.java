package com.holonplatform.artisan.vaadin.flow.components.internal;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.holonplatform.artisan.vaadin.flow.components.Window;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 * Default {@link Window} implementation.
 * 
 * @since 1.0.0
 */
@HtmlImport("frontend://com-holonplatform-vaadin-window-styles.html")
public class DefaultWindow extends Dialog implements Window {

	private static final long serialVersionUID = -4024830017084261133L;

	private final HorizontalLayout header;
	private final Span title;

	private final HorizontalLayout buttons;
	private final Button btnClose;
	private final Button btnMaximize;
	private final Button btnMinimize;

	private final HorizontalLayout footer;
	private final Div content;

	/**
	 * Default constructor
	 */
	public DefaultWindow() {
		super();

		// set window content scrollable
		addThemeVariants(WindowVariant.SCROLLABLE);

		header = new HorizontalLayout();
		header.addClassName("h-window-header");
		header.setWidth("100%");
		header.setJustifyContentMode(JustifyContentMode.BETWEEN);
		header.setDefaultVerticalComponentAlignment(Alignment.CENTER);

		// add title
		title = new Span("");
		title.addClassName("h-window-title");
		header.add(title);
		header.setFlexGrow(1, title);

		// add maximize/minimize/close buttons
		buttons = new HorizontalLayout();
		buttons.addClassName("h-window-buttons");
		buttons.setSpacing(false);
		header.add(buttons);

		btnClose = new Button();
		btnClose.setIcon(VaadinIcon.CLOSE_SMALL.create());
		btnClose.addThemeVariants(ButtonVariant.LUMO_ICON);
		btnClose.getStyle().set("min-width", "0px");
		btnClose.addClickListener(evt -> {
			close();
		});

		btnMaximize = new Button();
		btnMaximize.setIcon(VaadinIcon.PLUS.create());
		btnMaximize.addThemeVariants(ButtonVariant.LUMO_ICON);
		btnMaximize.getStyle().set("min-width", "0px");
		btnMaximize.addClickListener(evt -> {
			addThemeVariants(WindowVariant.FULL_WIDTH, WindowVariant.FULL_HEIGHT);
		});

		btnMinimize = new Button();
		btnMinimize.setIcon(VaadinIcon.MINUS.create());
		btnMinimize.addThemeVariants(ButtonVariant.LUMO_ICON);
		btnMinimize.getStyle().set("min-width", "0px");
		btnMinimize.addClickListener(evt -> {
			removeThemeVariants(WindowVariant.FULL_WIDTH, WindowVariant.FULL_HEIGHT);
		});
		buttons.add(btnMaximize, btnMinimize, btnClose);

		add(header);

		// add scrollable content Div
		content = new Div();
		content.addClassName("h-window-content");
		content.setSizeFull();
		content.getStyle().set("overflow", "auto");
		add(content);

		// add footer
		footer = new HorizontalLayout();
		footer.addClassName("h-window-footer");
		footer.setWidth("100%");
		footer.setJustifyContentMode(JustifyContentMode.END);
		add(footer);

		// title.setVisible(false);
		buttons.setVisible(false);
		btnMaximize.setVisible(false);
		btnMinimize.setVisible(false);
		btnClose.setVisible(false);
		footer.setVisible(false);
	}

	/**
	 * Gets current window
	 * @return Component
	 */
	@Override
	public Component getComponent() {
		return this;
	}

	/**
	 * Gets the title text.
	 * @return Optional Optional window title value
	 */
	public Optional<String> getTitle() {
		String titleText = title.getText();
		if (titleText != null && !titleText.trim().equals("")) {
			return Optional.of(titleText);
		}
		return Optional.empty();
	}

	/**
	 * Sets window title
	 * @param windowTitle Window title value
	 */
	public void setTitle(String windowTitle) {
		title.setText(windowTitle);
		title.setVisible(true);
	}

	/**
	 * Check if window is closable
	 * @return true if close button is visible
	 */
	public boolean isClosable() {
		return buttons.isVisible() && btnClose.isVisible();
	}

	/**
	 * Makes the window explicitly closable
	 * <p>
	 * This method add a close button on window header
	 * </p>
	 * @param closable <code>true</code> value for closable window, <code>false</code> otherwise
	 */
	public void setClosable(boolean closable) {
		buttons.setVisible(closable);
		btnClose.setVisible(closable);
	}

	/**
	 * Checks if window is resizable
	 * @return true if maximize/minimize buttons are visible
	 */
	public boolean isResizable() {
		return buttons.isVisible() && btnMaximize.isVisible() && btnMinimize.isVisible();
	}

	/**
	 * Makes the window resizable
	 * <p>
	 * This method add classic buttons to maximize and minimize window
	 * </p>
	 * @param resizable <code>true</code> value for resizable window, <code>false</code> otherwise
	 */
	public void setResizable(boolean resizable) {
		buttons.setVisible(resizable);
		btnMaximize.setVisible(resizable);
		btnMinimize.setVisible(resizable);
	}

	/**
	 * Adds components to a scrollable div
	 * <p>
	 * This method set a list of components to a scrollable Div
	 * </p>
	 * @param component Component to set as Window content
	 */
	public void setContent(Component component) {
		ObjectUtils.argumentNotNull(component, "Window content must be not null");
		content.removeAll();
		content.add(component);
	}

	/**
	 * Adds component to window header
	 * <p>
	 * This method set a list of components to a header
	 * </p>
	 * @param component Component to add to header
	 */
	public void addHeaderComponent(Component component) {
		header.setVisible(true);
		header.addComponentAtIndex(1, component);
	}

	/**
	 * Adds component to footer
	 * <p>
	 * This method set a list of components to a footer
	 * </p>
	 * @param component Component to add to footer
	 */
	public void addFooterComponent(Component component) {
		footer.setVisible(true);
		footer.add(component);
	}

	/**
	 * Adds theme variants to the component.
	 * @param variants theme variants to add
	 */
	public void addThemeVariants(WindowVariant... variants) {
		getThemeNames().addAll(Stream.of(variants).map(WindowVariant::getVariantName).collect(Collectors.toList()));
	}

	/**
	 * Removes theme variants from the component.
	 * @param variants theme variants to remove
	 */
	public void removeThemeVariants(WindowVariant... variants) {
		getThemeNames().removeAll(Stream.of(variants).map(WindowVariant::getVariantName).collect(Collectors.toList()));
	}

}
