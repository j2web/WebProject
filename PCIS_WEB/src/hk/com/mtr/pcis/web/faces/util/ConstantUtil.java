package hk.com.mtr.pcis.web.faces.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Log;

@Name("constant")
@Scope(ScopeType.APPLICATION)
@BypassInterceptors
@Startup
public class ConstantUtil extends HashMap<String, String> {

	private static final long serialVersionUID = -1449437581908695014L;
	@Logger
	protected Log log;

	@Create
	public void doLoad() throws Exception {
		Pattern shortInnerClassPattern = Pattern.compile("(.+)\\$([^\\$]+)");
		for (Class<?> clz : Constant.class.getDeclaredClasses()) {
			for (Field field : clz.getDeclaredFields()) {
				if (field.getType().equals(String.class)) {
					int mod = field.getModifiers();
					if (Modifier.isPublic(mod) && Modifier.isStatic(mod)) {
						Matcher m = shortInnerClassPattern.matcher(clz.getName() + "." + field.getName());
						if (m.find()) {
							String key = m.group(2);

							field.setAccessible(true);
							try {
								String value = (String) field.get(null);
								this.put(key, value);
								log.debug("Put [#0,#1] to global constant hashmap.", key, value);
							} catch (Exception e) {
								log.error("Can not put [#0] to global constant hashmap.", field.getName());
								throw e;
							}
						}

					}
				}
			}
		}

		for (Field field : Constant.class.getDeclaredFields()) {
			if (field.getType().equals(String.class)) {
				field.setAccessible(true);
				try {
					String key = field.getName();
					String value = (String) field.get(null);
					this.put(key, value);
					log.debug("Put [#0,#1] to global constant hashmap.", key, value);
				} catch (Exception e) {
					log.error("Can not put [#0] to global constant hashmap.", field.getName());
					throw e;
				}
			}
		}

	}

}
