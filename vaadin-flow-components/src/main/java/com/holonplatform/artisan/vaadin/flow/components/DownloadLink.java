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
package com.holonplatform.artisan.vaadin.flow.components;

import java.io.OutputStream;
import java.io.Serializable;

import com.holonplatform.artisan.vaadin.flow.components.internal.DefaultDownloadLink;
import com.holonplatform.vaadin.flow.components.HasComponent;
import com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasEnabledConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasSizeConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasStyleConfigurator;
import com.holonplatform.vaadin.flow.components.builders.HasTextConfigurator;
import com.vaadin.flow.component.HasEnabled;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.html.Anchor;

/**
 * A link (using an {@link Anchor} component) which can be used to download a file.
 * 
 * @since 1.0.2
 */
public interface DownloadLink extends HasComponent, HasEnabled, HasText, HasSize, HasStyle {

	/**
	 * Gets the href (the URL) of this link.
	 * @return the href value
	 */
	String getHref();

	/**
	 * Get the file name to use when the content is downloaded.
	 * @return The file name
	 */
	String getFileName();
	
	/**
	 * Get a builder to create a new {@link DownloadLink}.
	 * @param contentProvider The function to use to provide the content to download when the link is clicked (not null)
	 * @return A new {@link Builder}
	 */
	static Builder builder(ContentProvider contentProvider) {
		return new DefaultDownloadLink.DefaultBuilder(contentProvider);
	}

	/**
	 * Download content provider.
	 */
	@FunctionalInterface
	public interface ContentProvider extends Serializable {

		/**
		 * Provide the download content, writing it to the download {@link OutputStream}.
		 * @param outputStream The download output stream
		 * @throws Exception If an error occurred
		 */
		void provideContent(OutputStream outputStream) throws Exception;

	}

	/**
	 * {@link DownloadLink} builder.
	 */
	public interface Builder extends ComponentConfigurator<Builder>, HasTextConfigurator<Builder>,
			HasStyleConfigurator<Builder>, HasSizeConfigurator<Builder>, HasEnabledConfigurator<Builder> {

		/**
		 * Set the file name to use when the content is downloaded.
		 * @param fileName The file name to set
		 * @return this
		 */
		Builder fileName(String fileName);

		/**
		 * Build the {@link DownloadLink}.
		 * @return A new {@link DownloadLink} instance
		 */
		DownloadLink build();

	}

}
