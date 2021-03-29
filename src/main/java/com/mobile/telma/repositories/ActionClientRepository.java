package com.mobile.telma.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.mobile.telma.domains.ActionClient;
import com.mobile.telma.exceptions.EtAuthException;

import java.util.List;

@Repository
public class ActionClientRepository {
	public static final String SQL_FIND_ALL_NON_VALIDE = "select * from v_actionclient where etat = 0";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private RowMapper<ActionClient> actionClientRowMapper = ((rs, num) -> {
		ActionClient acl = new ActionClient();
		acl.setIdClient( rs.getInt("idclient"));
		acl.setIdOperateur( rs.getInt("idoperateur"));
		acl.setNom(rs.getString("nom"));
		acl.setPrenom(rs.getString("prenom"));
		acl.setNumero(rs.getString("numero"));
		acl.setMdp(rs.getString("mdp"));
		acl.setSolde(rs.getDouble("solde"));
		acl.setCredit(rs.getDouble("credit"));
		acl.setDateAdhesion(rs.getDate("dateadhesion"));
		acl.setIdaction(rs.getInt( "idaction"));
		acl.setMontantAction(rs.getDouble("montantaction"));
		acl.setEtat( rs.getInt("etat"));
		acl.setDateAction(rs.getDate("dateaction"));
		acl.setNomTypeAction( rs.getString("nomtypeaction") );
		return acl;
	});
	
	public List<ActionClient> findAllNonValide()throws EtAuthException{
		try {
			return jdbcTemplate.query(SQL_FIND_ALL_NON_VALIDE,  actionClientRowMapper);
		}catch(Exception e) {
			throw e;
		}
	}
}
