package hk.com.mtr.pcis.report;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.framework.ISessionMgr;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.exception.SDKException;
import hk.com.mtr.pcis.exception.ReportException;
import hk.com.mtr.pcis.util.StringUtil;

public class ReportCommon {

	private final static Log log = LogFactory.getLog(ReportCommon.class);
	private IEnterpriseSession reportSession;
	private ReportServerSetting reportServerSetting;

	public ReportCommon(ReportServerSetting reportServerSetting) {
		this.reportServerSetting = reportServerSetting;
	}

	public ReportCommon() {
		super();
	}

	// find folder id
	public int getReportParentFolderId() throws ReportException {
		int ret = 0;
		try {
			ret = getReportSaveFolderId();
		} catch (ReportException e) {
			throw new ReportException("getReportParentFolderId occur error. ", e);
		} finally {
			if (reportSession != null)
				reportSession.logoff();
		}
		return ret;
	}

	// get parent folder id on BOXI server
	private int getReportSaveFolderId() throws ReportException {
		int ret = 0;
		String folderName = reportServerSetting.getCmsFolderName();
		String[] folders = folderName.split("/");
		try {
			if (folders.length == 1) {
				if (!StringUtil.isEmpty(folderName))
					ret = getFolderId(0, folderName);
				else {
					throw new ReportException("no path config");
				}
			} else {
				ret = findChildFolderId(folders);
			}
		} catch (SDKException e) {
			throw new ReportException("getParentFolderId occur error. ", e);
		}
		return ret;
	}

	// find current folder id
	private int getFolderId(int folderID, String folderName) throws SDKException, ReportException {
		StringBuffer query = new StringBuffer("SELECT SI_ID,SI_NAME FROM CI_INFOOBJECTS WHERE SI_KIND='folder'");
		if (folderID != 0)
			query.append(" and si_parentid=").append(folderID);
		if (!StringUtil.isEmpty(folderName))
			query.append(" and si_name='").append(folderName).append("'");
		IInfoObjects folders = getIInfoStore().query(query.toString());

		return folders.size() < 1 ? 0 : ((IInfoObject) folders.get(0)).getID();
	}

	// find folder id when folder have child folder
	private int findChildFolderId(String[] folders) throws SDKException, ReportException {
		int preFolderId = 0; // temp, cache folder id
		int ret = 0;
		for (int i = 0; i < folders.length; i++) {
			if (ret != 0)
				preFolderId = ret;
			ret = getFolderId(ret, folders[i]);
			if (ret == 0) {
				ret = preFolderId;
				log.error("no child folder found, folder name is: " + folders[i]);
				break;
			}
		}
		return ret;
	}

	// get IInfoStore, can be a commom function
	private IInfoStore getIInfoStore() throws ReportException {

		ISessionMgr ceSessionMgr = null;
		IInfoStore ceInfoStore = null;
		try {
			ceSessionMgr = CrystalEnterprise.getSessionMgr();
			reportSession = ceSessionMgr.logon(reportServerSetting.getCmsUserName(), reportServerSetting.getCmsPassword(), reportServerSetting.getCmsName(), reportServerSetting
					.getCmsAuthenticationType());
			ceInfoStore = (IInfoStore) reportSession.getService("InfoStore");
		} catch (SDKException e) {
			throw new ReportException("connect BO XI server occur error.", e);
		}
		return ceInfoStore;
	}
}
