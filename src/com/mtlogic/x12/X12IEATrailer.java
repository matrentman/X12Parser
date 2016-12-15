package com.mtlogic.x12;

import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12IEATrailer {
	final String name = "IEA";
	String iea01;
	String iea02;
	String segmentDelimiter;
	String elementDelimiter;
	String subelementDelimiter;
	
	public X12IEATrailer(String data, String segmentDelimiter, String elementDelimiter, String subelementDelimiter) 
			throws InvalidX12MessageException {
		if (data != null && !data.isEmpty() && data.startsWith(X12Message1.IEA) 
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
			iea01 = dataArray[1];
			iea02 = dataArray[2];
		} else {
			throw new InvalidX12MessageException("Unable to parse IEA segment!");
		}
	}

	public String getIea01() {
		return iea01;
	}

	public void setIea01(String iea01) {
		this.iea01 = iea01;
	}

	public String getIea02() {
		return iea02;
	}

	public void setIea02(String iea02) {
		this.iea02 = iea02;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return this.name + elementDelimiter + this.iea01 + elementDelimiter + this.iea02 
				+ this.segmentDelimiter;
	}

}
