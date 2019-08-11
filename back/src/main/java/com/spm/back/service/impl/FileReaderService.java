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

import com.spm.back.service.ICalcSizeFactorComplexityService;
import com.spm.back.service.IFileReaderService;
import com.spm.back.service.TotalComplexityService;
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
	private TotalComplexityService totalComplexityService;
	private ICalcControlStructureFactorComplexityService calcControlStructureFactorComplexityService;

	public String getFileType(String filePathloc) {

		String fileExtension = filePathloc.substring(filePathloc.indexOf('.') + 1);

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
		int braketsCounter = 0;

		String sizeComplexityCost_perLine = null;
		String filePath = null;
		String fileExtension = null;
	
		int complexityControlStructure = 0;
		int complexityNestingControlStructure = 0;
		int complexityInheritance = 0;
		String complexityTotalWeight = null;
		String complexityProgramStatement = null;
		int recursionCount = 0;
		HashMap<String, Integer> codecomplexities = new HashMap<String, Integer>();
		int controlTypeComplexityCost_perLine = 0;
		List<String> controlTypeComplexity_SwitchList = new ArrayList<String>();
		String totalControlTypeBasedCost;
		
		String controlTypeOp  = null;
		String filePath1 = null;
		String fileExtension1 = null;
		HashMap<String, String> codecomplexities1 = new HashMap<String, String>();
		RecursionServiceImpl recursionServiceImpl = new RecursionServiceImpl();

		FileReader fileReader = new FileReader(f);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		filePath1 = f.getPath();
		fileExtension1 = filePath1.substring(filePath1.indexOf('.') + 1);


		String fileType = getFileType(filePath1);

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
			// Obtaining Recursion Count
			recursionCount = recursionServiceImpl.calculateRecurionMethod(wordArrayList);
			
			/**
             * Start of Navod Content
             */
			controlTypeComplexityCost_perLine += calcControlStructureFactorComplexityService
					.calculateControlTypeComplexityCostPerLine_BasedOnType(line, wordArrayList);
			if (calcControlStructureFactorComplexityService
					.calculateControlTypeComplexityCostPerLine_BasedOnType(line) != null)
				controlTypeComplexity_SwitchList.add(calcControlStructureFactorComplexityService
						.calculateControlTypeComplexityCostPerLine_BasedOnType(line));
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
			complexityTotalWeight += totalComplexityService.totalComplexityWeight(complexityControlStructure, complexityNestingControlStructure, complexityInheritance);
			complexityProgramStatement += totalComplexityService.complexityProgramStatement(sizeComplexityCost_perLine, complexityTotalWeight);
			lineCounter++;

		}
		
		
		codecomplexities1.put(ComplexityConstants.SIZE_FACTOR_CODE_COMPLEXITY, sizeComplexityCost_perLine);
		codecomplexities1.put(ComplexityConstants.TOTAL_WEIGHT_COMPLEXITY, complexityTotalWeight);
		codecomplexities1.put(ComplexityConstants.COMPLEXITY_PROGRAM_STATEMENT, complexityProgramStatement);
		codecomplexities1.put(ComplexityConstants.COMPLEXITY_RECURSION, Integer.toString(recursionCount));
		System.out.println("Total count " + sizeComplexityCost_perLine);
		System.out.println(ComplexityConstants.TOTAL_WEIGHT_COMPLEXITY + complexityTotalWeight);
		System.out.println(ComplexityConstants.COMPLEXITY_PROGRAM_STATEMENT + complexityProgramStatement);
		System.out.println("Number of Lines " + lineCounter);
		totalControlTypeBasedCost = calcControlStructureFactorComplexityService
				.totalControlTypeComplexityCostPerLine_BasedOnType(controlTypeComplexityCost_perLine,
						controlTypeComplexity_SwitchList);
		codecomplexities1.put(ComplexityConstants.SIZE_FACTOR_CODE_COMPLEXITY, ""+sizeComplexityCost_perLine);
		codecomplexities1.put(ComplexityConstants.CONTROL_TYPE_FACTOR_CODE_COMPLEXITY, totalControlTypeBasedCost);
		System.out.println("Total count " + sizeComplexityCost_perLine);
		System.out.println("Number of Lines " + lineCounter);
		System.out.println("Recursion Count " + recursionCount);
		System.out.println("Control Type " + totalControlTypeBasedCost);
		return codecomplexities1;
	}

}
