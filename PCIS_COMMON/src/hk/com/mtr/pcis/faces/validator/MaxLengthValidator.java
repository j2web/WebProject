package hk.com.mtr.pcis.faces.validator;

import java.io.UnsupportedEncodingException;

import javax.faces.application.FacesMessage;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sun.faces.util.MessageFactory;

public class MaxLengthValidator implements Validator, StateHolder {

	public MaxLengthValidator() {
		super();
	}

	private Integer maxlength;
	private String charset;

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (context != null && component != null && value != null) {
			if (maxlength == null)
				maxlength = (Integer) component.getAttributes().get("maxlength");
			if (maxlength != null && maxlength > 0) {
				Object label = MessageFactory.getLabel(context, component);
				if (charset == null || charset.length() == 0)
					charset = "utf-8";
				int size = 0;
				try {
					size = String.valueOf(value).getBytes(charset).length;
				} catch (UnsupportedEncodingException e) {
					FacesMessage message = new FacesMessage();
					message.setSeverity(FacesMessage.SEVERITY_FATAL);
					message.setDetail("The charset " + charset + " is unsupported encoding.");
					throw new ValidatorException(message);
				}

				if (maxlength < size) {
					throw new ValidatorException(MessageFactory.getMessage(context, javax.faces.validator.LengthValidator.MAXIMUM_MESSAGE_ID, new Object[] { maxlength, label }));
				}
			}
		}

	}

	private boolean transientValue = false;

	public boolean isTransient() {
		return (this.transientValue);
	}

	public void setTransient(boolean transientValue) {
		this.transientValue = transientValue;
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		maxlength = (Integer) values[0];

	}

	public Object saveState(FacesContext context) {
		Object values[] = new Object[1];
		values[0] = maxlength;
		return values;

	}

	public Integer getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(Integer maxlength) {
		this.maxlength = maxlength;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

}
