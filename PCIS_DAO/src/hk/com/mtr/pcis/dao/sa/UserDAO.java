package hk.com.mtr.pcis.dao.sa;

import java.util.List;

import javax.ejb.Local;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.UserCriteriaVO;
import hk.com.mtr.pcis.vo.sa.RoleVO;
import hk.com.mtr.pcis.vo.sa.UserVO;
@Local
public interface UserDAO {
	public UserVO findByPrimaryKey(String userId);	;
	public void insertUser(UserVO userVO);
	public void updateUser(UserVO userVO);
	public void deleteUser(String userId);
	public void updateRole(UserVO userVO , List<RoleVO> roleList);
	public List<UserVO> findAllUserByPage(UserCriteriaVO userCriteriaVO, PageInfoVO pageInfoVO);
}
