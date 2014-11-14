package hk.com.mtr.pcis.vo.sa;

import java.util.ArrayList;
import java.util.List;

import hk.com.mtr.pcis.vo.AppBaseVO;

public class PrivilegeTreeNode extends AppBaseVO {

	private static final long serialVersionUID = -8531470630424403204L;
	private String id;
	private String name = null;

	private List<PrivilegeTreeNode> children;
	private boolean selected = false;

	private PrivilegeTreeNode parentNode;

	public List<PrivilegeTreeNode> getNodes() {
		if (children == null)
			children = new ArrayList<PrivilegeTreeNode>();
		return children;

	}

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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {

		this.selected = selected;
		if (selected) {
			PrivilegeTreeNode node = this.getParentNode();
			while (node != null) {
				node.setSelected(selected);
				node = node.getParentNode();
			}
		}
	}

	public PrivilegeTreeNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(PrivilegeTreeNode parentNode) {
		this.parentNode = parentNode;
	}
}
