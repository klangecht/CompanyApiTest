package com.klangecht.companyApiTestDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CompanyBasicRowMapper implements RowMapper<CompanyContent> {

	@Override
	public CompanyContent mapRow(ResultSet rs, int rowNum) throws SQLException {
		CompanyContent company = new CompanyContent();

		company.setId(rs.getInt("id"));
		company.setAddress(rs.getString("address"));
		company.setName(rs.getString("name"));
		company.setCity(rs.getString("city"));
		company.setCountry(rs.getString("country"));
		company.setPhone(rs.getString("phone"));
		company.setEmail(rs.getString("email"));
		//Array owners = rs.getArray("is_nullable");
		//company.setOwners((String[])owners.getArray());
		company.setContent(rs.getString("content"));
		
		return company;
	}

}
