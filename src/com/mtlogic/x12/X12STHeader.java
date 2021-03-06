package com.mtlogic.x12;

import java.util.Vector;

import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12STHeader extends X12Base {
	final String name = "ST";
	String st01;
	String st02;
	String st03;
	String segmentDelimiter;
	String elementDelimiter;
	String subelementDelimiter;
	
	public X12STHeader(String data, String segmentDelimiter, String elementDelimiter, String subelementDelimiter, String version) 
			throws InvalidX12MessageException {
		if (data != null && !data.isEmpty() && data.startsWith(ST) 
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
			if (version.equals(ANSI_5010)) {
			    st03 = dataArray[3];
			}
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
	
	public String getSetIdentifierCode() {
		return st01;
	}
	
	public void setSetIdentifierCode(String code) {
		this.st01 = code;
	}
	
	public String print() {
		return toString() + "\n";
	}
	
	public String toString() {
		return this.name + elementDelimiter + this.st01 + elementDelimiter + this.st02 + elementDelimiter + this.st03 + this.segmentDelimiter;
	}
	
	public String toJSONString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("\"");
		sb.append(this.name);
		sb.append("\":{\"");
		sb.append(X12Base.ST_TSIC);
		sb.append("\":\"");
		sb.append(this.st01);
		sb.append("\",\"");
		sb.append(X12Base.ST_TSCN);
		sb.append("\":\"");
		sb.append(this.st02);
		sb.append("\",\"");
		sb.append(X12Base.ST_ICR);
		sb.append("\":\"");
		sb.append(this.st03);
		sb.append("\"},");
		
		return sb.toString();
	}
	
	public Vector<String> validate(String version)  {
		Vector<String> messages = new Vector<String>();
		
		if (st01 == null || st01.isEmpty()) {
			messages.add("Missing field: ST01!");
		}
		if (st02 == null || st02.isEmpty()) {
			messages.add("Missing field: ST02!");
		}
		if (version.equals(ANSI_5010) && (st03 == null || st03.isEmpty())) {
			messages.add("Missing field: ST03!");
		}
		
		if (st01 != null && st01.length() != 3) {
			messages.add("Invalid field length: ST01!");
		}
		if (st02 != null && (st02.length() < 4 || st02.length() > 9)) {
			messages.add("Invalid field length: ST02!");
		}
		if (st03 != null && (st03.length() < 1 || st03.length() > 35)) {
			messages.add("Invalid field length: ST03!");
		}
		
		return messages;
	}
}
