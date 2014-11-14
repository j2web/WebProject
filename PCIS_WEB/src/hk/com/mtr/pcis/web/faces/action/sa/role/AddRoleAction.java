package hk.com.mtr.pcis.web.faces.action.sa.role;

import hk.com.mtr.pcis.facade.sa.FunctionFacade;
import hk.com.mtr.pcis.facade.sa.RoleFacade;
import hk.com.mtr.pcis.web.faces.action.AppBaseAction;
import hk.com.mtr.pcis.web.faces.util.Constant;

import java.util.ArrayList;

import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.international.StatusMessage;

import hk.com.mtr.pcis.vo.sa.PrivilegeTreeVO;
import hk.com.mtr.pcis.vo.sa.PrivilegeTreeNode;
import hk.com.mtr.pcis.vo.sa.PrivilegeTree;
import hk.com.mtr.pcis.vo.sa.PrivilegeVO;
import hk.com.mtr.pcis.vo.sa.RoleVO;
import hk.com.mtr.pcis.vo.sa.UserVO;

@Name("addRoleAction")
public class AddRoleAction extends AppBaseAction {

	private static final long serialVersionUID = -8946506709138590557L;
	private RoleVO roleVO;
	private PrivilegeTree privilegeTree;
	private List<UserVO> userList;

	@In(required = false, value = "vo")
	protected UserVO userVO;

	@Override
	protected void onLoad() {
		roleVO = new RoleVO();

		FunctionFacade functionFacade = this.getService(FunctionFacade.class);
		List<PrivilegeTreeVO> privilegeList = null;
		try {
			privilegeList = functionFacade.findAllFunction();
		} catch (Exception e) {
			this.handleException(e);
		}
		privilegeTree = new PrivilegeTree(privilegeList);

	}

	public RoleVO getRoleVO() {
		return roleVO;
	}

	public void setRoleVO(RoleVO roleVO) {
		this.roleVO = roleVO;
	}

	public PrivilegeTree getPrivilegeTree() {
		return privilegeTree;
	}

	@SuppressWarnings("unchecked")
	public List<UserVO> getUserList() {
		if (userList == null)
			userList = new ArrayList<UserVO>();
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

	public void doSave() {
		RoleFacade roleFacade = this.getService(RoleFacade.class);
		boolean exist = false;
		try {
			RoleVO tempVO = roleFacade.findByPrimaryKey(roleVO.getRoleId());
			exist = tempVO != null;
		} catch (Exception e) {
			this.handleException(e);
		}
		if (exist) {
			facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR, "msg.sa.roleMaint.roleExists", roleVO.getRoleId());
		} else {
			List<PrivilegeVO> privilegeList = new ArrayList<PrivilegeVO>();
			for (PrivilegeTreeNode rootNode : privilegeTree.getPrivilegeRootNodes()) {
				recursiveTreeNodes(rootNode, privilegeList);
			}
			try {
				roleFacade.insertRole(roleVO, privilegeList, getUserList());
				facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO, "msg.sa.roleMaint.createdSuccess", roleVO.getRoleId());
			} catch (Exception e) {
				this.handleException(e);
			}
		}
	}

	public void doDeleteUser() {
		userList.remove(userVO);
	}

	private void recursiveTreeNodes(PrivilegeTreeNode parentNode, List<PrivilegeVO> list) {
		String roleId = roleVO.getRoleId();
		for (PrivilegeTreeNode node : parentNode.getNodes()) {
			if (node.getNodes().size() == 0) {
				if (node.isSelected()) {

					PrivilegeVO vo = new PrivilegeVO();
					vo.setFunctionId(node.getId());
					vo.setRoleId(roleId);
					list.add(vo);
				}
			} else
				recursiveTreeNodes(node, list);

		}
	}

}
