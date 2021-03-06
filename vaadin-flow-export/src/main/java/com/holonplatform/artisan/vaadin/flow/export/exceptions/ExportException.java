/*
 * Copyright 2016-2019 Axioma srl.
 * 
 * Licensed under the Commercial Holon Platform Module License Version 1 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * https://docs.holon-platform.com/license/chpml_v1.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.holonplatform.artisan.vaadin.flow.export.exceptions;

/**
 * Exception to notify data export errors.
 *
 * @since 1.0.0
 */
public class ExportException extends RuntimeException {

	private static final long serialVersionUID = -6828814143020295850L;

	/**
	 * Constructor with error message
	 * @param message Error message
	 */
	public ExportException(String message) {
		super(message);
	}

	/**
	 * Constructor with error message and nested exception
	 * @param message Error message
	 * @param cause Nested exception
	 */
	public ExportException(String message, Throwable cause) {
		super(message, cause);
	}

}
