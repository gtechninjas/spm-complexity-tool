package com.spm.back.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spm.back.service.ICalcControlStructureFactorComplexityService;
import com.spm.back.service.ICalcSizeFactorComplexityService;
import com.spm.back.service.IFileReaderService;

@Service
@Transactional
public class FileReaderService implements IFileReaderService {

	@Autowired
	private ComplexityConstants complexityConstants = new ComplexityConstants();

	private ICalcSizeFactorComplexityService iCalcSizeFactorComplexityService = new CalcSizeFactorComplexityService();
	
	public String getFileType(String filePath) {

		String fileExtension = filePath.substring(filePath.indexOf('.') + 1);

		if (fileExtension.equals(ComplexityConstants.JAVA_FILE_TYPE)) {
			return ComplexityConstants.JAVA_FILE_TYPE;
		} else if (fileExtension.equals(ComplexityConstants.CPP_FILE_TYPE)) {
			return ComplexityConstants.CPP_FILE_TYPE;
		} else {
			return null;
		}

	}

	public HashMap<String, String> readAllLines(File f) throws IOException {

		/**
		 * Defining Variables
		 */
		String line = null;
		int lineCounter = 0;

		int sizeComplexityCost_perLine = 0;

		String filePath = null;
		HashMap<String, String> codecomplexities = new HashMap<String, String>();

		FileReader fileReader = new FileReader(f);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		filePath = f.getPath();
		String fileExtension = filePath.substring(filePath.indexOf('.') + 1);

		String fileType = getFileType(filePath);
		
		HashMap<String, String> complexitySummary = new HashMap<String, String>();
		
		if (fileType == null) {
			System.out.println("Cannot Calculate Complexity of this file");
			return null;
		}
		int sizeFctorComplexity = 0;

		while ((line = bufferedReader.readLine()) != null) {

			if (complexityConstants.isNonValueExcludeLine(line)) {
				continue;
			}
			
			sizeFctorComplexity = iCalcSizeFactorComplexityService.calculateSizefactorPerLine(line, fileType);
			sizeComplexityCost_perLine += sizeFctorComplexity;
			complexitySummary.put(ComplexityConstants.SIZE_FACTOR_CODE_COMPLEXITY, ""+sizeFctorComplexity);
			
			lineCounter++;

		}
		
		return codecomplexities;
	}

}
