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
package com.holonplatform.artisan.vaadin.flow.export.xls.internal;

import java.util.Date;

import com.holonplatform.artisan.vaadin.flow.export.xls.XLSDataType;
import com.holonplatform.artisan.vaadin.flow.export.xls.XLSValue;
import com.holonplatform.core.temporal.TemporalType;

/**
 * Date type {@link XLSValue}.
 *
 * @since 1.0.0
 */
public class XLSDateValue extends AbstractXLSValue<Date> {

	private static final long serialVersionUID = 8576091621985253707L;

	public XLSDateValue(Date value, TemporalType temporalType, String dataFormat) {
		super(Date.class, value, temporalType, dataFormat);
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
