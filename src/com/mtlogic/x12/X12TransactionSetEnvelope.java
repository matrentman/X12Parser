package com.mtlogic.x12;

import java.util.Vector;

public class X12TransactionSetEnvelope {
	X12STHeader stHeader;
	X12SETrailer seTrailer;
	Vector<X12Segment> segments;
	
	public X12TransactionSetEnvelope() {
		segments = new Vector<X12Segment>();
	}
	
	public X12STHeader getStHeader() {
		return stHeader;
	}
	
	public void setStHeader(X12STHeader stHeader) {
		this.stHeader = stHeader;
	}
	
	public X12SETrailer getSeTrailer() {
		return seTrailer;
	}
	
	public void setSeTrailer(X12SETrailer seTrailer) {
		this.seTrailer = seTrailer;
	}
	
	public Vector<X12Segment> getSegments() {
		return segments;
	}
	
	public void setSegments(Vector<X12Segment> segments) {
		this.segments = segments;
	}
	
	public Vector<String> validate() {
		Vector<String> messages = new Vector<String>();
		
		messages.addAll(this.getStHeader().validate());
		messages.addAll(this.getSeTrailer().validate());
		
		if (stHeader != null && seTrailer != null && stHeader.getSt02() != null 
				&& seTrailer.getSe02() != null && !stHeader.getSt02().equals(seTrailer.getSe02())) {
			messages.add("Mismatched field: ST02 <> SE02!");
		}
		if (this.getSegments()!=null && this.getSegments().size()+2 != Integer.parseInt(seTrailer.getSe01())) {
			messages.addElement("Mismatched field: SE01 does not match the actual number of transaction set segments!");
		}
		
		return messages;
	}
}
