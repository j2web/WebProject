package hk.com.mtr.pcis.dao.co;


import hk.com.mtr.pcis.criteria.PageInfoVO;
import hk.com.mtr.pcis.criteria.co.CompanyTypeCriteriaVO;
import hk.com.mtr.pcis.vo.co.CompanyTypeVO;
import hk.com.mtr.pcis.vo.sa.RoleVO;

import java.util.List;

import javax.ejb.Local;


@Local
public interface CompanyTypeDAO {
	public CompanyTypeVO findByPrimaryKey(String type);
	public CompanyTypeVO findByCompanyTypeAndForm(String companyType, Integer form);
	public void insertCompanyType(CompanyTypeVO companyTypeVO);
	public void updateCompanyType(CompanyTypeVO companyTypeVO);
	public void deleteCompanyType(String type);
	public void updateRole(CompanyTypeVO companyTypeVO , List<RoleVO> roleList);
	public List<CompanyTypeVO> findAllCompanyTypeByPage(CompanyTypeCriteriaVO CompanyTypeCriteriaVO, PageInfoVO pageInfoVO);
	public List<CompanyTypeVO> findAllCompanyType();

}
