package hk.com.mtr.pcis.dao.impl.sa;

import hk.com.mtr.pcis.dao.entity.sa.Role;
import hk.com.mtr.pcis.dao.entity.sa.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.UserCriteriaVO;
import hk.com.mtr.pcis.util.StringUtil;
import hk.com.mtr.pcis.vo.sa.RoleVO;
import hk.com.mtr.pcis.vo.sa.UserVO;
import hk.com.mtr.pcis.dao.AppBaseDAO;
import hk.com.mtr.pcis.dao.sa.UserDAO;

@Stateless
public class UserDAOImpl extends AppBaseDAO implements UserDAO {
	@SuppressWarnings("unused")
	private final static Log log = LogFactory.getLog(UserDAOImpl.class);

	@Override
	@SuppressWarnings("unchecked")
	public List<UserVO> findAllUserByPage(UserCriteriaVO userCriteriaVO, PageInfoVO pageInfoVO) {
		List<UserVO> userList = new ArrayList<UserVO>();

		String alias = "o";

		StringBuilder sqlQueryBuilder = new StringBuilder();
		StringBuilder sqlRecordCountBuilder = new StringBuilder();
		StringBuilder sqlConditionBuilder = new StringBuilder();
		String orderSql = this.buildOrderSql(pageInfoVO, alias);
		Map<String, Object> parameterMap = new HashMap<String, Object>();

		sqlQueryBuilder.append("select o from User o");
		sqlRecordCountBuilder.append("select count(o) from User o ");
		if (userCriteriaVO != null) {
			// build where sql
			String userId = userCriteriaVO.getUserId();
			String userName = userCriteriaVO.getUserName();
			String and = "";

			if (StringUtil.isNotEmpty(userId)) {
				if (StringUtil.isFuzzyQuery(userId))
					sqlConditionBuilder.append("o.userId like :userId");
				else
					sqlConditionBuilder.append("o.userId=:userId");
				parameterMap.put("userId", userId);
			}
			if (StringUtil.isNotEmpty(userName)) {
				and = this.buildCriteriaPrefix(parameterMap);
				if (StringUtil.isFuzzyQuery(userName))
					sqlConditionBuilder.append(and).append("o.userName like :userName");
				else
					sqlConditionBuilder.append(and).append("o.userName=:userName");
				parameterMap.put("userName", userName);
			}

			if (parameterMap.size() != 0) {
				sqlQueryBuilder.append(" WHERE ");
				sqlQueryBuilder.append(sqlConditionBuilder);

				sqlRecordCountBuilder.append(" WHERE ");
				sqlRecordCountBuilder.append(sqlConditionBuilder);

			}
		}
		String queryCountSql = sqlRecordCountBuilder.toString();
		String querySql = sqlQueryBuilder.append(orderSql).toString();

		this.buildRecordCount(pageInfoVO, queryCountSql, parameterMap);

		Query query = this.createPagedQuery(pageInfoVO, querySql, parameterMap);
		List<User> resultList = (List<User>) query.getResultList();

		UserVO userVO = null;

		for (User user : resultList) {
			userVO = new UserVO();
			this.copyProperties(user, userVO);
			if(user.getStation() != null){
				userVO.setStationCode((String)user.getStation().getStationCode());
			}
			userList.add(userVO);
		}
		return userList;

	}

	@Override
	public UserVO findByPrimaryKey(String userId) {
		UserVO userVO = null;
		User user = this.entityManager.find(User.class, userId);
		if (user != null) {
			userVO = new UserVO();
			this.copyProperties(user, userVO);
		}
		return userVO;
	}

	@Override
	public void insertUser(UserVO userVO) {
		User user = new User();
		this.copyProperties(userVO, user);
		this.entityManager.persist(user);
	}

	@Override
	public void updateUser(UserVO userVO) {
		User user = new User();
		this.copyProperties(userVO, user);
		this.entityManager.merge(user);
	}

	@Override
	public void updateRole(UserVO userVO, List<RoleVO> roleList) {
		User user = null;
		if (userVO != null) {
			user = (User) this.entityManager.find(User.class, userVO.getUserId());

			Set<Role> roles = new HashSet<Role>();
			for (int i = 0; i < roleList.size(); i++) {
				RoleVO roleVO = (RoleVO) roleList.get(i);
				Role role = (Role) this.entityManager.find(Role.class, roleVO.getRoleId());
				roles.add(role);
			}
			user.getRoleList().retainAll(roles);
			user.getRoleList().addAll(roles);

		}

	}

	@Override
	public void deleteUser(String userId) {
		User user = this.entityManager.find(User.class, userId);
		if (user != null)
			this.entityManager.remove(user);
	}
}
