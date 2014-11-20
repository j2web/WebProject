package hk.com.mtr.pcis.dao.impl.co;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.co.CompanyTypeCriteriaVO;
import hk.com.mtr.pcis.dao.AppBaseDAO;
import hk.com.mtr.pcis.dao.co.CompanyTypeDAO;
import hk.com.mtr.pcis.dao.entity.co.CompanyType;
import hk.com.mtr.pcis.util.StringUtil;
import hk.com.mtr.pcis.vo.co.CompanyTypeVO;
import hk.com.mtr.pcis.vo.sa.RoleVO;

@Stateless
public class CompanyTypeDAOImpl extends AppBaseDAO implements CompanyTypeDAO {
	
	@SuppressWarnings("unused")
	private final static Log log = LogFactory.getLog(CompanyTypeDAOImpl.class);

	@Override
	public void deleteCompanyType(String type) {
		CompanyType companyType = this.entityManager.find(CompanyType.class, type);
		
		if (companyType != null) 
			this.entityManager.remove(companyType);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CompanyTypeVO> findAllCompanyTypeByPage
		(CompanyTypeCriteriaVO companyTypeCriteriaVO, 
			PageInfoVO pageInfoVO) {
		
		List<CompanyTypeVO> companyTypeList = new ArrayList<CompanyTypeVO>();
		
		String alias = "c";

		StringBuilder sqlQueryBuilder = new StringBuilder();
		StringBuilder sqlRecordCountBuilder = new StringBuilder();
		StringBuilder sqlConditionBuilder = new StringBuilder();
		String orderSql = this.buildOrderSql(pageInfoVO, alias);
		Map<String, Object> parameterMap = new HashMap<String, Object>();

		sqlQueryBuilder.append("select c from CompanyType c");
		sqlRecordCountBuilder.append("select count(c) from CompanyType c ");
		if (companyTypeCriteriaVO != null) {
			String coType = companyTypeCriteriaVO.getCoType();

			if (StringUtil.isNotEmpty(coType)) {
				if (StringUtil.isFuzzyQuery(coType))
					sqlConditionBuilder.append("c.coType like :coType");
				else
					sqlConditionBuilder.append("c.coType=:coType");
				parameterMap.put("coType", coType);
			}

			if (parameterMap.size() != 0) {
				sqlQueryBuilder.append(" WHERE ");
				sqlQueryBuilder.append(sqlConditionBuilder);

			}
		}
		String queryCountSql = sqlRecordCountBuilder.toString();
		String querySql = sqlQueryBuilder.append(orderSql).toString();

		this.buildRecordCount(pageInfoVO, queryCountSql, parameterMap);

		Query query = this.createPagedQuery(pageInfoVO, querySql, parameterMap);
		List<CompanyType> resultList = (List<CompanyType>) query.getResultList();

		CompanyTypeVO companyTypeVO = null;

		for (CompanyType companyType : resultList) {
			companyTypeVO = new CompanyTypeVO();
			this.copyProperties(companyType, companyTypeVO);
			companyTypeList.add(companyTypeVO);
		}
		return companyTypeList;		
	}

	@Override
	public CompanyTypeVO findByPrimaryKey(String type) {
		CompanyTypeVO companyTypeVO = null;
		
		CompanyType companyType = this.entityManager.find(CompanyType.class, type);
		if (companyType!= null) {
			companyTypeVO = new CompanyTypeVO();
			this.copyProperties(companyType, companyTypeVO);
		}
		
		return companyTypeVO;
	}

	@Override
	public void insertCompanyType(CompanyTypeVO companyTypeVO) {
		
		CompanyType companyType = new CompanyType();
		this.copyProperties(companyTypeVO, companyType);
		this.entityManager.persist(companyType);
	}

	@Override
	public void updateCompanyType(CompanyTypeVO companyTypeVO) {
		CompanyType companyType = new CompanyType();
		this.copyProperties(companyTypeVO, companyType);
		this.entityManager.merge(companyType);
	}

	@Override
	public void updateRole(CompanyTypeVO companyTypeVO,
			List<RoleVO> roleList) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public CompanyTypeVO findByCompanyTypeAndForm(String type,
			Integer form) {
		CompanyTypeVO companyTypeVO = null;
		
		String and = " AND ";

		StringBuilder sqlQueryBuilder = new StringBuilder();
		StringBuilder sqlConditionBuilder = new StringBuilder();
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		sqlQueryBuilder.append("select c from CardExpiryDate c");

		if (StringUtil.isNotEmpty(type)) {
			if (StringUtil.isFuzzyQuery(type))
				sqlConditionBuilder.append("c.coType like :coType");
			else
				sqlConditionBuilder.append("c.coType=:coType");
			parameterMap.put("coType", type);
		}
		
		sqlConditionBuilder.append(and).append("c.form=:form");
		parameterMap.put("form", form);

		if (parameterMap.size() > 0) {
			sqlQueryBuilder.append(" WHERE ");
			sqlQueryBuilder.append(sqlConditionBuilder);
		}

		String sqlQuery = sqlQueryBuilder.toString();
		Query query = this.createQuery(sqlQuery, parameterMap);
		CompanyType companyType = new CompanyType();
		//CompanyTypeVO = (CompanyTypeVO) this.entityManager.createQuery(sqlQuery).getSingleResult();
		List<CompanyTypeVO> resultList = query.getResultList();
		
		if (resultList.size() > 0) {
			companyTypeVO = (CompanyTypeVO) resultList.get(0);
			this.copyProperties(companyType, companyTypeVO);
			
		}
		return companyTypeVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyTypeVO> findAllCompanyType() {
		List<CompanyTypeVO> companyTypeList = new ArrayList<CompanyTypeVO>();
		String querySql = "select o from CompanyType o";
		Query query = this.entityManager.createQuery(querySql);
		List<CompanyType> resultList = (List<CompanyType>) query.getResultList();
		
		if (resultList.size() > 0) {
			for (CompanyType companyType : resultList) {
				CompanyTypeVO companyTypeVO = new CompanyTypeVO();
				this.copyProperties(companyType, companyTypeVO);
				companyTypeList.add(companyTypeVO);
			}
		}
		return companyTypeList;
	}

}
