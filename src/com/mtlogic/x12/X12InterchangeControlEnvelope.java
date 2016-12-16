package com.mtlogic.x12;

import java.util.Vector;

import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12InterchangeControlEnvelope extends X12Base {
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
		if (!data.startsWith(ISA)) {
			throw new InvalidX12MessageException("Invalid message: [Missing segment: ISA!]");
		}
		
		String[] parsedSegments = data.split(segmentDelimiter);
		validateEnvelopes(parsedSegments);
		
		if ((parsedSegments[0].startsWith(ISA + elementDelimiter))) {
			X12Segment segment = null;
			for (String str : parsedSegments) {
	            if (str.startsWith(ISA + elementDelimiter)) {
	            	isaHeader = new X12ISAHeader(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            } else if (str.startsWith(IEA + elementDelimiter)) {
	            	ieaTrailer = new X12IEATrailer(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            } else if (str.startsWith(GS + elementDelimiter)) {
	            	gsEnvelope = new X12FunctionalGroupEnvelope();
	            	X12GSHeader gsHeader = new X12GSHeader(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            	gsEnvelope.setGsHeader(gsHeader);
	            } else if (str.startsWith(GE + elementDelimiter)) {
	            	if (gsEnvelope != null) {
		            	X12GETrailer geTrailer = new X12GETrailer(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
		            	gsEnvelope.setGeTrailer(geTrailer);
		            	gsEnvelope.getTransactionSetEnvelopes().add(stEnvelope);
		            	functionalGroupEnvelopes.add(gsEnvelope);
	            	} else	{
	            		throw new InvalidX12MessageException("Invalid message: [Missing segment: GS!]");
	            	}
	            } else if (str.startsWith(ST + elementDelimiter)) {
	            	stEnvelope = new X12TransactionSetEnvelope();
	            	X12STHeader stHeader = new X12STHeader(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            	stEnvelope.setStHeader(stHeader);
	            } else if (str.startsWith(SE + elementDelimiter)) {
	            	X12SETrailer seTrailer = new X12SETrailer(str, segmentDelimiter, elementDelimiter, subelementDelimiter);
	            	stEnvelope.setSeTrailer(seTrailer);
	            } else {
	            	if (stEnvelope != null) {
	            		segment = new X12Segment(str, elementDelimiter);
	            		stEnvelope.getSegments().add(segment);
	            	} else {
	            		throw new InvalidX12MessageException("Invalid message: [Missing segment: ST!]"); 
	            	}
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
		if (this.getIsaHeader() == null) {
			messages.add("Could not parse ISA segment!");
		}
		if (this.getIeaTrailer() == null) {
			messages.add("Could not parse IEA segment!");
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
	
	private void validateEnvelopes(String[] segments) throws InvalidX12MessageException {
		int numISA = 0;
		int numIEA = 0;
		int numGS = 0;
		int numGE = 0;
		int numST = 0;
		int numSE = 0;
		Vector<String> messages = new Vector<String>();
		
		for (String segment : segments) {
			if (segment.startsWith(ISA)) {
				numISA++;
			} else if (segment.startsWith(IEA)) {
				numIEA++;
			} else if (segment.startsWith(GS)) {
				numGS++;
			} else if (segment.startsWith(GE)) {
				numGE++;
			} else if (segment.startsWith(ST)) {
				numST++;
			} else if (segment.startsWith(SE)) {
				numSE++;
			}
		}
		
		if (numISA != numIEA) {
			messages.add("Missmatched segment: ISA/IEA");
		}
		if (numGS != numGE) {
			messages.add("Missmatched segment: GS/GE");
		}
		if (numST != numSE) {
			messages.add("Missmatched segment: ST/SE");
		}
		
		if (!messages.isEmpty()) {
			throw new InvalidX12MessageException(formatErrorMessages(messages));
		}
	}
	
	public String print() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(isaHeader.print());
		for (X12FunctionalGroupEnvelope envelope : functionalGroupEnvelopes) {
			sb.append(envelope.print());
		}
		sb.append(ieaTrailer.print());
		
		return sb.toString();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(isaHeader.toString());
		for (X12FunctionalGroupEnvelope envelope : functionalGroupEnvelopes) {
			sb.append(envelope.toString());
		}
		sb.append(ieaTrailer.toString());
		return sb.toString();
	}
}
