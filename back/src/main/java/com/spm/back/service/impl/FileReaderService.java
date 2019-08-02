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
	private ComplexityConstants complexityConstants;
	
	@Autowired
	private ICalcSizeFactorComplexityService iCalcSizeFactorComplexityService;
	
	@Autowired
	private ICalcControlStructureFactorComplexityService calcControlStructureFactorComplexityService;

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
	
	public HashMap<String, Integer> readAllLines(File f) throws IOException {

		/**
		 * Defining Variables
		 */
		String line = null;
		int lineCounter = 0;
		int braketsCounter = 0;

		int sizeComplexityCost_perLine = 0;
		int controlTypeComplexityCost_perLine = 0;
		String controlTypeOp  = null;
		String filePath = null;
		String fileExtension = null;
		HashMap<String, Integer> codecomplexities = new HashMap<String, Integer>();

		FileReader fileReader = new FileReader(f);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		filePath = f.getPath();
		fileExtension = filePath.substring(filePath.indexOf('.') + 1);


		String fileType = getFileType(filePath);

		if (fileType == null) {
			System.out.println("Cannot Calculate Complexity of this file");
			return null;
		}
		
		while ((line = bufferedReader.readLine()) != null) {

			if (complexityConstants.isNonValueExcludeLine(line)) {
				continue;
			}
			  
			sizeComplexityCost_perLine += iCalcSizeFactorComplexityService.getQuotationCount(line);
            line = iCalcSizeFactorComplexityService.quotationsOmmited(line);
            
            
			
			List<String> wordArrayList = Arrays.asList(line.split("\\s+"));
			List<String> dottedList = new ArrayList<String>();
			
			/**
             * Start of Navod Content
             */
            controlTypeComplexityCost_perLine += calcControlStructureFactorComplexityService.calculateControlTypeComplexityCostPerLine_BasedOnType(line, wordArrayList);
            /**
             * End of Navod Content
             */
			
			for (String word : wordArrayList) {
				if(word.contains(".")) {
					dottedList = Arrays.asList(word.split("\\."));
					sizeComplexityCost_perLine += dottedList.size() - 1;
					continue;
				}			
				sizeComplexityCost_perLine += iCalcSizeFactorComplexityService.complexitySizeFactorValues(word, fileType);
				
			}
			for (String word : dottedList) {
				sizeComplexityCost_perLine += iCalcSizeFactorComplexityService.complexitySizeFactorValues(word, fileType);
				
			}

			lineCounter++;

		}
		
		
		codecomplexities.put(ComplexityConstants.SIZE_FACTOR_CODE_COMPLEXITY, sizeComplexityCost_perLine);
		System.out.println("Total count " + sizeComplexityCost_perLine);
		System.out.println("Number of Lines " + lineCounter);
		
		System.out.println("Control Type Cost " + controlTypeComplexityCost_perLine);
		return codecomplexities;
	}

}
