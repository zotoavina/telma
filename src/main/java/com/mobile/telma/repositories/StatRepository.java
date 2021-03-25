package com.mobile.telma.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mobile.telma.domains.stats.StatForfait;
import com.mobile.telma.domains.stats.StatOffre;

import java.util.List;

@Repository
public class StatRepository {

	private static final String SQL_STAT_OFFRE = "SELECT frt.idoffre,frt.nomoffre,coalesce(st.montant,0) montant,"
			+ " coalesce(st.nbrachat,0) nbrachat,coalesce(st.anne, ?) annee, coalesce(st.mois, ?) mois"
			+ " FROM offres frt left JOIN v_statoffres st"
			+ " ON frt.idoffre = st.idoffre AND st.anne = ? and st.mois= ?";
	
	private static final String SQL_STAT_FORFAIT = "SELECT frt.idoffre,frt.idforfait,frt.nomforfait,coalesce(frt.prix * st.nbrachat,0) montant,"
			+ " coalesce(st.nbrachat,0) nbrachat,coalesce(st.anne, ?) annee, coalesce(st.mois, ?) mois"
			+ " FROM forfaits frt left JOIN v_statforfaits st"
			+ " ON frt.idforfait = st.idforfait AND anne = ? and mois= ? WHERE frt.idoffre = ?";
 	
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<StatOffre> statOffreRowMapper = ( (rs, numRow) -> {
		StatOffre so = new StatOffre();
		so.setId(rs.getInt("idoffre"));
		so.setNom(rs.getString("nomoffre"));
		so.setMontant(rs.getDouble("montant"));
		so.setNbrAchat(rs.getInt("nbrachat"));
		so.setAnnee(rs.getInt("annee"));
		so.setMois(rs.getInt("mois"));
		return so;
	} );
	
	private RowMapper<StatForfait> statForfaitRowMapper = ( (rs, numRow) -> {
		StatForfait sf = new StatForfait();
		sf.setId(rs.getInt("idoffre"));
		sf.setIdForfait(rs.getInt("idforfait"));
		sf.setNom(rs.getString("nomforfait"));
		sf.setMontant(rs.getDouble("montant"));
		sf.setNbrAchat(rs.getInt("nbrachat"));
		sf.setAnnee(rs.getInt("annee"));
		sf.setMois(rs.getInt("mois"));
		return sf;
	} );
	
	
	@SuppressWarnings("deprecation")
	public List<StatOffre> getStatOffre(int annee, int mois){
		return jdbcTemplate.query(SQL_STAT_OFFRE, new Object[] { annee, mois, annee, mois}, statOffreRowMapper);
	}
	
	@SuppressWarnings("deprecation")
	public List<StatForfait> getStatForfait(int idOffre, int annee, int mois){
		return jdbcTemplate.query(SQL_STAT_FORFAIT, new Object[] { annee, mois, annee, mois, idOffre}, 
				statForfaitRowMapper);
	}
	
}
