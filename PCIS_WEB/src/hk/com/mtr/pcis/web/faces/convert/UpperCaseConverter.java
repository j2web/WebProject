package hk.com.mtr.pcis.web.faces.convert;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * character UpperCase
 * @author YeJunhua
 *
 */

public class UpperCaseConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		value = value.toUpperCase();
		return value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		String object = String.valueOf(value).toUpperCase();
		return object;
	}

}
