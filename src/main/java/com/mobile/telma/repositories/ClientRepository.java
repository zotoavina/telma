package com.mobile.telma.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepository {

	private static final String SQL_INSERT = "insert into client(idoperateur, nom, prenom, numero, mdp) "
			+ " values()"

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public Client insert(Client client) {
		
	}
}
