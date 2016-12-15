package com.mtlogic.x12;

import java.util.Vector;

import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12ISAHeader {
	final String name = "ISA";
	String isa01;
	String isa02;
	String isa03;
	String isa04;
	String isa05;
	String isa06;
	String isa07;
	String isa08;
	String isa09;
	String isa10;
	String isa11;
	String isa12;
	String isa13;
	String isa14;
	String isa15;
	String isa16;
	String segmentDelimiter;
	String elementDelimiter;
	String subelementDelimiter;
	
	public X12ISAHeader(String data, String segmentDelimiter, String elementDelimiter, String subelementDelimiter) 
			throws InvalidX12MessageException {
		if (data != null && !data.isEmpty() && data.startsWith(X12Message.ISA) 
				&& segmentDelimiter != null && !segmentDelimiter.isEmpty()) {
			this.segmentDelimiter = segmentDelimiter;
			this.elementDelimiter = elementDelimiter;
			this.subelementDelimiter = subelementDelimiter;
			String[] dataArray;
			if (elementDelimiter.equals("*")) {
				dataArray = data.split("\\*");
			} else {
				dataArray = data.split(segmentDelimiter);
			}
			isa01 = dataArray[1];
			isa02 = dataArray[2];
			isa03 = dataArray[3];
			isa04 = dataArray[4];
			isa05 = dataArray[5];
			isa06 = dataArray[6];
			isa07 = dataArray[7];
			isa08 = dataArray[8];
			isa09 = dataArray[9];
			isa10 = dataArray[10];
			isa11 = dataArray[11];
			isa12 = dataArray[12];
			isa13 = dataArray[13];
			isa14 = dataArray[14];
			isa15 = dataArray[15];
			isa16 = dataArray[16];
		} else {
			throw new InvalidX12MessageException("Unable to parse ISA segment!");
		}
	}
	
	public String getIsa01() {
		return isa01;
	}
	
	public void setIsa01(String isa01) {
		this.isa01 = isa01;
	}
	
	public String getIsa02() {
		return isa02;
	}
	
	public void setIsa02(String isa02) {
		this.isa02 = isa02;
	}
	
	public String getIsa03() {
		return isa03;
	}
	
	public void setIsa03(String isa03) {
		this.isa03 = isa03;
	}
	
	public String getIsa04() {
		return isa04;
	}
	
	public void setIsa04(String isa04) {
		this.isa04 = isa04;
	}
	
	public String getIsa05() {
		return isa05;
	}
	
	public void setIsa05(String isa05) {
		this.isa05 = isa05;
	}
	
	public String getIsa06() {
		return isa06;
	}
	
	public void setIsa06(String isa06) {
		this.isa06 = isa06;
	}
	
	public String getIsa07() {
		return isa07;
	}
	
	public void setIsa07(String isa07) {
		this.isa07 = isa07;
	}
	
	public String getIsa08() {
		return isa08;
	}
	
	public void setIsa08(String isa08) {
		this.isa08 = isa08;
	}
	
	public String getIsa09() {
		return isa09;
	}
	
	public void setIsa09(String isa09) {
		this.isa09 = isa09;
	}
	
	public String getIsa10() {
		return isa10;
	}
	
	public void setIsa10(String isa10) {
		this.isa10 = isa10;
	}
	
	public String getIsa11() {
		return isa11;
	}
	
	public void setIsa11(String isa11) {
		this.isa11 = isa11;
	}
	
	public String getIsa12() {
		return isa12;
	}
	
	public void setIsa12(String isa12) {
		this.isa12 = isa12;
	}
	
	public String getIsa13() {
		return isa13;
	}
	
	public void setIsa13(String isa13) {
		this.isa13 = isa13;
	}
	
	public String getIsa14() {
		return isa14;
	}
	
	public void setIsa14(String isa14) {
		this.isa14 = isa14;
	}
	
	public String getIsa15() {
		return isa15;
	}
	
	public void setIsa15(String isa15) {
		this.isa15 = isa15;
	}
	
	public String getIsa16() {
		return isa16;
	}
	
	public void setIsa16(String isa16) {
		this.isa16 = isa16;
	}
	
	public String getSegmentDelimiter() {
		return segmentDelimiter;
	}
	
	public void setSegmentDelimiter(String segmentDelimiter) {
		this.segmentDelimiter = segmentDelimiter;
	}
	
	public String getElementDelimiter() {
		return elementDelimiter;
	}
	
	public void setElementDelimiter(String elementDelimiter) {
		this.elementDelimiter = elementDelimiter;
	}
	
	public String getSubelementDelimiter() {
		return subelementDelimiter;
	}
	
	public void setSubelementDelimiter(String subelementDelimiter) {
		this.subelementDelimiter = subelementDelimiter;
	}
	
	public String getName() {
		return name;
	}
	
	public String print() {
		return toString() + "\n";
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(this.name);
		sb.append(elementDelimiter);
		sb.append(this.isa01);
		sb.append(elementDelimiter);
		sb.append(this.isa02);
		sb.append(elementDelimiter);
		sb.append(this.isa03);
		sb.append(elementDelimiter);
		sb.append(this.isa04);
		sb.append(elementDelimiter);
		sb.append(this.isa05);
		sb.append(elementDelimiter);
		sb.append(this.isa06);
		sb.append(elementDelimiter);
		sb.append(this.isa07);
		sb.append(elementDelimiter);
		sb.append(this.isa08);
		sb.append(elementDelimiter);
		sb.append(this.isa09);
		sb.append(elementDelimiter);
		sb.append(this.isa10);
		sb.append(elementDelimiter);
		sb.append(this.isa11);
		sb.append(elementDelimiter);
		sb.append(this.isa12);
		sb.append(elementDelimiter);
		sb.append(this.isa13);
		sb.append(elementDelimiter);
		sb.append(this.isa14);
		sb.append(elementDelimiter);
		sb.append(this.isa15);
		sb.append(elementDelimiter);
		sb.append(this.isa16);
		sb.append(segmentDelimiter);
		
		return sb.toString();
	}
	
	public Vector<String> validate()  {
		Vector<String> messages = new Vector<String>();
		
		if (isa01 == null || isa01.isEmpty()) {
			messages.add("Missing field: ISA01!");
		}
		if (isa02 == null || isa02.isEmpty()) {
			messages.add("Missing field: ISA02!");
		}
		if (isa03 == null || isa03.isEmpty()) {
			messages.add("Missing field: ISA03!");
		}
		if (isa04 == null || isa04.isEmpty()) {
			messages.add("Missing field: ISA04!");
		}
		if (isa05 == null || isa05.isEmpty()) {
			messages.add("Missing field: ISA05!");
		}
		if (isa06 == null || isa06.isEmpty()) {
			messages.add("Missing field: ISA06!");
		}
		if (isa07 == null || isa07.isEmpty()) {
			messages.add("Missing field: ISA07!");
		}
		if (isa08 == null || isa08.isEmpty()) {
			messages.add("Missing field: ISA08!");
		}
		if (isa09 == null || isa09.isEmpty()) {
			messages.add("Missing field: ISA09!");
		}
		if (isa10 == null || isa10.isEmpty()) {
			messages.add("Missing field: ISA10!");
		}
		if (isa11 == null || isa11.isEmpty()) {
			messages.add("Missing field: ISA11!");
		}
		if (isa12 == null || isa12.isEmpty()) {
			messages.add("Missing field: ISA12!");
		}
		if (isa13 == null || isa13.isEmpty()) {
			messages.add("Missing field: ISA13!");
		}
		if (isa14 == null || isa14.isEmpty()) {
			messages.add("Missing field: ISA14!");
		}
		if (isa15 == null || isa15.isEmpty()) {
			messages.add("Missing field: ISA15!");
		}
		if (isa16 == null || isa16.isEmpty()) {
			messages.add("Missing field: ISA16!");
		}
		
		if (isa01 != null && isa01.length() != 2) {
			messages.add("Invalid field length: ISA01!");
		}
		if (isa02 != null && isa02.length() != 10) {
			messages.add("Invalid field length: ISA02!");
		}
		if (isa03 != null && isa03.length() != 2) {
			messages.add("Invalid field length: ISA03!");
		}
		if (isa04 != null && isa04.length() != 10) {
			messages.add("Invalid field length: ISA04!");
		}
		if (isa05 != null && isa05.length() != 2) {
			messages.add("Invalid field length: ISA05!");
		}
		if (isa06 != null && isa06.length() != 15) {
			messages.add("Invalid field length: ISA06!");
		}
		if (isa07 != null && isa07.length() != 2) {
			messages.add("Invalid field length: ISA07!");
		}
		if (isa08 != null && isa08.length() != 15) {
			messages.add("Invalid field length: ISA08!");
		}
		if (isa09 != null && isa09.length() != 6) {
			messages.add("Invalid field length: ISA09!");
		}
		if (isa10 != null && isa10.length() != 4) {
			messages.add("Invalid field length: ISA10!");
		}
		if (isa11 != null && isa11.length() != 1) {
			messages.add("Invalid field length: ISA11!");
		}
		if (isa12 != null && isa12.length() != 5) {
			messages.add("Invalid field length: ISA12!");
		}
		if (isa13 != null && isa13.length() != 9) {
			messages.add("Invalid field length: ISA13!");
		}
		if (isa14 != null && isa14.length() != 1) {
			messages.add("Invalid field length: ISA14!");
		}
		if (isa15 != null && isa15.length() != 1) {
			messages.add("Invalid field length: ISA15!");
		}
		if (isa16 != null && isa16.length() != 1) {
			messages.add("Invalid field length: ISA16!");
		}
		
		return messages;
	}
	
}
