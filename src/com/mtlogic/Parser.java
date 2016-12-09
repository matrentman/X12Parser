package com.mtlogic;

public class Parser {
	
	public static final int ELEMENT_DELIMITER_PS = 103;
	public static final int SUB_ELEMENT_DELIMITER_PS = 104;
	public static final int SEGMENT_DELIMITER_PS = 105;
	
	public static void main(String args[]) {
		String msg276 = "ISA*00*          *00*          *ZZ*CONSULT        *ZZ*341559999      *151005*0943*^*00501*000004251*1*P*:~GS*HN*CONSULT*341559999*20151005*0943*4251*X*005010X212~ST*277*000000001*005010X212~BHT*0010*08*0918151000*20151005*0943*DG~HL*1**20*1~NM1*PR*2*MEDICAL MUTUAL OF OHIO*****PI*29076~PER*IC*MEDICAL MUTUAL*TE*8003621279~HL*2*1*21*1~NM1*41*2*PATRICK J SKAROTE, MD, INC*****46*341559999~HL*3*2*19*1~NM1*1P*2*PATRICK J SKAROTE MD INC*****FI*550809531~HL*4*3*22*1~NM1*IL*1*TALLMAN*DARYN*C***MI*562912567807~HL*5*4*23~NM1*QC*1*TALLMAN*JENNIFER*E~TRN*1*5660-18903.0-12~STC*F0:1*20150630**248.8*0*20150706**20150706*E451131~REF*1K*5181439338000~REF*D9*062915781124957~DTP*472*RD8*20150604-20150604~SE*17*000000001~GE*1*4251~IEA*1*000004251~";
		
		Parser parser = new Parser();
		X12Message message = parser.parse(msg276);
		message.print();
	}
	
	public X12Message parse(String input) {
		String elementDelimiter;
		String subelementDelimiter;
		String segmentDelimiter;
		
		elementDelimiter = "" + input.charAt(ELEMENT_DELIMITER_PS);
		subelementDelimiter = "" + input.charAt(SUB_ELEMENT_DELIMITER_PS);
		segmentDelimiter = "" + input.charAt(SEGMENT_DELIMITER_PS);
	
		System.out.println("Element Delimiter: " + elementDelimiter);
		System.out.println("Sub Element Delimiter: " + subelementDelimiter);
		System.out.println("Segment Delimiter: " + segmentDelimiter);
		
		System.out.println();
		
		String[] segments = input.split(segmentDelimiter);
		X12Message message = new X12Message(); 
		for (String str : segments) {
			X12Segment segment = new X12Segment(str);
			message.add(segment);
		}
		
		return message;
	}

}
