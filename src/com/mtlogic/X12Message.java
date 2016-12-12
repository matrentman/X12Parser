package com.mtlogic;

import java.util.Vector;

public class X12Message {
	Vector<X12Segment> segments = new Vector<X12Segment>();
	X12Envelope envelope = new X12Envelope();
	
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
	
	public String getISASegment() {
		String isaSegment = segments.get(0).getData();
		return isaSegment;
	}
	
	public void setISASegment(String data) {
		X12Segment isaSegment = new X12Segment(data);
		segments.set(0, isaSegment);
	}
	
	public String getGSSegment() {
		String gsSegment = segments.get(1).getData();
		return gsSegment;
	}
	
	public void setGSSegment(String data) {
		X12Segment gsSegment = new X12Segment(data);
		segments.set(0, gsSegment);
	}
}