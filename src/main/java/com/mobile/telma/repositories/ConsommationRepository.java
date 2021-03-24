package com.mobile.telma.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mobile.telma.domains.Consommation;
import com.mobile.telma.domains.DetailCons;

@Repository
public class ConsommationRepository {

	private static final String  SQL_INSERT_CONSOMMATION = "insert into consommations(idclient, iddata, quantite, dateconsommation) values"
			+ " (? ,? ,? , ?)";
	
	private static final String SQL_INSERT_DETAIL_CONS = "insert into consommationdetails (idconsommation, iddataclient, idforfait, iddata, quantite, modeconsommation) values"
			+ " (?, ?, ?, ?, ?, ?)";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private int insertConsommation(Consommation cons) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( connection ->{ 
			PreparedStatement ps = connection.prepareStatement(SQL_INSERT_CONSOMMATION, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, cons.getIdClient());
			ps.setInt(2, cons.getIdData());
			ps.setDouble(3, cons.getQuantite());
			ps.setDate(4, cons.getDateConsommation());
			return ps;
		});
		cons.setIdConsommation( (Integer) keyHolder.getKeys().get("idconsommation") );
		return cons.getIdConsommation();
	}
	
	private int insertDetailCons(DetailCons detail) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( connection ->{ 
			PreparedStatement ps = connection.prepareStatement(SQL_INSERT_DETAIL_CONS, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, detail.getIdConsommation());
			ps.setInt(2, detail.getIdDataClient());
			ps.setInt(3, detail.getIdForfait());
			ps.setInt(4, detail.getIdData());
			ps.setDouble(5, detail.getQuantite());
			ps.setString(6, detail.getModeConsommation());
			return ps;
		});
		return (Integer) keyHolder.getKeys().get("idconsommation");
	}
	
	private void insertDetailCons(Consommation cons) {
		List<DetailCons> details = cons.getDetails();
		for(int i = 0; i < details.size(); i++ ) {
			details.get(i).setIdConsommation( cons.getIdConsommation() );
			insertDetailCons( details.get(i) );
		}
	}
	
	public int insertionConsommation(Consommation consommation) {
		insertConsommation(consommation);
		insertDetailCons(consommation);
		return 1;
	}
	
	
}
