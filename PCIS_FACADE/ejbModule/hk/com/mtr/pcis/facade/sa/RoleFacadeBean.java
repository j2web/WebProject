package hk.com.mtr.pcis.facade.sa;

import hk.com.mtr.pcis.facade.sa.RoleFacade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.RoleCriteriaVO;
import hk.com.mtr.pcis.exception.BusinessException;

import hk.com.mtr.pcis.vo.sa.PrivilegeVO;
import hk.com.mtr.pcis.vo.sa.RoleVO;
import hk.com.mtr.pcis.vo.sa.UserVO;

import hk.com.mtr.pcis.dao.sa.PrivilegeDAO;
import hk.com.mtr.pcis.dao.sa.RoleDAO;

/**
 * Session Bean implementation class RoleFacadeBean
 */
@Stateless
public class RoleFacadeBean implements RoleFacade {

	@EJB
	private RoleDAO roleDAO;
	@EJB
	private PrivilegeDAO privilegeDAO;

	public RoleFacadeBean() {

	}

	@Override
	public void deleteRole(String roleId) throws BusinessException {
		try {		
			roleDAO.deleteRole(roleId);
		} catch (Exception e) {
			throw new BusinessException("Delete role failed:", e);
		}
	}

	@Override
	public List<UserVO> findAllUserByRoleId(String roleId) throws BusinessException {
		List<UserVO> list = null;
		try {
			list = roleDAO.findAllUserByRoleId(roleId);
		} catch (Exception e) {
			throw new BusinessException("Find all user by roleId failed:", e);
		}
		return list;
	}

	@Override
	public void insertRole(RoleVO roleVO, List<PrivilegeVO> privilegeList, List<UserVO> userList) throws BusinessException {
		String roleId = roleVO.getRoleId();
		try {
			roleDAO.insertRole(roleVO);
		} catch (Exception e) {
			throw new BusinessException("Insert role failed:", e);
		}
		
		for (PrivilegeVO privilegeVO : privilegeList) {
			try {			
				privilegeDAO.insertPrivilege(privilegeVO);
			} catch (Exception e) {
				throw new BusinessException("Insert privilege failed:", e);
			}
		}
		
		try {
			roleDAO.insertUserByRoleId(roleId, userList);
		} catch (Exception e) {
			throw new BusinessException("Insert privilege failed:", e);
		}
	}

	@Override
	public void updateRole(RoleVO roleVO, List<PrivilegeVO> privilegeList, List<UserVO> userList) throws BusinessException {
		String roleId = roleVO.getRoleId();
		try {
			roleDAO.updateRole(roleVO);
		} catch (Exception e) {
			throw new BusinessException("update role failed:", e);
		}
		try {		
			privilegeDAO.deletePrivilegeByRoleId(roleId);
		} catch (Exception e) {
			throw new BusinessException("update role failed:", e);
		}
		for (PrivilegeVO privilegeVO : privilegeList) {
			try {
				privilegeDAO.insertPrivilege(privilegeVO);
			} catch (Exception e) {
				throw new BusinessException("Insert privilege failed:", e);
			}
		}
		try {
			roleDAO.updateUserByRoleId(roleId, userList);
		} catch (Exception e) {
			throw new BusinessException("Update user by roleId failed:", e);
		}
	}

	@Override
	public List<RoleVO> findAllRoleByPage(RoleCriteriaVO roleCriteriaVO, PageInfoVO pageInfoVO) throws BusinessException {
		List<RoleVO> list = null;
		try {
			list = roleDAO.findAllRoleByPage(roleCriteriaVO, pageInfoVO);
		} catch (Exception e) {
			throw new BusinessException("Find all role by page failed:", e);
		}
		return list;
	}

	@Override
	public RoleVO findByPrimaryKey(String roleId) throws BusinessException {
		RoleVO roleVO = null;
		try {
			roleVO = roleDAO.findByPrimaryKey(roleId);
		} catch (Exception e) {
			throw new BusinessException("Find role by primary key failed:", e);
		}
		return roleVO;
	}

}
