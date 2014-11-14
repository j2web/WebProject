package hk.com.mtr.pcis.facade.sa;

import java.util.List;

import javax.ejb.Local;

import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.sa.PrivilegeTreeVO;

@Local
public interface PrivilegeFacade {
	public List<PrivilegeTreeVO> findAllPrivilegeByRoleId(java.lang.String roleId) throws BusinessException;

	public List<String> getPrivilegeByUserIdAndUrl(String userId, String url) throws BusinessException;

}
