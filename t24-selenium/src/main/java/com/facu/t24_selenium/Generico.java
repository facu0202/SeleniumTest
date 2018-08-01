package com.facu.t24_selenium;

import java.util.Map;

import com.globallogic.core.T24Selenium;

public class Generico extends T24Selenium {

	
	private String enqueryName;
	
	public String getEnqueryName() {
		return enqueryName;
	}

	public void setEnqueryName(String enqueryName) {
		this.enqueryName = enqueryName;
	}

	public Map<String,String> runT24Example(String txNumber) {
		super.execute(getEnqueryName(), txNumber);
		return super.findNQuiryFirstResponseValue();
	}

	public Generico(String enqueryName) {
		super();
		this.enqueryName = enqueryName;
	}
	
	

}
