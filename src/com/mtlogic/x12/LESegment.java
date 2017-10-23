package com.mtlogic.x12;

public class LESegment extends X12Segment {

	public LESegment(String data, String delimiter, int segmentIndex, String currentLoop) {
		super(data, delimiter, segmentIndex, currentLoop);
	}

	public String toJSONString(Boolean verbose) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(this.getLabel());
		sb.append("\":{\"");
		sb.append(X12Base.LE_LIC);
		sb.append("\":\"");
		sb.append(findElement(1, verbose));
		sb.append("\"}");
		return sb.toString();
	}
	
}
