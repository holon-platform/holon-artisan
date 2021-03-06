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

import java.util.concurrent.Callable;

import com.holonplatform.artisan.core.exceptions.OperationExecutionException;
import com.holonplatform.artisan.core.operation.Operation;
import com.holonplatform.artisan.demo.root.Menu;
import com.holonplatform.artisan.vaadin.flow.components.OperationProgressDialog;
import com.holonplatform.vaadin.flow.components.Components;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "progress", layout = Menu.class)
public class OperationProgressDialogPage extends VerticalLayout {

	private static final long serialVersionUID = -6654611442660393224L;

	private final static Operation<String> OPERATION1 = callback -> {
		try {
			callback.onProgress(3, 0);
			Thread.sleep(2000);
			callback.onProgress(3, 1);
			Thread.sleep(2000);
			callback.onProgress(3, 2);
			Thread.sleep(2000);
			callback.onProgress(3, 3);
			return "DONE";
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	};

	private final static Runnable OPERATION2 = () -> {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	};

	private final static Operation<String> OPERATION3 = callback -> {
		try {
			callback.onProgress(3, 0);
			Thread.sleep(2000);
			callback.onProgress(3, 1);

			throw new OperationExecutionException("Test error");

		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	};

	private static final Callable<String> OPERATION4 = () -> {
		try {
			Thread.sleep(4000);
			return "Callable DONE";
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	};

	public OperationProgressDialogPage() {
		super();

		Button btn = new Button("Operation progress steps");
		btn.addClickListener(e -> {
			OperationProgressDialog.builder(OPERATION1).text("Operation running...").execute(r -> Notification.show(r));
		});
		add(btn);

		btn = new Button("Operation progress steps with abort");
		btn.addClickListener(e -> {
			OperationProgressDialog.builder(OPERATION1).text("Operation running...").abortable(true).execute();
		});
		add(btn);

		btn = new Button("Operation without progress steps");
		btn.addClickListener(e -> {
			OperationProgressDialog.builder(OPERATION2).text("Operation running...").execute();
		});
		add(btn);

		btn = new Button("Operation with exception");
		btn.addClickListener(e -> {
			OperationProgressDialog.builder(OPERATION3).text("Operation running...").execute(r -> Notification.show(r));
		});
		add(btn);

		btn = Components.button("Operation callable without progress",
				e -> OperationProgressDialog.builder(OPERATION4).text("Callable operation running...").abortable(true)
						.abortButtonConfigurator(bbc -> bbc.text("Abort operation").description("Click to abort"))
						.execute(r -> Notification.show(r)));
		add(btn);
	}

}
