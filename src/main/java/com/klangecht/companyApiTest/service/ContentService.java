package com.klangecht.companyApiTest.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klangecht.companyApiTestDAO.CompanyContent;
import com.klangecht.companyApiTestDAO.CompanyContentDAO;
import com.klangecht.companyApiTestDAO.OwnerContent;
import com.klangecht.companyApiTestDAO.OwnerContentDAO;

@Service("contentService")
public class ContentService {

	private static final Logger logger = LoggerFactory.getLogger(ContentService.class);

	private CompanyContentDAO companyContent;
	private OwnerContentDAO ownerContent;

	@Autowired
	public void setCompanyContentDAO(CompanyContentDAO companyContent) {
		this.companyContent = companyContent;
	}

	@Autowired
	public void setOwnerContentDAO(OwnerContentDAO ownerContent) {
		this.ownerContent = ownerContent;
	}

	public List<CompanyContent> getAllCompanies() {
		return companyContent.getAllCompanies();
	}
	
	public List<CompanyContent> getCompanyList() {
		return companyContent.getCompanyList();
	}

	public List<CompanyContent> getCompanyDetails(String name) {
		return companyContent.getCompanyDetails(name);
	}

	public boolean createCompany(CompanyContent newCompanyContent) {
		return companyContent.writeCompanyContent(newCompanyContent);
	}
	
	public boolean updateCompany(CompanyContent newCompanyContent) {
		return companyContent.updateCompanyContent(newCompanyContent);
	}

	public boolean createOwner(OwnerContent owner) {
		return ownerContent.writeOwner(owner);
	}
	public List<OwnerContent> getOwnersByCompany(String companyName) {
		return ownerContent.getOwnersByCompany(companyName);
	}
}
