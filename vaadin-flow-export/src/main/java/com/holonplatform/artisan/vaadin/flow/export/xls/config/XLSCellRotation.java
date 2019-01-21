package com.holonplatform.artisan.vaadin.flow.export.xls.config;

import com.holonplatform.core.i18n.Caption;

/**
 * XLS cell rotation.
 * 
 * @since 1.0.0
 */
public enum XLSCellRotation {

	@Caption(value = "None", messageCode = "holon.artisan.export.xls.cell.rotation.none")
	NONE((short) 0),

	@Caption(value = "90° to the left", messageCode = "holon.artisan.export.xls.cell.rotation.left90")
	LEFT_90((short) -90),

	@Caption(value = "90° to the right", messageCode = "holon.artisan.export.xls.cell.rotation.right90")
	RIGHT_90((short) 90);

	private final short degrees;

	private XLSCellRotation(short degrees) {
		this.degrees = degrees;
	}

	public short getDegrees() {
		return degrees;
	}

}
