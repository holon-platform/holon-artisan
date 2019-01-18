package com.holonplatform.artisan.vaadin.flow.export.xls.config;

import com.holonplatform.core.i18n.Caption;

public enum CellBorder {

	@Caption(value = "None", messageCode = "holon.artisan.export.xls.cell.border.none")
	NONE,

	@Caption(value = "Thin", messageCode = "holon.artisan.export.xls.cell.border.thin")
	THIN,

	@Caption(value = "Medium", messageCode = "holon.artisan.export.xls.cell.border.medium")
	MEDIUM,

	@Caption(value = "Thick", messageCode = "holon.artisan.export.xls.cell.border.thick")
	THICK,

	@Caption(value = "Dashed", messageCode = "holon.artisan.export.xls.cell.border.dashed")
	DASHED,

	@Caption(value = "Dotted", messageCode = "holon.artisan.export.xls.cell.border.dotted")
	DOTTED,

	@Caption(value = "Dash and dot", messageCode = "holon.artisan.export.xls.cell.border.dashdot")
	DASH_DOT,

	@Caption(value = "Dash-dot-dot", messageCode = "holon.artisan.export.xls.cell.border.dashdotdot")
	DASH_DOT_DOT,

	@Caption(value = "Double", messageCode = "holon.artisan.export.xls.cell.border.double")
	DOUBLE,

	@Caption(value = "Medium dashed", messageCode = "holon.artisan.export.xls.cell.border.mediumdashed")
	MEDIUM_DASHED,

	@Caption(value = "Slanted dash-dot", messageCode = "holon.artisan.export.xls.cell.border.slanted")
	SLANTED_DASH_DOT,

	@Caption(value = "Hair line", messageCode = "holon.artisan.export.xls.cell.border.hair")
	HAIR;

}
