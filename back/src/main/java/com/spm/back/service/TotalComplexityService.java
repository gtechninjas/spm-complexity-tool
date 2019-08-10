package com.spm.back.service;

public interface TotalComplexityService {

	int totalComplexityWeight(int controlstructure , int controlnestingstructure , int controlinheritance);
	
	int complexityProgramStatement( int controlsize , int totalweight);
}
