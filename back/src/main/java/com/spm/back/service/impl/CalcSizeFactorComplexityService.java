package com.spm.back.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spm.back.service.ICalcSizeFactorComplexityService;

@Service
@Transactional
public class CalcSizeFactorComplexityService implements ICalcSizeFactorComplexityService {

	@Autowired
	private ComplexityConstants complexityConstants;
	

	public CalcSizeFactorComplexityService() {
		
	}

	public int complexityConstantValues(String constant, String fileType) {

		if (complexityConstants.isArithmetic(constant)) {
			return 1;
		} else if (complexityConstants.isRelational(constant)) {
			return 1;
		} else if (complexityConstants.isLogical(constant)) {
			return 1;
		} else if (complexityConstants.isBitwise(constant)) {
			return 1;
		} else if (complexityConstants.isMiscelleaneous(constant)) {
			return 1;
		} else if (complexityConstants.isAssignment(constant)) {
			return 1;
		} else if (complexityConstants.isKeyword(constant)) {
			return 1;
		} else if (complexityConstants.isManipulator(constant)) {
			return 1;
		} else if (complexityConstants.isSpecialKeyword(constant)) {
			return 2;
		} else if (complexityConstants.isRefernce(constant)) {
			return 2;
		} else if (complexityConstants.isDereference(constant) && (!fileType.equals("cpp"))) {
			return 2;
		} else if (complexityConstants.isNonValueExcludeConcatOperator(constant)
				|| complexityConstants.isNonValueExcludeKeywords(constant) || constant.isBlank()) {
			return 0;
		} else {
			return 1;
		}

	}

	public int complexitySizeFactorValues(String word, String fileType) {

		if (word.isEmpty()) {
			return 0;
		}
		List<String> extractedWordArrList = extractVariable(word);

		if (extractedWordArrList == null) {
			return 0;
		}

		int cost = 0;
		for (String extractedWord : extractedWordArrList) {

			cost += complexityConstantValues(extractedWord, fileType);
			System.out.println(extractedWord + " CODE  " + complexityConstantValues(extractedWord, fileType));

		}
		return cost;

	}

	public List<String> extractVariable(String word) {

		List<String> splittedWordArrList = new ArrayList<String>();

		splittedWordArrList.addAll(extractByKeyword(word));

		return splittedWordArrList;
	}

	public List<String> extractByKeyword(String word) {

		String extractedOperator = complexityConstants.extractOperator(word);

		List<String> splittedWordArrList = new ArrayList<String>();

		if (extractedOperator == null) {
			splittedWordArrList.add(word);
		} else {
			for (String splittedWord : word.split("\\" + extractedOperator)) {
				
				if(complexityConstants.extractOperator(splittedWord) != null)
				    return extractByKeyword(splittedWord);
				splittedWordArrList.add(splittedWord);
			}
		}

		return splittedWordArrList;

	}

}
