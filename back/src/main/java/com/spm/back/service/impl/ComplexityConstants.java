package com.spm.back.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class ComplexityConstants {

	public static String ARITHMETIC_OPERATORS[] = { "+", "-", "*", "/", "%", "++", "--" };
	public static String RELATIONAL_OPERATORS[] = { "==", "!=", ">", "<", ">=", "<=" };
	public static String LOGICAL_OPERATORS[] = { "&&", "||", "!" };
	public static String BITWISE_OPERATORS[] = { "|", "^", "~", "<<", ">>", ">>>", "<<<" };
	public static String MISCELLEANEOUS_OPERATORS[] = { ",", "->", ".", "::" };
	public static String ASSIGNMENT_OPERATORS[] = { "+=", "-=", "*=", "/=", "=", ">>>=", "|=", "&=", "%=", "<<=", ">>=",
			"^=" };
	public static String KEY_WORDS[] = { "void", "double", "int", "long", "float", "string", "String", "printf",
			"println", "cout", "cin", "if", "for", "while", "do-while", "switch", "case" };
	public static String MANIPULATORS[] = { "endl", "\n" };
	public static String SPECIAL_KEYWORDS[] = { "new", "delete", "throws", "throw" };
	public static String REFERENCE = "&";
	public static String DEREFERENCE = "*";

	public static String NON_VALUE_EXCLUDE_LINE_KEYWORD[] = { "class", "else", "try", "include", "import", "//" };
	public static String NON_VALUE_EXCLUDE_KEYWORD[] = { "public", "static", "return" };
	public static String NON_VALUE_EXTRACT_CONCAT_OPERATOR[] = { "{", "}", "(", ")", "[", "]", ";", "\"", "\'" };

	public static String SIZE_FACTOR_CODE_COMPLEXITY = "size";
	public static String TOTAL_WEIGHT_COMPLEXITY = "TW";
	public static String COMPLEXITY_PROGRAM_STATEMENT = "Cps";

	public static String JAVA_FILE_TYPE = "java";
	public static String CPP_FILE_TYPE = "cpp";
	public static String WHITESPACE_SPLITTER = "\\s+";

	public Boolean isNonValueExcludeLine(String line) {
		for (String keyword : NON_VALUE_EXCLUDE_LINE_KEYWORD) {
			if (line.contains(keyword)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isNonValueExcludeKeywords(String word) {
		for (String keyword : NON_VALUE_EXCLUDE_KEYWORD) {
			if (word.equals(keyword)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isNonValueExcludeConcatOperator(String word) {
		for (String keyword : NON_VALUE_EXTRACT_CONCAT_OPERATOR) {
			if (word.equals(keyword)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isArithmetic(String constant) {
		for (String operator : ARITHMETIC_OPERATORS) {
			if (operator.equals(constant)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isRelational(String constant) {
		for (String operator : RELATIONAL_OPERATORS) {
			if (operator.equals(constant)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isLogical(String constant) {
		for (String operator : LOGICAL_OPERATORS) {
			if (operator.equals(constant)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isBitwise(String constant) {
		for (String operator : BITWISE_OPERATORS) {
			if (operator.equals(constant)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isMiscelleaneous(String constant) {
		for (String operator : MISCELLEANEOUS_OPERATORS) {
			if (operator.equals(constant)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isAssignment(String constant) {
		for (String operator : ASSIGNMENT_OPERATORS) {
			if (operator.equals(constant)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isKeyword(String constant) {
		for (String operator : KEY_WORDS) {
			if (operator.equals(constant)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isManipulator(String constant) {
		for (String operator : MANIPULATORS) {
			if (operator.equals(constant)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isSpecialKeyword(String constant) {
		for (String operator : SPECIAL_KEYWORDS) {
			if (operator.equals(constant)) {
				return true;
			}
		}
		return false;
	}

	public Boolean isRefernce(String constant) {
		if (constant.contains(REFERENCE)) {
			return true;
		}
		return false;
	}

	public Boolean isDereference(String constant) {
		if (constant.contains(DEREFERENCE)) {
			return true;
		}
		return false;
	}

	public String extractOperator(String word, String fileType) {

		List<String> collectiveList = new ArrayList<String>();
		collectiveList.addAll(Arrays.asList(NON_VALUE_EXTRACT_CONCAT_OPERATOR));
		collectiveList.addAll(Arrays.asList(MANIPULATORS));

		collectiveList.addAll(Arrays.asList(ARITHMETIC_OPERATORS));
		collectiveList.addAll(Arrays.asList(RELATIONAL_OPERATORS));
		collectiveList.addAll(Arrays.asList(LOGICAL_OPERATORS));
		collectiveList.addAll(Arrays.asList(BITWISE_OPERATORS));
		collectiveList.addAll(Arrays.asList(MISCELLEANEOUS_OPERATORS));
		collectiveList.addAll(Arrays.asList(ASSIGNMENT_OPERATORS));
		collectiveList.addAll(Arrays.asList(REFERENCE));
		if (!fileType.equals(CPP_FILE_TYPE)) {
			collectiveList.addAll(Arrays.asList(DEREFERENCE));
		}

		if (word == null) {
			return null;
		}
		for (String operator : collectiveList) {
			if (word.contains(operator)) {
				return operator;
			}
		}
		return null;
	}

}
