package hk.com.mtr.pcis.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import hk.com.mtr.pcis.exception.ReportException;
import hk.com.mtr.pcis.util.EncryptUtil;
import hk.com.mtr.pcis.util.StringUtil;

public class ReportConfiguration {
	private final static Log log = LogFactory.getLog(ReportConfiguration.class);

	private static Hashtable<String, ReportSetting> config;
	
	private static ReportServerSetting reportServerSetting;
	
	public static ReportServerSetting getReportServerSetting() {
		return reportServerSetting;
	}

	public static void setReportServerSetting(
			ReportServerSetting reportServerSetting) {
		ReportConfiguration.reportServerSetting = reportServerSetting;
	}

	public static void main(String[] arg0) throws ReportException {
		ReportConfiguration.configure();
	}

	public static Hashtable<String, ReportSetting> buildAllReportSetting() throws ReportException {
		if (config == null) {
			config = new Hashtable<String, ReportSetting>();
		}

		return config;
	}

	public static ReportSetting getReportSetting(String reportId) throws ReportException {
		if (StringUtil.isEmpty(reportId)) {
			throw new ReportException("The reportId is empty, please check it !!");
		}
		if (!config.containsKey(reportId))
			throw new ReportException("Can not find the report setting for reportId = " + reportId);
		ReportSetting reportSetting = (ReportSetting) config.get(reportId);

		return (ReportSetting) (reportSetting.clone());
	}

	public static void configure() throws ReportException {
		configure("/report.cfg.xml");
	}

	public static void configure(String resource) throws ReportException {
		buildAllReportSetting();

		InputStream stream = getResourceAsStream(resource);
		if (stream != null) {
			InputSource source = new InputSource(stream);

			DOMParser parser = new DOMParser();
			try {
				parser.parse(source);
			} catch (Exception e) {
				throw new ReportException("can not init report configure file", e);
			}

			Document doc = parser.getDocument();
			Element rootElement = doc.getDocumentElement();
			doConfigure(rootElement, resource);

			try {
				stream.close();
			} catch (IOException e) {
				throw new ReportException("can not init report configure file", e);
			}

		}

	}

	private static void doConfigure(Element parent, String resource) {
		NodeList listChild;
		listChild = parent.getElementsByTagName("server");
		if (listChild != null && listChild.getLength() > 0) {
			Element serverElement = (Element) listChild.item(0);
			reportServerSetting = buildReportServerSetting(serverElement, resource);
		}
		// get report configuration
		listChild = parent.getElementsByTagName("report");
		int iChild = listChild.getLength();

		ReportSetting reportSetting = null;
		for (int i = 0; i < iChild; i++) {
			Element e = (Element) (listChild.item(i));
			reportSetting = buildReportSetting(e);
			reportSetting.setReportServerSetting(reportServerSetting);
			config.put(reportSetting.getReportId(), reportSetting);
		}
	}

	private static ReportServerSetting buildReportServerSetting(Element element, String resource) {
		ReportServerSetting reportServerSetting = new ReportServerSetting();

		NodeList listChild = element.getElementsByTagName("property");

		String propertyName = null;
		Element att;

		for (int i = 0; i < listChild.getLength(); i++) {

			att = (Element) (listChild.item(i));
			propertyName = att.getAttribute("name");
			if (propertyName != null && propertyName.length() > 0) {
				String propertyValue = att.getChildNodes().item(0).getNodeValue();

				if (propertyName.equalsIgnoreCase("report.odbc.username"))
					reportServerSetting.setUserName(propertyValue);
				else if (propertyName.equalsIgnoreCase("report.odbc.password")) {
					reportServerSetting.setPassword(getPassword(propertyValue));
				} else if (propertyName.equalsIgnoreCase("report.odbc.dsn"))
					reportServerSetting.setDsn(propertyValue);
				else if (propertyName.equalsIgnoreCase("report.odbc.dbname"))
					reportServerSetting.setDatabaseName(propertyValue);
				else if (propertyName.equalsIgnoreCase("report.server.changeTableLocation"))
					reportServerSetting.setChangeTableLocation(propertyValue.equalsIgnoreCase("Y"));
				else if (propertyName.equalsIgnoreCase("report.cms.coucurrent"))
					reportServerSetting.setMaxConcurrent(Integer.parseInt(propertyValue));
				else if (propertyName.equalsIgnoreCase("report.cms.username"))
					reportServerSetting.setCmsUserName(propertyValue);
				else if (propertyName.equalsIgnoreCase("report.cms.password")) {
					reportServerSetting.setCmsPassword(getPassword(propertyValue));
				} else if (propertyName.equalsIgnoreCase("report.cms.cmsName"))
					reportServerSetting.setCmsName(propertyValue);
				else if (propertyName.equalsIgnoreCase("report.cms.authenticationType"))
					reportServerSetting.setCmsAuthenticationType(propertyValue);
				else if (propertyName.equalsIgnoreCase("report.cms.folder")) {
					reportServerSetting.setCmsFolderName(propertyValue);
					ReportCommon reportCommon = new ReportCommon(reportServerSetting);
					try {
						reportServerSetting.setCmsFolderId(reportCommon.getReportParentFolderId());
						log.debug("Initialize successfully BO XI! The Folder Id = " + reportServerSetting.getCmsFolderId());

					} catch (ReportException e) {
						log.error("getReportParentFolderId occur error. ", e);
					}
				}
			}
		}
		return reportServerSetting;
	}

	private static ReportSetting buildReportSetting(Element e) {
		String reportId = e.getAttribute("id");
		ReportSetting reportSetting = new ReportSetting(reportId);

		NodeList listChild = e.getElementsByTagName("property");

		String propertyName, propertyValue = null;
		Element att;

		for (int i = 0; i < listChild.getLength(); i++) {

			att = (Element) (listChild.item(i));
			propertyName = att.getAttribute("name");
			if (propertyName != null) {
				if (att.getChildNodes().getLength() > 0) {
					propertyValue = att.getChildNodes().item(0).getNodeValue();

					if (propertyName.equalsIgnoreCase("reportFileName")) {
						reportSetting.setReportFileName(propertyValue);
					} else if (propertyName.equalsIgnoreCase("outputFileName"))
						reportSetting.setOutputFileName(propertyValue);
					else if (propertyName.equalsIgnoreCase("outputDirectory"))
						reportSetting.setOutputDirectory(propertyValue);
				}
			}
		}

		return reportSetting;
	}

	private static InputStream getResourceAsStream(String resource) throws ReportException {
		String stripped = resource.startsWith("/") ? resource.substring(1) : resource;

		InputStream stream = null;
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader != null) {
			stream = classLoader.getResourceAsStream(stripped);
		}

		if (stream == null) {
			throw new ReportException(resource + " not found");
		}
		return stream;
	}

	private static String getPassword(String encryptFile) {
		String password = null;
		try {
			File file = new File(encryptFile);
			javax.crypto.SecretKey deskey = EncryptUtil.genDESKey(EncryptUtil.KEY_BYTE);

			FileInputStream fis = new FileInputStream(file);
			byte[] encrypt = new byte[fis.available()];
			fis.read(encrypt, 0, fis.available());
			fis.close();
			byte[] decrypt = EncryptUtil.desDecrypt(deskey, encrypt);
			password = new String(decrypt);

		} catch (Exception e) {
			throw new ReportException("The encrypt password file is not available.");
		}
		
		return password;
	}

}
