package com.mtlogic;

public class Main {
	
	public static final int ELEMENT_DELIMITER_PS = 103;
	public static final int SUB_ELEMENT_DELIMITER_PS = 104;
	public static final int SEGMENT_DELIMITER_PS = 105;
	
	public static void main(String args[]) {
		String msg276 = "ISA*00*          *00*          *ZZ*CONSULT        *ZZ*341559999      *151005*0943*^*00501*000004251*1*P*:~GS*HN*CONSULT*341559999*20151005*0943*4251*X*005010X212~ST*277*000000001*005010X212~BHT*0010*08*0918151000*20151005*0943*DG~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~PER*IC*MEDICAL MUTUAL*TE*8003621279~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN*C***MI*562912567807~HL*5*4*23~NM1*QC*1*TALLMAN*JENNIFER*E~TRN*1*5660-18903.0-12~STC*F0:1*20150630**248.8*0*20150706**20150706*E451131~REF*1K*5181439338000~REF*D9*062915781124957~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~IEA*1*000004251~";
		
		X12Message x12Message = new X12Message(msg276);

		if (x12Message.validate()) {		
			System.out.println("\nResult of calling X12Message.print()...");
			x12Message.print();
			
			System.out.println("\nResult of calling X12Message.toString()...");
			System.out.println(x12Message.toString());
		} else {
			System.out.println("Invalid message!!!");
		}
		
		if(msg276.equals(x12Message.toString())) {
			System.out.println("Hooray! Generated correct X12 message!!!");
		} else {
			System.out.println("Booooo! Could not Generate correct X12 message!!!");
		}
	}
	
}
