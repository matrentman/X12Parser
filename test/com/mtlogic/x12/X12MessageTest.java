package com.mtlogic.x12;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12MessageTest {
	String msg276 = "ISA*00*          *00*          *ZZ*341559999      *ZZ*CONSULT        *151005*1043*^*00501*000004251*0*P*:~GS*HR*341559999*CONSULT*20151005*1043*4251*X*005010X212~ST*276*000000001*005010X212~BHT*0010*13*0918151000*20151005*1043~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN****MI*562912567807~HL*5*4*23~DMG*D8*19780729*F~NM1*QC*1*TALLMAN*JENNIFER~TRN*1*5660-18903.0-12~AMT*T3*0~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~IEA*1*000004251~";
	
	@Test
    public void testHello() {
		try {
			X12Message message = new X12Message(msg276);
			String parsed276 = message.toString();
			assertEquals(parsed276, msg276);
		} catch (InvalidX12MessageException ixme) {
			System.out.println(ixme.getMessage());
		}
    }
}
