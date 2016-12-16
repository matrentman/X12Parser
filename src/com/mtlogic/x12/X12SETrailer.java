package com.mtlogic.x12;

import java.util.Vector;

import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12SETrailer extends X12Base {
	final String name = "SE";
	String se01;
	String se02;
	String segmentDelimiter;
	String elementDelimiter;
	String subelementDelimiter;
	
	public X12SETrailer(String data, String segmentDelimiter, String elementDelimiter, String subelementDelimiter) 
			throws InvalidX12MessageException {
		if (data != null && !data.isEmpty() && data.startsWith(SE) 
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
			se01 = dataArray[1];
			se02 = dataArray[2];
		} else {
			throw new InvalidX12MessageException("Unable to parse GS segment!");
		}
	}
	
	public String getSe01() {
		return se01;
	}
	
	public void setSe01(String se01) {
		this.se01 = se01;
	}
	
	public String getSe02() {
		return se02;
	}
	
	public void setSe02(String se02) {
		this.se02 = se02;
	}
	
	public String getName() {
		return name;
	}
	
	public String print() {
		return toString() + "\n";
	}
	
	public String toString() {
		return this.name + elementDelimiter + this.se01 + elementDelimiter + this.se02 + this.segmentDelimiter;
	}
	
	public Vector<String> validate()  {
		Vector<String> messages = new Vector<String>();
		
		if (se01 == null || se01.isEmpty()) {
			messages.add("Missing field: SE01!");
		}
		if (se02 == null || se02.isEmpty()) {
			messages.add("Missing field: SE02!");
		}
		
		if (se01 != null && (se01.length() < 1 || se01.length() > 10)) {
			messages.add("Invalid field length: SE01!");
		}
		if (se02 != null && (se02.length() < 4 || se02.length() > 9)) {
			messages.add("Invalid field length: SE02!");
		}
		
		return messages;
	}
}
