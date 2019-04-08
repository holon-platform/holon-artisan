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

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import com.holonplatform.artisan.core.utils.Obj;
import com.holonplatform.artisan.vaadin.flow.components.DownloadLink;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasEnabledConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasTextConfigurator;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.HasEnabled;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.server.RequestHandler;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinSession;

/**
 * Default {@link DownloadLink} implementation.
 * 
 * @since 1.0.2
 */
public class DefaultDownloadLink extends Anchor implements DownloadLink {

	private static final long serialVersionUID = 3551261544236607587L;

	private final String identifier;

	private String fileName;

	protected RequestHandler requestHandler;

	public DefaultDownloadLink(ContentProvider contentProvider) {
		super();
		Obj.argumentNotNull(contentProvider, "Content provider must be not null");
		this.identifier = "h-fdl-" + UUID.randomUUID().toString();
		this.fileName = identifier;
		runBeforeClientResponse(ui -> {
			requestHandler = new RequestHandler() {

				private static final long serialVersionUID = 6040205962194702542L;

				@Override
				public boolean handleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response)
						throws IOException {
					if (request.getPathInfo().endsWith(identifier)) {
						try {
							response.setStatus(200);
							response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
							contentProvider.provideContent(response.getOutputStream());
							return true;
						} catch (IOException ioe) {
							throw ioe;
						} catch (Exception e) {
							throw new IOException("Failed to dowload file [" + fileName + "]", e);
						}
					}
					return false;
				}
			};
			ui.getSession().addRequestHandler(requestHandler);
			setHref("./" + identifier);
		});
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		getUI().get().getSession().removeRequestHandler(requestHandler);
		super.onDetach(detachEvent);
	}

	@Override
	public Component getComponent() {
		return this;
	}

	/**
	 * Set the file name to use when the content is downloaded.
	 * @param fileName The file name to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Get the file name to use when the content is downloaded.
	 * @return The file name
	 */
	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public Optional<HasEnabled> hasEnabled() {
		return Optional.of(this);
	}

	@Override
	public Optional<HasStyle> hasStyle() {
		return Optional.of(this);
	}

	@Override
	public Optional<HasSize> hasSize() {
		return Optional.of(this);
	}

	private void runBeforeClientResponse(SerializableConsumer<UI> command) {
		getElement().getNode().runWhenAttached(ui -> ui.beforeClientResponse(this, context -> command.accept(ui)));
	}

	// ------- builder

	/**
	 * Default {@link Builder} implementation.
	 */
	public static class DefaultBuilder implements Builder {

		private final DefaultDownloadLink link;

		private final BaseComponentConfigurator componentConfigurator;
		private final BaseHasTextConfigurator textConfigurator;
		private final BaseHasStyleConfigurator styleConfigurator;
		private final BaseHasSizeConfigurator sizeConfigurator;
		private final BaseHasEnabledConfigurator enabledConfigurator;

		public DefaultBuilder(ContentProvider contentProvider) {
			super();
			this.link = new DefaultDownloadLink(contentProvider);

			this.componentConfigurator = ComponentConfigurator.create(link);
			this.textConfigurator = HasTextConfigurator.create(link);
			this.styleConfigurator = HasStyleConfigurator.create(link);
			this.sizeConfigurator = HasSizeConfigurator.create(link);
			this.enabledConfigurator = HasEnabledConfigurator.create(link);
		}

		@Override
		public Builder fileName(String fileName) {
			link.setFileName(fileName);
			return this;
		}

		@Override
		public Builder id(String id) {
			componentConfigurator.id(id);
			return this;
		}

		@Override
		public Builder visible(boolean visible) {
			componentConfigurator.visible(visible);
			return this;
		}

		@Override
		public Builder elementConfiguration(Consumer<Element> element) {
			componentConfigurator.elementConfiguration(element);
			return this;
		}

		@Override
		public Builder withAttachListener(ComponentEventListener<AttachEvent> listener) {
			componentConfigurator.withAttachListener(listener);
			return this;
		}

		@Override
		public Builder withDetachListener(ComponentEventListener<DetachEvent> listener) {
			componentConfigurator.withDetachListener(listener);
			return this;
		}

		@Override
		public Builder withThemeName(String themeName) {
			componentConfigurator.withThemeName(themeName);
			return this;
		}

		@Override
		public Builder withEventListener(String eventType, DomEventListener listener) {
			componentConfigurator.withEventListener(eventType, listener);
			return this;
		}

		@Override
		public Builder withEventListener(String eventType, DomEventListener listener, String filter) {
			componentConfigurator.withEventListener(eventType, listener, filter);
			return this;
		}

		@Override
		public Builder text(Localizable text) {
			textConfigurator.text(text);
			return this;
		}

		@Override
		public Builder styleNames(String... styleNames) {
			styleConfigurator.styleNames(styleNames);
			return this;
		}

		@Override
		public Builder styleName(String styleName) {
			styleConfigurator.styleName(styleName);
			return this;
		}

		@Override
		public Builder enabled(boolean enabled) {
			enabledConfigurator.enabled(enabled);
			return this;
		}

		@Override
		public Builder width(String width) {
			sizeConfigurator.width(width);
			return this;
		}

		@Override
		public Builder height(String height) {
			sizeConfigurator.height(height);
			return this;
		}

		@Override
		public Builder minWidth(String minWidth) {
			sizeConfigurator.minWidth(minWidth);
			return this;
		}

		@Override
		public Builder maxWidth(String maxWidth) {
			sizeConfigurator.maxWidth(maxWidth);
			return this;
		}

		@Override
		public Builder minHeight(String minHeight) {
			sizeConfigurator.minHeight(minHeight);
			return this;
		}

		@Override
		public Builder maxHeight(String maxHeight) {
			sizeConfigurator.maxHeight(maxHeight);
			return this;
		}

		@Override
		public DownloadLink build() {
			return link;
		}

	}

}
