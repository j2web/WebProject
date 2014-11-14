package hk.com.mtr.pcis.dao.impl.sa;

import hk.com.mtr.pcis.dao.entity.sa.Role;
import hk.com.mtr.pcis.dao.entity.sa.User;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import hk.com.mtr.pcis.criteria.sa.RoleCriteriaVO;
import hk.com.mtr.pcis.util.StringUtil;
import hk.com.mtr.pcis.vo.sa.RoleVO;
import hk.com.mtr.pcis.vo.sa.UserVO;
import hk.com.mtr.pcis.dao.AppBaseDAO;
import hk.com.mtr.pcis.dao.sa.RoleDAO;

@Stateless
public class RoleDAOImpl extends AppBaseDAO implements RoleDAO {
	@SuppressWarnings("unused")
	private final static Log log = LogFactory.getLog(RoleDAOImpl.class);

	@Override
	@SuppressWarnings("unchecked")
	public List<RoleVO> findAllRoleByPage(RoleCriteriaVO roleCriteriaVO, PageInfoVO pageInfoVO) {
		List<RoleVO> roleList = new ArrayList<RoleVO>();
		String alias = "o";

		StringBuilder sqlQueryBuilder = new StringBuilder();
		StringBuilder sqlRecordCountBuilder = new StringBuilder();
		StringBuilder sqlConditionBuilder = new StringBuilder();
		String orderSql = this.buildOrderSql(pageInfoVO, alias);
		Map<String, Object> parameterMap = new HashMap<String, Object>();

		sqlQueryBuilder.append("select o from Role o");
		sqlRecordCountBuilder.append("select count(o) from Role o ");

		if (roleCriteriaVO != null) {
			String and = "";
			String roleId = roleCriteriaVO.getRoleId();
			String roleName = roleCriteriaVO.getRoleName();
			if (StringUtil.isNotEmpty(roleId)) {
				if (StringUtil.isFuzzyQuery(roleId)) {
					sqlConditionBuilder.append(" o.roleId like :roleId");
				} else {
					sqlConditionBuilder.append(" o.roleId = :roleId");
				}
				parameterMap.put("roleId", roleId);
			}

			if (StringUtil.isNotEmpty(roleName)) {
				and = this.buildCriteriaPrefix(parameterMap);
				if (StringUtil.isFuzzyQuery(roleName)) {
					sqlConditionBuilder.append(and).append(" o.roleName like :roleName");
				} else {
					sqlConditionBuilder.append(and).append(" o.roleName = :roleName");
				}
				parameterMap.put("roleName", roleName);
			}

			if (parameterMap.size() != 0) {
				sqlQueryBuilder.append(" where ");
				sqlQueryBuilder.append(sqlConditionBuilder);

				sqlRecordCountBuilder.append(" where ");
				sqlRecordCountBuilder.append(sqlConditionBuilder);
			}
		}
		String queryCountSql = sqlRecordCountBuilder.toString();
		String querySql = sqlQueryBuilder.append(orderSql).toString();

		this.buildRecordCount(pageInfoVO, queryCountSql, parameterMap, false);

		Query query = this.createPagedQuery(pageInfoVO, querySql, parameterMap, false);
		List<Role> resultList = (List<Role>) query.getResultList();

		RoleVO roleVO = null;

		for (Role role : resultList) {
			roleVO = new RoleVO();
			this.copyProperties(role, roleVO);
			roleList.add(roleVO);
		}

		return roleList;
	}

	@Override
	public RoleVO findByPrimaryKey(String roleId) {
		RoleVO roleVO = null;
		Role role = (Role) this.entityManager.find(Role.class, roleId);
		if (role != null) {
			roleVO = new RoleVO();
			this.copyProperties(role, roleVO);
		}
		return roleVO;
	}

	@Override
	public void insertRole(RoleVO roleVO) {
		Role role = new Role();
		this.copyProperties(roleVO, role);
		this.entityManager.persist(role);
	}

	@Override
	public void updateRole(RoleVO roleVO) {
		String roleId = roleVO.getRoleId();
		Role role = (Role) this.entityManager.find(Role.class, roleId);

		if (role != null)
			this.copyProperties(roleVO, role);

	}

	@Override
	public void deleteRole(String roleId) {
		Role role = (Role) this.entityManager.find(Role.class, roleId);
		if (role != null)
			this.entityManager.remove(role);
	}

	@Override
	public List<UserVO> findAllUserByRoleId(String roleId) {		
		Role role = (Role) this.entityManager.find(Role.class, roleId);
		Set<User> resultList = role.getUserList();

		List<UserVO> userList = new ArrayList<UserVO>();		
		UserVO userVO;
		for (User user : resultList) {
			userVO = new UserVO();
			this.copyProperties(user, userVO);
			userList.add(userVO);
		}
		userComparator comp = new userComparator();
		Collections.sort(userList, comp);
		return userList;
	}

	@Override
	public void insertUserByRoleId(String roleId, List<UserVO> userList) {

		Role role = (Role) this.entityManager.find(Role.class, roleId);
		Set<User> users = new HashSet<User>();

		for (UserVO userVO : userList) {
			User user = (User) this.entityManager.find(User.class, userVO.getUserId());
			users.add(user);
		}

		role.getUserList().addAll(users);

	}

	@Override
	public void updateUserByRoleId(String roleId, List<UserVO> userList) {

		Role role = (Role) this.entityManager.find(Role.class, roleId);
		Set<User> users = new HashSet<User>();

		for (UserVO userVO : userList) {
			User user = (User) this.entityManager.find(User.class, userVO.getUserId());
			users.add(user);
		}
		Set<User> list = role.getUserList();
		list.retainAll(users);
		list.addAll(users);

	}
	
	/**
	 * compare user
	 * @author YeJunhua
	 *
	 */
	class userComparator implements Comparator<UserVO>{
		Collator strCollator = Collator.getInstance();

		@Override
		public int compare(UserVO object1, UserVO object2) {
			return strCollator.compare(object1.getUserId(),object2.getUserId());
		}
		
	}
}
