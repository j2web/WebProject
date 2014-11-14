package hk.com.mtr.pcis.vo.sa;

import hk.com.mtr.pcis.vo.AppBaseVO;

public class MenuVO extends AppBaseVO {

	private static final long serialVersionUID = -4311713476869971480L;

	private java.lang.String menuId;
	private java.lang.String parentMenuId;

	private java.lang.String resourceKey;
	private java.lang.String location;
	private java.lang.Integer displaySeq;

	public java.lang.String getMenuId() {
		return menuId;
	}

	public void setMenuId(java.lang.String menuId) {
		this.menuId = menuId;
	}

	public java.lang.String getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(java.lang.String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	public java.lang.String getResourceKey() {
		return resourceKey;
	}

	public void setResourceKey(java.lang.String resourceKey) {
		this.resourceKey = resourceKey;
	}

	public java.lang.String getLocation() {
		return location;
	}

	public void setLocation(java.lang.String location) {
		this.location = location;
	}

	public java.lang.Integer getDisplaySeq() {
		return displaySeq;
	}

	public void setDisplaySeq(java.lang.Integer displaySeq) {
		this.displaySeq = displaySeq;
	}
}
