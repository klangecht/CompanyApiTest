package com.klangecht.companyApiTestDAO;

public class OwnerContent {

	private long id;
	private String name;
	private String companyName;
	private long companyId;

	public OwnerContent(String name, String companyName, long companyId) {

		this.name = name;
		this.companyName = companyName;
		this.companyId = companyId;
	}
	
	public OwnerContent() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}
