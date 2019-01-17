package com.holonplatform.artisan.vaadin.flow.export.config;

import com.holonplatform.core.i18n.Caption;

public enum CellRotation {

	@Caption(value = "None", messageCode = "holon.artisan.export.xls.cell.rotation.none")
	NONE,

	@Caption(value = "90° to the left", messageCode = "holon.artisan.export.xls.cell.rotation.left90")
	LEFT_90,

	@Caption(value = "90° to the right", messageCode = "holon.artisan.export.xls.cell.rotation.right90")
	RIGHT_90;

}
