package hk.com.mtr.pcis.dao.impl.sa;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.ParameterCriteriaVO;
import hk.com.mtr.pcis.dao.AppBaseDAO;
import hk.com.mtr.pcis.dao.entity.sa.Parameter;
import hk.com.mtr.pcis.dao.sa.ParameterDAO;
import hk.com.mtr.pcis.util.StringUtil;
import hk.com.mtr.pcis.vo.sa.ParameterVO;
@Stateless
public class ParameterDAOImpl extends AppBaseDAO implements ParameterDAO {
	
	private final static Log log = LogFactory.getLog(ParameterDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ParameterVO> findAllParamByPage(ParameterCriteriaVO paramCriteriaVO, PageInfoVO pageInfoVO) {
		List<ParameterVO> paramVOList = new ArrayList<ParameterVO>();
		StringBuilder sqlQueryBuilder = new StringBuilder();
		StringBuilder sqlRecordCountBuilder = new StringBuilder();
		StringBuilder sqlConditionBuilder = new StringBuilder();
		String orderSql = this.buildOrderSql(pageInfoVO, "o");
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		
		sqlQueryBuilder.append("select o from Parameter o");
		sqlRecordCountBuilder.append("select count(o) from Parameter o ");
		
		if (paramCriteriaVO != null) {
			if (StringUtil.isNotEmpty(paramCriteriaVO.getParamId())) {
				if (StringUtil.isFuzzyQuery(paramCriteriaVO.getParamId()))
					sqlConditionBuilder.append("o.paramId like :paramId");
				else
					sqlConditionBuilder.append("o.paramId=:paramId");
				parameterMap.put("paramId", paramCriteriaVO.getParamId());
			}
			
			if (parameterMap.size() != 0) {
				sqlQueryBuilder.append(" WHERE ");
				sqlQueryBuilder.append(sqlConditionBuilder);

				sqlRecordCountBuilder.append(" WHERE ");
				sqlRecordCountBuilder.append(sqlConditionBuilder);

			}
		}
		
		String queryCountSql = sqlRecordCountBuilder.toString();
		String querySql = sqlQueryBuilder.append(orderSql).toString();

		this.buildRecordCount(pageInfoVO, queryCountSql, parameterMap);

		Query query = this.createPagedQuery(pageInfoVO, querySql, parameterMap);
		
		List<Parameter> paramList = (List<Parameter>)query.getResultList();
		if (paramList.size() > 0) {
			ParameterVO paramVO = null;
			try {
				for (Parameter param : paramList) {
					paramVO = new ParameterVO();
	//				copyEntity2VO(param,paramVO);
					PropertyUtils.copyProperties(paramVO, param);
					paramVOList.add(paramVO);
				}
			} catch (IllegalAccessException e) {
				log.error("IllegalAccessException ", e);
			} catch (InvocationTargetException e) {
				log.error("InvocationTargetException ", e);
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				log.error("NoSuchMethodException ", e);
			}
		}
		return paramVOList;
	}
	
	@SuppressWarnings("unchecked")
	public List<ParameterVO> findAllParam() {
		List<ParameterVO> paramVOList = new ArrayList<ParameterVO>();
		String sql = "select o from Parameter o";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		Query query = this.createQuery(sql, parameterMap, Parameter.class, false);
		List<Parameter> paramList = (List<Parameter>)query.getResultList();
		if (paramList.size() > 0) {
			ParameterVO paramVO = null;
			try {
				for (Parameter param : paramList) {
					paramVO = new ParameterVO();
//					copyEntity2VO(param,paramVO);
					PropertyUtils.copyProperties(paramVO, param);
					paramVOList.add(paramVO);
				}
			} catch (IllegalAccessException e) {
				log.error("IllegalAccessException ", e);
			} catch (InvocationTargetException e) {
				log.error("InvocationTargetException ", e);
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				log.error("NoSuchMethodException ", e);
			}
		}
		return paramVOList;
	}
	@Override
	public ParameterVO findByPrimaryKey(String paramId) {
		ParameterVO paramVO = null;
		Parameter param = this.entityManager.find(Parameter.class, paramId);
		if (param != null){
			paramVO = new ParameterVO();
//			copyEntity2VO(param, paramVO);
			try {
				PropertyUtils.copyProperties(paramVO, param);
			} catch (IllegalAccessException e) {
				log.error("IllegalAccessException ", e);
			} catch (InvocationTargetException e) {
				log.error("InvocationTargetException ", e);
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				log.error("NoSuchMethodException ", e);
			}
		}
		return paramVO;
	}

	@Override
	public void updateParam(ParameterVO paramVO) {
		Parameter param = new Parameter();
		try {
			PropertyUtils.copyProperties(param, paramVO);
		} catch (IllegalAccessException e) {
			log.error("IllegalAccessException ", e);
		} catch (InvocationTargetException e) {
			log.error("InvocationTargetException ", e);
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			log.error("NoSuchMethodException ", e);
		}
//		copyVO2Entity(paramVO, param);
		this.entityManager.merge(param);
	}
	
//	private void copyVO2Entity(ParameterVO paramVO, Parameter param){
//		param.setDescription(paramVO.getDescription());
//		param.setParamCharValue(paramVO.getParamCharValue());
//		param.setParamDateValue(paramVO.getParamDateValue());
//		param.setParamId(paramVO.getParamId());
//		param.setParamIntValue(paramVO.getParamIntValue());
//		param.setUpdateTime(paramVO.getUpdateTime());
//		param.setUpdateUser(paramVO.getUpdateUser());
//	}
//	
//	private void copyEntity2VO(Parameter param, ParameterVO paramVO){
//		paramVO.setDescription(param.getDescription());
//		paramVO.setParamCharValue(param.getParamCharValue());
//		paramVO.setParamDateValue(param.getParamDateValue());
//		paramVO.setParamId(param.getParamId());
//		paramVO.setParamIntValue(param.getParamIntValue());
//		paramVO.setUpdateTime(param.getUpdateTime());
//		paramVO.setUpdateUser(param.getUpdateUser());
//	}
}
