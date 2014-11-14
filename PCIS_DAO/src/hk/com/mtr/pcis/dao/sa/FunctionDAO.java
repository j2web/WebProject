package hk.com.mtr.pcis.dao.sa;

import java.util.List;
import javax.ejb.Local;

import hk.com.mtr.pcis.vo.sa.PrivilegeTreeVO;

@Local
public interface FunctionDAO {
	public List<PrivilegeTreeVO> findAllFunction();
}
