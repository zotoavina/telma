package com.mobile.telma.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.mobile.telma.domains.ForfaitClient;

@Repository
public class ForfaitClientRepository {
	
	private final static String SQL_INSERT = "insert into forfaitclients(idclient,idforfait,appel, sms, facebook,instagram,internet, modepaiement) values"
			+ "(?, ?, ?, ?, ?, ?, ?, ?)";
	
	private final static String SQL_UPDATE = "update forfaitclients set appel = ?, sms = ? ,facebook = ?, instagram = ?, internet = ? where idforfaitclient = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public int insert(ForfaitClient fclient) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update( connection ->{ 
			PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, fclient.getIdClient());
			ps.setInt(2, fclient.getIdForfait());
			ps.setDouble(3, fclient.getAppel());
			ps.setDouble(4, fclient.getSms());
			ps.setDouble(5, fclient.getFacebook());
			ps.setDouble(6, fclient.getInstagram());
			ps.setDouble(7, fclient.getInternet());
			ps.setString(8, fclient.getModePaiement());
			System.out.println( ps.toString() );
			return ps;
		}, keyHolder);
		return (Integer)keyHolder.getKeys().get("idforfaitclient");
	}
	
	public void update(ForfaitClient fclient) {
		jdbcTemplate.update(SQL_UPDATE, new Object[] { 
				fclient.getAppel(), fclient.getSms(), 
				fclient.getFacebook(), fclient.getInstagram(), fclient.getInternet()
				});
	}
	
}
