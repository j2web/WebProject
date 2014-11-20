package hk.com.mtr.pcis.dao.co;


import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.co.CardExpiryDateCriteriaVO;
import hk.com.mtr.pcis.vo.co.CardExpiryDateVO;
import hk.com.mtr.pcis.vo.sa.RoleVO;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Local;

@Local
public interface CardExpiryDateDAO {
	public CardExpiryDateVO findByPrimaryKey(String companyType);
	public CardExpiryDateVO findByCompanyTypeAndForm(String companyType, Integer form);
	public void insertCardExpiryDate(CardExpiryDateVO cardExpiryDateVO);
	public void updateCardExpiryDate(CardExpiryDateVO cardExpiryDateVO);
	public void deleteCardExpiryDate(String companyType, Timestamp updateTime);
	public void updateRole(CardExpiryDateVO cardExpiryDateVO , List<RoleVO> roleList);
	public List<CardExpiryDateVO> findAllCardExpiryDateByPage(CardExpiryDateCriteriaVO cardExpiryDateCriteriaVO, PageInfoVO pageInfoVO);
	public void updateYear();
	public List<CardExpiryDateVO> finAllCompanyType();
}
