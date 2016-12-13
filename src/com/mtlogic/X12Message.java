package com.mtlogic;

import java.util.Vector;

public class X12Message {
	public static final int ELEMENT_DELIMITER_PS = 103;
	public static final int SUB_ELEMENT_DELIMITER_PS = 104;
	public static final int SEGMENT_DELIMITER_PS = 105;
	public static final int ISA_TOTAL_ELEMENTS = 16;
	public static final int ISA01_LENGTH = 2;
	public static final int ISA02_LENGTH = 10;
	public static final int ISA03_LENGTH = 2;
	public static final int ISA04_LENGTH = 10;
	public static final int ISA05_LENGTH = 2;
	public static final int ISA06_LENGTH = 15;
	public static final int ISA07_LENGTH = 2;
	public static final int ISA08_LENGTH = 15;
	public static final int ISA09_LENGTH = 6;
	public static final int ISA10_LENGTH = 4;
	public static final int ISA11_LENGTH = 1;
	public static final int ISA12_LENGTH = 5;
	public static final int ISA13_LENGTH = 9;
	public static final int ISA14_LENGTH = 1;
	public static final int ISA15_LENGTH = 1;
	public static final int ISA16_LENGTH = 1;
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
		Vector<String> errorMessages = validateISASegment();
		return errorMessages.isEmpty();
	}
	
	public Vector<String> validateISASegment() {
		Vector<String> errorMessages = new Vector<String>();
		
		if (isaEnvelope.getStartingSegment() == null 
				|| !isaEnvelope.getStartingSegment().getName().equals(ISA)) {
			errorMessages.add("Missing ISA segment!");
		}
		
		String[] stringArray = (isaEnvelope.getStartingSegment().toString()).split("\\*");
		if (stringArray.length != (ISA_TOTAL_ELEMENTS + 1)) {
			errorMessages.addElement("Incorrect numebr of ISA elements!");
		}
				
		if (stringArray[1].length() != ISA01_LENGTH) {
			errorMessages.add("Invalid length for ISA01!");
		}
		
		if (stringArray[2].length() != ISA02_LENGTH) {
			errorMessages.add("Invalid length for ISA02");
		}
		
		if (stringArray[3].length() != ISA03_LENGTH) {
			errorMessages.add("Invalid length for ISA03!");
		}
		
		if (stringArray[4].length() != ISA04_LENGTH) {
			errorMessages.add("Invalid length for ISA04");
		}
		
		if (stringArray[5].length() != ISA05_LENGTH) {
			errorMessages.add("Invalid length for ISA05!");
		}
		
		if (stringArray[6].length() != ISA06_LENGTH) {
			errorMessages.add("Invalid length for ISA06");
		}
		
		if (stringArray[7].length() != ISA07_LENGTH) {
			errorMessages.add("Invalid length for ISA07!");
		}
		
		if (stringArray[8].length() != ISA08_LENGTH) {
			errorMessages.add("Invalid length for ISA08");
		}
		
		if (stringArray[9].length() != ISA09_LENGTH) {
			errorMessages.add("Invalid length for ISA09!");
		}
		
		if (stringArray[10].length() != ISA10_LENGTH) {
			errorMessages.add("Invalid length for ISA10");
		}
		
		if (stringArray[11].length() != ISA11_LENGTH) {
			errorMessages.add("Invalid length for ISA11!");
		}
		
		if (stringArray[12].length() != ISA12_LENGTH) {
			errorMessages.add("Invalid length for ISA12");
		}
		
		if (stringArray[13].length() != ISA13_LENGTH) {
			errorMessages.add("Invalid length for ISA13!");
		}
		
		if (stringArray[14].length() != ISA14_LENGTH) {
			errorMessages.add("Invalid length for ISA14");
		}
		
		if (stringArray[15].length() != ISA15_LENGTH) {
			errorMessages.add("Invalid length for ISA15!");
		}
		
		if (stringArray[16].length() != ISA16_LENGTH) {
			errorMessages.add("Invalid length for ISA16");
		}
				
		return errorMessages;
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