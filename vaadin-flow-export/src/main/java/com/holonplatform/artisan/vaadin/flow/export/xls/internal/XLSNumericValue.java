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

import com.holonplatform.artisan.vaadin.flow.export.xls.XLSDataType;
import com.holonplatform.artisan.vaadin.flow.export.xls.XLSValue;

/**
 * Numeric type {@link XLSValue}.
 * 
 * @param <N> Number type
 *
 * @since 1.0.0
 */
public class XLSNumericValue<N extends Number> extends AbstractXLSValue<N> {

	private static final long serialVersionUID = 5577482374496574033L;

	public XLSNumericValue(Class<? extends N> valueType, N value, String dataFormat) {
		super(valueType, value, null, dataFormat);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.XLSValue#getDataType()
	 */
	@Override
	public XLSDataType getDataType() {
		return XLSDataType.NUMERIC;
	}

}
