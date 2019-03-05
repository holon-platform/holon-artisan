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
package com.holonplatform.artisan.demo.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/download")
public class FileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

	private static final int BUFFER = 10240;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// file name
		final String fileName = request.getParameter("fn");
		if (fileName == null || fileName.trim().equals("")) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing file name");
			return;
		}

		// file path
		String filePath = request.getParameter("fp");
		if (filePath == null || filePath.trim().equals("")) {
			// defaults to temp
			filePath = System.getProperty("java.io.tmpdir");
		}

		// remove after download
		boolean removeAfterDownload = false;
		String remove = request.getParameter("rm");
		if (remove != null && remove.trim().equalsIgnoreCase("true")) {
			removeAfterDownload = true;
		}

		// content type
		String contentType = request.getParameter("ct");
		if (contentType == null || contentType.trim().equals("")) {
			contentType = DEFAULT_CONTENT_TYPE;
		}

		final File file = new File(filePath + fileName);
		if (!file.exists()) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File not found [" + filePath + fileName + "]");
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
					e.printStackTrace();
				}
			}
		}

	}

}
