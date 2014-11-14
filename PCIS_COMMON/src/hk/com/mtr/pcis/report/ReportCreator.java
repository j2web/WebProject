package hk.com.mtr.pcis.report;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.framework.ISessionMgr;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.occa.managedreports.IReportAppFactory;

import com.crystaldecisions.sdk.occa.report.application.OpenReportOptions;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.data.Fields;
import com.crystaldecisions.sdk.occa.report.data.ParameterField;
import com.crystaldecisions.sdk.occa.report.data.ParameterFieldDiscreteValue;
import com.crystaldecisions.sdk.occa.report.data.Values;
import com.crystaldecisions.sdk.occa.report.definition.AreaSectionKind;
import com.crystaldecisions.sdk.occa.report.exportoptions.ExcelExportFormatOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ExportOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ExportPageAreaPairKind;
import com.crystaldecisions.sdk.occa.report.exportoptions.IExportOptions;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase;

import hk.com.mtr.pcis.exception.ReportException;

public class ReportCreator {
	private final static Log log = LogFactory.getLog(ReportCreator.class);

	public ReportCreator(ReportSetting reportSetting, Map<String, Object> inputParameters) {
		this.reportSetting = reportSetting;
		this.inputParameters = inputParameters;
		this.outputDirectory = reportSetting.getOutputDirectory();
		this.outputFileName = reportSetting.getOutputFileName();

	}

	private ReportSetting reportSetting;

	private Map<String, Object> inputParameters;

	private String outputDirectory;

	private String outputFileName;

	private String mime;

	private final int BUFFER_SIZE = 512 * 1024;

	private final String SQL_FOUND_ID_NAME = "Select SI_ID,SI_NAME From CI_INFOOBJECTS Where SI_Name ='";

	private IEnterpriseSession reportSession;

	private void setExportOption() {
		if (reportSetting != null) {
			if (reportSetting.getExportFormat().equals(ReportExportFormat.PDF)) {
				outputFileName = outputFileName + ".pdf";
				mime = "application/pdf";
			} else if (reportSetting.getExportFormat().equals(ReportExportFormat.MSExcel)) {
				outputFileName = outputFileName + ".xls";
				mime = "application/vnd.ms-excel";
			} else if (reportSetting.getExportFormat().equals(ReportExportFormat.text)) {
				outputFileName = outputFileName + ".txt";
				mime = "text/download";
			} else if (reportSetting.getExportFormat().equals(ReportExportFormat.RTF)) {
				outputFileName = outputFileName + ".rtf";
				mime = "application/rtf";
			}

		}
	}

	private IInfoObjects createIInfoObjects() throws SDKException, ReportException {

		ISessionMgr ceSessionMgr = null;
		IInfoStore ceInfoStore = null;
		ReportServerSetting reportServerSetting = reportSetting.getReportServerSetting();
		ceSessionMgr = CrystalEnterprise.getSessionMgr();
		reportSession = ceSessionMgr.logon(reportServerSetting.getCmsUserName(), reportServerSetting.getCmsPassword(), reportServerSetting.getCmsName(), reportServerSetting
				.getCmsAuthenticationType());
		ceInfoStore = (IInfoStore) reportSession.getService("InfoStore");

		StringBuffer query = new StringBuffer(SQL_FOUND_ID_NAME);

		query.append(reportSetting.getReportFileName()).append("'");
		if (reportServerSetting.getCmsFolderId() > 0)
			query.append(" AND SI_PARENTID=").append(reportServerSetting.getCmsFolderId());

		log.info("Query CMS for ReportId = " + reportSetting.getReportId() + " : " + query.toString());
		return ceInfoStore.query(query.toString());
	}

	private ReportClientDocument prepareExportReport() throws ReportException {

		ReportClientDocument reportClientDocument = null;
		try {
			setExportOption();
			IInfoObjects ceReports = createIInfoObjects();
			if (ceReports != null && ceReports.size() > 0) {
				IReportAppFactory rptAppFactory = (IReportAppFactory) reportSession.getService("RASReportFactory");
				IInfoObject report = (IInfoObject) ceReports.get(0);
				reportClientDocument = rptAppFactory.openDocument(report, OpenReportOptions.openAsReadOnly.value(), Locale.ENGLISH);
				if (reportSetting.getReportServerSetting().isChangeTableLocation()) {
					TableLocation.changeTableLocation(reportClientDocument, reportSetting.getReportServerSetting());
				} else {
					reportClientDocument.getDatabaseController().logon(reportSetting.getReportServerSetting().getUserName(), reportSetting.getReportServerSetting().getPassword());
				}
				// set parameter
				Fields paramFields = reportClientDocument.getDataDefinition().getParameterFields();
				for (int i = 0; i < paramFields.size(); i++) {
					String paramName = ((ParameterField) paramFields.get(i)).getName();
					ParameterField oldParam = (ParameterField) paramFields.get(i);
					ParameterField tempParam = (ParameterField) oldParam.clone(true);
					ParameterFieldDiscreteValue parameterFieldDiscreteValue = new ParameterFieldDiscreteValue();
					parameterFieldDiscreteValue.setValue(inputParameters.get(paramName));
					parameterFieldDiscreteValue.setDescription(paramName);
					Values values = new Values();
					values.add(parameterFieldDiscreteValue);
					tempParam.setCurrentValues(values);
					reportClientDocument.getDataDefController().getParameterFieldController().modify(oldParam, tempParam);
				}
			} else {
				throw new ReportException("No found report file in server folder. " + reportSetting.getReportServerSetting().getCmsFolderName() + ", reportId is "
						+ reportSetting.getReportId());
			}
		} catch (Exception e) {
			throw new ReportException("Prepare export report occur exception.", e);
		} catch (Throwable t) {
			log.error("Prepare export report failed.", t);
		}
		return reportClientDocument;
	}

	private ByteArrayInputStream getReportStream() throws ReportException {
		IExportOptions option = new ExportOptions();
		ReportClientDocument reportClientDocument = null;
		ByteArrayInputStream byteIS = null;
		try {
			if (reportSetting.getExportFormat().equals(ReportExportFormat.PDF)) {
				option.setExportFormatType(ReportExportFormat.PDF);

			} else if (reportSetting.getExportFormat().equals(ReportExportFormat.MSExcel)) {
				ExcelExportFormatOptions excelOption = new ExcelExportFormatOptions();
				excelOption.setBaseAreaType(AreaSectionKind.detail);
				excelOption.setExcelTabHasColumnHeadings(true);
				excelOption.setExportPageAreaPairType(ExportPageAreaPairKind.forEachPage);//Add by yejunhua for page heaher and footer 20100120

				option.setFormatOptions(excelOption);
				option.setExportFormatType(ReportExportFormat.MSExcel);
			} else if (reportSetting.getExportFormat().equals(ReportExportFormat.text)) {
				option.setExportFormatType(ReportExportFormat.text);
			} else if (reportSetting.getExportFormat().equals(ReportExportFormat.RTF)) {
				option.setExportFormatType(ReportExportFormat.RTF);
			}
			
			reportClientDocument = prepareExportReport();
			byteIS = (ByteArrayInputStream) reportClientDocument.getPrintOutputController().export(option);

		} catch (ReportSDKExceptionBase e) {
			throw new ReportException("Get report stream occur ReportSDKExceptionBase.", e);
		} catch (Exception e) {
			throw new ReportException("Get report stream occur exception.", e);
		} catch (Throwable t) {
			log.error("Get report stream failed.", t);

		} finally {
			// close report session
			try {
				if (reportClientDocument != null) {
					reportClientDocument.close();
					// reportClientDocument.dispose();
				}
			} catch (Throwable t) {
				log.error("Can not close report session.", t);

			}
			if (reportSession != null)
				reportSession.logoff();
		}
		return byteIS;
	}

	public void exportReportToFile() throws ReportException {

		ByteArrayInputStream byteIS = null;
		byte[] buf = new byte[BUFFER_SIZE];
		int nRead = 0;
		FileOutputStream fos = null;
		log.debug("Start export report to file ......");
		try {

			byteIS = getReportStream();

			File file = new File(outputDirectory + File.separatorChar + outputFileName);

			if (file.exists()) {
				if (file.isFile()) {
					
					if (file.delete())
						log.warn("The file [FileName=" + outputDirectory + File.separatorChar + outputFileName + "] is deleted!");
				}
			} else {
				if (file.getParentFile().mkdirs())
					log.info("Create folder : "+outputDirectory);
			}

			fos = new FileOutputStream(file);

			log.debug("Write export report to file ......");
			while ((nRead = byteIS.read(buf)) != -1) {
				fos.write(buf, 0, nRead);
			}
			log.debug("Complete export report to file.");
			log.debug("Generate report file [FileName=" + outputDirectory + File.separatorChar + outputFileName + "].");
		} catch (FileNotFoundException e) {
			throw new ReportException("Can not found the report file [" + outputDirectory + File.separatorChar + outputFileName + "].", e);
		} catch (IOException e) {
			throw new ReportException("Write file occur I/O exception.", e);
		} catch (Exception e) {
			throw new ReportException("Export file failed.", e);

		} finally {
			// close file stream
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				log.error("Can not close report file stream.");
			}

			try {
				if (byteIS != null)
					byteIS.close();
			} catch (IOException e) {
				log.error("Can not close byte input stream.");
			}
		}

	}

	public void exportReportToWebView(HttpServletRequest request, HttpServletResponse response) throws ReportException {

		ByteArrayInputStream byteIS = null;
		byte[] buf = new byte[BUFFER_SIZE];
		int nRead = 0;
		try {
			log.debug("Start export report to web page ......");
			byteIS = getReportStream();

			// Set response headers to indicate pdf MIME type and inline file
			response.reset();
			response.setContentType(mime);
			response.setHeader("Content-disposition", "attachment;filename=" + outputFileName);
			log.debug("Write export report to response stream ......");
			while ((nRead = byteIS.read(buf)) != -1) {
				response.getOutputStream().write(buf, 0, nRead);
			}
			log.debug("Complete export report to web page.");

		} catch (IOException e) {
			throw new ReportException("Export web page occur I/O exception.", e);
		} catch (Exception e) {
			throw new ReportException("Export web page failed.", e);
		} finally {
			// close file stream
			try {
				if (byteIS != null)
					byteIS.close();
			} catch (IOException e) {
				log.error("Can not close report file stream.");
			}
			// close response stream
			try {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			} catch (IOException e) {
				log.error("Can not close response stream.", e);
			}
		}
	}

}