package com.holonplatform.artisan.vaadin.flow.export.xls.config;

import com.holonplatform.core.i18n.Caption;

public enum CellVerticalAlignment {

	@Caption(value = "Default", messageCode = "holon.artisan.export.xls.cell.vertical.alignemnt.default")
	DEFAULT,

	@Caption(value = "Top", messageCode = "holon.artisan.export.xls.cell.vertical.alignemnt.top")
	TOP,

	@Caption(value = "Center", messageCode = "holon.artisan.export.xls.cell.vertical.alignemnt.center")
	CENTER,

	@Caption(value = "Bottom", messageCode = "holon.artisan.export.xls.cell.vertical.alignemnt.bottom")
	BOTTOM,

	@Caption(value = "Justify", messageCode = "holon.artisan.export.xls.cell.vertical.alignemnt.justify")
	JUSTIFY;

}
