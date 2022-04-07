package com.autobahn.challenge.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IExcelReader {
	
	public void intializeWorkBook(MultipartFile file) throws FileNotFoundException, IOException;
	
	public boolean skipHeader();
	
	public boolean hasNextRow();
	
	public List<Object> getRowValues();
	
	public List<String> getHeader();
	
}
