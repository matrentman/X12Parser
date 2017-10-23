package com.mtlogic.x12;

public class N3Segment extends X12Segment {

	public N3Segment(String data, String delimiter, int segmentIndex, String currentLoop) {
		super(data, delimiter, segmentIndex, currentLoop);
	}

	public String toJSONString(Boolean verbose) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(this.getLabel());
		sb.append("\":{\"");
		sb.append(X12Base.N3_AI);
		sb.append("\":\"");
		sb.append(findElement(1, verbose));
		if (this.getElements().length>2) {
			sb.append("\",\"");
			sb.append(X12Base.N3_AI_2);
			sb.append("\":\"");
			sb.append(findElement(2, verbose));
		}
		sb.append("\"}");
		return sb.toString();
	}
	
}
