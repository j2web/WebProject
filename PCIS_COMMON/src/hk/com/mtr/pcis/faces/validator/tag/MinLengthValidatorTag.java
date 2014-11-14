package hk.com.mtr.pcis.faces.validator.tag;

import hk.com.mtr.pcis.faces.validator.MinLengthValidator;

import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.jsf.ValidatorConfig;

public class MinLengthValidatorTag extends com.sun.facelets.tag.jsf.ValidateHandler {
	public MinLengthValidatorTag(ValidatorConfig config) {
		super(config);

		minlength = this.getAttribute("minlength");
		charset = this.getAttribute("charset");
	}

	protected final TagAttribute minlength;
	protected final TagAttribute charset;

	@Override
	protected Validator createValidator(FaceletContext ctx) {
		MinLengthValidator validator = new MinLengthValidator();
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		
		if (this.charset != null) {
			String charset = (String) (this.charset.getValueExpression(ctx, String.class).getValue(elContext));
			validator.setCharset(charset);
		}

		if (this.minlength != null) {
			Integer minlength = (Integer) this.minlength.getValueExpression(ctx, Integer.class).getValue(elContext);
			validator.setMinlength(minlength);
		}

		return validator;
	}
}
