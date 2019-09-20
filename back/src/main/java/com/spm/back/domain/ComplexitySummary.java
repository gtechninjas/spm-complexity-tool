package com.spm.back.domain;

import java.util.List;

public class ComplexitySummary {

	private int lineNumber;
	private String programStatement;
	private List<String> SizeFactorTokenList;
	private int sizeFactorComplexityVal;
	private String controlTypeFactorComplexityVal;
	private String nestedFactorComplexityVal;
	private int inheritanceFactorComplexityVal;
	private String totalWeightComplexityVal;
	private String programStatmentComplexityVal;
	private String recursionFactorComplexityVal;
	private String complexityOfProgram;
	
	
	public String getComplexityOfProgram() {
		return complexityOfProgram;
	}
	public void setComplexityOfProgram(String complexityOfProgram) {
		this.complexityOfProgram = complexityOfProgram;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getProgramStatement() {
		return programStatement;
	}
	public void setProgramStatement(String programStatement) {
		this.programStatement = programStatement;
	}
	public List<String> getSizeFactorTokenList() {
		return SizeFactorTokenList;
	}
	public void setSizeFactorTokenList(List<String> sizeFactorTokenList) {
		SizeFactorTokenList = sizeFactorTokenList;
	}
	public int getSizeFactorComplexityVal() {
		return sizeFactorComplexityVal;
	}
	public void setSizeFactorComplexityVal(int sizeFactorComplexityVal) {
		this.sizeFactorComplexityVal = sizeFactorComplexityVal;
	}
	public String getControlTypeFactorComplexityVal() {
		return controlTypeFactorComplexityVal;
	}
	public void setControlTypeFactorComplexityVal(String controlTypeFactorComplexityVal) {
		this.controlTypeFactorComplexityVal = controlTypeFactorComplexityVal;
	}
	public String getNestedFactorComplexityVal() {
		return nestedFactorComplexityVal;
	}
	public void setNestedFactorComplexityVal(String nestedFactorComplexityVal) {
		this.nestedFactorComplexityVal = nestedFactorComplexityVal;
	}
	public int getInheritanceFactorComplexityVal() {
		return inheritanceFactorComplexityVal;
	}
	public void setInheritanceFactorComplexityVal(int inheritanceFactorComplexityVal) {
		this.inheritanceFactorComplexityVal = inheritanceFactorComplexityVal;
	}
	public String getTotalWeightComplexityVal() {
		return totalWeightComplexityVal;
	}
	public void setTotalWeightComplexityVal(String totalWeightComplexityVal) {
		this.totalWeightComplexityVal = totalWeightComplexityVal;
	}
	public String getProgramStatmentComplexityVal() {
		return programStatmentComplexityVal;
	}
	public void setProgramStatmentComplexityVal(String programStatmentComplexityVal) {
		this.programStatmentComplexityVal = programStatmentComplexityVal;
	}
	public String getRecursionFactorComplexityVal() {
		return recursionFactorComplexityVal;
	}
	public void setRecursionFactorComplexityVal(String recursionFactorComplexityVal) {
		this.recursionFactorComplexityVal = recursionFactorComplexityVal;
	}
	
	
	
}
