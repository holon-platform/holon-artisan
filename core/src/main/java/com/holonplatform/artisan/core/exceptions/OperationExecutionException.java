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
package com.holonplatform.artisan.core.exceptions;

/**
 * Exception for generic operation execution errors.
 * <p>
 * Note that this is a runtime (unchecked) exception.
 * </p>
 * 
 * @since 1.0.0
 */
public class OperationExecutionException extends RuntimeException {

	private static final long serialVersionUID = -3507499439999582934L;

	/**
	 * Constructor with error message
	 * @param message Error message
	 */
	public OperationExecutionException(String message) {
		super(message);
	}

	/**
	 * Constructor with nested exception
	 * @param cause Nested exception
	 */
	public OperationExecutionException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor with error message and nested exception
	 * @param message Error message
	 * @param cause Nested exception
	 */
	public OperationExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

}
