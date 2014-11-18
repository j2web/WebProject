package hk.com.mtr.pcis.dao;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.dao.entity.co.CardExpiryDate;
import hk.com.mtr.pcis.enums.OrderMode;
import hk.com.mtr.pcis.util.StringUtil;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AppBaseDAO {
	private final static Log log = LogFactory.getLog(AppBaseDAO.class);
	@PersistenceContext(unitName = "oracle")
	protected EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void copyProperties(Object orig, Object dest) {
		try {
			PropertyUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			log.error("Copy properties occur error:", e);
		}

	}

	protected String buildOrderSql(PageInfoVO pageInfoVO, String alias) {
		StringBuilder sb = new StringBuilder();
		List<String> sortColumn = pageInfoVO.getSortedExpression();
		List<OrderMode> order = pageInfoVO.getOrder();
		if (sortColumn != null && sortColumn.size() > 0) {
			sb.append(" order by ");
			for (int i = 0; i < sortColumn.size(); i++) {
				if (StringUtil.isNotEmpty(alias)) {
					sb.append(alias);
					sb.append(".");
				}
				sb.append(sortColumn.get(i));
				sb.append(" ");
				if (order.size() == sortColumn.size()) {
					OrderMode orderMode = order.get(i);
					if (orderMode != null) {
						sb.append(orderMode.name());
					}
				}
				if (i != sortColumn.size() - 1) {
					sb.append(" , ");
				}
			}

		}
		return sb.toString();
	}
	protected String buildOrderSql(PageInfoVO pageInfoVO, String alias, boolean isNative) {
		StringBuilder sb = new StringBuilder();
		if (isNative) {
			List<String> sortColumn = pageInfoVO.getSortedExpression();
			List<OrderMode> order = pageInfoVO.getOrder();
			if (sortColumn != null && sortColumn.size() > 0) {
				sb.append(" order by ");
				for (int i = 0; i < sortColumn.size(); i++) {
					if (StringUtil.isNotEmpty(alias)) {
						sb.append(alias);
						sb.append(".");
					}
					String tmp = sortColumn.get(i);
					StringBuffer tmpSB = new StringBuffer();
					for (int j = 0; j < tmp.length(); j ++) {
						if (tmp.charAt(j) >= 'A' && tmp.charAt(j) <= 'Z') {
							tmpSB.append("_");
							tmpSB.append(tmp.charAt(j));
						} else {
							tmpSB.append(tmp.charAt(j));
						}
					}
					sb.append(tmpSB);
					sb.append(" ");
					if (order.size() == sortColumn.size()) {
						OrderMode orderMode = order.get(i);
						if (orderMode != null) {
							sb.append(orderMode.name());
						}
					}
					if (i != sortColumn.size() - 1) {
						sb.append(" , ");
					}
				}
				
			}
		} else {
			sb.append(buildOrderSql(pageInfoVO, alias));
		}
		return sb.toString();
	}

	
	protected String buildCriteriaPrefix(Map<?, Object> parameterMap) {
		if (parameterMap.size() == 0)
			return "";
		else
			return " AND ";

	}

	@SuppressWarnings("unchecked")
	protected Query createQuery(String sql, Map<?, Object> parameterMap,
			Class<?> type, boolean isNative) {
		if (log.isDebugEnabled()) {
			log.debug("isNative = " + isNative);
			log.debug("SQL(HQL) ======= " + sql);
		}

		Query query = null;

		if (isNative) {
			if (type == null) {
				query = this.entityManager.createNativeQuery(sql);
			} else {
				System.out.println("createQuery: " + type);
				query = this.entityManager.createNativeQuery(sql, type);
				if (type.equals(CardExpiryDate.class)) {
					List<CardExpiryDate> cs = query.getResultList();
					for (CardExpiryDate d : cs) {
						System.out.println("evan === coType:" + d.getCoType() + "  , form:" + d.getForm());
					}
				}
			}
		} else {
			query = this.entityManager.createQuery(sql);
		}

		if (parameterMap.size() != 0) {
			java.util.Iterator it = parameterMap.entrySet().iterator();
			while (it.hasNext()) {
				java.util.Map.Entry entry = (java.util.Map.Entry) it.next();
				Object key = entry.getKey();
				Object value = entry.getValue();

				if (key instanceof String)
					query.setParameter((String) key, value);
				else if (key instanceof Integer)
					query.setParameter((Integer) key, value);
				else
					log.warn("The paramter name [" + key.getClass()
							+ "] is unknow type");
			}

		}
		
		return query;
	}

	protected Query createQuery(String sql, Map<?, Object> parameterMap) {
		return createQuery(sql, parameterMap, null, false);
	}

	protected Query createQuery(String sql, Map<?, Object> parameterMap,
			boolean isNative) {
		return createQuery(sql, parameterMap, null, isNative);
	}

	protected Query createPagedQuery(PageInfoVO pageInfoVO, String sql,
			Map<?, Object> parameterMap) {
		return createPagedQuery(pageInfoVO, sql, parameterMap, false);
	}

	protected Query createPagedQuery(PageInfoVO pageInfoVO, String sql,
			Map<?, Object> parameterMap, boolean isNative) {

		Query query = this.createQuery(sql, parameterMap, isNative);

		query.setFirstResult(pageInfoVO.getStartRow());
		query.setMaxResults(pageInfoVO.getPageSize());

		return query;
	}
	
	protected Query createPagedQuery(PageInfoVO pageInfoVO, String sql,
			Map<?, Object> parameterMap, boolean isNative, Class<?> type) {
		System.out.println("pagedQuery: " + type);
		Query query = this.createQuery(sql, parameterMap, type, isNative);
		
		query.setFirstResult(pageInfoVO.getStartRow());
		query.setMaxResults(pageInfoVO.getPageSize());
		
		return query;
	}

	protected void buildRecordCount(PageInfoVO pageInfoVO, String sql,
			Map<?, Object> parameterMap) {
		buildRecordCount(pageInfoVO, sql, parameterMap, false);
	}

	protected void buildRecordCount(PageInfoVO pageInfoVO, String sql,
			Map<?, Object> parameterMap, boolean isNative) {

		Query query = this.createQuery(sql, parameterMap, isNative);
		Long count = ((Number) query.getSingleResult()).longValue();
		pageInfoVO.setRecordCount(count.intValue());
	}

}
