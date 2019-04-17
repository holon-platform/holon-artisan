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
package com.holonplatform.artisan.vaadin.flow.export.xls.internal;

import com.holonplatform.artisan.vaadin.flow.export.xls.XLSPropertyValueContext;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.property.PropertyBox;

/**
 * Default {@link XLSPropertyValueContext} implementation.
 *
 * @param <T> Property type
 * 
 * @since 1.0.4
 */
public class DefaultXLSPropertyValueContext<T> implements XLSPropertyValueContext<T> {

	private static final long serialVersionUID = 513556251269958849L;

	private final Property<T> property;
	private final XLSPropertyConfiguration configuration;
	private final PropertyBox row;

	public DefaultXLSPropertyValueContext(Property<T> property, XLSPropertyConfiguration configuration,
			PropertyBox row) {
		super();
		this.property = property;
		this.configuration = configuration;
		this.row = row;
	}

	@Override
	public Property<T> getProperty() {
		return property;
	}

	@Override
	public XLSPropertyConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public PropertyBox getRow() {
		return row;
	}

}
