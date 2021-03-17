package com.mobile.telma.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mobile.telma.exceptions.*;
import com.mobile.telma.domains.Client;

@Repository
public class ClientRepository {

	private static final String SQL_INSERT = "insert into clients (nom, prenom, numero, mdp) values "
			+ "(?, ?, ?, ?);";
	
	private static final String SQL_GET_BY_NUMERO ="select * from clients where numero = ?";

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private RowMapper<Client> clientRowMapper = (( rs, numRows) -> {
		return new Client(
				rs.getInt("idclient"),
				rs.getInt("idoperateur"),
				rs.getString("nom"),
				rs.getString("prenom"),
				rs.getString("numero"),
				rs.getString("mdp"),
				rs.getDate("dateadhesion")
				);
	});
	
	public int insert(String nom, String prenom, String numero, String mdp)throws EtBadRequestException {
				try {
					KeyHolder keyHolder = new GeneratedKeyHolder();
					jdbcTemplate.update( connection ->{ 
						PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
						ps.setString(1, nom);
						ps.setString(2, prenom);
						ps.setString(3, numero);
						ps.setString(4, mdp);
						System.out.println( ps.toString() );
						return ps;
					}, keyHolder);
					return (Integer) keyHolder.getKeys().get("idclient");
				}catch(Exception e) {
					throw new EtBadRequestException("Inscription echoue");
				}
	}
	
	public Client getClientByNumero(String numero, String mdp) throws EtBadRequestException{
		try {
			jdbcTemplate.queryForObject(SQL_GET_BY_NUMERO, new Object[] {numero}, clientRowMapper);
		}catch(Exception e) {
			throw new EtBadRequestException("numero et ou mot de passe non valide");
		}
	}
	
}
