package com.mtlogic;

import java.util.Vector;

public class X12Message {
	Vector<X12Segment> segments = new Vector<X12Segment>();
	
	public void add(X12Segment segment) {
		segments.addElement(segment);
	}
	
	public void print() {
		for (X12Segment segment : segments) {
			System.out.println(segment.getData());
		}
	}
	public Vector<X12Segment> getSegments() {
		return segments;
	}

	public void setSegments(Vector<X12Segment> segments) {
		this.segments = segments;
	}
}