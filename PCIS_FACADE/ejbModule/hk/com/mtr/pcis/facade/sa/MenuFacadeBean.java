package hk.com.mtr.pcis.facade.sa;

import hk.com.mtr.pcis.facade.sa.MenuFacade;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.sa.MenuVO;
import hk.com.mtr.pcis.dao.sa.MenuDAO;

/**
 * Session Bean implementation class MenuFacadeBean
 */
@Stateless
public class MenuFacadeBean implements MenuFacade {

	@EJB
	private MenuDAO menuDAO;

	public MenuFacadeBean() {
	}

	@Override
	public List<MenuVO> findMenuByUserId(String userId) throws BusinessException {
		List<MenuVO> list = null;
		try {
			list = menuDAO.findMenuByUserId(userId);
		} catch (Exception e) {
			throw new BusinessException("Find menu failed:", e);
		}
		return list;
	}

}
