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
import com.spm.back.service.impl.CalcSizeFactorComplexityService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/complexity")
public class ComplexitySizeFactorCalcController {
	
	@Autowired
	private CalcSizeFactorComplexityService calcSizeFactorComplexityService; 
	
	@Value("${codefilepath}")
	private String filepath;
	
	@GetMapping("/size")
	public ResponseEntity<?> getComplexityByFilePath() {
		ResponseEntity<?> responseEntity = null;
		
		if(filepath == null) {
			System.out.println("No file");
		}
		try {
			List<Integer> getSizeValueList = calcSizeFactorComplexityService.getCalcSizeComplexity(filepath);
			responseEntity = new ResponseEntity<>(getSizeValueList, HttpStatus.OK);
		} catch (IOException e) {
			System.out.println("Error has occurred");
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}

	
	@PostMapping("/size")
	public ResponseEntity<?> getComplexityByFilePath(@RequestBody Reader reader) {
		ResponseEntity<?> responseEntity = null;
		
		if(filepath == null) {
			System.out.println("No file");
		}
		try {
			List<Integer> getSizeValueList = calcSizeFactorComplexityService.getCalcSizeComplexity(reader.getFileLocation());
			responseEntity = new ResponseEntity<>(getSizeValueList, HttpStatus.OK);
		} catch (IOException e) {
			System.out.println("Error has occurred");
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}


}
