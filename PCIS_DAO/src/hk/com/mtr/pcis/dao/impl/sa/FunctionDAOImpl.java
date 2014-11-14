package hk.com.mtr.pcis.dao.impl.sa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hk.com.mtr.pcis.vo.sa.PrivilegeTreeVO;
import hk.com.mtr.pcis.dao.sa.FunctionDAO;
import hk.com.mtr.pcis.dao.AppBaseDAO;

@Stateless
public class FunctionDAOImpl extends AppBaseDAO implements FunctionDAO {
	@SuppressWarnings("unused")
	private final static Log log = LogFactory.getLog(FunctionDAOImpl.class);
	@Override
	@SuppressWarnings("unchecked")
	public List<PrivilegeTreeVO> findAllFunction() {
		StringBuilder sql = new StringBuilder();
		sql.append("Select t.id as id,t.Parent_Menu_Id as parentMenuId,t.res_key as name From ( ");
		sql.append("Select '1' As Type, m.Menu_Id As Id, m.Parent_Menu_Id ,m.res_key,m.display_seq ");
		sql.append("From Pci_Menu m ");
		sql.append("Union All ");
		sql.append("Select '2' as type, f.Func_Id As Id, m.Menu_Id As Parent_Menu_Id, f.res_key, f.display_seq ");
		sql.append("From Pci_Func f, Pci_Menu m ");
		sql.append("Where f.Menu_Id = m.Menu_Id) t ");
		sql.append("Order By t.Type,t.display_seq");
		Map<Integer, Object> parameterMap = new HashMap<Integer, Object>();
		Query query = this.createQuery(sql.toString(), parameterMap, PrivilegeTreeVO.class, true);
		List<PrivilegeTreeVO> list = (List<PrivilegeTreeVO>) query.getResultList();
		return list;
	}

}
