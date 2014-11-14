package hk.com.mtr.pcis.faces.validator.tag;

import javax.el.ELContext;

import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

import hk.com.mtr.pcis.faces.validator.NumberValidator;
import com.sun.facelets.FaceletContext;

import com.sun.facelets.tag.TagAttribute;

import com.sun.facelets.tag.jsf.ValidatorConfig;

public class NumberValidatorTag extends com.sun.facelets.tag.jsf.ValidateHandler {
	public NumberValidatorTag(ValidatorConfig config) {
		super(config);

		precision = this.getRequiredAttribute("precision");
		scale = this.getRequiredAttribute("scale");
		allowNegative = this.getAttribute("allowNegative");
	}

	private static final long serialVersionUID = -2744621726775506883L;

	protected final TagAttribute allowNegative;
	protected final TagAttribute precision;
	protected final TagAttribute scale;

	@Override
	protected Validator createValidator(FaceletContext ctx) {

		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		Boolean allowNegative = null;
		if (this.allowNegative != null) {
			allowNegative = (Boolean) this.allowNegative.getValueExpression(ctx, Boolean.class).getValue(elContext);
		}
		Integer precision = (Integer) this.precision.getValueExpression(ctx, Integer.class).getValue(elContext);
		Integer scale = (Integer) this.scale.getValueExpression(ctx, Integer.class).getValue(elContext);

		NumberValidator validator = new NumberValidator();

		validator.setAllowNegative(allowNegative);
		validator.setPrecision(precision);
		validator.setScale(scale);
		return validator;
	}

}
