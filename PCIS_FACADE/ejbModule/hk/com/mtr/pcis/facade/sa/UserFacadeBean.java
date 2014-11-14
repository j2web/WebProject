package hk.com.mtr.pcis.facade.sa;

import hk.com.mtr.pcis.facade.sa.UserFacade;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.UserCriteriaVO;
import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.sa.UserVO;
import hk.com.mtr.pcis.dao.sa.UserDAO;

@Stateless
public class UserFacadeBean implements UserFacade {

	@EJB
	private UserDAO userDAO;

	@Override
	public void deleteUser(String userId) throws BusinessException {
		try {
			userDAO.deleteUser(userId);
		} catch (Exception e) {
			throw new BusinessException("Delete user failed:", e);
		}
	}

	@Override
	public List<UserVO> findAllUserByPage(UserCriteriaVO userCriteriaVO, PageInfoVO pageInfoVO) throws BusinessException {
		List<UserVO> list = null;

		try {
			list = userDAO.findAllUserByPage(userCriteriaVO, pageInfoVO);
		} catch (Exception e) {
			throw new BusinessException("Find user by criteria failed:", e);
		}
		return list;
	}

	@Override
	public UserVO findByPrimaryKey(String userId) throws BusinessException {
		UserVO userVO = null;

		try {
			userVO = userDAO.findByPrimaryKey(userId);
		} catch (Exception e) {
			throw new BusinessException("Find user by primary key failed:", e);
		}
		return userVO;
	}

	@Override
	public void insertUser(UserVO userVO) throws BusinessException {
		try {
			userDAO.insertUser(userVO);
		} catch (Exception e) {
			throw new BusinessException("Insert user failed:", e);
		}

	}

	@Override
	public void updateUser(UserVO userVO) throws BusinessException {
		try {
			userDAO.updateUser(userVO);
		} catch (Exception e) {
			throw new BusinessException("Update user failed:", e);
		}
	}

}
