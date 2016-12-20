package com.mtlogic.x12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.Test;

import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12InterchangeControlEnvelopeTest {
	String msg276 = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~IEA*1*000004251~";
	String msg276WithMissingISASegment = "GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~IEA*1*000004251~";
	String msg276WithMissingIEASegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~";
	String msg276WithMissingGSSegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~IEA*1*000004251~";
	String msg276WithMissingGESegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~IEA*1*000004251~";
	String msg276WithMissingSTSegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~IEA*1*000004251~";
	String msg276WithMissingSESegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~GE*1*4251~IEA*1*000004251~";

	@Test
	public void testToStringShouldEqualOriginalMessageString() {
		String parsed276 = null;
		X12InterchangeControlEnvelope envelope = null;
		try {
			envelope = new X12InterchangeControlEnvelope(msg276);
			parsed276 = envelope.toString();
		} catch (InvalidX12MessageException ixme) {
			System.out.println(ixme.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			assertNotNull(parsed276);
			assertEquals(parsed276, msg276.replace(':', '|'));
		}
	}
	
	@Test
	public void testPrintShouldContainOriginalMessageSegments() {
		String parsed276 = null;
		X12InterchangeControlEnvelope envelope = null;
		String[] segments = null;
		try {
			envelope = new X12InterchangeControlEnvelope(msg276);
			parsed276 = envelope.toString();
		} catch (InvalidX12MessageException ixme) {
			System.out.println(ixme.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			segments = msg276.replace(':', '|').split("~");
			for (String segment : segments) {
				assertTrue(parsed276.contains(segment));
			}
		}
	}
	
	@Test
	public void testPrintShouldContainIndividualParsedSegments() {
		String formatted276 = null;
		X12InterchangeControlEnvelope envelope = null;
		try {
			envelope = new X12InterchangeControlEnvelope(msg276);
			formatted276 = envelope.print();
		} catch (InvalidX12MessageException ixme) {
			System.out.println(ixme.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			assertTrue(formatted276.contains(envelope.getIsaHeader().toString()));
			assertTrue(formatted276.contains(envelope.getIeaTrailer().toString()));
			assertTrue(formatted276.contains(envelope.getFunctionalGroupEnvelopes().get(0).getGsHeader().toString()));
			assertTrue(formatted276.contains(envelope.getFunctionalGroupEnvelopes().get(0).getGeTrailer().toString()));
			assertTrue(formatted276.contains(envelope.getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().get(0).getStHeader().toString()));
			assertTrue(formatted276.contains(envelope.getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().get(0).getSeTrailer().toString()));
			Vector<X12Segment> segments = envelope.getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().get(0).getSegments();
			for (X12Segment segment : segments) {
				assertTrue(formatted276.contains(segment.toString()));
			}
		}
	}

	@Test
	public void testParsedMessageStringWithMissingISASegmentShouldThrowException() {
		String exceptionMessage = null;
		X12InterchangeControlEnvelope envelope = null;
		try {
			envelope = new X12InterchangeControlEnvelope(msg276WithMissingISASegment);
		} catch (InvalidX12MessageException ixme) {
			exceptionMessage = ixme.getMessage();
		} catch (Exception e) {
			exceptionMessage = e.getMessage();
		} finally {
			assertNotNull(exceptionMessage);
			assertTrue(exceptionMessage.contains("[Missing segment: ISA!]"));
		}
	}

	@Test
	public void testParsedMessageStringWithMissingIEASegmentShouldThrowException() {
		String exceptionMessage = null;
		X12InterchangeControlEnvelope envelope = null;
		try {
			envelope = new X12InterchangeControlEnvelope(msg276WithMissingIEASegment);
		} catch (InvalidX12MessageException ixme) {
			exceptionMessage = ixme.getMessage();
		} catch (Exception e) {
			exceptionMessage = e.getMessage();
		} finally {
			assertNotNull(exceptionMessage);
			assertTrue(exceptionMessage.contains("[Missmatched segment: ISA/IEA]"));
		}
	}

	
	@Test
	public void testParsedMessageStringWithMissingGSSegmentShouldThrowException() {
		String exceptionMessage = null;
		X12InterchangeControlEnvelope envelope = null;
		try {
			envelope = new X12InterchangeControlEnvelope(msg276WithMissingGSSegment);
		} catch (InvalidX12MessageException ixme) {
			exceptionMessage = ixme.getMessage();
		} catch (Exception e) {
			exceptionMessage = e.getMessage();
		} finally {
			assertNotNull(exceptionMessage);
			assertTrue(exceptionMessage.contains("[Missmatched segment: GS/GE]"));
		}
	}

	@Test
	public void testParsedMessageStringWithMissingGESegmentShouldThrowException() {
		String exceptionMessage = null;
		X12InterchangeControlEnvelope envelope = null;
		try {
			envelope = new X12InterchangeControlEnvelope(msg276WithMissingGESegment);
		} catch (InvalidX12MessageException ixme) {
			exceptionMessage = ixme.getMessage();
		} catch (Exception e) {
			exceptionMessage = e.getMessage();
		} finally {
			assertNotNull(exceptionMessage);
			assertTrue(exceptionMessage.contains("[Missmatched segment: GS/GE]"));
		}
	}
	
	@Test
	public void testParsedMessageStringWithMissingSTSegmentShouldThrowException() {
		String exceptionMessage = null;
		X12InterchangeControlEnvelope envelope = null;
		try {
			envelope = new X12InterchangeControlEnvelope(msg276WithMissingSTSegment);
		} catch (InvalidX12MessageException ixme) {
			exceptionMessage = ixme.getMessage();
		} catch (Exception e) {
			exceptionMessage = e.getMessage();
		} finally {
			assertNotNull(exceptionMessage);
			assertTrue(exceptionMessage.contains("[Missmatched segment: ST/SE]"));
		}
	}

	@Test
	public void testParsedMessageStringWithMissingSESegmentShouldThrowException() {
		String exceptionMessage = null;
		X12InterchangeControlEnvelope envelope = null;
		try {
			envelope = new X12InterchangeControlEnvelope(msg276WithMissingSESegment);
		} catch (InvalidX12MessageException ixme) {
			exceptionMessage = ixme.getMessage();
		} catch (Exception e) {
			exceptionMessage = e.getMessage();
		} finally {
			assertNotNull(exceptionMessage);
			assertTrue(exceptionMessage.contains("[Missmatched segment: ST/SE]"));
		}
	}
}
