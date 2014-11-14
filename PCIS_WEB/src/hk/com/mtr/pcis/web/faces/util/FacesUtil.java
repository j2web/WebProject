package hk.com.mtr.pcis.web.faces.util;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.ajax4jsf.context.AjaxContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.contexts.Contexts;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import hk.com.mtr.pcis.criteria.AppBaseCriteriaVO;
import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.report.ReportFactory;
import hk.com.mtr.pcis.util.StringUtil;
import hk.com.mtr.pcis.vo.sa.ParameterVO;

public class FacesUtil {

	
	private static final Log log = LogFactory.getLog(FacesUtil.class);

	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public static UIComponent findComponent(String id) {
		FacesContext context = getFacesContext();
		return findComponent(context.getViewRoot(), id);
	}

	private static UIComponent findComponent(UIComponent component, String id) {
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

	public static void clearComponent(UIComponent component) {
		FacesContext facesContext = getFacesContext();
		AjaxContext ajaxContext = AjaxContext.getCurrentInstance(facesContext);

		ajaxContext.addRegionsFromComponent(component);
		Set<String> ids = ajaxContext.getAjaxAreasToRender();

		List<String> componentIds = new LinkedList<String>();

		for (String id : ids) {

			UIComponent c = FacesUtil.findComponent(id);
			if (c != null) {
				clearComponentHierarchy(c, componentIds);

			}
		}

	}

	private static void clearComponentHierarchy(UIComponent component, List<String> componentIds) {
		try {
			// if (component.isRendered()) {
			if (component instanceof EditableValueHolder && !componentIds.contains(component.getId())) {
				EditableValueHolder editableValueHolder = (EditableValueHolder) component;
				editableValueHolder.setSubmittedValue(null);
				editableValueHolder.setValue(null);
				editableValueHolder.setLocalValueSet(false);
				editableValueHolder.setValid(true);

				componentIds.add(component.getId());
			}

			for (Iterator<UIComponent> iterator = component.getFacetsAndChildren(); iterator.hasNext();) {
				clearComponentHierarchy(iterator.next(), componentIds);
			}

			// }
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static boolean isPostback() {
		FacesContext facesContext = getFacesContext();
		if (facesContext != null) {
			ExternalContext externalContext = facesContext.getExternalContext();
			if (externalContext != null) {
				return Boolean.TRUE.equals(externalContext.getRequestMap().get(Constant.POSTBACK_ATTRIBUTE_NAME));
			}
		}

		return false;
	}

	public static HttpServletRequest getRequest() {
		HttpServletRequest request = null;
		try {
			request = (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
		} catch (Exception e) {
		}
		return request;
	}

	public static HttpServletResponse getResponse() {
		HttpServletResponse response = null;
		try {
			response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
		} catch (Exception e) {
		}
		return response;
	}

	public static HttpSession getSession() {
		HttpSession session = null;
		try {
			session = (HttpSession) getFacesContext().getExternalContext().getSession(true);
		} catch (Exception e) {
		}
		return session;
	}

	@SuppressWarnings("unchecked")
	public static <T> T loadCriteriaVO(Class<? extends AppBaseCriteriaVO> cls) {
		T criteriaVO = (T) Contexts.getSessionContext().get(cls.getName());
		if (criteriaVO == null)
			try {
				criteriaVO = (T) cls.newInstance();
			} catch (IllegalAccessException e) {
				log.error("Can not generate criteriaVO:" + cls.getName(), e);
			} catch (InstantiationException e) {
				log.error("Can not create instance of criteriaVO:" + cls.getName(), e);
			}
		return criteriaVO;
	}

	public static void saveCriteriaVO(AppBaseCriteriaVO appBaseCriteriaVO) {
		Contexts.getSessionContext().set(appBaseCriteriaVO.getClass().getName(), appBaseCriteriaVO);
	}

	public static void resetCriteriaVO(AppBaseCriteriaVO appBaseCriteriaVO) {
		String innerClassPattern = appBaseCriteriaVO.getClass().getName() + "$";
		Field[] fields = appBaseCriteriaVO.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (!field.getName().equals("serialVersionUID")) {
				Object val = null;
				try {
					val = field.get(appBaseCriteriaVO);
				} catch (Exception e) {
				}

				if (val != null) {
					if (val instanceof Number || val instanceof String || val instanceof java.util.Date) {
						try {
							field.set(appBaseCriteriaVO, null);
						} catch (Exception e) {

						}
					} else {
						if (field.getType().getName().startsWith(innerClassPattern)) {
							fields = val.getClass().getDeclaredFields();
							for (Field field2 : fields) {
								field2.setAccessible(true);
								try {
									field2.set(val, null);
								} catch (Exception e) {
								}
							}
						}
					}
				}
			}
		}

		saveCriteriaVO(appBaseCriteriaVO);

	}

	private static String applicationContextPath = null;

	public static String getContextPath() {
		try {
			if (StringUtil.isEmpty(applicationContextPath)) {
				applicationContextPath = getFacesContext().getExternalContext().getRequestContextPath();
			}
		} catch (Exception e) {
		}

		return applicationContextPath;
	}

	public static String getRequestUrl() {
		String url = null;
		try {
			url = getFacesContext().getExternalContext().getRequestServletPath();
		} catch (Exception e) {
		}
		if (url != null)
			return url.replaceAll("\\?.*", "");
		else
			return null;
	}

	public static SystemUser getCurrentUser() {
		SystemUser systemUser = (SystemUser) getFacesContext().getExternalContext().getSessionMap().get("systemUser");
		return systemUser;
	}

	public static void generateReport(String reportId, Map<String, Object> inputParas, String exportFormat) {
		HttpServletRequest request = FacesUtil.getRequest();
		HttpServletResponse response = FacesUtil.getResponse();
		ReportFactory.GenerateReport(reportId, inputParas, ReportExportFormat.from_string(exportFormat), request, response);
		FacesUtil.getFacesContext().responseComplete();
	}

	public static ParameterVO getParameterVO(String key) {
		try {
			return ListboxUtil.getParameterVOMap().get(key);
		} catch (BusinessException e) {
			log.error("Can not get system parameter,", e);
			return null;
		}
	}
	
	public static void exportExcel(String content, String fileName) {
		try {
			exportFile(content.getBytes(Constant.CHARSET_UTF8_VALUE), Constant.CONTENT_TYPE_EXCEL, fileName + ".xls");
		} catch (UnsupportedEncodingException e) {
			log.error("Can not export excel to response stream. ", e);
		}
	}
	
	private static boolean isRequireWriteBOM(String fileName) {
		boolean isWriteBOM = true;

//		String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
//		if (Constant.FileExtName.CSV.equalsIgnoreCase(fileExtName)) {
//			isWriteBOM = true;
//		}

		return isWriteBOM;
	}

	private static void exportFile(byte[] content, String contentType, String fileName) {
		FacesUtil.getResponse().reset();
		FacesUtil.getResponse().setContentType(contentType);
		FacesUtil.getResponse().setHeader("Content-disposition", "attachment;filename=" + fileName);
		try {
			if (isRequireWriteBOM(fileName)) {
				// print UTF-8 BOM for MS excel
				FacesUtil.getResponse().getOutputStream().write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
			}
			FacesUtil.getResponse().getOutputStream().write(content);
		} catch (Exception e) {
			log.error("Can not export " + fileName + " to response stream.", e);
		} finally {
			// close response stream
			try {
				FacesUtil.getResponse().getOutputStream().flush();
				FacesUtil.getResponse().getOutputStream().close();
			} catch (Exception e1) {
				log.error("Can not close response stream.", e1);
			}
			FacesUtil.getFacesContext().responseComplete();
		}
	}
}
