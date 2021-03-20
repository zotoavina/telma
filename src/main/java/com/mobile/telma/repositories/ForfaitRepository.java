package com.mobile.telma.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mobile.telma.domains.Forfait;
import com.mobile.telma.domains.ForfaitData;

@Repository
public class ForfaitRepository {
	private final static String SQL_INSERT = "insert into forfaits(idoffre, nomforfait, code, prix, validite, description)\r\n"
			+ "values(?, ?, ?, ?, ?, ?)" ;
	private final static String SQL_GET_BY_ID = "select * from forfaits where idforfait = ?";
	
	private final static String SQL_INSERT_FDATA = "insert into forfaitdatas(idforfait,iddata,quantite) values(?,?,?)";
	
	private final static String SQL_GET_FORFAITS_DATA = "select * from forfaitdatasetdatas where idforfait = ?";
	
	private final static String SQL_GET_FORFAITS_OFFRE = "select * from forfaits where idoffre = ?";
	
	@Autowired 
	private JdbcTemplate jdbcTemplate ;
	
	private RowMapper<Forfait> forfaitRowMapper = ( (rs, numRow) ->{
		return new Forfait(
					rs.getInt("idforfait"),
					rs.getInt("idoffre"),
					rs.getString("nomforfait"),
					rs.getString("code"),
					rs.getDouble("prix"),
					rs.getInt("validite"),
					rs.getDate("datecreation"),
					rs.getInt("active"),
					rs.getString("description")
				);
	} );
	
	private RowMapper<ForfaitData> forfaitDataRowMapper = ( (rs, numRow) ->{
			return new ForfaitData(
						rs.getInt("idfdata"),
						rs.getInt("idforfait"),
						rs.getInt("iddata"),
						rs.getDouble("quantite"),
						rs.getString("nomdata")
					);
	});
	
	private int insertForfait(Forfait forfait) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( connection ->{ 
			PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, forfait.getIdOffre());
			ps.setString(2, forfait.getNomforfait());
			ps.setString(3, forfait.getCode());
			ps.setDouble(4, forfait.getPrix());
			ps.setInt(5, forfait.getValidite());
			ps.setString(6, forfait.getDescription());
			System.out.println( ps.toString() );
			return ps;
		}, keyHolder);
		return (Integer)keyHolder.getKeys().get("idforfait");
	}
	
	public int createForfait(Forfait forfait) {
		int idForfait = insertForfait(forfait);
		List<ForfaitData> datas = forfait.getDatas();
		for(int i = 0; i < datas.size(); i++) {
			datas.get(i).setIdForfait(idForfait);
			insertForfaitData(datas.get(i));
		}
		return idForfait;
	}
	
	
	@SuppressWarnings("deprecation")
	public Forfait getForfaitBId(int idForfait) {
		Forfait forfait = jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[] {idForfait}, forfaitRowMapper);
		getForfaitDatas(forfait);
		return forfait;
	}
	
	public int insertForfaitData(ForfaitData data) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( connection ->{ 
			PreparedStatement ps = connection.prepareStatement(SQL_INSERT_FDATA, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, data.getIdForfait());
			ps.setInt(2, data.getIdData());
			ps.setDouble(3, data.getQuantite());
			System.out.println( ps.toString() );
			return ps;
		}, keyHolder);
		return (Integer)keyHolder.getKeys().get("idforfait");
	}
	
	
	@SuppressWarnings("deprecation")
	private void getForfaitDatas(Forfait forfait){
		List<ForfaitData> datas = jdbcTemplate.query(SQL_GET_FORFAITS_DATA, new Object[] { forfait.getIdForfait()}, forfaitDataRowMapper);
		forfait.setDatas(datas);
	}
	
	@SuppressWarnings("deprecation")
	public List<Forfait> getOffreForfaits(int idOffre){
		List<Forfait> forfaits = jdbcTemplate.query(SQL_GET_FORFAITS_OFFRE, new Object[] {idOffre}, forfaitRowMapper);
		for(int i = 0; i < forfaits.size(); i++ ) {
			getForfaitDatas(forfaits.get(i));
		}
		return forfaits;
	}
}

