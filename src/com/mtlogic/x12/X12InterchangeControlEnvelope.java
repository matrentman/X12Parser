package com.mtlogic.x12;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.mtlogic.x12.exception.InvalidX12MessageException;

public class X12InterchangeControlEnvelope extends X12Base {
	X12ISAHeader isaHeader;
	X12IEATrailer ieaTrailer;
	Vector<X12FunctionalGroupEnvelope> functionalGroupEnvelopes;
	
	private HashMap<String, Integer> segmentMap;
	private String currentLoop = null;
	private String previousLoop = null;
	private String parentLoop = null;
	private Boolean dependentFlag = false;
	private Boolean ebFlag = false;
	private Boolean payerFlag = false;
	private Boolean providerFlag = false;
	private Boolean receiverFlag = false;
	private Boolean submitterFlag = false;
	private Boolean subscriberFlag = false;
	
	
	public X12InterchangeControlEnvelope(String data) throws InvalidX12MessageException {
		
		int last2110CIndex = 0;
		
		data = this.replaceDelimiters(data);
		
		functionalGroupEnvelopes = new Vector<X12FunctionalGroupEnvelope>();
		X12FunctionalGroupEnvelope gsEnvelope = null;
		X12TransactionSetEnvelope stEnvelope = null;
		if (!data.startsWith(ISA)) {
			throw new InvalidX12MessageException("Invalid message: [Missing segment: ISA!]");
		}
		
		String[] parsedSegments = data.split("" + ECP_STANDARD_SEGMENT_DELIMITER);
		validateEnvelopes(parsedSegments);
		
		if ((parsedSegments[0].startsWith(ISA + ECP_STANDARD_ELEMENT_DELIMITER))) {
			X12Segment segment = null;
			for (String str : parsedSegments) {
	            if (str.startsWith(ISA + ECP_STANDARD_ELEMENT_DELIMITER)) {
	            	isaHeader = new X12ISAHeader(str, "" + ECP_STANDARD_SEGMENT_DELIMITER, "" + ECP_STANDARD_ELEMENT_DELIMITER, "" + ECP_STANDARD_SUBELEMENT_DELIMITER);
	            } else if (str.startsWith(IEA + ECP_STANDARD_ELEMENT_DELIMITER)) {
	            	ieaTrailer = new X12IEATrailer(str, "" + ECP_STANDARD_SEGMENT_DELIMITER, "" + ECP_STANDARD_ELEMENT_DELIMITER, "" + ECP_STANDARD_SUBELEMENT_DELIMITER);
	            } else if (str.startsWith(GS + ECP_STANDARD_ELEMENT_DELIMITER)) {
	            	gsEnvelope = new X12FunctionalGroupEnvelope();
	            	X12GSHeader gsHeader = new X12GSHeader(str, "" + ECP_STANDARD_SEGMENT_DELIMITER, "" + ECP_STANDARD_ELEMENT_DELIMITER, "" + ECP_STANDARD_SUBELEMENT_DELIMITER);
	            	gsEnvelope.setGsHeader(gsHeader);
	            } else if (str.startsWith(GE + ECP_STANDARD_ELEMENT_DELIMITER)) {
	            	if (gsEnvelope != null) {
		            	X12GETrailer geTrailer = new X12GETrailer(str, "" + ECP_STANDARD_SEGMENT_DELIMITER, "" + ECP_STANDARD_ELEMENT_DELIMITER, "" + ECP_STANDARD_SUBELEMENT_DELIMITER);
		            	gsEnvelope.setGeTrailer(geTrailer);
		            	gsEnvelope.getTransactionSetEnvelopes().add(stEnvelope);
		            	functionalGroupEnvelopes.add(gsEnvelope);
	            	} else	{
	            		throw new InvalidX12MessageException("Invalid message: [Missing segment: GS!]");
	            	}
	            } else if (str.startsWith(ST + ECP_STANDARD_ELEMENT_DELIMITER)) {
	            	stEnvelope = new X12TransactionSetEnvelope();
	            	X12STHeader stHeader = new X12STHeader(str, "" + ECP_STANDARD_SEGMENT_DELIMITER, "" + ECP_STANDARD_ELEMENT_DELIMITER, "" + ECP_STANDARD_SUBELEMENT_DELIMITER, this.isaHeader.getVersion());
	            	stEnvelope.setStHeader(stHeader);
	            } else if (str.startsWith(SE + ECP_STANDARD_ELEMENT_DELIMITER)) {
	            	X12SETrailer seTrailer = new X12SETrailer(str, "" + ECP_STANDARD_SEGMENT_DELIMITER, "" + ECP_STANDARD_ELEMENT_DELIMITER, "" + ECP_STANDARD_SUBELEMENT_DELIMITER);
	            	stEnvelope.setSeTrailer(seTrailer);
	            } else {
	            	if (stEnvelope != null) {
	            		determineLoopIdentifier(str, "" + ECP_STANDARD_ELEMENT_DELIMITER, last2110CIndex);
	            		if (str.startsWith("EB")) {
	            			last2110CIndex++;
	            		}
	            		segment = X12Segment.buildX12Segment(str, "" + ECP_STANDARD_ELEMENT_DELIMITER, determineSegmentCount(str.substring(0, str.indexOf(ECP_STANDARD_ELEMENT_DELIMITER))), currentLoop);
	            		stEnvelope.getSegments().add(segment);
	            	} else {
	            		throw new InvalidX12MessageException("Invalid message: [Missing segment: ST!]"); 
	            	}
	            }
			}
		} 
	}
	
	private void determineLoopIdentifier(String segment, String delimiter, int current2110CIndex) {
		String[] elements = null; 
		
		if (delimiter.equals("*")) {
			elements = segment.split("\\*");
		} else {
			elements = segment.split(delimiter);
		}
		String segmentIdentifier =  elements[0];
		
		if (segmentIdentifier.equals("HL")) {
			if (elements[3].equals("20")) {
				previousLoop = currentLoop;
				currentLoop = "2000A";
				
				payerFlag = true;
				submitterFlag = false;
				receiverFlag = false;
				providerFlag = false;
				subscriberFlag = false;
				dependentFlag = false;
			}
			if (elements[3].equals("21")) {
				previousLoop = currentLoop;
				currentLoop = "2000B";
				
				payerFlag = false;
				submitterFlag = false;
				receiverFlag = true;
				providerFlag = false;
				subscriberFlag = false;
				dependentFlag = false;
			}
			if (elements[3].equals("22")) {
				previousLoop = currentLoop;
				currentLoop = "2000C";
				
				payerFlag = false;
				submitterFlag = false;
				receiverFlag = false;
				providerFlag = false;
				subscriberFlag = true;
				dependentFlag = false;
			}
			if (elements[3].equals("23")) {
				previousLoop = currentLoop;
				currentLoop = "2000D";
				
				payerFlag = false;
				submitterFlag = false;
				receiverFlag = false;
				providerFlag = false;
				subscriberFlag = false;
				dependentFlag = true;
			}
			
			ebFlag = false;
		}
		
		if (segmentIdentifier.equals("NM1")) {
			if (ebFlag) {
				// Subscriber Benefit Related Entity Name - 2120C.
				if (subscriberFlag) {
					previousLoop = currentLoop;
					currentLoop = "2120C";

					payerFlag = false;
					submitterFlag = false;
					receiverFlag = false;
					providerFlag = false;
					subscriberFlag = true;
					dependentFlag = false;
				}

				// Dependent Benefit Related Entity Name - 2120D.
				if (dependentFlag) {
					previousLoop = currentLoop;
					currentLoop = "2120D";

					payerFlag = false;
					submitterFlag = false;
					receiverFlag = false;
					providerFlag = false;
					subscriberFlag = false;
					dependentFlag = true;
				}

			} else {
				// Information Receiver Name - 2100B.
				String[] check2100A = { "2B", "36", "GP", "P5", "PR" };
				String[] check2100B = { "1P", "2B", "36", "80", "FA", "GP", "P5", "PR" };
				if (Arrays.asList(check2100B).contains(elements[1]) && receiverFlag)
				// if ($fields[1] =~ /1P|2B|36|80|FA|GP|P5|PR/ && $receiver)
				{
					previousLoop = currentLoop;
					currentLoop = "2100B";

					payerFlag = false;
					submitterFlag = false;
					receiverFlag = true;
					providerFlag = false;
					subscriberFlag = false;
					dependentFlag = false;
				}
				// Information Source Name - 2100A.
				else if (Arrays.asList(check2100A).contains(elements[1]) && payerFlag)
				// else if ($fields[1] =~ /2B|36|GP|P5|PR/ && $payer)
				{
					previousLoop = currentLoop;
					currentLoop = "2100A";

					payerFlag = true;
					submitterFlag = false;
					receiverFlag = false;
					providerFlag = false;
					subscriberFlag = false;
					dependentFlag = false;
				}

				// Subscriber Name - 2100C.
				if (elements[1].equalsIgnoreCase("IL")) {
					previousLoop = currentLoop;
					currentLoop = "2100C";

					payerFlag = false;
					submitterFlag = false;
					receiverFlag = false;
					providerFlag = false;
					subscriberFlag = true;
					dependentFlag = false;
				}

				// Dependent Name - 2100D.
				if (elements[1].equalsIgnoreCase("03")) {
					previousLoop = currentLoop;
					currentLoop = "2100D";

					payerFlag = false;
					submitterFlag = false;
					receiverFlag = false;
					providerFlag = false;
					subscriberFlag = false;
					dependentFlag = true;
				}

			}
		}
		
		if (segmentIdentifier.equals("EB") || segmentIdentifier.equals("EQ")) {
			// Dependent Eligibility Or Benefit Inquiry Information - 2110D.
			if (dependentFlag) {
				previousLoop = currentLoop;
				currentLoop = "2110D";

				payerFlag = false;
				submitterFlag = false;
				receiverFlag = false;
				providerFlag = false;
				subscriberFlag = false;
				dependentFlag = true;
			}

			//MAT! may need to keep counter of 2110 loops when we find a new eb increment counter and add _Counter to 2110C as in 2110C_0, 2110C_1 etc
			
			// Subscriber Eligibility Or Benefit Inquiry Information - 2110C.
			if (subscriberFlag) {
				previousLoop = currentLoop;
				currentLoop = "2110C";

				payerFlag = false;
				submitterFlag = false;
				receiverFlag = false;
				providerFlag = false;
				subscriberFlag = true;
				dependentFlag = false;
			}

			if (segmentIdentifier.equals("EB")) {
				ebFlag = true;
			}
		}
		
		if (segmentIdentifier.equals("III") && ebFlag) {
			// Dependent Eligibility Or Benefit Additional Information - 2115D.
			if (dependentFlag) {
				previousLoop = currentLoop;
				currentLoop = "2115D";

				payerFlag = false;
				submitterFlag = false;
				receiverFlag = false;
				providerFlag = false;
				subscriberFlag = false;
				dependentFlag = true;
			}

			// Subscriber Eligibility Or Benefit Additional Information - 2115C.
			if (subscriberFlag) {
				previousLoop = currentLoop;
				currentLoop = "2115C";

				payerFlag = false;
				submitterFlag = false;
				receiverFlag = false;
				providerFlag = false;
				subscriberFlag = true;
				dependentFlag = false;
			}
		}
		
		if (segmentIdentifier.equals("LS") && ebFlag) {
	         // Dependent Benefit Related Entity Name - 2120D.
	         if (dependentFlag)
	         {
	        	previousLoop = currentLoop;
	            currentLoop = "2120D";

	            payerFlag = false;
	            submitterFlag = false;
	            receiverFlag = false;
	            providerFlag = false;
	            subscriberFlag = false;
	            dependentFlag = true;
	         }

	         // Subscriber Benefit Related Entity Name - 2120C.
	         if (subscriberFlag)
	         {
	        	previousLoop = currentLoop;
	            currentLoop = "2120C";

	            payerFlag = false;
	            submitterFlag = false;
	            receiverFlag = false;
	            providerFlag = false;
	            subscriberFlag = true;
	            dependentFlag  = true;
	         }
	      }
		
		if (currentLoop != null && currentLoop.startsWith("2110C") && !currentLoop.contains("_")) {
			if (segmentIdentifier.startsWith("EB") && currentLoop.startsWith("2110C") && previousLoop.startsWith("2110C")) {
				if (currentLoop.contains("_")) {
					currentLoop = currentLoop.substring(0, currentLoop.indexOf("_"));
					currentLoop = currentLoop + "_" + current2110CIndex;
				} else {
					currentLoop = currentLoop + "_" + current2110CIndex;
				}
			} else {
				currentLoop = currentLoop + "_" + current2110CIndex;
			}
		} 

	}
	
	private int determineSegmentCount(String str) {
		if (segmentMap == null) {
			segmentMap = new HashMap<String, Integer>();
		}
		
		if (segmentMap.containsKey(str)) {
			segmentMap.put(str, segmentMap.remove(str)+1);
		} else {
			segmentMap.put(str, 0);
		}
		
		return segmentMap.get(str);
	}
	
	public X12ISAHeader getIsaHeader() {
		return isaHeader;
	}
	
	public void setIsaHeader(X12ISAHeader isaHeader) {
		this.isaHeader = isaHeader;
	}
	
	public X12IEATrailer getIeaTrailer() {
		return ieaTrailer;
	}
	
	public void setIeaTrailer(X12IEATrailer ieaTrailer) {
		this.ieaTrailer = ieaTrailer;
	}
	
	public Vector<X12FunctionalGroupEnvelope> getFunctionalGroupEnvelopes() {
		return functionalGroupEnvelopes;
	}
	
	public void setFunctionalGroupEnvelopes(Vector<X12FunctionalGroupEnvelope> functionalGroupEnvelopes) {
		this.functionalGroupEnvelopes = functionalGroupEnvelopes;
	}
	
	public Vector<String> validate() {
		Vector<String> messages = new Vector<String>();
		
		if (this.getIsaHeader() == null) {
			messages.add("Could not parse ISA segment!");
		}
		if (this.getIeaTrailer() == null) {
			messages.add("Could not parse IEA segment!");
		}
		
		if (messages.isEmpty()) {
			messages.addAll(this.getIsaHeader().validate());
			messages.addAll(this.getIeaTrailer().validate());
			
			if (isaHeader.getIsa13() != null && ieaTrailer.getIea02() != null 
					&& !isaHeader.getIsa13().equals(ieaTrailer.getIea02())) {
				messages.addElement("Mismatched field: ISA13 <> IEA02!");
			}
			
			for (X12FunctionalGroupEnvelope gsEnvelope : this.getFunctionalGroupEnvelopes()) {
				messages.addAll(gsEnvelope.validate());
				for (X12TransactionSetEnvelope stEnvelope : gsEnvelope.getTransactionSetEnvelopes()) {
					messages.addAll(stEnvelope.validate(this.isaHeader.getVersion()));
				}
			}
		}
		return messages;
	}
	
	private void validateEnvelopes(String[] segments) throws InvalidX12MessageException {
		int numISA = 0;
		int numIEA = 0;
		int numGS = 0;
		int numGE = 0;
		int numST = 0;
		int numSE = 0;
		Vector<String> messages = new Vector<String>();
		
		for (String segment : segments) {
			if (segment.startsWith(ISA + ECP_STANDARD_ELEMENT_DELIMITER)) {
				numISA++;
			} else if (segment.startsWith(IEA + ECP_STANDARD_ELEMENT_DELIMITER)) {
				numIEA++;
			} else if (segment.startsWith(GS + ECP_STANDARD_ELEMENT_DELIMITER)) {
				numGS++;
			} else if (segment.startsWith(GE + ECP_STANDARD_ELEMENT_DELIMITER)) {
				numGE++;
			} else if (segment.startsWith(ST + ECP_STANDARD_ELEMENT_DELIMITER)) {
				numST++;
			} else if (segment.startsWith(SE + ECP_STANDARD_ELEMENT_DELIMITER)) {
				numSE++;
			}
		}
		
		if (numISA != numIEA) {
			messages.add("Mismatched segment: ISA/IEA");
		}
		if (numGS != numGE) {
			messages.add("Mismatched segment: GS/GE");
		}
		if (numST != numSE) {
			messages.add("Mismatched segment: ST/SE");
		}
		
		if (!messages.isEmpty()) {
			throw new InvalidX12MessageException(formatErrorMessages(messages));
		}
	}
	
	public String print() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(isaHeader.print());
		for (X12FunctionalGroupEnvelope envelope : functionalGroupEnvelopes) {
			sb.append(envelope.print());
		}
		sb.append(ieaTrailer.print());
		
		return sb.toString();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(isaHeader.toString());
		for (X12FunctionalGroupEnvelope envelope : functionalGroupEnvelopes) {
			sb.append(envelope.toString());
		}
		sb.append(ieaTrailer.toString());
		return sb.toString();
	}
	
	public String toJSONString() {
		return toJSONString(Boolean.FALSE);
	}
	public String toJSONString(Boolean verbose) {
		StringBuilder sb = new StringBuilder();
		sb.append(isaHeader.toJSONString());
		for (X12FunctionalGroupEnvelope envelope : functionalGroupEnvelopes) {
			sb.append(envelope.toJSONString(verbose));
		}
		sb.append(ieaTrailer.toJSONString());
		return sb.toString();
	}
	
	private String replaceDelimiters(String data) throws InvalidX12MessageException {
		StringBuilder sb = null;
		char elementDelimiter = data.charAt(ELEMENT_DELIMITER_PS);
		char repetitionDelimiter = data.charAt(REPETITION_DELIMITER_PS);
		char subelementDelimiter = data.charAt(SUB_ELEMENT_DELIMITER_PS);
		char segmentDelimiter = data.charAt(SEGMENT_DELIMITER_PS);
		
		Vector<String> messages = new Vector<String>();
		
		if (elementDelimiter == '\u0000') {
			messages.add("Could not parse element delimiter!");
		}
		if (subelementDelimiter == '\u0000') {
			messages.add("Could not parse subelement delimiter!");
		}
		if (segmentDelimiter == '\u0000') {
			messages.add("Could not parse segment delimiter!");
		}
		
		if (messages.isEmpty()) {		
			sb = new StringBuilder(data);
			boolean hasRepetitionDelimiter = true;
			
			//if (version == "00401" || repetitionDelimiter == 'U') {
			if (repetitionDelimiter == 'U') {
	            hasRepetitionDelimiter = false;
	        }
			
			for (int i = 0; i < sb.length(); i++) {
				if (sb.charAt(i) == segmentDelimiter)
	            {
	                sb.setCharAt(i, ECP_STANDARD_SEGMENT_DELIMITER);
	                continue;
	            }
	            if (sb.charAt(i) == elementDelimiter)
	            {
	            	sb.setCharAt(i, '*');
	                continue;
	            }
	            if (sb.charAt(i) == subelementDelimiter)
	            {
	            	sb.setCharAt(i, '|');
	                continue;
	            }
	            if ((hasRepetitionDelimiter) &&  (sb.charAt(i) == repetitionDelimiter)) {
	            	sb.setCharAt(i, '^');
	                continue;
	            }
	            if ("~*|^".contains(""+sb.charAt(i))) {
	            	sb.setCharAt(i, '-');
	            }
			}
		} else {
			throw new InvalidX12MessageException(this.formatErrorMessages(messages));
		}
		
		return sb.toString();
	}
	
	public String getReceiver() {
		return this.isaHeader.isa08;
	}
	
	public void setReceiver(String receiver) {
		this.isaHeader.isa08 = receiver;
	}
	
	public String getSubmitter() {
		return this.isaHeader.isa06;
	}
	
	public void setSubmitter(String submitter) {
		this.isaHeader.isa06 = submitter;
	}
	
	public String getImplementationConventionReference() {
		return this.functionalGroupEnvelopes.get(0).getTransactionSetEnvelopes().get(0).getStHeader().getSt03();
	}
	
	public void setImplementationConventionReference(String implementationConventionReference) {
		this.functionalGroupEnvelopes.get(0).getGsHeader().setGs08(implementationConventionReference);
		this.functionalGroupEnvelopes.get(0).getTransactionSetEnvelopes().get(0).getStHeader().setSt03(implementationConventionReference);
	}
	
	public void setDate(String date) {
		this.functionalGroupEnvelopes.get(0).getGsHeader().setGs04(date);
		BHTSegment seg = this.functionalGroupEnvelopes.get(0).getTransactionSetEnvelopes().get(0).getBHTSegment();
		seg.getElements()[4] = date;
	}
	
	public void setTime(String time) {
		this.functionalGroupEnvelopes.get(0).getGsHeader().setGs05(time);
		BHTSegment seg = this.functionalGroupEnvelopes.get(0).getTransactionSetEnvelopes().get(0).getBHTSegment();
		seg.getElements()[5] = time;
	}
	
	public String getPatientFirstName() {
		String data = getDependentFirstName();
		if (data == null || data.isEmpty()) {
			data = getSubscriberFirstName();
		}
		return data;
	}
	
	public void setPatientFirstName(String name) {
		if (this.isDependent()) {
			this.setDependentFirstName(name);
		} else {
			this.setSubscriberFirstName(name);
		}
	}
	
	public String getPatientDateOfBirth() {
		String data = getDependentDateOfBirth();
		if (data == null || data.isEmpty()) {
			data = getSubscriberDateOfBirth();
		}
		return data;
	}
	
	public void setPatientDateOfBirth(String name) {
		if (this.isDependent()) {
			this.setDependentDateOfBirth(name);
		} else {
			this.setSubscriberDateOfBirth(name);
		}
	}
	
	public Boolean isDependent() {
		Boolean dependent = Boolean.FALSE;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(DEPENDENT)) {
				dependent = Boolean.TRUE;
				break;
			}
		}
		return dependent;
	}
	
	public String getPatientEligibilityDate() {
		String eligibilityDate = "";
		if (this.isDependent()) {
			eligibilityDate = this.getDependentEligibilityDate();
		} else {
			eligibilityDate = this.getSubscriberEligibilityDate();
		}
		return eligibilityDate;
	}
	
	public String getDependentEligibilityDate() {
		boolean isFound = false;
		String eligibilityDate = "";
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(DEPENDENT)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(DATE_TIME_PERIOD)
				&& isFound) {
				eligibilityDate = segment.getElements()[3];
				break;
			}
		}
		return eligibilityDate;
	}
	
	public String getSubscriberEligibilityDate() {
		boolean isFound = false;
		String eligibilityDate = "";
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(SUBSCRIBER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(DATE_TIME_PERIOD)
				&& isFound) {
				eligibilityDate = segment.getElements()[3];
				break;
			}
		}
		return eligibilityDate;
	}
	
	public void setPatientEligibilityDate(String date) {
		if (this.isDependent()) {
			this.setDependentEligibilityDate(date);
		} else {
			this.setSubscriberEligibilityDate(date);
		}
	}
	
	public void setDependentEligibilityDate(String date) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(DEPENDENT)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(DATE_TIME_PERIOD)
				&& isFound) {
				if (date.contains("-")) {
					segment.getElements()[2] = DATE_RANGE_FORMAT;
				} else {
					segment.getElements()[2] = DATE_FORMAT;
				}
				segment.getElements()[3] = date;
				break;
			}
		}
	}
	
	public void setSubscriberEligibilityDate(String date) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(SUBSCRIBER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(DATE_TIME_PERIOD)
				&& isFound) {
				if (date.contains("-")) {
					segment.getElements()[2] = DATE_RANGE_FORMAT;
				} else {
					segment.getElements()[2] = DATE_FORMAT;
				}
				segment.getElements()[3] = date;
				break;
			}
		}
	}
	
	public String getInformationReceiveIdentificationNumber() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(INFORMATION_RECEIVER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				data = segment.getElements()[9];
				break;
			}
		}
		return data;
	}
	
	public void setInformationReceiverIdentificationNumber(String identificationNumber) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(INFORMATION_RECEIVER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				segment.getElements()[9] = identificationNumber;
				break;
			}
		}
	}
	
	public String getInformationReceiverLastName() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(INFORMATION_RECEIVER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				data = segment.getElements()[3];
				break;
			}
		}
		return data;
	}
	
	public void setInformationReceiverLastName(String name) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(INFORMATION_RECEIVER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				segment.getElements()[3] = name;
				break;
			}
		}
	}
	
	public String getInformationReceiverFirstName() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(INFORMATION_RECEIVER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				data = segment.getElements()[4];
				break;
			}
		}
		return data;
	}
	
	public void setInformationReceiverFirstName(String name) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(INFORMATION_RECEIVER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				segment.getElements()[4] = name;
				break;
			}
		}
	}
	
	public String getInformationSourceName() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(INFORMATION_SOURCE)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				data = segment.getElements()[3];
				break;
			}
		}
		return data;
	}
	
	public void setInformationSourceName(String name) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(INFORMATION_SOURCE)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				segment.getElements()[3] = name;
				break;
			}
		}
	}
	
	public String getInformationSourceIdentifierCode() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(INFORMATION_SOURCE)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				data = segment.getElements()[9];
				break;
			}
		}
		return data;
	}
	
	public void setInformationSourceIdenitiferCode(String name) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(INFORMATION_SOURCE)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				segment.getElements()[9] = name;
				break;
			}
		}
	}
	
	public String getDependentDateOfBirth() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(DEPENDENT)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(DEMOGRAPHIC)
				&& isFound) {
				data = segment.getElements()[2];
				break;
			}
		}
		return data;
	}
	
	public void setDependentDateOfBirth(String dateOfBirth) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(DEPENDENT)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(DEMOGRAPHIC)
				&& isFound) {
				segment.getElements()[2] = dateOfBirth;
				break;
			}
		}
	}
	
	public String getSubscriberTrace() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(SUBSCRIBER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(TRACE_NUMBER)
				&& isFound) {
				data = segment.getElements()[2];
				break;
			}
		}
		return data;
	}
	
	public void setSubscriberTrace(String trace) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(SUBSCRIBER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(TRACE_NUMBER)
				&& isFound) {
				segment.getElements()[2] = trace;
				break;
			}
		}
	}
	
	public String getSubscriberDateOfBirth() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(SUBSCRIBER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(DEMOGRAPHIC)
				&& isFound) {
				data = segment.getElements()[2];
				break;
			}
		}
		return data;
	}
	
	public void setSubscriberDateOfBirth(String dateOfBirth) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(SUBSCRIBER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(DEMOGRAPHIC)
				&& isFound) {
				segment.getElements()[2] = dateOfBirth;
				break;
			}
		}
	}
	
	public String getDependentFirstName() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(DEPENDENT)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				data = segment.getElements()[4];
				break;
			}
		}
		return data;
	}
	
	public void setDependentFirstName(String name) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(DEPENDENT)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				segment.getElements()[4] = name;
				break;
			}
		}
	}
	
	public String getPatientLastName() {
		String data = getDependentLastName();
		if (data == null || data.isEmpty()) {
			data = getSubscriberLastName();
		}
		return data;
	}
	
	public void setPatientLastName(String name) {
		if (this.isDependent()) {
			this.setDependentLastName(name);
		} else {
			this.setSubscriberLastName(name);
		}
	}
	
	public String getDependentLastName() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(DEPENDENT)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				data = segment.getElements()[3];
				break;
			}
		}
		return data;
	}
	
	public void setDependentLastName(String name) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(DEPENDENT)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				segment.getElements()[3] = name;
				break;
			}
		}
	}
	
	public String getSubscriberFirstName() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(SUBSCRIBER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				data = segment.getElements()[4];
				break;
			}
		}
		return data;
	}
	
	public void setSubscriberFirstName(String name) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(SUBSCRIBER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				segment.getElements()[4] = name;
				break;
			}
		}
	}
	
	public String getSubscriberLastName() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(SUBSCRIBER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				data = segment.getElements()[3];
				break;
			}
		}
		return data;
	}
	
	public void setSubscriberLastName(String name) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(SUBSCRIBER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				segment.getElements()[3] = name;
				break;
			}
		}
	}
	
	public String getPatientBirthDate() {
		String data = null;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(DEMOGRAPHIC)) {
				data = segment.getElements()[2];
			}
		}
		return data;
	}
	
	public String getPayorCode() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(INFORMATION_SOURCE)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				data = segment.getElements()[9];
				break;
			}
		}
		return data;
	}
	
	public String getSubscriberIdentifier() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(SUBSCRIBER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				data = segment.getElements()[9];
				break;
			}
		}
		return data;
	}
	
	public void setSubscriberIdentifier(String identifier) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(SUBSCRIBER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				segment.getElements()[9] = identifier;
				break;
			}
		}
	}
	
	public String getInformationReceiverIdentificationCodeQualifier() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(SUBSCRIBER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				data = segment.getElements()[8];
				break;
			}
		}
		return data;
	}
	
	public void setInformationReceiverIdentificationCodeQualifier(String identificationCodeQualifier) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(INFORMATION_RECEIVER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				segment.getElements()[8] = identificationCodeQualifier;
				break;
			}
		}
	}
	
	public String getSubscriberIdentificationCodeQualifier() {
		String data = null;
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(SUBSCRIBER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				data = segment.getElements()[8];
				break;
			}
		}
		return data;
	}
	
	public void setSubscriberIdentificationCodeQualifier(String identificationCodeQualifier) {
		boolean isFound = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(HIERARCHICAL_LEVEL)
				&& segment.getElements()[3].equals(SUBSCRIBER)) {
				isFound = true;
			}
			if (segment.getElements()[0].equals(NAME)
				&& isFound) {
				segment.getElements()[8] = identificationCodeQualifier;
				break;
			}
		}
	}
	
	public String getServiceTypeCode() {
		String serviceTypeCode = null;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(INQUIRY_SERVICE_TYPE_CODE)) {
				serviceTypeCode = segment.getElements()[1];
				break;
			}
		}
		return serviceTypeCode;
	}
	
	public void setServiceTypeCode(String serviceTypeCode) {
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(INQUIRY_SERVICE_TYPE_CODE)) {
				segment.getElements()[1] = serviceTypeCode;
				break;
			}
		}
	}
	
	public List<X12Segment> getDataSegments() {
		return this.functionalGroupEnvelopes.get(0).getTransactionSetEnvelopes().get(0).getSegments();
	}
	
	public boolean hasConnectionError() {
		boolean connectionError = false;
		for (X12Segment segment : getDataSegments()) {
			if (segment.getElements()[0].equals(REQUEST_VALIDATION)) {
				connectionError = "42".equals(segment.getElements()[3]) || "80".equals(segment.getElements()[3]);
				break;
			}
		}
		
		return connectionError;
	}
	
}
