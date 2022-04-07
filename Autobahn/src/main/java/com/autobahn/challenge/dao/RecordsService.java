package com.autobahn.challenge.dao;

import java.io.File;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.autobahn.challenge.error.DuplicateRecordException;
import com.autobahn.challenge.excel.IExcelReader;
import com.autobahn.challenge.excel.IExcelWriter;
import com.autobahn.challenge.model.Record;
import com.autobahn.challenge.persistence.RecordsList;

@Service
public class RecordsService {

	private static final String FAILED_DUE_TO_DUPLICATE = "Failed due to Duplicate";

	private static final String RECORD_DOES_NOT_EXIST = "Record Does Not Exist";

	private static final String SUCCESS = "Success";

	@Autowired
	RecordsList list;

	@Autowired
	IExcelWriter writer;

	@Autowired
	IExcelReader reader;

	public File insertRecords(MultipartFile excelFile) throws Exception {
		reader.intializeWorkBook(excelFile);
		writer.createWorkBook();
		writer.createSheet("Output");
		List<String> headers = reader.getHeader();
		headers.add("Status");
		writer.setHeader(headers.toArray(new String[headers.size()]));
		reader.skipHeader();
		List<Object> obj = null;
		while (reader.hasNextRow()) {
			try {
				obj = reader.getRowValues();
				list.addRecord(new Record(obj));
				String[] tmp = getRecordWithStatus(obj, SUCCESS);
				writer.createRow(writer.getLastRowNumber() + 1, tmp);
			} catch (DuplicateRecordException e) {
				String[] tmp = getRecordWithStatus(obj, FAILED_DUE_TO_DUPLICATE);
				writer.createRow(writer.getLastRowNumber() + 1, tmp);
			}
		}
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		return writer.createExcel("InsertSheet_" + timeStampMillis);
	}

	public File deleteRecords(MultipartFile excelFile) throws Exception {
		reader.intializeWorkBook(excelFile);
		writer.createWorkBook();
		writer.createSheet("Output");
		List<String> headers = reader.getHeader();
		headers.add("Status");
		writer.setHeader(headers.toArray(new String[headers.size()]));
		reader.skipHeader();
		List<Object> obj = null;
		while (reader.hasNextRow()) {
			obj = reader.getRowValues();
			if (list.deleteRecord(new Record(obj))) {
				String[] tmp = getRecordWithStatus(obj, SUCCESS);
				writer.createRow(writer.getLastRowNumber() + 1, tmp);
			} else {
				String[] tmp = getRecordWithStatus(obj, RECORD_DOES_NOT_EXIST);
				writer.createRow(writer.getLastRowNumber() + 1, tmp);
			}
		}
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		return writer.createExcel("DeleteSheet_" + timeStampMillis);
	}
	
	public File getRecords() throws Exception {
		writer.createWorkBook();
		writer.createSheet("Records");
		writer.setHeader(new String[] {"Generic Header"});
		for(Record r : list.getRecords()) {
			String[] values = getRecord(r.getData());
			writer.createRow(writer.getLastRowNumber()+1, values);
		}
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		return writer.createExcel("RecordsSheet_" + timeStampMillis);
	}
	
	private String[] getRecordWithStatus(List<Object> data, String status) {
		String[] records = new String[data.size()+1];
		int i = 0;
		for(Object obj : data) {
			records[i] = obj.toString();
			i++;
		}
		records[i] = status;
		return records;
	}
	
	private String[] getRecord(List<Object> data) {
		String[] records = new String[data.size()+1];
		int i = 0;
		for(Object obj : data) {
			records[i] = obj.toString();
			i++;
		}
		return records;
	}
}
