package hk.com.mtr.pcis.web.faces.util;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.jboss.seam.security.Identity;

public class SSOFilter implements Filter {
	private final static Log log = LogFactory.getLog(SSOFilter.class);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String url = httpServletRequest.getServletPath();
		if (!url.startsWith("/login.jsf")) {
			HttpSession session = httpServletRequest.getSession(true);
			Identity identity = (Identity) session.getAttribute("org.jboss.seam.security.identity");

			Principal principal = httpServletRequest.getUserPrincipal();

			if (principal != null) {
				if (identity != null) {
					if (!identity.isLoggedIn()) {
						String userId = principal.getName().toUpperCase();

						identity.acceptExternallyAuthenticatedPrincipal(principal);
						identity.login();

						if (log.isInfoEnabled())
							log.info("Account \"" + userId + "\" login successfully.");

					}

				} else {
					if (log.isErrorEnabled()) {
						log.error("Cannot get seam identity component.");
					}
				}

			} else {
				if (log.isErrorEnabled()) {
					log.error("LDAP authentication failed.");
				}
			}
		}
		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
