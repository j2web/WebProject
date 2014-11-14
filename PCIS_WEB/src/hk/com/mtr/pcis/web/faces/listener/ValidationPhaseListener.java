package hk.com.mtr.pcis.web.faces.listener;

import hk.com.mtr.pcis.facade.sa.PrivilegeFacade;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIParameter;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.security.AuthorizationException;

import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.util.StringUtil;
import hk.com.mtr.pcis.web.faces.util.Constant;
import hk.com.mtr.pcis.web.faces.util.FacesUtil;
import hk.com.mtr.pcis.web.faces.util.ServiceUtil;
import hk.com.mtr.pcis.web.faces.util.SystemUser;

import com.sun.faces.context.FacesContextImpl;

public class ValidationPhaseListener implements PhaseListener {
	private static final long serialVersionUID = 604545246685867322L;
	private final static Log log = LogFactory.getLog(ValidationPhaseListener.class);

	@SuppressWarnings("unchecked")
	public void afterPhase(PhaseEvent event) {
		// Fix the bug that the value of UIInput within UIData is cleared when
		// validation fail.
//		long start = System.currentTimeMillis();
		PhaseId phaseId = event.getPhaseId();
		if (PhaseId.PROCESS_VALIDATIONS.equals(phaseId)) {

			FacesContext context = event.getFacesContext();
			boolean ajaxFlag = isAJAXRequest(context);

			if (!ajaxFlag) {
				if (FacesMessage.SEVERITY_WARN.equals(context.getMaximumSeverity()) || FacesMessage.SEVERITY_ERROR.equals(context.getMaximumSeverity())) {
					UIViewRoot viewRoot = context.getViewRoot();
					List<UIComponent> cmds = new ArrayList<UIComponent>();
					recurComponent(context, viewRoot, cmds);

					if (!cmds.isEmpty()) {
						if (context instanceof FacesContextImpl) {
							try {
								Field f = FacesContextImpl.class.getDeclaredField("componentMessageLists");
								f.setAccessible(true);

								((Map) f.get(context)).clear();
							} catch (Exception e) {

							}
						}
						for (UIComponent cmd : cmds) {
							cmd.broadcast(new ActionEvent(cmd));
						}
					} else {

						FacesMessages.instance().addFromResourceBundle(StatusMessage.Severity.ERROR, "msg.common.validationFail");

					}
				}
			}
		}
//		log.info("after phaseId is: " + phaseId.toString() + " use time: " + (System.currentTimeMillis() - start));
	}

	private static final String PARAMETER_NAME = "immediate";

	private void recurComponent(FacesContext context, UIComponent component, List<UIComponent> cmds) {
		if (component instanceof UIParameter) {
			UIParameter parameter = (UIParameter) component;
			if (PARAMETER_NAME.equals(parameter.getName()) && Boolean.parseBoolean(parameter.getValue().toString())) {
				UIComponent parent = component.getParent();
				if (parent instanceof UICommand) {
					boolean hasEvent = context.getExternalContext().getRequestParameterMap().containsKey(parent.getClientId(context));
					if (hasEvent) {
						cmds.add((UICommand) parent);
					}
				}
			}
		} else if (component instanceof UIData) {
			component.processUpdates(context);
		}

		List<UIComponent> children = component.getChildren();
		for (UIComponent child : children) {
			recurComponent(context, child, cmds);
		}
	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	public void beforePhase(PhaseEvent event) {
//		long start = System.currentTimeMillis();
		FacesContext context = event.getFacesContext();

		PhaseId phaseId = event.getPhaseId();
		if (PhaseId.APPLY_REQUEST_VALUES.equals(phaseId)) {
			Map<String, Object> requestMap = context.getExternalContext().getRequestMap();
			requestMap.put(Constant.POSTBACK_ATTRIBUTE_NAME, Boolean.TRUE);
		} else if (PhaseId.RENDER_RESPONSE.equals(phaseId)) {
			String url = FacesUtil.getRequestUrl();
			if (!isSkipCheckLogin(url)) {
				boolean isLogin = checkLogin();
				if (isLogin) {
					boolean isPostback = FacesUtil.isPostback();
					// It's NO need to check URL authorization for post request.
					if (!isPostback) {
						if (!isSkipAuth(url)) {
							checkURLPermission(url);
						}
					}
				}
			}
		}
//		log.info("before phaseId is: " + phaseId.toString() + " use time: " + (System.currentTimeMillis() - start));
	}

	private boolean isSkipCheckLogin(String url) {

		// is login page or error page
		if (StringUtil.isEmpty(url) || url.startsWith("/login.jsf") || url.startsWith("/error.jsf"))
			return true;

		return false;

	}

	private boolean checkLogin() {
		boolean isLogin = false;
		SystemUser systemUser = FacesUtil.getCurrentUser();
		if (systemUser == null || StringUtil.isEmpty(systemUser.getUserId())) {
			try {
				isLogin = false;
				log.debug("Not log in");
				org.jboss.seam.exception.Exceptions.instance().handle(new org.jboss.seam.security.NotLoggedInException());

			} catch (Exception e) {

				if (log.isErrorEnabled()) {
					log.error("Handling NotLoggedInException: ", e);
				}
			}
		} else
			isLogin = true;
		return isLogin;
	}

	private boolean isSkipAuth(String url) {
		if (StringUtil.isNotEmpty(url)) {
			return url.startsWith("/a4j_") || url.startsWith("/error.jsf") || url.startsWith("/home.jsf") || url.startsWith("/login.jsf") || url.startsWith("/debug.jsf");
		} else
			return false;
	}

	private void checkURLPermission(String url) {

		SystemUser systemUser = FacesUtil.getCurrentUser();
		boolean isAuth = false;

		String userId = systemUser.getUserId();

		if (log.isDebugEnabled()) {
			log.debug("Check user \"" + userId + "\" access url \"" + url + "\"");
		}
		Map<String, List<String>> privilegeList = systemUser.getPrivilegeList();
		List<String> functionList = null;
		if (privilegeList.containsKey(url)) {
			functionList = privilegeList.get(url);

		} else {
			PrivilegeFacade privilegeFacade = ServiceUtil.getService(PrivilegeFacade.class);
			try {
				functionList = privilegeFacade.getPrivilegeByUserIdAndUrl(userId, url);
			} catch (BusinessException e) {
				log.error("Get privilege by userId and url exception: ", e);
			}
			systemUser.getPrivilegeList().put(url, functionList);
		}
		// if (functionList != null && !functionList.isEmpty()) {
		// if (log.isInfoEnabled()) {
		// log.info("User \"" + userId +
		// "\" has permission to access to the following function of url \"" +
		// url + "\"");
		// }
		// for (String functionId : functionList) {
		// if (log.isInfoEnabled()) {
		// log.info("====================function Id=\"" + functionId + "\"");
		// }
		// }
		// }
		isAuth = functionList != null && !functionList.isEmpty();
		
		if (isAuth) {
			if (log.isDebugEnabled()) {
				log.debug("Authorized: User \"" + userId + "\" has permission to access \"" + url + "\"");
			}
		} else {
			try {
				String message = "Unauthorized: User \"" + userId + "\" has no permission to access \"" + url + "\"";
				if (log.isWarnEnabled()) {
					log.warn(message);
				}
				org.jboss.seam.exception.Exceptions.instance().handle(new AuthorizationException(message));
			} catch (Exception e) {

				if (log.isErrorEnabled()) {
					log.error("Handling AuthorizationException: ", e);
				}
			}
		}
	}

	private boolean isAJAXRequest(FacesContext context) {
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		boolean ajaxFlag = request.getParameter("AJAXREQUEST") != null;
		return ajaxFlag;
	}

}
