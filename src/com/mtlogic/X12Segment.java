package com.mtlogic;

import java.util.Arrays;

public class X12Segment {
	String seperator;
	String[] elements;
	
	X12Segment(String data, String seperator) {
		this.seperator = seperator;
		this.setData(data);
	}

	public String getData() {
		return Arrays.toString(elements);
	}

	public void setData(String data) {
		if (seperator.equals("*")) {
			elements = data.split("\\*");
		} else {
			elements = data.split(seperator);
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String element : elements) {
			sb.append(element);
			sb.append(seperator);
		}
		return sb.toString();
	}
	
}
