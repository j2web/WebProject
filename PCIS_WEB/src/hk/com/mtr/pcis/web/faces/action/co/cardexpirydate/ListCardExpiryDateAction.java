package hk.com.mtr.pcis.web.faces.action.co.cardexpirydate;

import java.sql.Timestamp;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
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
public class ListCardExpiryDateAction extends TxnListBaseAction implements ActionListener {

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
//		System.out.println("list: ======================  " + this.cardExpiryDateCriteriaVO.getCoType());
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
		CardExpiryDateVO cardExpiryDateVO = (CardExpiryDateVO) this.currentVO;
		String coType = cardExpiryDateVO.getCoType();
		Timestamp updateTime = cardExpiryDateVO.getUpdateTime();

		CardExpiryDateFacade.deleteCardExpiryDate(coType, updateTime);
		facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO, "msg.co.cardExpiryDateMaint.deletedSuccess", coType);
		this.refresh();
	}

	@Override
	protected void onEdit() throws Exception {
	}
	
	//更新所有过期年份
	protected void doUpdateExpiryDate() throws Exception {
		CardExpiryDateFacade cardExpiryDateFacade = getService(CardExpiryDateFacade.class);
		cardExpiryDateFacade.updateYear();
		this.refresh();
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
		System.out.println("haha  -----  companyType:" + companyType + "  form:" + form);
		
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
		cardExpiryDateCriteriaVO.setUpdateTime(cardExpiryDateVO.getUpdateTime());
		this.refresh();
		
	}

	@Override
	protected void onLoad() {
		this.cardExpiryDateCriteriaVO = this.loadCriteriaVO(CardExpiryDateCriteriaVO.class);
		this.sortedExpression.add("consumerGroupMapPK.coType");
		this.sortedExpression.add("form");
		this.sortedExpression.add("expiryDate");
		this.sortedExpression.add("description");
		
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

	@Override
	public void processAction(ActionEvent arg0) throws AbortProcessingException {
		try {
			doUpdateExpiryDate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
