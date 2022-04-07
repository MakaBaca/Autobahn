package com.autobahn.challenge.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PoiExcelWriter implements IExcelWriter {

	private XSSFWorkbook workBook;

	private XSSFSheet sheet;

	private XSSFRow row;

	private XSSFCell cell;

	private XSSFFont font;

	private XSSFCellStyle style;

	@Override
	public void createWorkBook() {
		workBook = new XSSFWorkbook();
	}

	@Override
	public File createExcel(String excelName) throws Exception{
		File f = new File(excelName);
		try {
			FileOutputStream out = new FileOutputStream(f);
			workBook.write(out);
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File Not Found" + e);
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IO error" + e);
			throw e;
		}
		return f;
	}

	@Override
	public void createSheet(String SheetName) {
		sheet = workBook.createSheet(SheetName);
	}

	@Override
	public void createRow(int index) {
		row = sheet.createRow(index);
	}
	
	@Override
	public void createRow(int index, String[] values) {
		row = sheet.createRow(index);
		int cellIndex = 0;
		for(String value: values) {
			createCell(index, cellIndex);
			setCellValue(value);
			cellIndex++;
		}
	}
	
	@Override
	public int getLastRowNumber() {
		return sheet.getLastRowNum();
	}

	@Override
	public void createCell(int rowIndex, int cellIndex) {
		row = sheet.getRow(rowIndex);
		if (row == null) {
			row = sheet.createRow(rowIndex);
		}
		cell = row.createCell(cellIndex);

	}

	@Override
	public void createCell(int cellIndex) {
		cell = row.createCell(cellIndex);
	}

	@Override
	public void setCellStyle(String fontName, int fontSize, int fontColor) {
		font = workBook.createFont();
		font.setFontHeightInPoints((short) fontSize);
		font.setFontName(fontName);
		font.setItalic(true);
		if (fontColor == IExcelWriter.RED) {
			font.setColor(HSSFColorPredefined.RED.getIndex());
		} else if (fontColor == IExcelWriter.BLUE) {
			font.setColor(HSSFColorPredefined.BLUE.getIndex());
		} else if (fontColor == IExcelWriter.GREEN) {
			font.setColor(HSSFColorPredefined.GREEN.getIndex());
		} else {
			font.setColor(HSSFColorPredefined.BLACK.getIndex());
		}

		// Set font into style
		style = workBook.createCellStyle();
		style.setFont(font);
		cell.setCellStyle(style);

	}

	@Override
	public void setCellValue(String value) {
		cell.setCellValue(value);

	}

	@Override
	public void setHeader(String[] cols) {
		row = sheet.createRow(0);
		int i = 0;
		for (String header : cols) {
			cell = row.createCell(i++);
			cell.setCellValue(header);
		}
	}

	@Override
	public String getCellValue(int rowIndex, int cellIndex) {
		XSSFCell tempCell = sheet.getRow(rowIndex).getCell(cellIndex);
		return tempCell.getStringCellValue();
	}

}
