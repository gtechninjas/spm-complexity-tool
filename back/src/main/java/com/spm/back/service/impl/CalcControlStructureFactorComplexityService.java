package com.spm.back.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spm.back.service.ICalcControlStructureFactorComplexityService;
@Service
@Transactional
public class CalcControlStructureFactorComplexityService implements ICalcControlStructureFactorComplexityService{

	@Autowired
	ComplexityConstants complexityConstants;

	@Override
	public int calculateControlTypeComplexityCostPerLine_BasedOnType(String line, List<String> wordList) {
		
		int costControlTypeBasedOnType = 0;
		if(line != null) {
			
			if(complexityConstants.getSingleValuedControlType(line) != null) {
				costControlTypeBasedOnType = 1;
				
				for(String word: wordList) {
					costControlTypeBasedOnType += complexityConstants.getBitwiseBasedValue(line, word);
				}
			}
			else if(complexityConstants.getDoubleValuedControlType(line) != null) {
				costControlTypeBasedOnType = 2;
			}
			
		}
		return costControlTypeBasedOnType;
	}


	public int claculateNestedControlComplexityCostPerLine(String line) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String totalControlTypeComplexityCostPerLine_BasedOnType(int costsPerLine, List<String> caseList) {
		
		String totalCost = ""+costsPerLine;
		if(caseList != null) {
			totalCost = caseList.size()+("n + ") + totalCost;
			return totalCost;
		}
		
		return totalCost;
	}


	@Override
	public String calculateControlTypeComplexityCostPerLine_BasedOnType(String line) {
		
		if(line != null) {
			
			if(line.contains("swicth")) {
				return "n";
			}
			
		}
		return null;
	}
	
	@Override
	public int claculateNestedControlComplexityCostPerLine(List<String> wordList, HashMap<String, Integer> bracesCounter_controlTypeOperatorMap,HashMap<String, Integer> value_controlTypeOperatorMap ){
		
		int complexityCost = 0;
		
		for(Map.Entry<String, Integer> entry : bracesCounter_controlTypeOperatorMap.entrySet()) {
		    String controlTypeOperator = entry.getKey();
		    int bracesCounter = entry.getValue();

		    if(bracesCounter > 0) {
		    	complexityCost += value_controlTypeOperatorMap.get(controlTypeOperator);
		    }
		}
		
		return complexityCost;
	}
	
	@Override
	public HashMap<String, Integer> mapBracesCounterWithControlStructures(List<String> wordList, HashMap<String, Integer> bracesCounter_controlTypeOperatorMap) {
		
		String controlTypeOperator = null;
		int counter = 0;
		
		for(String word : wordList) {
			controlTypeOperator = complexityConstants.getControlTypeOperator(word);
			if(controlTypeOperator == null) {
				continue;
			}
			if(word.contains("{")) {
				bracesCounter_controlTypeOperatorMap.put(controlTypeOperator, ++counter);
			}
			if(word.contains("}")) {
				bracesCounter_controlTypeOperatorMap.put(controlTypeOperator, --counter);
			}
			System.out.println("MAP_BRACES_COUNTER_WITH_CONTROL_STRUCTURE "+bracesCounter_controlTypeOperatorMap);
		}
		
		return bracesCounter_controlTypeOperatorMap;
	}


	@Override
	public HashMap<String, Integer> mapValuesWithControlStructures(List<String> wordList, HashMap<String, Integer> value_controlTypeOperatorMap) {
		String controlTypeOperator = null;
		int counter = 0;
		
		for(String word : wordList) {
			controlTypeOperator = complexityConstants.getControlTypeOperator(word);
			if(controlTypeOperator == null) {
				continue;
			}
			value_controlTypeOperatorMap.put(controlTypeOperator, ++counter);
		}
		
		return value_controlTypeOperatorMap;
	}
	


}
