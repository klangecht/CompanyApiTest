package com.klangecht.companyApiTestDAO;

public class CompanyContent {

	private long id;
	private String name;
	private String address;
	private String city;
	private String country;
	private String email;
	private String phone;
	private OwnerContent[] owners;
	private String content;

	public CompanyContent(String name, String address, String city, String country, String email, String phone,
			String content) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.country = country;
		this.email = email;
		this.phone = phone;
		this.content = content;
	}

	public CompanyContent() {

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public OwnerContent[] getOwners() {
		return owners;
	}

	public void setOwners(OwnerContent[] owners) {
		this.owners = owners;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
