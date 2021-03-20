package com.mobile.telma.domains;


import java.util.Date;
import java.util.List;


public class Forfait {
	private int idForfait;
	private int idOffre;
	private String nomforfait;
	private String code;
	private double prix;
	private int  validite;
	private Date datecreation;
	private int active;
	private String description;
	private List<ForfaitData> datas;
	
	public int getIdForfait() {
		return idForfait;
	}
	public void setIdForfait(int idForfait) {
		this.idForfait = idForfait;
	}
	public int getIdOffre() {
		return idOffre;
	}
	public void setIdOffre(int idOffre) {
		this.idOffre = idOffre;
	}
	public String getNomforfait() {
		return nomforfait;
	}
	public void setNomforfait(String nomforfait) {
		this.nomforfait = nomforfait;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public int getValidite() {
		return validite;
	}
	public void setValidite(int validite) {
		this.validite = validite;
	}
	public Date getDatecreation() {
		return datecreation;
	}
	public void setDatecreation(Date datecreation) {
		this.datecreation = datecreation;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public List<ForfaitData> getDatas() {
		return datas;
	}
	public void setDatas(List<ForfaitData> datas) {
		this.datas = datas;
	}
	public Forfait(int idForfait, int idOffre, String nomforfait, String code, double prix, int validite,
			Date datecreation, int active, String description) {
		super();
		this.idForfait = idForfait;
		this.idOffre = idOffre;
		this.nomforfait = nomforfait;
		this.code = code;
		this.prix = prix;
		this.validite = validite;
		this.datecreation = datecreation;
		this.active = active;
		this.description = description;
	}
	
	
	public Forfait() {}
	
	
}
