package hk.com.mtr.pcis.dao.sa;

import java.util.List;

import javax.ejb.Local;

import hk.com.mtr.pcis.vo.sa.MenuVO;
@Local
public interface MenuDAO {

	public List<MenuVO> findMenuByUserId(String userId);
}
