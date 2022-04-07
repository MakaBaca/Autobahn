package com.autobahn.challenge.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.autobahn.challenge.dao.RecordsService;

@Controller
public class AutobahnController {
	
	@Autowired
	RecordsService service;
	
	@RequestMapping(value = "/insertRecords", method = RequestMethod.POST)
	public ResponseEntity<Resource> insertRecord(@RequestBody MultipartFile inputFile){
		Resource r = null;
		File outputFile = null;
		try {
			outputFile = service.insertRecords(inputFile);
			InputStream in = new FileInputStream(outputFile);
			r = new InputStreamResource(in);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + outputFile.getName() + "\"")
				.body(r);
	}
	
	@RequestMapping(value = "/deleteRecords", method = RequestMethod.POST)
	public ResponseEntity<Resource> deleteRecord(@RequestBody MultipartFile inputFile){
		Resource r = null;
		File outputFile = null;
		try {
			outputFile = service.deleteRecords(inputFile);
			InputStream in = new FileInputStream(outputFile);
			r = new InputStreamResource(in);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + outputFile.getName() + "\"")
				.body(r);
	}
	
	@RequestMapping(value = "/getRecords", method = RequestMethod.GET)
	public ResponseEntity<Resource> getRecord(){
		Resource r = null;
		File outputFile = null;
		try {
			outputFile = service.getRecords();
			InputStream in = new FileInputStream(outputFile);
			r = new InputStreamResource(in);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + outputFile.getName() + "\"")
				.body(r);
	}
	
	

}
