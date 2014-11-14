package hk.com.mtr.pcis.web.faces.util;

import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import hk.com.mtr.pcis.exception.ServiceNotFoundException;

public final class ServiceUtil {
	private final static Map<String, Object> ejbCacheMap;
	private final static Map<String, Object> ejbRemoteMap;
	
	private ServiceUtil() {
	}

	static {
		ejbCacheMap = new HashMap<String, Object>();
		ejbRemoteMap = new HashMap<String, Object>();
	}
	

	@SuppressWarnings("unchecked")
	public static <T> T getService(Class<T> cls) throws ServiceNotFoundException {
		Object remote = null;
		String jndiName = "ejblocal:" + cls.getName();
		if (ejbCacheMap.containsKey(jndiName)) {
			remote = ejbCacheMap.get(jndiName);
		} else {
			try {
				InitialContext ctx = new InitialContext();
				ProxyHandler proxy = new ProxyHandler();
				remote = proxy.bind(ctx.lookup(jndiName));
			} catch (NamingException e) {
				throw new ServiceNotFoundException("Cannot get object with JNDI " + jndiName, e);
			}

			synchronized (ejbCacheMap) {
				ejbCacheMap.put(jndiName, remote);
			}

		}

		return (T) remote;
	}
	
	/**
	 * return original service
	 * @param <T>
	 * @param cls
	 * @return
	 * @throws ServiceNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getServiceOriginal(Class<T> cls) throws ServiceNotFoundException {
		Object remote = null;
		String jndiName = "ejblocal:" + cls.getName();
		if (ejbRemoteMap.containsKey(jndiName)) {
			remote = ejbRemoteMap.get(jndiName);
		} else {
			try {
				InitialContext ctx = new InitialContext();
				remote = ctx.lookup(jndiName);
			} catch (NamingException e) {
				throw new ServiceNotFoundException("Cannot get object with JNDI " + jndiName, e);
			}

			synchronized (ejbRemoteMap) {
				ejbRemoteMap.put(jndiName, remote);
			}
		}
		return (T) remote;
	}
	
}
