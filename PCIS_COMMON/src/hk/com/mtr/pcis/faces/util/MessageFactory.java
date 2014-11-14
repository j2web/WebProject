package hk.com.mtr.pcis.faces.util;

import javax.el.ValueExpression;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageFactory {
	private static final String RESOURCE_BASENAME = "hk.com.mtr.pcis.faces.Messages";

	public static FacesMessage getMessage(FacesContext context, String messageId, Object... params) {

		if (context == null || messageId == null) {
			throw new NullPointerException(" context " + context + " messageId " + messageId);
		}
		Locale locale;
		// viewRoot may not have been initialized at this point.
		if (context.getViewRoot() != null) {
			locale = context.getViewRoot().getLocale();
		} else {
			locale = Locale.getDefault();
		}

		if (null == locale) {
			throw new NullPointerException(" locale is null ");
		}

		FacesMessage message = getMessage(locale, messageId, params);
		if (message != null) {
			return message;
		}
		locale = Locale.getDefault();
		return (getMessage(locale, messageId, params));
	}

	public static FacesMessage getMessage(Locale locale, String messageId, Object... params) {
		String summary = null;
		String detail = null;
		ResourceBundle bundle;
		String bundleName;

		// see if we have a user-provided bundle
		if (null != (bundleName = getApplication().getMessageBundle())) {
			if (null != (bundle = ResourceBundle.getBundle(bundleName, locale, getCurrentLoader(bundleName)))) {
				// see if we have a hit
				try {
					summary = bundle.getString(messageId);
					detail = bundle.getString(messageId + "_detail");
				} catch (MissingResourceException e) {
					// ignore
				}
			}
		}

		// we couldn't find a summary in the user-provided bundle
		if (null == summary) {
			// see if we have a summary in the app provided bundle
			bundle = ResourceBundle.getBundle(FacesMessage.FACES_MESSAGES, locale, getCurrentLoader(bundleName));
			if (null == bundle) {
				throw new NullPointerException();
			}
			// see if we have a hit
			try {
				summary = bundle.getString(messageId);
				detail = bundle.getString(messageId + "_detail");
			} catch (MissingResourceException e) {
				// ignore
			}
		}

		// no hit found in the standard javax.faces.Messages bundle.
		// check the Mojarra resources
		if (summary == null) {
			// see if we have a summary in the app provided bundle
			bundle = ResourceBundle.getBundle(RESOURCE_BASENAME, locale, getCurrentLoader(bundleName));
			if (null == bundle) {
				throw new NullPointerException();
			}
			// see if we have a hit
			try {
				summary = bundle.getString(messageId);
			} catch (MissingResourceException e) {
				return null;
			}
		}

		// At this point, we have a summary and a bundle.
		FacesMessage ret = new BindingFacesMessage(locale, summary, detail, params);
		ret.setSeverity(FacesMessage.SEVERITY_ERROR);
		return (ret);
	}

	protected static Application getApplication() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			return (FacesContext.getCurrentInstance().getApplication());
		}
		ApplicationFactory afactory = (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
		return (afactory.getApplication());
	}

	protected static ClassLoader getCurrentLoader(Object fallbackClass) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = fallbackClass.getClass().getClassLoader();
		}
		return loader;
	}

	static class BindingFacesMessage extends FacesMessage {

		private static final long serialVersionUID = 4640579705862558345L;

		BindingFacesMessage(Locale locale, String messageFormat, String detailMessageFormat,
		// array of parameters, both Strings and ValueBindings
				Object[] parameters) {

			super(messageFormat, detailMessageFormat);
			this.locale = locale;
			this.parameters = parameters;
			if (parameters != null) {
				resolvedParameters = new Object[parameters.length];
			}
		}

		public String getSummary() {
			String pattern = super.getSummary();
			resolveBindings();
			return getFormattedString(pattern, resolvedParameters);
		}

		public String getDetail() {
			String pattern = super.getDetail();
			resolveBindings();
			return getFormattedString(pattern, resolvedParameters);
		}

		private void resolveBindings() {
			FacesContext context = null;
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					Object o = parameters[i];

					if (o instanceof ValueExpression) {
						if (context == null) {
							context = FacesContext.getCurrentInstance();
						}
						o = ((ValueExpression) o).getValue(context.getELContext());
					}
					// to avoid 'null' appearing in message
					if (o == null) {
						o = "";
					}
					resolvedParameters[i] = o;
				}
			}
		}

		private String getFormattedString(String msgtext, Object[] params) {
			String localizedStr = null;

			if (params == null || msgtext == null) {
				return msgtext;
			}
			StringBuffer b = new StringBuffer();
			MessageFormat mf = new MessageFormat(msgtext);
			if (locale != null) {
				mf.setLocale(locale);
				b.append(mf.format(params));
				localizedStr = b.toString();
			}
			return localizedStr;
		}

		private Locale locale;
		private Object[] parameters;
		private Object[] resolvedParameters;
	}
}
