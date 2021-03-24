package com.mobile.telma.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mobile.telma.domains.DataClient;

@Repository
public class DataClientRepository {

	private static final String INSERT_DATA_CLIENT = "insert into dataclients(idclient, idforfait, iddata, quantite, dateachat , validite) "
			+ "values( ?, ?, ?, ?, ?, ?);"; 
	
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	
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
}
