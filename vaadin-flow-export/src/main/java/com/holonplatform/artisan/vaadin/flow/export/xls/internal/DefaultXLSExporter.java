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

import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.holonplatform.artisan.vaadin.flow.export.xls.PropertyXLSValueProvider;
import com.holonplatform.artisan.vaadin.flow.export.xls.PropertyXLSValueProviderRegistry;
import com.holonplatform.artisan.vaadin.flow.export.xls.XLSDataType;
import com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter;
import com.holonplatform.artisan.vaadin.flow.export.xls.XLSValue;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellAlignment;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellBorder;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellConfiguration;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellRotation;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSCellVerticalAlignment;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSColor;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSConfiguration;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSFileVersion;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSFontSize;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSNumberGroupSeparator;
import com.holonplatform.artisan.vaadin.flow.export.xls.config.XLSPropertyConfiguration;
import com.holonplatform.core.i18n.Caption;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.i18n.LocalizationContext;
import com.holonplatform.core.internal.Logger;
import com.holonplatform.core.internal.utils.AnnotationUtils;
import com.holonplatform.core.internal.utils.ConversionUtils;
import com.holonplatform.core.internal.utils.ObjectUtils;
import com.holonplatform.core.internal.utils.TypeUtils;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.property.PropertyBox;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.core.property.PropertyValueProvider;
import com.holonplatform.core.property.VirtualProperty;
import com.holonplatform.vaadin.flow.components.Components;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;

/**
 * Default {@link XLSExporter} implementation.
 *
 * @since 1.0.0
 */
public class DefaultXLSExporter implements XLSExporter {

	protected static final Logger LOGGER = ArtisanLogger.create();

	private static final int DEFAULT_BATCH_SIZE = 20;

	private static final XLSCellConfiguration DEFAULT_CELL_CONFIGURATION = XLSCellConfiguration.builder().build();
	private static final XLSPropertyConfiguration DEFAULT_PROPERTY_CONFIGURATION = XLSPropertyConfiguration.builder()
			.build();

	private final DataProvider<PropertyBox, ?> dataSource;
	private final PropertySet<?> propertySet;

	private XLSConfiguration configuration;
	private PropertyXLSValueProviderRegistry propertyXLSValueProviderRegistry;
	private LocalizationContext localizationContext;

	private final Map<FontConfiguration, Font> workbookFonts = new HashMap<>();
	private final Map<StyleConfiguration, CellStyle> workbookStyles = new HashMap<>();

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
	 * Set the {@link XLSConfiguration} to use
	 * @param configuration The configuration to set
	 */
	protected void setConfiguration(XLSConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Set the {@link PropertyXLSValueProviderRegistry} to use.
	 * @param registry the registry to set
	 */
	protected void setPropertyXLSValueProviderRegistry(PropertyXLSValueProviderRegistry registry) {
		this.propertyXLSValueProviderRegistry = registry;
	}

	/**
	 * Set the {@link LocalizationContext} to use.
	 * @param localizationContext the localization context to set
	 */
	protected void setLocalizationContext(LocalizationContext localizationContext) {
		this.localizationContext = localizationContext;
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

	/**
	 * Get the {@link PropertyXLSValueProviderRegistry} to use.
	 * @return Optional registry to use
	 */
	protected Optional<PropertyXLSValueProviderRegistry> getPropertyXLSValueProviderRegistry() {
		return Optional.ofNullable(propertyXLSValueProviderRegistry);
	}

	/**
	 * @return the localizationContext
	 */
	protected Optional<LocalizationContext> getLocalizationContext() {
		return Optional.ofNullable(localizationContext);
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
		workbookStyles.clear();

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

			// Title
			boolean hasTitle = createTitleRow(workbook, sheet, 0, configuration);
			updateExportProgress(exportProgressCallback, totalSteps, 2);

			// Header
			int headerRowIndex = hasTitle ? 1 : 0;
			createHeaderRow(workbook, sheet, headerRowIndex, configuration, properties);
			updateExportProgress(exportProgressCallback, totalSteps, 3);

			// Data
			int dataEndRowIndex = createDataRows(workbook, sheet, headerRowIndex, configuration, properties,
					exportProgressCallback, totalSteps, 3);

			// Totals
			if (dataEndRowIndex > headerRowIndex) {
				int dataStartRowIndex = headerRowIndex + 1;
				// TODO
			}

			// Write to stream
			workbook.write(outputStream);
			updateExportProgress(exportProgressCallback, totalSteps, totalSteps);

		} catch (Exception e) {
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
						LOGGER.debug(() -> "Create custom header style for property: " + property);
						CellStyle headerStyle = workbook.createCellStyle();
						configureCellStyle(workbook, headerStyle, configuration, cfg);
						return headerStyle;
					}).orElse(defaultHeaderStyle));
			// value
			cell.setCellValue(localize(
					configuration.getPropertyConfiguration(property).flatMap(cfg -> cfg.getHeader()).orElse(property),
					property.getName()));
		}
	}

	protected int createDataRows(Workbook workbook, Sheet sheet, int lastRowIndex, XLSConfiguration configuration,
			List<Property<?>> properties, ExportProgressCallback exportProgressCallback, int totalSteps, int lastStep) {

		final PropertyXLSValueProviderRegistry registry = getPropertyXLSValueProviderRegistry()
				.orElseGet(() -> PropertyXLSValueProviderRegistry.get());

		int rowIndex = lastRowIndex;
		int progressStep = lastStep;

		int offset = 0;
		List<PropertyBox> results = Collections.emptyList();
		do {
			results = getDataProvider()
					.fetch(new Query<>(offset, DEFAULT_BATCH_SIZE, Collections.emptyList(), null, null))
					.collect(Collectors.toList());
			for (PropertyBox result : results) {
				rowIndex++;
				final Row row = sheet.createRow(rowIndex);
				createDataRow(workbook, row, configuration, properties, registry, result);
				progressStep = ((progressStep + 1) < totalSteps) ? (progressStep + 1) : progressStep;
				updateExportProgress(exportProgressCallback, totalSteps, progressStep);
			}
			offset += DEFAULT_BATCH_SIZE;
		} while (!results.isEmpty());

		return rowIndex;
	}

	protected void createDataRow(Workbook workbook, Row row, XLSConfiguration configuration,
			List<Property<?>> properties, PropertyXLSValueProviderRegistry registry, PropertyBox value) {
		for (int i = 0; i < properties.size(); i++) {
			@SuppressWarnings("unchecked")
			final Property<Object> property = (Property<Object>) properties.get(i);
			final Cell cell = row.createCell(i);
			// property configuration
			final XLSPropertyConfiguration propertyConfiguration = configuration.getPropertyConfiguration(property)
					.orElse(DEFAULT_PROPERTY_CONFIGURATION);
			// value provider
			PropertyXLSValueProvider<Object> provider = registry.getProvider(property)
					.orElseGet(() -> new DefaultPropertyXLSValueProvider<>());
			// value
			Object propertyValue = null;
			if (value != null && value.contains(property)) {
				propertyValue = value.getValue(property);
			} else {
				if (VirtualProperty.class.isAssignableFrom(property.getClass())) {
					final PropertyValueProvider<?> valueProvider = ((VirtualProperty<?>) property).getValueProvider();
					if (valueProvider != null) {
						propertyValue = valueProvider.getPropertyValue(value);
					}
				}
			}
			// get XLS value using provider
			XLSValue<?> xlsv = provider.provide(property, propertyConfiguration, propertyValue);
			if (xlsv == null) {
				// fallback to default
				xlsv = XLSValue.stringValue((propertyValue == null) ? null : String.valueOf(propertyValue));
			}
			final XLSValue<?> xlsValue = xlsv;
			setCellValue(cell, xlsValue);
			// style
			cell.setCellStyle(getOrCreateStyle(workbook, property, configuration,
					configuration.getPropertyConfiguration(property).map(cfg -> cfg.getCellConfiguration())
							.orElse(DEFAULT_CELL_CONFIGURATION),
					xlsValue.getDataFormat().orElseGet(() -> getDefaultDataFormat(xlsValue, propertyConfiguration))));
		}
	}

	protected String getDefaultDataFormat(XLSValue<?> xlsValue, XLSPropertyConfiguration configuration) {
		if (XLSDataType.NUMERIC == xlsValue.getDataType()) {
			if (TypeUtils.isIntegerNumber(xlsValue.getValueType())) {
				switch (configuration.getNumberGroupSeparator()) {
				case DISABLED:
					return "0";
				case ENABLED:
					return "#,##0";
				case DEFAULT:
				default:
					return null;
				}
			}
			if (TypeUtils.isDecimalNumber(xlsValue.getValueType())) {
				StringBuilder sb = new StringBuilder();
				if (configuration.getNumberGroupSeparator() == XLSNumberGroupSeparator.ENABLED) {
					sb.append("#,##0.");
				} else {
					sb.append("0.");
				}
				int decimals = configuration.getNumberDecimals().orElse(2);
				if (decimals < 1) {
					decimals = 2;
				}
				for (int i = 0; i < decimals; i++) {
					sb.append("0");
				}
				return sb.toString();
			}
		}
		return null;
	}

	protected void setCellValue(Cell cell, XLSValue<?> xlsValue) {
		boolean valueSetted = false;
		switch (xlsValue.getDataType()) {
		case BOOLEAN:
			valueSetted = setBooleanValue(cell, xlsValue);
			break;
		case DATE:
			valueSetted = setDateValue(cell, xlsValue);
			break;
		case ENUM:
			valueSetted = setEnumValue(cell, xlsValue);
			break;
		case FORMULA:
			valueSetted = setFormulaValue(cell, xlsValue);
			break;
		case NUMERIC:
			valueSetted = setNumericValue(cell, xlsValue);
			break;
		case STRING:
		default:
			valueSetted = setStringValue(cell, xlsValue);
			break;
		}
		if (!valueSetted) {
			cell.setCellType(CellType.BLANK);
		}
	}

	protected boolean setBooleanValue(Cell cell, XLSValue<?> xlsValue) {
		return xlsValue.getValue().map(v -> {
			if (!TypeUtils.isBoolean(xlsValue.getValueType())) {
				// fallback to string
				cell.setCellType(CellType.STRING);
				cell.setCellValue(String.valueOf(v));
				return true;
			}
			cell.setCellType(CellType.BOOLEAN);
			cell.setCellValue((Boolean) v);
			return true;
		}).orElse(Boolean.FALSE);
	}

	protected boolean setNumericValue(Cell cell, XLSValue<?> xlsValue) {
		return xlsValue.getValue().map(v -> {
			if (!TypeUtils.isNumber(xlsValue.getValueType())) {
				// fallback to string
				cell.setCellType(CellType.STRING);
				cell.setCellValue(String.valueOf(v));
				return true;
			}
			cell.setCellType(CellType.NUMERIC);
			cell.setCellValue(ConversionUtils.convertNumberToTargetClass((Number) v, Double.class));
			return true;
		}).orElse(Boolean.FALSE);
	}

	protected boolean setDateValue(Cell cell, XLSValue<?> xlsValue) {
		return xlsValue.getValue().map(v -> {
			if (Date.class.isAssignableFrom(xlsValue.getValueType())) {
				cell.setCellValue((Date) v);
				return true;
			}
			if (Calendar.class.isAssignableFrom(xlsValue.getValueType())) {
				cell.setCellValue((Calendar) v);
				return true;
			}
			if (LocalDate.class.isAssignableFrom(xlsValue.getValueType())) {
				cell.setCellValue(ConversionUtils.fromLocalDate((LocalDate) v));
				return true;
			}
			if (LocalDateTime.class.isAssignableFrom(xlsValue.getValueType())) {
				cell.setCellValue(ConversionUtils.fromLocalDateTime((LocalDateTime) v));
				return true;
			}
			if (LocalTime.class.isAssignableFrom(xlsValue.getValueType())) {
				final LocalTime time = (LocalTime) v;
				final Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, 0);
				calendar.set(Calendar.MONTH, 0);
				calendar.set(Calendar.DAY_OF_MONTH, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
				calendar.set(Calendar.MINUTE, time.getMinute());
				calendar.set(Calendar.SECOND, time.getSecond());
				cell.setCellValue(calendar.getTime());
				return true;
			}
			// fallback to string
			cell.setCellType(CellType.STRING);
			cell.setCellValue(String.valueOf(v));
			return true;
		}).orElse(Boolean.FALSE);
	}

	protected boolean setEnumValue(Cell cell, XLSValue<?> xlsValue) {
		return xlsValue.getValue().map(v -> {
			if (!TypeUtils.isEnum(xlsValue.getValueType())) {
				// fallback to string
				cell.setCellType(CellType.STRING);
				cell.setCellValue(String.valueOf(v));
				return true;
			}
			Enum<?> ev = (Enum<?>) v;
			cell.setCellType(CellType.STRING);
			cell.setCellValue(localize(getEnumCaption(ev), ev.name()));
			return true;
		}).orElse(Boolean.FALSE);
	}

	protected boolean setStringValue(Cell cell, XLSValue<?> xlsValue) {
		return xlsValue.getValue().map(v -> {
			cell.setCellType(CellType.STRING);
			cell.setCellValue(String.valueOf(v));
			return true;
		}).orElse(Boolean.FALSE);
	}

	protected boolean setFormulaValue(Cell cell, XLSValue<?> xlsValue) {
		return xlsValue.getValue().map(v -> {
			cell.setCellType(CellType.FORMULA);
			cell.setCellFormula(String.valueOf(v));
			return true;
		}).orElse(Boolean.FALSE);
	}

	/**
	 * Get given enum localizable caption value, using the {@link Caption} annotation if available.
	 * @param value The enum value
	 * @return The enum localizable caption, using the {@link Caption} annotation if available or the enum value name if
	 *         not
	 */
	private static Localizable getEnumCaption(Enum<?> value) {
		try {
			final java.lang.reflect.Field field = value.getClass().getField(value.name());
			if (field.isAnnotationPresent(Caption.class)) {
				String captionMessage = AnnotationUtils.getStringValue(field.getAnnotation(Caption.class).value());
				return Localizable.builder().message((captionMessage != null) ? captionMessage : value.name())
						.messageCode(AnnotationUtils.getStringValue(field.getAnnotation(Caption.class).messageCode()))
						.build();
			}
		} catch (@SuppressWarnings("unused") NoSuchFieldException | SecurityException e) {
			return Localizable.of(value.name());
		}
		return Localizable.of(value.name());
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
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			style.setFillForegroundColor(cellConfiguration.getBackgroundColor().getIndex1());
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

	protected CellStyle getOrCreateStyle(Workbook workbook, Property<?> property, XLSConfiguration configuration,
			XLSCellConfiguration cellConfiguration, String dataFormat) {
		final StyleConfiguration styleConfiguration = new StyleConfiguration(cellConfiguration, dataFormat);

		return workbookStyles.computeIfAbsent(styleConfiguration, config -> {
			LOGGER.debug(() -> "Create custom cell style for property: " + property);
			CellStyle style = workbook.createCellStyle();
			configureCellStyle(workbook, style, configuration, cellConfiguration);
			if (dataFormat != null && !dataFormat.trim().equals("")) {
				style.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(dataFormat));
			}
			return style;
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
		Optional<LocalizationContext> ctx = getLocalizationContext();
		if (!ctx.isPresent()) {
			ctx = LocalizationContext.getCurrent();
		}
		return ctx.map(lc -> lc.getMessage(message, true)).orElse(defaultText);
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
			this.exporter.setConfiguration(configuration);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter.Builder#registry(com.holonplatform.artisan.
		 * vaadin.flow.export.xls.PropertyXLSValueProviderRegistry)
		 */
		@Override
		public Builder registry(PropertyXLSValueProviderRegistry registry) {
			this.exporter.setPropertyXLSValueProviderRegistry(registry);
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see
		 * com.holonplatform.artisan.vaadin.flow.export.xls.XLSExporter.Builder#localizationContext(com.holonplatform.
		 * core.i18n.LocalizationContext)
		 */
		@Override
		public Builder localizationContext(LocalizationContext localizationContext) {
			this.exporter.setLocalizationContext(localizationContext);
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

		private static final long serialVersionUID = -6829041483209389400L;

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

	private static class StyleConfiguration implements Serializable {

		private final XLSCellConfiguration configuration;
		private final String dataFormat;

		public StyleConfiguration(XLSCellConfiguration configuration, String dataFormat) {
			super();
			this.configuration = configuration;
			this.dataFormat = dataFormat;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((configuration == null) ? 0 : configuration.hashCode());
			result = prime * result + ((dataFormat == null) ? 0 : dataFormat.hashCode());
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
			StyleConfiguration other = (StyleConfiguration) obj;
			if (configuration == null) {
				if (other.configuration != null)
					return false;
			} else if (!configuration.equals(other.configuration))
				return false;
			if (dataFormat == null) {
				if (other.dataFormat != null)
					return false;
			} else if (!dataFormat.equals(other.dataFormat))
				return false;
			return true;
		}

	}

}
