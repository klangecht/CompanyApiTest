package com.klangecht.companyApiTestDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CompanyNameRowMapper implements RowMapper<CompanyContent>  {

	@Override
	public CompanyContent mapRow(ResultSet rs, int rowNum) throws SQLException {
		CompanyContent company = new CompanyContent();
		company.setId(rs.getInt("id"));
		company.setName(rs.getString("name"));
		return company;
	}
}
