package com.mtlogic;

public class X12Envelope {
	X12Segment isaSegment;
	X12Segment gsSegment;
	X12Segment ieaSegment;
	X12Segment geSegment;
	
	public X12Segment getIsaSegment() {
		return isaSegment;
	}
	
	public void setIsaSegment(X12Segment isaSegment) {
		this.isaSegment = isaSegment;
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
}
