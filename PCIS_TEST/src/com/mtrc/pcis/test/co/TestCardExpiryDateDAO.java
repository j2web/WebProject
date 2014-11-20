package com.mtrc.pcis.test.co;

import hk.com.mtr.pcis.dao.entity.co.CardExpiryDate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;

import com.mtrc.pcis.test.AppBaseTestCase;

public class TestCardExpiryDateDAO extends AppBaseTestCase {
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		super.setUp(CardExpiryDate.class);
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

	@SuppressWarnings("unchecked")
	@Test
	public void testFindByPrimaryKey() {

		try {
			EntityManager em = emf.createEntityManager();
	//		CardExpiryDateDAOImpl cardExpiryDateDAO = new CardExpiryDateDAOImpl();
	//		cardExpiryDateDAO.setEntityManager(em);
	//		CardExpiryDateVO cardExpiryDateVO = cardExpiryDateDAO.findByPrimaryKey("123");
	//		assertNotNull(cardExpiryDateVO);
			String sql = "select * from Card_Expiry_Date o order by o.co_Type  , o.form";
			Query query = em.createNativeQuery(sql, CardExpiryDate.class);
			List<CardExpiryDate> cs = query.getResultList();
			for (CardExpiryDate ced : cs) {
			}
			
			em.close();
		} catch(Exception e) {
			System.out.println(e);
		}
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
