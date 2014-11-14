package hk.com.mtr.pcis.faces.validator.tag;

import javax.el.ELContext;

import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

import hk.com.mtr.pcis.faces.validator.RangeValidator;
import com.sun.facelets.FaceletContext;

import com.sun.facelets.tag.TagAttribute;

import com.sun.facelets.tag.jsf.ValidatorConfig;

public class RangeValidatorTag extends com.sun.facelets.tag.jsf.ValidateHandler {
	public RangeValidatorTag(ValidatorConfig config) {
		super(config);

		minimumId = this.getAttribute("minimumId");
		maximumId = this.getAttribute("maximumId");

	}

	protected final TagAttribute minimumId;
	protected final TagAttribute maximumId;

	@Override
	protected Validator createValidator(FaceletContext ctx) {

		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		String minId = null;
		String maxId = null;
		if (this.minimumId != null) {
			minId = (String) this.minimumId.getValueExpression(ctx, String.class).getValue(elContext);
		}
		if (this.maximumId != null) {
			maxId = (String) this.maximumId.getValueExpression(ctx, String.class).getValue(elContext);
		}

		RangeValidator validator = new RangeValidator();

		validator.setMinimumId(minId);
		validator.setMaximumId(maxId);
		return validator;
	}

}
