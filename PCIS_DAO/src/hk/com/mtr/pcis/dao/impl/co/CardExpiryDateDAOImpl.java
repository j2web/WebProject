package hk.com.mtr.pcis.dao.impl.co;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
import hk.com.mtr.pcis.dao.entity.co.ConsumerGroupMapPK;
import hk.com.mtr.pcis.util.StringUtil;
import hk.com.mtr.pcis.vo.co.CardExpiryDateVO;
import hk.com.mtr.pcis.vo.sa.RoleVO;

@Stateless
public class CardExpiryDateDAOImpl extends AppBaseDAO implements CardExpiryDateDAO {
	
	@SuppressWarnings("unused")
	private final static Log log = LogFactory.getLog(CardExpiryDateDAOImpl.class);

	@Override
	public void deleteCardExpiryDate(String companyType, Timestamp updateTime) {
		ConsumerGroupMapPK pk = new ConsumerGroupMapPK(companyType, updateTime);
		CardExpiryDate cardExpiryDate = this.entityManager.find(CardExpiryDate.class, pk);
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
		String orderSql = this.buildOrderSql(pageInfoVO, alias);
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		sqlQueryBuilder.append("select o from CardExpiryDate o");
		sqlRecordCountBuilder.append("select count(o) from CardExpiryDate o ");
		if (cardExpiryDateCriteriaVO != null) {
			String coType = cardExpiryDateCriteriaVO.getCoType();
			
			if (StringUtil.isNotEmpty(coType)) {
				if (StringUtil.isFuzzyQuery(coType))
					sqlConditionBuilder.append("o.consumerGroupMapPK.coType like :coType");
				else
					sqlConditionBuilder.append("o.consumerGroupMapPK.coType=:coType");
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
		this.buildRecordCount(pageInfoVO, queryCountSql, parameterMap);
		
		Query query = this.createPagedQuery(pageInfoVO, querySql, parameterMap);
		List<CardExpiryDate> resultList = (List<CardExpiryDate>) query.getResultList();
		
		CardExpiryDateVO cardExpiryDateVO = null;
		
		for (CardExpiryDate cardExpiryDate : resultList) {
			cardExpiryDateVO = new CardExpiryDateVO();
			this.copyProperties(cardExpiryDate, cardExpiryDateVO);
			cardExpiryDateVO.setCoType(cardExpiryDate.getConsumerGroupMapPK().getCoType());
			cardExpiryDateVO.setUpdateTime(cardExpiryDate.getConsumerGroupMapPK().getUpdateTime());
			cardExpiryDateList.add(cardExpiryDateVO);
		}
		return cardExpiryDateList;		
	}

	@Override
	public CardExpiryDateVO findByPrimaryKey(String companyType) {
		CardExpiryDateVO cardExpiryDateVO = null;
		
		CardExpiryDate cardExpiryDate = this.entityManager.find(CardExpiryDate.class, companyType);
		if (cardExpiryDate != null) {
			cardExpiryDateVO = new CardExpiryDateVO();
			this.copyProperties(cardExpiryDate, cardExpiryDateVO);
			cardExpiryDateVO.setCoType(cardExpiryDate.getConsumerGroupMapPK().getCoType());
			cardExpiryDateVO.setUpdateTime(cardExpiryDate.getConsumerGroupMapPK().getUpdateTime());
		}
		
		return cardExpiryDateVO;
	}

	@Override
	public void insertCardExpiryDate(CardExpiryDateVO cardExpiryDateVO) {
		CardExpiryDate cardExpiryDate = new CardExpiryDate();
		this.copyProperties(cardExpiryDateVO, cardExpiryDate);
		ConsumerGroupMapPK cg = new ConsumerGroupMapPK(cardExpiryDateVO.getCoType(), cardExpiryDateVO.getUpdateTime());
		cardExpiryDate.setConsumerGroupMapPK(cg);
		this.entityManager.persist(cardExpiryDate);
	}

	@Override
	public void updateCardExpiryDate(CardExpiryDateVO cardExpiryDateVO) {
		CardExpiryDate cardExpiryDate = new CardExpiryDate();
		this.copyProperties(cardExpiryDateVO, cardExpiryDate);
		ConsumerGroupMapPK cg = new ConsumerGroupMapPK(cardExpiryDateVO.getCoType(), cardExpiryDateVO.getUpdateTime());
		
		cardExpiryDate.setConsumerGroupMapPK(cg);
System.out.println("\nTest ========================= " + 
					cardExpiryDate.getDescription() + " : " 
					+ cardExpiryDate.getConsumerGroupMapPK().getCoType() + " : "
					+ cardExpiryDate.getConsumerGroupMapPK().getUpdateTime());
		this.entityManager.merge(cardExpiryDate);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateYear() {
		String querySql = "select o from CardExpiryDate o";
		Query query = this.entityManager.createQuery(querySql);
		List<CardExpiryDate> cardExpiryDates = query.getResultList();
		if (cardExpiryDates.size() > 0) {
			Calendar calendar = Calendar.getInstance();
			for (CardExpiryDate c : cardExpiryDates) {
				calendar.setTime(c.getExpiryDate());
				calendar.add(Calendar.YEAR, 1);
				c.setExpiryDate(calendar.getTime());
				this.entityManager.merge(c);
			}
		}
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
				sqlConditionBuilder.append("o.consumerGroupMapPK.coType like :coType");
			else
				sqlConditionBuilder.append("o.consumerGroupMapPK.coType=:coType");
			parameterMap.put("coType", companyType);
		}
		
		sqlConditionBuilder.append(and).append("o.form=:form");
		parameterMap.put("form", form);

		if (parameterMap.size() > 0) {
			sqlQueryBuilder.append(" WHERE ");
			sqlQueryBuilder.append(sqlConditionBuilder);
		}

		String sqlQuery = sqlQueryBuilder.toString();
		Query query = this.createQuery(sqlQuery, parameterMap);
		List<CardExpiryDateVO> resultList = query.getResultList();
		CardExpiryDate cardExpiryDate = new CardExpiryDate();
		
		if (resultList.size() > 0) {
			cardExpiryDateVO = (CardExpiryDateVO) resultList.get(0);
			this.copyProperties(cardExpiryDate, cardExpiryDateVO);
			cardExpiryDateVO.setCoType(cardExpiryDate.getConsumerGroupMapPK().getCoType());
			cardExpiryDateVO.setUpdateTime(cardExpiryDate.getConsumerGroupMapPK().getUpdateTime());
		}
		return cardExpiryDateVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CardExpiryDateVO> finAllCompanyType() {
		List<CardExpiryDateVO> companyTypeList = new ArrayList<CardExpiryDateVO>();
		String querySql = "select distinct o from CardExpiryDate o";
		Query query = this.entityManager.createQuery(querySql);
		List<CardExpiryDate> resultList = (List<CardExpiryDate>) query.getResultList();
		
		if (resultList.size() > 0) {
			for (CardExpiryDate companyType : resultList) {
				CardExpiryDateVO companyTypeVO = new CardExpiryDateVO();
				this.copyProperties(companyType, companyTypeVO);
				companyTypeList.add(companyTypeVO);
			}
		}
		return companyTypeList;
	}

}
