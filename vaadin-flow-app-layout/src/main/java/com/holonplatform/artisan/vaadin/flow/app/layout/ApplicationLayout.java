/*
 * Copyright 2016-2019 Axioma srl.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.holonplatform.artisan.vaadin.flow.app.layout;

import java.io.Serializable;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasElement;

public interface ApplicationLayout extends Serializable {

	/**
	 * Set the width limit for which the application menu becomes collapsed.
	 * @param width The responsive width (default il <code>640px</code>)
	 */
	void setResponsiveWidth(String width);

	/**
	 * Toggle (open if closed, close if opened) the application drawer, i.e. the left layout side which typically
	 * contains the application menu.
	 */
	void toggleDrawer();

	/**
	 * Open the application drawer, i.e. the left layout side which typically contains the application menu.
	 */
	void openDrawer();

	/**
	 * Close the application drawer, i.e. the left layout side which typically contains the application menu.
	 */
	void closeDrawer();

	/**
	 * Close the application drawer (i.e. the left layout side which typically contains the application menu) if in
	 * collapsible mode.
	 */
	void closeDrawerIfNotPersistent();

	/**
	 * Set the application drawer (i.e. the left layout side which typically contains the application menu) content.
	 * @param component The content to set
	 */
	void setDrawerContent(Component component);

	/**
	 * Set the application layout content.
	 * @param content The content to set in the application layout viewport
	 */
	void setContent(HasElement content);

	/**
	 * Get the application header <em>title</em> slot.
	 * @return The application header <em>title</em> slot {@link HasComponents} reference
	 */
	HasComponents getHeaderTitle();

	/**
	 * Get the application header <em>context actions</em> slot.
	 * @return The application header <em>context actions</em> slot {@link HasComponents} reference
	 */
	HasComponents getHeaderContextActions();

	/**
	 * Get the application header <em>actions</em> slot.
	 * @return The application header <em>actions</em> slot {@link HasComponents} reference
	 */
	HasComponents getHeaderActions();

}
