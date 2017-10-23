package com.mtlogic.x12;

public class HISegment extends X12Segment {

	public HISegment(String data, String delimiter, int segmentIndex, String currentLoop) {
		super(data, delimiter, segmentIndex, currentLoop);
	}
	
	public String toJSONString(Boolean verbose) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(this.getLabel());
		sb.append("\":{\"");
		sb.append(X12Base.HI_1);
		sb.append("\":\"");
		sb.append(findElement(1, verbose));
		if (this.getElements().length>2) {
			sb.append("\",\"");
			sb.append(X12Base.HI_2);
			sb.append("\":\"");
			sb.append(findElement(2, verbose));
		}
		if (this.getElements().length>3) {
			sb.append("\",\"");
			sb.append(X12Base.HI_3);
			sb.append("\":\"");
			sb.append(findElement(3, verbose));
		}
		if (this.getElements().length>4) {
			sb.append("\",\"");
			sb.append(X12Base.HI_4);
			sb.append("\":\"");
			sb.append(findElement(4, verbose));
		}
		if (this.getElements().length>5) {
			sb.append("\",\"");
			sb.append(X12Base.HI_5);
			sb.append("\":\"");
			sb.append(findElement(5, verbose));
		}
		if (this.getElements().length>6) {
			sb.append("\",\"");
			sb.append(X12Base.HI_6);
			sb.append("\":\"");
			sb.append(findElement(6, verbose));
		}
		if (this.getElements().length>7) {
			sb.append("\",\"");
			sb.append(X12Base.HI_7);
			sb.append("\":\"");
			sb.append(findElement(7, verbose));
		}
		if (this.getElements().length>8) {
			sb.append("\",\"");
			sb.append(X12Base.HI_8);
			sb.append("\":\"");
			sb.append(findElement(8, verbose));
		}
		if (this.getElements().length>9) {
			sb.append("\",\"");
			sb.append(X12Base.HI_9);
			sb.append("\":\"");
			sb.append(findElement(9, verbose));
		}
		if (this.getElements().length>10) {
			sb.append("\",\"");
			sb.append(X12Base.HI_10);
			sb.append("\":\"");
			sb.append(findElement(10, verbose));
		}
		if (this.getElements().length>11) {
			sb.append("\",\"");
			sb.append(X12Base.HI_11);
			sb.append("\":\"");
			sb.append(findElement(11, verbose));
		}
		if (this.getElements().length>12) {
			sb.append("\",\"");
			sb.append(X12Base.HI_12);
			sb.append("\":\"");
			sb.append(findElement(12, verbose));
		}
		sb.append("\"}");
		return sb.toString();
	}

}
