package com.mtlogic;

import java.util.Vector;

public class X12Message {
	public static final int ELEMENT_DELIMITER_PS = 103;
	public static final int SUB_ELEMENT_DELIMITER_PS = 104;
	public static final int SEGMENT_DELIMITER_PS = 105;
	public static final String ISA = "ISA";
	public static final String GS = "GS";
	public static final String IEA = "IEA";
	public static final String GE = "GE";
	
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
            if (str.startsWith(ISA + elementDelimiter)) {
            	segment = new X12Segment(str + segmentDelimiter, elementDelimiter);
            	envelope.setIsaSegment(segment);
            } else if (str.startsWith(GS + elementDelimiter)) {
            	segment = new X12Segment(str, elementDelimiter);
            	envelope.setGsSegment(segment);
            } else if (str.startsWith(IEA + elementDelimiter)) {
            	segment = new X12Segment(str, elementDelimiter);
            	envelope.setIeaSegment(segment);
            } else if (str.startsWith(GE + elementDelimiter)) {
            	segment = new X12Segment(str, elementDelimiter);
            	envelope.setGeSegment(segment);
            } else {
            	segment = new X12Segment(str, elementDelimiter);
            	segments.add(segment);
            }
		}
	}
	
	public void add(X12Segment segment) {
		segments.addElement(segment);
	}
	
	public boolean validate() {
		boolean isValid = false;
		isValid = envelope.validate();
		return isValid;
	}
	
	public Vector<X12Segment> getSegments() {
		return segments;
	}

	public void setSegments(Vector<X12Segment> segments) {
		this.segments = segments;
	}
	
	public X12Segment getISASegment() {
		return segments.get(0);
	}
	
	public void setISASegment(String data) {
		X12Segment isaSegment = new X12Segment(data, elementDelimiter);
		segments.set(0, isaSegment);
	}
	
	public X12Segment getGSSegment() {
		return segments.get(1);
	}
	
	public void setGSSegment(String data) {
		X12Segment gsSegment = new X12Segment(data, elementDelimiter);
		segments.set(0, gsSegment);
	}
	
	public X12Envelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(X12Envelope envelope) {
		this.envelope = envelope;
	}
	
	public void print() {
		System.out.println(envelope.getIsaSegment().toString());
		System.out.println(envelope.getGsSegment().toString() + segmentDelimiter);
		for (X12Segment segment : segments) {
			System.out.println(segment.toString() + segmentDelimiter);
		}
		System.out.println(envelope.getGeSegment().toString() + segmentDelimiter);
		System.out.println(envelope.getIeaSegment().toString() + segmentDelimiter);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(envelope.getIsaSegment().toString());
		sb.append(envelope.getGsSegment().toString());
		sb.append(segmentDelimiter);
		for (X12Segment segment : segments) {
			sb.append(segment.toString());
			sb.append(segmentDelimiter);
		}
		sb.append(envelope.getGeSegment().toString());
		sb.append(segmentDelimiter);
		sb.append(envelope.getIeaSegment().toString());
		sb.append(segmentDelimiter);
		return sb.toString();
	}

}