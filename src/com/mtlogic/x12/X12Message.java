package com.mtlogic.x12;

import java.util.Vector;

import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12Message extends X12Base {
	String elementDelimiter;
	String repetitionDelimiter;
	String subelementDelimiter;
	String segmentDelimiter;
	Vector<X12InterchangeControlEnvelope> interchangeControlList;

	public X12Message(String data) throws InvalidX12MessageException {
		preValidate(data);
		X12InterchangeControlEnvelope interchangeControlEnvelope = new X12InterchangeControlEnvelope(data);
		interchangeControlList = new Vector<X12InterchangeControlEnvelope>();
		interchangeControlList.add(interchangeControlEnvelope);
		postValidate();
	}
	
	public Vector<X12InterchangeControlEnvelope> getInterchangeControlList() {
		return interchangeControlList;
	}

	public void setInterchangeControlList(Vector<X12InterchangeControlEnvelope> interchangeControlList) {
		this.interchangeControlList = interchangeControlList;
	}
	
	public void validate() throws InvalidX12MessageException {
		Vector<String> messages = new Vector<String>();
		for (X12InterchangeControlEnvelope isaEnvelope : this.getInterchangeControlList()) {
			messages.addAll(isaEnvelope.validate());
		}
		if (!messages.isEmpty()) {
			throw new InvalidX12MessageException(formatErrorMessages(messages));
		}
	}
	
	public String print() {
		StringBuffer sb = new StringBuffer();
		
		for (int i=0; i < interchangeControlList.size(); i++) {
			sb.append(interchangeControlList.get(i).print());
		}
		
		return sb.toString();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		for (int i=0; i < interchangeControlList.size(); i++) {
			sb.append(interchangeControlList.get(i).toString());
		}
		
		return sb.toString();
	}
	
	private void preValidate(String data) throws InvalidX12MessageException {
		if (!data.startsWith(ISA)) {
			throw new InvalidX12MessageException("Invalid message: [Missing segment: ISA!]");
		}
	}
	
	private void postValidate() throws InvalidX12MessageException {
		this.validate();
	}
}
