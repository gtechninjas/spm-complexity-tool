package com.spm.back.service.impl;

import java.util.List;

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

}
