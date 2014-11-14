package hk.com.mtr.pcis.dao.impl.mf;

import hk.com.mtr.pcis.dao.entity.mf.Station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;


import hk.com.mtr.pcis.vo.mf.StationVO;
import hk.com.mtr.pcis.dao.AppBaseDAO;
import hk.com.mtr.pcis.dao.mf.StationDAO;

@Stateless
public class StationDAOImpl extends AppBaseDAO implements StationDAO {

	

	@SuppressWarnings("unchecked")
	@Override
	public List<StationVO> findAllStation() {
		List<StationVO> stationList = new ArrayList<StationVO>();
		StringBuilder sqlQueryBuilder = new StringBuilder();
		sqlQueryBuilder.append("select o from Station o order by o.stationCode");
		Map<String, Object> parameterMap = new HashMap<String, Object>();

		Query query = this.createQuery(sqlQueryBuilder.toString(), parameterMap);

		List<Station> resultList = query.getResultList();

		StationVO stationVO = null;
		for (Station station : resultList) {
			stationVO = new StationVO();
			this.copyProperties(station, stationVO);
			stationList.add(stationVO);
		}
		return stationList;
	}

}
