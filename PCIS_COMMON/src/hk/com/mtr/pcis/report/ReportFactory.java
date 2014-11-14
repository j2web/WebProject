package hk.com.mtr.pcis.report;

import hk.com.mtr.pcis.exception.ReportException;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;

public class ReportFactory {
	
	public static java.sql.Date utilDateToSqlDate(java.util.Date date) {
		if (date != null) {
			return new java.sql.Date(date.getTime());
		}
		return null;
	}
	
	public static java.util.Date strToUtilDate(String strDate, String pattern) {
		if (strDate != null && !strDate.equals("")) {
			SimpleDateFormat smf = new SimpleDateFormat(pattern);
			ParsePosition pos = new ParsePosition(0);
			smf.setLenient(false);
			return smf.parse(strDate, pos);
		}
		return null;
	}
	
	public static java.sql.Date strToSqlDate(String strDate, String pattern) {
		return utilDateToSqlDate(strToUtilDate(strDate, pattern));

	}
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		Map inputParas = new HashMap();
		
		inputParas.put("PC_REPORT_ID", "PCI3001");
		inputParas.put("PC_COMPANY_TYPE", "AIR");
		inputParas.put("PC_COMPANY_CODE_FROM", null);
		inputParas.put("PC_COMPANY_CODE_TO", null);
		inputParas.put("PC_USER_ID", "admin");

	        for(int i=0;i<100;i++){   
	        	GenerateReport("PCI3001", inputParas, ReportExportFormat.PDF);
	        }

	}

	static {
		ReportConfiguration.configure();
	}

	private final static Log log = LogFactory.getLog(ReportFactory.class);

	private static int counter;

	
	public static int getCounter() {
		return counter;
	}

	public static void setCounter(int counter) {
		ReportFactory.counter = counter;
	}

	public synchronized static void addCounter() {
		counter++;
	}

	public synchronized static void deductCounter() {
		counter--;

	}
	
	public synchronized static boolean checkMaxCounter() throws ReportException {	
		addCounter();
		ReportServerSetting reportServerSetting = ReportFactory.getReportServerSetting();
		int maxNo = reportServerSetting.getMaxConcurrent();
		int counter = getCounter();
		boolean result=counter <= maxNo;
		if (!result){
			log.debug(" Exceed the maximum error: The concurrent counter : "+counter+"  ");
		}else{
			log.debug(" The concurrent counter : "+counter+"   ");
		}
		return result;
	}
	
	public static ReportSetting getReportSettting(String reportId) throws ReportException{
 		return ReportConfiguration.getReportSetting(reportId);
    }
	
	public static void GenerateReport(String reportId, Map<String, Object> inputParameters, ReportExportFormat exportFormat, HttpServletRequest request,
			HttpServletResponse response) {
		GenerateReport(reportId, inputParameters, exportFormat, request, response, null, null);

	}

	public static void GenerateReport(String reportId, Map<String, Object> inputParameters, ReportExportFormat exportFormat) {
		GenerateReport(reportId, inputParameters, exportFormat, null, null, null, null);

	}

	public static void GenerateReport(String reportId, Map<String, Object> inputParameters, ReportExportFormat exportFormat, Object[] outputDirectoryArg, Object[] outputFileNameArg) {
		GenerateReport(reportId, inputParameters, exportFormat, null, null, outputDirectoryArg, outputFileNameArg);

	}

	private static void GenerateReport(String reportId, Map<String, Object> inputParameters, ReportExportFormat exportFormat, HttpServletRequest request,
			HttpServletResponse response, Object[] outputDirectoryArg, Object[] outputFileNameArg) {

		ReportSetting reportSetting = ReportConfiguration.getReportSetting(reportId);

		if (reportSetting != null) {
			reportSetting.setExportFormat(exportFormat);

			if (outputDirectoryArg != null) {
				reportSetting.setOutputDirectory(java.text.MessageFormat.format(reportSetting.getOutputDirectory(), outputDirectoryArg));
			}
			if (outputFileNameArg != null) {
				reportSetting.setOutputFileName(java.text.MessageFormat.format(reportSetting.getOutputFileName(), outputFileNameArg));
			}
			try {
				ReportCreator reportCreator = new ReportCreator(reportSetting, inputParameters);
	
				if (request == null || response == null)
					reportCreator.exportReportToFile();
				else
					reportCreator.exportReportToWebView(request, response);
				} catch (ReportException e) {
					throw e;
				} 
		} else
			log.warn("Can not find report config when reportId=" + reportId + " report.cfg.xml .");
	}

	public static Hashtable<String, ReportSetting> buildAllReportSettting() throws ReportException {
		return ReportConfiguration.buildAllReportSetting();
	}
	
	public static ReportServerSetting getReportServerSetting() throws ReportException {
		return ReportConfiguration.getReportServerSetting();
	}
}