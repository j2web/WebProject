package hk.com.mtr.pcis.vo.sa;

import java.util.ArrayList;
import java.util.List;

import hk.com.mtr.pcis.util.StringUtil;
import hk.com.mtr.pcis.vo.AppBaseVO;

public class PrivilegeTree extends AppBaseVO {

	private static final long serialVersionUID = 506932807070738496L;
	private List<PrivilegeTreeVO> privilegeVOList;

	public PrivilegeTree(List<PrivilegeTreeVO> menuFunctionAccessList) {
		this.privilegeVOList = menuFunctionAccessList;
	}

	private List<PrivilegeTreeNode> privilegeRootNodes = null;

	public List<PrivilegeTreeNode> getPrivilegeRootNodes() {
		if (privilegeRootNodes == null) {
			PrivilegeTreeNode node = null;
			privilegeRootNodes = new ArrayList<PrivilegeTreeNode>();
			for (PrivilegeTreeVO vo : privilegeVOList) {
				if (StringUtil.isEmpty(vo.getParentMenuId())) {
					node = new PrivilegeTreeNode();
					node.setId(vo.getId());
					node.setName(vo.getName());
					node.setSelected(vo.isChecked());

					privilegeRootNodes.add(node);

					buildTreeNode(node);
				}

			}

		}

		return privilegeRootNodes;
	}

	private void buildTreeNode(PrivilegeTreeNode parentNode) {
		PrivilegeTreeNode node = null;
		for (PrivilegeTreeVO vo : privilegeVOList) {
			if (StringUtil.isNotEmpty(vo.getParentMenuId()) && vo.getParentMenuId().equalsIgnoreCase(parentNode.getId())) {

				node = new PrivilegeTreeNode();
				node.setParentNode(parentNode);
				node.setId(vo.getId());
				node.setName(vo.getName());
				node.setSelected(vo.isChecked());

				parentNode.getNodes().add(node);
				buildTreeNode(node);

			}

		}
	}

}
