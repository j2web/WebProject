package hk.com.mtr.pcis.facade.sa;

import java.util.List;

import javax.ejb.Local;

import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.sa.PrivilegeTreeVO;

@Local
public interface FunctionFacade {
	public List<PrivilegeTreeVO> findAllFunction() throws BusinessException;

}
