package com.spm.back.service;

import java.util.HashMap;
import java.util.List;

public interface ICalcControlStructureFactorComplexityService {

	String totalControlTypeComplexityCostPerLine_BasedOnType(int costsPerLine, List<String> caseList);

	HashMap<String, Integer> mapBracesCounterWithControlStructures(List<String> wordList,HashMap<String, Integer> bracesCounter_controlTypeOperatorMap);

	int claculateNestedControlComplexityCostPerLine(List<String> wordList,
			HashMap<String, Integer> bracesCounter_controlTypeOperatorMap,
			HashMap<String, Integer> value_controlTypeOperatorMap);

	HashMap<String, Integer> mapValuesWithControlStructures(List<String> wordList,
			HashMap<String, Integer> value_controlTypeOperatorMap,
			HashMap<String, Integer> bracesCounter_controlTypeOperatorMap);

	int calculateControlTypeComplexityCostPerLine_BasedOnType(String line, List<String> wordList);

	String calculateControlTypeComplexityCostPerLine_BasedOnType(String line);


}
