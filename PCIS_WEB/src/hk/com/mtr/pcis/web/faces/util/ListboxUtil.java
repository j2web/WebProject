package hk.com.mtr.pcis.web.faces.util;

import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.facade.mf.StationFacade;
import hk.com.mtr.pcis.facade.sa.ParameterFacade;
import hk.com.mtr.pcis.vo.mf.StationVO;
import hk.com.mtr.pcis.vo.sa.ParameterVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

@Scope(ScopeType.APPLICATION)
@Name("listbox")
@BypassInterceptors
public class ListboxUtil implements Serializable {
	private static final long serialVersionUID = -2481066579829087287L;

	private static List<String> sexList = new java.util.ArrayList<String>();
	private static List<String> waiveList = new java.util.ArrayList<String>();
	private static List<Entry<String,String>> statusList = new java.util.ArrayList<Entry<String,String>>();
	private static Map<String, ParameterVO> parameterVOMap = new HashMap<String, ParameterVO>();
	private static List<String> reportExportFormatList = new java.util.ArrayList<String>();
	private static Map<String, String> companyNameMap = new HashMap<String, String>();
	private static List<Entry<String,String>>allRelationship = new ArrayList<Entry<String,String>>();
	private static Map<String, String> companyCodeNameMap = new HashMap<String, String>();
	private static List<String> rejectReason = new java.util.ArrayList<String>();
	private static List<StationVO> allStation = new java.util.ArrayList<StationVO>();;

	public List<StationVO> getAllStation() throws BusinessException {

		if (allStation.isEmpty()) {
			synchronized (allStation) {
				if (allStation.isEmpty()) {
					StationFacade stationMasterFacade = ServiceUtil.getService(StationFacade.class);
					allStation.addAll(stationMasterFacade.findAllStation());
				}
			}
		}
		return allStation;
	}

	public static Map<String, String> getCompanyNameMap(){
		return companyNameMap;
	}
	
	
	//add by Dannie for pi-2014046 release 1 pcis 20140218
	public List<Entry<String,String>> getAppTypeList3() throws BusinessException {
		List<Entry<String,String>> appTypeList3 = new ArrayList<Entry<String,String>>();
		if (appTypeList3.isEmpty()) {
			synchronized (appTypeList3) {
				if (appTypeList3.isEmpty()) {
					Map<String,String>map = new TreeMap<String,String>();
					map.put(Constant.ONLINE_APPTYPE_A,Constant.ONLINE_APPTYPE_A_LABEL);
					map.put(Constant.ONLINE_APPTYPE_N,Constant.ONLINE_APPTYPE_N_LABEL);
					map.put(Constant.ONLINE_APPTYPE_R,Constant.ONLINE_APPTYPE_R_LABEL);
					appTypeList3.addAll(map.entrySet());
				}
			}
		}
		return appTypeList3;
	}
	

	public List<String> getSexList() throws BusinessException {
		if (sexList.isEmpty()) {
			synchronized (sexList) {
				if (sexList.isEmpty()) {
					sexList.add(Constant.SEX_MALE_VALUE);
					sexList.add(Constant.SEX_FEMALE_VALUE);
				}
			}
		}
		return sexList;
	}
	//add by Dannie for 1302 20140214
	public List<String> getRejectReason() throws BusinessException {
		if (rejectReason.isEmpty()) {
			synchronized (rejectReason) {
				if (rejectReason.isEmpty()) {
					rejectReason.add("Validity not expired.");
					rejectReason.add("Duplicate application.");
					rejectReason.add("Insufficient personal information of applicant.");
					rejectReason.add("Insufficient personal information of parent/legal guardian.");
					rejectReason.add("Incorrect ID No. or Passport No.");
					rejectReason.add("Incorrect date of birth.");
					rejectReason.add("Incorrect Octopus card No.");
					rejectReason.add("Not recent passport photo.");
					rejectReason.add("The applicant cancelled the application.");
					rejectReason.add("Already applied for a replacement card via Card Company. App. is not required.");
					rejectReason.add("Application for a new card without reporting loss of card to Octopus Company.");
					rejectReason.add("'Student Status' cannot be recorded on the P-Card with “PwD Status”.");
					rejectReason.add("Card not SPO.");
					rejectReason.add("Not eligible course.");
					rejectReason.add("Others");
				}
			}
		}
		return rejectReason;
	}

	public List<String> getWaiveList() throws BusinessException {
		if (waiveList.isEmpty()) {
			synchronized (waiveList) {
				if (waiveList.isEmpty()) {
					waiveList.add(Constant.BOOL_FALSE_VALUE);
					waiveList.add(Constant.BOOL_TRUE_VALUE);
				}
			}
		}
		return waiveList;
	}
	public List<Entry<String,String>> getStatusList() throws BusinessException {
		if (statusList.isEmpty()) {
			synchronized (statusList) {
				if (statusList.isEmpty()) {
					Map<String,String>map = new HashMap<String,String>();
					map.put(Constant.STATUS_F_VALUE, Constant.STATUS_F_SHOW);
					map.put(Constant.STATUS_O_VALUE, Constant.STATUS_O_SHOW);
					statusList.addAll(map.entrySet());
				}
			}
		}
		return statusList;
	}
	
	public static Map<String, ParameterVO> getParameterVOMap() throws BusinessException {
		if (parameterVOMap.isEmpty()) {
			synchronized (parameterVOMap) {
				if (parameterVOMap.isEmpty()) {
					ParameterFacade paramFacade = ServiceUtil.getService(ParameterFacade.class);
					List<ParameterVO> paramVOList = paramFacade.findAllParam();
					for (ParameterVO paramVO : paramVOList) {
						parameterVOMap.put(paramVO.getParamId(), paramVO);
					}
				}
			}
		}
		return parameterVOMap;
	}

	public static synchronized void clearParameterVOMap() {
		parameterVOMap.clear();
	}

	public List<String> getReportExportFormatList() {
		if (reportExportFormatList.isEmpty()) {
			synchronized (reportExportFormatList) {
				if (reportExportFormatList.isEmpty()) {
					reportExportFormatList.add(Constant.REPORT_EXPORT_TYPE_PDF);
					reportExportFormatList.add(Constant.REPORT_EXPORT_TYPE_EXCEL);
				}
			}
		}
		return reportExportFormatList;
	}
	
	public static synchronized void clearAllRelationship() {
		allRelationship.clear();
	}

	public List<Entry<String,String>> getAllRelationship() {
		if (allRelationship.isEmpty()) {
			synchronized (allRelationship) {
				if (allRelationship.isEmpty()) {
					Map<String,String>map = new HashMap<String,String>();
					map.put(Constant.RelationShip.FATHER_VALUE, Constant.RelationShip.FATHER_SHOW);
					map.put(Constant.RelationShip.MOTHER_VALUE, Constant.RelationShip.MOTHER_SHOW);
					map.put(Constant.RelationShip.GUARDIAN_VALUE, Constant.RelationShip.GUARDIAN_SHOW);
					allRelationship.addAll(map.entrySet());
				}
			}
		}
		return allRelationship;
	}
	
	
	public static void clearCompanyCodeNameMap() {
		companyCodeNameMap.clear();
	}
}
