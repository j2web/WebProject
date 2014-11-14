package hk.com.mtr.pcis.web.faces.action.sa.user;

import hk.com.mtr.pcis.web.faces.action.SearchBaseAction;
import org.jboss.seam.annotations.Name;
import hk.com.mtr.pcis.criteria.sa.UserCriteriaVO;

@Name("searchUserAction")
public class SearchUserAction extends SearchBaseAction {

	private UserCriteriaVO userCriteriaVO;

	public UserCriteriaVO getUserCriteriaVO() {
		return userCriteriaVO;
	}

	public void setUserCriteriaVO(UserCriteriaVO userCriteriaVO) {
		this.userCriteriaVO = userCriteriaVO;
	}

	private static final long serialVersionUID = -5625335910301104358L;

	@Override
	protected void onReset() {
		this.resetCriteriaVO(userCriteriaVO);
	}

	@Override
	protected String onSearch() {
		this.saveCriteriaVO(userCriteriaVO);
		return "/sa/user/listUser.xhtml";
	}

	@Override
	protected void onLoad() {
		userCriteriaVO = this.loadCriteriaVO(UserCriteriaVO.class);

	}

}
