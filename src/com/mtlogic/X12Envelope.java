package com.mtlogic;

public class X12Envelope {
	public static int ISA_LENGTH = 126;
	
	private X12Segment isaSegment;
	private X12Segment gsSegment;
	private X12Segment ieaSegment;
	private X12Segment geSegment;
	private String segmentDelimiter;
	
	public boolean validate() {
		boolean isValid = false;
		if (this.validateIsaSegment() && this.validateIeaSegment()  
				&& this.validateGsSegment() && this.validateGeSegment()) {
			isValid = true;
		}
		return isValid;
	}
	
	public boolean validateIsaSegment() {
		boolean isValid = false;
		if (isaSegment != null && isaSegment.toString().length() != ISA_LENGTH) {
			isValid = true;
		}
		return isValid;
	}
	
	public boolean validateIeaSegment() {
		boolean isValid = false;
		if (ieaSegment != null) {
			isValid = true;
		}
		return isValid;
	}
	
	public boolean validateGsSegment() {
		boolean isValid = false;
		if (gsSegment != null) {
			isValid = true;
		}
		return isValid;
	}
	
	public boolean validateGeSegment() {
		boolean isValid = false;
		if (geSegment != null) {
			isValid = true;
		}
		return isValid;
	}
	
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
