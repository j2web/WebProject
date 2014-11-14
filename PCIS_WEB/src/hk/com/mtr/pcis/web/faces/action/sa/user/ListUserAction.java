package hk.com.mtr.pcis.web.faces.action.sa.user;

import hk.com.mtr.pcis.facade.sa.UserFacade;
import hk.com.mtr.pcis.web.faces.action.TxnListBaseAction;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.international.StatusMessage;
import com.sun.faces.util.MessageFactory;
import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.UserCriteriaVO;
import hk.com.mtr.pcis.vo.AppBaseVO;
import hk.com.mtr.pcis.vo.sa.UserVO;

@Name("listUserAction")
public class ListUserAction extends TxnListBaseAction {

	private static final long serialVersionUID = -8168808928330954389L;
	private UserCriteriaVO userCriteriaVO;

	public UserCriteriaVO getUserCriteriaVO() {
		return userCriteriaVO;
	}

	public void setUserCriteriaVO(UserCriteriaVO userCriteriaVO) {
		this.userCriteriaVO = userCriteriaVO;
	}

	@Override
	public List<UserVO> fetchPage(PageInfoVO pageInfoVO) throws Exception {
		UserFacade userFacade = getService(UserFacade.class);
		List<UserVO> userList = userFacade.findAllUserByPage(userCriteriaVO, pageInfoVO);

		return userList;
	}

	@Override
	protected void onLoad() {
		userCriteriaVO = this.loadCriteriaVO(UserCriteriaVO.class);
		this.sortedExpression.add("teamNo");
		this.sortedExpression.add("userId");
	}

	@Override
	protected void onCancel() {

	}

	@Override
	protected void onEdit() {

	}

	@Override
	protected AppBaseVO onNew() {
		UserVO userVO = new UserVO();
		return userVO;
	}

	@Override
	protected void onSave() throws Exception {
		UserFacade userFacade = getService(UserFacade.class);
		UserVO userVO = (UserVO) this.currentVO;
		userVO.setUserId(userVO.getUserId().toUpperCase());
		String userId = userVO.getUserId();

		boolean exist = false;

		UserVO tempVO = userFacade.findByPrimaryKey(userId);
		exist = tempVO != null;

		if (exist) {
			facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "msg.sa.userMaint.userIdExists", userId);

		} else {

			userFacade.insertUser(userVO);

			facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO, "msg.sa.userMaint.createdSuccess", userId);
			userCriteriaVO = new UserCriteriaVO();
			userCriteriaVO.setUserId(userId);
			this.refresh();

		}

	}

	@Override
	protected void onUpdate() throws Exception {
		UserFacade userFacade = getService(UserFacade.class);

		UserVO userVO = (UserVO) this.currentVO;
		String userId = userVO.getUserId();

		userFacade.updateUser(userVO);
		facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO, "msg.sa.userMaint.updatedSuccess", userId);
		userCriteriaVO = new UserCriteriaVO();
		userCriteriaVO.setUserId(userId);
		this.refresh();

	}

	@Override
	protected void onDelete() throws Exception {

		UserFacade userFacade = getService(UserFacade.class);
		UserVO userVO = (UserVO) this.currentVO;
		String userId = userVO.getUserId();

		userFacade.deleteUser(userVO.getUserId());
		facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO, "msg.sa.userMaint.deletedSuccess", userId);
		this.refresh();

	}

	public void validateUserID(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value != null) {
			boolean isValid = value.toString().contains(" ");
			if (isValid) {
				FacesMessage msg = MessageFactory.getMessage(context, "com.mtrc.pcis.userIDValidator.USERID", value.toString());
				throw new ValidatorException(msg);
			}
		}
	}
}
