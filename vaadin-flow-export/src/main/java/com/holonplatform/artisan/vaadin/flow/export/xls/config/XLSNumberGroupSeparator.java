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
package com.holonplatform.artisan.vaadin.flow.export.xls.config;

import com.holonplatform.core.i18n.Caption;

/**
 * XLS group separator mode.
 * 
 * @since 1.0.0
 */
public enum XLSNumberGroupSeparator {

	@Caption(value = "Default", messageCode = "holon.artisan.export.xls.group.separator.default")
	DEFAULT,

	@Caption(value = "Enabled", messageCode = "holon.artisan.export.xls.group.separator.enabled")
	ENABLED,

	@Caption(value = "Disabled", messageCode = "holon.artisan.export.xls.group.separator.disabled")
	DISABLED;

}
