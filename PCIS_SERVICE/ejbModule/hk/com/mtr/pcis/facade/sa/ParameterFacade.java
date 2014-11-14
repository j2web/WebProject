package hk.com.mtr.pcis.facade.sa;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.ParameterCriteriaVO;
import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.sa.ParameterVO;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ParameterFacade {
	ParameterVO findByPrimaryKey(String paramId)throws BusinessException;

	void updateParam(ParameterVO paramVO)throws BusinessException;

	List<ParameterVO> findAllParamByPage(ParameterCriteriaVO paramCriteriaVO, PageInfoVO pageInfoVO)throws BusinessException;
	
	List<ParameterVO> findAllParam() throws BusinessException;
}
