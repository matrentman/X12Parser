package com.mtlogic.x12;

public class DMGSegment extends X12Segment {

	public DMGSegment(String data, String delimiter, int segmentIndex, String currentLoop) {
		super(data, delimiter, segmentIndex, currentLoop);
	}
	
	public String toJSONString(Boolean verbose) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(this.getLabel());
		sb.append("\":{\"");
		sb.append(X12Base.DMG_DTPFQ);
		sb.append("\":\"");
		sb.append(findElement(1, verbose));
		if (this.getElements().length>2) {
			sb.append("\",\"");
			sb.append(X12Base.DMG_DTP);
			sb.append("\":\"");
			sb.append(findElement(2, verbose));
		}
		if (this.getElements().length>3) {
			sb.append("\",\"");
			sb.append(X12Base.DMG_GC);
			sb.append("\":\"");
			sb.append(findElement(3, verbose));
		}
		if (this.getElements().length>4) {
			sb.append("\",\"");
			sb.append(X12Base.DMG_MSC);
			sb.append("\":\"");
			sb.append(findElement(4, verbose));
		}
		if (this.getElements().length>5) {
			sb.append("\",\"");
			sb.append(X12Base.DMG_CR);
			sb.append("\":\"");
			sb.append(findElement(5, verbose));
		}
		if (this.getElements().length>6) {
			sb.append("\",\"");
			sb.append(X12Base.DMG_CSC);
			sb.append("\":\"");
			sb.append(findElement(6, verbose));
		}
		if (this.getElements().length>7) {
			sb.append("\",\"");
			sb.append(X12Base.DMG_CC);
			sb.append("\":\"");
			sb.append(findElement(7, verbose));
		}
		if (this.getElements().length>8) {
			sb.append("\",\"");
			sb.append(X12Base.DMG_BOVC);
			sb.append("\":\"");
			sb.append(findElement(8, verbose));
		}
		if (this.getElements().length>9) {
			sb.append("\",\"");
			sb.append(X12Base.DMG_QNT);
			sb.append("\":\"");
			sb.append(findElement(9, verbose));
		}
		if (this.getElements().length>10) {
			sb.append("\",\"");
			sb.append(X12Base.DMG_CLQC);
			sb.append("\":\"");
			sb.append(findElement(10, verbose));
		}
		if (this.getElements().length>11) {
			sb.append("\",\"");
			sb.append(X12Base.DMG_IC);
			sb.append("\":\"");
			sb.append(findElement(11, verbose));
		}
		sb.append("\"}");
		return sb.toString();
	}

}
