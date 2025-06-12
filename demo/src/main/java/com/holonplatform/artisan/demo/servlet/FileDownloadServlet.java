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
package com.holonplatform.artisan.demo.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/download")
public class FileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String PARAMETER_FILE_NAME = "fn";
	public static final String PARAMETER_FILE_PATH = "fp";
	public static final String PARAMETER_FILE_REMOVE = "rm";
	public static final String PARAMETER_FILE_MIME_TYPE = "ct";

	private static final Logger LOGGER = LoggerFactory.getLogger(FileDownloadServlet.class);

	private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

	private static final int BUFFER = 10240;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// file name
		final String fileName = getRequestParameter(request, PARAMETER_FILE_NAME).orElse(null);
		if (fileName == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing file name");
			return;
		}

		// file path (defaults to temp)
		final String filePath = getRequestParameter(request, PARAMETER_FILE_PATH)
				.orElseGet(() -> System.getProperty("java.io.tmpdir"));

		// remove after download
		final boolean removeAfterDownload = getRequestParameter(request, PARAMETER_FILE_REMOVE)
				.map(v -> v.equalsIgnoreCase("true")).orElse(false);

		// content type
		final String contentType = getRequestParameter(request, PARAMETER_FILE_MIME_TYPE).orElse(DEFAULT_CONTENT_TYPE);

		final String fileCompletePath = filePath + fileName;

		// check file exists
		final File file = new File(fileCompletePath);
		if (!file.exists()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File not found [" + fileCompletePath + "]");
			return;
		}

		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");

		try (FileInputStream is = new FileInputStream(file)) {
			try (OutputStream os = response.getOutputStream()) {
				int read = 0;
				byte[] bytes = new byte[BUFFER];
				while ((read = is.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}
				os.flush();
			}
		} finally {
			if (removeAfterDownload) {
				try {
					file.delete();
				} catch (Exception e) {
					LOGGER.error("Failed to remove file [" + fileCompletePath + "] after download", e);
				}
			}
		}

	}

	private static Optional<String> getRequestParameter(HttpServletRequest request, String name) {
		if (name != null) {
			String value = request.getParameter(name);
			if (value != null && !value.trim().equals("")) {
				try {
					return Optional.ofNullable(URLDecoder.decode(value.trim(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					LOGGER.error("Failed to decode servlet request parameter [" + name + "]", e);
				}
			}
		}
		return Optional.empty();
	}

	private static String encodeRequestParameter(String value) {
		if (value == null) {
			return "";
		}
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Failed to encode servlet request parameter [" + value + "]", e);
			return value;
		}
	}

	public static Builder build() {
		return build("/");
	}

	public static Builder build(String servletBasePath) {
		return new DefaultBuilder(servletBasePath);
	}

	public interface Builder {

		Builder fileName(String fileName);

		Builder filePath(String filePath);

		Builder fileType(String mimeType);

		Builder removeAfterDowload();

		String build();

	}

	private static class DefaultBuilder implements Builder {

		private final StringBuilder sb;
		private boolean firstParameter = true;

		public DefaultBuilder(String servletBasePath) {
			super();
			if (servletBasePath == null) {
				throw new IllegalArgumentException("Servlet base path must be not null");
			}
			sb = new StringBuilder();
			sb.append(servletBasePath);
			if (!servletBasePath.endsWith("/")) {
				sb.append("/");
			}
			sb.append("download");
		}

		private Builder addRequestParameter(String name, String value) {
			if (firstParameter) {
				sb.append("?");
				firstParameter = false;
			} else {
				sb.append("&");
			}
			sb.append(name);
			sb.append("=");
			sb.append(encodeRequestParameter(value));
			return this;
		}

		@Override
		public Builder fileName(String fileName) {
			return addRequestParameter(PARAMETER_FILE_NAME, fileName);
		}

		@Override
		public Builder filePath(String filePath) {
			return addRequestParameter(PARAMETER_FILE_PATH, filePath);
		}

		@Override
		public Builder fileType(String mimeType) {
			return addRequestParameter(PARAMETER_FILE_MIME_TYPE, mimeType);
		}

		@Override
		public Builder removeAfterDowload() {
			return addRequestParameter(PARAMETER_FILE_REMOVE, "true");
		}

		@Override
		public String build() {
			return sb.toString();
		}

	}

}
