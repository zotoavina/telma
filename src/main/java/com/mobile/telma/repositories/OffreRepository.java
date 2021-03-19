package com.mobile.telma.repositories;

import com.mobile.telma.domains.Forfait;
import com.mobile.telma.domains.Offre;
import com.mobile.telma.exceptions.EtBadRequestException;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Repository;

@Repository
public class OffreRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<Offre> findAll(){
		return mongoTemplate.findAll( Offre.class );
	}
	
	public void insert(Offre offre) {
		mongoTemplate.save(offre);
	}
	
	public Offre findById(String idOffre) throws EtBadRequestException{
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(idOffre)));
		List<Offre> offres = mongoTemplate.find(query, Offre.class);
		if(offres.isEmpty())
			throw new EtBadRequestException("Cette offre n' existe pas");
		return offres.get(0);
	}
	
	public Offre updateAddForfaits(String idOffre, Forfait forfait) {
		Query query = new Query();
		Criteria crt = Criteria.where("_id").is(new ObjectId(idOffre));
		Update update = new Update();
		//update.set("nomOffre", nom);
		update.addToSet("forfaits", forfait);
		query.addCriteria(crt);
		return mongoTemplate.findAndModify(query, update,  new FindAndModifyOptions().returnNew(true), Offre.class);
	}

}
