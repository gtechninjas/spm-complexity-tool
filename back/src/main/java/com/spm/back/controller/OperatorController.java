package com.spm.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spm.back.domain.OperatorDTO;
import com.spm.back.mapping.Operator;
import com.spm.back.service.CommonService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/operator")
public class OperatorController {
	@Autowired
	CommonService<OperatorDTO, Operator> operatorService;
	
	@GetMapping("")
	public ResponseEntity<?> getAllOperators() {
		ResponseEntity<?> responseEntity = null;
		try {
			responseEntity = new ResponseEntity<>(operatorService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOperatorById(@PathVariable("id") Long id) {
		ResponseEntity<?> responseEntity = null;
		try {
			responseEntity = new ResponseEntity<>(operatorService.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}

	@PostMapping("")
	public ResponseEntity<?> saveOperator(@RequestBody Operator operator) {
		ResponseEntity<?> responseEntity = null;
		try {
			responseEntity = new ResponseEntity<>(operatorService.add(operator), HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOperator(@PathVariable("id") Long id) {
		ResponseEntity<?> responseEntity = null;
		try {
			responseEntity = new ResponseEntity<>(operatorService.deleteById(id), HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}

}
