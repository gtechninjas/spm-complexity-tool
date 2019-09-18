package com.spm.back.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spm.back.service.ICalcControlStructureFactorComplexityService;

@Service
@Transactional
public class CalcControlStructureFactorComplexityService implements ICalcControlStructureFactorComplexityService {

	@Autowired
	ComplexityConstants complexityConstants;
	
	@Override
	public List<String> getCalcControlTypeComplexity(String filePath) throws IOException{
		
		File file = new File(filePath);
		List<String> listedControlTypeComplexities = new ArrayList<String>();
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String fileExtension = filePath.substring(filePath.indexOf('.') + 1);
        String fileType = getFileType(filePath);
		String line = null;
		String controlTypeFctorComplexity = null;
		while ((line = bufferedReader.readLine()) != null) {

			if (complexityConstants.isNonValueExcludeLine(line)) {
				continue;
			}
			
			controlTypeFctorComplexity = getControlTypeComplexityCostPerLine_BasedOnType(line);
			listedControlTypeComplexities.add(controlTypeFctorComplexity);

		}
		return listedControlTypeComplexities;
		
	}
	@Override
	public List<String> getCalcControlComplexity_Nested(String filePath) throws IOException{
		
		File file = new File(filePath);
		List<String> listedNestedComplexities = new ArrayList<String>();
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String fileExtension = filePath.substring(filePath.indexOf('.') + 1);
        String fileType = getFileType(filePath);
		String line = null;
		String nestedFctorComplexity = null;
		while ((line = bufferedReader.readLine()) != null) {

			if (complexityConstants.isNonValueExcludeLine(line)) {
				continue;
			}
			
			nestedFctorComplexity = getControlComplexity_NestedPerLine(line);
			listedNestedComplexities.add(nestedFctorComplexity);

		}
		return listedNestedComplexities;
		
	}
	
	public String getControlComplexity_NestedPerLine(String line) {
	  return "";	
	}

	public String getControlTypeComplexityKeyword_Nested(String line) {

		List<String> valuedCollectiveList = new ArrayList<String>();

		valuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.CONTROL_TYPE_SINGLE_VALUED));
		valuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.CONTROL_TYPE_DOUBLE_VALUED));

		Pattern pattern;

		for (String singleValuedOp : valuedCollectiveList) {
			String regExp = complexityConstants.convertOpToRegex(singleValuedOp);
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				System.out.println("1OP : " + matcher.group());
				return matcher.group();

			}

		}

		return null;
	}

	
	public int getOpenbracesCount(String line) {

		Pattern pattern;
		Matcher matcher;

		String openBraceregExp = "[\\{]";
		
		pattern = Pattern.compile(openBraceregExp);
		matcher = pattern.matcher(line);
		
		return matcher.groupCount();
		
	}
	public int getClosedBracesCount(String line) {

		Pattern pattern;
		Matcher matcher;
		
		String closedBraceregExp = "[\\{]";
		
		pattern = Pattern.compile(closedBraceregExp);
		matcher = pattern.matcher(line);
		return matcher.groupCount()*-1;
	}


	public int getControlTypeComplexity_Operator(String line, int valueType) {

		int costForOperator_BasedOnLine = 0;

		List<String> valuedCollectiveList = new ArrayList<String>();

		valuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.CONTROL_TYPE_BITWISE));

		Pattern pattern;

		for (String singleValuedOp : valuedCollectiveList) {
			String regExp = complexityConstants.convertOpToRegex(singleValuedOp);
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				System.out.println("1OP : " + matcher.group());
				costForOperator_BasedOnLine += valueType;

			}
			line = matcher.replaceAll(" ");
		}

		return costForOperator_BasedOnLine;
	}

	public HashMap<Integer, Integer> getControlTypeComplexity_Keyword(String line) {

		int costForKeyword_BasedOnLine = 0;
		String whitespace = "\\s+";

		List<String> singleValuedCollectiveList = new ArrayList<String>();
		singleValuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.CONTROL_TYPE_SINGLE_VALUED));

		List<String> doubleValuedCollectiveList = new ArrayList<String>();
		doubleValuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.CONTROL_TYPE_DOUBLE_VALUED));

		HashMap<Integer, Integer> controlTypeHashMap = new HashMap<Integer, Integer>();
		Pattern pattern;

		for (String singleValuedKey : singleValuedCollectiveList) {
			String regExp = whitespace + singleValuedKey + whitespace;
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {

				costForKeyword_BasedOnLine++;
			}
			line = matcher.replaceAll(" ");
		}
		controlTypeHashMap.put(1, costForKeyword_BasedOnLine);

		if (costForKeyword_BasedOnLine > 0) {
			return controlTypeHashMap;
		}

		for (String doubleValuedKey : doubleValuedCollectiveList) {
			String regExp = whitespace + doubleValuedKey + whitespace;
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {

				costForKeyword_BasedOnLine += 2;

			}
			line = matcher.replaceAll(" ");
		}
		controlTypeHashMap.put(2, costForKeyword_BasedOnLine);

		return controlTypeHashMap;
	}

	public int getControlTypeComplexity(String line) {

		HashMap<Integer, Integer> controlTypeHashMap_Keyword = new HashMap<Integer, Integer>();
		controlTypeHashMap_Keyword = getControlTypeComplexity_Keyword(line);

		Map.Entry<Integer, Integer> entry = controlTypeHashMap_Keyword.entrySet().iterator().next();
		int controlTypeComplexityTotalPerLine = getControlTypeComplexity_Operator(line, entry.getKey());

		return controlTypeComplexityTotalPerLine;
	}

	public String getControlTypeComplexityCostPerLine_BasedOnType(String line) {

		String calculatedControlTypeComplexity;
        if(calculateSwitchControlTypeComplexity_BasedOnType(line) != null) {
        	calculatedControlTypeComplexity = getControlTypeComplexity(line) + " "
    				+ calculateSwitchControlTypeComplexity_BasedOnType(line).size() + "n";
        }
        else {
        	calculatedControlTypeComplexity = getControlTypeComplexity(line) + " ";
        }
		return calculatedControlTypeComplexity;
	}

	public List<String> calculateSwitchControlTypeComplexity_BasedOnType(String line) {

		List<String> switchList = new ArrayList<String>();
		if (line != null) {

			if (line.contains("swicth")) {
				switchList.add("n");
				return switchList;
			}

		}
		return null;
	}
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
	
	public HashMap<Integer, String> readAllLines(File f) throws IOException {

		/**
		 * Defining Variables
		 */
//		String line = null;
//		int lineCounter = 0;
//		String controlTypeOperator = null;
//		int openBracesValue = 0;
//		int closedBracesValue = 0;
//		int controlComplexity_Nested = 0;
//		String filePath = null;
//		String fileExtension = null;
//		HashMap<Integer, Integer> controlTypecodecomplexities_Nested = new HashMap<Integer, Integer>();
//		int controlTypecodecomplexities_Nested_PerLine = 0;
//		List<String> controlType_KeywordPointer = new ArrayList<String>();
//		List<Integer> openBraces_LineCounter = new ArrayList<Integer>();
//		List<Integer> closedBraces_LineCounter = new ArrayList<Integer>();
//		List<Integer> nonValue_LineCounter = new ArrayList<Integer>();
//
//		FileReader fileReader = new FileReader(f);
//		BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//		filePath = f.getPath();
//		fileExtension = filePath.substring(filePath.indexOf('.') + 1);
//
//		String fileType = getFileType(filePath);
//		
//		if (fileType == null) {
//			System.out.println("Cannot Calculate Complexity of this file");
//			return null;
//		}
//		
//
//		while ((line = bufferedReader.readLine()) != null) {
//			
//			controlTypeOperator = getControlTypeComplexityKeyword_Nested(line);
//			openBracesValue = getOpenbracesCount(line);
//			closedBracesValue = getClosedBracesCount(line);
//			lineCounter++;
//			complexityConstants = new ComplexityConstants();
//			
//			if(complexityConstants.isNonValueExcludeLine_ControlType(line)) {
//				continue;
//			}
//			
//			if(controlTypeOperator != null) {
//				controlType_KeywordPointer.add(lineCounter,controlTypeOperator);
//			}
//			else {
//				controlType_KeywordPointer.add(lineCounter,null);
//			}
//			
//			if(openBracesValue > 0) {
//				openBraces_LineCounter.add(lineCounter,openBracesValue);
//			}
//			else{
//				openBraces_LineCounter.add(lineCounter,0);
//
//			}				
//			
//			if(closedBracesValue > 0) {
//				closedBraces_LineCounter.add(lineCounter,closedBracesValue);
//			}
//			else{
//				closedBraces_LineCounter.add(lineCounter,0);
//
//			}
//			
//			if(complexityConstants.isNonValueExcludeLine(line)) {
//				nonValue_LineCounter.add(0);
//			}
//			else {
//				nonValue_LineCounter.add(1);
//			}
//			
//		}
//		
//		while ((line = bufferedReader.readLine()) != null) {
//			
//			lineCounter++;
//			
//			for(int index = 1;index <= lineCounter;index++) {
//				
//				controlTypeOperator = controlType_KeywordPointer.get(index);
//				openBracesValue = openBraces_LineCounter.get(index);
//				closedBracesValue = closedBraces_LineCounter.get(index);
//				
//				if(controlTypeOperator != null) {
//					if(controlTypeOperator.equals("else")) {
//						controlTypecodecomplexities_Nested_PerLine++;
//					}
//				}
//				if(nonValue_LineCounter.get(index) == 1) {
//					
//				}
//				
//			}
//		}
		return null;
	}

}
