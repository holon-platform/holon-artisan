package com.holonplatform.artisan.vaadin.flow.export.xls.config;

import com.holonplatform.core.i18n.Caption;

public enum CellAlignment {

	/**
	 * Default.
	 * <p>
	 * Text data is left-aligned. Numbers, dates, and times are rightaligned. Boolean types are centered.
	 * </p>
	 */
	@Caption(value = "Default", messageCode = "holon.artisan.export.xls.cell.alignemnt.default")
	DEFAULT,

	/**
	 * Left alignment.
	 */
	@Caption(value = "Left", messageCode = "holon.artisan.export.xls.cell.alignemnt.left")
	LEFT,

	/**
	 * Centered alignment.
	 */
	@Caption(value = "Center", messageCode = "holon.artisan.export.xls.cell.alignemnt.center")
	CENTER,

	/**
	 * Right alignment.
	 */
	@Caption(value = "Right", messageCode = "holon.artisan.export.xls.cell.alignemnt.right")
	RIGHT,

	/**
	 * Indicates that the value of the cell should be filled across the entire width of the cell.
	 */
	@Caption(value = "Fill", messageCode = "holon.artisan.export.xls.cell.alignemnt.fill")
	FILL,

	/**
	 * Justified alignment (flush left and right).
	 */
	@Caption(value = "Justify", messageCode = "holon.artisan.export.xls.cell.alignemnt.justify")
	JUSTIFY;

}
