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
	
	private static final String SQL_GET_BY_ID = "select * from clients where idclient = ?";
	
	private static final String SQL_UPDATE_SOLDE = "update clients set solde = ? where idclient = ?";
	
	private static final String SQL_UPDATE_SOLDE_CREDIT = "update clients set solde = ? , credit = ? where idclient = ?";
	

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
				rs.getDouble("solde"),
				rs.getDouble("credit"),
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
	
	
	@SuppressWarnings("deprecation")
	public Client getClientByNumero(String numero, String mdp) throws EtAuthException{
		try {
			Client client = jdbcTemplate.queryForObject(SQL_GET_BY_NUMERO, new Object[] {numero}, clientRowMapper);
			 if(!client.validPassword(mdp))
				 throw new EtAuthException("Mot de passe non valide");
			 return client;
		}catch(Exception e) {
			throw new EtAuthException("numero et ou mot de passe non valide");
		}
	}
	
	@SuppressWarnings("deprecation")
	public Client getClientByNumero(String numero)throws EtBadRequestException{
		return jdbcTemplate.queryForObject(SQL_GET_BY_NUMERO, new Object[] {numero}, clientRowMapper);
	}
	
	@SuppressWarnings("deprecation")
	public Client getClientById(int idClient)throws EtBadRequestException{
		try {
			Client client = jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[] {idClient},  clientRowMapper);
			return client;
		}catch(Exception e) {
			throw new EtBadRequestException("Une erreur est surevenue");
		}
	}
	
	public void updateSolde(Client client)throws EtBadRequestException{
		try {
			jdbcTemplate.update(SQL_UPDATE_SOLDE, new Object[] { client.getSolde(), client.getIdClient()});
		}catch(EtBadRequestException et) {
			throw new EtBadRequestException("Une erreur est survenue lors du mise a jour de votre solde");
		}
	}
	
	public void updateSoldeEtCredit(Client client) throws EtBadRequestException{
		try {
			jdbcTemplate.update(SQL_UPDATE_SOLDE_CREDIT, new Object[] { 
					client.getSolde(), client.getCredit(), client.getIdClient()
					});
		}catch(EtBadRequestException et) {
			throw new EtBadRequestException("Une erreur est survenue lors du mise a jour de votre solde");
		}
	}
	
	
	
	
}
