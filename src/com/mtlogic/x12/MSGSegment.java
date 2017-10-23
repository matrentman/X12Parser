package com.mtlogic.x12;

public class MSGSegment extends X12Segment {

	public MSGSegment(String data, String delimiter, int segmentIndex, String currentLoop) {
		super(data, delimiter, segmentIndex, currentLoop);
	}

	public String toJSONString(Boolean verbose) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(this.getLabel());
		sb.append("\":{\"");
		sb.append(X12Base.MSG_FFMT);
		sb.append("\":\"");
		sb.append(findElement(1, verbose));
		if (this.getElements().length>2) {
			sb.append("\",\"");
			sb.append(X12Base.MSG_PCCC);
			sb.append("\":\"");
			sb.append(findElement(2, verbose));
		}
		if (this.getElements().length>3) {
			sb.append("\",\"");
			sb.append(X12Base.MSG_NUM);
			sb.append("\":\"");
			sb.append(findElement(3, verbose));
		}
		sb.append("\"}");
		return sb.toString();
	}
	
}
