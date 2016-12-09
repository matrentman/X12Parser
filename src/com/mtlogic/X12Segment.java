package com.mtlogic;

public class X12Segment {
	String data;
	
	X12Segment(String str) {
		data = str;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
