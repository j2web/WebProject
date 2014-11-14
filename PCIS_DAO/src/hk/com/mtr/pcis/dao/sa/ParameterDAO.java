package hk.com.mtr.pcis.dao.sa;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.ParameterCriteriaVO;
import hk.com.mtr.pcis.vo.sa.ParameterVO;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ParameterDAO {
	public ParameterVO findByPrimaryKey(String paramId);

	public void updateParam(ParameterVO paramVO);

	public List<ParameterVO> findAllParamByPage(ParameterCriteriaVO paramCriteriaVO, PageInfoVO pageInfoVO);
	
	public List<ParameterVO> findAllParam();
}
