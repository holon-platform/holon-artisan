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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;

import com.holonplatform.artisan.vaadin.flow.export.xls.PropertyXLSValueProviderRegistry;
import com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration;
import com.holonplatform.core.i18n.Caption;
import com.holonplatform.core.i18n.LocalizationContext;
import com.holonplatform.core.property.NumericProperty;
import com.holonplatform.core.property.PathProperty;
import com.holonplatform.core.property.PropertyBox;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.core.property.StringProperty;
import com.vaadin.flow.data.provider.DataProvider;

public class TestXLSExporter {

	private enum TestEnum {

		@Caption("The One")
		ONE, TWO;

	}

	private static final NumericProperty<Long> ID = NumericProperty.longType("id").message("The ID");
	private static final StringProperty TEXT = StringProperty.create("text");
	private static final NumericProperty<Integer> INTV = NumericProperty.integerType("intv");
	private static final NumericProperty<Double> DBLV = NumericProperty.doubleType("dblv");
	private static final PathProperty<TestEnum> ENMV = PathProperty.create("enmv", TestEnum.class);

	private static final PropertySet<?> SET = PropertySet.builderOf(ID, TEXT, INTV, DBLV, ENMV).identifier(ID).build();

	private static final DataProvider<PropertyBox, ?> DATASOURCE = DataProvider.ofItems(new PropertyBox[] {
			PropertyBox.builder(SET).set(ID, 1L).set(TEXT, "text1").set(INTV, 123).set(DBLV, 123456.78d)
					.set(ENMV, TestEnum.ONE).build(),
			PropertyBox.builder(SET).set(ID, 2L).set(INTV, 123).set(DBLV, 123456.78d).set(ENMV, TestEnum.TWO).build(),
			PropertyBox.builder(SET).set(ID, 3L).set(TEXT, "text3").set(DBLV, 123456.78567d).build(),
			PropertyBox.builder(SET).set(ID, 4L).set(TEXT, "text4").set(INTV, 123).build(),
			PropertyBox.builder(SET).set(ID, 5L).set(TEXT, "text5").set(INTV, 123456).set(DBLV, 123456.78d).build() });

	@Test
	public void testExport() throws IOException {

		LocalizationContext lc = LocalizationContext.builder().build();
		PropertyXLSValueProviderRegistry registry = PropertyXLSValueProviderRegistry.create(true);

		XLSExporter exporter = XLSExporter.builder(DATASOURCE, SET).localizationContext(lc).registry(registry).build();
		export(exporter, "test_xls_exporter_1");
	}

	@Test
	public void testExportDefaults() throws IOException {

		LocalizationContext lc = LocalizationContext.builder().build();
		PropertyXLSValueProviderRegistry registry = PropertyXLSValueProviderRegistry.create(true);
		XLSConfiguration configuration = XLSConfiguration.builder().shrinkToFitByDefault(true)
				.withTotalProperty(DBLV).withTotalProperty(ID).withTotalProperty(TEXT).build();

		XLSExporter exporter = XLSExporter.builder(DATASOURCE, SET).configuration(configuration).localizationContext(lc)
				.registry(registry).build();
		export(exporter, "test_xls_exporter_2");
	}

	private static void export(XLSExporter exporter, String fileName) throws IOException {
		File file = File.createTempFile(fileName + "_" + System.currentTimeMillis(), ".xlsx");
		try (OutputStream os = new FileOutputStream(file)) {
			exporter.export(os);
		}
	}

}
