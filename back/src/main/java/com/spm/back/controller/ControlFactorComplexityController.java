package com.spm.back.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.spm.back.domain.Reader;
import com.spm.back.service.ICalcControlStructureFactorComplexityService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/complexity")
public class ControlFactorComplexityController {

	@Autowired
	private ICalcControlStructureFactorComplexityService calcControlStructureFactorComplexityService; 
	
	@Value("${codefilepath}")
	private String filepath;
	
	@GetMapping("/controltype")
	public ResponseEntity<?> getComplexityByFilePath_BasedOnType(String filelocation) {
		if(filepath != null) {
			System.out.println("No file");
		}
		ResponseEntity<?> responseEntity = null;
		try {
			List<String> getControlTypeValueList = calcControlStructureFactorComplexityService.getCalcControlTypeComplexity(filepath);
			responseEntity = new ResponseEntity<>(getControlTypeValueList, HttpStatus.OK);
		} catch (IOException e) {
			System.out.println("Error has occurred");
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}
	
	@PostMapping("/controltype")
	public ResponseEntity<?> getComplexityByFilePath_BasedOnType(@RequestBody Reader reader) {
		if(filepath != null) {
			System.out.println("No file");
		}
		ResponseEntity<?> responseEntity = null;
		try {
			List<String> getControlTypeValueList = calcControlStructureFactorComplexityService.getCalcControlTypeComplexity(reader.getFileLocation());
			responseEntity = new ResponseEntity<>(getControlTypeValueList, HttpStatus.OK);
		} catch (IOException e) {
			System.out.println("Error has occurred");
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}
	
	@GetMapping("/nested")
	public ResponseEntity<?> getComplexityByFilePath_BasedOnNested(String filelocation) {
		if(filepath != null) {
			System.out.println("No file");
		}
		ResponseEntity<?> responseEntity = null;
		try {
			List<String> getNestedValueList = calcControlStructureFactorComplexityService.getCalcControlComplexity_Nested(filepath);
			responseEntity = new ResponseEntity<>(getNestedValueList, HttpStatus.OK);
		} catch (IOException e) {
			System.out.println("Error has occurred");
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}
	
	@PostMapping("/nested")
	public ResponseEntity<?> getComplexityByFilePath_BasedOnNested(@RequestBody Reader reader) {
		if(filepath != null) {
			System.out.println("No file");
		}
		ResponseEntity<?> responseEntity = null;
		try {
			List<String> getNestedValueList = calcControlStructureFactorComplexityService.getCalcControlComplexity_Nested(reader.getFileLocation());
			responseEntity = new ResponseEntity<>(getNestedValueList, HttpStatus.OK);
		} catch (IOException e) {
			System.out.println("Error has occurred");
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}
}

