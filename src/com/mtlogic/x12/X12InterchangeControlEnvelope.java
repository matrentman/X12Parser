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
		if ((parsedSegments[0].startsWith(X12Message.ISA + elementDelimiter))) {
			X12Segment segment = null;
			for (String str : parsedSegments) {
	            if (str.startsWith(X12Message.ISA + elementDelimiter)) {
	            	isaHeader = new X12ISAHeader(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            } else if (str.startsWith(X12Message.IEA + elementDelimiter)) {
	            	ieaTrailer = new X12IEATrailer(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            } else if (str.startsWith(X12Message.GS + elementDelimiter)) {
	            	gsEnvelope = new X12FunctionalGroupEnvelope();
	            	X12GSHeader gsHeader = new X12GSHeader(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            	gsEnvelope.setGsHeader(gsHeader);
	            } else if (str.startsWith(X12Message.GE + elementDelimiter)) {
	            	X12GETrailer geTrailer = new X12GETrailer(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            	gsEnvelope.setGeTrailer(geTrailer);
	            	gsEnvelope.getTransactionSetEnvelopes().add(stEnvelope);
	            	functionalGroupEnvelopes.add(gsEnvelope);
	            } else if (str.startsWith(X12Message.ST + elementDelimiter)) {
	            	stEnvelope = new X12TransactionSetEnvelope();
	            	X12STHeader stHeader = new X12STHeader(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            	stEnvelope.setStHeader(stHeader);
	            } else if (str.startsWith(X12Message.SE + elementDelimiter)) {
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
	
	public Vector<String> validate() {
		Vector<String> messages = new Vector<String>();
		
		if (this.elementDelimiter == null || this.elementDelimiter.isEmpty()) {
			messages.add("Could not parse element delimiter!");
		}
		if (this.subelementDelimiter == null || this.subelementDelimiter.isEmpty()) {
			messages.add("Could not parse subelement delimiter!");
		}
		if (this.segmentDelimiter == null || this.segmentDelimiter.isEmpty()) {
			messages.add("Could not parse segment delimiter!");
		}
		
		if (messages.isEmpty()) {
			messages.addAll(this.getIsaHeader().validate());
			messages.addAll(this.getIeaTrailer().validate());
			
			if (isaHeader.getIsa13() != null && ieaTrailer.getIea02() != null 
					&& !isaHeader.getIsa13().equals(ieaTrailer.getIea02())) {
				messages.addElement("Mismatched field: ISA13 <> IEA02!");
			}
			
			for (X12FunctionalGroupEnvelope gsEnvelope : this.getFunctionalGroupEnvelopes()) {
				messages.addAll(gsEnvelope.validate());
				for (X12TransactionSetEnvelope stEnvelope : gsEnvelope.getTransactionSetEnvelopes()) {
					messages.addAll(stEnvelope.validate());
				}
			}
		}
		return messages;
	}
}
