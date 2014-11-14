package hk.com.mtr.pcis.facade.sa;

import java.util.List;

import javax.ejb.Local;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.RoleCriteriaVO;
import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.sa.PrivilegeVO;
import hk.com.mtr.pcis.vo.sa.RoleVO;
import hk.com.mtr.pcis.vo.sa.UserVO;

@Local
public interface RoleFacade {

	public RoleVO findByPrimaryKey(String roleId) throws BusinessException;

	public List<UserVO> findAllUserByRoleId(String roleId) throws BusinessException;

	public List<RoleVO> findAllRoleByPage(RoleCriteriaVO roleCriteriaVO, PageInfoVO pageInfoVO) throws BusinessException;

	public void insertRole(RoleVO roleVO, List<PrivilegeVO> privilegeList, List<UserVO> userList) throws BusinessException;

	public void updateRole(RoleVO roleVO, List<PrivilegeVO> privilegeList, List<UserVO> userList) throws BusinessException;

	public void deleteRole(String roleId) throws BusinessException;

}
