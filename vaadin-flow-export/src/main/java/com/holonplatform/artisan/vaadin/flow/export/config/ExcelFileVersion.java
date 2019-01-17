package com.holonplatform.artisan.vaadin.flow.export.config;

import com.holonplatform.core.i18n.Caption;

public enum ExcelFileVersion {

	@Caption(value = "Excel 95/2003", messageCode = "holon.artisan.export.xls.file.version.xls")
	XLS("xls"),

	@Caption(value = "Excel 2007 or higher", messageCode = "holon.artisan.export.xls.file.version.xlsx")
	XLSX("xlsx");

	private final String extension;

	private ExcelFileVersion(String extension) {
		this.extension = extension;
	}

	public String getExtension() {
		return extension;
	}

}
