package hk.com.mtr.pcis.dao.impl.sa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;
import hk.com.mtr.pcis.vo.sa.MenuVO;
import hk.com.mtr.pcis.dao.AppBaseDAO;
import hk.com.mtr.pcis.dao.sa.MenuDAO;

@Stateless
public class MenuDAOImp extends AppBaseDAO implements MenuDAO {
	@Override
	@SuppressWarnings("unchecked")
	public List<MenuVO> findMenuByUserId(String userId) {
		Map<Integer, Object> parameterMap = new HashMap<Integer, Object>();
		parameterMap.put(1, userId);

		StringBuffer sql = new StringBuffer();
		sql.append("Select Distinct t.Menu_Id as menuId, t.Parent_Menu_Id as parentMenuId, t.Res_Key as resourceKey,t.location, t.Display_Seq as displaySeq ");
		sql.append("From (Select Distinct m.Menu_Id, m.Res_Key,m.location, m.Display_Seq, m.Parent_Menu_Id, ");
		sql.append("Decode(p.Func_Id, Null, 'N', 'Y') As Selected ");
		sql.append("From Pci_Menu m ");
		sql.append("Left Join Pci_Func f On m.Menu_Id = f.Menu_Id ");
		sql.append("Left Join Pci_User_In_Role r On r.User_Id = ?1 ");
		sql.append("Left Join Pci_Privilege p On r.Role_Id = p.Role_Id And p.Func_Id = f.Func_Id) t ");
		sql.append("Connect By Prior t.Parent_Menu_Id = t.Menu_Id ");
		sql.append("Start With t.Selected = 'Y'");
		Query query = this.createQuery(sql.toString(), parameterMap, MenuVO.class, true);
		List<MenuVO> list = (List<MenuVO>) query.getResultList();
		return list;
	}

}
