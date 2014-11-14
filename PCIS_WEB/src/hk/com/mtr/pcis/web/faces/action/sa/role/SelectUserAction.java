package hk.com.mtr.pcis.web.faces.action.sa.role;

import java.util.ArrayList;
import java.util.List;

import hk.com.mtr.pcis.facade.sa.UserFacade;
import hk.com.mtr.pcis.web.faces.action.ListBaseAction;
import hk.com.mtr.pcis.web.faces.util.Constant;
import hk.com.mtr.pcis.web.faces.util.FacesUtil;

import javax.faces.event.ActionEvent;

import org.jboss.seam.annotations.Name;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.UserCriteriaVO;
import hk.com.mtr.pcis.enums.PageMode;

import hk.com.mtr.pcis.vo.sa.UserVO;

@Name("selectUserAction")
public class SelectUserAction extends ListBaseAction {

	private static final long serialVersionUID = 3551421234204180192L;
	private UserCriteriaVO userCriteriaVO;

	@Override
	protected void onLoad() {
		this.mode = PageMode.NONE;
		this.pageSize = 10;
	}

	@Override
	public List<UserVO> fetchPage(PageInfoVO pageInfoVO) throws Exception {
		pageInfoVO.getSortedExpression().clear();
		pageInfoVO.getSortedExpression().add("userId");
		UserFacade userFacade = getService(UserFacade.class);
		List<UserVO> userList = userFacade.findAllUserByPage(userCriteriaVO, pageInfoVO);

		return userList;
	}

	public void doSearch() {
		this.refresh();

	}

	@SuppressWarnings("unchecked")
	public void doSelect(ActionEvent event) {
		List<UserVO> selectedUserList = new ArrayList<UserVO>();

		List<UserVO> userList = (List<UserVO>) this.pageDataModel.pageData.getSearchResult();
		for (UserVO userVO : userList) {
			Boolean selected = userVO.isSelected();
			if (selected)
				selectedUserList.add(userVO);
			userVO.setSelected(false);

		}

		if (selectedUserList.size() > 0)
			this.saveObject(Constant.SA_ROLE_SELECTED_USER_KEY, selectedUserList);

		FacesUtil.clearComponent(event.getComponent());
		this.mode = PageMode.NONE;

	}

	public UserCriteriaVO getUserCriteriaVO() {
		if (userCriteriaVO == null)
			userCriteriaVO = new UserCriteriaVO();
		return userCriteriaVO;
	}

	public void setUserCriteriaVO(UserCriteriaVO userCriteriaVO) {
		this.userCriteriaVO = userCriteriaVO;
	}
}
