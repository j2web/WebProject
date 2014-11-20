package hk.com.mtr.pcis.web.faces.util;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hk.com.mtr.pcis.util.StringUtil;

public class FunctionUtil {
	private final static Log log = LogFactory.getLog(FunctionUtil.class);

	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	public static boolean isPostback() {
		return FacesUtil.isPostback();
	}

	public static boolean hasPrivilege(String functionId) {
		String key = Constant.SA_PRIVILEGE_CHECK_KEY + functionId;
		Map<String, Object> requestMap = FacesUtil.getFacesContext().getExternalContext().getRequestMap();
		Boolean isAuth = (Boolean) requestMap.get(key);
		if (isAuth == null) {
			SystemUser systemUser = hk.com.mtr.pcis.web.faces.util.FacesUtil.getCurrentUser();

			if (systemUser != null && StringUtil.isNotEmpty(functionId)) {
				String userId = systemUser.getUserId();
				String url = hk.com.mtr.pcis.web.faces.util.FacesUtil.getRequestUrl();

				if (log.isInfoEnabled()) {
					log.info("Checking user \"" + userId + "\" access url \"" + url + "\" with functionId \"" + functionId + "\"");
				}
				Map<String, List<String>> privilegeList = systemUser.getPrivilegeList();
				List<String> functionList = null;
				if (privilegeList.containsKey(url)) {
					functionList = privilegeList.get(url);
				} else {
					if (log.isWarnEnabled())
						log.warn("Privilege Map don't contains url \"" + url + "\", you maybe refresh current page.");
				}
				if (functionList != null && functionList.contains(functionId))
					isAuth = Boolean.TRUE;
				else
					isAuth = Boolean.FALSE;
				requestMap.put(key, isAuth);

				if (log.isWarnEnabled()) {
					if (!isAuth) {
						if (log.isWarnEnabled())
							log.warn("Unauthorized. User \"" + userId + "\" has no permission to access url \"" + url + "\" with functionId \"" + functionId + "\"");
					} else if (log.isInfoEnabled()) {
						log.info("Passed. User \"" + userId + "\" has permission to access url \"" + url + "\" with functionId \"" + functionId + "\"");
					}
				}
			} else {
				isAuth = Boolean.FALSE;
			}
		}

		return isAuth.booleanValue();
	}

	public static String encodeURL(String text) {
		try {
			return java.net.URLEncoder.encode(text, Constant.CHARSET_UTF8_VALUE);
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug("Can not encode URL: " + text);
			}
		}
		return "";
	}
}
