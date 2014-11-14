/**
 * 
 */
package com.mtrc.pcis.test.sa;

import hk.com.mtr.pcis.dao.entity.sa.Role;
import hk.com.mtr.pcis.dao.entity.sa.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import javax.persistence.EntityManager;
import org.junit.Test;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.RoleCriteriaVO;
import hk.com.mtr.pcis.criteria.sa.UserCriteriaVO;
import hk.com.mtr.pcis.enums.OrderMode;
import hk.com.mtr.pcis.vo.sa.RoleVO;
import hk.com.mtr.pcis.vo.sa.UserVO;
import hk.com.mtr.pcis.dao.impl.sa.RoleDAOImpl;
import com.mtrc.pcis.test.AppBaseTestCase;

public class TestRoleDAO extends AppBaseTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		super.setUp(User.class, Role.class);
	}

	@Test
	public void testInsertRole() {
		EntityManager em = emf.createEntityManager();
		RoleDAOImpl roleDAO = new RoleDAOImpl();
		roleDAO.setEntityManager(em);

		em.getTransaction().begin();
		RoleVO roleVO = new RoleVO();
		roleVO.setRoleId("2");
		roleVO.setUpdateTime(new Timestamp(new Date().getTime()));
		roleVO.setUpdateUser("Alex");
		roleVO.setRoleName("admin");

		roleDAO.insertRole(roleVO);

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void testUpdateRoleUser() {
		EntityManager em = emf.createEntityManager();
		RoleDAOImpl roleDAO = new RoleDAOImpl();
		roleDAO.setEntityManager(em);

		em.getTransaction().begin();

		String roleId = "2";

		List<UserVO> userList = new ArrayList<UserVO>();

		UserVO userVO = new UserVO();
		userVO.setUserId("1234");

		userList.add(userVO);

		roleDAO.insertUserByRoleId(roleId, userList);

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void testUpdateRole() {
		EntityManager em = emf.createEntityManager();
		RoleDAOImpl roleDAO = new RoleDAOImpl();
		roleDAO.setEntityManager(em);

		em.getTransaction().begin();
		RoleVO roleVO = new RoleVO();
		roleVO.setRoleId("2");
		roleVO.setUpdateTime(new Timestamp(new Date().getTime()));
		roleVO.setUpdateUser("Alex");
		roleVO.setRoleName("admin");
		roleVO.setRoleDesc("changed");

		roleDAO.updateRole(roleVO);

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void testFindByPrimaryKey() {

		EntityManager em = emf.createEntityManager();
		RoleDAOImpl roleDAO = new RoleDAOImpl();
		roleDAO.setEntityManager(em);
		RoleVO roleVO = roleDAO.findByPrimaryKey("2");
		assertNotNull(roleVO);
		em.close();
	}

	@Test
	public void testFindAll() {

		RoleCriteriaVO roleCriteriaVO = new RoleCriteriaVO();
		roleCriteriaVO.setRoleName("%B%");
		List<String> sortColumn = new ArrayList<String>();
		sortColumn.add("roleName");
		List<OrderMode> order = new ArrayList<OrderMode>();
		order.add(OrderMode.ASC);

		PageInfoVO pageInfoVO = new PageInfoVO(1, 10, sortColumn, order);

		EntityManager em = emf.createEntityManager();
		RoleDAOImpl roleDAO = new RoleDAOImpl();
		roleDAO.setEntityManager(em);
		UserCriteriaVO userCriteriaVO = new UserCriteriaVO();
		userCriteriaVO.setUserName("%B%");
		List<RoleVO> list = roleDAO.findAllRoleByPage(roleCriteriaVO, pageInfoVO);
		assertNotNull(list);
		em.close();
	}

}
