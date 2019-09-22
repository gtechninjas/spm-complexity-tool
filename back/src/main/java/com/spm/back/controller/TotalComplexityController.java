package com.spm.back.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spm.back.domain.ComplexitySummary;
import com.spm.back.domain.Reader;
import com.spm.back.service.impl.CalcControlStructureFactorComplexityService;
import com.spm.back.service.impl.CalcSizeFactorComplexityService;
import com.spm.back.service.impl.InheritanceServiceImpl;
import com.spm.back.service.impl.RecursionServiceImpl;
import com.spm.back.service.impl.TotalComplexityServiceImpl;

import net.bytebuddy.build.Plugin.Engine.Summary;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/complexity")
public class TotalComplexityController {	
	
	@Autowired
	private TotalComplexityServiceImpl totalComplexityServiceImpl;

	@Value("${codefilepath}")
	private String filepath;
	
	@PostMapping("/total")
	public ResponseEntity<?> getTotalComplexityByFilePath(@RequestBody Reader reader) {
		ResponseEntity<?> responseEntity = null;
		System.out.println("file : " + reader.getFileLocation());
		 
		if(reader.getFileLocation() == null) {
			System.out.println("No file");
			return null;
		}
		try {
		    List<ComplexitySummary> complexitySummaryList = totalComplexityServiceImpl.getComplexitySummary(reader.getFileLocation());
			responseEntity = new ResponseEntity<>(complexitySummaryList, HttpStatus.OK);
		} catch (IOException e) {
			System.out.println("Error has occurred");
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}
	

}
