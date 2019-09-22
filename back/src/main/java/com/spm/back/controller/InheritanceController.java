package com.spm.back.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import com.spm.back.service.impl.InheritanceServiceImpl;
import com.spm.back.service.impl.RecursionServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/complexity")
public class InheritanceController {

	@Autowired
	private InheritanceServiceImpl inheritanceServiceImpl;

	@Value("${codefilepath}")
	private String filepath;

	@GetMapping("/inheritance")
	public ResponseEntity<?> getComplexityByFilePath() {

		if (filepath == null) {
			System.out.println("No file");
		}
		ResponseEntity<?> responseEntity = null;
		try {
			List<Integer> getInheritanceValueList = inheritanceServiceImpl.getCalcInheritanceComplexity(filepath);
			responseEntity = new ResponseEntity<>(getInheritanceValueList, HttpStatus.OK);
		} catch (IOException e) {
			System.out.println("Error has occurred");
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}

	@PostMapping("/inheritance")
	public ResponseEntity<?> getComplexityByFilePath(@RequestBody Reader reader) {

		if (filepath == null) {
			System.out.println("No file");
		}
		ResponseEntity<?> responseEntity = null;
		try {
			List<Integer> getInheritanceValueList = inheritanceServiceImpl.getCalcInheritanceComplexity(reader.getFileLocation());
			responseEntity = new ResponseEntity<>(getInheritanceValueList, HttpStatus.OK);
		} catch (IOException e) {
			System.out.println("Error has occurred");
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}
}
