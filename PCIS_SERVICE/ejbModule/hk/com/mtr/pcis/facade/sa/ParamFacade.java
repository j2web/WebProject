package hk.com.mtr.pcis.facade.sa;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.ParamCriteriaVO;
import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.sa.ParamVO;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ParamFacade {
	ParamVO findByPrimaryKey(String paramId)throws BusinessException;

	void updateParam(ParamVO paramVO)throws BusinessException;

	List<ParamVO> findAllParamByPage(ParamCriteriaVO paramCriteriaVO, PageInfoVO pageInfoVO)throws BusinessException;
	
	List<ParamVO> findAllParam() throws BusinessException;
}
