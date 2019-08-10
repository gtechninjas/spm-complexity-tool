package com.spm.back.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public interface IFileReaderService {

	public HashMap<String, Integer> readAllLines(File f) throws IOException;
	
}
