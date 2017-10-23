package com.mtlogic.x12;

public class EBSegment extends X12Segment {

	public EBSegment(String data, String delimiter, int segmentIndex, String currentLoop) {
		super(data, delimiter, segmentIndex, currentLoop);
	}
	
	public String toJSONString(Boolean verbose) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(this.getLabel());
		sb.append("\":{\"");
		sb.append(X12Base.EB_EOBIC);
		sb.append("\":\"");
		sb.append(findElement(1, verbose));
		if (this.getElements().length>2) {
			sb.append("\",\"");
			sb.append(X12Base.EB_CLC);
			sb.append("\":\"");
			sb.append(findElement(2, verbose));
		}
		if (this.getElements().length>3) {
			sb.append("\",\"");
			sb.append(X12Base.EB_STC);
			sb.append("\":\"");
			sb.append(findElement(3, verbose));
		}
		if (this.getElements().length>4) {
			sb.append("\",\"");
			sb.append(X12Base.EB_ITC);
			sb.append("\":\"");
			sb.append(findElement(4, verbose));
		}
		if (this.getElements().length>5) {
			sb.append("\",\"");
			sb.append(X12Base.EB_PCD);
			sb.append("\":\"");
			sb.append(findElement(5, verbose));
		}
		if (this.getElements().length>6) {
			sb.append("\",\"");
			sb.append(X12Base.EB_TPQ);
			sb.append("\":\"");
			sb.append(findElement(6, verbose));
		}
		if (this.getElements().length>7) {
			sb.append("\",\"");
			sb.append(X12Base.EB_MA);
			sb.append("\":\"");
			sb.append(findElement(7, verbose));
		}
		if (this.getElements().length>8) {
			sb.append("\",\"");
			sb.append(X12Base.EB_PAD);
			sb.append("\":\"");
			sb.append(findElement(8, verbose));
		}
		if (this.getElements().length>9) {
			sb.append("\",\"");
			sb.append(X12Base.EB_QQ);
			sb.append("\":\"");
			sb.append(findElement(9, verbose));
		}
		if (this.getElements().length>10) {
			sb.append("\",\"");
			sb.append(X12Base.EB_QTY);
			sb.append("\":\"");
			sb.append(findElement(10, verbose));
		}
		if (this.getElements().length>11) {
			sb.append("\",\"");
			sb.append(X12Base.EB_RC);
			sb.append("\":\"");
			sb.append(findElement(11, verbose));
		}
		if (this.getElements().length>12) {
			sb.append("\",\"");
			sb.append(X12Base.EB_RC2);
			sb.append("\":\"");
			sb.append(findElement(12, verbose));
		}
		if (this.getElements().length>13) {
			sb.append("\",\"");
			sb.append(X12Base.EB_CMPI);
			sb.append("\":\"");
			sb.append(findElement(13, verbose));
		}
		if (this.getElements().length>14) {
			sb.append("\",\"");
			sb.append(X12Base.EB_CDCP);
			sb.append("\":\"");
			sb.append(findElement(14, verbose));
		}
		sb.append("\"}");
		return sb.toString();
	}

}
