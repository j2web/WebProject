package hk.com.mtr.pcis.web.faces.validator;

import hk.com.mtr.pcis.web.faces.util.ValidationUtil;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sun.faces.util.MessageFactory;

public class HKIDValidator implements Validator{
	
	public void validate(FacesContext context, UIComponent component, Object value)   
		throws ValidatorException {   		
		if(value != null) {
			boolean isValid = ValidationUtil.validateHKID(value.toString());
			if(!isValid) {
				FacesMessage msg = MessageFactory.getMessage(context, "com.mtrc.pcis.HKIDValidator.HKID", value.toString());
				throw new ValidatorException(msg);				
			}			
		}			
	}
	
}
