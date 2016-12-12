package com.mtlogic;

public class X12Segment {
	private String name;
	private String subName;
	private String seperator;
	private String[] elements;
	
	X12Segment(String data, String seperator) {
		this.seperator = seperator;
		if (seperator.equals("*")) {
			elements = data.split("\\*");
		} else {
			elements = data.split(seperator);
		}
		this.name = elements[0];
		this.subName = elements[1];
		this.name = elements[0];
		this.subName = elements[1];
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}
	
	public String[] getElements() {
		return elements;
	}

	public void setElements(String[] elements) {
		this.elements = elements;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < elements.length - 1; i++) {
			sb.append(elements[i]);
			sb.append(seperator);
		}
		sb.append(elements[elements.length - 1]);
		return sb.toString();
	}
	
}
