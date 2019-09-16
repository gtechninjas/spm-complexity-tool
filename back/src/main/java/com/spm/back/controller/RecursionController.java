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

import com.spm.back.service.impl.RecursionServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/complexity")
public class RecursionController {

	@Autowired
	private RecursionServiceImpl recursionServiceImpl;
	
	@GetMapping("/recursion")
	public ResponseEntity<?> getComplexityByFilePath(@SessionAttribute(name = "filepath")
    String filepath) {
		if(filepath != null) {
			System.out.println("No file");
		}
		ResponseEntity<?> responseEntity = null;
		try {
			List<Integer> getRecursionValueList = recursionServiceImpl.getCalcRecursionComplexity(filepath);
			responseEntity = new ResponseEntity<>(getRecursionValueList, HttpStatus.OK);
		} catch (IOException e) {
			System.out.println("Error has occurred");
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}
}
