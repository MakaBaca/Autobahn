package com.autobahn.challenge.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Record {
	
	@Id
	@GeneratedValue(generator="record_seq")
	@SequenceGenerator(name="record_seq",sequenceName="RECORD_SEQ", allocationSize=1, initialValue=1000)
	long id;
	
	@ElementCollection
	List<String> data;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="headerId", nullable = false)
	RecordsHeader header;
	
	public List<String> getData() {
		return data;
	}
	
	public void setData(List<String> data) {
		this.data = data;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public RecordsHeader getHeader() {
		return header;
	}

	public void setHeader(RecordsHeader header) {
		this.header = header;
	}

	@Override
	public String toString() {
		return "Record [id=" + id + ", data=" + data + ", header=" + header + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, header, id);
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
		return Objects.equals(data, other.data) && Objects.equals(header, other.header);
	}
}
