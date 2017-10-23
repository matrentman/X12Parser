package com.mtlogic.x12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.Test;

import com.mtlogic.x12.X12FunctionalGroupEnvelope;
import com.mtlogic.x12.X12Message;
import com.mtlogic.x12.X12Segment;
import com.mtlogic.x12.X12TransactionSetEnvelope;
import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12TransactionSetEnvelopeTest {
	String msg276 = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~IEA*1*000004251~";
	String msg276GSEnvelope = "GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~";
	String msg276STEnvelope = "ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~";
	String msg276WithMissingISASegment = "GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~IEA*1*000004251~";
	String msg276WithMissingIEASegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~";
	String msg276WithMissingGSSegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~IEA*1*000004251~";
	String msg276WithMissingGESegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~IEA*1*000004251~";
	String msg276WithMissingSTSegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~IEA*1*000004251~";
	String msg276WithMissingSESegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~GE*1*4251~IEA*1*000004251~";

	@Test
	public void testToStringShouldEqualSTDataFromTheOriginalMessageString() {
		String parsedEnvelope = null;
		X12Message message = null;
		X12TransactionSetEnvelope envelope = null;
		try {
			message = new X12Message(msg276);
			envelope = message.getInterchangeControlList().get(0).getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().get(0);
			parsedEnvelope = envelope.toString();
		} catch (InvalidX12MessageException ixme) {
			System.out.println(ixme.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			assertNotNull(parsedEnvelope);
			assertEquals(parsedEnvelope, msg276STEnvelope);
		}
	}
	
	@Test
	public void testPrintShouldContainOriginalEnvelopeSegments() {
		String formattedEnvelope = null;
		String[] segments = null;
		X12Message message = null;
		X12FunctionalGroupEnvelope envelope = null;
		try {
			message = new X12Message(msg276);
			envelope = message.getInterchangeControlList().get(0).getFunctionalGroupEnvelopes().get(0);
			formattedEnvelope = message.print();
		} catch (InvalidX12MessageException ixme) {
			System.out.println(ixme.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			segments = msg276GSEnvelope.split("~");
			for (String segment : segments) {
				assertTrue(formattedEnvelope.contains(segment));
			}
		}
	}
	
	@Test
	public void testPrintShouldContainIndividualParsedSegments() {
		String formattedEnvelope = null;
		X12Message message = null;
		X12FunctionalGroupEnvelope envelope = null;
		try {
			message = new X12Message(msg276);
			envelope = message.getInterchangeControlList().get(0).getFunctionalGroupEnvelopes().get(0);
			formattedEnvelope = message.print();
		} catch (InvalidX12MessageException ixme) {
			System.out.println(ixme.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			assertTrue(formattedEnvelope.contains(message.getInterchangeControlList().get(0).getIsaHeader().toString()));
			assertTrue(formattedEnvelope.contains(message.getInterchangeControlList().get(0).getIeaTrailer().toString()));
			assertTrue(formattedEnvelope.contains(message.getInterchangeControlList().get(0).getFunctionalGroupEnvelopes().get(0).getGsHeader().toString()));
			assertTrue(formattedEnvelope.contains(message.getInterchangeControlList().get(0).getFunctionalGroupEnvelopes().get(0).getGeTrailer().toString()));
			assertTrue(formattedEnvelope.contains(message.getInterchangeControlList().get(0).getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().get(0).getStHeader().toString()));
			assertTrue(formattedEnvelope.contains(message.getInterchangeControlList().get(0).getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().get(0).getSeTrailer().toString()));
			Vector<X12Segment> segments = message.getInterchangeControlList().get(0).getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().get(0).getSegments();
			for (X12Segment segment : segments) {
				assertTrue(formattedEnvelope.contains(segment.toString()));
			}
		}
	}
	
	@Test
	public void testParsedMessageStringWithMissingSTSegmentShouldThrowException() {
		String exceptionMessage = null;
		X12Message message = null;
		try {
			message = new X12Message(msg276WithMissingSTSegment);
		} catch (InvalidX12MessageException ixme) {
			exceptionMessage = ixme.getMessage();
		} catch (Exception e) {
			exceptionMessage = e.getMessage();
		} finally {
			assertNotNull(exceptionMessage);
			assertTrue(exceptionMessage.contains("[Mismatched segment: ST/SE]"));
		}
	}
	
	@Test
	public void testShouldGetSetIdentifierCode() {
		String formattedEnvelope = null;
		X12Message message = null;
		X12TransactionSetEnvelope envelope = null;
		try {
			message = new X12Message(msg276);
			envelope = message.getInterchangeControlList().get(0).getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().get(0);
			formattedEnvelope = message.print();
		} catch (InvalidX12MessageException ixme) {
			System.out.println(ixme.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			assertNotNull(envelope.getSetIdentifierCode());
			assertTrue(envelope.getSetIdentifierCode().equals("276"));
		}
	}
	
	@Test
	public void testShouldSetSetIdentifierCode() {
		String formattedEnvelope = null;
		X12Message message = null;
		X12TransactionSetEnvelope envelope = null;
		try {
			message = new X12Message(msg276);
			envelope = message.getInterchangeControlList().get(0).getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().get(0);
			formattedEnvelope = message.print();
			envelope.setSetIdentifierCode("277");
		} catch (InvalidX12MessageException ixme) {
			System.out.println(ixme.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			assertNotNull(envelope.getSetIdentifierCode());
			assertTrue(envelope.getSetIdentifierCode().equals("277"));
		}
	}

	@Test
	public void testParsedMessageStringWithMissingSESegmentShouldThrowException() {
		String exceptionMessage = null;
		X12Message message = null;
		try {
			message = new X12Message(msg276WithMissingSESegment);
		} catch (InvalidX12MessageException ixme) {
			exceptionMessage = ixme.getMessage();
		} catch (Exception e) {
			exceptionMessage = e.getMessage();
		} finally {
			assertNotNull(exceptionMessage);
			assertTrue(exceptionMessage.contains("[Mismatched segment: ST/SE]"));
		}
	}
}
