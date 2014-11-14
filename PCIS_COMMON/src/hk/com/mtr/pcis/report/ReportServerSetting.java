package hk.com.mtr.pcis.report;

public class ReportServerSetting {

	private String dsn;
	
	private String databaseName;

	private String userName;

	private String password;

	private boolean changeTableLocation;

	private String cmsUserName;

	private String cmsPassword;

	private String cmsName;

	private String cmsAuthenticationType;

	private int cmsFolderId;

	private String cmsFolderName;
	
	private int maxConcurrent;

	public String getDsn() {
		return dsn;
	}

	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isChangeTableLocation() {
		return changeTableLocation;
	}

	public void setChangeTableLocation(boolean changeTableLocation) {
		this.changeTableLocation = changeTableLocation;
	}

	public String getCmsUserName() {
		return cmsUserName;
	}

	public void setCmsUserName(String cmsUserName) {
		this.cmsUserName = cmsUserName;
	}

	public String getCmsPassword() {
		return cmsPassword;
	}

	public void setCmsPassword(String cmsPassword) {
		this.cmsPassword = cmsPassword;
	}

	public String getCmsName() {
		return cmsName;
	}

	public void setCmsName(String cmsName) {
		this.cmsName = cmsName;
	}

	public String getCmsAuthenticationType() {
		return cmsAuthenticationType;
	}

	public void setCmsAuthenticationType(String cmsAuthenticationType) {
		this.cmsAuthenticationType = cmsAuthenticationType;
	}

	public int getCmsFolderId() {
		return cmsFolderId;
	}

	public void setCmsFolderId(int cmsFolderId) {
		this.cmsFolderId = cmsFolderId;
	}

	public int getMaxConcurrent() {
		return maxConcurrent;
	}

	public void setMaxConcurrent(int maxConcurrent) {
		this.maxConcurrent = maxConcurrent;
	}

	public String getCmsFolderName() {
		return cmsFolderName;
	}

	public void setCmsFolderName(String cmsFolderName) {
		this.cmsFolderName = cmsFolderName;
	}

}
