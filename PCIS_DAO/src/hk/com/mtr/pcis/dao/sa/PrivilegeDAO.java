package hk.com.mtr.pcis.dao.sa;

import java.util.List;
import javax.ejb.Local;

import hk.com.mtr.pcis.vo.sa.PrivilegeTreeVO;
import hk.com.mtr.pcis.vo.sa.PrivilegeVO;

@Local
public interface PrivilegeDAO {
	public List<PrivilegeTreeVO> findAllPrivilegeByRoleId(java.lang.String roleId);

	public void insertPrivilege(PrivilegeVO privilegeVO);

	public void deletePrivilegeByRoleId(java.lang.String roleId);

	public List<String> getPrivilegeByUserIdAndUrl(String userId, String url);
}