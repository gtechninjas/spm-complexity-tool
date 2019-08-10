package com.spm.back.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spm.back.domain.OperatorDTO;
import com.spm.back.mapping.Operator;
import com.spm.back.repository.OperatorRepository;
import com.spm.back.service.OperatorService;

@Service
@Transactional
public class OperatorServiceImpl implements OperatorService{
	
	@Autowired
	private OperatorRepository operatorRepository;

	@Override
	public OperatorDTO add(Operator u) {
		return getDTOByOperator(operatorRepository.save(u));
	}

	@Override
	public List<OperatorDTO> getAll() {
		return operatorRepository.findAll().stream().map(operator -> getDTOByOperator(operator))
				.collect(Collectors.toList());
	}

	@Override
	public OperatorDTO getById(Long id) {
		Optional<Operator> operator = operatorRepository.findById(id);
		return (operator.isPresent()) ? getDTOByOperator(operator.get()) : null;
	}

	@Override
	public boolean deleteById(Long id) {
		boolean result = false;
		try {
			operatorRepository.deleteById(id);
			result = true;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	private OperatorDTO getDTOByOperator(Operator operator) {
		
		OperatorDTO operatorDTO = new OperatorDTO();
		operatorDTO.setLanguage(operator.getLanguage());
		operatorDTO.setValue(operator.getValue());
		operatorDTO.setOperatorId(operator.getOperatorId());
		operatorDTO.setOperator(operator.getOperator());
		operatorDTO.setOperatorType(operator.getOperatorType());
	
		
		return operatorDTO;
	}

}
