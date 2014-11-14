package hk.com.mtr.pcis.web.faces.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Calendar;
import java.util.List;
import hk.com.mtr.pcis.vo.AppBaseVO;

public class ProxyHandler implements InvocationHandler {
	private Object business;

	public Object bind(Object business) {
		this.business = business;
		return Proxy.newProxyInstance(business.getClass().getClassLoader(), business.getClass().getInterfaces(), this);
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (args != null) {
			for (Object o : args) {
				if (o instanceof AppBaseVO) {
					AppBaseVO vo = (AppBaseVO) o;
					vo.setUpdateUser(FacesUtil.getCurrentUser().getUserId());					
					vo.setUpdateTime(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
				} else if (o instanceof List) {
					List<?> list = (List<?>) o;
					for (Object item : list) {
						if (item instanceof AppBaseVO) {
							AppBaseVO vo = (AppBaseVO) item;
							vo.setUpdateUser(FacesUtil.getCurrentUser().getUserId());							
							vo.setUpdateTime(new java.sql.Timestamp(Calendar.getInstance().getTime().getTime()));
						}
					}
				}

			}
		}
		return method.invoke(business, args);

	}

}
