package com.mtrc.pcis.test.sa;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.sa.ParameterCriteriaVO;
import hk.com.mtr.pcis.dao.entity.sa.Parameter;
import hk.com.mtr.pcis.dao.impl.sa.ParameterDAOImpl;
import hk.com.mtr.pcis.enums.OrderMode;
import hk.com.mtr.pcis.vo.sa.ParameterVO;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.mtrc.pcis.test.AppBaseTestCase;

public class TestParamDAO extends AppBaseTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();
		setUp(Parameter.class);
	}
	
	@Test
	public void testFindAllParamByPage(){
		EntityManager em = emf.createEntityManager();
		ParameterDAOImpl dao = new ParameterDAOImpl();
		dao.setEntityManager(em);
		ParameterCriteriaVO criteriaVO = null;
//		criteriaVO = new ParamCriteriaVO("PARAM1");
		List<String> sort = new ArrayList<String>();
		sort.add("paramId");
		List<OrderMode> order = new ArrayList<OrderMode>();
		order.add(OrderMode.ASC);
		PageInfoVO pageInfoVO = new PageInfoVO(1, 10, sort, order);
		List<ParameterVO> list = dao.findAllParamByPage(criteriaVO, pageInfoVO);
		System.out.println("======> " + pageInfoVO.getRecordCount());
		assertNotNull(list);
		em.close();
	}
	
	@Test
	public void testFindByPrimaryKey() {
		EntityManager em = emf.createEntityManager();
		ParameterDAOImpl dao = new ParameterDAOImpl();
		dao.setEntityManager(em);
		String paramId = "PARAM1";
		ParameterVO paramVO = dao.findByPrimaryKey(paramId);
//		System.out.println(paramVO.getParamId());
		System.out.println("testFindByPrimaryKey: " + (paramVO != null));
		em.close();
	}
	
	@Test
	public void testUpdateParam(){
		EntityManager em = emf.createEntityManager();
		ParameterDAOImpl dao = new ParameterDAOImpl();
		dao.setEntityManager(em);
		em.getTransaction().begin();
		ParameterVO paramVO = new ParameterVO();
		paramVO.setParamId("PARAM1");
		paramVO.setParamIntValue(0);
		paramVO.setParamCharValue("TEST12");
		paramVO.setDescription("TEST UPDATE11");
		paramVO.setUpdateTime(new Timestamp(new Date().getTime()));
//		paramVO.setUpdateUser("Suny");
		dao.updateParam(paramVO);
		em.getTransaction().commit();
		em.close();
	}
	
	@Test
	public void testFindAll(){
		EntityManager em = emf.createEntityManager();
		ParameterDAOImpl dao = new ParameterDAOImpl();
		dao.setEntityManager(em);
		List<ParameterVO> list = dao.findAllParam();
		System.out.println(list.size());
	}
}
