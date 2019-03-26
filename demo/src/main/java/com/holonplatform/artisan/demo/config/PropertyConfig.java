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
package com.holonplatform.artisan.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.holonplatform.artisan.demo.model.Product;
import com.holonplatform.artisan.vaadin.flow.export.xls.PropertyXLSValueProviderRegistry;

@Configuration
public class PropertyConfig {

	@Bean
	public PropertyXLSValueProviderRegistry propertyXLSValueProviderRegistry() {
		PropertyXLSValueProviderRegistry registry = PropertyXLSValueProviderRegistry.create(true);
		registry.forProperty(Product.CATEGORY, new CategoryXLSValueProvider());
		return registry;
	}

}
