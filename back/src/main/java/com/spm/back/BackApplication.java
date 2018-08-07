package com.spm.back;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.spm.back.service.IFileReaderService;
import com.spm.back.service.impl.ComplexityConstants;
import com.spm.back.service.impl.FileReaderService;

@SpringBootApplication
@ComponentScan("com.spm.back.service") 
public class BackApplication {

	
	
	public static void main(String[] args) {
		String filePath = "C:\\Users\\diaalk\\Desktop\\Test.java";
		File file = new File(filePath);
		IFileReaderService readLine = new FileReaderService();
		try {
			HashMap<String, String> complexityResults = readLine.readAllLines(file);
			System.out.println(complexityResults.get(ComplexityConstants.SIZE_FACTOR_CODE_COMPLEXITY));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
