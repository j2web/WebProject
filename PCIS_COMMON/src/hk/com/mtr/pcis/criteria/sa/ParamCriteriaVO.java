package hk.com.mtr.pcis.criteria.sa;

import hk.com.mtr.pcis.criteria.AppBaseCriteriaVO;

public class ParamCriteriaVO extends AppBaseCriteriaVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String paramId;
	
	public ParamCriteriaVO(String paramId) {
		setParamId(paramId);
	}
	
	public ParamCriteriaVO() {
	
	}
	
	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
}
