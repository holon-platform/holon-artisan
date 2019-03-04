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
package com.holonplatform.artisan.vaadin.flow.export;

/**
 * Boolean values export modes.
 *
 * @since 1.0.0
 */
public enum BooleanExportMode {

	/**
	 * Use the default <code>true</code> and <code>false</code> values.
	 */
	DEFAULT,

	/**
	 * Use a text.
	 */
	TEXT;

	public static final String DEFAULT_TRUE_TEXT = "Yes";
	public static final String DEFAULT_TRUE_TEXT_MESSAGE_CODE = "holon.artisan.export.boolean.text.true";
	public static final String DEFAULT_FALSE_TEXT = "No";
	public static final String DEFAULT_FALSE_TEXT_MESSAGE_CODE = "holon.artisan.export.boolean.text.false";

}