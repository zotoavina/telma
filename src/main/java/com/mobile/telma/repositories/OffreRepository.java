package com.mobile.telma.repositories;

import com.mobile.telma.domains.Forfait;
import com.mobile.telma.domains.Offre;
import com.mobile.telma.exceptions.EtBadRequestException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OffreRepository {
	private static final String SQL_FIND_ALL = "select * from offres";
	private static final String SQL_FIND_BY_ID = "select * from offres where idoffre = ?";
	private static final String SQL_UPDATE = "update offres set nomoffre = ? , code = ?, interne = ?,"
			+ " autres = ?, international = ?, active = ?, description = ?, priorite = ? where idoffre = ?";
	
	private static final String SQL_INSERT = "insert into offres( nomoffre, code, interne, autres, international, description, priorite) values "
			+ "(?,  ?, ?, ?, ?, ?, ?)";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ForfaitRepository forfaitRepository;
	
	
	private RowMapper<Offre> offreRowMapper = ( (rs, numRows) -> {
		return new Offre(
				rs.getInt("idoffre"),
				rs.getString("nomoffre"),
				rs.getString("code"),
				rs.getDouble("interne"),
				rs.getDouble("autres"),
				rs.getDouble("international"),
				rs.getDate("datecreation"),
				rs.getInt("active"),
				rs.getString("description"),
				rs.getInt("priorite")
				);
	} );
	
	
	public List<Offre> findAll()throws EtBadRequestException{
		try {
			return jdbcTemplate.query(SQL_FIND_ALL, offreRowMapper);
		}catch(Exception e) {
			throw new EtBadRequestException("Une erreur est survenue lors de la selection des offres");
		}
	}
	
	@SuppressWarnings("deprecation")
	public Offre findById(int idOffre)throws EtBadRequestException{
		try {
			return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[] {idOffre}, offreRowMapper);
		}catch(Exception e) {
			throw new EtBadRequestException("Une erreur est survenue lors de la selection des offres");
		}
	}
	
	public void updateOffre(Offre offre) {
		System.out.println(offre.toString());
		jdbcTemplate.update(SQL_UPDATE, new Object[] { 
				offre.getNomOffre(), offre.getCode(),
				offre.getInterne(), offre.getAutres(),
				offre.getInternational(), offre.getActive(),
				offre.getDescription(), offre.getPriorite(),
				offre.getIdOffre()
				});
	}
	
	public int insertOffre(Offre offre)throws EtBadRequestException {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update( connection ->{ 
				PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, offre.getNomOffre());
				ps.setString(2, offre.getCode());
				ps.setDouble(3, offre.getInterne());
				ps.setDouble(4, offre.getAutres());
				ps.setDouble(5, offre.getInternational());
				ps.setString(6, offre.getDescription());
				ps.setInt(7, offre.getPriorite());
				return ps;
			}, keyHolder);
			return (Integer) keyHolder.getKeys().get("idoffre");
		}catch(Exception e) {
			throw new EtBadRequestException("Ajout de l' offre echoue");
		}
	}
	
	public List<Forfait> getForfaits(int idOffre){
		return forfaitRepository.getOffreForfaits(idOffre);
	}
	
	
	
//	public List<Offre> findAll(){
//		return mongoTemplate.findAll( Offre.class );
//	}
//	
//	public void insert(Offre offre) {
//		mongoTemplate.save(offre);
//	}
//	
//	public Offre findById(String idOffre) throws EtBadRequestException{
//		Query query = new Query();
//		query.addCriteria(Criteria.where("_id").is(new ObjectId(idOffre)));
//		List<Offre> offres = mongoTemplate.find(query, Offre.class);
//		if(offres.isEmpty())
//			throw new EtBadRequestException("Cette offre n' existe pas");
//		return offres.get(0);
//	}
//	
//	public Offre updateAddForfaits(String idOffre, Forfait forfait) {
//		Query query = new Query();
//		Criteria crt = Criteria.where("_id").is(new ObjectId(idOffre));
//		Update update = new Update();
//		//update.set("nomOffre", nom);
//		update.addToSet("forfaits", forfait);
//		query.addCriteria(crt);
//		return mongoTemplate.findAndModify(query, update,  new FindAndModifyOptions().returnNew(true), Offre.class);
//	}

}
