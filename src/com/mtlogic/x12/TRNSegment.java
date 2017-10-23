package com.mtlogic.x12;

public class TRNSegment extends X12Segment {

	public TRNSegment(String data, String delimiter, int segmentIndex, String currentLoop) {
		super(data, delimiter, segmentIndex, currentLoop);
	}

	public String toJSONString(Boolean verbose) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(this.getLabel());
		sb.append("\":{\"");
		sb.append(X12Base.TRN_TTC);
		sb.append("\":\"");
		sb.append(findElement(1, verbose));
		if (this.getElements().length>2) {
			sb.append("\",\"");
			sb.append(X12Base.TRN_RI);
			sb.append("\":\"");
			sb.append(findElement(2, verbose));
		}
		if (this.getElements().length>3) {
			sb.append("\",\"");
			sb.append(X12Base.TRN_OCI);
			sb.append("\":\"");
			sb.append(findElement(3, verbose));
		}
		if (this.getElements().length>4) {
			sb.append("\",\"");
			sb.append(X12Base.TRN_RI_2);
			sb.append("\":\"");
			sb.append(findElement(4, verbose));
		}
		sb.append("\"}");
		return sb.toString();
	}
	
}
