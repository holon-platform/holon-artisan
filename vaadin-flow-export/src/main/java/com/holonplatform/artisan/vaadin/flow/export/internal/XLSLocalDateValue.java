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
package com.holonplatform.artisan.vaadin.flow.export.internal;

import java.time.LocalDate;

import com.holonplatform.artisan.vaadin.flow.export.XLSDataType;
import com.holonplatform.artisan.vaadin.flow.export.XLSValue;
import com.holonplatform.core.temporal.TemporalType;

/**
 * LocalDate type {@link XLSValue}.
 *
 * @since 1.0.0
 */
public class XLSLocalDateValue extends AbstractXLSValue<LocalDate> {

	private static final long serialVersionUID = 6658565822840794270L;

	public XLSLocalDateValue(LocalDate value, String dataFormat) {
		super(value, TemporalType.DATE, dataFormat);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.XLSValue#getDataType()
	 */
	@Override
	public XLSDataType getDataType() {
		return XLSDataType.DATE;
	}

}
