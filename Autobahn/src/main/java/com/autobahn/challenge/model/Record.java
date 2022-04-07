package com.autobahn.challenge.model;

import java.util.List;
import java.util.Objects;

public class Record {

	List<Object> data;
	
	public Record(List<Object> record){
		this.data = record;
	}

	@Override
	public int hashCode() {
		return Objects.hash(data);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Record other = (Record) obj;
		return data.equals(other.data);
	}

	@Override
	public String toString() {
		return "Record [data=" + data + "]";
	}

	public List<Object> getData() {
		return data;
	}
}
