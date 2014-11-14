package hk.com.mtr.pcis.web.faces.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.sun.facelets.tag.AbstractTagLibrary;

public class FunctionLibrary extends AbstractTagLibrary {
	public static final String NAMESPACE = "http://www.mtr.com.hk/functions";

	public FunctionLibrary() {
		super(NAMESPACE);
		try {
			Method[] methods = FunctionUtil.class.getMethods();
			for (int i = 0; i < methods.length; i++) {
				if (Modifier.isStatic(methods[i].getModifiers())) {
					addFunction(methods[i].getName(), methods[i]);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}