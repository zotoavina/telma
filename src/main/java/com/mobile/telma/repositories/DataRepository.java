package com.mobile.telma.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.mobile.telma.domains.Data;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class DataRepository {

	private static String SQL_FIND_ALL_ACTIVE = " select * from datas where active = ? ";
	private static String SQL_INSERT = "insert into datas(nomdata, active, datecreation) values (?, ?, ?)";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<Data> dataRowMapper = (( rs, numRows) ->{
		Data data = new Data();
		data.setIdData( rs.getInt(1));
		data.setNomData( rs.getString(2));
		data.setActive( rs.getInt(3));
		data.setDateCreation(rs.getDate(4));
		return data;
	});
	
	
	@SuppressWarnings("deprecation")
	public List<Data> findDatas(){
		return jdbcTemplate.query(SQL_FIND_ALL_ACTIVE, new Object[] { Data.active() }, dataRowMapper);
	}
	
	public int insertData(Data data) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( connection ->{ 
			PreparedStatement ps = connection.prepareStatement(SQL_FIND_ALL_ACTIVE, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, data.getNomData());
			ps.setInt(2, data.getActive());
			ps.setDate(3, data.getDateCreation());
			return ps;
		});
		return (Integer) keyHolder.getKeys().get("iddata");
	}
	
}