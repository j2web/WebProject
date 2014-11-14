package hk.com.mtr.pcis.facade.mf;

import hk.com.mtr.pcis.facade.mf.StationFacade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.mf.StationVO;
import hk.com.mtr.pcis.dao.mf.StationDAO;

@Stateless
public class StationFacadeBean implements StationFacade {

	@EJB
	private StationDAO stationDAO;

	@Override
	public List<StationVO> findAllStation() throws BusinessException {
		List<StationVO> stationList = null;
		try {
			stationList = stationDAO.findAllStation();
		} catch (Exception e) {
			throw new BusinessException("Find all staionMaster failed:", e);
		}
		return stationList;
	}

}
