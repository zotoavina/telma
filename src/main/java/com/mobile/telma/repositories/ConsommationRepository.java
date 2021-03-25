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
	
	private static final String SQL_INSERT_DETAIL_CONS = "insert into consommationdetails (idconsommation, iddataclient, quantite, modeconsommation) values"
			+ " (?, ?, ?, ?)";
	
	private static final String SQL_INSERT_DETAIL_CONS1 = "insert into consommationdetails (idconsommation, quantite, modeconsommation) values"
			+ " (?, ?, ?)";
	
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
			System.out.println(ps.toString());
			return ps;
		}, keyHolder); 
		cons.setIdConsommation( (Integer) keyHolder.getKeys().get("idconsommation") );
		return cons.getIdConsommation();
	}
	
	private int insertDetailCons1(DetailCons detail) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( connection ->{ 
			PreparedStatement ps = connection.prepareStatement(SQL_INSERT_DETAIL_CONS, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, detail.getIdConsommation());
			ps.setInt(2, detail.getIdDataClient());
			ps.setDouble(3, detail.getQuantite());
			ps.setString(4, detail.getModeConsommation());
			return ps;
		}, keyHolder);
		return (Integer) keyHolder.getKeys().get("idconsommation");
	}
	
	private int insertDetailCons2(DetailCons detail) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( connection ->{ 
			PreparedStatement ps = connection.prepareStatement(SQL_INSERT_DETAIL_CONS1, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, detail.getIdConsommation());
			ps.setDouble(2, detail.getQuantite());
			ps.setString(3, detail.getModeConsommation());
			return ps;
		}, keyHolder);
		return (Integer) keyHolder.getKeys().get("idconsommation");
	}
	
	
	private int insertDetailCons(DetailCons detail) {
		if(detail.parCredit()) return insertDetailCons2(detail);
		return insertDetailCons1(detail);
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
