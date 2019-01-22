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
package com.holonplatform.artisan.vaadin.flow.export.xls.internal.config;

import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellAlignment;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellBorder;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellRotation;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellVerticalAlignment;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSColor;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSFontSize;

/**
 * Default {@link XLSCellConfiguration} implementation.
 *
 * @since 1.0.0
 */
public class DefaultXLSCellConfiguration implements XLSCellConfiguration {

	private static final long serialVersionUID = 7156475483174456634L;

	static final XLSCellConfiguration DEFAULT_HEADER_CONFIGURATION = XLSCellConfiguration.builder().bold(true)
			.backgroundColor(XLSColor.LIGHT_CORNFLOWER_BLUE).borderBottom(XLSCellBorder.THIN).wrap(true).build();

	static final XLSCellConfiguration DEFAULT_TOTAL_CONFIGURATION = XLSCellConfiguration.builder()
			.backgroundColor(XLSColor.LIGHT_YELLOW).borderTop(XLSCellBorder.MEDIUM).build();

	private boolean bold = false;
	private boolean italic = false;
	private boolean underline = false;
	private boolean strikeOut = false;
	private boolean wrap = false;
	private boolean shrinkToFit = false;
	private XLSFontSize fontSize = XLSFontSize.AUTOMATIC;
	private XLSColor fontColor = XLSColor.AUTOMATIC;
	private XLSColor backgroundColor = XLSColor.AUTOMATIC;
	private XLSCellAlignment alignment = XLSCellAlignment.DEFAULT;
	private XLSCellVerticalAlignment verticalAlignment = XLSCellVerticalAlignment.DEFAULT;
	private XLSCellRotation rotation = XLSCellRotation.NONE;
	private XLSCellBorder borderTop = XLSCellBorder.NONE;
	private XLSCellBorder borderRight = XLSCellBorder.NONE;
	private XLSCellBorder borderBottom = XLSCellBorder.NONE;
	private XLSCellBorder borderLeft = XLSCellBorder.NONE;
	private XLSColor borderTopColor = XLSColor.AUTOMATIC;
	private XLSColor borderRightColor = XLSColor.AUTOMATIC;
	private XLSColor borderBottomColor = XLSColor.AUTOMATIC;
	private XLSColor borderLeftColor = XLSColor.AUTOMATIC;

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#isBold()
	 */
	@Override
	public boolean isBold() {
		return bold;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#isItalic()
	 */
	@Override
	public boolean isItalic() {
		return italic;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#isUnderline()
	 */
	@Override
	public boolean isUnderline() {
		return underline;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#isStrikeOut()
	 */
	@Override
	public boolean isStrikeOut() {
		return strikeOut;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#isWrap()
	 */
	@Override
	public boolean isWrap() {
		return wrap;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#isShrinkToFit()
	 */
	@Override
	public boolean isShrinkToFit() {
		return shrinkToFit;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#getFontSize()
	 */
	@Override
	public XLSFontSize getFontSize() {
		return fontSize;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#getFontColor()
	 */
	@Override
	public XLSColor getFontColor() {
		return fontColor;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#getBackgroundColor()
	 */
	@Override
	public XLSColor getBackgroundColor() {
		return backgroundColor;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#getAlignment()
	 */
	@Override
	public XLSCellAlignment getAlignment() {
		return alignment;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#getVerticalAlignment()
	 */
	@Override
	public XLSCellVerticalAlignment getVerticalAlignment() {
		return verticalAlignment;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#getRotation()
	 */
	@Override
	public XLSCellRotation getRotation() {
		return rotation;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#getBorderTop()
	 */
	@Override
	public XLSCellBorder getBorderTop() {
		return borderTop;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#getBorderRight()
	 */
	@Override
	public XLSCellBorder getBorderRight() {
		return borderRight;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#getBorderBottom()
	 */
	@Override
	public XLSCellBorder getBorderBottom() {
		return borderBottom;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#getBorderLeft()
	 */
	@Override
	public XLSCellBorder getBorderLeft() {
		return borderLeft;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#getBorderTopColor()
	 */
	@Override
	public XLSColor getBorderTopColor() {
		return borderTopColor;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#getBorderRightColor()
	 */
	@Override
	public XLSColor getBorderRightColor() {
		return borderRightColor;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#getBorderBottomColor()
	 */
	@Override
	public XLSColor getBorderBottomColor() {
		return borderBottomColor;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#getBorderLeftColor()
	 */
	@Override
	public XLSColor getBorderLeftColor() {
		return borderLeftColor;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	public void setUnderline(boolean underline) {
		this.underline = underline;
	}

	public void setStrikeOut(boolean strikeOut) {
		this.strikeOut = strikeOut;
	}

	public void setWrap(boolean wrap) {
		this.wrap = wrap;
	}

	public void setShrinkToFit(boolean shrinkToFit) {
		this.shrinkToFit = shrinkToFit;
	}

	public void setFontSize(XLSFontSize fontSize) {
		this.fontSize = (fontSize != null) ? fontSize : XLSFontSize.AUTOMATIC;
	}

	public void setFontColor(XLSColor fontColor) {
		this.fontColor = (fontColor != null) ? fontColor : XLSColor.AUTOMATIC;
	}

	public void setBackgroundColor(XLSColor backgroundColor) {
		this.backgroundColor = (backgroundColor != null) ? backgroundColor : XLSColor.AUTOMATIC;
	}

	public void setAlignment(XLSCellAlignment alignment) {
		this.alignment = (alignment != null) ? alignment : XLSCellAlignment.DEFAULT;
	}

	public void setVerticalAlignment(XLSCellVerticalAlignment verticalAlignment) {
		this.verticalAlignment = (verticalAlignment != null) ? verticalAlignment : XLSCellVerticalAlignment.DEFAULT;
	}

	public void setRotation(XLSCellRotation rotation) {
		this.rotation = (rotation != null) ? rotation : XLSCellRotation.NONE;
	}

	public void setBorderTop(XLSCellBorder borderTop) {
		this.borderTop = (borderTop != null) ? borderTop : XLSCellBorder.NONE;
	}

	public void setBorderRight(XLSCellBorder borderRight) {
		this.borderRight = (borderRight != null) ? borderRight : XLSCellBorder.NONE;
	}

	public void setBorderBottom(XLSCellBorder borderBottom) {
		this.borderBottom = (borderBottom != null) ? borderBottom : XLSCellBorder.NONE;
	}

	public void setBorderLeft(XLSCellBorder borderLeft) {
		this.borderLeft = (borderLeft != null) ? borderLeft : XLSCellBorder.NONE;
	}

	public void setBorderTopColor(XLSColor borderTopColor) {
		this.borderTopColor = (borderTopColor != null) ? borderTopColor : XLSColor.AUTOMATIC;
	}

	public void setBorderRightColor(XLSColor borderRightColor) {
		this.borderRightColor = (borderRightColor != null) ? borderRightColor : XLSColor.AUTOMATIC;
	}

	public void setBorderBottomColor(XLSColor borderBottomColor) {
		this.borderBottomColor = (borderBottomColor != null) ? borderBottomColor : XLSColor.AUTOMATIC;
	}

	public void setBorderLeftColor(XLSColor borderLeftColor) {
		this.borderLeftColor = (borderLeftColor != null) ? borderLeftColor : XLSColor.AUTOMATIC;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration#cloneConfiguration()
	 */
	@Override
	public Builder cloneConfiguration() {
		final Builder builder = XLSCellConfiguration.builder();
		builder.bold(isBold());
		builder.italic(isItalic());
		builder.underline(isUnderline());
		builder.strikeOut(isStrikeOut());
		builder.wrap(isWrap());
		builder.shrinkToFit(isShrinkToFit());
		builder.fontSize(getFontSize());
		builder.fontColor(getFontColor());
		builder.backgroundColor(getBackgroundColor());
		builder.alignment(getAlignment());
		builder.verticalAlignment(getVerticalAlignment());
		builder.rotation(getRotation());
		builder.borderTop(getBorderTop());
		builder.borderRight(getBorderRight());
		builder.borderBottom(getBorderBottom());
		builder.borderLeft(getBorderLeft());
		builder.borderTopColor(getBorderTopColor());
		builder.borderRightColor(getBorderRightColor());
		builder.borderBottomColor(getBorderBottomColor());
		builder.borderLeftColor(getBorderLeftColor());
		return builder;
	}

	public static class DefaultBuilder implements Builder {

		private final DefaultXLSCellConfiguration instance = new DefaultXLSCellConfiguration();

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#bold(boolean)
		 */
		@Override
		public Builder bold(boolean bold) {
			instance.setBold(bold);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#italic(boolean)
		 */
		@Override
		public Builder italic(boolean italic) {
			instance.setItalic(italic);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#underline(boolean)
		 */
		@Override
		public Builder underline(boolean underline) {
			instance.setUnderline(underline);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#strikeOut(boolean)
		 */
		@Override
		public Builder strikeOut(boolean strikeOut) {
			instance.setStrikeOut(strikeOut);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#wrap(boolean)
		 */
		@Override
		public Builder wrap(boolean wrap) {
			instance.setWrap(wrap);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#shrinkToFit(boolean)
		 */
		@Override
		public Builder shrinkToFit(boolean shrinkToFit) {
			instance.setShrinkToFit(shrinkToFit);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#fontSize(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSFontSize)
		 */
		@Override
		public Builder fontSize(XLSFontSize fontSize) {
			instance.setFontSize(fontSize);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#fontColor(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSColor)
		 */
		@Override
		public Builder fontColor(XLSColor fontColor) {
			instance.setFontColor(fontColor);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#backgroundColor(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSColor)
		 */
		@Override
		public Builder backgroundColor(XLSColor backgroundColor) {
			instance.setBackgroundColor(backgroundColor);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#alignment(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellAlignment)
		 */
		@Override
		public Builder alignment(XLSCellAlignment alignment) {
			instance.setAlignment(alignment);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#verticalAlignment(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellVerticalAlignment)
		 */
		@Override
		public Builder verticalAlignment(XLSCellVerticalAlignment verticalAlignment) {
			instance.setVerticalAlignment(verticalAlignment);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#rotation(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellRotation)
		 */
		@Override
		public Builder rotation(XLSCellRotation rotation) {
			instance.setRotation(rotation);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#borderTop(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellBorder)
		 */
		@Override
		public Builder borderTop(XLSCellBorder borderTop) {
			instance.setBorderTop(borderTop);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#borderRight(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellBorder)
		 */
		@Override
		public Builder borderRight(XLSCellBorder borderRight) {
			instance.setBorderRight(borderRight);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#borderBottom(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellBorder)
		 */
		@Override
		public Builder borderBottom(XLSCellBorder borderBottom) {
			instance.setBorderBottom(borderBottom);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#borderLeft(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellBorder)
		 */
		@Override
		public Builder borderLeft(XLSCellBorder borderLeft) {
			instance.setBorderLeft(borderLeft);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#borderTopColor(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSColor)
		 */
		@Override
		public Builder borderTopColor(XLSColor borderTopColor) {
			instance.setBorderTopColor(borderTopColor);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#borderRightColor(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSColor)
		 */
		@Override
		public Builder borderRightColor(XLSColor borderRightColor) {
			instance.setBorderRightColor(borderRightColor);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#borderBottomColor(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSColor)
		 */
		@Override
		public Builder borderBottomColor(XLSColor borderBottomColor) {
			instance.setBorderBottomColor(borderBottomColor);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#borderLeftColor(com.
		 * holonplatform.artisan.vaadin.flow.export.xls.config.XLSColor)
		 */
		@Override
		public Builder borderLeftColor(XLSColor borderLeftColor) {
			instance.setBorderLeftColor(borderLeftColor);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration.Builder#build()
		 */
		@Override
		public XLSCellConfiguration build() {
			return instance;
		}

	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alignment == null) ? 0 : alignment.hashCode());
		result = prime * result + ((backgroundColor == null) ? 0 : backgroundColor.hashCode());
		result = prime * result + (bold ? 1231 : 1237);
		result = prime * result + ((borderBottom == null) ? 0 : borderBottom.hashCode());
		result = prime * result + ((borderBottomColor == null) ? 0 : borderBottomColor.hashCode());
		result = prime * result + ((borderLeft == null) ? 0 : borderLeft.hashCode());
		result = prime * result + ((borderLeftColor == null) ? 0 : borderLeftColor.hashCode());
		result = prime * result + ((borderRight == null) ? 0 : borderRight.hashCode());
		result = prime * result + ((borderRightColor == null) ? 0 : borderRightColor.hashCode());
		result = prime * result + ((borderTop == null) ? 0 : borderTop.hashCode());
		result = prime * result + ((borderTopColor == null) ? 0 : borderTopColor.hashCode());
		result = prime * result + ((fontColor == null) ? 0 : fontColor.hashCode());
		result = prime * result + ((fontSize == null) ? 0 : fontSize.hashCode());
		result = prime * result + (italic ? 1231 : 1237);
		result = prime * result + ((rotation == null) ? 0 : rotation.hashCode());
		result = prime * result + (shrinkToFit ? 1231 : 1237);
		result = prime * result + (strikeOut ? 1231 : 1237);
		result = prime * result + (underline ? 1231 : 1237);
		result = prime * result + ((verticalAlignment == null) ? 0 : verticalAlignment.hashCode());
		result = prime * result + (wrap ? 1231 : 1237);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultXLSCellConfiguration other = (DefaultXLSCellConfiguration) obj;
		if (alignment != other.alignment)
			return false;
		if (backgroundColor != other.backgroundColor)
			return false;
		if (bold != other.bold)
			return false;
		if (borderBottom != other.borderBottom)
			return false;
		if (borderBottomColor != other.borderBottomColor)
			return false;
		if (borderLeft != other.borderLeft)
			return false;
		if (borderLeftColor != other.borderLeftColor)
			return false;
		if (borderRight != other.borderRight)
			return false;
		if (borderRightColor != other.borderRightColor)
			return false;
		if (borderTop != other.borderTop)
			return false;
		if (borderTopColor != other.borderTopColor)
			return false;
		if (fontColor != other.fontColor)
			return false;
		if (fontSize != other.fontSize)
			return false;
		if (italic != other.italic)
			return false;
		if (rotation != other.rotation)
			return false;
		if (shrinkToFit != other.shrinkToFit)
			return false;
		if (strikeOut != other.strikeOut)
			return false;
		if (underline != other.underline)
			return false;
		if (verticalAlignment != other.verticalAlignment)
			return false;
		if (wrap != other.wrap)
			return false;
		return true;
	}

}
