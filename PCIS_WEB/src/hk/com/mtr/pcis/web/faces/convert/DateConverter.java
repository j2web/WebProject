package hk.com.mtr.pcis.web.faces.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.richfaces.component.html.HtmlCalendar;

/**
 * convert date
 * 
 * @author YeJunhua
 * @author modify by Justin 2010-11-22
 */
public class DateConverter implements Converter {

	private Pattern numberPattern = Pattern.compile("[0-9]{8,}");
	private static final String sourceDateFormat = "ddMMyyyy";
	private SimpleDateFormat sdf = new SimpleDateFormat();

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Date currentDate = null;
		try {
			if (component instanceof HtmlCalendar && value != null) {
				String pattern = ((HtmlCalendar) component).getDatePattern();
				Matcher m = numberPattern.matcher(value);
				if (pattern != null && m.find()) {
					sdf.applyPattern(sourceDateFormat);
					currentDate = sdf.parse(value);				
				} 
				else if (pattern != null) {
					sdf.applyPattern(pattern);
					currentDate = sdf.parse(value);
				}
			}
		} catch (ParseException ex) {
			throw new ConverterException(ex);			
		}
		return currentDate;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		String strDate = "";
		try {
			if (component instanceof HtmlCalendar && value != null) {
				String pattern = ((HtmlCalendar) component).getDatePattern();
				if (pattern != null){
					sdf.applyPattern(pattern);
					strDate = sdf.format(value);
				}
			}
		} catch (Exception ex) {
			throw new ConverterException(ex);
		}
		return strDate;
	}

//	public static void main(String[] args) {		
//		DateConverter converter = new DateConverter();
//		HtmlCalendar htmlCalendar = new HtmlCalendar();
//		htmlCalendar.setDatePattern("yyyy-MM-dd");
//		System.out.println(converter.getAsString(null, htmlCalendar, new Date()));
//		System.out.println(converter.getAsObject(null, htmlCalendar, "22112010"));
//		System.out.println(converter.getAsObject(null, htmlCalendar, "2010-11-12"));
//	}
}
