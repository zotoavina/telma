package com.mobile.telma.domains;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="offres")
public class Offre {

	 @Id
	  public String id;

	  public String nomOffre;
	  public Date dateCreation = new Date();
	  public boolean active = true;
	  public String code;
	  public String description;
	  public Object tarifs;
	  public Object forfaits;
	  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNomOffre() {
		return nomOffre;
	}
	public void setNomOffre(String nomOffre) {
		this.nomOffre = nomOffre;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Object getTarifs() {
		return tarifs;
	}
	public void setTarifs(Object tarifs) {
		this.tarifs = tarifs;
	}
	public Object getForfaits() {
		return forfaits;
	}
	public void setForfaits(Object forfaits) {
		this.forfaits = forfaits;
	}
	  
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Offre() {}
	
	

}
