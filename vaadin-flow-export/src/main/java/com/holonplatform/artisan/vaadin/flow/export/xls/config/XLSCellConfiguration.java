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

import java.io.Serializable;

/**
 * Configuration of a cell.
 *
 * @since 1.0.0
 */
public interface XLSCellConfiguration extends Serializable {

	/**
	 * Get whether the text should be rendered using <em>bold</em> style.
	 * @return Whether the text is <em>bold</em>
	 */
	boolean isBold();

	/**
	 * Get whether the text should be rendered using <em>italic</em> style.
	 * @return Whether the text is <em>italic</em>
	 */
	boolean isItalic();

	/**
	 * Get whether the text should be rendered using <em>underline</em> style.
	 * @return Whether the text is <em>underline</em>
	 */
	boolean isUnderline();

	/**
	 * Get whether the text should be rendered using <em>strikeout</em> style.
	 * @return Whether the text is <em>strikeout</em>
	 */
	boolean isStrikeOut();

	/**
	 * Get whether the text should be wrapped, i.e. to make all content visible within a cell by displaying it on
	 * multiple lines.
	 * @return Whether the text should be wrapped
	 */
	boolean isWrap();

	/**
	 * Get whether the cell should be auto-sized.
	 * @return Whether the cell should be auto-sized
	 */
	boolean isShrinkToFit();

	/**
	 * Get the text size.
	 * @return The text size, {@link XLSFontSize#AUTOMATIC} if not configured
	 */
	XLSFontSize getFontSize();

	/**
	 * Get the text color.
	 * @return The text color, {@link XLSColor#AUTOMATIC} if not configured
	 */
	XLSColor getFontColor();

	/**
	 * Get the cell background color.
	 * @return The cell background color, {@link XLSColor#AUTOMATIC} if not configured
	 */
	XLSColor getBackgroundColor();

	/**
	 * Get the cell horizontal alignment.
	 * @return The cell horizontal alignment, {@link XLSCellAlignment#DEFAULT} if not configured
	 */
	XLSCellAlignment getAlignment();

	/**
	 * Get the cell vertical alignment.
	 * @return The cell vertical alignment, {@link XLSCellVerticalAlignment#DEFAULT} if not configured
	 */
	XLSCellVerticalAlignment getVerticalAlignment();

	/**
	 * Get the cell rotation.
	 * @return The cell rotation, {@link XLSCellRotation#NONE} if not configured
	 */
	XLSCellRotation getRotation();

	/**
	 * Get the cell top border style.
	 * @return The cell top border style, {@link XLSCellBorder#NONE} if not configured
	 */
	XLSCellBorder getBorderTop();

	/**
	 * Get the cell righr border style.
	 * @return The cell right border style, {@link XLSCellBorder#NONE} if not configured
	 */
	XLSCellBorder getBorderRight();

	/**
	 * Get the cell bottom border style.
	 * @return The cell bottom border style, {@link XLSCellBorder#NONE} if not configured
	 */
	XLSCellBorder getBorderBottom();

	/**
	 * Get the cell left border style.
	 * @return The cell left border style, {@link XLSCellBorder#NONE} if not configured
	 */
	XLSCellBorder getBorderLeft();

	/**
	 * Get the cell top border color.
	 * @return The cell top border color, {@link XLSColor#AUTOMATIC} if not configured
	 */
	XLSColor getBorderTopColor();

	/**
	 * Get the cell right border color.
	 * @return The cell right border color, {@link XLSColor#AUTOMATIC} if not configured
	 */
	XLSColor getBorderRightColor();

	/**
	 * Get the cell bottom border color.
	 * @return The cell bottom border color, {@link XLSColor#AUTOMATIC} if not configured
	 */
	XLSColor getBorderBottomColor();

	/**
	 * Get the cell left border color.
	 * @return The cell left border color, {@link XLSColor#AUTOMATIC} if not configured
	 */
	XLSColor getBorderLeftColor();

}
