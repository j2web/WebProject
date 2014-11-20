package hk.com.mtr.pcis.facade.co;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.co.CompanyTypeCriteriaVO;
import hk.com.mtr.pcis.dao.co.CompanyTypeDAO;
import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.co.CompanyTypeVO;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class CompanyTypeFacadeBean implements CompanyTypeFacade {
	
	@EJB
	CompanyTypeDAO companyTypeDAO;

	@Override
	public void deleteCompanyType(String type) throws BusinessException {
		this.companyTypeDAO.deleteCompanyType(type);
	}

	@Override
	public List<CompanyTypeVO> findAllCompanyTypeByPage(
			CompanyTypeCriteriaVO companyTypeCriteriaVO, PageInfoVO pageInfoVO)
			throws BusinessException {
		List<CompanyTypeVO> list = null;

		try {
			list = companyTypeDAO.findAllCompanyTypeByPage(companyTypeCriteriaVO, pageInfoVO);
		} catch (Exception e) {
			throw new BusinessException("Find Company Type by criteria failed:", e);
		}
		return list;
	}

	@Override
	public CompanyTypeVO findByPrimaryKey(String type)
			throws BusinessException {
		
		return this.companyTypeDAO.findByPrimaryKey(type);
	}

	@Override
	public void insertCompanyType(CompanyTypeVO companyTypeVO)
			throws BusinessException {
		this.companyTypeDAO.insertCompanyType(companyTypeVO);
	}

	@Override
	public void updateCompanyType(CompanyTypeVO companyTypeVO)
			throws BusinessException {
		this.companyTypeDAO.updateCompanyType(companyTypeVO);
	}

	public CompanyTypeDAO getCompanyTypeDAO() {
		return companyTypeDAO;
	}

	public void setCompanyTypeDAO(CompanyTypeDAO companyTypeDAO) {
		this.companyTypeDAO = companyTypeDAO;
	}

	@Override
	public CompanyTypeVO findByTypeAndForm(String type,
			Integer form) throws BusinessException {
		
		return this.companyTypeDAO.findByCompanyTypeAndForm(type, form);
	}

	@Override
	public List<CompanyTypeVO> findAllCompanyType() {
		return this.companyTypeDAO.findAllCompanyType();
	}
	

	
}
