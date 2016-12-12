package com.mtlogic;

public class X12Envelope {
	X12Segment isaSegment;
	X12Segment gsSegment;
	X12Segment ieaSegment;
	X12Segment geSegment;
	String segmentDelimiter;
	
	public X12Segment getIsaSegment() {
		return isaSegment;
	}
	
	public void setIsaSegment(X12Segment isaSegment) {
		this.isaSegment = isaSegment;
		segmentDelimiter = isaSegment.getElements()[isaSegment.getElements().length-1].substring(isaSegment.getElements()[isaSegment.getElements().length-1].length()-1);
	}
	
	public X12Segment getGsSegment() {
		return gsSegment;
	}
	
	public void setGsSegment(X12Segment gsSegment) {
		this.gsSegment = gsSegment;
	}
	
	public X12Segment getIeaSegment() {
		return ieaSegment;
	}
	
	public void setIeaSegment(X12Segment ieaSegment) {
		this.ieaSegment = ieaSegment;
	}
	
	public X12Segment getGeSegment() {
		return geSegment;
	}
	
	public void setGeSegment(X12Segment geSegment) {
		this.geSegment = geSegment;
	}
	
	public void print() {
		System.out.println(isaSegment + segmentDelimiter);
		System.out.println(gsSegment + segmentDelimiter);
		System.out.println(geSegment + segmentDelimiter);
		System.out.println(ieaSegment + segmentDelimiter);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(isaSegment);
		sb.append(gsSegment);
		sb.append(segmentDelimiter);
		sb.append(geSegment);
		sb.append(segmentDelimiter);
		sb.append(ieaSegment);
		sb.append(segmentDelimiter);
		return sb.toString();
	}

}
