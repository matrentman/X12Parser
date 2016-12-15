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
}
