package hk.com.mtr.pcis.facade.sa;

import java.util.List;
import javax.ejb.Local;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.UserCriteriaVO;
import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.sa.UserVO;

@Local
public interface UserFacade {
	public UserVO findByPrimaryKey(String userId) throws BusinessException;

	public List<UserVO> findAllUserByPage(UserCriteriaVO userCriteriaVO, PageInfoVO pageInfoVO) throws BusinessException;

	public void insertUser(UserVO userVO) throws BusinessException;

	public void updateUser(UserVO userVO) throws BusinessException;

	public void deleteUser(String userId) throws BusinessException;
}
