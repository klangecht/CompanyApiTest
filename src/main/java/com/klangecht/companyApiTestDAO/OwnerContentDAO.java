package com.klangecht.companyApiTestDAO;

import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component("OwnerContentDAO")
public class OwnerContentDAO {

	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

	public boolean writeOwner(OwnerContent owner) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(owner);
		String SQL = "insert into owners (name, company_name, company_id) values (:name, :companyName, :companyId)";
		System.out.println(owner.getName());
		return jdbc.update(SQL, params) == 1;
	}

	public List<OwnerContent> getOwnersByCompany(String companyName) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("companyName", companyName);
		String SQL = "select * from owners where company_name = :companyName";
		return jdbc.query(SQL, params, new OwnerBasicRowMapper());
	}

	public Object setOwnersByCompany(String[] owners) {
		// TODO Auto-generated method stub
		return null;
	}
}
