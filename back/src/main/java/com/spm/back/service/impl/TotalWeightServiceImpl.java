package com.spm.back.service.impl;

import com.spm.back.service.TotalComplexityService;

public class TotalWeightServiceImpl implements TotalComplexityService{

	@Override
	public int totalComplexityWeight(int controlstructure, int controlnestingstructure, int controlinheritance) {
		return controlstructure + controlnestingstructure + controlinheritance;
	}


	@Override
	public int complexityProgramStatement(String sizeComplexityCost_perLine, String complexityTotalWeight) {
		return Integer.parseInt(sizeComplexityCost_perLine) * Integer.parseInt(complexityTotalWeight);
	}

}
