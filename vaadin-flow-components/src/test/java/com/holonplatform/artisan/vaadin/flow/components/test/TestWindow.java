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
package com.holonplatform.artisan.vaadin.flow.components.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.holonplatform.artisan.vaadin.flow.components.Window;
import com.holonplatform.artisan.vaadin.flow.components.builders.WindowBuilder;
import com.holonplatform.artisan.vaadin.flow.components.internal.DefaultWindow;
import com.holonplatform.artisan.vaadin.flow.components.internal.WindowVariant;
import com.holonplatform.artisan.vaadin.flow.components.internal.builders.DefaultWindowBuilder;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.vaadin.flow.components.support.Unit;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;

public class TestWindow {

	@Test
	public void testBuilderCreation() {

		WindowBuilder builder = Window.builder();
		assertNotNull(builder);
		assertTrue(builder instanceof DefaultWindowBuilder);

		DefaultWindowBuilder defaultBuilder = new DefaultWindowBuilder();
		assertNotNull(defaultBuilder);
	}

	@Test
	public void testComponent() {

		DefaultWindow wnd = (DefaultWindow) Window.builder().title("title").build();
		assertNotNull(wnd);
		assertTrue(wnd.getTitle().isPresent());
		assertEquals("title", wnd.getTitle().get());

		wnd = (DefaultWindow) Window.builder().title(Localizable.of("localizableTitle")).build();
		assertTrue(wnd.getTitle().isPresent());
		assertEquals("localizableTitle", wnd.getTitle().get());

		wnd = (DefaultWindow) Window.builder().title("defaultTitle").build();
		assertTrue(wnd.getTitle().isPresent());
		assertEquals("defaultTitle", wnd.getTitle().get());

		wnd = (DefaultWindow) Window.builder().content(new Label("Content label")).build();
		Stream<Component> components = wnd.getChildren();
		assertNotNull(components);

		wnd = (DefaultWindow) Window.builder().closable(true).build();
		assertTrue(wnd.isClosable());

		wnd = (DefaultWindow) Window.builder().closable(false).build();
		assertFalse(wnd.isClosable());

		wnd = (DefaultWindow) Window.builder().resizable(true).build();
		assertTrue(wnd.isResizable());

		wnd = (DefaultWindow) Window.builder().resizable(false).build();
		assertFalse(wnd.isResizable());
	}

	@Test
	public void testSize() {
		Window wnd = Window.builder().fullHeight().build();
		assertNotNull(wnd);
		assertTrue(wnd.getThemeNames().contains(WindowVariant.FULL_HEIGHT.getVariantName()));

		wnd = Window.builder().fullWidth().build();
		assertTrue(wnd.getThemeNames().contains(WindowVariant.FULL_WIDTH.getVariantName()));

		wnd = Window.builder().fullWidth().fullHeight().build();
		assertTrue(wnd.getThemeNames().contains(WindowVariant.FULL_WIDTH.getVariantName()));
		assertTrue(wnd.getThemeNames().contains(WindowVariant.FULL_HEIGHT.getVariantName()));

		wnd = Window.builder().fullSize().build();
		assertTrue(wnd.getThemeNames().containsAll(
				Arrays.asList(WindowVariant.FULL_WIDTH.getVariantName(), WindowVariant.FULL_HEIGHT.getVariantName())));

		wnd = Window.builder().height("250px").build();
		assertEquals("250px", wnd.getHeight());

		wnd = Window.builder().height(250, Unit.PIXELS).build();
		assertEquals("250px", wnd.getHeight());

		wnd = Window.builder().width("250px").build();
		assertEquals("250px", wnd.getWidth());

		wnd = Window.builder().width(250, Unit.PIXELS).build();
		assertEquals("250px", wnd.getWidth());

		wnd = Window.builder().height("250px").heightUndefined().build();
		assertNull(wnd.getHeight());

		wnd = Window.builder().width("250px").widthUndefined().build();
		assertNull(wnd.getWidth());

		wnd = Window.builder().width("250px").height("250px").sizeUndefined().build();
		assertNull(wnd.getHeight());
		assertNull(wnd.getWidth());
	}

	@Test
	public void testStyles() {
		Window wnd = Window.builder().styleName("style1").build();
		assertNotNull(wnd);

		assertTrue(wnd.getClassName().equals("style1"));

		wnd = Window.builder().styleName("style1").styleName("style2").build();
		assertTrue(wnd.getClassNames().contains("style1"));
		assertTrue(wnd.getClassNames().contains("style2"));

		wnd = Window.builder().styleNames("style1", "style2").styleName("style3").build();
		assertTrue(wnd.getClassNames().contains("style1"));
		assertTrue(wnd.getClassNames().contains("style2"));
		assertTrue(wnd.getClassNames().contains("style3"));
	}

}
