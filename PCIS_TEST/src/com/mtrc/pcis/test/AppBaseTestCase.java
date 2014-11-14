package com.mtrc.pcis.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.openjpa.persistence.test.SingleEMFTestCase;

public class AppBaseTestCase extends SingleEMFTestCase {

	protected Log log = LogFactory.getLog(this.getClass());

	@Override
	protected String getPersistenceUnitName() {
		return "test";
	}

	@Override
	public void tearDown() throws Exception {

	}

}
