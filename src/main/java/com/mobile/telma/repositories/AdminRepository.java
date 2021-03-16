package com.mobile.telma.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mobile.telma.domains.Admin;
import com.mobile.telma.exceptions.EtAuthException;

@Repository
public class AdminRepository {
	private static final String SQL_FIND_BY_EMAIL = "select * from admins where email = ?";
	private static final String SQL_COUNT_EMAIL = "select count(*) from admins where email = ?";
	
	@Autowired 
	JdbcTemplate jdbcTemplate;
	
	RowMapper<Admin> adminRowMapper = ((rs, rowNum) ->{
		return new Admin(
				rs.getInt("idadmin"),
				rs.getInt("idoperateur"),
				rs.getString("nom"),
				rs.getString("prenom"),
				rs.getString("email"),
				rs.getString("mdp")
				);
	});
	
	@SuppressWarnings("deprecation")
	public Admin findAdminByEmailAndMdp(String email, String mdp) throws EtAuthException{
		try {
			Admin admin = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[] {email}, adminRowMapper);
			if(admin.getMdp().compareTo(mdp) != 0) throw new EtAuthException("Mot de passe non valide");
			return admin;
		}catch(Exception e) {
			throw new EtAuthException("Mot de passe et (ou) email non valide");
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public int countAdminByEmail(String email) {
		return jdbcTemplate.queryForObject(SQL_COUNT_EMAIL, new Object[] {email}, Integer.class);
	}
}
