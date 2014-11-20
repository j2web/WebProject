package hk.com.mtr.pcis.facade.co;

import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.co.CompanyTypeCriteriaVO;
import hk.com.mtr.pcis.exception.BusinessException;
import hk.com.mtr.pcis.vo.co.CompanyTypeVO;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

@Local
public interface CompanyTypeFacade {
	
	public CompanyTypeVO findByPrimaryKey(String type) throws BusinessException;

	public CompanyTypeVO findByTypeAndForm(String type, Integer form) throws BusinessException;

	public List<CompanyTypeVO> findAllCompanyTypeByPage(CompanyTypeCriteriaVO companyTypeCriteriaVO, PageInfoVO pageInfoVO) throws BusinessException;

	public void insertCompanyType(CompanyTypeVO companyTypeVO) throws BusinessException;

	public void updateCompanyType(CompanyTypeVO companyTypeVO) throws BusinessException;

	public void deleteCompanyType(String type) throws BusinessException;

	public List<CompanyTypeVO> findAllCompanyType();

}
