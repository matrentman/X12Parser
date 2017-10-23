package com.mtlogic.x12;

import java.util.Vector;

public class X12Base {
	public static final String ISA = "ISA";
	public static final String GS = "GS";
	public static final String ST = "ST";
	public static final String SE = "SE";
	public static final String IEA = "IEA";
	public static final String GE = "GE";
	public static final String ANSI_4010 = "00401";
	public static final String ANSI_5010 = "00501";
	public static final String ANSI_5010_IMPLEMENTAION_REFERENCE = "005010X279";
	public static final int REPETITION_DELIMITER_PS = 82;
	public static final int ELEMENT_DELIMITER_PS = 103;
	public static final int SUB_ELEMENT_DELIMITER_PS = 104;
	public static final int SEGMENT_DELIMITER_PS = 105;
	public static final char ECP_STANDARD_SEGMENT_DELIMITER = '~';
	public static final char ECP_STANDARD_ELEMENT_DELIMITER = '*';
	public static final char ECP_STANDARD_SUBELEMENT_DELIMITER = '|';
	public static final char ECP_STANDARD_REPETIION_DELIMITER = '^';

	//AAA
	public static final String AAA_RC = "response_code";
	public static final String AAA_AQC = "agency_qualifier_code";
	public static final String AAA_RRC = "reject_reason_code";
	public static final String AAA_FAC = "followup_action_code";
	
	//AMT
	public static final String AMT_AQC = "amount_qualifier_code";
	public static final String AMT_MA = "monetary_amount";
	public static final String AMT_CDFC = "credt_debit_flag_code";	
	
	//BHT
	public static final String BHT_HSC = "hierarchical_structure_code";
	public static final String BHT_TSPC = "transaction_set_purpose_code";
	public static final String BHT_RI = "reference_identification";
	public static final String BHT_DATE = "date";
	public static final String BHT_TIME = "time";
	public static final String BHT_TTC = "transaction_type_code";	

	//DMG
	public static final String DMG_DTPFQ = "date_time_period_format_qualifier";
	public static final String DMG_DTP = "date_time_period";
	public static final String DMG_GC = "gender_code";
	public static final String DMG_MSC = "marital_status_code";
	public static final String DMG_CR = "composite_race";
	public static final String DMG_CSC = "citizenship_status_code";
	public static final String DMG_CC = "country_code";
	public static final String DMG_BOVC = "basis_of_verification_code";
	public static final String DMG_QNT = "quantity";
	public static final String DMG_CLQC = "code_list_qualifier_code";
	public static final String DMG_IC = "industry_code";

	//DTP
	public static final String DTP_DTQ = "date_time_qualifier";
	public static final String DTP_DTPFQ = "date_time_period_format_qualifier";
	public static final String DTP_DTP = "date_time_period";	
	
	//EB
	public static final String EB_EOBIC = "eligibility_or_benefit_information_code";
	public static final String EB_CLC = "coverage_level_code";
	public static final String EB_STC = "service_type_code";
	public static final String EB_ITC = "insurance_type_code";
	public static final String EB_PCD = "plan_coverage_description";
	public static final String EB_TPQ = "time_period_qualifier";
	public static final String EB_MA = "monetary_amount";
	public static final String EB_PAD = "percentage_as_decimal";
	public static final String EB_QQ = "quantity_qualifier";
	public static final String EB_QTY = "quantity";
	public static final String EB_RC = "response_code";
	public static final String EB_RC2 = "response_code_2";
	public static final String EB_CMPI = "composite_medical_procedure_identifier";
	public static final String EB_CDCP = "composite_diagnosis_code_pointer";	

	//EQ
	public static final String INQUIRY_SERVICE_TYPE_CODE = "EQ";
	public static final String EQ_STC = "service_tpye_code";
	public static final String EQ_CMPI = "composite_medical_procedure_identifier";
	public static final String EQ_CLC = "coverage_level_code";
	public static final String EQ_ITC = "insurance_type_code";
	public static final String EQ_CDCP = "composite_diagnosis_code_pointer";	
	
	//GE
	public static final String GE_NOTS = "number_of_transaction_sets";
	public static final String GE_GCN = "group_control_number";

	//GS
	public static final String GS_FIC = "functional_identifier_code";
	public static final String GS_ASC = "application_sender_code";
	public static final String GS_ARC = "application_receiver_code";
	public static final String GS_DATE = "date";
	public static final String GS_TIME = "time";
	public static final String GS_GCN = "group_control_number";
	public static final String GS_RAC = "responsible_agency_code";
	public static final String GS_VERSION = "version";	

	//HI
	public static final String HI_1 = "health_care_code_information_1";
	public static final String HI_2 = "health_care_code_information_2";
	public static final String HI_3 = "health_care_code_information_3";
	public static final String HI_4 = "health_care_code_information_4";
	public static final String HI_5 = "health_care_code_information_5";
	public static final String HI_6 = "health_care_code_information_6";
	public static final String HI_7 = "health_care_code_information_7";
	public static final String HI_8 = "health_care_code_information_8";
	public static final String HI_9 = "health_care_code_information_9";
	public static final String HI_10 = "health_care_code_information_10";
	public static final String HI_11 = "health_care_code_information_11";
	public static final String HI_12 = "health_care_code_information_12";

	//HL
	public static final String HL_HID = "hierarchical_id_number";
	public static final String HL_HPN = "hierarchical_parent_number";
	public static final String HL_HLC = "hierarchical_level_code";
	public static final String HL_HCC = "hierarchical_level_code_2";
	
	//HSD
	public static final String HSD_QQ = "quantity_qualifier";
	public static final String HSD_QTY = "quantity";
	public static final String HSD_UOBOMC = "unit_or_basis_of_measurement_code";
	public static final String HSD_SSM = "sample_selection_modulus";
	public static final String HSD_TPQ = "time_period_qualifier";
	public static final String HSD_NOP = "number_of_periods";
	public static final String HSD_SDOCPC = "ship_delivery_or_calendar_pattern_code";
	public static final String HSD_SDPTC = "ship_delivery_pattern_time_code";
	
	//IEA
	public static final String IEA_NOFG = "number_of_functional_groups";
	public static final String IEA_ICN = "interchange_control_number";

	//III
	public static final String III_CLQC = "code_list_qualifier_code";
	public static final String III_IC = "industry_code";
	public static final String III_CC = "code_category";
	public static final String III_FFMT = "free_form_message_text";
	public static final String III_QTY = "quantity";
	public static final String III_CUOM = "composite_unit_of_measure";
	public static final String III_SLPC1 = "surface_layer_position_code_1";
	public static final String III_SLPC2 = "surface_layer_position_code_2";
	public static final String III_SLPC3 = "surface_layer_position_code_3";	
	
	//INS
	public static final String INS_RC = "response_code";
	public static final String INS_IRC = "individual_relationship_code";
	public static final String INS_MTC = "maintenance_type_code";
	public static final String INS_MRC = "maintenance_reason_code";
	public static final String INS_BSC = "benefit_status_code";
	public static final String INS_MSC = "medicare_status_code";
	public static final String INS_CQ = "cobra_qualifying";
	public static final String INS_ESC = "employment_status_code";
	public static final String INS_SSC = "student_status_code";
	public static final String INS_RC2 = "response_code_2";
	public static final String INS_DTPFQ = "date_time_period_format_qualifier";
	public static final String INS_DTP = "date_time_period";
	public static final String INS_CONF = "confidentiality_code";
	public static final String INS_CN = "city_name";
	public static final String INS_SC = "state_code";
	public static final String INS_CC = "country_code";
	public static final String INS_NUM = "number";
		
	//ISA
	public static final String ISA_AIQ = "authorization_information_qualifier";
	public static final String ISA_AI = "authorization_information";
	public static final String ISA_SIQ = "security_information_qualifier";
	public static final String ISA_SI = "security_information";
	public static final String ISA_ISIQ = "interchange_sender_id_qualifier";
	public static final String ISA_ISI = "interchange_sender_id";
	public static final String ISA_IRIQ = "interchange_receiver_id_qualifier";
	public static final String ISA_IRI = "interchange_receiver_id";
	public static final String ISA_ID = "interchange_date";
	public static final String ISA_IT = "interchange_time";
	public static final String ISA_RS = "repetition_separator";
	public static final String ISA_ICVN = "interchange_control_verison_number";
	public static final String ISA_AR = "acknowledgement_requested";
	public static final String ISA_IUI = "interchange_usage_indicator";
	public static final String ISA_CES = "component_element_separator";
	public static final String ISA_SES = "segment_element_separator";

	//LE
	public static final String LE_LIC = "loop_identifier_code";
	
	//LS
	public static final String LS_LIC = "loop_identifier_code";	

	//MPI
	public static final String MPI_ISC = "information_status_code";
	public static final String MPI_ESC = "employment_status_code";
	public static final String MPI_GSAC = "government_service_affiliation_code";
	public static final String MPI_DESC = "description";
	public static final String MPI_MSRC = "military_service_rank_code";
	public static final String MPI_DTPFQ = "date_time_period_format_qualifer";
	public static final String MPI_DTP = "date_time_period";	

	//MSG
	public static final String MSG_FFMT = "free_form_message_text";
	public static final String MSG_PCCC = "printer_carriage_control_code";
	public static final String MSG_NUM = "number";
	
	//N3
	public static final String N3_AI = "address_information";
	public static final String N3_AI_2 = "address_information_2";
	
	//N4
	public static final String N4_CN = "city_name";
	public static final String N4_SC = "state_code";
	public static final String N4_PC = "postal_code";
	public static final String N4_CC = "country_code";
	public static final String N4_LQ = "location_qualifier";
	public static final String N4_LI = "location_identifier";
	public static final String N4_CSC = "country_subdivision_code";
	
	//NM1
	public static final String NM1_EIC = "entity_identifier_code";
	public static final String NM1_ETQ = "entity_type_qualifier";
	public static final String NM1_NL = "name_last";
	public static final String NM1_NF = "name_first";
	public static final String NM1_NM = "name_middle";
	public static final String NM1_NP = "name_prefix";
	public static final String NM1_NS = "name_suffix";
	public static final String NM1_ICQ = "identification_code_qualifier";
	public static final String NM1_IC = "identification_code";
	public static final String NM1_ERC = "entity_relationship_code";
	public static final String NM1_EIC_2 = "entity_identifier_code_2";
	public static final String NM1_NL_2 = "name_last_2";
	
	//PER
	public static final String PER_CFC = "contact_function_code";
	public static final String PER_N = "name";
	public static final String PER_CNQ = "communication_number_qualifier";
	public static final String PER_CN = "commnication_number";
	public static final String PER_CNQ_2 = "communication_number_qualifier_2";
	public static final String PER_CN_2 = "communication_number_2";
	public static final String PER_CNQ_3 = "communication_number_qualifier_3";
	public static final String PER_CN_3 = "communication_number_3";
	public static final String PER_CIR = "contact_inquiry_reference";

	//PRV
	public static final String PRV_PC = "provider_code";
	public static final String PRV_RIQ = "reference_identification_qualifier";
	public static final String PRV_RI = "reference_identification";
	public static final String PRV_SC = "state_code";
	public static final String PRV_PSI = "provider_specialty_information";
	public static final String PRV_POC = "provider_organization_code";	
	
	//REF
	public static final String REF_RIQ = "reference_identification_qualifier";
	public static final String REF_RI = "reference_identification";
	public static final String REF_DESC = "description";
	public static final String REF_RID = "reference_identifier";

	//SE
	public static final String SE_NOTS = "number_of_transaction_sets";
	public static final String SE_GCN = "group_control_number";
	
	//ST
	public static final String ST_TSIC = "transaction_set_identifier_code";
	public static final String ST_TSCN = "transaction_set_control_number";
	public static final String ST_ICR = "implementation_convention_reference";	
		
	//TRN
	public static final String TRN_TTC = "trace_type_code";
	public static final String TRN_RI = "reference_identification";
	public static final String TRN_OCI = "originating_company_identifier";
	public static final String TRN_RI_2 = "reference_identification";
	
	public static final String DATE_FORMAT = "D8";
	public static final String DATE_RANGE_FORMAT = "RD8";
	
	public static final String INFORMATION_SOURCE = "20";
	public static final String INFORMATION_RECEIVER = "21";
	public static final String SUBSCRIBER = "22";
	public static final String DEPENDENT = "23";
	public static final String DEMOGRAPHIC = "DMG";
	public static final String NAME = "NM1";
	public static final String DATE_TIME_PERIOD = "DTP";
	//public static final String DEPENDENT = "QC";
	public static final String HIERARCHICAL_LEVEL = "HL";
	public static final String TRACE_NUMBER = "TRN";
	//public static final String SUBSCRIBER = "IL";
	public static final String REQUEST_VALIDATION = "AAA";
	
	public String formatErrorMessages(Vector<String> messages) {
		StringBuffer sb = new StringBuffer();
		if (!messages.isEmpty()) {
			sb.append("Invalid message: [");
			for (int i=0; i < messages.size()-1; i++) {
				sb.append(messages.get(i));
				sb.append(", ");
			}
			sb.append(messages.get(messages.size()-1));
			sb.append("]");
		}
		return sb.toString();
	}
}
