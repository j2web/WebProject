package hk.com.mtr.pcis.dao.sa;

import java.util.List;

import javax.ejb.Local;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.RoleCriteriaVO;
import hk.com.mtr.pcis.vo.sa.RoleVO;
import hk.com.mtr.pcis.vo.sa.UserVO;

@Local
public interface RoleDAO {

	public RoleVO findByPrimaryKey(String roleId);

	public List<RoleVO> findAllRoleByPage(RoleCriteriaVO roleCriteriaVO, PageInfoVO pageInfoVO);

	public void insertRole(RoleVO roleVO);

	public void updateRole(RoleVO roleVO);

	public void deleteRole(String roleId);

	public List<UserVO> findAllUserByRoleId(String roleId);

	public void insertUserByRoleId(String roleId, List<UserVO> userList);

	public void updateUserByRoleId(String roleId, List<UserVO> userList);
}
