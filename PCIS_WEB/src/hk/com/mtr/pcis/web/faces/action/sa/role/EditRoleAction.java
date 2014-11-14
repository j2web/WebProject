package hk.com.mtr.pcis.web.faces.action.sa.role;

import hk.com.mtr.pcis.facade.sa.PrivilegeFacade;
import hk.com.mtr.pcis.facade.sa.RoleFacade;
import hk.com.mtr.pcis.web.faces.action.AppBaseAction;
import hk.com.mtr.pcis.web.faces.util.Constant;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.international.StatusMessage;

import hk.com.mtr.pcis.util.StringUtil;
import hk.com.mtr.pcis.vo.sa.PrivilegeTreeVO;
import hk.com.mtr.pcis.vo.sa.PrivilegeTreeNode;
import hk.com.mtr.pcis.vo.sa.PrivilegeTree;
import hk.com.mtr.pcis.vo.sa.PrivilegeVO;
import hk.com.mtr.pcis.vo.sa.RoleVO;
import hk.com.mtr.pcis.vo.sa.UserVO;

@Name("editRoleAction")
public class EditRoleAction extends AppBaseAction {

	private static final long serialVersionUID = -8946506709138590557L;
	private RoleVO roleVO;
	private List<UserVO> userList;
	private PrivilegeTree privilegeTree;
	@RequestParameter("roleId")
	private String roleId;
	@In(required = false, value = "vo")
	protected UserVO userVO;

	@Override
	protected void onLoad() {
		if (StringUtil.isNotEmpty(roleId)) {
			RoleFacade roleFacade = this.getService(RoleFacade.class);
			try {
				roleVO = roleFacade.findByPrimaryKey(roleId);
			} catch (Exception e) {
				this.handleException(e);
			}
			PrivilegeFacade privilegeFacade = this.getService(PrivilegeFacade.class);
			List<PrivilegeTreeVO> privilegeList = null;
			try {
				privilegeList = privilegeFacade.findAllPrivilegeByRoleId(roleId);
			} catch (Exception e) {
				this.handleException(e);
			}
			privilegeTree = new PrivilegeTree(privilegeList);
			try {
				userList = roleFacade.findAllUserByRoleId(roleId);
			} catch (Exception e) {
				this.handleException(e);
			}
		}

	}

	public RoleVO getRoleVO() {
		return roleVO;
	}

	public void setRoleVO(RoleVO roleVO) {
		this.roleVO = roleVO;
	}

	@SuppressWarnings("unchecked")
	public List<UserVO> getUserList() {

		List<UserVO> selectedUserList = (List<UserVO>) this.getObject(Constant.SA_ROLE_SELECTED_USER_KEY);
		if (selectedUserList != null) {
			boolean include = false;
			for (UserVO userVO : selectedUserList) {
				for (UserVO userVO1 : userList) {
					include = userVO.getUserId().equals(userVO1.getUserId());
					if (include)
						break;
				}
				if (!include)
					userList.add(userVO);
			}
			this.removeObject(Constant.SA_ROLE_SELECTED_USER_KEY);
		}

		return userList;
	}

	public PrivilegeTree getPrivilegeTree() {
		return privilegeTree;
	}

	public void doUpdate() {
		RoleFacade roleFacade = this.getService(RoleFacade.class);
		List<PrivilegeVO> privilegeList = new ArrayList<PrivilegeVO>();
		for (PrivilegeTreeNode rootNode : privilegeTree.getPrivilegeRootNodes()) {
			recursiveTreeNodes(rootNode, privilegeList);
		}
		try {
			roleFacade.updateRole(roleVO, privilegeList, userList);
			facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO, "msg.sa.roleMaint.updatedSuccess", roleVO.getRoleId());
		} catch (Exception e) {
			this.handleException(e);
		}
	}

	public void doDeleteUser() {
		userList.remove(userVO);
	}

	private void recursiveTreeNodes(PrivilegeTreeNode parentNode, List<PrivilegeVO> list) {
		for (PrivilegeTreeNode node : parentNode.getNodes()) {
			if (node.getNodes().size() == 0) {
				if (node.isSelected()) {

					PrivilegeVO vo = new PrivilegeVO();
					vo.setFunctionId(node.getId());
					vo.setRoleId(roleVO.getRoleId());
					list.add(vo);
				}
			} else
				recursiveTreeNodes(node, list);

		}
	}

}
