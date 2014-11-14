package hk.com.mtr.pcis.faces.validator;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import javax.faces.validator.DoubleRangeValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;

import com.sun.faces.util.MessageFactory;

public class RangeValidator implements Validator, StateHolder {

	public RangeValidator() {
		super();
	}

	private String minimumId;
	private String maximumId;

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		UIInput minComponent = null;
		UIInput maxComponent = null;

		if (minimumId != null) {
			minComponent = (UIInput) findComponent(context, minimumId);

		}
		if (maximumId != null) {
			maxComponent = (UIInput) findComponent(context, maximumId);

		}
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String strFromValue = null;
		String strToValue = null;
		if (minComponent != null) {

			if (minComponent.getClass().getName().equals("org.richfaces.component.html.HtmlCalendar"))
				strFromValue = request.getParameter(minComponent.getClientId(context) + "InputDate");
			else
				strFromValue = request.getParameter(minComponent.getClientId(context));

		}

		if (maxComponent != null) {
			if (maxComponent.getClass().getName().equals("org.richfaces.component.html.HtmlCalendar"))
				strToValue = request.getParameter(maxComponent.getClientId(context) + "InputDate");
			else
				strToValue = request.getParameter(maxComponent.getClientId(context));
		}
		if ((minComponent != null && minComponent.isValid()) || (maxComponent != null && maxComponent.isValid())) {
			Object label = MessageFactory.getLabel(context, component);

			if (value instanceof Date) {

				// for richCalendar
				String datePattern = (String) component.getAttributes().get("datePattern");

				Date minDateValue = null;
				Date maxDateValue = null;

				if (strFromValue != null && strFromValue.length() > 0) {
					minDateValue = strToUtilDate(strFromValue, datePattern);
				}
				if (strToValue != null && strToValue.length() > 0) {
					maxDateValue = strToUtilDate(strToValue, datePattern);
				}
				Date dateValue = (Date) value;
				if (minDateValue != null && value != null && dateValue.before(minDateValue)) {
					throw new ValidatorException(MessageFactory.getMessage(context, DoubleRangeValidator.MINIMUM_MESSAGE_ID, new Object[] { strFromValue, label }));
				}
				if (maxDateValue != null && value != null && dateValue.after(maxDateValue)) {
					throw new ValidatorException(MessageFactory.getMessage(context, DoubleRangeValidator.MAXIMUM_MESSAGE_ID, new Object[] { strToValue, label }));
				}

			} else if (value instanceof Number) {

				Double minNo = null;
				Double maxNo = null;
				Double numberValue = null;

				if (strFromValue != null && strFromValue.length() > 0) {
					minNo = Double.valueOf(strFromValue);
				}

				if (strToValue != null && strToValue.length() > 0) {
					maxNo = Double.valueOf(strToValue);
				}
				if (value != null)
					numberValue = ((Number) value).doubleValue();
				if (minNo != null && value != null && numberValue < minNo) {
					throw new ValidatorException(MessageFactory.getMessage(context, DoubleRangeValidator.MINIMUM_MESSAGE_ID, new Object[] { minNo, label }));
				}
				if (maxNo != null && value != null && numberValue > maxNo) {
					throw new ValidatorException(MessageFactory.getMessage(context, DoubleRangeValidator.MAXIMUM_MESSAGE_ID, new Object[] { maxNo, label }));
				}

			} else if (value instanceof String) {

				String minValue = null;
				String maxValue = null;
				String stringValue = null;
				if (strFromValue != null && strFromValue.length() > 0) {
					minValue = String.valueOf(strFromValue);
				}

				if (strToValue != null && strToValue.length() > 0) {
					maxValue = String.valueOf(strToValue);
				}
				if (value != null)
					stringValue = (String) value;
				if (minValue != null && value != null && stringValue.compareTo(minValue) < 0) {
					throw new ValidatorException(MessageFactory.getMessage(context, DoubleRangeValidator.MINIMUM_MESSAGE_ID, new Object[] { minValue, label }));
				}
				if (maxValue != null && value != null && stringValue.compareTo(maxValue) > 0) {
					throw new ValidatorException(MessageFactory.getMessage(context, DoubleRangeValidator.MAXIMUM_MESSAGE_ID, new Object[] { maxValue, label }));
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
		minimumId = (String) values[0];
		maximumId = (String) values[1];

	}

	public Object saveState(FacesContext context) {
		Object values[] = new Object[2];
		values[0] = minimumId;
		values[1] = maximumId;

		return values;

	}

	public String getMinimumId() {
		return minimumId;
	}

	public void setMinimumId(String minimumId) {
		this.minimumId = minimumId;
	}

	public String getMaximumId() {
		return maximumId;
	}

	public void setMaximumId(String maximumId) {
		this.maximumId = maximumId;
	}

	private java.util.Date strToUtilDate(String strDate, String pattern) {
		if (strDate != null && strDate.length() > 0 && pattern != null && pattern.length() > 0) {
			SimpleDateFormat smf = new SimpleDateFormat(pattern);
			ParsePosition pos = new ParsePosition(0);
			smf.setLenient(false);
			return smf.parse(strDate, pos);
		}
		return null;

	}

	private UIComponent findComponent(FacesContext context, String id) {
		UIComponent c = null;

		List<UIComponent> children = context.getViewRoot().getChildren();
		for (UIComponent child : children) {
			if (child.getId().equals(id)) {
				c = child;
				break;
			}

			c = findComponent(child, id);
			if (c != null) {
				break;
			}
		}

		return c;
	}

	private UIComponent findComponent(UIComponent component, String id) {
		UIComponent c = null;
		List<UIComponent> children = component.getChildren();
		for (UIComponent child : children) {

			if (child.getId().equals(id)) {
				c = child;
				break;
			}

			c = findComponent(child, id);
			if (c != null) {
				break;
			}
		}
		return c;

	}
}
