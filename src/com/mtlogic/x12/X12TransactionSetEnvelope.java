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
}
