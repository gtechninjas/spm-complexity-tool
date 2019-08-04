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
	
	public HashMap<String, String> readAllLines(File f) throws IOException {

		/**
		 * Defining Variables
		 */
		String line = null;
		int lineCounter = 0;
		int braketsCounter = 0;

		int sizeComplexityCost_perLine = 0;

		int controlTypeComplexityCost_perLine = 0;
		int nestedControlTypeComplexityCost_perLine = 0;
		List<String> controlTypeComplexity_SwitchList = new ArrayList<String>();
		String totalControlTypeBasedCost;

		String filePath = null;
		String fileExtension = null;
		HashMap<String, String> codecomplexities = new HashMap<String, String>();

		FileReader fileReader = new FileReader(f);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		filePath = f.getPath();
		fileExtension = filePath.substring(filePath.indexOf('.') + 1);

		ICalcSizeFactorComplexityService iCalcSizeFactorComplexityService = new CalcSizeFactorComplexityService();

		String fileType = getFileType(filePath);

		if (fileType == null) {
			System.out.println("Cannot Calculate Complexity of this file");
			return null;
		}

		HashMap<String, Integer> bracesCounter_controlTypeOperatorMap = new HashMap<String, Integer>();
		HashMap<String, Integer> value_controlTypeOperatorMap = new HashMap<String, Integer>();

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
				if (word.contains(".")) {
					dottedList = Arrays.asList(word.split("\\."));
					sizeComplexityCost_perLine += dottedList.size() - 1;
					continue;
				}
				sizeComplexityCost_perLine += iCalcSizeFactorComplexityService.complexitySizeFactorValues(word,
						fileType);

			}
			for (String word : dottedList) {
				sizeComplexityCost_perLine += iCalcSizeFactorComplexityService.complexitySizeFactorValues(word,
						fileType);

			}

			bracesCounter_controlTypeOperatorMap = calcControlStructureFactorComplexityService
					.mapBracesCounterWithControlStructures(wordArrayList, bracesCounter_controlTypeOperatorMap);
			value_controlTypeOperatorMap = calcControlStructureFactorComplexityService
					.mapValuesWithControlStructures(wordArrayList, value_controlTypeOperatorMap,bracesCounter_controlTypeOperatorMap);
			if (sizeComplexityCost_perLine > 0) {
				nestedControlTypeComplexityCost_perLine += calcControlStructureFactorComplexityService
						.claculateNestedControlComplexityCostPerLine(wordArrayList,
								bracesCounter_controlTypeOperatorMap, value_controlTypeOperatorMap);
			}

			lineCounter++;

		}
		totalControlTypeBasedCost = calcControlStructureFactorComplexityService
				.totalControlTypeComplexityCostPerLine_BasedOnType(controlTypeComplexityCost_perLine,
						controlTypeComplexity_SwitchList);
		codecomplexities.put(ComplexityConstants.SIZE_FACTOR_CODE_COMPLEXITY, "" + sizeComplexityCost_perLine);
		codecomplexities.put(ComplexityConstants.CONTROL_TYPE_FACTOR_CODE_COMPLEXITY, totalControlTypeBasedCost);
		System.out.println("Total count " + sizeComplexityCost_perLine);
		System.out.println("Number of Lines " + lineCounter);

		System.out.println("Control Type " + totalControlTypeBasedCost);
		System.out.println("Nested Control Type " + nestedControlTypeComplexityCost_perLine);
		return codecomplexities;
	}


}
