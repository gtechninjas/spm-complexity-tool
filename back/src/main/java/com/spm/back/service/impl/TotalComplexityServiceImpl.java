package com.spm.back.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spm.back.domain.ComplexitySummary;
import com.spm.back.service.ITotalComplexityService;

@Service
@Transactional
public class TotalComplexityServiceImpl implements ITotalComplexityService{
	
	@Autowired
	private CalcControlStructureFactorComplexityService calcControlStructureFactorComplexityService; 
	
	@Autowired
	private CalcSizeFactorComplexityService calcSizeFactorComplexityService;
	
	@Autowired
	private InheritanceServiceImpl inheritanceServiceImpl;
	
	@Autowired
	private RecursionServiceImpl recursionServiceImpl;
	
	
	public ArrayList<ComplexitySummary> getComplexitySummary(String filePath) throws IOException{
		
		ArrayList<ComplexitySummary> complexitySummaryList = new ArrayList<ComplexitySummary>();
		
		ArrayList<String> programStatementList = getProgramStatementList(filePath);
		
		int getTotalProgramStatmentCounter = getTotalLineCounter(filePath);
		
		ArrayList<ArrayList<String>> identifiedTokenList = (ArrayList<ArrayList<String>>) calcSizeFactorComplexityService.getSizefactorTokenList(filePath);
		
		ArrayList<Integer> sizeFactorList_Cs = (ArrayList<Integer>) calcSizeFactorComplexityService.getCalcSizeComplexity(filePath);
		
		ArrayList<String> controlTypeFactorList_Ctc = (ArrayList<String>) calcControlStructureFactorComplexityService.getCalcControlTypeComplexity(filePath);
		
		ArrayList<String> nestedFactorList_Cnc = (ArrayList<String>) calcControlStructureFactorComplexityService.getCalcControlComplexity_Nested(filePath);
		
		ArrayList<Integer> inheritanceFactorList_Ci = (ArrayList<Integer>) inheritanceServiceImpl.getCalcInheritanceComplexity(filePath);
		
		ArrayList<String> totalWeightList_Tw = (ArrayList<String>) recursionServiceImpl.getTotalWeightList(controlTypeFactorList_Ctc, nestedFactorList_Cnc, inheritanceFactorList_Ci, getTotalProgramStatmentCounter);

		ArrayList<String> complexityProgramStatementList_Cps = (ArrayList<String>) recursionServiceImpl.getComplexityProgramConstant(totalWeightList_Tw, sizeFactorList_Cs, getTotalProgramStatmentCounter);
		
		ArrayList<String>recursionList_Cr = (ArrayList<String>) recursionServiceImpl.getCalcRecursionComplexity(filePath);
		
		String totalProgramComplexity = null;
		System.out.println("PROGRAM STATEMENT LIST "+identifiedTokenList);
		for(int i = 0;i<getTotalProgramStatmentCounter;i++) {
			ComplexitySummary complexitySummary = new ComplexitySummary();
			complexitySummary.setLineNumber(i+1);
			complexitySummary.setProgramStatement(programStatementList.get(i));
			complexitySummary.setSizeFactorTokenList(identifiedTokenList.get(i));
			complexitySummary.setSizeFactorComplexityVal(sizeFactorList_Cs.get(i));
			complexitySummary.setControlTypeFactorComplexityVal(controlTypeFactorList_Ctc.get(i));
			complexitySummary.setNestedFactorComplexityVal(nestedFactorList_Cnc.get(i));
			complexitySummary.setInheritanceFactorComplexityVal(inheritanceFactorList_Ci.get(i));
			complexitySummary.setTotalWeightComplexityVal(totalWeightList_Tw.get(i));
			complexitySummary.setProgramStatmentComplexityVal(complexityProgramStatementList_Cps.get(i));
			complexitySummary.setRecursionFactorComplexityVal(recursionList_Cr.get(i));
			totalProgramComplexity = getTotalProgramComplexity(recursionList_Cr, complexityProgramStatementList_Cps, getTotalProgramStatmentCounter);
			complexitySummary.setComplexityOfProgram(totalProgramComplexity);
			complexitySummaryList.add(complexitySummary);
		}
		for(ComplexitySummary com: complexitySummaryList) {
			System.out.println(com);
		}
		return complexitySummaryList;
		
		
	}
	private static boolean TryParse(String input) {
		try {
			Integer.parseInt(input.trim());		
			return true;
		}
		catch (NumberFormatException e) {
			return false; 
		}
	}
	
	private String getTotalProgramComplexity(List<String> recursionList, List<String> complexityProgramStatmentList, int linecounter) {
		
		int totalProgramComplexityVal_INT = 0;
		String totalProgramComplexityVal_STRING = "";
		
		for(int i = 0;i<linecounter;i++) {

			int complexityProgramStatmentVal = 0;
			int recursionVal = 0;
			boolean isIntRecusrion = false;
			boolean isIntComplexityProgramStatment = false;

			if(recursionList.get(i) == null || recursionList.get(i) == "" ||TryParse(recursionList.get(i))) {
				if(recursionList.get(i) == null|| recursionList.get(i) == "") {					
					recursionVal = 0;
				}
				else {
					recursionVal = Integer.parseInt(recursionList.get(i).trim());
				}
				isIntRecusrion = true;
			}
			
			if(complexityProgramStatmentList.get(i) == null || complexityProgramStatmentList.get(i) == "" ||TryParse(complexityProgramStatmentList.get(i))) {
				if(complexityProgramStatmentList.get(i) == null|| complexityProgramStatmentList.get(i) == "") {					
					complexityProgramStatmentVal = 0;
				}
				else {
					complexityProgramStatmentVal = Integer.parseInt(complexityProgramStatmentList.get(i).trim());
				}
				isIntComplexityProgramStatment = true;
			}
			if(isIntRecusrion == true && recursionVal == 0) {
				if(isIntComplexityProgramStatment) {
					
					totalProgramComplexityVal_INT += complexityProgramStatmentVal;
				}
				else {
					totalProgramComplexityVal_STRING = totalProgramComplexityVal_STRING +" + "+ complexityProgramStatmentList.get(i).trim();
				}
			}
			else {
				if(isIntRecusrion) {
					totalProgramComplexityVal_INT += recursionVal;
				}
				else {
					totalProgramComplexityVal_STRING = totalProgramComplexityVal_STRING +" + "+ recursionList.get(i).trim();
				}
			}
			
			
		}
		totalProgramComplexityVal_STRING = totalProgramComplexityVal_STRING + totalProgramComplexityVal_INT;
		
		return totalProgramComplexityVal_STRING;
	}
	
	private int getTotalLineCounter(String filePath) throws IOException {
		
		File file = new File(filePath);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		int lineCounter = 0;
		while ((line = bufferedReader.readLine()) != null) {
			lineCounter++;
		}
		
		return lineCounter;
		
	}
	
    private ArrayList<String> getProgramStatementList(String filePath) throws IOException {
		
		File file = new File(filePath);
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		int lineCounter = 0;
		ArrayList<String> getProgramStatementList = new ArrayList<String>();
		while ((line = bufferedReader.readLine()) != null) {
			getProgramStatementList.add(line);
		}
		
		return getProgramStatementList;
		
	}

}
