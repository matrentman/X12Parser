package com.mtlogic.x12;

import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12STHeader {
	final String name = "ST";
	String st01;
	String st02;
	String st03;
	String segmentDelimiter;
	String elementDelimiter;
	String subelementDelimiter;
	
	public X12STHeader(String data, String segmentDelimiter, String elementDelimiter, String subelementDelimiter) 
			throws InvalidX12MessageException {
		if (data != null && !data.isEmpty() && data.startsWith(X12Message.ST) 
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
			st01 = dataArray[1];
			st02 = dataArray[2];
			st03 = dataArray[3];
		} else {
			throw new InvalidX12MessageException("Unable to parse GS segment!");
		}
	}
	
	public String getSt01() {
		return st01;
	}
	
	public void setSt01(String st01) {
		this.st01 = st01;
	}
	
	public String getSt02() {
		return st02;
	}
	
	public void setSt02(String st02) {
		this.st02 = st02;
	}
	
	public String getSt03() {
		return st03;
	}
	
	public void setSt03(String st03) {
		this.st03 = st03;
	}
	
	public String getName() {
		return name;
	}
	
	public String print() {
		return toString() + "\n";
	}
	
	public String toString() {
		return this.name + elementDelimiter + this.st01 + elementDelimiter + this.st02 + elementDelimiter + this.st03 + this.segmentDelimiter;
	}
	
}
