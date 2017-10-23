package com.mtlogic.x12;

public class HSDSegment extends X12Segment {

	public HSDSegment(String data, String delimiter, int segmentIndex, String currentLoop) {
		super(data, delimiter, segmentIndex, currentLoop);
	}

	public String toJSONString(Boolean verbose) {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(this.getLabel());
		sb.append("\":{\"");
		sb.append(X12Base.HSD_QQ);
		sb.append("\":\"");
		sb.append(findElement(1, verbose));
		if (this.getElements().length>2) {
			sb.append("\",\"");
			sb.append(X12Base.HSD_QTY);
			sb.append("\":\"");
			sb.append(findElement(2, verbose));
		}
		if (this.getElements().length>3) {
			sb.append("\",\"");
			sb.append(X12Base.HSD_UOBOMC);
			sb.append("\":\"");
			sb.append(findElement(3, verbose));
		}
		if (this.getElements().length>4) {
			sb.append("\",\"");
			sb.append(X12Base.HSD_SSM);
			sb.append("\":\"");
			sb.append(findElement(4, verbose));
		}
		if (this.getElements().length>5) {
			sb.append("\",\"");
			sb.append(X12Base.HSD_TPQ);
			sb.append("\":\"");
			sb.append(findElement(5, verbose));
		}
		if (this.getElements().length>6) {
			sb.append("\",\"");
			sb.append(X12Base.HSD_NOP);
			sb.append("\":\"");
			sb.append(findElement(6, verbose));
		}
		if (this.getElements().length>7) {
			sb.append("\",\"");
			sb.append(X12Base.HSD_SDOCPC);
			sb.append("\":\"");
			sb.append(findElement(7, verbose));
		}
		if (this.getElements().length>8) {
			sb.append("\",\"");
			sb.append(X12Base.HSD_SDPTC);
			sb.append("\":\"");
			sb.append(findElement(8, verbose));
		}
		sb.append("\"}");
		return sb.toString();
	}
	
}
