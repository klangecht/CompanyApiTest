package com.klangecht.companyApiTestDAO;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component("CompanyContentDAO")
public class CompanyContentDAO {

	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	public List<CompanyContent> getAllCompanies() {
		String SQL = "select * from companies ORDER BY id DESC";
		return jdbc.query(SQL, new CompanyBasicRowMapper());
	}

	public List<CompanyContent> getCompanyDetails(String name) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", name);
		String SQL = "select * from companies where name = :name";
		return jdbc.query(SQL, params, new CompanyBasicRowMapper());
	}

	public boolean writeCompanyContent(CompanyContent companyContent) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(companyContent);
		String SQL = "insert into companies (name, address, city, country, email, phone, content, owners) values (:name, :address, :city, :country, :email, :phone, :content, :owners)";
		return jdbc.update(SQL, params) == 1;
	}

	public boolean updateCompanyContent(CompanyContent companyContent) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(companyContent);
		String SQL = "update companies set name=:name, address=:address, city=:city, country=:country, email=:email, phone=:phone, content=:content, owners=:owners where id=:id";
		return jdbc.update(SQL, params) == 1;
	}

	public List<CompanyContent> getCompanyList() {
		String SQL = "select * from companies";
		return jdbc.query(SQL, new CompanyNameRowMapper());
	}
}
