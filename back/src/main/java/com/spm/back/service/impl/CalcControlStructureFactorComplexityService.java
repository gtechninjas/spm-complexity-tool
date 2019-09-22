package com.spm.back.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spm.back.service.ICalcControlStructureFactorComplexityService;

@Service
@Transactional
public class CalcControlStructureFactorComplexityService implements ICalcControlStructureFactorComplexityService {

	@Autowired
	ComplexityConstants complexityConstants;

	@Override
	public List<String> getCalcControlTypeComplexity(String filePath) throws IOException {

		File file = new File(filePath);
		List<String> listedControlTypeComplexities = new ArrayList<String>();
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String fileExtension = filePath.substring(filePath.indexOf('.') + 1);
		String fileType = getFileType(filePath);
		String line = null;
		String controlTypeFctorComplexity = null;


		while ((line = bufferedReader.readLine()) != null) {
			
			boolean switchFlag = false;
			int switchIndex = 0;
			int index = 0;
			int caseCount = 0;
			int switchReturnVal = 0;
			int count = 0;

			HashMap<Integer, Integer> hmap = new HashMap<Integer, Integer>();

			Pattern patternIf = Pattern.compile(ComplexityConstants.MATCH_CONTROL_STRUCTURE_TYPE_IF);
			Matcher matcherIf = patternIf.matcher(line);

			Pattern patternLoop = Pattern.compile(ComplexityConstants.MATCH_CONTROL_STRUCTURE_TYPE_LOOP);
			Matcher matchLoop = patternLoop.matcher(line);

			Pattern patternCatch = Pattern.compile(ComplexityConstants.MATCH_CONTROL_STRUCTURE_TYPE_CATCH);
			Matcher matchCatch = patternCatch.matcher(line);

			Pattern patternSwitch = Pattern.compile(ComplexityConstants.MATCH_KEYWORD_SWITCH);
			Matcher matchSwitch = patternSwitch.matcher(line);

			Pattern patterBreakPoint = Pattern.compile(ComplexityConstants.MATCH_NESTING_CONTROL_BREAK);
			Matcher matcherBreakPoint = patterBreakPoint.matcher(line);

			Pattern patternCase = Pattern.compile(ComplexityConstants.MATCH_KEYWORD_CASE);
			Matcher matcherCase = patternCase.matcher(line);

			while (matchSwitch.find()) {
				switchFlag = true;
				switchIndex = index;
			}

			while (matcherBreakPoint.find()) {
				System.out.println("break point detected");
				switchFlag = false;
				hmap.put(index, caseCount);
				switchReturnVal = caseCount;
				caseCount = 0;
			}

			while (matcherCase.find()) {
				System.out.println("case detected");
				++caseCount;
			}

			while (matcherIf.find()) {
				count++;
				// System.out.println("found: " + count + " : " + matcherIf.start() + " - " +
				// matcherIf.end());

				String innerKeyword = ComplexityConstants.MATCH_CONTROL_STRUCTURE_TYPE_BREAKS;

				Pattern innerPattern = Pattern.compile(innerKeyword);
				Matcher innerMatcher = innerPattern.matcher(line);

				while (innerMatcher.find()) {
					++count;

				}

			}

			while (matchCatch.find()) {
				count++;
				// System.out.println("found: " + count + " : " + matcherIf.start() + " - " +
				// matcherIf.end());

				String innerKeyword = ComplexityConstants.MATCH_CONTROL_STRUCTURE_TYPE_BREAKS;

				Pattern innerPattern = Pattern.compile(innerKeyword);
				Matcher innerMatcher = innerPattern.matcher(line);

				while (innerMatcher.find()) {
					++count;

				}

			}

			while (matchLoop.find()) {
				count += 2;
				// System.out.println("found: " + count + " : " + matcherIf.start() + " - " +
				// matcherIf.end());

				String innerKeyword = ComplexityConstants.MATCH_CONTROL_STRUCTURE_TYPE_BREAKS;

				Pattern innerPattern = Pattern.compile(innerKeyword);
				Matcher innerMatcher = innerPattern.matcher(line);

				while (innerMatcher.find()) {
					count += 2;

				}

			}
			++index;
			// line.setCtc(switchReturnVal);

			if (switchReturnVal != 0) {
				listedControlTypeComplexities.add(Integer.toString(0));
			} else {
				listedControlTypeComplexities.add(Integer.toString(count));
			}
			System.out.println("CONTROL TYPE "+"      "+listedControlTypeComplexities);

		}

		
		return listedControlTypeComplexities;

	}

	@Override
	public List<String> getCalcControlComplexity_Nested(String filePath) throws IOException {

		File file = new File(filePath);
		List<Integer> listedNestedComplexities = new ArrayList<Integer>();
		List<String> listedNestedComplexities_STRING = new ArrayList<String>();
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String fileExtension = filePath.substring(filePath.indexOf('.') + 1);
		String fileType = getFileType(filePath);
		String line = null;
		int nestedFctorComplexity = 0;
		String lastControlTypeOp, currentControlTypeOp;
		int nestedComplexity = 0;
		int bracesCounter = 0;
		lastControlTypeOp = null;
		currentControlTypeOp = null;
		int nestingLevel = 0;

		int cnc = 0;
		boolean mainFlag = false;
		boolean elseFlag = false;
		boolean singleCommentFlag = false;
		boolean multiCommentFlagBegin = false;
		boolean multiCommentFlagEnd = false;

		while ((line = bufferedReader.readLine()) != null) {
			
			Pattern patternStructure = Pattern.compile(complexityConstants.MATCH_NESTING_CONTROL_STRUCTURE);
			Matcher matcherStructure = patternStructure.matcher(line);

			Pattern patterBreakPoint = Pattern.compile(complexityConstants.MATCH_NESTING_CONTROL_BREAK);
			Matcher matcherBreakPoint = patterBreakPoint.matcher(line);

			Pattern patterElse = Pattern.compile(complexityConstants.MATCH_NESTING_CONTROL_ELSE);
			Matcher matcherElse = patterElse.matcher(line);

			Pattern patternSingleLineComment = Pattern.compile(complexityConstants.MATCH_NESTING_SINGLE_LINE_COMMENT);
			Matcher matcherSingleLineComment = patternSingleLineComment.matcher(line);

			Pattern patternMultiLineCommentBegin = Pattern
					.compile(complexityConstants.MATCH_NESTING_MULTI_LINE_COMMENT_BEGIN);
			Matcher matcherMultiLineCommentBegin = patternMultiLineCommentBegin.matcher(line);

			Pattern patternMultiLineCommentEnd = Pattern
					.compile(complexityConstants.MATCH_NESTING_MULTI_LINE_COMMENT_END);
			Matcher matcherMultiLineCommentEnd = patternMultiLineCommentEnd.matcher(line);

			while (matcherStructure.find()) {
				++cnc;
				mainFlag = true;
			}

			while (matcherBreakPoint.find()) {
				--cnc;
				mainFlag = true;
			}

			while (matcherElse.find()) {
				elseFlag = true;
				mainFlag = true;
			}

			while (matcherSingleLineComment.find()) {

				if (mainFlag) {
					singleCommentFlag = false;
					mainFlag = false;
				} else {
					singleCommentFlag = true;
					mainFlag = false;
				}
			}

			while (matcherMultiLineCommentBegin.find()) {
				multiCommentFlagBegin = true;
			}

			while (matcherMultiLineCommentEnd.find()) {
				multiCommentFlagEnd = true;
			}

			if (cnc < 0) {
				cnc = 0;
			}

			System.out.println("nester line : " + line);
			if (line.isEmpty()) {
				System.out.println("null statement");
				listedNestedComplexities.add(0);
			} else {
				if (elseFlag) {
					listedNestedComplexities.add(0);
					elseFlag = false;
				} else {

					if (singleCommentFlag) {
						listedNestedComplexities.add(0);
						singleCommentFlag = false;
					} else if (multiCommentFlagBegin) {

						if (multiCommentFlagEnd) {
							listedNestedComplexities.add(0);
							multiCommentFlagBegin = false;
							multiCommentFlagEnd = false;
						}

					} else {
						listedNestedComplexities.add(cnc);
					}

				}

			}

		}
		for (int nestedComplexityVal : listedNestedComplexities) {
			listedNestedComplexities_STRING.add(Integer.toString(nestedComplexityVal));
		}
		return listedNestedComplexities_STRING;

	}

	public String getControlTypeComplexityKeyword_Nested(String line) {

		List<String> valuedCollectiveList = new ArrayList<String>();

		valuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.CONTROL_TYPE_SINGLE_VALUED));
		valuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.CONTROL_TYPE_DOUBLE_VALUED));

		Pattern pattern;

		for (String singleValuedOp : valuedCollectiveList) {
			String regExp = complexityConstants.convertOpToRegex(singleValuedOp);
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				System.out.println("1OP : " + matcher.group());
				return matcher.group();

			}

		}

		return null;
	}

	public int getOpenbracesCount(String line) {

		Pattern pattern;
		Matcher matcher;

		String openBraceregExp = "[\\{]";

		pattern = Pattern.compile(openBraceregExp);
		matcher = pattern.matcher(line);

		return matcher.groupCount();

	}

	public int getClosedBracesCount(String line) {

		Pattern pattern;
		Matcher matcher;

		String closedBraceregExp = "[\\{]";

		pattern = Pattern.compile(closedBraceregExp);
		matcher = pattern.matcher(line);
		return matcher.groupCount() * -1;
	}

	public int getControlTypeComplexity_Operator(String line, int valueType) {

		int costForOperator_BasedOnLine = 0;

		List<String> valuedCollectiveList = new ArrayList<String>();

		valuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.CONTROL_TYPE_BITWISE));

		Pattern pattern;

		for (String singleValuedOp : valuedCollectiveList) {
			String regExp = complexityConstants.convertOpToRegex(singleValuedOp);
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {
				System.out.println("1OP : " + matcher.group());
				costForOperator_BasedOnLine += valueType;

			}
			line = matcher.replaceAll(" ");
		}

		return costForOperator_BasedOnLine;
	}

	public HashMap<Integer, Integer> getControlTypeComplexity_Keyword(String line) {

		int costForKeyword_BasedOnLine = 0;
		String whitespace = "\\s+";

		List<String> singleValuedCollectiveList = new ArrayList<String>();
		singleValuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.CONTROL_TYPE_SINGLE_VALUED));

		List<String> doubleValuedCollectiveList = new ArrayList<String>();
		doubleValuedCollectiveList.addAll(Arrays.asList(ComplexityConstants.CONTROL_TYPE_DOUBLE_VALUED));

		HashMap<Integer, Integer> controlTypeHashMap = new HashMap<Integer, Integer>();
		Pattern pattern;

		for (String singleValuedKey : singleValuedCollectiveList) {
			String regExp = whitespace + singleValuedKey + whitespace;
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {

				costForKeyword_BasedOnLine++;
			}
			line = matcher.replaceAll(" ");
		}
		controlTypeHashMap.put(1, costForKeyword_BasedOnLine);

		if (costForKeyword_BasedOnLine > 0) {
			return controlTypeHashMap;
		}

		for (String doubleValuedKey : doubleValuedCollectiveList) {
			String regExp = whitespace + doubleValuedKey + whitespace;
			pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(line);
			while (matcher.find()) {

				costForKeyword_BasedOnLine += 2;

			}
			line = matcher.replaceAll(" ");
		}
		controlTypeHashMap.put(2, costForKeyword_BasedOnLine);

		return controlTypeHashMap;
	}

	public int getControlTypeComplexity(String line) {

		HashMap<Integer, Integer> controlTypeHashMap_Keyword = new HashMap<Integer, Integer>();
		controlTypeHashMap_Keyword = getControlTypeComplexity_Keyword(line);

		Map.Entry<Integer, Integer> entry = controlTypeHashMap_Keyword.entrySet().iterator().next();
		int controlTypeComplexityTotalPerLine = getControlTypeComplexity_Operator(line, entry.getKey());

		return controlTypeComplexityTotalPerLine;
	}

	public String getControlTypeComplexityCostPerLine_BasedOnType(String line) {

		String calculatedControlTypeComplexity;
		if (calculateSwitchControlTypeComplexity_BasedOnType(line) != null) {
			calculatedControlTypeComplexity = getControlTypeComplexity(line) + " "
					+ calculateSwitchControlTypeComplexity_BasedOnType(line).size() + "n";
		} else {
			calculatedControlTypeComplexity = getControlTypeComplexity(line) + " ";
		}
		return calculatedControlTypeComplexity;
	}

	public List<String> calculateSwitchControlTypeComplexity_BasedOnType(String line) {

		List<String> switchList = new ArrayList<String>();
		if (line != null) {

			if (line.contains("swicth")) {
				switchList.add("n");
				return switchList;
			}

		}
		return null;
	}

	public String getFileType(String filePath) {

		String fileExtension = filePath.substring(filePath.indexOf('.') + 1);

		if (fileExtension.equals(ComplexityConstants.JAVA_FILE_TYPE)) {
			return ComplexityConstants.JAVA_FILE_TYPE;
		} else if (fileExtension.equals(ComplexityConstants.CPP_FILE_TYPE)) {
			return ComplexityConstants.CPP_FILE_TYPE;
		} else {
			return null;
		}

	}

}
