package com.spm.back.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.spm.back.service.RecursionService;

public class RecursionServiceImpl implements RecursionService {

	@Override
	public List<String> getAllMethods(File file) {
		List<String> getMethods = new ArrayList<>();
		try {
			// create class object
			Class classobj = File.class;

			// get list of methods
			Method[] methods = classobj.getMethods();

			// get the name of every method present in the list
			for (Method method : methods) {

				String MethodName = method.getName();
				getMethods.add(MethodName);

			}
			return getMethods;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean checkRecursionPresent(File file) throws FileNotFoundException, IOException {
		List<String> list = new ArrayList<>();
		List<String> methodList = new ArrayList<>();
		int recursionCall = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			list = br.lines().collect(Collectors.toList());
			methodList = getAllMethods(file);
			recursionCall = getDupCount(methodList);
			if (recursionCall > 0) {
				return true;
			} else {
				return false;
			}
		}
	}

	public int getDupCount(List<String> l) {
		int cnt = 0;
		HashSet<String> h = new HashSet<String>(l);

		for (String token : h) {
			if (Collections.frequency(l, token) > 1)
				cnt++;
		}

		return cnt;
	}

	@Override
	public int calculateRecurionOccurence(List<String> line) {
		int index = 0;
		int indexOfMethod = 0;
		List<String> methods = new ArrayList<>();
		for (String word : line) {
			for (String dataType : ComplexityConstants.DATA_TYPES) {
				if (word.contains(dataType)) {
					index = line.indexOf(word);
					indexOfMethod = index + 1;
					methods.add(line.get(indexOfMethod));
				}
			}
		}
		return 0;

	}

	@Override
	public int calculateRecurionMethod(List<String> words) {

		String s = null;

		int isString = 0;
		String input1 = "public";
		String input2 = "void";
		String input3 = "(";
		String input4 = "static";
		String input5 = "int";
		String input6 = "String";
		String input7 = "float";
		int count = 0;
        int tempCount = 0; // to keep local count of matched numbers
        List<String> duplicates = new ArrayList<>();
		int insideMethod = 1;

		String open = "{";
		String closed = "}";
		int bracket = 0;

		String methodName = null;
		List<String> methodList = new ArrayList<>();

		int k = 0;

		for (int i = 0; i < words.size(); i++) {
			for (int j = 0; j < words.get(i).length(); j++) {
				char ch = words.get(i).charAt(j); // Read the word char by char
				if (ch == '"') {
					isString = isString + 1;
				}
			}
		}
		for (String word : words) {
			s = word;
			if (isString % 2 == 0) {
				if (s.contains(input1) && s.contains(input3)
						&& (s.contains(input2) || s.contains(input5) || s.contains(input6) || s.contains(input7))) {
					int l = 2;
					String mName[] = null;

					if (s.contains(input4)) {
						l++;
					}
					methodName = words.get(l);
					// Inserting the method names to the array list
					methodList.add(methodName);
					insideMethod++;
				}

				if (insideMethod > 0) {
					if (s.contains(open)) {
						bracket++;
					}
					if (s.contains(closed)) {
						bracket--;
					}
				}
			}
		}
		
		 for (int i = 1; i < methodList.size(); i++) {
	            if (methodList.get(i) == methodList.get(i - 1)) {
	                if ((tempCount == 0)) { // If same method is repeated more than
	                                        // two times, like TEST, TEST
	                    count = count + 1;
	                    tempCount = tempCount + 1;
	                    duplicates.add(methodList.get(i));
	                }
	            } else {
	                tempCount = 0;
	            }
	        }
		
		return duplicates.size();
	}

}



