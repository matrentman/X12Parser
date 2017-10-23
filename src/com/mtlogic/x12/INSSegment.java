package com.mtlogic.x12;

public class INSSegment extends X12Segment {

	public INSSegment(String data, String delimiter, int segmentIndex, String currentLoop) {
		super(data, delimiter, segmentIndex, currentLoop);
	}
	
	public String toJSONString(Boolean verbose) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(this.getLabel());
		sb.append("\":{\"");
		sb.append(X12Base.INS_RC);
		sb.append("\":\"");
		sb.append(findElement(1, verbose));
		if (this.getElements().length>2) {
			sb.append("\",\"");
			sb.append(X12Base.INS_IRC);
			sb.append("\":\"");
			sb.append(findElement(2, verbose));
		}
		if (this.getElements().length>3) {
			sb.append("\",\"");
			sb.append(X12Base.INS_MTC);
			sb.append("\":\"");
			sb.append(findElement(3, verbose));
		}
		if (this.getElements().length>4) {
			sb.append("\",\"");
			sb.append(X12Base.INS_MRC);
			sb.append("\":\"");
			sb.append(findElement(4, verbose));
		}
		if (this.getElements().length>5) {
			sb.append("\",\"");
			sb.append(X12Base.INS_BSC);
			sb.append("\":\"");
			sb.append(findElement(5, verbose));
		}
		if (this.getElements().length>6) {
			sb.append("\",\"");
			sb.append(X12Base.INS_MSC);
			sb.append("\":\"");
			sb.append(findElement(6, verbose));
		}
		if (this.getElements().length>7) {
			sb.append("\",\"");
			sb.append(X12Base.INS_CQ);
			sb.append("\":\"");
			sb.append(findElement(7, verbose));
		}
		if (this.getElements().length>8) {
			sb.append("\",\"");
			sb.append(X12Base.INS_ESC);
			sb.append("\":\"");
			sb.append(findElement(8, verbose));
		}
		if (this.getElements().length>9) {
			sb.append("\",\"");
			sb.append(X12Base.INS_SSC);
			sb.append("\":\"");
			sb.append(findElement(9, verbose));
		}
		if (this.getElements().length>10) {
			sb.append("\",\"");
			sb.append(X12Base.INS_RC2);
			sb.append("\":\"");
			sb.append(findElement(10, verbose));
		}
		if (this.getElements().length>11) {
			sb.append("\",\"");
			sb.append(X12Base.INS_DTPFQ);
			sb.append("\":\"");
			sb.append(findElement(11, verbose));
		}
		if (this.getElements().length>12) {
			sb.append("\",\"");
			sb.append(X12Base.INS_DTP);
			sb.append("\":\"");
			sb.append(findElement(12, verbose));
		}
		if (this.getElements().length>13) {
			sb.append("\",\"");
			sb.append(X12Base.INS_CONF);
			sb.append("\":\"");
			sb.append(findElement(13, verbose));
		}
		if (this.getElements().length>14) {
			sb.append("\",\"");
			sb.append(X12Base.INS_CN);
			sb.append("\":\"");
			sb.append(findElement(14, verbose));
		}
		if (this.getElements().length>15) {
			sb.append("\",\"");
			sb.append(X12Base.INS_SC);
			sb.append("\":\"");
			sb.append(findElement(15, verbose));
		}
		if (this.getElements().length>16) {
			sb.append("\",\"");
			sb.append(X12Base.INS_CC);
			sb.append("\":\"");
			sb.append(findElement(16, verbose));
		}
		if (this.getElements().length>17) {
			sb.append("\",\"");
			sb.append(X12Base.INS_NUM);
			sb.append("\":\"");
			sb.append(findElement(17, verbose));
		}
		sb.append("\"}");
		return sb.toString();
	}

}
