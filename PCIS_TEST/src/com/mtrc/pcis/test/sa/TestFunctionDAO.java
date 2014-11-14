package com.mtrc.pcis.test.sa;

import java.util.List;
import javax.persistence.EntityManager;
import org.junit.Test;
import hk.com.mtr.pcis.vo.sa.PrivilegeTreeVO;
import hk.com.mtr.pcis.dao.impl.sa.FunctionDAOImpl;
import com.mtrc.pcis.test.AppBaseTestCase;

public class TestFunctionDAO extends AppBaseTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		super.setUp(PrivilegeTreeVO.class);
	}

	@Test
	public void testFindAllFunction() {

		EntityManager em = emf.createEntityManager();
		FunctionDAOImpl functionDAO = new FunctionDAOImpl();
		functionDAO.setEntityManager(em);
		List<PrivilegeTreeVO> list = functionDAO.findAllFunction();
		assertNotNull(list);
		em.close();
	}
}