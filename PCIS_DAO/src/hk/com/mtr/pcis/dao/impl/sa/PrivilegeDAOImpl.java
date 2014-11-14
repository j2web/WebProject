package hk.com.mtr.pcis.dao.impl.sa;

import hk.com.mtr.pcis.dao.entity.sa.Privilege;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hk.com.mtr.pcis.vo.sa.PrivilegeTreeVO;
import hk.com.mtr.pcis.vo.sa.PrivilegeVO;
import hk.com.mtr.pcis.dao.sa.PrivilegeDAO;
import hk.com.mtr.pcis.dao.AppBaseDAO;

@Stateless
public class PrivilegeDAOImpl extends AppBaseDAO implements PrivilegeDAO {
	@SuppressWarnings("unused")
	private final static Log log = LogFactory.getLog(UserDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<PrivilegeTreeVO> findAllPrivilegeByRoleId(java.lang.String roleId) {
		StringBuilder sb = new StringBuilder();
		sb.append("Select t.id,t.Parent_Menu_Id as parentMenuId,t.res_key as name,t.selected as checked From ( ");
		sb.append("Select '1' As Type, m.Menu_Id As Id, m.Parent_Menu_Id , '' As selected,m.res_key,m.display_seq ");
		sb.append("From Pci_Menu m ");
		sb.append("Union All ");
		sb.append("Select '2' as type, f.Func_Id As Id, m.Menu_Id As Parent_Menu_Id, Decode(p.Func_Id, Null, '', 'true') As Selected,f.res_key,f.display_seq ");
		sb.append("From Pci_Func f, Pci_Menu m, Pci_Privilege p ");
		sb.append("Where f.Menu_Id = m.Menu_Id And p.Func_Id(+) = f.Func_Id And p.Role_Id(+) = ?) ");
		sb.append("t Order By t.Type,t.display_seq");

		Map<Integer, Object> parameterMap = new HashMap<Integer, Object>();
		parameterMap.put(1, roleId);
		Query query = this.createQuery(sb.toString(), parameterMap, PrivilegeTreeVO.class, true);

		List<PrivilegeTreeVO> list = (List<PrivilegeTreeVO>) query.getResultList();

		return list;
	}

	@Override
	public void insertPrivilege(PrivilegeVO privilegeVO) {

		Privilege privilege = new Privilege();
		this.copyProperties(privilegeVO, privilege);
		Privilege.PrivilegeId privilegeId = new Privilege.PrivilegeId(privilegeVO.getFunctionId(), privilegeVO.getRoleId());
		privilege.setId(privilegeId);
		this.entityManager.persist(privilege);
	}

	@Override
	public void deletePrivilegeByRoleId(java.lang.String roleId) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("roleId", roleId);
		String sql = "DELETE from Privilege t WHERE t.id.roleId = :roleId";

		Query query = this.createQuery(sql, parameterMap);
		query.executeUpdate();

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getPrivilegeByUserIdAndUrl(String userId, String url) {

		Map<Integer, Object> parameterMap = new HashMap<Integer, Object>();

		parameterMap.put(1, userId);
		parameterMap.put(2, url);
		StringBuffer sql = new StringBuffer();

		sql.append("Select Distinct p.func_id As func_id ");
		sql.append("From Pci_Privilege p, Pci_User_In_Role u, Pci_Func_Detail d ");
		sql.append("Where d.Func_Id = p.Func_Id And p.Role_Id = u.Role_Id And u.User_Id = ?1 And d.Url = ?2");
		Query query = this.createQuery(sql.toString(), parameterMap, true);

		return (List<String>) query.getResultList();

	}
}
