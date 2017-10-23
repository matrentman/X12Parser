package com.mtlogic.x12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Vector;

import org.junit.Test;

import com.mtlogic.x12.X12Message;
import com.mtlogic.x12.X12Segment;
import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12MessageTest {
	String msg276 = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*1P*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~IEA*1*000004251~";
	String msg270 = "ISA*00*          *00*          *12*ABCCOM         *01*999999999      *120117*1719*^*00500*000006768*0*P*|~GS*HS*4405197800*999999999*20120117*1719*1421*X*004010VICS~ST*270*1234~BHT*0022*13*1*20010820*1330~HL*1**20*1~NM1*PR*2******PI*123456789~HL*2*1*21*1~NM1*1P*2******SV*987654321~HL*3*2*22*0~NM1*IL*1*DOE*JANE****MI*345678901~EQ*30**FAM~SE*10*1234~GE*1*1421~IEA*1*000006768~";
	String msg276WithMissingISASegment = "GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~IEA*1*000004251~";
	String msg276WithMissingIEASegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~";
	String msg276WithMissingGSSegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~IEA*1*000004251~";
	String msg276WithMissingGESegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~IEA*1*000004251~";
	String msg276WithMissingSTSegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~IEA*1*000004251~";
	String msg276WithMissingSESegment = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~GE*1*4251~IEA*1*000004251~";
	String jsonMsg276 = "{\"ISA\":{\"authorization_information_qualifier\":\"00\",\"authorization_information\":\"          \",\"security_information_qualifier\":\"00\",\"security_information\":\"          \",\"interchange_sender_id_qualifier\":\"ZZ\",\"interchange_sender_id\":\"341559999      \",\"interchange_receiver_id_qualifier\":\"ZZ\",\"interchange_receiver_id\":\"CONSULT        \",\"interchange_date\":\"151005\",\"interchange_time\":\"1043\",\"repetition_separator\":\"^\",\"interchange_control_verison_number\":\"00501\",\"acknowledgement_requested\":\"000004251\",\"interchange_usage_indicator\":\"0\",\"component_element_separator\":\"P\",\"segment_element_separator\":\"|\"},\"GS\":{\"functional_identifier_code\":\"HR\",\"application_sender_code\":\"341559999\",\"application_receiver_code\":\"CONSULT\",\"date\":\"20151005\",\"time\":\"1043\",\"group_control_number\":\"4251\",\"responsible_agency_code\":\"X\",\"version\":\"005010X212\"},\"ST\":{\"transaction_set_identifier_code\":\"276\",\"transaction_set_control_number\":\"000000001\",\"implementation_convention_reference\":\"005010X212\"},\"BHT_0\":{\"hierarchical_structure_code\":\"0010\",\"transaction_set_purpose_code\":\"13\",\"reference_identification\":\"0918151000\",\"date\":\"20151005\",\"time\":\"1043\"},\"2000A_0\":{\"HL_0\":{\"hierarchical_id_number\":\"1\",\"hierarchical_parent_number\":\"\",\"hierarchical_level_code\":\"20\",\"hierarchical_level_code_2\":\"1\"},\"2100A_0\":{\"NM1_0\":{\"entity_identifier_code\":\"PR\",\"entity_type_qualifier\":\"2\",\"name_last\":\"MEDICAL MUTUAL OF OHIO\",\"name_first\":\"\",\"name_middle\":\"\",\"name_prefix\":\"\",\"name_suffix\":\"\",\"identification_code_qualifier\":\"PI\",\"identification_code\":\"29076\"}}},\"2000B_0\":{\"HL_1\":{\"hierarchical_id_number\":\"2\",\"hierarchical_parent_number\":\"1\",\"hierarchical_level_code\":\"21\",\"hierarchical_level_code_2\":\"1\"}XXX}},\"2000B_1\":{\"HL_2\":{\"hierarchical_id_number\":\"3\",\"hierarchical_parent_number\":\"2\",\"hierarchical_level_code\":\"19\",\"hierarchical_level_code_2\":\"1\"},\"2100B_0\":{\"NM1_2\":{\"entity_identifier_code\":\"1P\",\"entity_type_qualifier\":\"2\",\"name_last\":\"PATRICK J SKAROTE MD INC\",\"name_first\":\"\",\"name_middle\":\"\",\"name_prefix\":\"\",\"name_suffix\":\"\",\"identification_code_qualifier\":\"FI\",\"identification_code\":\"550809531\"}}},\"2000C_0\":{\"HL_3\":{\"hierarchical_id_number\":\"4\",\"hierarchical_parent_number\":\"3\",\"hierarchical_level_code\":\"22\",\"hierarchical_level_code_2\":\"1\"},\"2100C_0\":{\"NM1_3\":{\"entity_identifier_code\":\"IL\",\"entity_type_qualifier\":\"1\",\"name_last\":\"TALLMAN\",\"name_first\":\"DARYN\",\"name_middle\":\"\",\"name_prefix\":\"\",\"name_suffix\":\"\",\"identification_code_qualifier\":\"MI\",\"identification_code\":\"562912567807\"}}},\"GE\":{\"number_of_transaction_sets\":\"1\",\"group_control_number\":\"4251\"},\"IEA\":{\"number_of_functional_groups\":\"1\",\"interchange_control_number\":\"000004251\"}}";
	String jsonMsg271 = "{\"ISA\":{\"authorization_information_qualifier\":\"00\",\"authorization_information\":\"          \",\"security_information_qualifier\":\"00\",\"security_information\":\"          \",\"interchange_sender_id_qualifier\":\"12\",\"interchange_sender_id\":\"ABCCOM         \",\"interchange_receiver_id_qualifier\":\"01\",\"interchange_receiver_id\":\"999999999      \",\"interchange_date\":\"120117\",\"interchange_time\":\"1719\",\"repetition_separator\":\"^\",\"interchange_control_verison_number\":\"00500\",\"acknowledgement_requested\":\"000006768\",\"interchange_usage_indicator\":\"0\",\"component_element_separator\":\"P\",\"segment_element_separator\":\"|\"},\"GS\":{\"functional_identifier_code\":\"HS\",\"application_sender_code\":\"4405197800\",\"application_receiver_code\":\"999999999\",\"date\":\"20120117\",\"time\":\"1719\",\"group_control_number\":\"1421\",\"responsible_agency_code\":\"X\",\"version\":\"004010VICS\"},\"ST\":{\"transaction_set_identifier_code\":\"270\",\"transaction_set_control_number\":\"1234\",\"implementation_convention_reference\":\"null\"},\"BHT_0\":{\"hierarchical_structure_code\":\"0022\",\"transaction_set_purpose_code\":\"13\",\"reference_identification\":\"1\",\"date\":\"20010820\",\"time\":\"1330\"},\"2000A_0\":{\"HL_0\":{\"hierarchical_id_number\":\"1\",\"hierarchical_parent_number\":\"\",\"hierarchical_level_code\":\"20\",\"hierarchical_level_code_2\":\"1\"},\"2100A_0\":{\"NM1_0\":{\"entity_identifier_code\":\"PR\",\"entity_type_qualifier\":\"2\",\"name_last\":\"\",\"name_first\":\"\",\"name_middle\":\"\",\"name_prefix\":\"\",\"name_suffix\":\"\",\"identification_code_qualifier\":\"PI\",\"identification_code\":\"123456789\"}}},\"2000B_0\":{\"HL_1\":{\"hierarchical_id_number\":\"2\",\"hierarchical_parent_number\":\"1\",\"hierarchical_level_code\":\"21\",\"hierarchical_level_code_2\":\"1\"},\"2100B_0\":{\"NM1_1\":{\"entity_identifier_code\":\"1P\",\"entity_type_qualifier\":\"2\",\"name_last\":\"\",\"name_first\":\"\",\"name_middle\":\"\",\"name_prefix\":\"\",\"name_suffix\":\"\",\"identification_code_qualifier\":\"SV\",\"identification_code\":\"987654321\"}}},\"2000C_0\":{\"HL_2\":{\"hierarchical_id_number\":\"3\",\"hierarchical_parent_number\":\"2\",\"hierarchical_level_code\":\"22\",\"hierarchical_level_code_2\":\"0\"},\"2100C_0\":{\"NM1_2\":{\"entity_identifier_code\":\"IL\",\"entity_type_qualifier\":\"1\",\"name_last\":\"DOE\",\"name_first\":\"JANE\",\"name_middle\":\"\",\"name_prefix\":\"\",\"name_suffix\":\"\",\"identification_code_qualifier\":\"MI\",\"identification_code\":\"345678901\"}}},\"GE\":{\"number_of_transaction_sets\":\"1\",\"group_control_number\":\"1421\"},\"IEA\":{\"number_of_functional_groups\":\"1\",\"interchange_control_number\":\"000006768\"}}";
	
	@Test
	public void testToStringShouldEqualOriginalMessageString() {
		String parsed276 = null;
		X12Message message = null;
		try {
			message = new X12Message(msg276);
			parsed276 = message.toString();
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
	public void testToJSONStringShouldProduceValidJSON() {
		String parsedJSON276 = null;
		X12Message message = null;
		
		try {
			message = new X12Message(msg270);
			
			Vector<X12Segment> segments = (Vector<X12Segment>) message.getInterchangeControlList().get(0).getDataSegments();
			for (X12Segment segment : segments) {
				System.out.println(segment.getLoopIdentifier());
				System.out.println(segment.toString());
			}
			
			parsedJSON276 = message.toJSONString(Boolean.TRUE);
		} catch (InvalidX12MessageException ixme) {
			System.out.println(ixme.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			assertNotNull(parsedJSON276);
			//assertEquals(parsedJSON276, jsonMsg276);
			assertEquals(parsedJSON276, jsonMsg271);
		}
	}
	
	@Test
	public void testPrintShouldContainOriginalMessageSegments() {
		String formatted276 = null;
		String[] segments = null;
		X12Message message = null;
		try {
			message = new X12Message(msg276);
			formatted276 = message.print();
		} catch (InvalidX12MessageException ixme) {
			System.out.println(ixme.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			segments = msg276.replace(':', '|').split("~");
			for (String segment : segments) {
				assertTrue(formatted276.contains(segment));
			}
		}
	}
	
	@Test
	public void testPrintShouldContainIndividualParsedSegments() {
		String formatted276 = null;
		X12Message message = null;
		try {
			message = new X12Message(msg276);
			formatted276 = message.print();
		} catch (InvalidX12MessageException ixme) {
			System.out.println(ixme.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			assertTrue(formatted276.contains(message.getInterchangeControlList().get(0).getIsaHeader().toString()));
			assertTrue(formatted276.contains(message.getInterchangeControlList().get(0).getIeaTrailer().toString()));
			assertTrue(formatted276.contains(message.getInterchangeControlList().get(0).getFunctionalGroupEnvelopes().get(0).getGsHeader().toString()));
			assertTrue(formatted276.contains(message.getInterchangeControlList().get(0).getFunctionalGroupEnvelopes().get(0).getGeTrailer().toString()));
			assertTrue(formatted276.contains(message.getInterchangeControlList().get(0).getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().get(0).getStHeader().toString()));
			assertTrue(formatted276.contains(message.getInterchangeControlList().get(0).getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().get(0).getSeTrailer().toString()));
			Vector<X12Segment> segments = message.getInterchangeControlList().get(0).getFunctionalGroupEnvelopes().get(0).getTransactionSetEnvelopes().get(0).getSegments();
			for (X12Segment segment : segments) {
				assertTrue(formatted276.contains(segment.toString()));
			}
		}
	}

	@Test
	public void testParsedMessageStringWithMissingISASegmentShouldThrowException() {
		String exceptionMessage = null;
		X12Message message = null;
		try {
			message = new X12Message(msg276WithMissingISASegment);
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
		X12Message message = null;
		try {
			message = new X12Message(msg276WithMissingIEASegment);
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
		X12Message message = null;
		try {
			message = new X12Message(msg276WithMissingGSSegment);
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
		X12Message message = null;
		try {
			message = new X12Message(msg276WithMissingGESegment);
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
