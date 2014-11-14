package hk.com.mtr.pcis.web.faces.util;
public class Constant {
	
	/*
	 * store selected user to PageContext when add user-role relation
	 */
	public static final String SA_ROLE_SELECTED_USER_KEY = "hk.com.mtr.pcis.web.user";

	/*
	 * store selected company/school to PageContext when new application or
	 * renew
	 */
	public static final String MF_NEWAPPLICANT_SELECTED_COMPANY_KEY = "hk.com.mtr.pcis.web.company";

	/*
	 * store selected person to PageContext when new application or renew
	 */
	public static final String MF_RENEWALAPPLICANT_SELECTED_PERSON_KEY = "hk.com.mtr.pcis.web.person";

	public static final String SA_PRIVILEGE_CHECK_KEY = "hk.com.mtr.pcis.web.privilege.";

	public static final String POSTBACK_ATTRIBUTE_NAME = "hk.com.mtr.pcis.web.postback";

	public static final String ONLINE_CSCNO = "CSCNO";
	public static final String ONLINE_COLLECTIONDATE = "COLLECTIONDATE";
	public static final String ONLINE_SERIALNO = "SERIALNO";

	public static final String ONLINE_APPTYPE_N = "N";
	public static final String ONLINE_APPTYPE_A = "A";
	public static final String ONLINE_APPTYPE_R = "R";
	
	public static final String ONLINE_APPTYPE_N_LABEL = "New";
	public static final String ONLINE_APPTYPE_A_LABEL = "Activation";
	public static final String ONLINE_APPTYPE_R_LABEL = "Renew";
	
	public static final String CARD_STATUS_PRODUCTION = "PRODUCTION";

	public static final String CARD_STATUS_VALID = "VALID";

	public static final String CARD_STATUS_EXPIRED = "EXPIRED";

	public static final String CARD_STATUS_RENEWAPP = "RENEWAPP";

	public static final String CARD_STATUS_RENEWED = "RENEWED";
	
	public static final String CARD_STATUS_REJECT = "REJECT";

	public static final String PERSON_CARD_TYPE_RED = "RED";

	public static final String CARD_STATUS_TRANSFER = "TRANSFER";

	public static final String CARD_STATUS_DAMAGE = "DAMAGE";

	public static final String CARD_STATUS_MALFUNCTION = "MALFUNCTION";

	public static final String CARD_STATUS_LOST = "LOST";

	public static final String ACTION_NEW = "NEW";

	public static final String ACTION_VIEW = "VIEW";

	public static final String ACTION_EDIT = "EDIT";

	public static final String ACTION_TRANSFERRED = "TRANSFERRED";

	public static final String ACTION_DAMAGED = "DAMAGED";

	public static final String ACTION_LOST = "LOST";

	public static final String ACTION_MALFUNCTION = "MALFUNCTION";

	public static final String ACTION_RENEW = "RENEWAPP";
	
	public static final String CARD_TYPE_BLU = "BLU";
	
	public static final String CARD_TYPE_RED = "RED";

	public static final String SEX_MALE_VALUE = "M";

	public static final String SEX_FEMALE_VALUE = "F";

	public static final String NEWAPPLICANT_HKID = "HK";

	public static final String PERSON_CARD_TYPE_SPO = "SPO";

	public static final String PERSON_CARD_TYPE_PWD = "PWD";
	
	public static final String PERSON_CARD_TYPE_RCD = "RCD";

	public static final String FILE_PATH_KEY = "IMAGE_PATH";

	public static final String FILE_ORIGIN_PATH = "origin";

	public static final String FILE_BAK_PATH = "backup";

	public static final String FILE_SKIP_PATH = "skip";

	public static final String FILE_TYPE_PDF = "pdf";
	
	public static final String FILE_TYPE_JPG = "jpg";

	public static final String FILE_TYPE_IMAGE = "image";

	public static final String MIME_TYPE_PDF = "application/pdf";

	public static final String MIME_TYPE_IMAGE = "image/jpeg";

	public static final String CHARSET_UTF8_VALUE = "UTF-8";

	public static final String BOOL_TRUE_VALUE = "Y";

	public static final String BOOL_FALSE_VALUE = "N";

	public static final String RADIO_BUTTON_NAME = "hk.com.mtr.pcis.web.radiobutton";

	public static final String FLAG_INDEX_1 = "1";

	public static final String FLAG_INDEX_2 = "2";

	public static final String FLAG_INDEX_3 = "3";

	public static final String FLAG_INDEX_4 = "4";

	public static final String FLAG_INDEX_5 = "5";

	public static final String PARAMETER_KEY = "key";

	public static final String CONTENT_TYPE_EXCEL = "application/vnd.ms-excel; charset=UTF-8";

	public static final String REPORT_EXPORT_TYPE_PDF = "PDF";

	public static final String REPORT_EXPORT_TYPE_EXCEL = "MSExcel";

	public static final String KEY_TAB = "\t";

	public static final String KEY_ENTER = "\r\n";

	public static final String APP_FORM_EXT_NAME = FacesUtil.getParameterVO("APP_FORM_EXT_NAME").getParamCharValue();

	public static final String APP_FORM_DATA = "hk.com.mtr.pcis.web.applicationForm";

	public static final String DATE_FORMAT_YYMMDD = "yyyy-MM-dd";
	
	public static final String CONTENT_TYPE_CSV = "application/csv";
	
	public static final String CSV_COL_DELIMITER = ",";
	
	public static final String CSV_ROW_DELIMITER = "\r\n";
	
	public static final String AUTHORIZED = "authorized";
	
	public static final String REPORT_FLAG = "hk.com.mtr.pcis.web.report.flag";
	
	public static final String CONTENT_TYPE_HTML = "text/html; charset=UTF-8";
	
	public static final String PHOTO_IMAGE = "PHOTO_IMAGE";
	
	public static final String CARD_IMAGE = "CARD_IMAGE";
	
	public static final String PROOF_IMAGE = "PROOF_IMAGE";
	
	public static class FileExtName {
		public static final String PDF = "pdf";

		public static final String MSEXCEL = "xls";

		public static final String TEXT = "txt";

		public static final String RTF = "rtf";

		public static final String ZIP = "zip";

		public static final String CSV = "csv";
	}
	
	public static final String STATUS_O_VALUE = "O";
	
	public static final String STATUS_F_VALUE = "F";
	
	public static final String STATUS_O_SHOW = "Outstanding";
	
	public static final String STATUS_F_SHOW = "Follow";
	public static class User {
		public static final String SEARCH_USER = "M1001-S";

		public static final String ADD_USER = "M1001-A";

		public static final String UPDATE_USER = "M1001-U";

		public static final String DELETE_USER = "M1001-D";
	}
	public static class RelationShip{
		public static final String NULL_SHOW="";
		public static final String FATHER_SHOW="Father";
		public static final String FATHER_VALUE="F";
		public static final String MOTHER_SHOW="Mother";
		public static final String MOTHER_VALUE="M";
		public static final String GUARDIAN_SHOW="Guardian";
		public static final String GUARDIAN_VALUE="G";
	}
	public static class Role {
		public static final String SEARCH_ROLE = "M1002-S";

		public static final String ADD_ROLE = "M1002-A";

		public static final String UPDATE_ROLE = "M1002-U";

		public static final String DELETE_ROLE = "M1002-D";
	}

	public static class CompanyType {
		public static final String SEARCH_COMPANYTYPE = "M2001-S";

		public static final String ADD_COMPANYTYPE = "M2001-A";

		public static final String UPDATE_COMPANYTYPE = "M2001-U";

		public static final String DELETE_COMPANYTYPE = "M2001-D";
	}

	//与PCI_FUNC_DETAIL对应的菜单ID
	//PCI_FUNC_DETAIL 是配置对应菜单对相应页面操作的权限
	public static class CardExpiryDate {
		public static final String SEARCH_CARDEXPIRYDATE = "M2004-S";

		public static final String ADD_CARDEXPIRYDATE = "M2004-A";

		public static final String UPDATE_CARDEXPIRYDATE = "M2004-U";

		public static final String DELETE_CARDEXPIRYDATE = "M2004-D";

		public static final String UPDATE_YEAR_CARDEXPIRYDATE = "M2004-UPDATE-YEAR";
	}

	public static class Company {
		public static final String SEARCH_COMPANY = "M2003-S";

		public static final String ADD_COMPANY = "M2003-A";

		public static final String UPDATE_COMPANY = "M2003-U";

		public static final String DELETE_COMPANY = "M2003-D";
	}

	public static class NewApplicant {
		public static final String NEWAPPLICANT = "M3001-A";
	}

	public static class PersonCardMaintenance {
		public static final String SEARCH_PERSON = "M3005-S";
		public static final String ADD_PERSON = "M3005-PERSON-A";
		public static final String DELETE_PERSON = "M3005-PERSON-D";
		public static final String ADD_CARD = "M3005-CARD-A";
		public static final String UPDATE_CARD = "M3005-CARD-U";
		public static final String DELETE_CARD = "M3005-CARD-D";
		public static final String LOST_CARD = "M3005-CARD-LOST";
		public static final String MALFUNCTION_CARD = "M3005-CARD-MALFUNC";
		public static final String DAMAGE_CARD = "M3005-CARD-DAMAGE";
		public static final String TRANSFER_CARD = "M3005-CARD-TRANSFER";
		public static final String RENEW_CARD = "M3005-CARD-RENEW";
	}

	public static class CardVerification {
		public static final String CONCESSION_TYPE_STUDENT = "Student";

		public static final String CONCESSION_TYPE_DISABLE = "Disable";

		public static final String STAFF_TYPE_NUMBER4 = "C - Staff Senior Dependent";
	}

	public static class StaffType {
		public static final String P_STAFF = "P - Staff";
		public static final String S_STAFF_SPOUSE = "S - Staff Spouse";
		public static final String STAFF_JUNIOR_DEPENDENT = "C - Staff Junior Dependent";
		public static final String STAFF_SENIOR_DEPENDENT = "C - Staff Senior Dependent";
		public static final String RETIREE = "H - Retiree";
		public static final String POLICE = "X -鈥凱olice";
		public static final String CONTRACTOR_I = "D - Contractor I";
		public static final String CONTRACTOR_II = "B - Contractor II";
		public static final String CONSULTANT = "N - Consultant";
	}

	public static class OctopusCardType {
		public static final String ANONYMOUS_CSC = "Anonymous CSC";
		public static final String PERSONALISED_CSC = "Personalised CSC";
		public static final String MAINTENANCE_CSC = "Maintenance Mode Switch CSC";
		public static final String PCP_CSC = "PCP Entry/Exit Switch CSC";
		public static final String TEST_CSC = "Test CSC";
		public static final String DRIVER_CSC = "Driver CSC";
		public static final String SUPERVISOR_CSC = "Supervisor CSC";
		public static final String OPERATOR_CSC = "Operator CSC";
		public static final String INSPECTOR_CSC = "Inspector CSC";
		public static final String PERMITTED_POOL_CSC = "Permitted Pool CSC";
	}

	public static class ConcessionType {
		public static final String NO_CONCESSION = "No concession";
		public static final String STUDENT = "Student";
		public static final String DISABLED = "Disabled";
		public static final String CHILD = "Child";
		public static final String SENIOR_CITIZEN = "Senior Citizen";
	}

	public static class ParamMaint {
		public static final String UPDATE_PARAM = "M1003-U";
		public static final String PARAM_MAINT_KEY_LOGIN_MSG = "LOGIN_MSG";
	}

	public static class DispatchSheet {
		public static final String DELETE_DISPATCHSHEET = "M3002-D";
		public static final String DELETE_DISPATCHSHEET_PAGE = "M3002-D2";
		public static final String REPORT_DISPATCHSHEET = "M3002-P";
		public static final String GENERATE_DISPATCHSHEET = "M3002-G";
		public static final String SEARCH_DISPATCHSHEET = "M3002-S";
	}
	
	public static class OnlineAppValid {
		public static final String SEARCH_ONLINEAPPVALID = "M7001-S";
		public static final String CONFIRM_ONLINEAPPVALID = "M7001-C";
		public static final String INVALID_ONLINEAPPVALID = "M7001-I";
		public static final String SKIP_ONLINEAPPVALID = "M7001-SK";
	}
	
	public static class OnlineAppEquiry {
		public static final String SEARCH_ONLINEAPPENQUIRY = "M7003-S";
		public static final String VIEW_ONLINEAPPENQUIRY = "M7003-V";
		public static final String DETAIL_ONLINEAPPENQUIRY = "M7003-D";
	}
}
