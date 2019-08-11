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
			if ( recursionCall > 0 ) {
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
	public int calculateRecurionOccurence(String line) {
		int startIndex = 0;
		int methodIndex = 0;
		Pattern p = Pattern.compile("?<={");
		Matcher m = p.matcher(line);
		while(m.find()) {
			startIndex = m.start();
			methodIndex = startIndex - 1;
		}
		return 0;
	}

}
