package com.mtlogic;

import java.util.Vector;

public class X12Message {
	public static final int ELEMENT_DELIMITER_PS = 103;
	public static final int SUB_ELEMENT_DELIMITER_PS = 104;
	public static final int SEGMENT_DELIMITER_PS = 105;
	
	Vector<X12Segment> segments = new Vector<X12Segment>();
	X12Envelope envelope = new X12Envelope();
	
	String elementDelimiter;
	String subelementDelimiter;
	String segmentDelimiter;
	
	X12Message(String data) {		
		elementDelimiter = "" + data.charAt(ELEMENT_DELIMITER_PS);
		subelementDelimiter = "" + data.charAt(SUB_ELEMENT_DELIMITER_PS);
		segmentDelimiter = "" + data.charAt(SEGMENT_DELIMITER_PS);
		
		String[] parsedSegments = data.split(segmentDelimiter); 
		for (String str : parsedSegments) {
			X12Segment segment;
            segment = new X12Segment(str, elementDelimiter);
			segments.add(segment);
		}
	}
	
	public void add(X12Segment segment) {
		segments.addElement(segment);
	}
	
	public void print() {
		for (X12Segment segment : segments) {
			System.out.println(segment.toString() + segmentDelimiter);
		}
	}
	
	public boolean validate() {
		boolean isValid = false;
		
		return isValid;
	}
	
	public Vector<X12Segment> getSegments() {
		return segments;
	}

	public void setSegments(Vector<X12Segment> segments) {
		this.segments = segments;
	}
	
	public String getISASegment() {
		String isaSegment = segments.get(0).getData();
		return isaSegment;
	}
	
	public void setISASegment(String data) {
		X12Segment isaSegment = new X12Segment(data, elementDelimiter);
		segments.set(0, isaSegment);
	}
	
	public String getGSSegment() {
		String gsSegment = segments.get(1).getData();
		return gsSegment;
	}
	
	public void setGSSegment(String data) {
		X12Segment gsSegment = new X12Segment(data, elementDelimiter);
		segments.set(0, gsSegment);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (X12Segment segment : segments) {
			sb.append(segment.toString());
			sb.append(segmentDelimiter);
		}
		return sb.toString();
	}
}