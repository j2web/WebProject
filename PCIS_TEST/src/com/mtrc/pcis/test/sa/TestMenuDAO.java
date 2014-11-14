package com.mtrc.pcis.test.sa;

import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Test;
import hk.com.mtr.pcis.vo.sa.MenuVO;
import hk.com.mtr.pcis.dao.impl.sa.MenuDAOImp;
import com.mtrc.pcis.test.AppBaseTestCase;

public class TestMenuDAO extends AppBaseTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		super.setUp(MenuVO.class);
	}

	@Test
	public void testFindAll() {

		EntityManager em = emf.createEntityManager();
		MenuDAOImp menuDAO = new MenuDAOImp();
		menuDAO.setEntityManager(em);
		List<MenuVO> list = menuDAO.findMenuByUserId("1234");
		assertNotNull(list);
		em.close();
	}
}
