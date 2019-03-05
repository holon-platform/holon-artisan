/*
 * Copyright 2000-2017 Holon TDCN.
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
package com.holonplatform.artisan.demo.model;

import com.holonplatform.core.Validator;
import com.holonplatform.core.property.BooleanProperty;
import com.holonplatform.core.property.NumericProperty;
import com.holonplatform.core.property.PropertyBox;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.core.property.StringProperty;

public interface Product {

	public static final NumericProperty<Long> ID = NumericProperty.longType("id").message("Product ID")
			.messageCode("product.id") // Product ID
			// not null value validator
			.withValidator(Validator.notNull());

	public static final StringProperty DESCRIPTION = StringProperty.create("description") // Description
			.message("Description").messageCode("product.description")
			// max 500 characters value validator
			.withValidator(Validator.max(500));

	public static final StringProperty CATEGORY = StringProperty.create("category") // Category
			.message("Category").messageCode("product.category");

	public static final NumericProperty<Double> UNIT_PRICE = NumericProperty.doubleType("price") // Price
			.message("Price").messageCode("product.price")
			// not negative value validator
			.withValidator(Validator.notNegative());

	public static final BooleanProperty WITHDRAWN = BooleanProperty.create("withdrawn") // Withdrawn
			.message("Withdrawn").messageCode("product.withdrawn");

	// Product property set with the ID property as identifier property
	public static final PropertySet<?> PRODUCT = PropertySet.builderOf(ID, DESCRIPTION, CATEGORY, UNIT_PRICE, WITHDRAWN)
			.identifier(ID).build();

	// example items

	public static final PropertyBox ITEM1 = PropertyBox.builder(PRODUCT).set(ID, 1L).set(DESCRIPTION, "The item one")
			.set(CATEGORY, "Category 1").set(UNIT_PRICE, 123.50).build();

	public static final PropertyBox ITEM2 = PropertyBox.builder(PRODUCT).set(ID, 2L).set(DESCRIPTION, "The item two")
			.set(CATEGORY, "Category 1").set(WITHDRAWN, true).build();

	public static final PropertyBox ITEM3 = PropertyBox.builder(PRODUCT).set(ID, 3L).set(DESCRIPTION, "The item three")
			.set(CATEGORY, "Category 2").set(UNIT_PRICE, 45.25).build();

	public static final PropertyBox ITEM4 = PropertyBox.builder(PRODUCT).set(ID, 4L).set(DESCRIPTION, "The item four")
			.set(UNIT_PRICE, 50.00).set(WITHDRAWN, true).build();

	public static final PropertyBox ITEM5 = PropertyBox.builder(PRODUCT).set(ID, 5L).set(DESCRIPTION, "The item five")
			.set(CATEGORY, "Category 3").set(UNIT_PRICE, 100.50).build();

}