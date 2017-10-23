package com.mtlogic.x12;

public class EQSegment extends X12Segment {

	public EQSegment(String data, String delimiter, int segmentIndex, String currentLoop) {
		super(data, delimiter, segmentIndex, currentLoop);
	}

	public String toJSONString(Boolean verbose) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(this.getLabel());
		sb.append("\":{\"");
		sb.append(X12Base.EQ_STC);
		sb.append("\":\"");
		sb.append(findElement(1, verbose));
		if (this.getElements().length>2) {
			sb.append("\",\"");
			sb.append(X12Base.EQ_CMPI);
			sb.append("\":\"");
			sb.append(findElement(2, verbose));
		}
		if (this.getElements().length>3) {
			sb.append("\",\"");
			sb.append(X12Base.EQ_CLC);
			sb.append("\":\"");
			sb.append(findElement(3, verbose));
		}
		if (this.getElements().length>4) {
			sb.append("\",\"");
			sb.append(X12Base.EQ_ITC);
			sb.append("\":\"");
			sb.append(findElement(4, verbose));
		}
		if (this.getElements().length>5) {
			sb.append("\",\"");
			sb.append(X12Base.EQ_CDCP);
			sb.append("\":\"");
			sb.append(findElement(5, verbose));
		}
		sb.append("\"}");
		return sb.toString();
	}
	
}
