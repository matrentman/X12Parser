package com.mtlogic.x12;

import java.util.Vector;

import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12InterchangeControlEnvelope {
	public static final int REPETITION_DELIMITER_PS = 82;
	public static final int ELEMENT_DELIMITER_PS = 103;
	public static final int SUB_ELEMENT_DELIMITER_PS = 104;
	public static final int SEGMENT_DELIMITER_PS = 105;
	
	String elementDelimiter;
	String repetitionDelimiter;
	String subelementDelimiter;
	String segmentDelimiter;
	X12ISAHeader isaHeader;
	X12IEATrailer ieaTrailer;
	Vector<X12FunctionalGroupEnvelope> functionalGroupEnvelopes;
	
	public X12InterchangeControlEnvelope(String data) throws InvalidX12MessageException {
		elementDelimiter = "" + data.charAt(ELEMENT_DELIMITER_PS);
		repetitionDelimiter = "" + data.charAt(REPETITION_DELIMITER_PS);
		subelementDelimiter = "" + data.charAt(SUB_ELEMENT_DELIMITER_PS);
		segmentDelimiter = "" + data.charAt(SEGMENT_DELIMITER_PS);
		functionalGroupEnvelopes = new Vector<X12FunctionalGroupEnvelope>();
		X12FunctionalGroupEnvelope gsEnvelope = null;
		X12TransactionSetEnvelope stEnvelope = null;
		String[] parsedSegments = data.split(segmentDelimiter);
		if ((parsedSegments[0].startsWith(X12Message1.ISA + elementDelimiter))) {
			X12Segment segment = null;
			for (String str : parsedSegments) {
	            if (str.startsWith(X12Message1.ISA + elementDelimiter)) {
	            	isaHeader = new X12ISAHeader(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            } else if (str.startsWith(X12Message1.IEA + elementDelimiter)) {
	            	ieaTrailer = new X12IEATrailer(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            } else if (str.startsWith(X12Message1.GS + elementDelimiter)) {
	            	gsEnvelope = new X12FunctionalGroupEnvelope();
	            	X12GSHeader gsHeader = new X12GSHeader(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            	gsEnvelope.setGsHeader(gsHeader);
	            } else if (str.startsWith(X12Message1.GE + elementDelimiter)) {
	            	X12GETrailer geTrailer = new X12GETrailer(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            	gsEnvelope.setGeTrailer(geTrailer);
	            	gsEnvelope.getTransactionSetEnvelopes().add(stEnvelope);
	            	functionalGroupEnvelopes.add(gsEnvelope);
	            } else if (str.startsWith(X12Message1.ST + elementDelimiter)) {
	            	stEnvelope = new X12TransactionSetEnvelope();
	            	X12STHeader stHeader = new X12STHeader(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            	stEnvelope.setStHeader(stHeader);
	            } else if (str.startsWith(X12Message1.SE + elementDelimiter)) {
	            	X12SETrailer seTrailer = new X12SETrailer(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            	stEnvelope.setSeTrailer(seTrailer);
	            } else {
	            	segment = new X12Segment(str, elementDelimiter);
	            	stEnvelope.getSegments().add(segment);
	            }
			}
		} 
	}
	
	public X12ISAHeader getIsaHeader() {
		return isaHeader;
	}
	public void setIsaHeader(X12ISAHeader isaHeader) {
		this.isaHeader = isaHeader;
	}
	public X12IEATrailer getIeaTrailer() {
		return ieaTrailer;
	}
	public void setIeaTrailer(X12IEATrailer ieaTrailer) {
		this.ieaTrailer = ieaTrailer;
	}
	public Vector<X12FunctionalGroupEnvelope> getFunctionalGroupEnvelopes() {
		return functionalGroupEnvelopes;
	}
	public void setFunctionalGroupEnvelopes(Vector<X12FunctionalGroupEnvelope> functionalGroupEnvelopes) {
		this.functionalGroupEnvelopes = functionalGroupEnvelopes;
	}
}