package hk.com.mtr.pcis.report;

import com.crystaldecisions.sdk.occa.report.data.*;
import com.crystaldecisions.sdk.occa.report.lib.*;
import hk.com.mtr.pcis.exception.ReportException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TableLocation {

	@SuppressWarnings("unused")
	private final static Log log = LogFactory.getLog(TableLocation.class);

	@SuppressWarnings("unchecked")
	public static ConnectionInfo getConnectionInfo(Table table, ReportServerSetting reportServerSetting) {
		ConnectionInfo ci = null;
		if (table != null && reportServerSetting != null) {
			String userName = reportServerSetting.getUserName();
			String password = reportServerSetting.getPassword();
			String dbName = reportServerSetting.getDatabaseName();
			String dsn = reportServerSetting.getDsn();

			ci = (ConnectionInfo) ((Table) table.clone(true)).getConnectionInfo();
			PropertyBag pb = ci.getAttributes();

			// 1. Set the DSN
			// 2. Set the Data Source (Database)
			PropertyBag logonPb = new PropertyBag();

			logonPb.put("DSN", dsn);
			logonPb.put("Data Source", dbName);

			// 1. Pass the Logon Properties to the main PropertyBag
			// 2. Set the Server Description tpo the new **System DSN**
			pb.put(PropertyBagHelper.CONNINFO_CRQE_LOGONPROPERTIES, logonPb);
			pb.put(PropertyBagHelper.CONNINFO_CRQE_SERVERDESCRIPTION, dsn);

			// Set the connectionInfo attribute to the PropertyBag
			ci.setAttributes(pb);
			// Set Username and Password for this connection
			ci.setUserName(userName);
			ci.setPassword(password);
			// Set the Kind of connection. CRQE (Crystal Reports Query Engine)
			// type
			ci.setKind(ConnectionInfoKind.CRQE);
		}
		return ci;
	}

	public static void changeTableLocation(com.crystaldecisions.sdk.occa.report.application.ReportClientDocument clientDoc, ReportServerSetting reportServerSetting)
			throws ReportException {

		String dbName = reportServerSetting.getDatabaseName();

		try {
			// Retrieve the first table in main report or sub report by cloning
			// the existing table
			IStrings subReports = clientDoc.getSubreportController().querySubreportNames();
			ConnectionInfo ci = null;
			String subReportName = null;
			Table table = null;
			Tables tables = null;

			if (clientDoc.getDatabaseController().getDatabase().getTables().size() == 0) {
				if (subReports.size() != 0) {
					subReportName = subReports.getString(0);
					// Get all of the tables on the specified subReport by index
					tables = clientDoc.getSubreportController().getSubreportDatabase(subReportName).getTables();
					if (tables.size() != 0) {
						table = (Table) ((Table) tables.getTable(0)).clone(true);
					}
				}
			} else {
				table = (Table) clientDoc.getDatabaseController().getDatabase().getTables().getTable(0);
			}
			ci = getConnectionInfo(table, reportServerSetting);
			if (ci != null) {
				// Main Reports
				// ===================================================
				tables = clientDoc.getDatabaseController().getDatabase().getTables();
				for (int i = 0; i < tables.size(); i++)
					try {
						setTables(tables.getTable(i), ci, dbName, clientDoc);
					} catch (ReportSDKException rSDKe) {
						throw new ReportException("Change tableLocation failed:", rSDKe);
					}
				// Sub Reports
				// ===================================================
				for (int i = 0; i < subReports.size(); i++) {
					subReportName = subReports.getString(i);
					tables = clientDoc.getSubreportController().getSubreportDatabase(subReportName).getTables();
					for (int j = 0; j < tables.size(); j++)
						try {
							setSubreportTables(tables.getTable(j), ci, dbName, clientDoc, subReportName);
						} catch (ReportSDKException rSDKe) {
							throw new ReportException("can not change sub report table", rSDKe);
						}
				}
			}

		} catch (ReportSDKException sdkex) {
			throw new ReportException("Change tableLocation failed:", sdkex);
		} catch (Exception ex) {
			throw new ReportException("Change tableLocation failed:", ex);
		}

	}

	public static void setTables(com.crystaldecisions.sdk.occa.report.data.ITable table, com.crystaldecisions.sdk.occa.report.data.ConnectionInfo ci, String dataSource,
			com.crystaldecisions.sdk.occa.report.application.ReportClientDocument clientDoc) throws ReportSDKException {

		ITable newTable = new Table();
		// Set table information for new table to current tables
		newTable.setName(table.getName());
		newTable.setAlias(table.getAlias());
		// set the qualified name for the table ( Database.Owner.Table )

		newTable.setQualifiedName(table.getQualifiedName());
		newTable.setConnectionInfo(ci);

		// Set the table location to the new table
		clientDoc.getDatabaseController().setTableLocation(table, newTable);
	}

	public static void setSubreportTables(com.crystaldecisions.sdk.occa.report.data.ITable table, com.crystaldecisions.sdk.occa.report.data.ConnectionInfo ci, String dataSource,
			com.crystaldecisions.sdk.occa.report.application.ReportClientDocument clientDoc, String subreportName) throws ReportSDKException {

		ITable newTable = new Table();
		// Set table information for new table to current tables
		newTable.setName(table.getName());
		newTable.setAlias(table.getAlias());
		// set the qualified name for the table ( Database.Owner.Table )
		// newTable.setQualifiedName(dataSource + ".dbo." + table.getName());
		newTable.setQualifiedName(table.getQualifiedName());
		// set the tables connection info
		newTable.setConnectionInfo(ci);
		// Set the table location to the new table
		clientDoc.getSubreportController().setTableLocation(subreportName, table, newTable);
	}

}
