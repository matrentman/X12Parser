package com.mtlogic.x12;

public class NM1Segment extends X12Segment {

	public NM1Segment(String data, String delimiter, int segmentIndex, String currentLoop) {
		super(data, delimiter, segmentIndex, currentLoop);
	}
	
	public String toJSONString(Boolean verbose) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(this.getLabel());
		sb.append("\":{\"");
		sb.append(X12Base.NM1_EIC);
		sb.append("\":\"");
		sb.append(findElement(1, verbose));
		if (this.getElements().length>2) {
			sb.append("\",\"");
			sb.append(X12Base.NM1_ETQ);
			sb.append("\":\"");
			sb.append(findElement(2, verbose));
		}
		if (this.getElements().length>3) {
			sb.append("\",\"");
			sb.append(X12Base.NM1_NL);
			sb.append("\":\"");
			sb.append(findElement(3, verbose));
		}
		if (this.getElements().length>4) {
			sb.append("\",\"");
			sb.append(X12Base.NM1_NF);
			sb.append("\":\"");
			sb.append(findElement(4, verbose));
		}
		if (this.getElements().length>5) {
			sb.append("\",\"");
			sb.append(X12Base.NM1_NM);
			sb.append("\":\"");
			sb.append(findElement(5, verbose));
		}
		if (this.getElements().length>6) {
			sb.append("\",\"");
			sb.append(X12Base.NM1_NP);
			sb.append("\":\"");
			sb.append(findElement(6, verbose));
		}
		if (this.getElements().length>7) {
			sb.append("\",\"");
			sb.append(X12Base.NM1_NS);
			sb.append("\":\"");
			sb.append(findElement(7, verbose));
		}
		if (this.getElements().length>8) {
			sb.append("\",\"");
			sb.append(X12Base.NM1_ICQ);
			sb.append("\":\"");
			sb.append(findElement(8, verbose));
		}
		if (this.getElements().length>9) {
			sb.append("\",\"");
			sb.append(X12Base.NM1_IC);
			sb.append("\":\"");
			sb.append(findElement(9, verbose));
		
		}
		if (this.getElements().length>10) {
			sb.append("\",\"");
			sb.append(X12Base.NM1_ERC);
			sb.append("\":\"");
			sb.append(findElement(10, verbose));
		}
		if (this.getElements().length>11) {
			sb.append("\",\"");
			sb.append(X12Base.NM1_EIC_2);
			sb.append("\":\"");
			sb.append(findElement(11, verbose));
		}
		if (this.getElements().length>12) {
			sb.append("\",\"");
			sb.append(X12Base.NM1_NL_2);
			sb.append("\":\"");
			sb.append(findElement(12, verbose));
		}
		sb.append("\"}");
		return sb.toString();
	}

}
