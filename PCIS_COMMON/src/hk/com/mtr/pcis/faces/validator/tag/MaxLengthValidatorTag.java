package hk.com.mtr.pcis.faces.validator.tag;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

import hk.com.mtr.pcis.faces.validator.MaxLengthValidator;
import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.jsf.ValidatorConfig;

public class MaxLengthValidatorTag extends com.sun.facelets.tag.jsf.ValidateHandler {
	public MaxLengthValidatorTag(ValidatorConfig config) {
		super(config);

		maxlength = this.getAttribute("maxlength");
		charset = this.getAttribute("charset");
	}

	protected final TagAttribute maxlength;
	protected final TagAttribute charset;

	@Override
	protected Validator createValidator(FaceletContext ctx) {
		MaxLengthValidator validator = new MaxLengthValidator();
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		
		if (this.charset != null) {
			String charset = (String) (this.charset.getValueExpression(ctx, String.class).getValue(elContext));
			validator.setCharset(charset);
		}

		if (this.maxlength != null) {
			Integer maxlength = (Integer) this.maxlength.getValueExpression(ctx, Integer.class).getValue(elContext);
			validator.setMaxlength(maxlength);
		}

		return validator;
	}
}
