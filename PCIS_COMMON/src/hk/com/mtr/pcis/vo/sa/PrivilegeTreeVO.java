package hk.com.mtr.pcis.vo.sa;

import hk.com.mtr.pcis.util.StringUtil;
import hk.com.mtr.pcis.vo.AppBaseVO;

public class PrivilegeTreeVO extends AppBaseVO {

	private static final long serialVersionUID = 5676299890440839197L;

	private String id;
	private String parentMenuId;
	private String name;
	private boolean checked;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return this.checked;
	}

	public void setChecked(String checked) {
		if (StringUtil.isNotEmpty(checked))
			this.checked = true;
		else
			this.checked = false;
	}

	public String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

}
