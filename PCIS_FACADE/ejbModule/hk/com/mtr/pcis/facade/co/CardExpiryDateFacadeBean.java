package hk.com.mtr.pcis.facade.co;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.co.CardExpiryDateCriteriaVO;
import hk.com.mtr.pcis.dao.co.CardExpiryDateDAO;
import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.co.CardExpiryDateVO;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class CardExpiryDateFacadeBean implements CardExpiryDateFacade {
	
	@EJB
	CardExpiryDateDAO cardExpiryDateDAO;

	@Override
	public void deleteCardExpiryDate(String companyType) throws BusinessException {
		this.cardExpiryDateDAO.deleteCardExpiryDate(companyType);
	}

	@Override
	public List<CardExpiryDateVO> findAllCardExpiryDateByPage(
			CardExpiryDateCriteriaVO cardExpiryDateCriteriaVO, PageInfoVO pageInfoVO)
			throws BusinessException {
		List<CardExpiryDateVO> list = null;

		try {
			list = cardExpiryDateDAO.findAllCardExpiryDateByPage(cardExpiryDateCriteriaVO, pageInfoVO);
		} catch (Exception e) {
			throw new BusinessException("Find card expiry date by criteria failed:", e);
		}
		return list;
	}

	@Override
	public CardExpiryDateVO findByPrimaryKey(String companyType)
			throws BusinessException {
		
		return this.cardExpiryDateDAO.findByPrimaryKey(companyType);
	}

	@Override
	public void insertCardExpiryDate(CardExpiryDateVO cardExpiryDateVO)
			throws BusinessException {
		this.cardExpiryDateDAO.insertCardExpiryDate(cardExpiryDateVO);
	}

	@Override
	public void updateCardExpiryDate(CardExpiryDateVO cardExpiryDateVO)
			throws BusinessException {
		this.cardExpiryDateDAO.updateCardExpiryDate(cardExpiryDateVO);
	}

	public CardExpiryDateDAO getCardExpiryDateDAO() {
		return cardExpiryDateDAO;
	}

	public void setCardExpiryDateDAO(CardExpiryDateDAO cardExpiryDateDAO) {
		this.cardExpiryDateDAO = cardExpiryDateDAO;
	}

	@Override
	public CardExpiryDateVO findByCompanyTypeAndForm(String companyType,
			Integer form) throws BusinessException {
		
		return this.cardExpiryDateDAO.findByCompanyTypeAndForm(companyType, form);
	}

	
}
