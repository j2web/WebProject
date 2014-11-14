package hk.com.mtr.pcis.web.faces.action;

import hk.com.mtr.pcis.web.faces.util.FacesUtil;
import hk.com.mtr.pcis.web.faces.util.ServiceUtil;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;

import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Log;

import hk.com.mtr.pcis.criteria.AppBaseCriteriaVO;

@Scope(ScopeType.PAGE)
public abstract class AppBaseAction implements Serializable {

	private static final long serialVersionUID = -4163652845090703399L;

	@Logger
	protected Log log;
	@In
	protected FacesMessages facesMessages;

	@Create
	public void doLoad() {

		onLoad();
	}

	public <T> T loadCriteriaVO(Class<? extends AppBaseCriteriaVO> cls) {
		return FacesUtil.loadCriteriaVO(cls);
	}

	protected abstract void onLoad();

	public <T> T getService(Class<T> cls) {
		T service = ServiceUtil.getService(cls);

		return service;

	}
	
	public <T> T getServiceOriginal(Class<T> cls) {		
		T service = ServiceUtil.getServiceOriginal(cls);
		return service;
	}

	protected void saveObject(String key, Object o) {
		Contexts.getPageContext().set(key, o);
	}

	protected Object getObject(String key) {
		return Contexts.getPageContext().get(key);
	}

	protected void removeObject(String key) {
		Contexts.getPageContext().remove(key);
	}

	protected void handleException(Exception e) {
		log.error("Handle seam exception: ", e);
		facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "msg.common.error");
	}	
	
}
