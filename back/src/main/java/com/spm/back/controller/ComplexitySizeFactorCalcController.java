package com.spm.back.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.spm.back.service.impl.CalcSizeFactorComplexityService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/complexity")
public class ComplexitySizeFactorCalcController {
	
	@Autowired
	private CalcSizeFactorComplexityService calcSizeFactorComplexityService; 
	
	@GetMapping("/size")
	public ResponseEntity<?> getComplexityByFilePath() {
		ResponseEntity<?> responseEntity = null;
		String filepath = "C:\\Users\\diaalk\\Desktop\\Test.java";
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


}
