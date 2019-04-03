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
import java.util.Optional;

import com.holonplatform.artisan.vaadin.flow.app.layout.ApplicationLayout;
import com.vaadin.flow.component.HasElement;

/**
 * Application content change event.
 *
 * @since 1.0.2
 */
public interface ApplicationContentChangeEvent extends Serializable {

	/**
	 * Get the {@link ApplicationLayout} which triggered the event.
	 * @return Event source
	 */
	ApplicationLayout getApplicationLayout();

	/**
	 * Get the new application layout content, if available.
	 * @return Optional application layout content
	 */
	Optional<HasElement> getContent();

}
