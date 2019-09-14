package com.spm.back.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spm.back.service.ICalcSizeFactorComplexityService;

@Service
@Transactional
public class CalcSizeFactorComplexityService implements ICalcSizeFactorComplexityService {

	@Autowired
	private ComplexityConstants complexityConstants = new ComplexityConstants();
	

	public CalcSizeFactorComplexityService() {

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
	
	@Override
	public List<Integer> getCalcSizeComplexity(String filePath) throws IOException{
		
		File file = new File(filePath);
		List<Integer> listedSizeComplexities = new ArrayList<Integer>();
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String fileExtension = filePath.substring(filePath.indexOf('.') + 1);
        String fileType = getFileType(filePath);
		String line = null;
		int sizeFctorComplexity = 0;
		while ((line = bufferedReader.readLine()) != null) {

			if (complexityConstants.isNonValueExcludeLine(line)) {
				continue;
			}
			
			sizeFctorComplexity = calculateSizefactorPerLine(line, fileType);
			listedSizeComplexities.add(sizeFctorComplexity);

		}
		return listedSizeComplexities;
		
	}
	
	public int calculateSizefactorPerLine(String line, String type) {
		
		int totalSizeComplexityPerLine = 0;
		totalSizeComplexityPerLine += getQuotationCount(line);
		line = quotationsOmmited(line).trim();
		totalSizeComplexityPerLine += getSizeComplexity_Operator(line, type);
		line = replaceOperators_WithWhiteSpace(line, type).trim();
		totalSizeComplexityPerLine += getSizeComplexity_Keyword(line);
		line = replaceKeywords_WithWhiteSpace(line).trim();
		totalSizeComplexityPerLine += getVariableNameCount(line);
		
		
		return totalSizeComplexityPerLine;
		
	}

	public String quotationsOmmited(String line) {

		Pattern p = Pattern.compile("\"([^\"]*)\"");
		Matcher m = p.matcher(line);
		while (m.find()) {
			line = line.replaceAll(m.group(), " ");
		}

		return line;
	}

	public int getQuotationCount(String line) {

		Pattern p = Pattern.compile("\"([^\"]*)\"");
		Matcher m = p.matcher(line);
		int quotationCounter = 0;
		while (m.find()) {
            
			quotationCounter += m.groupCount();
		}
		System.out.println("LINE "+line);
        System.out.println("QUOTATION COUNT "+quotationCounter);
		return quotationCounter;
	}

	public int getSizeComplexity_Operator(String line, String fileType) {

		int costForOperator_BasedOnLine = 0;

		List<String> singleValuedCollectiveList = new ArrayList<String>();
		List<String> doubleValuedCollectiveList = new ArrayList<String>();
       
		singleValuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.ASSIGNMENT_OPERATORS));
		singleValuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.MISCELLEANEOUS_OPERATORS));		
		singleValuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.RELATIONAL_OPERATORS));
		singleValuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.LOGICAL_OPERATORS));
		singleValuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.BITWISE_OPERATORS));
		singleValuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.ARITHMETIC_OPERATORS));
		
		doubleValuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.REFERENCE));
		if (!fileType.equals(ComplexityConstants.CPP_FILE_TYPE)) {
			doubleValuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.DEREFERENCE));
		}
		Pattern pattern;

		for (String singleValuedOp : singleValuedCollectiveList) {
			String regExp = complexityConstants.convertOpToRegex(singleValuedOp);
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				System.out.println("1OP : "+matcher.group());
				costForOperator_BasedOnLine++;
				
			}
			line = matcher.replaceAll(" ");
		}
		
		
		for (String doubleValuedOp : doubleValuedCollectiveList) {
			String regExp = complexityConstants.convertOpToRegex(doubleValuedOp);
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				System.out.println("2OP : "+matcher.group());
				costForOperator_BasedOnLine += 2;
				
			}
			line = matcher.replaceAll(" ");
		}
		
		return costForOperator_BasedOnLine;
	}

	public String replaceOperators_WithWhiteSpace(String line, String fileType) {

		List<String> collectiveLsit = new ArrayList<String>();
		collectiveLsit.addAll(Arrays.asList(ComplexityConstants.NON_VALUE_EXTRACT_CONCAT_OPERATOR));

		collectiveLsit.addAll(Arrays.asList(ComplexityConstants.ASSIGNMENT_OPERATORS));
		collectiveLsit.addAll(Arrays.asList(ComplexityConstants.MISCELLEANEOUS_OPERATORS));
		collectiveLsit.addAll(Arrays.asList(ComplexityConstants.RELATIONAL_OPERATORS));
		collectiveLsit.addAll(Arrays.asList(ComplexityConstants.LOGICAL_OPERATORS));
		collectiveLsit.addAll(Arrays.asList(ComplexityConstants.BITWISE_OPERATORS));
		collectiveLsit.addAll(Arrays.asList(ComplexityConstants.ARITHMETIC_OPERATORS));
		
		
		
		
		collectiveLsit.addAll(Arrays.asList(ComplexityConstants.REFERENCE));
		if (!fileType.equals(ComplexityConstants.CPP_FILE_TYPE)) {
			collectiveLsit.addAll(Arrays.asList(ComplexityConstants.DEREFERENCE));
		}
		Pattern pattern;
        
		for (String operator : collectiveLsit) {
			String regExp = complexityConstants.convertOpToRegex(operator);
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				line = matcher.replaceAll(" ");
			}
		}
     
		return line;
	}
	
	public int getSizeComplexity_Keyword(String line) {

		int costForKeyword_BasedOnLine = 0;
		String whitespace =  "\\s+";
		
		List<String> singleValuedCollectiveList = new ArrayList<String>();
		singleValuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.KEY_WORDS));
		singleValuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.MANIPULATORS));
		
        List<String> doubleValuedCollectiveList = new ArrayList<String>();
        doubleValuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.SPECIAL_KEYWORDS));
		
		Pattern pattern;

		for (String singleValuedKey : singleValuedCollectiveList) {
			String regExp = whitespace +singleValuedKey+whitespace;
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				
				costForKeyword_BasedOnLine++;				
			}
			line = matcher.replaceAll(" ");
		}
		
		for (String doubleValuedKey : doubleValuedCollectiveList) {
			String regExp = whitespace +doubleValuedKey+whitespace;
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				
				costForKeyword_BasedOnLine += 2;
				
			}
			line = matcher.replaceAll(" ");
		}
	

		return costForKeyword_BasedOnLine;
	}

	public String replaceKeywords_WithWhiteSpace(String line) {
        
		List<String> collectiveLsit = new ArrayList<String>();
		collectiveLsit.addAll(Arrays.asList(ComplexityConstants.KEY_WORDS));
        collectiveLsit.addAll(Arrays.asList(ComplexityConstants.MANIPULATORS));
		collectiveLsit.addAll(Arrays.asList(ComplexityConstants.SPECIAL_KEYWORDS));
		collectiveLsit.addAll(Arrays.asList(ComplexityConstants.NON_VALUE_EXCLUDE_KEYWORD));
		Pattern pattern;

		for (String keyword : collectiveLsit) {
			String regExp = keyword;
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				line = matcher.replaceAll(" ");
			}
		}

		return line;
	}
	
	public int getVariableNameCount(String line) {
		
		int variableNameCounter = 0;
		if(line == null || line.isBlank()) {
			return 0;
		}
		String splittedArr[] = line.trim().split("\\s+");
		variableNameCounter = splittedArr.length;
		
		return variableNameCounter;
	}
}
