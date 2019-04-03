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
package com.holonplatform.artisan.vaadin.flow.app.layout.events;

import java.io.Serializable;
import java.util.EventListener;

import com.holonplatform.artisan.vaadin.flow.app.layout.routing.AppRouterLayout;

/**
 * A listener to listen for application content change events.
 *
 * @see AppRouterLayout
 * 
 * @since 1.0.2
 */
@FunctionalInterface
public interface ApplicationContentChangeListener extends EventListener, Serializable {

	/**
	 * Invoked when the application content changes.
	 * @param event The application content change event
	 */
	void applicationContentChange(ApplicationContentChangeEvent event);

}
