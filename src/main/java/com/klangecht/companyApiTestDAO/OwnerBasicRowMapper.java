package com.klangecht.companyApiTestDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class OwnerBasicRowMapper implements RowMapper<OwnerContent> {

	@Override
	public OwnerContent mapRow(ResultSet rs, int rowNum) throws SQLException {
		OwnerContent owner = new OwnerContent();

		owner.setId(rs.getInt("id"));
		owner.setName(rs.getString("name"));
		owner.setCompanyId(rs.getLong("company_id"));
		return owner;
	}
}
