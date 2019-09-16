package com.spm.back.service;

import java.io.IOException;
import java.util.List;

public interface IInheritanceService {

	List<Integer> getCalcInheritanceComplexity(String filePath) throws IOException;

}
