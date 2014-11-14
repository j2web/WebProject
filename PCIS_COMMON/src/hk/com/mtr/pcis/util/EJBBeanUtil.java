package hk.com.mtr.pcis.util;

import hk.com.mtr.pcis.exception.ServiceNotFoundException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EJBBeanUtil {

	private final static Log log = LogFactory.getLog(EJBBeanUtil.class);

	@SuppressWarnings("unchecked")
	public static <T> T getLocalDAOBean(Class<T> daoInterface) {
		String jndi = EnvironmentUtil.EJB_JNDI_PATTERN_DAO.replaceAll(
				"\\$\\{databaseType\\}", EnvironmentUtil.DATABASE_TYPE)
				.replaceAll("\\$\\{interfaceSimpleName\\}",
						daoInterface.getSimpleName()).replaceAll(
						"\\$\\{interfaceName\\}",
						daoInterface.getCanonicalName());
		InitialContext ctx = null;
		T bean = null;
		try {
			ctx = new InitialContext();
			bean = (T) ctx.lookup(jndi);
		} catch (NamingException e) {
			if (log.isInfoEnabled()) {
				log.info("Can't find DAO bean by JNDI - " + jndi);
			}

			// If the database specified DAO implementation is not found,
			// then try to fetch the common DAO concrete bean.
			jndi = EnvironmentUtil.EJB_JNDI_PATTERN_DAO.replaceAll(
					"\\$\\{databaseType\\}", "").replaceAll(
					"\\$\\{interfaceSimpleName\\}",
					daoInterface.getSimpleName()).replaceAll(
					"\\$\\{interfaceName\\}", daoInterface.getCanonicalName());

			if (log.isInfoEnabled()) {
				log.info("Try to get DAO bean by JNDI - " + jndi);
			}

			try {
				bean = (T) ctx.lookup(jndi);
			} catch (NamingException e1) {
				throw new ServiceNotFoundException(
						"Cannot get object with JNDI " + jndi, e1);
			}
		}
		return bean;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFacadeBean(Class<T> facadeInterface) {
		String jndi = getFacadeJndiName(facadeInterface);
		try {
			InitialContext ctx = new InitialContext();
			return (T) ctx.lookup(jndi);
		} catch (NamingException e) {
			throw new ServiceNotFoundException("Cannot get object with JNDI "
					+ jndi, e);
		}
	}

	public static <T> String getFacadeJndiName(Class<T> facadeInterface) {
		String jndi = EnvironmentUtil.EJB_JNDI_PATTERN
				.replaceAll("\\$\\{interfaceSimpleName\\}",
						facadeInterface.getSimpleName()).replaceAll(
						"\\$\\{interfaceName\\}",
						facadeInterface.getCanonicalName());
		return jndi;
	}

}
