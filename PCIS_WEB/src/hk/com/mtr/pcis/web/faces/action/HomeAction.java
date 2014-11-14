package hk.com.mtr.pcis.web.faces.action;

import hk.com.mtr.pcis.web.faces.util.FacesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Identity;


@Name("homeAction")
public class HomeAction extends AppBaseAction {
	@In
	private Identity identity;
	private static final long serialVersionUID = 9121521782203456301L;

	@Override
	protected void onLoad() {

	}

	public String doLogout() {
		try {

			HttpServletRequest request = FacesUtil.getRequest();
			HttpServletResponse response = FacesUtil.getResponse();

			if (request != null && response != null) {
				String userId = identity.getPrincipal().getName().toUpperCase();
				identity.logout();
				// invalidate session
				org.jboss.seam.web.Session.instance().invalidate();
				com.ibm.websphere.security.WSSecurityHelper.revokeSSOCookies(request, response);

				log.debug("Account \"" + userId + "\" logout successfully.");
			}

		} catch (Exception e) {
			log.error("JAASLogoutServlet: logout Exception : ", e);

		}
		return "logout";
	}

}