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

	private static final String INSERT_DATA_CLIENT = "insert into dataclients(idclient, idforfait, iddata, quantite, dateachat , validite) "
			+ "values( ?, ?, ?, ?, ?, ?);"; 
	
	private static final String SQL_DATA_ACTUEL = "select dco.idoffre,  o.nomOffre,dco.iddata, "
			+ " sum(dco.quantite - coalesce(co.quantite, 0)) as quantite, d.nomdata"
			+ "	from v_dataclientsoffres dco left join v_consommationoffres co on  dco.idoffre = co.idoffre"
			+ "	and dco.iddata = co.iddata"
			+ "	and dco.expiration > ? join datas d on"
			+ "	dco.iddata = d.iddata join offres o on "
			+ "	dco.idoffre = o.idoffre where dco.idclient = ?"
			+ "	group by dco.idoffre, dco.iddata, d.nomdata, o.nomoffre";
	
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<DataActuel> dataActuelRowMapper = ( (rs, numRows) -> {
		DataActuel da = new DataActuel();
		da.setIdOffre(rs.getInt(1));
		da.setNomOffre(rs.getString(2));
		da.setIdData(rs.getInt(3));
		da.setQuantite(rs.getDouble(4));
		da.setNomOffre(rs.getString(5));
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
	public List<DataActuel> getDataActuel(int idClient, Date date){
		return jdbcTemplate.query(SQL_DATA_ACTUEL, new Object[] { idClient, date }, dataActuelRowMapper);
	}
	
	
}
