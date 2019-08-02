package com.spm.back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/complexity")
public class ComplexityCalcController {
	
	@GetMapping("/size")
	public ResponseEntity<?> getKeywordById(@PathVariable("id") Long id) {
		ResponseEntity<?> responseEntity = null;
		
		return responseEntity;
	}


}
