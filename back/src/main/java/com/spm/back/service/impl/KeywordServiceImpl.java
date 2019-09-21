package com.spm.back.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spm.back.domain.KeywordDTO;
import com.spm.back.mapping.Keyword;
import com.spm.back.repository.KeywordRepository;
import com.spm.back.service.KeywordService;

@Service
@Transactional
public class KeywordServiceImpl implements KeywordService {
	@Autowired
	private KeywordRepository keywordRepository;

	@Override
	public KeywordDTO add(Keyword u) {
		return getDTOByKeyword(keywordRepository.save(u));
	}

	@Override
	public List<KeywordDTO> getAll() {
		return keywordRepository.findAll().stream().map(keyword -> getDTOByKeyword(keyword))
				.collect(Collectors.toList());
	}

	@Override
	public KeywordDTO getById(Long id) {
		Optional<Keyword> keyword = keywordRepository.findById(id);
		return (keyword.isPresent()) ? getDTOByKeyword(keyword.get()) : null;
	}

	@Override
	public boolean deleteById(Long id) {
		boolean result = false;
		try {
			keywordRepository.deleteById(id);
			result = true;
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	private KeywordDTO getDTOByKeyword(Keyword keyword) {
		KeywordDTO keywordDTO = new KeywordDTO();
		keywordDTO.setKeywordId(keyword.getKeywordId());
		keywordDTO.setKeword(keyword.getKeyword());
		keywordDTO.setLanguage(keyword.getLanguage());
		keywordDTO.setValue(keyword.getValue());
		keywordDTO.setKeywordType(keyword.getKeywordType());
		return keywordDTO;
	}

}
