package hk.com.mtr.pcis.facade.sa;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.ParamCriteriaVO;
import hk.com.mtr.pcis.dao.sa.ParamDAO;
import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.sa.ParamVO;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class ParamFacadeBean implements ParamFacade {
	@EJB
	private ParamDAO paramDAO;

	@Override
	public List<ParamVO> findAllParamByPage(ParamCriteriaVO paramCriteriaVO, PageInfoVO pageInfoVO) throws BusinessException {
		List<ParamVO> paramVOList = null;
		try {
			paramVOList = paramDAO.findAllParamByPage(paramCriteriaVO, pageInfoVO);
		} catch (Exception e) {
			throw new BusinessException("Find all param_maint function failed:", e);
		}
		return paramVOList;
	}

	@Override
	public ParamVO findByPrimaryKey(String paramId) throws BusinessException {
		ParamVO paramVO = null;
		try {
			paramVO = paramDAO.findByPrimaryKey(paramId);
		} catch (Exception e) {
			throw new BusinessException("Find all param_maint function failed:", e);
		}
		return paramVO;
	}

	@Override
	public void updateParam(ParamVO paramVO) throws BusinessException {
		try {
			paramDAO.updateParam(paramVO);
		} catch (Exception e) {
			throw new BusinessException("Find all param_maint function failed:", e);
		}
	}

	@Override
	public List<ParamVO> findAllParam() throws BusinessException {
		List<ParamVO> paramVOList = null;
		try {
			paramVOList = paramDAO.findAllParam();
		} catch (Exception e) {
			throw new BusinessException("Find all param_maint function failed:", e);
		}
		return paramVOList;
	}
}
