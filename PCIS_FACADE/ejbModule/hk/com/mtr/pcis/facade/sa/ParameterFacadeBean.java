package hk.com.mtr.pcis.facade.sa;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.ParameterCriteriaVO;
import hk.com.mtr.pcis.dao.sa.ParameterDAO;
import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.sa.ParameterVO;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ParameterFacadeBean implements ParameterFacade {
	@EJB
	private ParameterDAO paramDAO;

	@Override
	public List<ParameterVO> findAllParamByPage(ParameterCriteriaVO paramCriteriaVO, PageInfoVO pageInfoVO) throws BusinessException {
		List<ParameterVO> paramVOList = null;
		try {
			paramVOList = paramDAO.findAllParamByPage(paramCriteriaVO, pageInfoVO);
		} catch (Exception e) {
			throw new BusinessException("Find all param_maint function failed:", e);
		}
		return paramVOList;
	}

	@Override
	public ParameterVO findByPrimaryKey(String paramId) throws BusinessException {
		ParameterVO paramVO = null;
		try {
			paramVO = paramDAO.findByPrimaryKey(paramId);
		} catch (Exception e) {
			throw new BusinessException("Find all param_maint function failed:", e);
		}
		return paramVO;
	}

	@Override
	public void updateParam(ParameterVO paramVO) throws BusinessException {
		try {
			paramDAO.updateParam(paramVO);
		} catch (Exception e) {
			throw new BusinessException("Find all param_maint function failed:", e);
		}
	}

	@Override
	public List<ParameterVO> findAllParam() throws BusinessException {
		List<ParameterVO> paramVOList = null;
		try {
			paramVOList = paramDAO.findAllParam();
		} catch (Exception e) {
			throw new BusinessException("Find all param_maint function failed:", e);
		}
		return paramVOList;
	}
}
