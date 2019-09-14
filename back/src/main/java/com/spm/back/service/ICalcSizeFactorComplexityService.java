package com.spm.back.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ICalcSizeFactorComplexityService {

	List<Integer> getCalcSizeComplexity(String filePath) throws IOException;
}
