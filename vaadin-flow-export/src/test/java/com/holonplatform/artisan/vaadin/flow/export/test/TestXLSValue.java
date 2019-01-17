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
package com.holonplatform.artisan.vaadin.flow.export.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.holonplatform.artisan.vaadin.flow.export.XLSDataType;
import com.holonplatform.artisan.vaadin.flow.export.XLSValue;

public class TestXLSValue {

	enum TestEnum {

		ONE, TWO;

	}

	@Test
	public void testBooleanValue() {

		XLSValue<Boolean> v = XLSValue.booleanValue(null);
		assertNotNull(v);
		assertEquals(XLSDataType.BOOLEAN, v.getDataType());
		assertFalse(v.getValue().isPresent());

		v = XLSValue.booleanValue(Boolean.TRUE);
		assertNotNull(v);
		assertEquals(XLSDataType.BOOLEAN, v.getDataType());
		assertTrue(v.getValue().isPresent());
		assertEquals(Boolean.TRUE, v.getValue().orElse(null));

	}

	@Test
	public void testStringValue() {

		XLSValue<String> v = XLSValue.stringValue(null);
		assertNotNull(v);
		assertEquals(XLSDataType.STRING, v.getDataType());
		assertFalse(v.getValue().isPresent());

		v = XLSValue.stringValue("test");
		assertNotNull(v);
		assertEquals(XLSDataType.STRING, v.getDataType());
		assertTrue(v.getValue().isPresent());
		assertEquals("test", v.getValue().orElse(null));

		XLSValue.enumValue(TestEnum.ONE);

	}

	@Test
	public void testEnumValue() {

		XLSValue<TestEnum> v = XLSValue.enumValue(null);
		assertNotNull(v);
		assertEquals(XLSDataType.ENUM, v.getDataType());
		assertFalse(v.getValue().isPresent());

		v = XLSValue.enumValue(TestEnum.ONE);
		assertNotNull(v);
		assertEquals(XLSDataType.ENUM, v.getDataType());
		assertTrue(v.getValue().isPresent());
		assertEquals(TestEnum.ONE, v.getValue().orElse(null));

	}

}