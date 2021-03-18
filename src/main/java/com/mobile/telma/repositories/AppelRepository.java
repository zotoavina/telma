package com.mobile.telma.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mobile.telma.domains.Appel;
import com.mobile.telma.exceptions.EtAuthException;
import java.util.List;

@Repository
public class AppelRepository {
	
	private static final String SQL_INSERT = "insert into appels(idclient, numero, duree) values(?, ?, ?)"; 

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Appel insert(Appel appel)throws EtAuthException{
		return null;
	}
	
	public List<Appel> getAppelClient(){
		return null;
	};
	
	
}
