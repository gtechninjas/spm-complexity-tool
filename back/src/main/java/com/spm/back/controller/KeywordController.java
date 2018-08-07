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

import com.spm.back.domain.KeywordDTO;
import com.spm.back.mapping.Keyword;
import com.spm.back.service.CommonService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/keyword")
public class KeywordController {

	@Autowired
	CommonService<KeywordDTO, Keyword> keywordService;
	
	@GetMapping("")
	public ResponseEntity<?> getAllKeywords() {
		ResponseEntity<?> responseEntity = null;
		try {
			responseEntity = new ResponseEntity<>(keywordService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getKeywordById(@PathVariable("id") Long id) {
		ResponseEntity<?> responseEntity = null;
		try {
			responseEntity = new ResponseEntity<>(keywordService.getById(id), HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}

	@PostMapping("")
	public ResponseEntity<?> saveKeyword(@RequestBody Keyword keyword) {
		ResponseEntity<?> responseEntity = null;
		try {
			responseEntity = new ResponseEntity<>(keywordService.add(keyword), HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteKeyword(@PathVariable("id") Long id) {
		ResponseEntity<?> responseEntity = null;
		try {
			responseEntity = new ResponseEntity<>(keywordService.deleteById(id), HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
		}
		return responseEntity;
	}

}
