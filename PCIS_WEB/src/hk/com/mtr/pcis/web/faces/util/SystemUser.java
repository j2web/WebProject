package hk.com.mtr.pcis.web.faces.util;

import hk.com.mtr.pcis.facade.sa.MenuFacade;

import java.io.File;
import java.io.Serializable;
import java.io.StringReader;

import java.io.StringWriter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.international.LocaleSelector;
import org.jboss.seam.international.Messages;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.util.StringUtil;
import hk.com.mtr.pcis.vo.sa.MenuVO;

@Name("systemUser")
@Scope(ScopeType.SESSION)
@Startup
public class SystemUser implements Serializable {

	private static final long serialVersionUID = -8591042771006651739L;
	@Logger
	protected static Log log;
	@In
	private Identity identity;
	@In
	private LocaleSelector localeSelector;
	private String menu;
	private boolean hideMenu;

	private Integer teamNo;

	public Integer getTeamNo() {
		return teamNo;
	}

	public void setTeamNo(Integer teamNo) {
		this.teamNo = teamNo;
	}

	private Map<String, List<String>> privilegeList = new HashMap<String, List<String>>();

	public String getUserId() {
		if (identity != null && identity.getPrincipal() != null)
			return identity.getPrincipal().getName().toUpperCase();
		else
			return null;

	}

	public String getDatePattern() {
		if (localeSelector.getLocale().equals(Locale.ENGLISH))
			return FacesUtil.getParameterVO("ENG_DATE_FORMAT").getParamCharValue();
		else
			return FacesUtil.getParameterVO("CHS_DATE_FORMAT").getParamCharValue();
	}

	public String getDatetimePattern() {
		if (localeSelector.getLocale().equals(Locale.ENGLISH))
			return FacesUtil.getParameterVO("ENG_DATE_FORMAT").getParamCharValue() + " " + FacesUtil.getParameterVO("TIME_FORMAT").getParamCharValue();			
		else
			return FacesUtil.getParameterVO("CHS_DATE_FORMAT").getParamCharValue() + " " + FacesUtil.getParameterVO("TIME_FORMAT").getParamCharValue();
	}

	public void changeLocale(ValueChangeEvent event) {
		localeSelector.select(event);
		menu = null;

		FacesContext context = FacesUtil.getFacesContext();
		applyNewDateStyle(context, context.getViewRoot());

		context.renderResponse();

	}

	private void applyNewDateStyle(FacesContext context, UIComponent component) {
		if (component instanceof HtmlOutputText) {

			HtmlOutputText output = (HtmlOutputText) component;
			if (output.getConverter() != null && output.getConverter() instanceof org.jboss.seam.ui.converter.DateTimeConverter) {
				org.jboss.seam.ui.converter.DateTimeConverter converter = (org.jboss.seam.ui.converter.DateTimeConverter) output.getConverter();

				converter.setPattern(getDatePattern());

			}
		}
		List<UIComponent> children = component.getChildren();
		for (UIComponent child : children) {
			applyNewDateStyle(context, child);
		}
	}

	public String getMenu() {

		if (getUserId() != null && menu == null) {
			try {
				HttpSession session = (HttpSession) FacesUtil.getSession();

				String xsltPath = "style" + File.separatorChar + "menu.xslt";
				File xsltFile = new File(session.getServletContext().getRealPath(xsltPath));

				String userXmlMenu = buildMenu();
				if (userXmlMenu == null) {
					log.fatal("Can not load user menu");
				} else {
					StringReader reader = null;
					reader = new StringReader(userXmlMenu);
					StreamSource xmlSource = new StreamSource();
					xmlSource.setReader(reader);

					Source xsltSource = new StreamSource(xsltFile);
					TransformerFactory transFact = TransformerFactory.newInstance();
					Transformer trans;

					Templates templates = transFact.newTemplates(xsltSource);

					trans = templates.newTransformer();
					StringWriter write = new StringWriter();
					trans.transform(xmlSource, new StreamResult(write));
					menu = write.toString();
				}
			} catch (Exception e) {
				log.error("can not get user menu.", e);
			}
		}

		return menu;
	}

	public String buildMenu() throws BusinessException {

		MenuFacade menuFacade = (MenuFacade) ServiceUtil.getService(MenuFacade.class);
		List<MenuVO> menuList = null;

		menuList = menuFacade.findMenuByUserId(this.getUserId());

		String menuXml = null;
		if (menuXml == null) {
			StringBuffer xml = new StringBuffer();
			if (menuList != null) {
				xml.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
				xml.append("<Menu>");
				buildSubMenu(menuList, null, xml);
				xml.append("</Menu>");
			}
			menuXml = xml.toString();
		}
		return menuXml;
	}

	private void buildSubMenu(List<MenuVO> menuList, String parentMenuId, StringBuffer xml) {
		MenuVO menuVO;
		Iterator<MenuVO> it = menuList.iterator();
		while (it.hasNext()) {
			menuVO = (MenuVO) it.next();

			if ((parentMenuId == null && StringUtil.isEmpty(menuVO.getParentMenuId())) || (parentMenuId != null && parentMenuId.equals(menuVO.getParentMenuId()))) {
				xml.append("<MenuItem");
				xml.append(" ID='" + menuVO.getMenuId() + "'");
				String name = Messages.instance().get(menuVO.getResourceKey());
				if(name.indexOf("&")>=0){
					name = name.replaceAll("&", "&amp;");
				}
				xml.append(" Name='" + name + "'");
				if (StringUtil.isNotEmpty(menuVO.getLocation()))
					xml.append(" Url='" + FacesUtil.getContextPath() + menuVO.getLocation() + "'");
				if (menuVO.getDisplaySeq() != null)
					xml.append(" Sequence='" + menuVO.getDisplaySeq() + "'");
				xml.append(" >");

				buildSubMenu(menuList, menuVO.getMenuId(), xml);
				xml.append("</MenuItem>");
			}
		}
	}

	public Map<String, List<String>> getPrivilegeList() {
		return privilegeList;
	}

	public boolean isHideMenu() {
		return hideMenu;
	}

	public void hideLeftMenu() {
		this.hideMenu = true;
	}

	public void showLeftMenu() {
		this.hideMenu = false;
	}
}
