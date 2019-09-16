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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.spm.back.service.impl.InheritanceServiceImpl;
import com.spm.back.service.impl.RecursionServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/complexity")
public class InheritanceController {

	@Autowired
	private InheritanceServiceImpl inheritanceServiceImpl;
	
	@GetMapping("/inheritance")
	public ResponseEntity<?> getComplexityByFilePath(@SessionAttribute(name = "filepath")
    String filepath) {
		ResponseEntity<?> responseEntity = null;
		try {
			List<Integer> getInheritanceValueList = inheritanceServiceImpl.getCalcInheritanceComplexity(filepath);
		} catch (IOException e) {
			System.out.println("Error has occurred");
		}
		return responseEntity;
	}
}
