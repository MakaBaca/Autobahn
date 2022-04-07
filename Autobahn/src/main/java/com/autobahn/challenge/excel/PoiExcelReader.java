package com.autobahn.challenge.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Primary
public class PoiExcelReader implements IExcelReader {

	private XSSFWorkbook workBook;

	private XSSFSheet sheet;

	Iterator<Row> iterator;

	@Override
	public void intializeWorkBook(MultipartFile inputFile) throws IOException {
		InputStream excelFile = inputFile.getInputStream();
		workBook = new XSSFWorkbook(excelFile);
		sheet = workBook.getSheetAt(0);
		iterator = sheet.iterator();
	}

	@Override
	public boolean skipHeader() {
		if (hasNextRow()) {
			iterator.next();
			return true;
		}
		return false;
	}

	@Override
	public boolean hasNextRow() {
		return iterator.hasNext();
	}

	@Override
	public List<Object> getRowValues() {
		Row currentRow = iterator.next();
		Iterator<Cell> cellIterator = currentRow.iterator();
		List<Object> values = new ArrayList<>();
		while (cellIterator.hasNext()) {
			Cell currentCell = cellIterator.next();
			if (currentCell.getCellType() == CellType.STRING) {
				values.add(currentCell.getStringCellValue());
			} else if (currentCell.getCellType() == CellType.NUMERIC) {
				values.add(currentCell.getNumericCellValue());
			}else if(currentCell.getCellType() == CellType.BOOLEAN) {
				values.add(currentCell.getBooleanCellValue());
			}else {
				values.add(currentCell.getStringCellValue());
			}
		}
		return values;
	}

	@Override
	public List<String> getHeader() {
		List<String> headers = new ArrayList<>();
		Iterator<Cell> cellIterator =  sheet.getRow(0).iterator();
		while(cellIterator.hasNext()) {
			Cell currentCell = cellIterator.next();
			headers.add(currentCell.getStringCellValue());
		}
		return headers;
	}

}
