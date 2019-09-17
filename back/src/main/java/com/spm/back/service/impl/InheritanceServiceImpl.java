package com.spm.back.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spm.back.service.IInheritanceService;
@Service
@Transactional
public class InheritanceServiceImpl implements IInheritanceService{
	
	@Autowired
	private ComplexityConstants complexityConstants = new ComplexityConstants();
	
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
	
	@Override
	public List<Integer> getCalcInheritanceComplexity(String filePath) throws IOException{
		
		File file = new File(filePath);
		List<Integer> listedInheritanceComplexities = new ArrayList<Integer>();
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		int sizeFctorComplexity = 0;
		while ((line = bufferedReader.readLine()) != null) {

			if (complexityConstants.isNonValueExcludeLine(line)) {
				continue;
			}
			
			sizeFctorComplexity = showResourceData(filePath);
			listedInheritanceComplexities.add(sizeFctorComplexity);

		}
		return listedInheritanceComplexities;
		
	}

	public int showResourceData(String filePath) throws IOException {

		File file = new File(filePath);
		List<String> ancestorClasses = new ArrayList<>();

		int complexity = 0;
		
		int ancClasses = 0;

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);

			String filePaths = file.getPath();
			String fileExtension = filePath.substring(filePath.indexOf('.') + 1);
			System.out.println(fileExtension);
			System.out.println(filePaths);

			switch (fileExtension) {
			case "java":
				ancestorClasses = this.processJavaCode(reader, ancestorClasses);
				ancClasses = ancClasses + ancestorClasses.size();

				if (!ancestorClasses.isEmpty()) {

					String classFolderPath = filePath.substring(0, filePath.lastIndexOf("\\") + 1);

					for (String ancestorClass : ancestorClasses) {

						String classPath = classFolderPath + ancestorClass + ".java";

						showResourceData(classPath);

					}
				}

				complexity = ancClasses;
				complexity++;

			case "cp":
				ancestorClasses = this.processCplusCode(reader, ancestorClasses);
				ancClasses = ancClasses + ancestorClasses.size();
				
				if (!ancestorClasses.isEmpty()) {
					
					String classFolderPath = filePath.substring(0, filePath.lastIndexOf("\\") + 1);

					for (String ancestorClass : ancestorClasses) {
						
						String classPath = classFolderPath + ancestorClass + ".java";

						showResourceData(classPath);


					}
				}
				complexity = ancClasses;
				complexity++;
			
			}

		} catch (IOException e) {
			e.printStackTrace();
			complexity = 0;
		}
		return complexity;

	}

	private List<String> processJavaCode(BufferedReader reader, List<String> ancestorClasses) throws IOException {

		String line;
		List<String> wordArrayList;
		int numberOfLines = 0;
		int ancestorClassCount = 0;

		while (true) {
			line = reader.readLine();
			if (line == null)
				break;
			wordArrayList = Arrays.asList(line.split("\\W+"));

			wordArrayList.replaceAll(String::trim);

			for (String word : wordArrayList) {
				if (word.equals("extends")) {

					int index = wordArrayList.indexOf("extends");

					ancestorClasses.add(wordArrayList.get(index + 1));

					ancestorClassCount++;
				} else if (word.equals("implements")) {

					int index = wordArrayList.indexOf("implements");

					ancestorClasses.add(wordArrayList.get(index + 1));

					ancestorClassCount++;

				}
			}

			numberOfLines++;

		}

		return ancestorClasses;
	}

	private List<String> processCplusCode(BufferedReader reader, List<String> ancestorClasses) throws IOException {

		String line;
		List<String> wordArrayList;
		List<String> listWithDuplicates = new ArrayList<>();

		int numberOfLines = 0;
		int ancestorClassCount = 0;

		while (true) {
			line = reader.readLine();
			if (line == null)
				break;

			if (line.contains(":")) {
				String x = line.substring(line.indexOf(":"));

				wordArrayList = Arrays.asList(x.split("\\W+"));

				for (int i = 0; i < wordArrayList.size(); i++) {
					if (wordArrayList.get(i).trim().equals("public")) {

						listWithDuplicates.add(wordArrayList.get(i + 1));

					}
				}

			}

			numberOfLines++;

		}

		return listWithDuplicates.stream().distinct().collect(Collectors.toList());
	}

}
