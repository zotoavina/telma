package com.mobile.telma.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mobile.telma.domains.stats.StatForfait;
import com.mobile.telma.domains.stats.Stat;

import java.util.List;

@Repository
public class StatRepository {

	private static final String SQL_STAT_OFFRE = "select * from f_statoffre(?, ?)";
	
	private static final String SQL_STAT_FORFAIT = "select * from f_statforfait(?, ?, ?)";
	
	private static final String SQL_STAT_CONS_DATA = "select * from f_consommationpardata( ? , ? )";
	
	private static final String SQL_STAT_CONS_DATA_MOIS = "select * from f_consommationdataparmois( ? , ?)";
 	
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<Stat> statOffreRowMapper = ( (rs, numRow) -> {
		Stat so = new Stat();
		so.setId(rs.getInt("id"));   
		so.setName(rs.getString("name"));
		so.setMontant(rs.getDouble("montant"));
		so.setValue(rs.getInt("value"));
		so.setAnnee(rs.getInt("annee"));
		so.setMois(rs.getInt("mois"));
		return so;
	} );
	
	
	
	@SuppressWarnings("deprecation")
	public List<Stat> getStatOffre(int annee, int mois){
		return jdbcTemplate.query(SQL_STAT_OFFRE, new Object[] { annee, mois,}, statOffreRowMapper);
	}
	
	@SuppressWarnings("deprecation")
	public List<Stat> getStatForfait(int idOffre, int annee, int mois){
		return jdbcTemplate.query(SQL_STAT_FORFAIT, new Object[] { idOffre, annee, mois}, 
				statOffreRowMapper);
	}
	
	@SuppressWarnings("deprecation")
	public List<Stat> getStatConsommationParData(int annee, int mois){
		return jdbcTemplate.query(SQL_STAT_CONS_DATA, new Object[] { annee, mois }, statOffreRowMapper );
	}
	
	@SuppressWarnings("deprecation")
	public List<Stat> getStatConsommationDataParMois(int idData, int annee){
		return jdbcTemplate.query(SQL_STAT_CONS_DATA_MOIS , new Object[] {idData, annee}, statOffreRowMapper );
	}
	
}
