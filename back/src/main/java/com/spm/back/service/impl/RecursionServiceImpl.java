package com.spm.back.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spm.back.service.RecursionService;

@Service
@Transactional
public class RecursionServiceImpl implements RecursionService {

	@Autowired
	private ComplexityConstants complexityConstants;

	@Autowired
	private CalcSizeFactorComplexityService calcSizeFactorComplexityService;

	@Autowired
	private CalcControlStructureFactorComplexityService calcControlStructureFactorComplexityService;

	@Autowired
	private InheritanceServiceImpl inheritanceServiceImpl;
	
	private static boolean TryParse(String input) {
		try {
			Integer.parseInt(input.trim());		
			return true;
		}
		catch (NumberFormatException e) {
			return false; 
		}
	}

	@Override
	public List<String> getTotalWeightList(List<String> controlTypeList, List<String> nestedList,
			List<Integer> inheritanceList,int totLineCounter) {
		
		List<String> totalWeightList = new ArrayList<String>();
		totLineCounter--;
		for (int i = 0; i <= totLineCounter; i++) {
			String recursionVal = "";
			int controlTypeVal = 0;
			int nestedVal = 0;
			int programsConstantVal = 0;
			boolean isStringControlType = false;
			boolean isStringNested = false;
			
			System.out.println("LINE NUM "+i+" CONTROL TYPE VAL: "+controlTypeList.get(i));
			System.out.println("LINE NUM "+i+" NESTED TYPE VAL: "+nestedList.get(i));
			if(controlTypeList.get(i) == null || controlTypeList.get(i) == "" ||TryParse(controlTypeList.get(i))) {
				System.out.println("TRUE INT CONTROL TYPE");
				if(controlTypeList.get(i) == null|| controlTypeList.get(i) == "") {
					
					controlTypeVal = 0;
				}
				else {
					controlTypeVal = Integer.parseInt(controlTypeList.get(i).trim());
				}
				isStringControlType = true;
			}
			if(nestedList.get(i) == null || nestedList.get(i) == "" || TryParse(nestedList.get(i))) {
				System.out.println("TRUE INT NESTED TYPE");
				if(nestedList.get(i) == null|| nestedList.get(i) == "") {
					nestedVal = 0;
				}
				else {
					nestedVal = Integer.parseInt(nestedList.get(i).trim());
				}
				isStringNested = true;
			}
			if(isStringControlType == true && isStringNested == true) {
				programsConstantVal = (controlTypeVal+nestedVal+inheritanceList.get(i));
				totalWeightList.add(programsConstantVal+"");
			}
			if(isStringControlType == true && isStringNested != true) {
				programsConstantVal = controlTypeVal+inheritanceList.get(i);				
				totalWeightList.add("("+programsConstantVal+"+"+nestedList.get(i)+")");
			}
			if(isStringControlType != true && isStringNested == true) {
				programsConstantVal = nestedVal+inheritanceList.get(i);				
				totalWeightList.add("("+programsConstantVal+"+"+controlTypeList.get(i)+")");
			}
			if(isStringControlType != true && isStringNested != true) {
				programsConstantVal = inheritanceList.get(i);				
				totalWeightList.add("("+programsConstantVal+"+"+nestedList+"+"+controlTypeList.get(i)+")");
			}
		}
		System.out.println("TOTAL WEIGHT LIST"+totalWeightList);
		return totalWeightList;
		
	}
    @Override
	public List<String> getComplexityProgramConstant(List<String> totalWeightList,List<Integer> sizeList, int totLineCounter) {
		List<String> programConstantList = new ArrayList<String>();
		for (int i = 0; i < totLineCounter; i++) {
			int totalWeightVal = 0;
			int programsConstantVal = 0;
			boolean isIntTotalWeight = false;

			if(totalWeightList.get(i) == null || totalWeightList.get(i) == "" ||TryParse(totalWeightList.get(i))) {
				System.out.println("TRUE INT TOTAL WEIGHT");
				if(totalWeightList.get(i) == null|| totalWeightList.get(i) == "") {
					
					totalWeightVal = 0;
				}
				else {
					totalWeightVal = Integer.parseInt(totalWeightList.get(i).trim());
				}
				isIntTotalWeight = true;
			}
			if(isIntTotalWeight == true) {
				programsConstantVal = (totalWeightVal) *sizeList.get(i);
				programConstantList.add(programsConstantVal+"");
			}
			if(isIntTotalWeight != true) {			
				programConstantList.add("("+totalWeightList.get(i).trim()+")" +sizeList.get(i));
			}
		}
		return programConstantList;
	}

	@Override
	public List<String> getCalcRecursionComplexity(String filePath) throws IOException {

		File file = new File(filePath);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String fileExtension = filePath.substring(filePath.indexOf('.') + 1);
		String line = null;
		List<String> recursionList = new ArrayList<String>();
		List<String> controlTypeList = calcControlStructureFactorComplexityService
				.getCalcControlTypeComplexity(filePath);
		List<String> nestedList = calcControlStructureFactorComplexityService.getCalcControlComplexity_Nested(filePath);
		List<Integer> inheritanceList = inheritanceServiceImpl.getCalcInheritanceComplexity(filePath);
		List<Integer> sizeList = calcSizeFactorComplexityService.getCalcSizeComplexity(filePath);
		int recursionFctorComplexity = 0;
		int lineCounter = 0;
		int lineTotal = 0;
		int start = 0;
		int end = 0;
		List<Integer> startAndEndLineList = new ArrayList<Integer>();
		String extractedMethodName = null;
		List<String> extractedMethodNameList_Lines = getAllMethodNameLines(file);

		int cpsValue = 1;
		while ((line = bufferedReader.readLine()) != null) {
			lineTotal++;
		}
		List<String> twValueList = getTotalWeightList(controlTypeList, nestedList, inheritanceList, lineTotal);
		List<String> cpsValueList = getComplexityProgramConstant(twValueList, sizeList, lineTotal);
		
		String recursionArr[] = new String[lineTotal];
		Arrays.fill(recursionArr, "0");
		for (String extractedMethodName_Line : extractedMethodNameList_Lines) {
			extractedMethodName = getExtractedMethodNames(extractedMethodName_Line);
			if (methodRecursionStartAndEnd(file, extractedMethodName_Line, extractedMethodName) != null) {
				startAndEndLineList
						.addAll(methodRecursionStartAndEnd(file, extractedMethodName_Line, extractedMethodName));
				System.out.println("METHOD " + extractedMethodName);
			}
		}
		System.out.println("ITERATIONS " + startAndEndLineList.size() / 2);
		for (int i = 1; i <= startAndEndLineList.size() / 2; i++) {
			start = startAndEndLineList.get(i - 1) - 1;
			end = startAndEndLineList.get(i);
			for (int j = start; j <= end; j++) {
				try {
					recursionArr[j] = (Integer.parseInt(cpsValueList.get(j)) * 2)+"";
				}
				catch (NumberFormatException e) {
					recursionArr[j] = "("+cpsValueList.get(j)+")"+2;
				}
				
			}
		}
		for (String val : recursionArr) {
			recursionList.add(val);
		}
		System.out.println("RECURSION LIST "+recursionList);
		return recursionList;

	}

	public String replaceKeywords_WithWhiteSpace(String line) {

		String whitespace = "([\"\\s+\"]|[\"\\(\"])+";
		List<String> collectiveLsit = new ArrayList<String>();
		collectiveLsit.addAll(Arrays.asList(ComplexityConstants.KEY_WORDS));
		collectiveLsit.addAll(Arrays.asList(ComplexityConstants.MANIPULATORS));
		collectiveLsit.addAll(Arrays.asList(ComplexityConstants.SPECIAL_KEYWORDS));
		collectiveLsit.addAll(Arrays.asList(ComplexityConstants.NON_VALUE_EXCLUDE_KEYWORD));
		Pattern pattern;

		for (String keyword : collectiveLsit) {
			String regExp = keyword;
			pattern = Pattern.compile(regExp + whitespace);
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				line = matcher.replaceAll(" ");
			}
		}

		return line;
	}

	public String getExtractedMethodNames(String methodLine) {
		if (methodLine != null) {
			return replaceKeywords_WithWhiteSpace(methodLine).trim().split("\\(")[0];
		}
		return null;
	}

	public List<String> getAllMethodNameLines(File file) {
		List<String> getMethods = new ArrayList<>();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				Pattern p = Pattern.compile(
						"(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])");
				Matcher m = p.matcher(line);
				while (m.find()) {
					String methodNameLine = m.group();
					getMethods.add(methodNameLine);
				}

			}

			return getMethods;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Integer> methodRecursionStartAndEnd(File file, String methodLineName, String methodName)
			throws FileNotFoundException, IOException {
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		List<Integer> startAndEndLineNumber = new ArrayList<Integer>();
		String open = "{";
		String closed = "}";
		Boolean isRecursion = false;
		int lineCounter = 0;
		int braceCounter = 0;
		while ((line = bufferedReader.readLine()) != null) {
			lineCounter++;
			if (line.contains(methodLineName)) {
				System.out.println("START " + lineCounter);
				startAndEndLineNumber.add(lineCounter);
			}
			System.out.println(braceCounter);
			if (line.contains("return "))
				System.out.println("YES " + methodName);
			if (line.contains("return ") && braceCounter > 0 && line.contains(methodName)) {
				startAndEndLineNumber.add(lineCounter);
				break;
			}
			if (line.contains("{") && startAndEndLineNumber.size() == 1) {
				braceCounter++;
			} else if (line.contains("}")) {
				braceCounter--;
			}
		}
		System.out.println(startAndEndLineNumber);
		if (startAndEndLineNumber.size() != 2) {
			return null;
		}
		return startAndEndLineNumber;
	}

}
