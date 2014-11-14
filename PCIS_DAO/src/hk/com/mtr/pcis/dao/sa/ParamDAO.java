package hk.com.mtr.pcis.dao.sa;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.ParamCriteriaVO;
import hk.com.mtr.pcis.vo.sa.ParamVO;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ParamDAO {
	public ParamVO findByPrimaryKey(String paramId);

	public void updateParam(ParamVO paramVO);

	public List<ParamVO> findAllParamByPage(ParamCriteriaVO paramCriteriaVO, PageInfoVO pageInfoVO);
	
	public List<ParamVO> findAllParam();
}
