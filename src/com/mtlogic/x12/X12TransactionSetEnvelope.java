package com.mtlogic.x12;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

public class X12TransactionSetEnvelope {
	X12STHeader stHeader;
	X12SETrailer seTrailer;
	Vector<X12Segment> segments;
	
	public X12TransactionSetEnvelope() {
		segments = new Vector<X12Segment>();
	}
	
	public X12STHeader getStHeader() {
		return stHeader;
	}
	
	public void setStHeader(X12STHeader stHeader) {
		this.stHeader = stHeader;
	}
	
	public X12SETrailer getSeTrailer() {
		return seTrailer;
	}
	
	public void setSeTrailer(X12SETrailer seTrailer) {
		this.seTrailer = seTrailer;
	}
	
	public Vector<X12Segment> getSegments() {
		return segments;
	}
	
	public void setSegments(Vector<X12Segment> segments) {
		this.segments = segments;
	}
	
	public BHTSegment getBHTSegment() {
		return (BHTSegment)this.getSegments().get(0);
	}
	
	public void setBHTSegment(BHTSegment segment) {
		 this.getSegments().set(0, segment);
	}
	
	public NM1Segment getsubscriberNM1Segment() {
		//iterate over segments
		for (X12Segment segment : segments) {
			String s = segment.getLabel();
		}
		
		return (NM1Segment)this.getSegments().get(0);
	}
	
	public void setsubscriberNM1Segment(NM1Segment segment) {
		//iterate over segments
		
		 this.getSegments().set(0, segment);
	}
	
	public Vector<String> validate(String version) {
		Vector<String> messages = new Vector<String>();
		
		if (stHeader == null) {
			messages.add("Missing segment: ST!");
		}
		if (seTrailer == null) {
			messages.add("Missing segment: SE!");
		}
		
		if (messages.isEmpty()) {
			messages.addAll(this.getStHeader().validate(version));
			messages.addAll(this.getSeTrailer().validate());
			
			if (stHeader != null && seTrailer != null && stHeader.getSt02() != null 
					&& seTrailer.getSe02() != null && !stHeader.getSt02().equals(seTrailer.getSe02())) {
				messages.add("Mismatched field: ST02 <> SE02!");
			}
			if (this.getSegments()!=null && this.getSegments().size()+2 != Integer.parseInt(seTrailer.getSe01())) {
				messages.addElement("Mismatched field: SE01 does not match the actual number of transaction set segments!");
			}
		}
		
		return messages;
	}
	
	public String print() {
		StringBuffer sb = new StringBuffer();
					
		sb.append(stHeader.print());
		
		for (X12Segment segment : getSegments()) {
			sb.append(segment.print());
		}
		
		sb.append(seTrailer.print());
		
		return sb.toString();
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(stHeader.toString());
		
		for (X12Segment segment : getSegments()) {
			sb.append(segment.toString());
		}
		
		sb.append(seTrailer.toString());
		
		return sb.toString();
	}
	
	public String toJSONString() {
		return toJSONString(Boolean.FALSE);
	}
	
	public String toJSONString(Boolean verbose) {
		try {
			return markupLoops(verbose);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}
	
	private int getCurrentLoopIndex(Map map, String key) {
		int loopIndex = 0;
		try {
		if (map.containsKey(key)) {
			 loopIndex = (Integer)map.get(key);
			 map.put(key, loopIndex+1);
			 loopIndex = (Integer)map.get(key);
		} else {
			map.put(key, 0);
		}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return loopIndex;
	}
	
	public String markupLoops(Boolean verbose) {
		StringBuffer sb = new StringBuffer();
		sb.append(stHeader.toJSONString());
		String parentLoopIdentifier = null;
		String previousLoopIdentifier = null;
		String currentLoopIdentifier = null;
		Map<String, Integer> loopMap = new HashMap<String, Integer>();
		int openBlocks = 0;
		
		for (X12Segment segment : getSegments()) {
			//Initial loop (2000A)
			int stopit = 0;
			stopit++;
			if ((currentLoopIdentifier == null) && (segment.getLoopIdentifier() != null) && (segment.getLoopIdentifier().equalsIgnoreCase("2000A"))) {
				sb.append("\"");
				sb.append(segment.getLoopIdentifier());
				sb.append("_");
				sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
				sb.append("\":{");
				openBlocks++;
				currentLoopIdentifier = segment.getLoopIdentifier();
				sb.append(segment.toJSONString(verbose));
			}
			else if ((currentLoopIdentifier != null) && (segment.getLoopIdentifier() != null)) {
				if (segment.getLoopIdentifier().endsWith("A")) {
					if (segment.getName().startsWith("HL") || segment.getName().startsWith("AAA")) {
						System.out.println("123");
						//Add this segment
						sb.append(",");
						sb.append(segment.toJSONString(verbose));
						currentLoopIdentifier = segment.getLoopIdentifier();
						previousLoopIdentifier = currentLoopIdentifier;
					} else if (segment.getLoopIdentifier().equalsIgnoreCase("2100A")) {
						if (currentLoopIdentifier.equalsIgnoreCase("2000A")) {
							//Open 2100A Loop
							sb.append(",\"");
							sb.append(segment.getLoopIdentifier());
							sb.append("_");
							sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
							sb.append("\":{");
							openBlocks++;
							sb.append(segment.toJSONString(verbose));
							parentLoopIdentifier = currentLoopIdentifier;
							previousLoopIdentifier = currentLoopIdentifier;
							currentLoopIdentifier = segment.getLoopIdentifier();
						} else {
							if (segment.getName().startsWith("NM1") || segment.getName().startsWith("PER") || segment.getName().startsWith("AAA")) {
								sb.append(",");
								sb.append(segment.toJSONString(verbose));
							}
						}
					} else {
						//Close the loop
						sb.append("XX1");
					}
					
				} else if (segment.getLoopIdentifier().endsWith("B")) {
					if (segment.getName().startsWith("HL")) {
						//Close the 2100A Loop
						sb.append("}");
						openBlocks--;
						//Close the 2000A Loop
						sb.append("}");
						openBlocks--;
						//Open the 2000B Loop
						sb.append(",\"");
						sb.append(segment.getLoopIdentifier());
						sb.append("_");
						sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
						sb.append("\":{");
						openBlocks++;
						sb.append(segment.toJSONString(verbose));
						parentLoopIdentifier = null;
						previousLoopIdentifier = currentLoopIdentifier;
						currentLoopIdentifier = segment.getLoopIdentifier();
					} else if (segment.getLoopIdentifier().equalsIgnoreCase("2100B")) {
						if (currentLoopIdentifier.equalsIgnoreCase("2000B")) {
							//Open 2100B Loop
							sb.append(",\"");
							sb.append(segment.getLoopIdentifier());
							sb.append("_");
							sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
							sb.append("\":{");
							openBlocks++;
							sb.append(segment.toJSONString(verbose));
							parentLoopIdentifier = currentLoopIdentifier;
							previousLoopIdentifier = currentLoopIdentifier;
							currentLoopIdentifier = segment.getLoopIdentifier();
						} else {
							if (segment.getName().startsWith("NM1") || segment.getName().startsWith("REF") || segment.getName().startsWith("AAA") || segment.getName().startsWith("PRV")) {
								sb.append(",");
								sb.append(segment.toJSONString(verbose));
							}
						}
					} else {
						//Close the loop
						sb.append("XX2");
					}
				} else if (segment.getLoopIdentifier().substring(4).startsWith("C")) {
					if (segment.getLoopIdentifier().equalsIgnoreCase("2000C") && segment.getName().startsWith("HL")) {
						//Close the 2100B Loop
						sb.append("}");
						openBlocks--;
						//Close the 2000B Loop
						sb.append("}");
						openBlocks--;
						//Open the 2000C Loop
						sb.append(",\"");
						sb.append(segment.getLoopIdentifier());
						sb.append("_");
						sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
						sb.append("\":{");
						openBlocks++;
						sb.append(segment.toJSONString(verbose));
						parentLoopIdentifier = null;
						previousLoopIdentifier = currentLoopIdentifier;
						currentLoopIdentifier = segment.getLoopIdentifier();
					} else if (segment.getLoopIdentifier().equalsIgnoreCase("2000C") &&	segment.getName().startsWith("TRN")) {
						sb.append(",");
						sb.append(segment.toJSONString(verbose));
					} else if (segment.getLoopIdentifier().equalsIgnoreCase("2100C")) {
						if (currentLoopIdentifier.equalsIgnoreCase("2000C")) {
							//Open 2100C Loop
							sb.append(",\"");
							sb.append(segment.getLoopIdentifier());
							sb.append("_");
							sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
							sb.append("\":{");
							openBlocks++;
							sb.append(segment.toJSONString(verbose));
							parentLoopIdentifier = currentLoopIdentifier;
							previousLoopIdentifier = currentLoopIdentifier;
							currentLoopIdentifier = segment.getLoopIdentifier();
						} else {
							if (segment.getLoopIdentifier().equalsIgnoreCase("2100C") &&
									segment.getName().startsWith("NM1") || segment.getName().startsWith("REF") || segment.getName().startsWith("N3") || segment.getName().startsWith("N4") || segment.getName().startsWith("AAA") || segment.getName().startsWith("PRV") || segment.getName().startsWith("DMG") || segment.getName().startsWith("INS") || segment.getName().startsWith("HI") || segment.getName().startsWith("DTP") || segment.getName().startsWith("MPI")) {
								sb.append(",");
								sb.append(segment.toJSONString(verbose));
								previousLoopIdentifier = currentLoopIdentifier;
								currentLoopIdentifier = segment.getLoopIdentifier();
							} 
						}
					} else if (segment.getLoopIdentifier().startsWith("2110C")) {
						if (previousLoopIdentifier.equalsIgnoreCase("2100C")) {
							//Open 2110C Loop
							sb.append(",\"");
							sb.append(segment.getLoopIdentifier());
							sb.append("_");
							sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
							sb.append("\":{");
							openBlocks++;
							sb.append(segment.toJSONString(verbose));
							currentLoopIdentifier = segment.getLoopIdentifier();
							parentLoopIdentifier = currentLoopIdentifier;
							previousLoopIdentifier = currentLoopIdentifier;
						} else if (previousLoopIdentifier.startsWith("2110C") && segment.getName().equalsIgnoreCase("EB")) {
							//Close 2110C Loop
							sb.append("}");
							openBlocks--;
							//Open 2110C Loop
							sb.append(",\"");
							sb.append(segment.getLoopIdentifier());
							sb.append("_");
							sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
							sb.append("\":{");
							openBlocks++;
							sb.append(segment.toJSONString(verbose));
							currentLoopIdentifier = segment.getLoopIdentifier();
							parentLoopIdentifier = currentLoopIdentifier;
							previousLoopIdentifier = currentLoopIdentifier;
						} else {
							if (segment.getLoopIdentifier().startsWith("2110C") &&
									segment.getName().startsWith("EB") || segment.getName().startsWith("HSD") || segment.getName().startsWith("REF") || segment.getName().startsWith("DTP") || segment.getName().startsWith("AAA") || segment.getName().startsWith("MSG")) {
								sb.append(",");
								sb.append(segment.toJSONString(verbose));
								previousLoopIdentifier = currentLoopIdentifier;
								currentLoopIdentifier = segment.getLoopIdentifier();
							} 
						}
					} else if (segment.getLoopIdentifier().startsWith("2115C")) {
						if (previousLoopIdentifier.equalsIgnoreCase("2110C") || (previousLoopIdentifier.equalsIgnoreCase("2110C") && segment.getName().equalsIgnoreCase("III"))) {
							//Open 2115C Loop
							sb.append(",\"");
							sb.append(segment.getLoopIdentifier());
							sb.append("_");
							sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
							sb.append("\":{");
							openBlocks++;
							sb.append(segment.toJSONString(verbose));
							parentLoopIdentifier = currentLoopIdentifier;
							previousLoopIdentifier = currentLoopIdentifier;
							currentLoopIdentifier = segment.getLoopIdentifier();
						} else {
							if (segment.getLoopIdentifier().startsWith("2115C") &&
									segment.getName().startsWith("III") || segment.getName().startsWith("LS")) {
								sb.append(",");
								sb.append(segment.toJSONString(verbose));
							} 
						}
					} else if (segment.getLoopIdentifier().startsWith("2120C")) {
						if (previousLoopIdentifier.startsWith("2110C") && segment.getName().equals("LS")) {
							//Add this segment
							sb.append(",");
							sb.append(segment.toJSONString(verbose));
							currentLoopIdentifier = segment.getLoopIdentifier();
							previousLoopIdentifier = currentLoopIdentifier;
						} else if (previousLoopIdentifier.startsWith("2120C") && segment.getName().equals("LE")) {
							//Close 2120C Loop
							sb.append("}");
							openBlocks--;
							//Add this segment
							sb.append(",");
							sb.append(segment.toJSONString(verbose));
//							//Close 2110C Loop
//							sb.append("}");
							
//							//These loops below should not be closed here as LE is within 2110C which can repeat
//							//Close 2100C Loop
//							sb.append("}");
//							//Close 2000C Loop
//							sb.append("}");
							
							currentLoopIdentifier = "2110C";
							previousLoopIdentifier = currentLoopIdentifier;
						} else if (previousLoopIdentifier.startsWith("2115C") || (previousLoopIdentifier.equalsIgnoreCase("2115C") && segment.getName().equalsIgnoreCase("NM1"))) {
							// Close 2115C Loop
							sb.append("}");
							openBlocks--;
							//Open 2120C Loop
							sb.append(",\"");
							sb.append(segment.getLoopIdentifier());
							sb.append("_");
							sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
							sb.append("\":{");
							openBlocks++;
							sb.append(segment.toJSONString(verbose));
							parentLoopIdentifier = currentLoopIdentifier;
							previousLoopIdentifier = currentLoopIdentifier;
							currentLoopIdentifier = segment.getLoopIdentifier();
						} else if (previousLoopIdentifier.startsWith("2120C") && parentLoopIdentifier.startsWith("2110C")) {
							//Open 2120C Loop
							sb.append(",\"");
							sb.append(segment.getLoopIdentifier());
							sb.append("_");
							sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
							sb.append("\":{");
							openBlocks++;
							sb.append(segment.toJSONString(verbose));
							currentLoopIdentifier = segment.getLoopIdentifier();
							parentLoopIdentifier = currentLoopIdentifier;
							previousLoopIdentifier = currentLoopIdentifier;
						}else {
							if (segment.getLoopIdentifier().startsWith("2120C") &&
									segment.getName().startsWith("NM1") || segment.getName().startsWith("N3") || segment.getName().startsWith("N4") || segment.getName().startsWith("PER") || segment.getName().startsWith("PRV") || segment.getName().startsWith("LE")) {
								sb.append(",");
								sb.append(segment.toJSONString(verbose));
							} 
						}
					} else {
						//Close the loop
						sb.append("XX3");
					}
				} else if (segment.getLoopIdentifier().endsWith("D")) {
					if (segment.getLoopIdentifier().equalsIgnoreCase("2000D") && segment.getName().startsWith("HL")) {
						//Close the 2100C Loop
						sb.append("}");
						openBlocks--;
						//Close the 2000C Loop
						sb.append("}");
						openBlocks--;
						//Open the 2000D Loop
						sb.append(",\"");
						sb.append(segment.getLoopIdentifier());
						sb.append("_");
						sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
						sb.append("\":{");
						openBlocks++;
						sb.append(segment.toJSONString(verbose));
						parentLoopIdentifier = null;
						previousLoopIdentifier = currentLoopIdentifier;
						currentLoopIdentifier = segment.getLoopIdentifier();
					} else if (segment.getLoopIdentifier().equalsIgnoreCase("2000D") &&	segment.getName().startsWith("TRN")) {
						sb.append(",");
						sb.append(segment.toJSONString(verbose));
					} else if (segment.getLoopIdentifier().equalsIgnoreCase("2100D")) {
						if (currentLoopIdentifier.equalsIgnoreCase("2000D")) {
							//Open 2100D Loop
							sb.append(",\"");
							sb.append(segment.getLoopIdentifier());
							sb.append("_");
							sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
							sb.append("\":{");
							openBlocks++;
							sb.append(segment.toJSONString(verbose));
							parentLoopIdentifier = currentLoopIdentifier;
							previousLoopIdentifier = currentLoopIdentifier;
							currentLoopIdentifier = segment.getLoopIdentifier();
						} else {
							if (segment.getLoopIdentifier().equalsIgnoreCase("2100D") &&
									segment.getName().startsWith("NM1") || segment.getName().startsWith("REF") || segment.getName().startsWith("N3") || segment.getName().startsWith("N4") || segment.getName().startsWith("AAA") || segment.getName().startsWith("PRV") || segment.getName().startsWith("DMG") || segment.getName().startsWith("INS") || segment.getName().startsWith("HI") || segment.getName().startsWith("DTP") || segment.getName().startsWith("MPI")) {
								sb.append(",");
								sb.append(segment.toJSONString(verbose));
								previousLoopIdentifier = currentLoopIdentifier;
								currentLoopIdentifier = segment.getLoopIdentifier();
							} 
						}
					} else if (segment.getLoopIdentifier().startsWith("2110D")) {
						if (previousLoopIdentifier.equalsIgnoreCase("2100D")) {
							//Open 2110D Loop
							sb.append(",\"");
							sb.append(segment.getLoopIdentifier());
							sb.append("_");
							sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
							sb.append("_0");
							sb.append("\":{");
							openBlocks++;
							sb.append(segment.toJSONString(verbose));
							currentLoopIdentifier = segment.getLoopIdentifier();
							parentLoopIdentifier = currentLoopIdentifier;
							previousLoopIdentifier = currentLoopIdentifier;
						} else if (previousLoopIdentifier.startsWith("2110D") && segment.getName().equalsIgnoreCase("EB")) {
							//Close 2110D Loop
							sb.append("}");
							openBlocks--;
							//Open 2110D Loop
							sb.append(",\"");
							sb.append(segment.getLoopIdentifier());
							sb.append("_");
							sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
							sb.append("_0");
							sb.append("\":{");
							openBlocks++;
							sb.append(segment.toJSONString(verbose));
							currentLoopIdentifier = segment.getLoopIdentifier();
							parentLoopIdentifier = currentLoopIdentifier;
							previousLoopIdentifier = currentLoopIdentifier;
						} else {
							if (segment.getLoopIdentifier().startsWith("2110D") &&
									segment.getName().startsWith("EB") || segment.getName().startsWith("HSD") || segment.getName().startsWith("REF") || segment.getName().startsWith("DTP") || segment.getName().startsWith("AAA") || segment.getName().startsWith("MSG")) {
								sb.append(",");
								sb.append(segment.toJSONString(verbose));
								previousLoopIdentifier = currentLoopIdentifier;
								currentLoopIdentifier = segment.getLoopIdentifier();
							} 
						}
					} else if (segment.getLoopIdentifier().startsWith("2115D")) {
						if (previousLoopIdentifier.equalsIgnoreCase("2110D") || (previousLoopIdentifier.equalsIgnoreCase("2110D") && segment.getName().equalsIgnoreCase("III"))) {
							//Open 2115D Loop
							sb.append(",\"");
							sb.append(segment.getLoopIdentifier());
							sb.append("_");
							sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
							sb.append("\":{");
							openBlocks++;
							sb.append(segment.toJSONString(verbose));
							parentLoopIdentifier = currentLoopIdentifier;
							previousLoopIdentifier = currentLoopIdentifier;
							currentLoopIdentifier = segment.getLoopIdentifier();
						} else {
							if (segment.getLoopIdentifier().startsWith("2115D") &&
									segment.getName().startsWith("III") || segment.getName().startsWith("LS")) {
								sb.append(",");
								sb.append(segment.toJSONString(verbose));
							} 
						}
					} else if (segment.getLoopIdentifier().startsWith("2120D")) {
						if (previousLoopIdentifier.startsWith("2110D") && segment.getName().equals("LS")) {
							//Add this segment
							sb.append(",");
							sb.append(segment.toJSONString(verbose));
							currentLoopIdentifier = segment.getLoopIdentifier();
							previousLoopIdentifier = currentLoopIdentifier;
						} else if (previousLoopIdentifier.startsWith("2120D") && segment.getName().equals("LE")) {
							//Close 2120D Loop
							sb.append("}");
							openBlocks--;
							//Add this segment
							sb.append(",");
							sb.append(segment.toJSONString(verbose));
//							//Close 2110D Loop
//							sb.append("}");
//							openBlocks--;
//							currentLoopIdentifier = segment.getLoopIdentifier();
							currentLoopIdentifier = "2110D";
							previousLoopIdentifier = currentLoopIdentifier;
						} else if (previousLoopIdentifier.startsWith("2115C") || (previousLoopIdentifier.equalsIgnoreCase("2115C") && segment.getName().equalsIgnoreCase("NM1"))) {
							// Close 2115D Loop
							sb.append("}");
							openBlocks--;
							//Open 2120D Loop
							sb.append(",\"");
							sb.append(segment.getLoopIdentifier());
							sb.append("_");
							sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
							sb.append("\":{");
							openBlocks++;
							sb.append(segment.toJSONString(verbose));
							parentLoopIdentifier = currentLoopIdentifier;
							previousLoopIdentifier = currentLoopIdentifier;
							currentLoopIdentifier = segment.getLoopIdentifier();
						} else if (previousLoopIdentifier.startsWith("2120D") && parentLoopIdentifier.startsWith("2110D")) {
							//Open 2120D Loop
							sb.append(",\"");
							sb.append(segment.getLoopIdentifier());
							sb.append("_");
							sb.append(getCurrentLoopIndex(loopMap, segment.getLoopIdentifier()));
							sb.append("\":{");
							openBlocks++;
							sb.append(segment.toJSONString(verbose));
							currentLoopIdentifier = segment.getLoopIdentifier();
							parentLoopIdentifier = currentLoopIdentifier;
							previousLoopIdentifier = currentLoopIdentifier;
						}else {
							if (segment.getLoopIdentifier().startsWith("2120d") &&
									segment.getName().startsWith("NM1") || segment.getName().startsWith("N3") || segment.getName().startsWith("N4") || segment.getName().startsWith("PER") || segment.getName().startsWith("PRV") || segment.getName().startsWith("LE")) {
								sb.append(",");
								sb.append(segment.toJSONString(verbose));
							} 
						}
					} else {
						//Close the loop
						sb.append("XX4");
					}
				}
			}
			else if (currentLoopIdentifier != null) {
				sb.append(",");
			} else {			
				sb.append(segment.toJSONString(verbose));
				if (segment.getName().startsWith("BHT")) {
					sb.append(",");
				}
			}
		}
//may need to keep a count of open loops decrement when we close then close any unclosed ones here!
		for (int i=0; i<openBlocks; i++) {
			sb.append("}");
		}
		sb.append(",");
		
//		//Close 2100D Loop
//		sb.append("}");
//		//Close 2000D Loop
//		sb.append("}");
//		//?
//		sb.append("},");
		
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	
	public String getSetIdentifierCode() {
		return this.stHeader.st01;
	}
	
	public void setSetIdentifierCode(String code) {
		this.stHeader.st01 = code;
	}
	
}
