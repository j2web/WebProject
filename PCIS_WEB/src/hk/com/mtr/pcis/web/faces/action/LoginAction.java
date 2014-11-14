package hk.com.mtr.pcis.web.faces.action;

import hk.com.mtr.pcis.facade.sa.UserFacade;
import hk.com.mtr.pcis.web.faces.util.Constant;
import hk.com.mtr.pcis.web.faces.util.FacesUtil;
import hk.com.mtr.pcis.web.faces.util.ServiceUtil;
import hk.com.mtr.pcis.web.faces.util.SystemUser;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.international.StatusMessage;

import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.util.StringUtil;
import hk.com.mtr.pcis.vo.sa.ParameterVO;
import hk.com.mtr.pcis.vo.sa.UserVO;

@Name("loginAction")
public class LoginAction extends AppBaseAction {

	private static final long serialVersionUID = -7228363234093587783L;

	private String userId;
	private String password;
	private boolean loginFlag;
	private boolean showErrorFlag;
	private String loginMessage;

	@In
	private SystemUser systemUser;

	@RequestParameter("error")
	private String error;

	@Override
	protected void onLoad() {
		loginFlag = false;

		if (StringUtil.isNotEmpty(error)) {
			log.debug("LDAP authentication failed.");
			showErrorFlag = true;
		} else
			showErrorFlag = false;

		showMessage();
	}

	public void showError() {

		showErrorFlag = false;
		loginFlag = false;
		facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "msg.login.failed");

	}

	public void doLogin() {

		String userId = this.userId.toUpperCase();

		UserFacade userFacade = ServiceUtil.getService(UserFacade.class);
		UserVO userVO = null;
		try {
			userVO = userFacade.findByPrimaryKey(userId);
		} catch (BusinessException e) {
			if (log.isErrorEnabled())
				log.error("Cannot get record from PCIS_USR where userId=" + userId, e);
		}

		if (userVO != null) {
			loginFlag = true;
			systemUser.setTeamNo(userVO.getTeamNo());
		} else {
			facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "msg.common.NotLoggedInException");
			loginFlag = false;
			if (log.isWarnEnabled())
				log.warn("Account \"" + userId + "\" is not exists in table PCIS_USR.");
		}

	}

	public boolean isShowErrorFlag() {
		return showErrorFlag;
	}

	public void setShowErrorFlag(boolean showErrorFlag) {
		this.showErrorFlag = showErrorFlag;
	}

	public boolean isLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(boolean loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginMessage() {
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	private void showMessage() {
		ParameterVO paramVO = FacesUtil.getParameterVO(Constant.ParamMaint.PARAM_MAINT_KEY_LOGIN_MSG); 
		if (paramVO != null && StringUtil.isNotEmpty(paramVO.getParamCharValue()))
			setLoginMessage(paramVO.getParamCharValue());
		else
			setLoginMessage("");
	}
}