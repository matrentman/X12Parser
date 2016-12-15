package com.mtlogic.x12;

import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12GSHeader {
	final String name = "GS";
	String gs01;
	String gs02;
	String gs03;
	String gs04;
	String gs05;
	String gs06;
	String gs07;
	String gs08;
	String segmentDelimiter;
	String elementDelimiter;
	String subelementDelimiter;
	
	public X12GSHeader(String data, String segmentDelimiter, String elementDelimiter, String subelementDelimiter) 
			throws InvalidX12MessageException {
		if (data != null && !data.isEmpty() && data.startsWith(X12Message.GS) 
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
			gs01 = dataArray[1];
			gs02 = dataArray[2];
			gs03 = dataArray[3];
			gs04 = dataArray[4];
			gs05 = dataArray[5];
			gs06 = dataArray[6];
			gs07 = dataArray[7];
			gs08 = dataArray[8];
		} else {
			throw new InvalidX12MessageException("Unable to parse GS segment!");
		}
	}
	
	public String getGs01() {
		return gs01;
	}
	
	public void setGs01(String gs01) {
		this.gs01 = gs01;
	}
	
	public String getGs02() {
		return gs02;
	}
	
	public void setGs02(String gs02) {
		this.gs02 = gs02;
	}
	
	public String getGs03() {
		return gs03;
	}
	
	public void setGs03(String gs03) {
		this.gs03 = gs03;
	}
	
	public String getGs04() {
		return gs04;
	}
	
	public void setGs04(String gs04) {
		this.gs04 = gs04;
	}
	
	public String getGs05() {
		return gs05;
	}
	
	public void setGs05(String gs05) {
		this.gs05 = gs05;
	}
	
	public String getGs06() {
		return gs06;
	}
	
	public void setGs06(String gs06) {
		this.gs06 = gs06;
	}
	
	public String getGs07() {
		return gs07;
	}
	
	public void setGs07(String gs07) {
		this.gs07 = gs07;
	}
	
	public String getGs08() {
		return gs08;
	}
	
	public void setGs08(String gs08) {
		this.gs08 = gs08;
	}
	
	public String getName() {
		return name;
	}
	
	public String print() {
		return toString() + "\n";
	}
	
	public String toString() {
		return this.name + elementDelimiter + this.gs01 + elementDelimiter + this.gs02 + elementDelimiter 
				+ this.gs03 + elementDelimiter + this.gs04 + elementDelimiter + this.gs05 
				+ elementDelimiter + this.gs06 + elementDelimiter + this.gs07 + elementDelimiter 
				+ this.gs08 + this.segmentDelimiter;
	}
	
}
