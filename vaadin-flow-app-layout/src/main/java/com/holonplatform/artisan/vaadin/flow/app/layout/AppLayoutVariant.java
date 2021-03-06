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

import java.util.Optional;

/**
 * The variants for app layout.
 * 
 * @since 1.0.2
 */
public enum AppLayoutVariant {

	/**
	 * App layout drawer small width.
	 */
	SMALL("small");

	private final String variant;

	AppLayoutVariant(String variant) {
		this.variant = variant;
	}

	/**
	 * Gets the variant name.
	 * @return variant name
	 */
	public String getVariantName() {
		return variant;
	}

	public static Optional<AppLayoutVariant> fromThemeName(String name) {
		if (name != null) {
			for (AppLayoutVariant variant : values()) {
				if (name.equals(variant.getVariantName())) {
					return Optional.of(variant);
				}
			}
		}
		return Optional.empty();
	}

}
