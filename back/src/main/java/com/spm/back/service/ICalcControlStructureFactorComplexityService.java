package com.spm.back.service;

import java.util.List;

public interface ICalcControlStructureFactorComplexityService {

	int calculateControlTypeComplexityCostPerLine_BasedOnType(String line, List<String> wordList);
	
	String calculateControlTypeComplexityCostPerLine_BasedOnType(String line);

	String totalControlTypeComplexityCostPerLine_BasedOnType(int costsPerLine, List<String> caseList);

}
