package com.spm.back.service;

public interface ICalcSizeFactorComplexityService {

	int complexitySizeFactorValues(String constant, String fileType);
	
	String quotationsOmmited(String line);
	
	int getQuotationCount(String line);
}
