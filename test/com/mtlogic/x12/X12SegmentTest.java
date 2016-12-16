package com.mtlogic.x12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class X12SegmentTest {
	String isaSegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~";

	@Test
	public void testToStringShouldEqualOriginalSegmentString() {
		String parsedSegment = null;
		X12Segment segment = null;
		try {
			String[] segmentArray = isaSegment.split("~");
			segment = new X12Segment(segmentArray[0], "*");
			parsedSegment = segment.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			assertNotNull(parsedSegment);
			assertEquals(parsedSegment, isaSegment);
		}
	}
	
	@Test
	public void testNameShouldEqualISA() {
		String parsedSegment = null;
		X12Segment segment = null;
		try {
			String[] segmentArray = isaSegment.split("~");
			segment = new X12Segment(segmentArray[0], "*");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			assertNotNull(segment);
			assertNotNull(segment.getName());
			assertEquals(segment.getName(), "ISA");
		}
	}
	
	@Test
	public void testSubNameShouldEqual00() {
		String parsedSegment = null;
		X12Segment segment = null;
		try {
			String[] segmentArray = isaSegment.split("~");
			segment = new X12Segment(segmentArray[0], "*");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			assertNotNull(segment);
			assertNotNull(segment.getSubName());
			assertEquals(segment.getSubName(), "00");
		}
	}
	
	@Test
	public void testElementsShouldNotBeNull() {
		String parsedSegment = null;
		X12Segment segment = null;
		String[] segmentArray = null;
		String[] fieldArray = null;
		try {
			segmentArray = isaSegment.split("~");
			segment = new X12Segment(segmentArray[0], "*");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			assertNotNull(segment);
			assertNotNull(segment.getElements());
			fieldArray = segmentArray[0].split("\\*");
			assertEquals(segment.getElements().length, fieldArray.length);
		}
	}
}
