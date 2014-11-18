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
import hk.com.mtr.pcis.criteria.co.CardExpiryDateCriteriaVO;
import hk.com.mtr.pcis.dao.AppBaseDAO;
import hk.com.mtr.pcis.dao.co.CardExpiryDateDAO;
import hk.com.mtr.pcis.dao.entity.co.CardExpiryDate;
import hk.com.mtr.pcis.util.StringUtil;
import hk.com.mtr.pcis.vo.co.CardExpiryDateVO;
import hk.com.mtr.pcis.vo.sa.RoleVO;

@Stateless
public class CardExpiryDateDAOImpl extends AppBaseDAO implements CardExpiryDateDAO {
	
	@SuppressWarnings("unused")
	private final static Log log = LogFactory.getLog(CardExpiryDateDAOImpl.class);

	@Override
	public void deleteCardExpiryDate(String companyType) {
		CardExpiryDate cardExpiryDate = this.entityManager.find(CardExpiryDate.class, companyType);
		
		if (cardExpiryDate != null) 
			this.entityManager.remove(cardExpiryDate);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CardExpiryDateVO> findAllCardExpiryDateByPage(
			CardExpiryDateCriteriaVO cardExpiryDateCriteriaVO,
			PageInfoVO pageInfoVO) {
		
		List<CardExpiryDateVO> cardExpiryDateList = new ArrayList<CardExpiryDateVO>();
		
		String alias = "o";

		StringBuilder sqlQueryBuilder = new StringBuilder();
		StringBuilder sqlRecordCountBuilder = new StringBuilder();
		StringBuilder sqlConditionBuilder = new StringBuilder();
		String orderSql = this.buildOrderSql(pageInfoVO, alias, true);
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		
		sqlQueryBuilder.append("select * from Card_Expiry_Date o");
		sqlRecordCountBuilder.append("select count(*) from Card_Expiry_Date o ");
		if (cardExpiryDateCriteriaVO != null) {
			String coType = cardExpiryDateCriteriaVO.getCoType();

			if (StringUtil.isNotEmpty(coType)) {
				if (StringUtil.isFuzzyQuery(coType))
					sqlConditionBuilder.append("o.co_Type like :coType");
				else
					sqlConditionBuilder.append("o.co_Type=:coType");
				parameterMap.put("coType", coType);
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

		this.buildRecordCount(pageInfoVO, queryCountSql, parameterMap, true);

		
		Query query = this.createPagedQuery(pageInfoVO, querySql, parameterMap, true, CardExpiryDate.class);
		List resultList =  query.getResultList();

		System.out.println("evan ======= resultList:" + resultList.size());
		for (int i = 0; i < resultList.size(); i ++) {
			CardExpiryDateVO cardExpiryDateVO = new CardExpiryDateVO();
			CardExpiryDate cardExpiryDate = (CardExpiryDate) resultList.get(i);
			this.copyProperties(cardExpiryDate, cardExpiryDateVO);
			cardExpiryDateList.add(cardExpiryDateVO);
			System.out.println("Test: coType:" + cardExpiryDate.getCoType() + " form:" + cardExpiryDate.getForm());
			
		}
		/*CardExpiryDateVO cardExpiryDateVO = null;

		for (CardExpiryDate cardExpiryDate : resultList) {
			cardExpiryDateVO = new CardExpiryDateVO();
			this.copyProperties(cardExpiryDate, cardExpiryDateVO);
			cardExpiryDateList.add(cardExpiryDateVO);
			System.out.println("Test: coType:" + cardExpiryDate.getCoType() + " form:" + cardExpiryDate.getForm());
		}*/
		return cardExpiryDateList;		
	}
/*	@Override
	@SuppressWarnings("unchecked")
	public List<CardExpiryDateVO> findAllCardExpiryDateByPage(
			CardExpiryDateCriteriaVO cardExpiryDateCriteriaVO,
			PageInfoVO pageInfoVO) {
		
		List<CardExpiryDateVO> cardExpiryDateList = new ArrayList<CardExpiryDateVO>();
		
		String alias = "o";
		
		StringBuilder sqlQueryBuilder = new StringBuilder();
		StringBuilder sqlRecordCountBuilder = new StringBuilder();
		StringBuilder sqlConditionBuilder = new StringBuilder();
		String orderSql = this.buildOrderSql(pageInfoVO, alias);
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		
		sqlQueryBuilder.append("select * from CardExpiryDate o");
		sqlRecordCountBuilder.append("select count(o) from CardExpiryDate o ");
		if (cardExpiryDateCriteriaVO != null) {
			String coType = cardExpiryDateCriteriaVO.getCoType();
			
			if (StringUtil.isNotEmpty(coType)) {
				if (StringUtil.isFuzzyQuery(coType))
					sqlConditionBuilder.append("o.coType like :coType");
				else
					sqlConditionBuilder.append("o.coType=:coType");
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
		List<CardExpiryDate> resultList = (List<CardExpiryDate>) query.getResultList();
		
		CardExpiryDateVO cardExpiryDateVO = null;
		
		for (CardExpiryDate cardExpiryDate : resultList) {
			cardExpiryDateVO = new CardExpiryDateVO();
			this.copyProperties(cardExpiryDate, cardExpiryDateVO);
			cardExpiryDateList.add(cardExpiryDateVO);
			System.out.println("Test: coType:" + cardExpiryDate.getCoType() + " form:" + cardExpiryDate.getForm());
		}
		return cardExpiryDateList;		
	}
*/
	@Override
	public CardExpiryDateVO findByPrimaryKey(String companyType) {
		CardExpiryDateVO cardExpiryDateVO = null;
		
		CardExpiryDate cardExpiryDate = this.entityManager.find(CardExpiryDate.class, companyType);
		if (cardExpiryDate != null) {
			cardExpiryDateVO = new CardExpiryDateVO();
			this.copyProperties(cardExpiryDate, cardExpiryDateVO);
		}
		
		return cardExpiryDateVO;
	}

	@Override
	public void insertCardExpiryDate(CardExpiryDateVO cardExpiryDateVO) {
		System.out.println("evan:inserted....");
		CardExpiryDate cardExpiryDate = new CardExpiryDate();
		this.copyProperties(cardExpiryDateVO, cardExpiryDate);
		this.entityManager.persist(cardExpiryDate);
	}

	@Override
	public void updateCardExpiryDate(CardExpiryDateVO cardExpiryDateVO) {
		CardExpiryDate cardExpiryDate = new CardExpiryDate();
		this.copyProperties(cardExpiryDateVO, cardExpiryDate);
		this.entityManager.merge(cardExpiryDate);
	}

	@Override
	public void updateRole(CardExpiryDateVO cardExpiryDateVO,
			List<RoleVO> roleList) {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public CardExpiryDateVO findByCompanyTypeAndForm(String companyType,
			Integer form) {
		CardExpiryDateVO cardExpiryDateVO = null;
		
		String and = " AND ";

		StringBuilder sqlQueryBuilder = new StringBuilder();
		StringBuilder sqlConditionBuilder = new StringBuilder();
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		sqlQueryBuilder.append("select o from CardExpiryDate o");

		if (StringUtil.isNotEmpty(companyType)) {
			if (StringUtil.isFuzzyQuery(companyType))
				sqlConditionBuilder.append("o.coType like :coType");
			else
				sqlConditionBuilder.append("o.coType=:coType");
			parameterMap.put("coType", companyType);
		}
		
		sqlConditionBuilder.append(and).append("o.form=:form");
		parameterMap.put("form", form);

		if (parameterMap.size() > 0) {
			sqlQueryBuilder.append(" WHERE ");
			sqlQueryBuilder.append(sqlConditionBuilder);
		}

		String sqlQuery = sqlQueryBuilder.toString();
		System.out.println("sqlQuery:" + sqlQuery);
		Query query = this.createQuery(sqlQuery, parameterMap);
		//cardExpiryDateVO = (CardExpiryDateVO) this.entityManager.createQuery(sqlQuery).getSingleResult();
		List<CardExpiryDateVO> resultList = query.getResultList();
		System.out.println("resultList.size:" + resultList.size());
		CardExpiryDate cardExpiryDate = new CardExpiryDate();
		
		if (resultList.size() > 0) {
			cardExpiryDateVO = (CardExpiryDateVO) resultList.get(0);
			this.copyProperties(cardExpiryDate, cardExpiryDateVO);
			
		}
		return cardExpiryDateVO;
	}

}
