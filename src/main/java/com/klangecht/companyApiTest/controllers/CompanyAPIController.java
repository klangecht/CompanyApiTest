package com.klangecht.companyApiTest.controllers;

import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.klangecht.companyApiTest.service.ContentService;
import com.klangecht.companyApiTestDAO.CompanyContent;
import com.klangecht.companyApiTestDAO.OwnerContent;

@Controller
public class CompanyAPIController {

	//private static final Logger logger = LoggerFactory.getLogger(CompanyAPIController.class);
	
	private ContentService contentService;

	@Autowired
	public void setContentService(ContentService contentService) {

		this.contentService = contentService;
	}

	/*
	 * get complete json of all companies or a specific company
	 */
	@RequestMapping(value = "/api/company", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String company(
			@RequestParam(value = "companyName", defaultValue = "") String name) {

		List<CompanyContent> companies;

		if (name.isEmpty()) {
			companies = contentService.getAllCompanies();
			// get owners for each company
		} else {
			companies = contentService.getCompanyDetails(name);
		}
		companies = addOwnersToCompany(companies);
		return new Gson().toJson(companies);
	}

	/*
	 * get short json of all companies
	 */
	@RequestMapping(value = "/api/company/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String companyList() {
		List<CompanyContent> companies = contentService.getCompanyList();
		return new Gson().toJson(companies);
	}

	// Create new company
	@RequestMapping(value = "/api/company/new")
	@ResponseBody
	public String newCompany(@RequestParam(value = "name") String name,
			@RequestParam(value = "address") String address,
			@RequestParam(value = "city") String city,
			@RequestParam(value = "country") String country,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "phone", defaultValue = "") String phone,
			@RequestParam(value = "owners") String[] owners,
			@RequestParam(value = "content") String content) {

		// verify if company exists
		List<CompanyContent> companies = contentService.getCompanyDetails(name);
		if (!companies.isEmpty()) {
			return "Company already exists!";
		}

		CompanyContent newCompany = new CompanyContent(name, address, city,
				country, email, phone, content);

		long id = 0;
		// create company and get newly created company-id
		if (contentService.createCompany(newCompany)) {
			List<CompanyContent> myNewCompany = contentService
					.getCompanyDetails(name);
			id = myNewCompany.get(0).getId();
		} else {
			return "Error: Company could not be added to the Database";
		}
		// store owners with newly created company id
		// owner is stored in owner table
		for (String owner : owners)
			contentService.createOwner(new OwnerContent(owner, name, id));

		// get owners for Json output
		// TBD Database access not necessary
		List<OwnerContent> ownerList = contentService.getOwnersByCompany(name);
		String[] ow = new String[ownerList.size()];
		for (int i = 0; i < ownerList.size(); i++)
			ow[i] = ownerList.get(i).getName();

		newCompany.setId(id);

		return new Gson().toJson(newCompany);
	}

	/*
	 * update a company with one or more parameters, identify company by name
	 */
	@RequestMapping(value = "/api/company/update")
	@ResponseBody
	public String updateCompany(@RequestParam(value = "name") String name,
			@RequestParam(value = "newName", required = false) String newName,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "country", required = false) String country,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "owners", required = false) String[] owners,
			@RequestParam(value = "content", required = false) String content) {

		// get id
		if (contentService.getCompanyDetails(name).isEmpty()) {
			return "Company does not exist!";
		}
		// work with List, not necessary but easier
		List<CompanyContent> companies = contentService.getCompanyDetails(name);
		CompanyContent companyToModify = companies.get(0);

		if (newName != null) {
			companyToModify.setName(newName);
		}
		if (address != null) {
			companyToModify.setAddress(address);
		}
		if (city != null) {
			companyToModify.setCity(city);
		}
		if (country != null) {
			companyToModify.setCountry(country);
		}
		if (email != null) {
			companyToModify.setEmail(email);
		}
		if (phone != null) {
			companyToModify.setPhone(phone);
		}
		if (content != null) {
			companyToModify.setContent(content);
		}
		if (contentService.updateCompany(companyToModify)) {
			return company(name);
		} else {
			return "Error: Company could not be modified in the Database";
		}
	}

	/*
	 * add owners to company using string array owners
	 */
	@RequestMapping(value = "/api/company/owners/add", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String addOwner(
			@RequestParam(value = "companyName") String companyName,
			@RequestParam(value = "owners", required = false) String[] owners) {

		List<CompanyContent> companies = contentService
				.getCompanyDetails(companyName);
		List<OwnerContent> currentOwners = contentService
				.getOwnersByCompany(companyName);
		if (owners != null) {
			for (String newOwner : owners) {
				if (currentOwners.isEmpty()) {
					contentService.createOwner(new OwnerContent(newOwner,
							companyName, companies.get(0).getId()));
				} else {
					// have to verify in currentOwner list, if owner currently
					// exists
					Boolean ownerExists = false;
					for (OwnerContent currentOwner : currentOwners) {
						if ((currentOwner.getName().equals(newOwner))) {
							ownerExists = true;
							break;
						}
					}
					if (!ownerExists) {
						contentService.createOwner(new OwnerContent(newOwner,
								companyName, companies.get(0).getId()));
					}
				}
			}
			companies = addOwnersToCompany(companies);
		}
		return new Gson().toJson(companies);
	}

	/*
	 * get owners and add them to company object
	 */
	private List<CompanyContent> addOwnersToCompany(
			List<CompanyContent> companies) {
		for (CompanyContent cc : companies) {
			List<OwnerContent> ownerList = contentService.getOwnersByCompany(cc
					.getName());
			OwnerContent[] ow = new OwnerContent[ownerList.size()];
			for (int i = 0; i < ownerList.size(); i++)
				ow[i] = ownerList.get(i);
			cc.setOwners(ow);
		}
		return companies;
	}

}