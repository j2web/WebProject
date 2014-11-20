package hk.com.mtr.pcis.web.faces.action.co.cardexpirydate;

import hk.com.mtr.pcis.criteria.co.CardExpiryDateCriteriaVO;
import hk.com.mtr.pcis.web.faces.action.SearchBaseAction;

import org.jboss.seam.annotations.Name;

@Name("searchCardExpiryDateAction")
public class SearchCardExpiryDateAction extends SearchBaseAction{
	
	private static final long serialVersionUID = -5918686603184890156L;
	
	private CardExpiryDateCriteriaVO cardExpiryDateCriteriaVO;
	
	public CardExpiryDateCriteriaVO getCardExpiryDateCriteriaVO() {
		return cardExpiryDateCriteriaVO;
	}

	public void setCardExpiryDateCriteriaVO(CardExpiryDateCriteriaVO cardExpiryDateCriteriaVO) {
		this.cardExpiryDateCriteriaVO = cardExpiryDateCriteriaVO;
	}

	@Override
	protected void onReset() {
		this.resetCriteriaVO(cardExpiryDateCriteriaVO);
	}

	@Override
	protected String onSearch() {
		this.saveCriteriaVO(cardExpiryDateCriteriaVO);
//		System.out.println("Search: =========== " + cardExpiryDateCriteriaVO.getCoType());
		return "/co/cardExpiryDate/listCardExpiryDate.xhtml";
	}

	@Override
	protected void onLoad() {
		cardExpiryDateCriteriaVO = this.loadCriteriaVO(CardExpiryDateCriteriaVO.class);
	}

}
