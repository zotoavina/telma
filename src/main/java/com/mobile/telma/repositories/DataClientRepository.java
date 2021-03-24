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
	
	private static final String SQL_DATAS_ACTUEL = " select dc.idforfait,  f.nomforfait, dc.iddata, "
			+ "    sum(dc.quantite - coalesce(c.quantite, 0)) as quantite, d.nomdata"
			+ "	from v_dataclients dc left join v_consommationforfaits c on  dc.idforfait = c.idforfait"
			+ "	and dc.iddata = c.iddata "
			+ "	and dc.expiration > ? join datas d on"
			+ "	dc.iddata = d.iddata join forfaits f on "
			+ "	dc.idforfait = f.idforfait where dc.idclient = ? "
			+ "	group by dc.idforfait, dc.iddata, d.nomdata, f.nomforfait";
	
	private static final String SQL_DATA_ACTUEL= "select dc.idforfait,  f.nomforfait, dc.iddata, "
			+ " sum(dc.quantite - coalesce(c.quantite, 0)) as quantite, d.nomdata"
			+ "	from v_dataclients dc left join v_consommationforfaits c on  dc.idforfait = c.idforfait"
			+ "	and dc.iddata = c.iddata "
			+ "	and dc.expiration > ? join datas d on"
			+ "	dc.iddata = d.iddata join forfaits f on "
			+ "	dc.idforfait = f.idforfait where dc.idclient = ? and dc.iddata = ?"
			+ "	group by dc.idforfait, dc.iddata, d.nomdata, f.nomforfait";
	
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<DataActuel> dataActuelRowMapper = ( (rs, numRows) -> {
		DataActuel da = new DataActuel();
		da.setIdForfait(rs.getInt(1));
		da.setNomForfait(rs.getString(2));
		da.setIdData(rs.getInt(3));
		da.setQuantite(rs.getDouble(4));
		da.setNomData(rs.getString(5));
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
		return jdbcTemplate.query(SQL_DATAS_ACTUEL, new Object[] {  date , idClient}, dataActuelRowMapper);
	}
	
	@SuppressWarnings("deprecation")
	public List<DataActuel> getDataActuel(int idClient, int idData, Date date){
		System.out.println(SQL_DATA_ACTUEL);
		return jdbcTemplate.query(SQL_DATA_ACTUEL, new Object[] {  date , idClient, idData}, dataActuelRowMapper);
	}
	
	
}
