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
package com.holonplatform.artisan.demo.components;

import com.holonplatform.artisan.demo.root.Menu;
import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterOperator;
import com.holonplatform.core.property.NumericProperty;
import com.holonplatform.core.property.StringProperty;
import com.holonplatform.core.query.QueryFilter;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "filters", layout = Menu.class)
public class InputFilterPage extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private static final StringProperty STR = StringProperty.create("str").message("String value");
	private static final StringProperty STR2 = StringProperty.create("str2").message("String value 2");
	private static final NumericProperty<Integer> ITG = NumericProperty.integerType("itg").message("Integer value");
	private static final NumericProperty<Double> DBL = NumericProperty.doubleType("dbl").message("Double value");

	public InputFilterPage() {
		super();

		add(buildRow(InputFilter.string(STR).build()));
		add(buildRow(
				InputFilter.string(STR2).maxLength(3).filterOperatorSelectConfiguration(c -> c.width("5em")).build(),
				true));
		add(buildRow(InputFilter.number(ITG).build()));
		add(buildRow(InputFilter.number(DBL).defaultOperator(InputFilterOperator.GREATER_OR_EQUAL).build()));

	}

	private static Component buildRow(InputFilter<?> i) {
		return buildRow(i, false);
	}

	private static Component buildRow(InputFilter<?> i, boolean full) {
		final HorizontalLayout hl = new HorizontalLayout();
		if (full) {
			hl.setWidthFull();
		}
		hl.setAlignItems(Alignment.BASELINE);
		hl.add(i.getComponent());
		if (full) {
			hl.setFlexGrow(1, i.getComponent());
		}
		hl.add(new Button("Get filter", e -> {
			final QueryFilter f = i.getFilter().orElse(null);
			Notification.show("Filter: [" + ((f == null) ? "NONE" : f) + "]");
		}));
		hl.add(new Button("Clear", e -> i.clear()));
		hl.add(new Button("Reset", e -> i.reset()));
		return hl;
	}

}
