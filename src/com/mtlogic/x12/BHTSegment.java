package com.mtlogic.x12;

public class BHTSegment extends X12Segment {

	BHTSegment(String data, String delimiter, int segmentIndex, String currentLoop) {
		super(data, delimiter, segmentIndex, currentLoop);
	}
	
	public String toJSONString(Boolean verbose) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(this.getLabel());
		sb.append("\":{\"");
		sb.append(X12Base.BHT_HSC);
		sb.append("\":\"");
		sb.append(findElement(1, verbose));
		if (this.getElements().length>2) {
			sb.append("\",\"");
			sb.append(X12Base.BHT_TSPC);
			sb.append("\":\"");
			sb.append(findElement(2, verbose));
		}
		if (this.getElements().length>3) {
			sb.append("\",\"");
			sb.append(X12Base.BHT_RI);
			sb.append("\":\"");
			sb.append(findElement(3, verbose));
		}
		if (this.getElements().length>4) {
			sb.append("\",\"");
			sb.append(X12Base.BHT_DATE);
			sb.append("\":\"");
			sb.append(findElement(4, verbose));
		}
		if (this.getElements().length>5) {
			sb.append("\",\"");
			sb.append(X12Base.BHT_TIME);
			sb.append("\":\"");
			sb.append(findElement(5, verbose));
		}
		if (this.getElements().length>6) {
			sb.append("\",\"");
			sb.append(X12Base.BHT_TTC);
			sb.append("\":\"");
			sb.append(findElement(6, verbose));
		}
		sb.append("\"}");
		return sb.toString();
	}
	
}