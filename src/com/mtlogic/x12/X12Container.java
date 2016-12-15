package com.mtlogic.x12;

import java.util.Vector;

public class X12Container {
	X12Envelope envelope;
	Vector<X12Segment> segments;
	String segmentDelimiter;

	X12Container() {
		envelope = new X12Envelope();
		segments = new Vector<X12Segment>();
	}
	
	public X12Envelope getEnvelope() {
		return envelope;
	}

	public void setEnvelope(X12Envelope envelope) {
		this.envelope = envelope;
	}

	public Vector<X12Segment> getSegments() {
		return segments;
	}

	public void setSegments(Vector<X12Segment> segments) {
		this.segments = segments;
	}

	public String getSegmentDelimiter() {
		return segmentDelimiter;
	}

	public void setSegmentDelimiter(String segmentDelimiter) {
		this.segmentDelimiter = segmentDelimiter;
	}
}
