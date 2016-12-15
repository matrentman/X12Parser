package com.mtlogic.x12;

import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12GETrailer {
	final String name = "GE";
	String ge01;
	String ge02;
	String segmentDelimiter;
	String elementDelimiter;
	String subelementDelimiter;
	
	public X12GETrailer(String data, String segmentDelimiter, String elementDelimiter, String subelementDelimiter) 
			throws InvalidX12MessageException {
		if (data != null && !data.isEmpty() && data.startsWith(X12Message1.GE) 
				&& segmentDelimiter != null && !segmentDelimiter.isEmpty()) {
			this.segmentDelimiter = segmentDelimiter;
			this.elementDelimiter = elementDelimiter;
			this.subelementDelimiter = subelementDelimiter;
			String[] dataArray;
			if (elementDelimiter.equals("*")) {
				dataArray = data.split("\\*");
			} else {
				dataArray = data.split(segmentDelimiter);
			}
			ge01 = dataArray[1];
			ge02 = dataArray[2];
		} else {
			throw new InvalidX12MessageException("Unable to parse GS segment!");
		}
	}

	public String getGe01() {
		return ge01;
	}

	public void setGe01(String ge01) {
		this.ge01 = ge01;
	}

	public String getGe02() {
		return ge02;
	}

	public void setGe02(String ge02) {
		this.ge02 = ge02;
	}

	public String getSegmentDelimiter() {
		return segmentDelimiter;
	}

	public void setSegmentDelimiter(String segmentDelimiter) {
		this.segmentDelimiter = segmentDelimiter;
	}

	public String getElementDelimiter() {
		return elementDelimiter;
	}

	public void setElementDelimiter(String elementDelimiter) {
		this.elementDelimiter = elementDelimiter;
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return this.name + elementDelimiter + this.ge01 + elementDelimiter + this.ge02 
				+ this.segmentDelimiter;
	}
	
	
}
