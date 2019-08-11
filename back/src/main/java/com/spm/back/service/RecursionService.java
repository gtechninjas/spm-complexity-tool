package com.spm.back.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface RecursionService {
	List<String> getAllMethods(File file);
	Boolean checkRecursionPresent(File file) throws FileNotFoundException, IOException;
	int calculateRecurionOccurence( String line );
}
