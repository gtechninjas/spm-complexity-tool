package com.spm.back.mapping;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Keyword")
public class Keyword implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1749776699311779530L;
	private long keywordId;
	private String keyword;
	private String language;
	private String value;
	private String keywordType;

	public Keyword() {
	}

	public Keyword(long keywordId) {
		super();
		this.keywordId = keywordId;
	}
	
	public Keyword(String keyword, String language, String value, String keywordType) {
		super();
		this.keyword = keyword;
		this.language = language;
		this.value = value;
		this.keywordType = keywordType;
	}

	@Column(name = "keywordtype", nullable = false, length = 50)
	public String getKeywordType() {
		return keywordType;
	}

	public void setKeywordType(String keywordType) {
		this.keywordType = keywordType;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "keyword_id", unique = true, nullable = false)
	public long getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(long keywordId) {
		this.keywordId = keywordId;
	}

	@Column(name = "keyword", nullable = false, length = 50)
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Column(name = "language", nullable = false, length = 50)
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
