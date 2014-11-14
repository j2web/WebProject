package hk.com.mtr.pcis.vo.sa;

import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;

import hk.com.mtr.pcis.vo.AppBaseVO;
/**
 * @ClassName: ParamVO 
 * @Description: vo  
 * @author Suny
 * @date 2010-7-21
 */
public class ParameterVO extends AppBaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String paramId;
	private Integer paramIntValue;
	private String paramCharValue;
	private Date paramDateValue;
	private String description;
	
	public ParameterVO() {

	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof ParameterVO)) {
			return false;
		}

		ParameterVO other = (ParameterVO) o;

		if (getParamId() == null) {
			return false;
		}

		return getParamId().equals(other.getParamId());
	}

	public int hashCode() {
		return new HashCodeBuilder().append(this.getParamId()).toHashCode();
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	}

	public String getParamCharValue() {
		return paramCharValue;
	}

	public void setParamCharValue(String paramCharValue) {
		this.paramCharValue = paramCharValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getParamIntValue() {
		return paramIntValue;
	}

	public void setParamIntValue(Integer paramIntValue) {
		this.paramIntValue = paramIntValue;
	}

	public Date getParamDateValue() {
		return paramDateValue;
	}

	public void setParamDateValue(Date paramDateValue) {
		this.paramDateValue = paramDateValue;
	}
}
