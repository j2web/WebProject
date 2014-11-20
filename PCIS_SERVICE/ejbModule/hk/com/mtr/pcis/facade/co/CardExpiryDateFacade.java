package hk.com.mtr.pcis.facade.co;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.co.CardExpiryDateCriteriaVO;
import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.co.CardExpiryDateVO;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

@Local
public interface CardExpiryDateFacade {
	
	public CardExpiryDateVO findByPrimaryKey(String companyType) throws BusinessException;

	public CardExpiryDateVO findByCompanyTypeAndForm(String companyType, Integer form) throws BusinessException;

	public List<CardExpiryDateVO> findAllCardExpiryDateByPage(CardExpiryDateCriteriaVO cardExpiryDateCriteriaVO, PageInfoVO pageInfoVO) throws BusinessException;

	public void insertCardExpiryDate(CardExpiryDateVO cardExpiryDateVO) throws BusinessException;

	public void updateCardExpiryDate(CardExpiryDateVO cardExpiryDateVO) throws BusinessException;

	public void deleteCardExpiryDate(String companyType, Timestamp updateTime) throws BusinessException;

	public void updateYear();

	public List<CardExpiryDateVO> findAllCompanyType();
}
