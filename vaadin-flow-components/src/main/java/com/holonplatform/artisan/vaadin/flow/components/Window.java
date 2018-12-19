package com.holonplatform.artisan.vaadin.flow.components;

import com.holonplatform.artisan.vaadin.flow.components.builders.WindowBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultWindowBuilder;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.vaadin.flow.components.HasComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasTheme;

/**
 * A component to represent a floating popup window positioned at the center of the screen.
 * 
 * @since 1.0.0
 */
public interface Window extends HasStyle, HasSize, HasComponent, HasTheme {

	/**
	 * Opens the window.
	 */
	void open();

	/**
	 * Closes the window.
	 */
	void close();

	/**
	 * Gets a builder to create {@link Window} instances.
	 * @return A new {@link WindowBuilder}
	 */
	static WindowBuilder builder() {
		return new DefaultWindowBuilder();
	}

	/**
	 * Gets a window setting title and content.
	 * @param title The window title
	 * @param content The component to show as window content
	 * @return A new {@link Window} component
	 */
	static Window create(Localizable title, Component content) {
		return builder().title(title).content(content).build();
	}

}
