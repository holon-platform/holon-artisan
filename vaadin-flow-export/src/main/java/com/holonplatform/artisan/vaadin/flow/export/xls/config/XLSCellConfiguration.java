/*
 * Copyright 2016-2019 Axioma srl.
 * 
 * Licensed under the Commercial Holon Platform Module License Version 1 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * https://docs.holon-platform.com/license/chpml_v1.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.holonplatform.artisan.vaadin.flow.export.xls.config;

import java.io.Serializable;

import com.holonplatform.artisan.vaadin.flow.export.xls.internal.config.DefaultXLSCellConfiguration;

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

	/**
	 * Clone this configuration.
	 * @return Cloned configuration builder
	 */
	Builder cloneConfiguration();

	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);

	/**
	 * Get a builder to create a new {@link XLSCellConfiguration}.
	 * @return A {@link XLSCellConfiguration} builder
	 */
	static Builder builder() {
		return new DefaultXLSCellConfiguration.DefaultBuilder();
	}

	/**
	 * XLSCellConfiguration builder.
	 */
	public interface Builder {

		/**
		 * Set the font style as bold.
		 * @param bold Whether the font is bold
		 * @return this
		 */
		Builder bold(boolean bold);

		/**
		 * Set the font style as italic.
		 * @param italic Whether the font is italic
		 * @return this
		 */
		Builder italic(boolean italic);

		/**
		 * Set the font style as underline.
		 * @param underline Whether the font is underline
		 * @return this
		 */
		Builder underline(boolean underline);

		/**
		 * Set the font style as strikeout.
		 * @param strikeOut Whether the font is strikeout
		 * @return this
		 */
		Builder strikeOut(boolean strikeOut);

		/**
		 * Set whether the text should be wrapped, i.e. to make all content visible within a cell by displaying it on
		 * multiple lines.
		 * @param wrap Whether the text should be wrapped
		 * @return this
		 */
		Builder wrap(boolean wrap);

		/**
		 * Set whether the cell should be auto-sized.
		 * @param shrinkToFit Whether the cell should be auto-sized.
		 * @return this
		 */
		Builder shrinkToFit(boolean shrinkToFit);

		/**
		 * Set the font size.
		 * @param fontSize The font size
		 * @return this
		 */
		Builder fontSize(XLSFontSize fontSize);

		/**
		 * Set the font size.
		 * @param fontColor The font color
		 * @return this
		 */
		Builder fontColor(XLSColor fontColor);

		/**
		 * Set the cell background color.
		 * @param backgroundColor The background color
		 * @return this
		 */
		Builder backgroundColor(XLSColor backgroundColor);

		/**
		 * Set the cell horizontal alignment.
		 * @param alignment the cell horizontal alignment
		 * @return this
		 */
		Builder alignment(XLSCellAlignment alignment);

		/**
		 * Set the cell vertical alignment.
		 * @param verticalAlignment the cell vertical alignment
		 * @return this
		 */
		Builder verticalAlignment(XLSCellVerticalAlignment verticalAlignment);

		/**
		 * Set the cell text rotation.
		 * @param rotation the cell text rotation
		 * @return this
		 */
		Builder rotation(XLSCellRotation rotation);

		/**
		 * Set the cell top border style.
		 * @param borderTop The top border style
		 * @return this
		 */
		Builder borderTop(XLSCellBorder borderTop);

		/**
		 * Set the cell right border style.
		 * @param borderRight The right border style
		 * @return this
		 */
		Builder borderRight(XLSCellBorder borderRight);

		/**
		 * Set the cell bottom border style.
		 * @param borderBottom The bottom border style
		 * @return this
		 */
		Builder borderBottom(XLSCellBorder borderBottom);

		/**
		 * Set the cell left border style.
		 * @param borderLeft The left border style
		 * @return this
		 */
		Builder borderLeft(XLSCellBorder borderLeft);

		/**
		 * Set the cell top border color.
		 * @param borderTopColor The top border color
		 * @return this
		 */
		Builder borderTopColor(XLSColor borderTopColor);

		/**
		 * Set the cell right border color.
		 * @param borderRightColor The right border color
		 * @return this
		 */
		Builder borderRightColor(XLSColor borderRightColor);

		/**
		 * Set the cell bottom border color.
		 * @param borderBottomColor The bottom border color
		 * @return this
		 */
		Builder borderBottomColor(XLSColor borderBottomColor);

		/**
		 * Set the cell left border color.
		 * @param borderLeftColor The left border color
		 * @return this
		 */
		Builder borderLeftColor(XLSColor borderLeftColor);

		/**
		 * Build the configuration.
		 * @return The {@link XLSCellConfiguration} instance
		 */
		XLSCellConfiguration build();

	}

}
