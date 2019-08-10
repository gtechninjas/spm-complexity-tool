package com.spm.back.service.impl;

import com.spm.back.service.TotalComplexityService;

public class TotalWeightServiceImpl implements TotalComplexityService{

	@Override
	public int totalComplexityWeight(int controlstructure, int controlnestingstructure, int controlinheritance) {
		return controlstructure + controlnestingstructure + controlinheritance;
	}

	@Override
	public int complexityProgramStatement(int controlsize, int totalweight) {
		return controlsize * totalweight;
	}

}
