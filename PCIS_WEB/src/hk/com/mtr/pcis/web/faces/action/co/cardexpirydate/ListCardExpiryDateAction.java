package hk.com.mtr.pcis.web.faces.action.co.cardexpirydate;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.international.StatusMessage;

import com.sun.faces.util.MessageFactory;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.co.CardExpiryDateCriteriaVO;
import hk.com.mtr.pcis.facade.co.CardExpiryDateFacade;
import hk.com.mtr.pcis.vo.AppBaseVO;
import hk.com.mtr.pcis.vo.co.CardExpiryDateVO;
import hk.com.mtr.pcis.web.faces.action.TxnListBaseAction;

@Name("listCardExpiryDateAction")
public class ListCardExpiryDateAction extends TxnListBaseAction {

	private static final long serialVersionUID = 5590415315316689187L;
	
	private CardExpiryDateCriteriaVO cardExpiryDateCriteriaVO;
	
	public CardExpiryDateCriteriaVO getCardExpiryDateCriteriaVO() {
		return cardExpiryDateCriteriaVO;
	}

	public void setCardExpiryDateCriteriaVO(CardExpiryDateCriteriaVO cardExpiryDateCriteriaVO) {
		this.cardExpiryDateCriteriaVO = cardExpiryDateCriteriaVO;
	}

	@Override
	protected List<CardExpiryDateVO> fetchPage(PageInfoVO pageInfoVO) throws Exception {
		CardExpiryDateFacade cardExpiryDateFacade = getService(CardExpiryDateFacade.class);
		List<CardExpiryDateVO> cardExpiryDateList = cardExpiryDateFacade.findAllCardExpiryDateByPage(cardExpiryDateCriteriaVO, pageInfoVO);

		return cardExpiryDateList;
	}

	@Override
	protected void onCancel() throws Exception {
		
	}

	@Override
	protected void onDelete() throws Exception {
		CardExpiryDateFacade CardExpiryDateFacade = getService(CardExpiryDateFacade.class);
		CardExpiryDateVO CardExpiryDateVO = (CardExpiryDateVO) this.currentVO;
		String coType = CardExpiryDateVO.getCoType();

		CardExpiryDateFacade.deleteCardExpiryDate(coType);
		facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO, "msg.co.cardExpiryDateMaint.deletedSuccess", coType);
		this.refresh();
	}

	@Override
	protected void onEdit() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected AppBaseVO onNew() throws Exception {
		CardExpiryDateVO cardExpiryDateVO = new CardExpiryDateVO();
		return cardExpiryDateVO;
	}

	@Override
	protected void onSave() throws Exception {
		CardExpiryDateFacade cardExpiryDateFacade = getService(CardExpiryDateFacade.class);
		CardExpiryDateVO cardExpiryDateVO = (CardExpiryDateVO) this.currentVO;
		
		boolean exist = false;
		String companyType = cardExpiryDateVO.getCoType();
		Integer form = cardExpiryDateVO.getForm();
		System.out.println("companyType:" + companyType + "  form:" + form);
		
		CardExpiryDateVO tempVO = cardExpiryDateFacade.findByCompanyTypeAndForm(companyType, form);
		exist = tempVO != null;
		
		if (exist) {
			facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "msg.co.cardExpiryDateMaint.coTypeExists", companyType, form);
		} else {
			cardExpiryDateFacade.insertCardExpiryDate(cardExpiryDateVO);

			facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO, "msg.co.cardExpiryDateMaint.createdSuccess", companyType);
			cardExpiryDateCriteriaVO = new CardExpiryDateCriteriaVO();
			cardExpiryDateCriteriaVO.setCoType(companyType);
			cardExpiryDateCriteriaVO.setForm(form);
			this.refresh();

		}
		cardExpiryDateFacade.insertCardExpiryDate(cardExpiryDateVO );
		
	}

	@Override
	protected void onUpdate() throws Exception {
		CardExpiryDateFacade cardExpiryDateFacade = getService(CardExpiryDateFacade.class);

		CardExpiryDateVO cardExpiryDateVO = (CardExpiryDateVO) this.currentVO;
		String coType = cardExpiryDateVO.getCoType();

		cardExpiryDateFacade.updateCardExpiryDate(cardExpiryDateVO);
		facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO, "msg.co.cardExpiryDateMaint.updatedSuccess", coType);
		cardExpiryDateCriteriaVO = new CardExpiryDateCriteriaVO();
		cardExpiryDateCriteriaVO.setCoType(coType);
		this.refresh();
		
	}

	@Override
	protected void onLoad() {
		this.cardExpiryDateCriteriaVO = this.loadCriteriaVO(CardExpiryDateCriteriaVO.class);
		this.sortedExpression.add("coType");
		this.sortedExpression.add("form");
		
	}
	
	public void validateCompanyType(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value != null) {
			boolean isValid = value.toString().contains(" ");
			if (isValid) {
				FacesMessage msg = MessageFactory.getMessage(context, "com.mtrc.pcis.companyTypeValidator.COTYPE", value.toString());
				throw new ValidatorException(msg);
			}
		}
	}

}
