package hk.com.mtr.pcis.criteria.sa;

import hk.com.mtr.pcis.criteria.AppBaseCriteriaVO;

public class ParameterCriteriaVO extends AppBaseCriteriaVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String paramId;
	
	public ParameterCriteriaVO(String paramId) {
		setParamId(paramId);
	}
	
	public ParameterCriteriaVO() {
	
	}
	
	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}
}
