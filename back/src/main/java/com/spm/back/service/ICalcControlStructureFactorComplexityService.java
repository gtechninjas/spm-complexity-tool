package com.spm.back.service;

import java.io.IOException;
import java.util.List;

public interface ICalcControlStructureFactorComplexityService {

	List<String> getCalcControlTypeComplexity(String filePath) throws IOException;

	List<String> getCalcControlComplexity_Nested(String filePath) throws IOException;

}
