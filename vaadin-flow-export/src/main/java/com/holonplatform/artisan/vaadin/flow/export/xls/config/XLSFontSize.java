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
package com.holonplatform.artisan.vaadin.flow.export.xls.config;

import com.holonplatform.core.i18n.Caption;

/**
 * XLS font size.
 * 
 * @since 1.0.0
 */
public enum XLSFontSize {

	@Caption(value = "Auto", messageCode = "holon.artisan.export.xls.font.size.automatic")
	AUTOMATIC((short) -1),

	@Caption(value = "8", messageCode = "holon.artisan.export.xls.font.size.8")
	EIGHT((short) 8),

	@Caption(value = "9", messageCode = "holon.artisan.export.xls.font.size.9")
	NINE((short) 9),

	@Caption(value = "10", messageCode = "holon.artisan.export.xls.font.size.10")
	TEN((short) 10),

	@Caption(value = "11", messageCode = "holon.artisan.export.xls.font.size.11")
	ELEVEN((short) 11),

	@Caption(value = "12", messageCode = "holon.artisan.export.xls.font.size.12")
	TWELVE((short) 12),

	@Caption(value = "14", messageCode = "holon.artisan.export.xls.font.size.14")
	FOURTEEN((short) 14),

	@Caption(value = "16", messageCode = "holon.artisan.export.xls.font.size.16")
	SIXTEEN((short) 16),

	@Caption(value = "18", messageCode = "holon.artisan.export.xls.font.size.18")
	EIGHTEEN((short) 18),

	@Caption(value = "20", messageCode = "holon.artisan.export.xls.font.size.20")
	TWENTY((short) 20),

	@Caption(value = "22", messageCode = "holon.artisan.export.xls.font.size.22")
	TWENTYTWO((short) 22),

	@Caption(value = "24", messageCode = "holon.artisan.export.xls.font.size.24")
	TWENTYFOUR((short) 24),

	@Caption(value = "26", messageCode = "holon.artisan.export.xls.font.size.26")
	TWENTYSIX((short) 26),

	@Caption(value = "28", messageCode = "holon.artisan.export.xls.font.size.28")
	TWENTYEIGHT((short) 28),

	@Caption(value = "36", messageCode = "holon.artisan.export.xls.font.size.36")
	THIRTYSIX((short) 36),

	@Caption(value = "48", messageCode = "holon.artisan.export.xls.font.size.48")
	FOURTYEIGHT((short) 48),

	@Caption(value = "72", messageCode = "holon.artisan.export.xls.font.size.72")
	SEVENTYTWO((short) 72);

	private final short points;

	private XLSFontSize(short points) {
		this.points = points;
	}

	/**
	 * Get the font size in points.
	 * @return the font size in points
	 */
	public short getPoints() {
		return points;
	}

	/**
	 * Checks whether this value represents the {@link #AUTOMATIC} value.
	 * @return Whether this value is the {@link #AUTOMATIC} value
	 */
	public boolean isAuto() {
		return this == XLSFontSize.AUTOMATIC;
	}

}
