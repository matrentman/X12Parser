package com.mtlogic.x12;

import java.util.Vector;

import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12Message {
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
	
	String elementDelimiter;
	String repetitionDelimiter;
	String subelementDelimiter;
	String segmentDelimiter;
	Vector<X12InterchangeControlEnvelope> interchangeControlList;

	public X12Message(String data) throws InvalidX12MessageException {
		X12InterchangeControlEnvelope interchangeControlEnvelope = new X12InterchangeControlEnvelope(data);
		interchangeControlList = new Vector<X12InterchangeControlEnvelope>();
		interchangeControlList.add(interchangeControlEnvelope);
	}
	
	public Vector<X12InterchangeControlEnvelope> getInterchangeControlList() {
		return interchangeControlList;
	}

	public void setInterchangeControlList(Vector<X12InterchangeControlEnvelope> interchangeControlList) {
		this.interchangeControlList = interchangeControlList;
	}
	
	public void validate() {
		
	}
	
	public String print() {
		StringBuffer sb = new StringBuffer();
		sb.append(interchangeControlList.get(0).getIsaHeader().print());
		for (int i=0; i < interchangeControlList.get(0).getFunctionalGroupEnvelopes().size(); i++) {
			X12FunctionalGroupEnvelope gsEnvelope = interchangeControlList.get(0).getFunctionalGroupEnvelopes().get(i);
			
			sb.append(gsEnvelope.getGsHeader().print());
			
			for (int j=0; j < interchangeControlList.get(0).getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().size(); j++) {
				X12TransactionSetEnvelope stEnvelope = interchangeControlList.get(0).getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().get(j);
				sb.append(stEnvelope.getStHeader().print());
				
				for (X12Segment segment : stEnvelope.getSegments()) {
					sb.append(segment.print());
				}
				
				sb.append(stEnvelope.getSeTrailer().print());
			}
			
			sb.append(gsEnvelope.getGeTrailer().print());
		}
		sb.append(interchangeControlList.get(0).getIeaTrailer().print());
		return sb.toString();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(interchangeControlList.get(0).getIsaHeader().toString());
		for (int i=0; i < interchangeControlList.get(0).getFunctionalGroupEnvelopes().size(); i++) {
			X12FunctionalGroupEnvelope gsEnvelope = interchangeControlList.get(0).getFunctionalGroupEnvelopes().get(i);
			
			sb.append(gsEnvelope.getGsHeader().toString());
			
			for (int j=0; j < interchangeControlList.get(0).getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().size(); j++) {
				X12TransactionSetEnvelope stEnvelope = interchangeControlList.get(0).getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().get(j);
				sb.append(stEnvelope.getStHeader().toString());
				
				for (X12Segment segment : stEnvelope.getSegments()) {
					sb.append(segment.toString());
				}
				
				sb.append(stEnvelope.getSeTrailer().toString());
			}
			
			sb.append(gsEnvelope.getGeTrailer().toString());
		}
		sb.append(interchangeControlList.get(0).getIeaTrailer().toString());
		return sb.toString();
	}

}
