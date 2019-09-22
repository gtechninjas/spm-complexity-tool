package com.spm.back.domain;

public class Reader {
	public String fileLocation;
	
	public Reader() {}

	public Reader(String fileLocation) {
		super();
		this.fileLocation = fileLocation;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	
}
