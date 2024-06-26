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
package com.holonplatform.artisan.vaadin.flow.components.templates;

import com.holonplatform.artisan.core.utils.Obj;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.littemplate.LitTemplate;

@Tag("holon-input-filter")
@JsModule("./src/holon-input-filter.ts")
public class HolonInputFilter extends LitTemplate {

	private static final long serialVersionUID = -8446468454355099170L;

	public HolonInputFilter() {
		super();
	}

	public void setOperatorComponent(Component operator) {
		Obj.argumentNotNull(operator, "Component must be not null");
		if (operator != null) {
			addToSlot("operator", operator);
		}
	}

	public void setInputComponent(Component input) {
		Obj.argumentNotNull(input, "Component must be not null");
		if (input != null) {
			// input.getElement().getStyle().set("flex-grow", "1");
			input.getElement().getStyle().set("width", "100%");
			addToSlot("input", input);
		}
	}

	public void setAdditionalInputComponent(Component input) {
		Obj.argumentNotNull(input, "Component must be not null");
		if (input != null) {
			// input.getElement().getStyle().set("flex-grow", "1");
			input.getElement().getStyle().set("width", "100%");
			addToSlot("additional-input", input);
		}
	}

	public void setAdditionalInputVisible(boolean visible) {
		if (visible) {
			getElement().setAttribute("with-additional-input", "");
		} else {
			getElement().removeAttribute("with-additional-input");
		}
	}

	public boolean isAdditionalInputVisible() {
		return getElement().hasAttribute("with-additional-input");
	}

	private void addToSlot(String slot, Component... components) {
		for (Component component : components) {
			setSlot(component, slot);
			add(component);
		}
	}

	private void add(Component component) {
		getElement().appendChild(component.getElement());
	}

	private static void setSlot(Component component, String slot) {
		component.getElement().setAttribute("slot", slot);
	}

}
