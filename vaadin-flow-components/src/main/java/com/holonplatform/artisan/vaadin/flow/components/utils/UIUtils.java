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
package com.holonplatform.artisan.vaadin.flow.components.utils;

import java.io.Serializable;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import com.holonplatform.artisan.core.utils.Obj;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.shared.communication.PushMode;

/**
 * Utility class for UI related operations.
 *
 * @since 1.0.0
 */
public final class UIUtils implements Serializable {

	private static final long serialVersionUID = 4009975396105684460L;

	private UIUtils() {
	}

	/**
	 * Provides exclusive access to this UI from outside a request handling thread.
	 * @param ui The UI (not null)
	 * @param task The task to execute
	 * @return A future that can be used to check for task completion and to cancel the task
	 */
	public static Future<Void> access(UI ui, final Consumer<UI> task) {
		Obj.argumentNotNull(ui, "UI must be not null");
		return ui.access(() -> {
			task.accept(ui);
			// check push
			final PushMode pushMode = ui.getPushConfiguration().getPushMode();
			if (PushMode.MANUAL == pushMode) {
				ui.push();
			}
		});
	}

}
