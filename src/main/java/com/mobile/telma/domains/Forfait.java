package com.mobile.telma.domains;

import org.springframework.data.annotation.Id;
import java.util.Date;

public class Forfait {
	@Id
	private String id;
	
	private String nomForfait;
	private double prix;
	private Date dateCreation = new Date();
	private int facebook;
	private int internet;
	private int instagram;
	private int appel;
	private int sms;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNomForfait() {
		return nomForfait;
	}
	public void setNomForfait(String nomForfait) {
		this.nomForfait = nomForfait;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public int getFacebook() {
		return facebook;
	}
	public void setFacebook(int facebook) {
		this.facebook = facebook;
	}
	public int getInternet() {
		return internet;
	}
	public void setInternet(int internet) {
		this.internet = internet;
	}
	public int getInstagram() {
		return instagram;
	}
	public void setInstagram(int instagram) {
		this.instagram = instagram;
	}
	public int getAppel() {
		return appel;
	}
	public void setAppel(int appel) {
		this.appel = appel;
	}
	public int getSms() {
		return sms;
	}
	public void setSms(int sms) {
		this.sms = sms;
	}
	
	public Forfait() {}
	
	
}
