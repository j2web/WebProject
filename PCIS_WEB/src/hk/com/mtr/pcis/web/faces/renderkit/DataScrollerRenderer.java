package hk.com.mtr.pcis.web.faces.renderkit;

import java.io.IOException;
import java.text.MessageFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.jboss.seam.international.Messages;
import org.richfaces.component.UIDatascroller;

public class DataScrollerRenderer extends org.richfaces.renderkit.html.DatascrollerTemplate {

	@Override
	public void doEncodeEnd(ResponseWriter writer, FacesContext context, UIComponent component) throws IOException {

		UIDatascroller scroller = (UIDatascroller) component;
		int rowCount = scroller.getRowCount();
		int pageCount = scroller.getPageCount();
		int page = scroller.getPage();

		String pages = Messages.instance().get("label.common.pages");
		String records = Messages.instance().get("label.common.records");

		writer.startElement("table", component);
		writer.startElement("tbody", component);
		writer.startElement("tr", component);
		writer.startElement("td", component);
		writer.writeAttribute("class", "recordcount-cell", null);
		writer.writeText(pages + ": " + page + " / " + pageCount + "  (" + records + ": " + rowCount + ")", null);
		writer.endElement("td");

		writer.startElement("td", component);
		super.doEncodeEnd(writer, context, component);
		writer.endElement("td");

		boolean showGoto = false;
		if (pageCount > scroller.getMaxPages()) {
			showGoto = true;
			writer.startElement("td", component);
			String id = component.getClientId(context) + "_goto";
			writer.startElement("input", component);
			writer.writeAttribute("type", "text", null);
			writer.writeAttribute("id", id, null);
			writer.writeAttribute("class", "input", null);
			writer.writeAttribute("style", "width:20px", null);
			writer.writeAttribute("value", page, null);
			writer.endElement("input");
			writer.endElement("td");
			writer.startElement("td", component);
			writer.startElement("input", component);
			writer.writeAttribute("type", "button", null);
			writer.writeAttribute("value", Messages.instance().get("button.common.go"), null);

			writer.writeAttribute("onclick", "return gotoPage('" + id + "')", null);
			writer.endElement("input");
			writer.endElement("td");

		}
		writer.endElement("tr");
		writer.endElement("tbody");
		writer.endElement("table");
		if (showGoto) {
			StringBuilder script = new StringBuilder();
			script.append("<script language=\"javascript\">");
			script.append("//<![CDATA[");
			script.append("\nfunction gotoPage(id){");
			script.append("\n\t\tvar txt=document.getElementById(id);");
			script.append("\n\t\tvar page=txt.value;");

			script.append("\n\t\tif (!isNaN(page) && page>0 && page<=" + pageCount + "){");
			script.append("\n\t\t\t\tdocument.getElementById('" + component.getClientId(context) + "').component.switchToPage(page);");
			script.append("\n\t\t\t\treturn true;");
			script.append("\n\t\t}");

			script.append("\n\t\talert(\"" + MessageFormat.format(Messages.instance().get("msg.common.invalidPage"), 1, pageCount) + "\");");
			script.append("\n\t\treturn false;");
			script.append("\n}");
			script.append("\n//]]>");
			script.append("\n</script>");
			writer.write(script.toString());
		}
	}

}
