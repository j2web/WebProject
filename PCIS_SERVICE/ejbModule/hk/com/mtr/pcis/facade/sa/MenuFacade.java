package hk.com.mtr.pcis.facade.sa;
import java.util.List;

import javax.ejb.Local;

import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.sa.MenuVO;

@Local
public interface MenuFacade {

	public List<MenuVO> findMenuByUserId(String userId) throws BusinessException;
}
