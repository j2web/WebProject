package hk.com.mtr.pcis.facade.mf;

import java.util.List;

import javax.ejb.Local;

import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.mf.StationVO;

@Local
public interface StationFacade {

	public List<StationVO> findAllStation() throws BusinessException;
}
