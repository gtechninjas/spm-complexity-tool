package com.spm.back.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface RecursionService {
	List<String> getCalcRecursionComplexity(String filePath) throws IOException;
}
