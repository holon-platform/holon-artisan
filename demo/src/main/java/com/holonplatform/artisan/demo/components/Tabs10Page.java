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
package com.holonplatform.artisan.demo.components;

import com.holonplatform.artisan.demo.root.Menu;
import com.holonplatform.artisan.vaadin.flow.components.TabLayout;
import com.holonplatform.vaadin.flow.components.Components;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "tabs10", layout = Menu.class)
public class Tabs10Page extends Div {

	private static final long serialVersionUID = 1L;

	public Tabs10Page() {
		super();
		setSizeFull();

		VerticalLayout vl = new VerticalLayout();
		vl.setWidth("100%");
		vl.add(Components.label().text("\r\n" + "\r\n"
				+ "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus tempor erat vehicula, cursus justo non, viverra orci. Sed eget mauris eu sem tempus vehicula vel ac lorem. Suspendisse potenti. Cras dictum mattis turpis, ut imperdiet elit consectetur vel. Sed sit amet libero quis neque cursus sollicitudin. Pellentesque vel ultrices libero. Nulla consequat leo quis aliquam viverra. Pellentesque pellentesque viverra neque.\r\n"
				+ "\r\n"
				+ "Duis blandit pharetra sem eu ultricies. Suspendisse eu placerat massa. Fusce tincidunt purus mauris, ac consectetur mi vestibulum eu. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Quisque posuere viverra leo sit amet tempor. Etiam quis tortor enim. Morbi eleifend enim tortor, ac accumsan ligula maximus at.\r\n"
				+ "\r\n"
				+ "Ut hendrerit ante quis lorem rhoncus lacinia. Nam condimentum euismod velit, eget euismod elit tempus et. Sed id velit in sem finibus tincidunt. Morbi nibh sem, posuere sed vulputate sed, interdum eget augue. Nulla vel ipsum vitae velit auctor fermentum. Sed in eros quis mi viverra vestibulum et at erat. Lorem ipsum dolor sit amet, consectetur adipiscing elit.\r\n"
				+ "\r\n"
				+ "Quisque aliquam posuere massa sit amet sodales. Suspendisse erat lacus, tempus sit amet libero non, sodales consequat leo. Nullam vestibulum laoreet urna. Quisque mollis metus felis, in euismod ipsum ultricies lobortis. Aenean vel velit et risus mollis finibus. Curabitur efficitur velit tortor, in faucibus purus imperdiet a. Mauris ante erat, finibus eu tellus id, tincidunt placerat sapien. Sed porttitor in arcu quis mattis. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.\r\n"
				+ "\r\n"
				+ "Aliquam quis velit ut dui accumsan dignissim. Nulla mattis et nisl non blandit. Sed fringilla, ipsum vel laoreet porttitor, felis nunc interdum metus, a euismod turpis arcu commodo risus. Suspendisse mi libero, congue porta interdum euismod, auctor ut ante. Aliquam ornare eros eget nunc molestie malesuada. Ut feugiat neque ac lobortis pellentesque. Quisque pretium egestas augue, nec ultrices odio tristique sed. Praesent eleifend tortor sit amet diam fermentum mollis. Pellentesque id dui tellus. Vestibulum eget pretium purus. Pellentesque porta odio nec magna ullamcorper, in aliquam velit fringilla. ")
				.build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());
		vl.add(Components.label().text("A text").build());

		vl.add(Components.hl().spacing().fullWidth().wrap()
				.add(Components.button().text("Button one").width("100%").build(),
						Components.button().text("Button two").width("100%").build(),
						Components.button().text("Button three").width("100%").build())
				.build());

		add(TabLayout.builder().fullSize()
				// tab 1
				.withTab(vl).scrollable().label("Tab 1").add()
				// tab 1
				.withTab(Components.label().text("Tab two").build()).label("Tab 2").add()
				// selected
				.build().getComponent());
	}

}
