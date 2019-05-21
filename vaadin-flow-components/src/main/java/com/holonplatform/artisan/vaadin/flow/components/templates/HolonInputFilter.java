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
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("holon-input-filter")
@HtmlImport("frontend://src/holon-input-filter.html")
public class HolonInputFilter extends PolymerTemplate<TemplateModel> {

	private static final long serialVersionUID = -8446468454355099170L;

	private Component operator;
	private Component input;

	public HolonInputFilter() {
		super();
	}

	public void setOperatorComponent(Component operator) {
		Obj.argumentNotNull(operator, "Component must be not null");
		removeOperator();
		if (operator != null) {
			addToSlot("operator", operator);
        }
	}
	
	public void setInputComponent(Component input) {
		Obj.argumentNotNull(input, "Component must be not null");
		removeInput();
		if (input != null) {
			input.getElement().getStyle().set("flex-grow", "1");
			addToSlot("input", input);
        }
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

	private void removeOperator() {
        remove(this.operator);
        this.operator = null;
    }
	
	private void removeInput() {
        remove(this.input);
        this.input = null;
    }

	private static void remove(Component component) {
		if (component != null) {
			component.getElement().removeFromParent();
		}
	}

    private static void setSlot(Component component, String slot) {
        component.getElement().setAttribute("slot", slot);
    }
	
}
