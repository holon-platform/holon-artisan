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
package com.holonplatform.artisan.vaadin.flow.components.utils;

import java.io.Serializable;

/**
 * Utility class to work with objects.
 *
 * @since 1.0.1
 */
public final class Obj implements Serializable {

	private static final long serialVersionUID = 1843728976064864155L;

	private Obj() {
	}

	/**
	 * Checks that the specified method or constructor argument is not <code>null</code>.
	 * @param <T> Argument type
	 * @param argument Argument to check
	 * @param message Exception message if given <code>argument</code> is <code>null</code>
	 * @return <code>argument</code> if not <code>null</code>
	 * @throws IllegalArgumentException if given <code>argument</code> is <code>null</code>
	 */
	public static <T> T argumentNotNull(T argument, String message) {
		if (argument == null) {
			throw new IllegalArgumentException(message);
		}
		return argument;
	}

}
