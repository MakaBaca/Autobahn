package com.autobahn.challenge.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class RecordsHeader {
	
	@Id
	@GeneratedValue(generator="records_list_seq")
	@SequenceGenerator(name="records_list_seq",sequenceName="RECORDS_LIST_SEQ", allocationSize=1, initialValue=1000)
	private long headerId;
	
	@ElementCollection
	private List<String> headers;
	
	public long getId() {
		return headerId;
	}

	public void setId(long id) {
		this.headerId = id;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	@Override
	public String toString() {
		return "RecordsList [id=" + headerId + ", headers=" + headers + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(headers, headerId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecordsHeader other = (RecordsHeader) obj;
		return Objects.equals(headers, other.headers) && headerId == other.headerId;
	}

	
		
}
