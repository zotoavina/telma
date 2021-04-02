package com.mobile.telma.repositories;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
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
	
	private static final String UPDATE_DC_EXPIRATION = "update dataclients set expiration = ? where idforfait = ? "
			+ " and iddata = ? ";
	
	private static final String SQL_DATAS_ACTUEL = "select * from f_getDatasClientActuel(?, ?)";
	
	private static final String SQL_DATA_ACTUEL= "select * from f_getDataClientActuel(?, ?, ?)";
	
	
			
	
	
	  
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<DataActuel> datasActuelRowMapper = ( (rs, numRows) -> {
		DataActuel da = new DataActuel();
		da.setIdForfait(rs.getInt("idforfait"));
		da.setNomForfait(rs.getString("nomforfait"));
		da.setIdData(rs.getInt("iddata"));
		da.setQuantite(rs.getDouble("quantite"));
		da.setNomData(rs.getString("nomdata"));
		da.setDateExpiration(rs.getTimestamp("expiration"));
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
			ps.setTimestamp(5, data.getDateAchat());
			ps.setInt(6, data.getValidite());
			ps.setTimestamp(7, data.getExpiration());
			System.out.println(ps.toString());
			return ps;
		}, keyHolder);  
		return (Integer)keyHolder.getKeys().get("iddataclient");
	} 
	
	public void insertDataClient(List<DataClient> data) {
		for(int i = 0 ; i < data.size(); i ++ ) {
			insert(data.get(i));
			updateExpiration(data.get(i));
		}
	}
	
	
	@SuppressWarnings("deprecation")
	public List<DataActuel> getDatasActuel(int idClient, Timestamp date){
		return jdbcTemplate.query(SQL_DATAS_ACTUEL, new Object[] { idClient, date }, datasActuelRowMapper);
	}
	
	@SuppressWarnings("deprecation")
	public List<DataActuel> getDataActuel(int idClient, int idData,Timestamp date){
		System.out.println(SQL_DATA_ACTUEL);
		return jdbcTemplate.query(SQL_DATA_ACTUEL, new Object[] {  idClient, idData, date }, dataActuelRowMapper);
	}
	
	public void updateExpiration(DataClient da) {
		System.out.println("update data client : " + UPDATE_DC_EXPIRATION);
		jdbcTemplate.update(UPDATE_DC_EXPIRATION , new Object[] { 
				da.getExpiration(), da.getIdForfait(), da.getIdData()
		});
	}
	
	
}
