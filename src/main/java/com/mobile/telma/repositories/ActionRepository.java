package com.mobile.telma.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mobile.telma.domains.Action;
import com.mobile.telma.exceptions.EtBadRequestException;

@Repository
public class ActionRepository {

	private static final String SQL_INSERT = "insert into actions(idtypeaction, idclient, montant ,etat) "
			+ "values(?, ?, ?, ?)";
	
	private static final String SQL_UPDATE_ETAT = "update actions set etat = ? where idaction = ?";
	
	private static final String SQL_FIND_BY_ID = "select * from actions where idaction = ?";
	
	private RowMapper<Action> actionRowMapper = ( (rs, rowNum) -> {
		return new Action( 
				rs.getInt("idaction"),
				rs.getInt("idtypeaction"),
				rs.getInt("idclient"),
				rs.getDouble("montant"),
				rs.getInt("etat"),
				rs.getDate("dateaction"));
	} );
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int insertAction(Action action)throws EtBadRequestException{
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update( connection ->{ 
				PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, action.getIdTypeAction());
				ps.setInt(2, action.getIdClient());
				ps.setDouble(3, action.getMontant());
				ps.setInt(4, action.getEtat());
				System.out.println( ps.toString() );
				return ps;
			}, keyHolder);
			return (Integer) keyHolder.getKeys().get("idaction");
		}catch(Exception e) {
			throw new EtBadRequestException("Invalid request");
		}
	}
	
	public void update(Action action)throws EtBadRequestException{
		try {
			jdbcTemplate.update(SQL_UPDATE_ETAT, new Object[] { action.getEtat(), action.getIdAction() });
		}catch(Exception e) {
			throw new EtBadRequestException("Validation de l' action non reussi");
		}
	}
	
	@SuppressWarnings("deprecation")
	public Action findById(int idAction)throws EtBadRequestException{
		try {
			return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] {idAction}, actionRowMapper);
		}catch(Exception e) {
			throw new EtBadRequestException("Une erreur est survenue lors de la selection de l' action");
		}
	}
	
	

}
