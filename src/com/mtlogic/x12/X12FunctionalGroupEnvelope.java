package com.mtlogic.x12;

import java.util.Vector;

public class X12FunctionalGroupEnvelope {
	X12GSHeader gsHeader;
	X12GETrailer geTrailer;
	Vector<X12TransactionSetEnvelope> transactionSetEnvelopes;
	
	public X12FunctionalGroupEnvelope() {
		transactionSetEnvelopes = new Vector<X12TransactionSetEnvelope>();
	}
	
	public X12GSHeader getGsHeader() {
		return gsHeader;
	}
	
	public void setGsHeader(X12GSHeader gsHeader) {
		this.gsHeader = gsHeader;
	}
	
	public X12GETrailer getGeTrailer() {
		return geTrailer;
	}
	
	public void setGeTrailer(X12GETrailer geTrailer) {
		this.geTrailer = geTrailer;
	}
	
	public Vector<X12TransactionSetEnvelope> getTransactionSetEnvelopes() {
		return transactionSetEnvelopes;
	}
	
	public void setTransactionSetEnvelopes(Vector<X12TransactionSetEnvelope> transactionSetEnvelopes) {
		this.transactionSetEnvelopes = transactionSetEnvelopes;
	}
	
	public Vector<String> validate() {
		Vector<String> messages = new Vector<String>();
		
		if (gsHeader == null) {
			messages.add("Could not parse GS segment!");
		}
		if (geTrailer == null) {
			messages.add("Could not parse GE segment!");
		}
		if (messages.isEmpty()) {
			messages.addAll(this.getGsHeader().validate());
			messages.addAll(this.getGeTrailer().validate());
			
			if (gsHeader.getGs06() != null && geTrailer.getGe02() != null 
					&& !gsHeader.getGs06().equals(geTrailer.getGe02())) {
				messages.addElement("Mismatched field: GS06 <> GE02!");
			}
		}
		
		return messages;
	}
	
	public String print() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(gsHeader.print());
		
		for (X12TransactionSetEnvelope envelope : transactionSetEnvelopes) {
			sb.append(envelope.print());
		}
		
		sb.append(geTrailer.print());
		
		return sb.toString();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(gsHeader.toString());
		
		for (X12TransactionSetEnvelope envelope : transactionSetEnvelopes) {
			sb.append(envelope.toString());
		}
		
		sb.append(geTrailer.toString());
		
		return sb.toString();
	}
}
