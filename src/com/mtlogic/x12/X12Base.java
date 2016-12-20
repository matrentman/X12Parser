package com.mtlogic.x12;

import java.util.Vector;

public class X12Base {
	public static final String ISA = "ISA";
	public static final String GS = "GS";
	public static final String ST = "ST";
	public static final String SE = "SE";
	public static final String IEA = "IEA";
	public static final String GE = "GE";
	public static final int REPETITION_DELIMITER_PS = 82;
	public static final int ELEMENT_DELIMITER_PS = 103;
	public static final int SUB_ELEMENT_DELIMITER_PS = 104;
	public static final int SEGMENT_DELIMITER_PS = 105;
	public static final String ECP_STANDARD_SEGMENT_DELIMITER = "~";
	public static final String ECP_STANDARD_ELEMENT_DELIMITER = "*";
	public static final String ECP_STANDARD_SUBELEMENT_DELIMITER = ":";
	
	public String formatErrorMessages(Vector<String> messages) {
		StringBuffer sb = new StringBuffer();
		if (!messages.isEmpty()) {
			sb.append("Invalid message: [");
			for (int i=0; i < messages.size()-1; i++) {
				sb.append(messages.get(i));
				sb.append(", ");
			}
			sb.append(messages.get(messages.size()-1));
			sb.append("]");
		}
		return sb.toString();
	}
}
