package com.holonplatform.artisan.vaadin.flow.export.xls.config;

import com.holonplatform.core.i18n.Caption;

/**
 * XLS file version.
 * 
 * @since 1.0.0
 */
public enum XLSFileVersion {

	@Caption(value = "Excel 95/2003", messageCode = "holon.artisan.export.xls.file.version.xls")
	XLS("xls"),

	@Caption(value = "Excel 2007 or higher", messageCode = "holon.artisan.export.xls.file.version.xlsx")
	XLSX("xlsx");

	private final String extension;

	private XLSFileVersion(String extension) {
		this.extension = extension;
	}

	public String getExtension() {
		return extension;
	}

}
