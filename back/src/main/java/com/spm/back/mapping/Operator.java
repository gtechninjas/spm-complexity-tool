package com.spm.back.mapping;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "operator")
public class Operator implements java.io.Serializable {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -7843128777543947304L;
	private long operatorId;
	private String operator;
	private String operatorType;
	private String language;
	private int value;

	public Operator(long operatorId) {
		super();
		this.operatorId = operatorId;
	}

	public Operator(String operator, String language, int value, String operatorType) {
		super();
		this.operator = operator;
		this.language = language;
		this.value = value;
		this.operatorType = operatorType;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "operator_id", unique = true, nullable = false)
	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

	@Column(name = "operator", nullable = false, length = 50)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Column(name = "language", nullable = false, length = 50)
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name = "value", nullable = false)
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Column(name = "operator_type", nullable = false, length = 50)
	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	
	
	

}
