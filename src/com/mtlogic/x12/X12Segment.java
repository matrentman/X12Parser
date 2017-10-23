package com.mtlogic.x12;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class X12Segment {
	private String name;
	private String subName;
	private String delimiter;
	private String loopIdentifier;
	private String[] elements;
	private int index = 0;
	Map<String, Integer> nameIndex = null;
	static private HashMap<String, String> eligibilityLookup = null;
	
	X12Segment(String data, String delimiter, int segmentIndex, String currentLoop) {
		if (nameIndex == null) {
			initializeNameIndex();
		}
		this.delimiter = delimiter;
		this.loopIdentifier = currentLoop;
		if (delimiter.equals("*")) {
			elements = data.split("\\*");
		} else {
			elements = data.split(delimiter);
		}
		this.name = elements[0];
		this.subName = elements[1];
		this.index = segmentIndex;
	}
	
	private void initializeNameIndex() {
		nameIndex = new HashMap<String, Integer>();
		nameIndex.put("AAA", 0);
		nameIndex.put("AMT", 0);
		nameIndex.put("BHT", 0);
		nameIndex.put("EB", 0);
		nameIndex.put("EQ", 0);
		nameIndex.put("DMG", 0);
		nameIndex.put("DTP", 0);
		nameIndex.put("HI", 0);
		nameIndex.put("HL", 0);
		nameIndex.put("HSD", 0);
		nameIndex.put("III", 0);
		nameIndex.put("INS", 0);
		nameIndex.put("LE", 0);
		nameIndex.put("LS", 0);
		nameIndex.put("MPI", 0);
		nameIndex.put("MPI", 0);
		nameIndex.put("MSG", 0);
		nameIndex.put("N3", 0);
		nameIndex.put("N4", 0);
		nameIndex.put("NM1", 0);
		nameIndex.put("PER", 0);
		nameIndex.put("PRV", 0);
		nameIndex.put("REF", 0);
		nameIndex.put("TRN", 0);
		
	}
	
	public static X12Segment buildX12Segment(String data, String delimiter, int segmentIndex, String currentLoop) {
		X12Segment segment = null;
		String name = data.substring(0, data.indexOf(delimiter));
		if (name.equalsIgnoreCase("AAA")) {
			segment = new AAASegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("AMT")) {
			segment = new AMTSegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("BHT")) {
			segment = new BHTSegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("EB")) {
			segment = new EBSegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("EQ")) {
			segment = new EQSegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("DMG")) {
			segment = new DMGSegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("DTP")) {
			segment = new DTPSegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("HI")) {
			segment = new HISegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("HL")) {
			segment = new HLSegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("HSD")) {
			segment = new HSDSegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("III")) {
			segment = new IIISegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("INS")) {
			segment = new INSSegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("LE")) {
			segment = new LESegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("LS")) {
			segment = new LSSegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("MPI")) {
			segment = new MPISegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("MSG")) {
			segment = new MSGSegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("N3")) {
			segment = new N3Segment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("N4")) {
			segment = new N4Segment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("NM1")) {
			segment = new NM1Segment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("PER")) {
			segment = new PERSegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("PRV")) {
			segment = new PRVSegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("REF")) {
			segment = new REFSegment(data, delimiter, segmentIndex, currentLoop);
		} else if (name.equalsIgnoreCase("TRN")) {
			segment = new TRNSegment(data, delimiter, segmentIndex, currentLoop);
		} else {
			segment = new X12Segment(data, "" + delimiter, segmentIndex, currentLoop);
		}
		
		return segment;
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
	
	public String getLoopIdentifier() {
		return loopIdentifier;
	}

	public void setLoopIdentifier(String loopIdentifier) {
		this.loopIdentifier = loopIdentifier;
	}
	
	public String[] getElements() {
		return elements;
	}

	public void setElements(String[] elements) {
		this.elements = elements;
	}
	
	public String print() {
		return toString() + "\n";
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < elements.length - 1; i++) {
			sb.append(elements[i]);
			sb.append(delimiter);
		}
		sb.append(elements[elements.length - 1]);
		sb.append("~");
		return sb.toString();
	}
	
	public String toJSONString() {
		return toJSONString(Boolean.FALSE);
	}
	
	public String toJSONString(Boolean verbose) {
		StringBuilder sb = new StringBuilder();
	
		return sb.toString();
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getLabel() {
		return this.name + "_" + index;
	}
	
	private HashMap<String, String> loadEligibilityLookup() {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		
		Context envContext = null;
		Connection con = null;
		PreparedStatement preparedStatement = null;

		String selectMessageSQL = "select key, value from public.lookup where topic = ?";
		try {
			envContext = new InitialContext();
			Context initContext = (Context) envContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) initContext.lookup("jdbc/eligibility");

			con = ds.getConnection();

			preparedStatement = con.prepareStatement(selectMessageSQL);
			preparedStatement.setString(1, "271");

			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				hashMap.put(rs.getString("key"), rs.getString("value"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			//logger.error("ERROR!!! : " + e.getMessage());
		} catch (NamingException e) {
			e.printStackTrace();
			//logger.error("ERROR!!! : " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			//logger.error("ERROR!!! : " + e.getMessage());
		} finally {
			try {preparedStatement.close();} catch (Exception e) {};
			try {con.close();} catch (Exception e) {};
		}
		
		return hashMap;
	}
	
	public String findElement(int index, Boolean verbose) {
		String element = null;
		if (verbose) {
			element = findVerboseDescription(this.getName(), index, this.getElements()[index]);
		} else {
			element = this.getElements()[index];
		}
		return element;
	}
	
	public String findVerboseDescription(String name, int index, String value) {
		String sindex = (index < 10) ? "0" + Integer.toString(index) : Integer.toString(index);
		
		if (eligibilityLookup == null) {
			eligibilityLookup = this.loadEligibilityLookup();
		}
		
		String[] values = value.split("\\^");
		StringBuilder sb = new StringBuilder();
		String key = null;
		String lookupValue = null;
		for (int i=0; i<values.length; i++) {
			key = name + sindex + values[i];
			lookupValue = eligibilityLookup.get(key);
			sb.append((lookupValue == null) ? values[i] : values[i] + "-" + lookupValue);
			if (i<values.length-1) {
				sb.append("^");
			}
		}
		
		return sb.toString();
	}
}
