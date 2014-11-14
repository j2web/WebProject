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
import hk.com.mtr.pcis.criteria.sa.UserCriteriaVO;
import hk.com.mtr.pcis.enums.OrderMode;
import hk.com.mtr.pcis.vo.sa.RoleVO;
import hk.com.mtr.pcis.vo.sa.UserVO;
import hk.com.mtr.pcis.dao.impl.sa.UserDAOImpl;
import com.mtrc.pcis.test.AppBaseTestCase;

public class TestUserDAO extends AppBaseTestCase {
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		super.setUp(User.class,Role.class);
	}
	
//	@Test
//	public void testUpdateUserRole() {
//		EntityManager em = emf.createEntityManager();
//		UserDAOImpl userDAO = new UserDAOImpl();
//		userDAO.setEntityManager(em);
//		
//		em.getTransaction().begin();
//		
//		UserVO userVO = new UserVO();
//		userVO.setUserId("1234");
//		userVO.setUserName("Alex");
//		userVO.setUpdateTime(new Timestamp(new Date().getTime()));
//		userVO.setUpdateUser("Alex");
//		
//		List<RoleVO> roleList = new ArrayList<RoleVO>();
//		RoleVO roleVO = new RoleVO();
//		roleVO.setRoleId("2");
//		roleVO.setUpdateTime(new Timestamp(new Date().getTime()));
//		roleVO.setUpdateUser("Alex");
//		roleVO.setRoleName("admin");
//		roleVO.setRoleDesc("changed");
//		roleList.add(roleVO);
//		
//		
//		
//		userDAO.updateRole(userVO, roleList);
//		
//		em.getTransaction().commit();
//		em.close();
//
//	}
//	
//
//	@Test
//	public void testInsertUser() {
//		EntityManager em = emf.createEntityManager();
//		UserDAOImpl userDAO = new UserDAOImpl();
//		userDAO.setEntityManager(em);
//		
//		em.getTransaction().begin();
//		for(int i = 0 ; i < 10 ; i++){
//			UserVO userVO = new UserVO();
//			userVO.setUserId("1234");
//			userVO.setUserName("Alex");
//			userVO.setUpdateTime(new Timestamp(new Date().getTime()));
//			userVO.setUpdateUser("Alex");
//			
//			userDAO.insertUser(userVO);
//		}
//		
//		em.getTransaction().commit();
//		em.close();
//
//	}
//	
//	@Test
//	public void testUpdateUser() {
//		EntityManager em = emf.createEntityManager();
//		UserDAOImpl userDAO = new UserDAOImpl();
//		userDAO.setEntityManager(em);
//		
//		em.getTransaction().begin();
//		UserVO userVO = new UserVO();
//		userVO.setUserId("1234");
//		userVO.setUserName("Alex");
//		userVO.setUpdateTime(new Timestamp(new Date().getTime()));
//		userVO.setUpdateUser("Alex");
//		
//		userDAO.updateUser(userVO);
//		
//		em.getTransaction().commit();
//		em.close();
//
//	}

	@Test
	public void testFindByPrimaryKey() {

		EntityManager em = emf.createEntityManager();
		UserDAOImpl userDAO = new UserDAOImpl();
		userDAO.setEntityManager(em);
		UserVO userVO = userDAO.findByPrimaryKey("123");
		assertNotNull(userVO);
		em.close();
	}
	
//	@Test
//	public void testDeleteUser() {
//		EntityManager em = emf.createEntityManager();
//		UserDAOImpl userDAO = new UserDAOImpl();
//		userDAO.setEntityManager(em);
//		em.getTransaction().begin();
//		userDAO.deleteUser("222");
//		
//		em.getTransaction().commit();
//		em.close();
//
//	}
//	
//	@Test
//	public void testFindAll() {
//
//		UserCriteriaVO userCriteriaVO = new UserCriteriaVO();
//		//userCriteriaVO.setUserId(new Long(1));
//		List<String> sortColumn = new ArrayList<String>();
//		sortColumn.add("userId");
//		List<OrderMode> order = new ArrayList<OrderMode>();
//		order.add(OrderMode.ASC);
//		PageInfoVO pageInfoVO = new PageInfoVO(1, 10, sortColumn, order);
//		
//		EntityManager em = emf.createEntityManager();
//		UserDAOImpl userDAO = new UserDAOImpl();
//		userDAO.setEntityManager(em);
//		List<UserVO> list = userDAO.findAllUserByPage(userCriteriaVO,pageInfoVO);
//		this.log.debug(pageInfoVO.getRecordCount());
//		assertNotNull(list);
//		em.close();
//	}
//	
	

}
