package com.mtrc.pcis.test.sa;

import hk.com.mtr.pcis.dao.entity.sa.Privilege;

import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Test;

import hk.com.mtr.pcis.vo.sa.PrivilegeTreeVO;

import hk.com.mtr.pcis.dao.impl.sa.PrivilegeDAOImpl;
import com.mtrc.pcis.test.AppBaseTestCase;

public class TestPrivilegeDAO extends AppBaseTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		super.setUp(Privilege.class, Privilege.PrivilegeId.class);
	}

	@Test
	public void testFindAll() {

		EntityManager em = emf.createEntityManager();
		PrivilegeDAOImpl privilegeDAOImpl = new PrivilegeDAOImpl();
		privilegeDAOImpl.setEntityManager(em);

		List<PrivilegeTreeVO> list = privilegeDAOImpl.findAllPrivilegeByRoleId("3");

		for (PrivilegeTreeVO vo : list) {
			log.debug("id is " + vo.getId() + " ,name is " + vo.getName() + " ,parentMenuId is " + vo.getParentMenuId() + " ,selected is " + vo.isSelected());
		}
		em.close();
	}

	@Test
	public void testGetPrivilegeByUserIdAndUrl() {

		EntityManager em = emf.createEntityManager();
		PrivilegeDAOImpl privilegeDAOImpl = new PrivilegeDAOImpl();
		privilegeDAOImpl.setEntityManager(em);

		List<String> list = privilegeDAOImpl.getPrivilegeByUserIdAndUrl("1234", "/sa/user/listUser.jsf");
		for(String functionId:list){
			log.info(functionId);
		}
assertNotEquals(0, list.size());
		em.close();
	}

//	@Test
//	public void testDeletel() {
//
//		EntityManager em = emf.createEntityManager();
//		PrivilegeDAOImpl privilegeDAOImpl = new PrivilegeDAOImpl();
//		privilegeDAOImpl.setEntityManager(em);
//
//		privilegeDAOImpl.deletePrivilegeByRoleId("10");
//
//		em.close();
//	}

}
