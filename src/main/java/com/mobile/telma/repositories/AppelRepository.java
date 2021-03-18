package com.mobile.telma.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mobile.telma.domains.Appel;
import com.mobile.telma.exceptions.EtAuthException;
import com.mobile.telma.exceptions.EtBadRequestException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class AppelRepository {
	
	private static final String SQL_INSERT = "insert into appels(idclient, numero, duree) values(?, ?, ?)"; 
	private static final String SQL_FIND_BY_ID = "select * from appels where idappel = ?";
	private static final String SQL_FIND_BY_ID_CLIENT= "select * from appels where idclient = ?";
	
	private RowMapper<Appel> appelRowMapper = ( (rs, numRow) -> {
		return new Appel(
				rs.getInt("idappel"),
				rs.getInt("idclient"),
				rs.getString("numero"),
				rs.getInt("duree"),
				rs.getDate("dateappel")
				);
		
	} );

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int insert(Appel appel)throws EtBadRequestException{
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update( connection -> {
				PreparedStatement ps= connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, appel.getIdclient());
				ps.setString(2, appel.getNumero());
				ps.setDouble(3, appel.getDuree());
				System.out.println(ps.toString());
				return ps;
			}, keyHolder);
			return (Integer) keyHolder.getKeys().get("idappel");
		}catch(Exception e) {
			throw new EtBadRequestException("Une erreur est survenu lors de l' insertion de l ' appel");
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public Appel findById(int idAppel)throws EtBadRequestException{
		return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] {idAppel}, appelRowMapper);
	}
	
	
	@SuppressWarnings("deprecation")
	public List<Appel> getAppelClient(int idClient){
		return jdbcTemplate.query(SQL_FIND_BY_ID_CLIENT, new Object[] { idClient }, appelRowMapper );
	};
	
	
}
