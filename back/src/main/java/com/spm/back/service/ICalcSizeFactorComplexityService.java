package com.spm.back.service;

public interface ICalcSizeFactorComplexityService {

	
	String quotationsOmmited(String line);
	
	int getQuotationCount(String line);

	int calculateSizefactorPerLine(String line, String type);
}
