package com.spm.back.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.spm.back.service.ICalcControlStructureFactorComplexityService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/complexity")
public class ControlFactorComplexityController {

	@Autowired
	private ICalcControlStructureFactorComplexityService calcControlStructureFactorComplexityService; 
	
	@GetMapping("/controltype")
	public ResponseEntity<?> getComplexityByFilePath_BasedOnType(@SessionAttribute(name = "filepath")
    String filepath) {
		if(filepath != null) {
			System.out.println("No file");
		}
		ResponseEntity<?> responseEntity = null;
		try {
			List<String> getControlTypeValueList = calcControlStructureFactorComplexityService.getCalcControlTypeComplexity(filepath);
		} catch (IOException e) {
			System.out.println("Error has occurred");
		}
		return responseEntity;
	}
	
	@GetMapping("/nested")
	public ResponseEntity<?> getComplexityByFilePath_BasedOnNested(@SessionAttribute(name = "filepath")
    String filepath) {
		if(filepath != null) {
			System.out.println("No file");
		}
		ResponseEntity<?> responseEntity = null;
		try {
			List<String> getNestedValueList = calcControlStructureFactorComplexityService.getCalcControlComplexity_Nested(filepath);
		} catch (IOException e) {
			System.out.println("Error has occurred");
		}
		return responseEntity;
	}
}
