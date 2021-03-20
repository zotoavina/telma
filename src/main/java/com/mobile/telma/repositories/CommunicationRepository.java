package com.mobile.telma.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mobile.telma.domains.Appel;
import com.mobile.telma.domains.Sms;
import com.mobile.telma.exceptions.EtAuthException;
import com.mobile.telma.exceptions.EtBadRequestException;

import java.util.List;

@Repository
public class CommunicationRepository {
	private static final int ACTIVE = 1;
	private static final int INACTIVE = 0;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void insertAppel(Appel appel) {
		mongoTemplate.save(appel);
	}
	
	
	public List<Appel> getAppelClient(int idClient, String numero){
		Query query = new Query();
		Criteria criteria = new Criteria().andOperator( Criteria.where("idClient").is(idClient), 
				Criteria.where("activeEnvoyeur").is(ACTIVE) );
		Criteria criteria1 = new Criteria().andOperator(  Criteria.where("numero").is(numero),
				Criteria.where("activeReceveur").is(ACTIVE)	);
		query.addCriteria( new Criteria().orOperator(criteria, criteria1));
		query.with(Sort.by(Sort.Direction.DESC, "date"));
		return mongoTemplate.find(query, Appel.class);
	}
	
	public List<Appel> getAppelClientSortant(int idClient) {
		Query query = new Query();
		Criteria criteria1 = Criteria.where("idClient").is(idClient);
		Criteria criteria2 = Criteria.where("activeEnvoyeur").is( ACTIVE );
		query.addCriteria(criteria1);
		query.addCriteria(criteria2);
		query.with(Sort.by(Sort.Direction.DESC, "date"));
		return mongoTemplate.find(query, Appel.class);
	}
	
	public List<Appel> getAppelClientEntrant(String numero){
		Query query = new Query();
		Criteria criteria = Criteria.where("numero").is(numero);
		Criteria criteria2 = Criteria.where("activeReceveur").is( ACTIVE );
		query.addCriteria(criteria);
		query.addCriteria(criteria2);
		query.with(Sort.by(Sort.Direction.DESC, "date"));
		return mongoTemplate.find(query, Appel.class);
	}
	
	public void SupprimerAppelSortant(int idClient) {
		Query query = new Query();
		Criteria criteria = Criteria.where("idClient").is(idClient);
		Criteria criteria2 = Criteria.where("activeEnvoyeur").is( ACTIVE );
		query.addCriteria(criteria);
		query.addCriteria(criteria2);
		Update update = new Update();
		update.set("activeEnvoyeur", INACTIVE);
		mongoTemplate.updateMulti(query, update, Appel.class);
	}
	
	public void SupprimerAppelEntrant(String numero) {
		Query query = new Query();
		Criteria criteria = Criteria.where("numero").is(numero);
		Criteria criteria2 = Criteria.where("activeReceveur").is( ACTIVE );
		query.addCriteria(criteria);
		query.addCriteria(criteria2);
		Update update = new Update();
		update.set("activeReceveur", INACTIVE);
		 mongoTemplate.updateMulti(query, update,  Appel.class);
	}
	
	public void SupprimerAppel(int idClient, String numero) {
		SupprimerAppelEntrant(numero);
		SupprimerAppelSortant(idClient);
	}
	
	
	//--------------------------------------------------------------------------------------------
	
	public void insertMessage(Sms sms) {
		mongoTemplate.save(sms);
	}
	
	public List<Sms> getSmsClient(int idClient, String numero){
		Query query = new Query();
		Criteria criteria = new Criteria().andOperator( Criteria.where("idClient").is(idClient), 
				Criteria.where("activeEnvoyeur").is(ACTIVE) );
		Criteria criteria1 = new Criteria().andOperator(  Criteria.where("numero").is(numero),
				Criteria.where("activeReceveur").is(ACTIVE)	);
		query.addCriteria( new Criteria().orOperator(criteria, criteria1));
		query.with(Sort.by(Sort.Direction.DESC, "date"));
		return mongoTemplate.find(query, Sms.class);
	}
	
	public void SupprimerSmsSortant(int idClient) {
		Query query = new Query();
		Criteria criteria = Criteria.where("idClient").is(idClient);
		Criteria criteria2 = Criteria.where("activeEnvoyeur").is( ACTIVE );
		query.addCriteria(criteria);
		query.addCriteria(criteria2);
		Update update = new Update();
		update.set("activeEnvoyeur", INACTIVE);
		mongoTemplate.updateMulti(query, update, Sms.class);
	}
	
	public void SupprimerSmsEntrant(String numero) {
		Query query = new Query();
		Criteria criteria = Criteria.where("numero").is(numero);
		Criteria criteria2 = Criteria.where("activeReceveur").is( ACTIVE );
		query.addCriteria(criteria);
		query.addCriteria(criteria2);
		Update update = new Update();
		update.set("activeReceveur", INACTIVE);
		 mongoTemplate.updateMulti(query, update,  Sms.class);
	}
	
	public void SupprimerSms(int idClient, String numero) {
		SupprimerSmsEntrant(numero);
		SupprimerSmsSortant(idClient);
	}
	
	
	
}
