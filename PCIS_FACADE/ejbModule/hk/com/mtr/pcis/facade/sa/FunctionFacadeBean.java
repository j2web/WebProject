package hk.com.mtr.pcis.facade.sa;

import hk.com.mtr.pcis.facade.sa.FunctionFacade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.sa.PrivilegeTreeVO;
import hk.com.mtr.pcis.dao.sa.FunctionDAO;

@Stateless
public class FunctionFacadeBean implements FunctionFacade {
	@EJB
	private FunctionDAO functionDAO;
	@Override
	public List<PrivilegeTreeVO> findAllFunction() throws BusinessException{
		List<PrivilegeTreeVO> list = null;
		try {
			list = functionDAO.findAllFunction();
		} catch (Exception e) {
			throw new BusinessException("Find all function failed:", e);
		}
		return list;
	}

}
