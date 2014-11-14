package hk.com.mtr.pcis.report;

import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;

public class ReportSetting implements Cloneable {
	public ReportSetting(String reportId) {
		this.reportId = reportId;
	}

	private String reportId;

	private ReportServerSetting reportServerSetting;

	private String outputFileName;

	private String outputDirectory;

	private ReportExportFormat exportFormat;

	private String reportFileName;

	public ReportExportFormat getExportFormat() {
		return exportFormat;
	}

	public void setExportFormat(ReportExportFormat exportFormat) {
		this.exportFormat = exportFormat;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportFileName() {
		return reportFileName;
	}

	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}

	public ReportServerSetting getReportServerSetting() {
		return reportServerSetting;
	}

	public void setReportServerSetting(ReportServerSetting reportServerSetting) {
		this.reportServerSetting = reportServerSetting;
	}

	public Object clone() {
		ReportSetting o = null;
		try {
			o = (ReportSetting) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
}
