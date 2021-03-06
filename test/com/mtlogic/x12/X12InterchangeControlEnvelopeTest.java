package com.mtlogic.x12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Vector;

import org.junit.Test;

import com.mtlogic.x12.X12InterchangeControlEnvelope;
import com.mtlogic.x12.X12Segment;
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
	public void testShouldGetDataSegments() {
		String formatted276 = null;
		X12InterchangeControlEnvelope envelope = null;
		List<X12Segment> dataSegments = null;
		try {
			envelope = new X12InterchangeControlEnvelope(msg276);
			formatted276 = envelope.print();
			dataSegments = envelope.getDataSegments();
		} catch (InvalidX12MessageException ixme) {
			System.out.println(ixme.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			assertNotNull(dataSegments);
			assertTrue(dataSegments.size() == 15);
			for (X12Segment segment : dataSegments) {
				assertTrue(formatted276.contains(segment.toString()));
			}
		}
	}
	
	@Test
	public void testShouldGetSubscriberIdentifier() {
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
			assertNotNull(envelope.getSubscriberIdentifier());
			assertTrue(envelope.getSubscriberIdentifier().equals("562912567807"));
		}
	}
	
	@Test
	public void testIsDependentShouldReturnTrue() {
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
			assertTrue(envelope.isDependent());
		}
	}
	
	@Test
	public void testShouldGetPatientFirstName() {
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
			assertNotNull(envelope.getPatientFirstName());
			assertTrue(envelope.getPatientFirstName().equals("JENNIFER"));
		}
	}
	
	@Test
	public void testShouldGetPatientLastName() {
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
			assertNotNull(envelope.getPatientLastName());
			assertTrue(envelope.getPatientLastName().equals("TALLMAN"));
		}
	}
	
	@Test
	public void testShouldGetDependentFirstName() {
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
			assertNotNull(envelope.getDependentFirstName());
			assertTrue(envelope.getDependentFirstName().equals("JENNIFER"));
		}
	}
	
	@Test
	public void testShouldGetDependentLastName() {
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
			assertNotNull(envelope.getDependentLastName());
			assertTrue(envelope.getDependentLastName().equals("TALLMAN"));
		}
	}
	
	@Test
	public void testShouldGetSubscriberFirstName() {
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
			assertNotNull(envelope.getSubscriberFirstName());
			assertTrue(envelope.getSubscriberFirstName().equals("DARYN"));
		}
	}
	
	@Test
	public void testShouldGetSubscriberLastName() {
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
			assertNotNull(envelope.getSubscriberLastName());
			assertTrue(envelope.getSubscriberLastName().equals("TALLMAN"));
		}
	}
	
	@Test
	public void testShouldGetPatientBirthDate() {
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
			assertNotNull(envelope.getPatientBirthDate());
			assertTrue(envelope.getPatientBirthDate().equals("19780729"));
		}
	}

	@Test
	public void testShouldGetSubmitter() {
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
			assertNotNull(envelope.getSubmitter());
			assertTrue(envelope.getSubmitter().equals("341559999      "));
		}
	}
	
	@Test
	public void testShouldSetSubmitter() {
		String formatted276 = null;
		X12InterchangeControlEnvelope envelope = null;
		try {
			envelope = new X12InterchangeControlEnvelope(msg276);
			formatted276 = envelope.print();
			envelope.setSubmitter("SUBMITTER      ");
		} catch (InvalidX12MessageException ixme) {
			System.out.println(ixme.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			assertNotNull(envelope.getSubmitter());
			assertTrue(envelope.getSubmitter().equals("SUBMITTER      "));
		}
	}
	
	@Test
	public void testShouldGetReceiver() {
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
			assertNotNull(envelope.getReceiver());
			assertTrue(envelope.getReceiver().equals("CONSULT        "));
		}
	}
	
	@Test
	public void testShouldSetReceiver() {
		String formatted276 = null;
		X12InterchangeControlEnvelope envelope = null;
		try {
			envelope = new X12InterchangeControlEnvelope(msg276);
			formatted276 = envelope.print();
			envelope.setReceiver("RECEIVER       ");
		} catch (InvalidX12MessageException ixme) {
			System.out.println(ixme.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			assertNotNull(envelope.getReceiver());
			assertTrue(envelope.getReceiver().equals("RECEIVER       "));
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
			assertTrue(exceptionMessage.contains("[Mismatched segment: ISA/IEA]"));
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
			assertTrue(exceptionMessage.contains("[Mismatched segment: GS/GE]"));
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
			assertTrue(exceptionMessage.contains("[Mismatched segment: GS/GE]"));
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
			assertTrue(exceptionMessage.contains("[Mismatched segment: ST/SE]"));
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
			assertTrue(exceptionMessage.contains("[Mismatched segment: ST/SE]"));
		}
	}
}
