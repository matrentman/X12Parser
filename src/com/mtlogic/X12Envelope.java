package com.mtlogic;

public class X12Envelope {	
	private X12Segment startingSegment;
	private X12Segment endingSegment;
	private String segmentDelimiter;
	
	public boolean validate() {
		boolean isValid = false;
		if (startingSegment !=null && endingSegment != null) {
			isValid = true;
		}
		return isValid;
	}
	
	public X12Segment getStartingSegment() {
		return startingSegment;
	}
	
	public void setStartingSegment(X12Segment segment) {
		this.startingSegment = segment;
	}
	
	public X12Segment getEndingSegment() {
		return endingSegment;
	}
	
	public void setEndingSegment(X12Segment segment) {
		this.endingSegment = segment;
	}
	
	public String getSegmentDelimiter() {
		return segmentDelimiter;
	}

	public void setSegmentDelimiter(String segmentDelimiter) {
		this.segmentDelimiter = segmentDelimiter;
	}
	
	public void print() {
		System.out.println(startingSegment + segmentDelimiter);
		System.out.println(endingSegment + segmentDelimiter);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(startingSegment);
		return sb.toString();
	}
}
