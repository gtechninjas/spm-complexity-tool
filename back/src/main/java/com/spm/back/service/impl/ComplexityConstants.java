package com.spm.back.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class ComplexityConstants {

	private static final String MATCH_KEYWORD_IF = "if[(]|if\\s[(]*?";
	private static final String MATCH_KEYWORD_FOR = "for[(]|for\\s[(]*?";
	private static final String MATCH_KEYWORD_WHILE = "while[(]|while\\s[(]*?";
	private static final String MATCH_KEYWORD_CATCH = "catch[(]|catch\\s[(]*?";
	public static final String MATCH_KEYWORD_SWITCH = "switch[(]|switch\\s[(]*?";
	public static final String MATCH_KEYWORD_CASE = "case\\s";
	private static final String MATCH_ELSE = "else.*?";
	private static final String MATCH_RIGHT_CURLY_BRACKET = "[}]";

	public static final String MATCH_NESTING_CONTROL_STRUCTURE = MATCH_KEYWORD_IF + "|" + MATCH_KEYWORD_WHILE + "|"
			+ MATCH_KEYWORD_FOR + "|" + MATCH_ELSE + "|" + MATCH_KEYWORD_SWITCH;

	public static final String MATCH_NESTING_CONTROL_BREAK = MATCH_RIGHT_CURLY_BRACKET;

	public static final String MATCH_NESTING_CONTROL_ELSE = MATCH_ELSE;

	public static final String MATCH_NESTING_SINGLE_LINE_COMMENT = "(?<!http:|https:)\\/\\/.*(?<!\\>)$";
	public static final String MATCH_NESTING_MULTI_LINE_COMMENT_BEGIN = "([/][*])+";
	public static final String MATCH_NESTING_MULTI_LINE_COMMENT_END = "(?:.*)([*][/])+";
	
	public static final String MATCH_CONTROL_STRUCTURE_TYPE_IF = MATCH_KEYWORD_IF;

	public static final String MATCH_CONTROL_STRUCTURE_TYPE_CATCH = MATCH_KEYWORD_CATCH;
	private static final String MATCH_DOUBLE_OR = "[|][|]";
	private static final String MATCH_DOUBLE_AND = "\\&&\\s";
	private static final String MATCH_OR = "[|]";
	private static final String MATCH_AND = "\\&\\s";

	public static final String MATCH_CONTROL_STRUCTURE_TYPE_LOOP = MATCH_KEYWORD_FOR + "|" + MATCH_KEYWORD_WHILE;

	public static final String MATCH_CONTROL_STRUCTURE_TYPE_BREAKS = MATCH_DOUBLE_OR + "|" + MATCH_DOUBLE_AND + "|"
			+ MATCH_OR + "|" + MATCH_AND;
	
	public static String ARITHMETIC_OPERATORS[] = { "++", "--","+", "-", "*", "/", "%","=" };
	public static String RELATIONAL_OPERATORS[] = { "==", "!=", ">=", "<=",">", "<" };
	public static String LOGICAL_OPERATORS[] = { "&&", "||", "!" };
	public static String BITWISE_OPERATORS[] = { "|", "^", "~", "<<", ">>", ">>>", "<<<" };
	public static String MISCELLEANEOUS_OPERATORS[] = { ",", "->", ".", "::" };
	public static String ASSIGNMENT_OPERATORS[] = { "+=", "-=", "*=", "/=", ">>>=", "|=", "&=", "%=", "<<=", ">>=",
			"^=" };
	public static String KEY_WORDS[] = { "void", "double", "int", "long", "float", "string", "String", "printf",
			"println", "cout", "cin", "if", "for", "while", "do-while", "switch", "case" };
	public static String MANIPULATORS[] = { "endl", "\n" };
	public static String SPECIAL_KEYWORDS[] = { "new", "delete", "throws", "throw" };
	public static String REFERENCE = "&";
	public static String DEREFERENCE = "*";
	public static String DATA_TYPES[] = {"boolean","char","byte","byte","int","long","float","double","void"};
	public static String NON_VALUE_EXCLUDE_LINE_KEYWORD[] = { "class", "else", "try", "include", "import", "//" };
	
	public static String NON_VALUE_EXCLUDE_LINE_KEYWORD_CONTROL_TYPE[] = { "class", "try", "include", "import", "//" };
	
	public static String NON_VALUE_EXCLUDE_KEYWORD[] = { "public", "static", "return" };
	public static String NON_VALUE_EXTRACT_CONCAT_OPERATOR[] = { "{", "}", "(", ")", "[", "]", ";", "\"", "\'" };

	public static String SIZE_FACTOR_CODE_COMPLEXITY = "size";
	public static String CONTROL_TYPE_FACTOR_CODE_COMPLEXITY = "control type";

	public static String JAVA_FILE_TYPE = "java";
	public static String CPP_FILE_TYPE = "cpp";
	public static String WHITESPACE_SPLITTER = "\\s+";
	
	public static String CONTROL_TYPE_SINGLE_VALUED[] = {"if", "else"};
	public static String CONTROL_TYPE_BITWISE[] = {"||", "&&", "|", "&"};
	public static String CONTROL_TYPE_DOUBLE_VALUED[] = {"for", "while", "do"};
	
	/**
	 * 
	 * Start of Dilan
	 * 
	 */
	
	public String convertOpToRegex(String Operator) {
		if(Operator == null) {
			return null;
		}
		String convertedRegex = "";
		for(int i = 0;i< Operator.length();i++) {
			convertedRegex += "[\\"+Operator.charAt(i)+ "]";
		}
		return convertedRegex;
	}
	/**
	 * 
	 *End of Dilan
	 * 
	 */

	public Boolean isNonValueExcludeLine(String line) {
		if(line == null || line.isBlank()) {
			return true;
		}
		for (String keyword : NON_VALUE_EXCLUDE_LINE_KEYWORD) {
			if (line.contains(keyword)) {
				return true;
			}
		}
		return false;
	}
	
	public Boolean isNonValueExcludeLine_ControlType(String line) {
		
		for (String keyword : NON_VALUE_EXCLUDE_LINE_KEYWORD_CONTROL_TYPE) {
			if (line.contains(keyword)) {
				return true;
			}
		}
		return false;
	}
	public String extractComments(String line) {
		if(line == null ||line.isBlank()) {
			return null;			
		}
		else {
			String regExp = "\\/\\*[\\s\\S]*?\\*\\/|([^:]|^)\\/\\/.*$";
			Pattern pattern = Pattern.compile(regExp);
			Matcher matcher = pattern.matcher(line);
			line = matcher.replaceAll(" ");
			if(line.trim().isBlank())
				return null;
			
			return line;
		}
	}
	
}
