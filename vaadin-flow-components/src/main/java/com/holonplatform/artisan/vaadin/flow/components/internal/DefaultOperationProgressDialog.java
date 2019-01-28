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
package com.holonplatform.artisan.vaadin.flow.components.internal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.holonplatform.artisan.core.internal.ArtisanLogger;
import com.holonplatform.artisan.core.operation.Operation;
import com.holonplatform.artisan.core.operation.OperationProgress;
import com.holonplatform.artisan.core.operation.OperationProgressCallback;
import com.holonplatform.artisan.vaadin.flow.UIUtils;
import com.holonplatform.artisan.vaadin.flow.components.OperationProgressDialog;
import com.holonplatform.auth.AuthContext;
import com.holonplatform.core.Context;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.i18n.LocalizationContext;
import com.holonplatform.core.internal.Logger;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.PropertyRendererRegistry;
import com.holonplatform.core.property.PropertyValuePresenterRegistry;
import com.holonplatform.vaadin.flow.components.builders.ButtonConfigurator;
import com.holonplatform.vaadin.flow.components.builders.ButtonConfigurator.BaseButtonConfigurator;
import com.holonplatform.vaadin.flow.i18n.LocalizationProvider;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.shared.communication.PushMode;

/**
 * A {@link Dialog} which can be used to execute an asynchronous operation and notify the operation progress.
 * 
 * @param <T> Operation result type
 * 
 * @since 1.0.0
 */
public class DefaultOperationProgressDialog<T> extends Dialog implements OperationProgressDialog<T> {

	private static final long serialVersionUID = 4036015960558648086L;

	protected static final Logger LOGGER = ArtisanLogger.create();

	private static final int DEFAULT_POLLING_INTERVAL = 200;

	private static final Localizable ABORT_BUTTON_TEXT = Localizable.of("Abort",
			"com.holonplatform.artisan.vaadin.flow.components.operation.progress.dialog.action.abort");

	private final VerticalLayout content;
	private final Div message;
	private final ProgressBar progressBar;
	private final HorizontalLayout toolbar;
	private final Button abortButton;

	private Localizable text;
	private Consumer<BaseButtonConfigurator> abortConfigurator;

	private List<Consumer<Map<String, Object>>> contextResourcesConfigurators = new LinkedList<>();

	private volatile OperationProgress operationProgress = OperationProgress.PROCEED;

	private final Operation<T> operation;

	/**
	 * Constructor.
	 * @param operation The operation to execute
	 */
	public DefaultOperationProgressDialog(Operation<T> operation) {
		super();
		ObjectUtils.argumentNotNull(operation, "The operation must be not null");
		this.operation = operation;

		// id
		setId("holon-artisan-operation-progress-dialog");

		setCloseOnEsc(false);
		setCloseOnOutsideClick(false);

		this.message = new Div();
		this.message.addClassName("message");

		this.progressBar = new ProgressBar();
		this.progressBar.setWidth("100%");
		this.progressBar.setMin(0);
		this.progressBar.setIndeterminate(false);

		this.toolbar = new HorizontalLayout();
		this.toolbar.addClassName("toolbar");
		this.toolbar.setAlignItems(Alignment.END);

		this.abortButton = new Button();
		this.abortButton.setIcon(VaadinIcon.CLOSE.create());
		this.abortButton.addClassName("abort");
		this.abortButton.addClickListener(e -> onAbortRequested());
		this.abortButton.setDisableOnClick(true);
		this.abortButton.setVisible(false);

		this.toolbar.add(this.abortButton);

		this.content = new VerticalLayout();
		this.content.addClassName("operation-progress-dialog");
		this.content.setSpacing(true);

		this.content.add(this.message);
		this.content.add(this.progressBar);
		this.content.add(this.toolbar);
		this.content.setAlignSelf(Alignment.START, this.message);
		this.content.setAlignSelf(Alignment.START, this.progressBar);
		this.content.setAlignSelf(Alignment.END, this.toolbar);

		this.toolbar.setVisible(false);

		add(content);
	}

	/**
	 * Set the text to display.
	 * @param text The text to set
	 */
	public void setText(Localizable text) {
		this.text = text;
	}

	/**
	 * Set whether the operation is abortable, showing the abort button accordingly.
	 * @param abortable Whether the operation is abortable
	 */
	public void setAbortable(boolean abortable) {
		this.toolbar.setVisible(abortable);
		this.abortButton.setVisible(abortable);
	}

	/**
	 * Set the abort button configurator.
	 * @param abortConfigurator the abort button configurator to set
	 */
	public void setAbortConfigurator(Consumer<BaseButtonConfigurator> abortConfigurator) {
		this.abortConfigurator = abortConfigurator;
	}

	/**
	 * Add a context resources configurator.
	 * @param contextResourcesConfigurator the context resources configurator to add
	 */
	public void addContextResourcesConfigurator(Consumer<Map<String, Object>> contextResourcesConfigurator) {
		ObjectUtils.argumentNotNull(contextResourcesConfigurator, "Context resources configurator must be not null");
		this.contextResourcesConfigurators.add(contextResourcesConfigurator);
	}

	/**
	 * Get the text to display, if any.
	 * @return the optional text
	 */
	protected Optional<Localizable> getText() {
		return Optional.ofNullable(text);
	}

	/**
	 * Get the abort button configurator, if available.
	 * @return Optional abort button configurator
	 */
	protected Optional<Consumer<BaseButtonConfigurator>> getAbortConfigurator() {
		return Optional.ofNullable(abortConfigurator);
	}

	/**
	 * Get the context resources configurator, if available.
	 * @return Optional context resources configurator
	 */
	protected List<Consumer<Map<String, Object>>> getContextResourcesConfigurators() {
		return contextResourcesConfigurators;
	}

	/**
	 * Invoked when the abort action is performed.
	 */
	protected void onAbortRequested() {
		this.operationProgress = OperationProgress.ABORT;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.components.OperationProgressDialog#execute()
	 */
	@Override
	public CompletionStage<T> execute() {
		// configure text
		getText().ifPresent(t -> message.setText(LocalizationProvider.localize(t).orElse("")));
		// configure button
		final BaseButtonConfigurator configurator = ButtonConfigurator.configure(this.abortButton);
		configurator.text(ABORT_BUTTON_TEXT);
		getAbortConfigurator().ifPresent(c -> c.accept(configurator));

		// open the dialog
		open();

		// UI
		final UI ui = getUI().orElseGet(() -> UI.getCurrent());
		if (ui == null) {
			close();
			throw new IllegalStateException("No UI available");
		}

		// context resources
		final Map<String, Object> contextResources = new HashMap<>();
		LocalizationContext.getCurrent().ifPresent(c -> contextResources.put(LocalizationContext.CONTEXT_KEY, c));
		AuthContext.getCurrent().ifPresent(c -> contextResources.put(AuthContext.CONTEXT_KEY, c));
		contextResources.put(PropertyValuePresenterRegistry.CONTEXT_KEY, PropertyValuePresenterRegistry.get());
		contextResources.put(PropertyRendererRegistry.CONTEXT_KEY, PropertyRendererRegistry.get());

		getContextResourcesConfigurators().forEach(c -> c.accept(contextResources));

		final boolean usePolling = (ui.getPushConfiguration().getPushMode() == PushMode.DISABLED)
				&& ui.getPollInterval() < 0;

		// setup
		if (usePolling) {
			UIUtils.access(ui, theUi -> {
				theUi.setPollInterval(DEFAULT_POLLING_INTERVAL);
			});
		}

		return CompletableFuture.supplyAsync(new OperationRunner<>(ui, this, usePolling, progressBar, operation,
				contextResources, () -> operationProgress), Executors.newSingleThreadExecutor());

	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.holonplatform.artisan.vaadin.flow.components.OperationProgressDialog#execute(java.util.function.Consumer)
	 */
	@Override
	public void execute(Consumer<T> thenExecute) {
		ObjectUtils.argumentNotNull(thenExecute, "The Consumer must be not null");
		final UI ui = getUI().orElseGet(() -> UI.getCurrent());
		execute().thenAccept(result -> {
			UIUtils.access(ui, theUi -> {
				thenExecute.accept(result);
			});
		});
	}

	/**
	 * Operation runner.
	 * @param <V> Operation result type
	 */
	private final class OperationRunner<V> implements Supplier<V>, OperationProgressCallback {

		private final UI ui;
		private final Dialog dialog;
		private final boolean usePolling;
		private final ProgressBar progress;
		private final Operation<V> task;
		private final Map<String, Object> contextResources;
		private final Supplier<OperationProgress> operationProgressSupplier;

		/**
		 * Constructor.
		 * @param ui The UI reference
		 * @param dialog The dialog
		 * @param usePolling Whether to use UI polling
		 * @param progress The progress bar to use to notify the operation progress
		 * @param task The operation to execute
		 * @param contextResources Optional context resources to bind to the thread scope
		 * @param operationProgressSupplier The operation progress supplier
		 */
		public OperationRunner(UI ui, Dialog dialog, boolean usePolling, ProgressBar progress, Operation<V> task,
				Map<String, Object> contextResources, Supplier<OperationProgress> operationProgressSupplier) {
			super();
			ObjectUtils.argumentNotNull(ui, "The UI must be not null");
			ObjectUtils.argumentNotNull(dialog, "The Dialog must be not null");
			ObjectUtils.argumentNotNull(progress, "The ProgressBar must be not null");
			ObjectUtils.argumentNotNull(task, "The operation must be not null");
			ObjectUtils.argumentNotNull(operationProgressSupplier, "The operation progress supplier must be not null");
			ObjectUtils.argumentNotNull(contextResources, "Context resources map must be not null");
			this.ui = ui;
			this.dialog = dialog;
			this.usePolling = usePolling;
			this.progress = progress;
			this.task = task;
			this.operationProgressSupplier = operationProgressSupplier;
			this.contextResources = contextResources;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.function.Supplier#get()
		 */
		@Override
		public V get() {
			// context resources
			Context.get().threadScope().ifPresent(scope -> {
				this.contextResources.entrySet().forEach(e -> {
					scope.put(e.getKey(), e.getValue());
				});
			});

			// execute
			try {
				return this.task.execute(OperationRunner.this);
			} finally {
				// close dialog
				UIUtils.access(this.ui, ui -> {
					this.dialog.close();
				});
				// clean up resources
				Context.get().threadScope().ifPresent(scope -> {
					this.contextResources.keySet().forEach(k -> {
						scope.remove(k);
					});
				});
				// check polling
				if (usePolling) {
					UIUtils.access(ui, theUi -> {
						theUi.setPollInterval(-1);
					});
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.core.operation.OperationProgressCallback#onProgress(int, int)
		 */
		@Override
		public OperationProgress onProgress(int totalSteps, int completedSteps) {
			final OperationProgress state = operationProgressSupplier.get();
			// check aborted
			if (state != null && OperationProgress.ABORT == state) {
				return state;
			}
			UIUtils.access(this.ui, ui -> {
				if (totalSteps <= 0) {
					// indeterminate
					if (!this.progress.isIndeterminate()) {
						this.progress.setIndeterminate(true);
					}
				} else {
					if (this.progress.isIndeterminate()) {
						this.progress.setIndeterminate(false);
					}
					double total = totalSteps;
					// check total
					if (this.progress.getMax() != total) {
						this.progress.setMax(total);
					}
					double value = (completedSteps < 0) ? 0 : completedSteps;
					this.progress.setValue(value);
				}
			});
			return state;
		}

	}

}
