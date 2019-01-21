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
package com.holonplatform.artisan.vaadin.flow.export.xls.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.holonplatform.artisan.core.ArtisanLogger;
import com.holonplatform.artisan.vaadin.flow.export.ExportProgressCallback;
import com.holonplatform.artisan.vaadin.flow.export.ExportProgressState;
import com.holonplatform.artisan.vaadin.flow.export.exceptions.ExportException;
import com.holonplatform.artisan.vaadin.flow.export.exceptions.InterruptedExportException;
import com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellAlignment;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellBorder;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellRotation;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellVerticalAlignment;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSColor;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSFileVersion;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSFontSize;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.internal.Logger;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.property.PropertyBox;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.vaadin.flow.components.Components;
import com.holonplatform.vaadin.flow.i18n.LocalizationProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;

/**
 * Default {@link XLSExporter} implementation.
 *
 * @since 1.0.0
 */
public class DefaultXLSExporter implements XLSExporter {

	protected static final Logger LOGGER = ArtisanLogger.create();

	private final DataProvider<PropertyBox, ?> dataSource;
	private final PropertySet<?> propertySet;

	private XLSConfiguration configuration;

	private final Map<FontConfiguration, Font> workbookFonts = new HashMap<>();

	/**
	 * Constructor.
	 * @param dataSource The {@link DataProvider} to use as data source (not null)
	 * @param propertySet The property set to use (not null)
	 */
	public DefaultXLSExporter(DataProvider<PropertyBox, ?> dataSource, PropertySet<?> propertySet) {
		super();
		ObjectUtils.argumentNotNull(dataSource, "The DataProvider must be not null");
		ObjectUtils.argumentNotNull(propertySet, "The PropertySet must be not null");
		this.dataSource = dataSource;
		this.propertySet = propertySet;
	}

	/**
	 * Set the {@link XLSConfiguration} to use.
	 * @param configuration the configuration to set
	 */
	protected void setConfigurationSupplier(XLSConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Get the {@link DataProvider} to use as export data source.
	 * @return The {@link DataProvider}
	 */
	protected DataProvider<PropertyBox, ?> getDataProvider() {
		return dataSource;
	}

	/**
	 * Get the property set.
	 * @return the property set
	 */
	protected PropertySet<?> getPropertySet() {
		return propertySet;
	}

	/**
	 * Get the export configuration.
	 * @return Optional export configuration
	 */
	protected Optional<XLSConfiguration> getConfiguration() {
		return Optional.ofNullable(configuration);
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter#export(java.io.OutputStream,
	 * com.holonplatform.artisan.vaadin.flow.export.ExportProgressCallback)
	 */
	@Override
	public void export(OutputStream outputStream, ExportProgressCallback exportProgressCallback)
			throws ExportException {
		ObjectUtils.argumentNotNull(outputStream, "The data output stream must be not null");
		ObjectUtils.argumentNotNull(exportProgressCallback, "The export progres callback must be not null");

		workbookFonts.clear();

		LOGGER.debug(() -> "Start XLS export...");

		final XLSConfiguration configuration = getConfiguration().orElseGet(() -> XLSConfiguration.builder().build());

		final int estimatedSize = getDataProvider().size(new Query<>());
		final int totalSteps = estimatedSize + 4;

		LOGGER.debug(() -> "XLS export estimated row count: " + estimatedSize);

		updateExportProgress(exportProgressCallback, totalSteps, 0);

		// Workbook setup
		try (Workbook workbook = createWorkbook(configuration)) {

			// export properties
			final List<Property<?>> properties = getExportProperties(configuration);
			if (properties.isEmpty()) {
				throw new ExportException("No property to export");
			}

			// Sheet setup
			final String sheetName = localize(configuration.getSheetName().orElse(DEFAULT_SHEET_NAME), "export");

			final Sheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName(sheetName, '_'));

			updateExportProgress(exportProgressCallback, totalSteps, 1);

			int currentRowIndex = 0;

			// Title
			if (createTitleRow(workbook, sheet, 0, configuration)) {
				currentRowIndex++;
			}
			updateExportProgress(exportProgressCallback, totalSteps, 2);

			// Header
			createHeaderRow(workbook, sheet, currentRowIndex, configuration, properties);
			currentRowIndex++;
			updateExportProgress(exportProgressCallback, totalSteps, 3);

			// Data

			// TODO

			// Totals

			// TODO
			updateExportProgress(exportProgressCallback, totalSteps, (totalSteps - 1));

			// Write to stream

			// TODO
			updateExportProgress(exportProgressCallback, totalSteps, totalSteps);

		} catch (IOException e) {
			throw new ExportException("Export failed", e);
		}
	}

	protected List<Property<?>> getExportProperties(XLSConfiguration configuration) {
		final List<Property<?>> properties = new LinkedList<>();
		// check configuration
		if (!configuration.getProperties().isEmpty()) {
			configuration.getProperties().stream().filter(p -> getPropertySet().contains(p)).forEach(property -> {
				properties.add(property);
			});
		} else {
			for (Property<?> property : getPropertySet()) {
				properties.add(property);
			}
		}
		// exclude components
		properties.removeIf(property -> Components.class.isAssignableFrom(property.getType()));
		return properties;
	}

	protected boolean createTitleRow(Workbook workbook, Sheet sheet, int rowIndex, XLSConfiguration configuration) {
		return configuration.getTitle().flatMap(t -> isValidMessage(t)).map(title -> {
			final Row titleRow = sheet.createRow(rowIndex);
			final Cell cell = titleRow.createCell(0, CellType.STRING);
			// style
			final CellStyle style = workbook.createCellStyle();
			final Font font = workbook.createFont();
			font.setBold(true);
			if (!configuration.getTitleFontSize().isAuto()) {
				font.setFontHeightInPoints(configuration.getTitleFontSize().getPoints());
			} else {
				font.setFontHeightInPoints(XLSFontSize.FOURTEEN.getPoints());
			}
			if (configuration.getTitleFontColor() != XLSColor.AUTOMATIC) {
				font.setColor(configuration.getTitleFontColor().getIndex1());
			}
			style.setFont(font);
			cell.setCellStyle(style);
			// text
			cell.setCellValue(localize(title));

			return true;
		}).orElse(Boolean.FALSE);
	}

	protected void createHeaderRow(Workbook workbook, Sheet sheet, int rowIndex, XLSConfiguration configuration,
			List<Property<?>> properties) {
		// configuration
		XLSCellConfiguration.Builder builder = configuration.getHeaderConfiguration().cloneConfiguration();
		if (configuration.getHeaderConfiguration().getFontSize().isAuto()
				&& !configuration.getDefaultFontSize().isAuto()) {
			builder.fontSize(configuration.getDefaultFontSize());
		}
		if (configuration.isShrinkToFitByDefault()) {
			builder.shrinkToFit(true);
		}
		final XLSCellConfiguration headerConfig = builder.build();

		// style
		final CellStyle defaultHeaderStyle = workbook.createCellStyle();
		configureCellStyle(workbook, defaultHeaderStyle, configuration, headerConfig);

		// cells
		final Row headerRow = sheet.createRow(rowIndex);
		for (int i = 0; i < properties.size(); i++) {
			final Property<?> property = properties.get(i);
			final Cell cell = headerRow.createCell(i);
			// style
			cell.setCellStyle(configuration.getPropertyConfiguration(property).map(cfg -> cfg.getHeaderConfiguration())
					.filter(cfg -> !cfg.equals(headerConfig)).map(cfg -> {
						CellStyle headerStyle = workbook.createCellStyle();
						configureCellStyle(workbook, headerStyle, configuration, cfg);
						return headerStyle;
					}).orElse(defaultHeaderStyle));
			// value
			cell.setCellValue(localize(
					configuration.getPropertyConfiguration(property).flatMap(cfg -> cfg.getHeader()).orElse(property),
					"?"));
		}
	}

	protected void configureCellStyle(Workbook workbook, CellStyle style, XLSConfiguration configuration,
			XLSCellConfiguration cellConfiguration) {
		// config
		style.setWrapText(cellConfiguration.isWrap());
		style.setShrinkToFit(cellConfiguration.isShrinkToFit());
		if (!cellConfiguration.isWrap() && configuration.isWrapByDefault()) {
			style.setWrapText(true);
		}
		if (!cellConfiguration.isShrinkToFit() && configuration.isShrinkToFitByDefault()) {
			style.setShrinkToFit(true);
		}
		// alignment
		if (cellConfiguration.getAlignment() != XLSCellAlignment.DEFAULT) {
			style.setAlignment(convert(cellConfiguration.getAlignment()));
		}
		if (cellConfiguration.getVerticalAlignment() != XLSCellVerticalAlignment.DEFAULT) {
			style.setVerticalAlignment(convert(cellConfiguration.getVerticalAlignment()));
		}
		// rotation
		if (cellConfiguration.getRotation() != XLSCellRotation.NONE) {
			style.setRotation(cellConfiguration.getRotation().getDegrees());
		}
		// border
		if (cellConfiguration.getBorderTop() != XLSCellBorder.NONE) {
			style.setBorderTop(convert(cellConfiguration.getBorderTop()));
			if (!cellConfiguration.getBorderTopColor().isAuto()) {
				style.setTopBorderColor(cellConfiguration.getBorderTopColor().getIndex1());
			}
		}
		if (cellConfiguration.getBorderRight() != XLSCellBorder.NONE) {
			style.setBorderRight(convert(cellConfiguration.getBorderRight()));
			if (!cellConfiguration.getBorderRightColor().isAuto()) {
				style.setRightBorderColor(cellConfiguration.getBorderRightColor().getIndex1());
			}
		}
		if (cellConfiguration.getBorderBottom() != XLSCellBorder.NONE) {
			style.setBorderBottom(convert(cellConfiguration.getBorderBottom()));
			if (!cellConfiguration.getBorderBottomColor().isAuto()) {
				style.setBottomBorderColor(cellConfiguration.getBorderBottomColor().getIndex1());
			}
		}
		if (cellConfiguration.getBorderLeft() != XLSCellBorder.NONE) {
			style.setBorderLeft(convert(cellConfiguration.getBorderLeft()));
			if (!cellConfiguration.getBorderLeftColor().isAuto()) {
				style.setLeftBorderColor(cellConfiguration.getBorderLeftColor().getIndex1());
			}
		}
		// background
		if (!cellConfiguration.getBackgroundColor().isAuto()) {
			style.setFillBackgroundColor(cellConfiguration.getBackgroundColor().getIndex1());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}
		// font
		style.setFont(getOrCreateFont(workbook, configuration, cellConfiguration));
	}

	protected Font getOrCreateFont(Workbook workbook, XLSConfiguration configuration,
			XLSCellConfiguration cellConfiguration) {

		final XLSFontSize size = !cellConfiguration.getFontSize().isAuto() ? cellConfiguration.getFontSize()
				: configuration.getDefaultFontSize();

		final FontConfiguration fc = new FontConfiguration(cellConfiguration.isBold(), cellConfiguration.isItalic(),
				cellConfiguration.isUnderline(), cellConfiguration.isStrikeOut(), size.getPoints(),
				cellConfiguration.getFontColor().getIndex1());

		return workbookFonts.computeIfAbsent(fc, config -> {
			Font font = workbook.createFont();
			font.setBold(cellConfiguration.isBold());
			font.setItalic(cellConfiguration.isItalic());
			font.setStrikeout(cellConfiguration.isStrikeOut());
			if (cellConfiguration.isUnderline()) {
				font.setUnderline(Font.U_SINGLE);
			}
			if (!cellConfiguration.getFontColor().isAuto()) {
				font.setColor(cellConfiguration.getFontColor().getIndex1());
			}
			if (!size.isAuto()) {
				font.setFontHeightInPoints(size.getPoints());
			}
			return font;
		});

	}

	protected void updateExportProgress(ExportProgressCallback exportProgressCallback, int totalSteps,
			int completedSteps) {
		final ExportProgressState state = exportProgressCallback.onExportProgress(totalSteps, completedSteps);
		if (ExportProgressState.ABORT.equals(state)) {
			throw new InterruptedExportException("Export interrupted by external request");
		}
	}

	protected String localize(Localizable message) {
		return localize(message, "");
	}

	protected String localize(Localizable message, String defaultText) {
		if (message == null) {
			return defaultText;
		}
		return LocalizationProvider.localize(message).orElse(defaultText);
	}

	protected Optional<Localizable> isValidMessage(Localizable message) {
		if (message != null && (message.getMessage() != null || message.getMessageCode() != null)) {
			return Optional.of(message);
		}
		return Optional.empty();
	}

	protected Workbook createWorkbook(XLSConfiguration configuration) {
		if (XLSFileVersion.XLSX.equals(configuration.getFileVersion())) {
			return new SXSSFWorkbook(10);
		}
		return new HSSFWorkbook();
	}

	protected static HorizontalAlignment convert(XLSCellAlignment alignment) {
		switch (alignment) {
		case CENTER:
			return HorizontalAlignment.CENTER;
		case FILL:
			return HorizontalAlignment.FILL;
		case JUSTIFY:
			return HorizontalAlignment.JUSTIFY;
		case LEFT:
			return HorizontalAlignment.LEFT;
		case RIGHT:
			return HorizontalAlignment.RIGHT;
		case DEFAULT:
		default:
			return HorizontalAlignment.GENERAL;
		}
	}

	protected static VerticalAlignment convert(XLSCellVerticalAlignment alignment) {
		switch (alignment) {
		case CENTER:
			return VerticalAlignment.CENTER;
		case JUSTIFY:
			return VerticalAlignment.JUSTIFY;
		case BOTTOM:
			return VerticalAlignment.BOTTOM;
		case TOP:
			return VerticalAlignment.TOP;
		case DEFAULT:
		default:
			return VerticalAlignment.BOTTOM;
		}
	}

	protected static BorderStyle convert(XLSCellBorder border) {
		switch (border) {
		case DASHED:
			return BorderStyle.DASHED;
		case DASH_DOT:
			return BorderStyle.DASH_DOT;
		case DASH_DOT_DOT:
			return BorderStyle.DASH_DOT_DOT;
		case DOTTED:
			return BorderStyle.DOTTED;
		case DOUBLE:
			return BorderStyle.DOUBLE;
		case HAIR:
			return BorderStyle.HAIR;
		case MEDIUM:
			return BorderStyle.MEDIUM;
		case MEDIUM_DASHED:
			return BorderStyle.MEDIUM_DASHED;
		case SLANTED_DASH_DOT:
			return BorderStyle.SLANTED_DASH_DOT;
		case THICK:
			return BorderStyle.THICK;
		case THIN:
			return BorderStyle.THIN;
		case NONE:
		default:
			return BorderStyle.NONE;
		}
	}

	/**
	 * Default {@link Builder} implementation.
	 */
	public static class DefaultBuilder implements Builder {

		private final DefaultXLSExporter exporter;

		/**
		 * Constructor.
		 * @param dataSource The {@link DataProvider} to use as data source (not null)
		 * @param propertySet The property set to use (not null)
		 */
		public DefaultBuilder(DataProvider<PropertyBox, ?> dataSource, PropertySet<?> propertySet) {
			super();
			this.exporter = new DefaultXLSExporter(dataSource, propertySet);
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter.Builder#configuration(com.holonplatform.artisan.
		 * vaadin.flow.export.xls.config.XLSConfiguration)
		 */
		@Override
		public Builder configuration(XLSConfiguration configuration) {
			this.exporter.setConfigurationSupplier(configuration);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter.Builder#build()
		 */
		@Override
		public XLSExporter build() {
			return this.exporter;
		}

	}

	private static class FontConfiguration implements Serializable {

		private final boolean bold;
		private final boolean italic;
		private final boolean underline;
		private final boolean strikeout;
		private final short size;
		private final short color;

		public FontConfiguration(boolean bold, boolean italic, boolean underline, boolean strikeout, short size,
				short color) {
			super();
			this.bold = bold;
			this.italic = italic;
			this.underline = underline;
			this.strikeout = strikeout;
			this.size = size;
			this.color = color;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (bold ? 1231 : 1237);
			result = prime * result + color;
			result = prime * result + (italic ? 1231 : 1237);
			result = prime * result + size;
			result = prime * result + (strikeout ? 1231 : 1237);
			result = prime * result + (underline ? 1231 : 1237);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			FontConfiguration other = (FontConfiguration) obj;
			if (bold != other.bold)
				return false;
			if (color != other.color)
				return false;
			if (italic != other.italic)
				return false;
			if (size != other.size)
				return false;
			if (strikeout != other.strikeout)
				return false;
			if (underline != other.underline)
				return false;
			return true;
		}

	}

}
