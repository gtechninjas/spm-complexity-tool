package com.spm.back.service;

public interface TotalComplexityService {

	int totalComplexityWeight(int controlstructure , int controlnestingstructure , int controlinheritance);
	
	int complexityProgramStatement( String sizeComplexityCost_perLine , String complexityTotalWeight);
}
