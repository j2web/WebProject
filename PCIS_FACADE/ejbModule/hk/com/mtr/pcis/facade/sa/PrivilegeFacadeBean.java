package hk.com.mtr.pcis.facade.sa;

import hk.com.mtr.pcis.facade.sa.PrivilegeFacade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.sa.PrivilegeTreeVO;
import hk.com.mtr.pcis.dao.sa.PrivilegeDAO;

@Stateless
public class PrivilegeFacadeBean implements PrivilegeFacade {
	@EJB
	private PrivilegeDAO privilegeDAO;
	@Override
	public List<PrivilegeTreeVO> findAllPrivilegeByRoleId(java.lang.String roleId) throws BusinessException {
		List<PrivilegeTreeVO> list = null;
		try {
			list = privilegeDAO.findAllPrivilegeByRoleId(roleId);
		} catch (Exception e) {
			throw new BusinessException("Find all privilege by roleId failed:", e);
		}
		return list;
	}
	@Override
	public List<String> getPrivilegeByUserIdAndUrl(String userId, String url) throws BusinessException {
		List<String> list = null;
		try { 
			list = privilegeDAO.getPrivilegeByUserIdAndUrl(userId, url);
		} catch (Exception e) {
			throw new BusinessException("Get privilege by userId and url failed:", e);
		}
		return list;
	}

	

}
