package com.mobile.telma.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mobile.telma.domains.DataActuel;
import com.mobile.telma.domains.DataClient;

@Repository
public class DataClientRepository {

	private static final String INSERT_DATA_CLIENT = "insert into dataclients(idclient, idforfait, iddata, quantite, dateachat , validite, expiration) "
			+ "values( ?, ?, ?, ?, ?, ?, ?)"; 
	
	private static final String SQL_DATAS_ACTUEL = "select  dc.idforfait, f.nomforfait ,dc.iddata,"
			+ " sum(dc.quantite - coalesce( cf.quantite, 0) ) as quantite, d.nomdata"
			+ " from dataclients dc left join v_consommationforfaits cf on dc.iddataclient = cf.iddataclient"
			+ " and dc.expiration >? join forfaits f on dc.idforfait = f.idforfait"
			+ " join datas d on dc.iddata = d.iddata where dc.idclient = ?"
			+ " group by dc.idforfait , f.nomforfait, dc.iddata, d.nomdata";
	
	private static final String SQL_DATA_ACTUEL= "select dc.iddataclient, dc.idforfait, f.nomforfait ,dc.iddata,"
			+ " (dc.quantite - coalesce( cf.quantite, 0) ) as quantite, d.nomdata, offre.interne,"
			+ " offre.autres, offre.international"
			+ " from dataclients dc left join v_consommationforfaits cf on dc.iddataclient = cf.iddataclient"
			+ " and dc.expiration > ? join forfaits f on dc.idforfait = f.idforfait"
			+ " join datas d on dc.iddata = d.iddata join offres offre on"
			+ " f.idoffre = offre.idoffre where dc.idclient = ? and dc.iddata = ?";
			
	
	
	
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<DataActuel> datasActuelRowMapper = ( (rs, numRows) -> {
		DataActuel da = new DataActuel();
		da.setIdForfait(rs.getInt("idforfait"));
		da.setNomForfait(rs.getString("nomforfait"));
		da.setIdData(rs.getInt("iddata"));
		da.setQuantite(rs.getDouble("quantite"));
		da.setNomData(rs.getString("nomdata"));
		return da;
	} );
	
	private RowMapper<DataActuel> dataActuelRowMapper = ( (rs, numRows) -> {
		DataActuel da = new DataActuel();
		da.setIdDataClient(rs.getInt("iddataclient"));
		da.setIdForfait(rs.getInt("idforfait"));
		da.setNomForfait(rs.getString("nomforfait"));
		da.setIdData(rs.getInt("iddata"));
		da.setQuantite(rs.getDouble("quantite"));
		da.setNomData(rs.getString("nomdata"));
		da.setInterne( rs.getDouble("interne"));
		da.setAutres( rs.getDouble("autres") );
		da.setInternational( rs.getDouble("international"));
		return da;
	} );
	
	
	public int insert(DataClient data) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( connection ->{ 
			PreparedStatement ps = connection.prepareStatement(INSERT_DATA_CLIENT, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, data.getIdClient());
			ps.setInt(2, data.getIdForfait());
			ps.setInt(3, data.getIdData());
			ps.setDouble(4, data.getQuantite());
			ps.setDate(5, data.getDateAchat());
			ps.setInt(6, data.getValidite());
			ps.setDate(7, data.getExpiration());
			System.out.println(ps.toString());
			return ps;
		}, keyHolder);
		return (Integer)keyHolder.getKeys().get("iddataclient");
	} 
	
	public void insertDataClient(List<DataClient> data) {
		for(int i = 0 ; i < data.size(); i ++ ) {
			insert(data.get(i));
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public List<DataActuel> getDatasActuel(int idClient, Date date){
		return jdbcTemplate.query(SQL_DATAS_ACTUEL, new Object[] {  date , idClient}, datasActuelRowMapper);
	}
	
	@SuppressWarnings("deprecation")
	public List<DataActuel> getDataActuel(int idClient, int idData, Date date){
		System.out.println(SQL_DATA_ACTUEL);
		return jdbcTemplate.query(SQL_DATA_ACTUEL, new Object[] {  date , idClient, idData}, dataActuelRowMapper);
	}
	
	
}
