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
	
	X12Envelope isaEnvelope;
	Vector<X12Container> containers = new Vector<X12Container>();
	
	String elementDelimiter;
	String subelementDelimiter;
	String segmentDelimiter;
	
	X12Message(String data) {		
		elementDelimiter = "" + data.charAt(ELEMENT_DELIMITER_PS);
		subelementDelimiter = "" + data.charAt(SUB_ELEMENT_DELIMITER_PS);
		segmentDelimiter = "" + data.charAt(SEGMENT_DELIMITER_PS);
		
		isaEnvelope = new X12Envelope();
		
		String[] parsedSegments = data.split(segmentDelimiter);
		X12Segment segment = null;
		X12Container container = null;
		for (String str : parsedSegments) {
            if (str.startsWith(ISA + elementDelimiter)) {
            	segment = new X12Segment(str, elementDelimiter);
            	isaEnvelope.setStartingSegment(segment);
            } else if (str.startsWith(IEA + elementDelimiter)) {
            	segment = new X12Segment(str, elementDelimiter);
            	isaEnvelope.setEndingSegment(segment);
            } else if (str.startsWith(GS + elementDelimiter)) {
            	segment = new X12Segment(str, elementDelimiter);
            	container = new X12Container();
            	container.setSegmentDelimiter(segmentDelimiter);
            	container.getEnvelope().setSegmentDelimiter(segmentDelimiter);
            	container.getEnvelope().setStartingSegment(segment);
            } else if (str.startsWith(GE + elementDelimiter)) {
            	segment = new X12Segment(str, elementDelimiter);
            	container.getEnvelope().setEndingSegment(segment);
            	containers.add(container);
            } else {
            	segment = new X12Segment(str, elementDelimiter);
            	container.getSegments().add(segment);
            }
		}
	}
	
	public boolean validate() {
		boolean isValid = false;
		isValid = isaEnvelope.validate();
		return isValid;
	}
	
	public X12Envelope getIsaEnvelope() {
		return isaEnvelope;
	}

	public void setIsaEnvelope(X12Envelope envelope) {
		this.isaEnvelope = envelope;
	}
	
	public void print() {
		System.out.println(isaEnvelope.getStartingSegment().toString() + segmentDelimiter);
		for (X12Container container : containers) {
			System.out.println(container.getEnvelope().getStartingSegment().toString() + segmentDelimiter);
			for (X12Segment segment : container.getSegments()) {
				System.out.println(segment.toString() + segmentDelimiter);
			}
			System.out.println(container.getEnvelope().getEndingSegment().toString() + segmentDelimiter);
		}
		System.out.println(isaEnvelope.getEndingSegment().toString() + segmentDelimiter);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(isaEnvelope.getStartingSegment().toString());
		sb.append(segmentDelimiter);
		for (X12Container container : containers) {
			sb.append(container.getEnvelope().getStartingSegment().toString());
			sb.append(segmentDelimiter);
			for (X12Segment segment : container.getSegments()) {
				sb.append(segment.toString());
				sb.append(segmentDelimiter);
			}
			sb.append(container.getEnvelope().getStartingSegment().toString());
			sb.append(segmentDelimiter);
		}
		sb.append(isaEnvelope.getEndingSegment().toString());
		sb.append(segmentDelimiter);
		return sb.toString();
	}

}