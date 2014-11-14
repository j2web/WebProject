package hk.com.mtr.pcis.faces.validator;

import java.math.BigDecimal;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import hk.com.mtr.pcis.faces.util.MessageFactory;

public class NumberValidator implements Validator, StateHolder {
	private static final String NUMBER_VALIDATOR_INVALID_MESSAGE_ID = "com.mtr.web.jsf.numberValidator.INVALID";

	public NumberValidator() {
		super();
	}

	private Boolean allowNegative;
	private Integer precision;
	private Integer scale;

	private boolean transientValue = false;

	public boolean isTransient() {
		return (this.transientValue);
	}

	public void setTransient(boolean transientValue) {
		this.transientValue = transientValue;
	}

	public void restoreState(FacesContext context, Object state) {
		Object values[] = (Object[]) state;
		allowNegative = (Boolean) values[0];
		precision = (Integer) values[1];
		scale = (Integer) values[2];

	}

	public Object saveState(FacesContext context) {
		Object values[] = new Object[3];
		values[0] = allowNegative;
		values[1] = precision;
		values[2] = scale;
		return values;

	}

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value != null) {
			if (allowNegative == null)
				allowNegative = false;
			boolean result = true;
			String number = value.toString();
			if (!allowNegative && number.startsWith("-")) {
				result = false;
			}

			if (result) {
				result = isDecimal(number);
			}
			if (result) {

				BigDecimal decimal = new BigDecimal(number);
				if (decimal.doubleValue() < 0)
					result = decimal.scale() <= scale && String.valueOf(decimal.toBigInteger()).length() <= precision - scale + 1;
				else
					result = decimal.scale() <= scale && String.valueOf(decimal.toBigInteger()).length() <= precision - scale;
			}

			if (!result) {

				String pattern = "number(" + precision.toString() + "," + scale.toString() + ")";

				throw new ValidatorException(MessageFactory.getMessage(context, NUMBER_VALIDATOR_INVALID_MESSAGE_ID, new Object[] { number, pattern }));

			}

		}

	}

	private static boolean isDecimal(String strBd) {
		try {
			new BigDecimal(strBd);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public Boolean getAllowNegative() {
		return allowNegative;
	}

	public void setAllowNegative(Boolean allowNegative) {
		this.allowNegative = allowNegative;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

}
