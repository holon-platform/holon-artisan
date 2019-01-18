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
package com.holonplatform.artisan.vaadin.flow.export.exceptions;

/**
 * Exception to notify the data export was interrupted.
 *
 * @since 1.0.0
 */
public class InterruptedExportException extends RuntimeException {

	private static final long serialVersionUID = 6155405386145474783L;

	/**
	 * Constructor with error message
	 * @param message Error message
	 */
	public InterruptedExportException(String message) {
		super(message);
	}

}
