package hk.com.mtr.pcis.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import hk.com.mtr.pcis.enums.RowMode;

public class AppBaseVO implements Serializable {

	private static final long serialVersionUID = 376896887193014368L;

	private RowMode mode = RowMode.NONE;

	private boolean none;
	private boolean edit;
	private boolean add;

	private boolean selected;

	private String updateUser;

	private Timestamp updateTime;

	public boolean isNone() {
		none = mode == RowMode.NONE;
		return none;
	}

	public boolean isEdit() {
		edit = mode == RowMode.EDIT;
		return edit;
	}

	public boolean isAdd() {
		add = mode == RowMode.ADD;
		return add;
	}

	public RowMode getMode() {
		return mode;
	}

	public void setMode(RowMode mode) {
		this.mode = mode;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}
