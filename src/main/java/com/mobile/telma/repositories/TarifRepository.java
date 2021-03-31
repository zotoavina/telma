package com.mobile.telma.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mobile.telma.domains.Tarif;

@Repository
public class TarifRepository {
	private static final String SQL_GET_TARIFS = "select * from v_tarifpardefaut";
	private static final String SQL_GET_TARIF_BY_DATA = "select * from v_tarifpardefaut where iddata = ?";
	private static final String SQL_UPDATE_TARIF = "update tarifcredit set interne = ? , autres = ? , "
			+ "international = ? where idtarif = ?";
	private static final String SQL_GET_BY_IDTARIF = "select * from v_tarifpardefaut where idtarif = ?";
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<Tarif> tarifRowMapper = ( (rs, numRows ) -> {
		Tarif tarif = new Tarif();
		tarif.setIdTarif( rs.getInt("idtarif"));
		tarif.setIdData( rs.getInt("iddata"));
		tarif.setInterne(rs.getDouble("interne"));
		tarif.setAutres( rs.getDouble("autres"));
		tarif.setInternational(rs.getDouble("international"));
		tarif.setNomData(rs.getString("nomData"));
		return tarif;
	} );
	
	public List<Tarif> getTarifs(){
		return jdbcTemplate.query(SQL_GET_TARIFS, tarifRowMapper);
	}
	
	
	@SuppressWarnings("deprecation")
	public Tarif getTarifById(int idTarif) {
		return jdbcTemplate.queryForObject(SQL_GET_BY_IDTARIF, new Object[] {idTarif}, tarifRowMapper);
	}
	
	@SuppressWarnings("deprecation")
	public Tarif getTarifByIdData(int idData){
		return jdbcTemplate.queryForObject(SQL_GET_TARIF_BY_DATA, new Object[] {idData}, tarifRowMapper);
	}
	
	public void updateTarif(Tarif tarif) {
		jdbcTemplate.update( SQL_UPDATE_TARIF, new Object[] {
				tarif.getInterne(), tarif.getAutres(), tarif.getInternational(), tarif.getIdTarif(), });
	}
	
	
}
