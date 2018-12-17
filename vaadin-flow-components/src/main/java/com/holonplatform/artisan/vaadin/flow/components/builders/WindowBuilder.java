package com.holonplatform.artisan.vaadin.flow.components.builders;

import java.util.Locale;

import com.holonplatform.artisan.vaadin.flow.components.Window;
import com.holonplatform.artisan.vaadin.flow.components.internal.WindowVariant;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator;
import com.holonplatform.vaadin.flow.i18n.LocalizationProvider;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;

/**
 * {@link Window} component builder.
 * 
 * @since 1.0.0
 */
public interface WindowBuilder extends ComponentConfigurator<WindowBuilder>, HasSizeConfigurator<WindowBuilder>,
		HasStyleConfigurator<WindowBuilder> {

	/**
	 * Sets the {@link Localizable} window title.
	 * @param title The {@link Localizable} window title
	 * @return this
	 * @see LocalizationProvider
	 */
	WindowBuilder title(Localizable title);

	/**
	 * Sets the window title.
	 * @param title The window title to set
	 * @return this
	 */
	default WindowBuilder title(String title) {
		return title(Localizable.builder().message(title).build());
	}

	/**
	 * Sets the localizable window title.
	 * @param defaultTitle Default window title if no translation is available for given <code>messageCode</code> for
	 *        current {@link Locale}
	 * @param messageCode Window title translation message key
	 * @param arguments Optional window title translation arguments
	 * @return this
	 * @see LocalizationProvider
	 */
	default WindowBuilder title(String defaultTitle, String messageCode, Object... arguments) {
		return title(Localizable.builder().message(defaultTitle).messageCode(messageCode).messageArguments(arguments)
				.build());
	}

	/**
	 * Sets the window content.
	 * @param content The content component (not null)
	 * @return this
	 */
	WindowBuilder content(Component... content);

	/**
	 * Sets whether window is closable.
	 * @param closable <code>true</code> value enables a default close button in window header. <code>true</code> value
	 *        also enable {@link #closeOnEsc(boolean) closeOnEsc} and {@link #closeOnOutsideClick(boolean)
	 *        closeOnOutsideClick} functionalities
	 * @return this
	 */
	WindowBuilder closable(boolean closable);

	/**
	 * Sets whether window is resizable.
	 * <p>
	 * By default, the window is not resizable.
	 * </p>
	 * @param resizable <code>true</code> to enable two default buttons in window header to maximize/minimize window,
	 *        <code>false</code> to disable it
	 * @return this
	 */
	WindowBuilder resizable(boolean resizable);

	/**
	 * Adds component to window header
	 * <p>
	 * Component is added between window title and close/resize buttons
	 * </p>
	 * @param component The component to add to window header
	 * @return this
	 */
	WindowBuilder withHeaderComponent(Component component);

	/**
	 * Adds component to window footer
	 * <p>
	 * By default component is added to the end of the footer through {@link JustifyContentMode#END}
	 * </p>
	 * @param component The component to add to window footer
	 * @return this
	 */
	WindowBuilder withFooterComponent(Component component);

	/**
	 * Sets whether the window can be closed by hitting the esc-key or not.
	 * <p>
	 * By default, the window is closable with esc.
	 * </p>
	 * @param closeOnEsc <code>true</code> to enable closing this window with the esc-key, <code>false</code> to disable
	 *        it
	 * @return this
	 */
	WindowBuilder closeOnEsc(boolean closeOnEsc);

	/**
	 * Sets whether this window can be closed by clicking outside of it or not.
	 * <p>
	 * By default, the window is closable with an outside click.
	 * </p>
	 * @param closeOnOutsideClick <code>true</code> to enable closing this window with an outside click,
	 *        <code>false</code> to disable it
	 * @return this
	 */
	WindowBuilder closeOnOutsideClick(boolean closeOnOutsideClick);

	/**
	 * Adds theme variants to the component.
	 * @param variants theme variants to add
	 */
	WindowBuilder withThemeVariants(WindowVariant... variants);

	/**
	 * Removes theme variants from the component.
	 * @param variants theme variants to remove
	 */
	WindowBuilder removeThemeVariants(WindowVariant... variants);

	/**
	 * Build the {@link Window}.
	 * @return the {@link Window} instance
	 */
	Window build();

	/**
	 * Build the {@link Window} component and open it.
	 * @return The {@link Window} instance
	 */
	default Window open() {
		final Window window = build();
		window.open();
		return window;
	}

}
