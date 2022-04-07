package com.autobahn.challenge.persistence;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.autobahn.challenge.error.DuplicateRecordException;
import com.autobahn.challenge.model.Record;

@Component
public class RecordsList {
	
	private List<Record> records = new ArrayList<>();
	
	public void addRecord(Record r) throws DuplicateRecordException {
		if(records.contains(r))
			 throw new DuplicateRecordException();
		else
			 records.add(r);
	}
	
	public boolean isRecordExist(Record r) {
		return records.contains(r);
	}
	
	public boolean deleteRecord(Record r) {
		return records.remove(r);
	}

	public List<Record> getRecords() {
		return records;
	}
	
}
