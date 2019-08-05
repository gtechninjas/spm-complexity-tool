package com.spm.back.domain;

public class KeywordDTO {
	private long keywordId;
	private String keword;
	private String language;
	private String value;

	public long getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(long keywordId) {
		this.keywordId = keywordId;
	}

	public String getKeword() {
		return keword;
	}

	public void setKeword(String keword) {
		this.keword = keword;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
