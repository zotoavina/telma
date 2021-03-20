package com.mobile.telma.domains;

import java.util.Date;
import java.util.List;

public class ForfaitClient {
	private int idForfaitClient;
	private int idClient;
	private int idForfait;
	private double appel = 0;
	private double sms = 0;
	private double facebook = 0;
	private double instagram = 0;
	private double internet = 0;
	private String modePaiement;
	private Date dateachat = new Date();
	
	
	public int getIdForfaitClient() {
		return idForfaitClient;
	}
	public void setIdForfaitClient(int idForfaitClient) {
		this.idForfaitClient = idForfaitClient;
	}
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	public int getIdForfait() {
		return idForfait;
	}
	public void setIdForfait(int idForfait) {
		this.idForfait = idForfait;
	}
	public double getAppel() {
		return appel;
	}
	public void setAppel(double appel) {
		this.appel = appel;
	}
	public double getFacebook() {
		return facebook;
	}
	public void setFacebook(double facebook) {
		this.facebook = facebook;
	}
	public double getInstagram() {
		return instagram;
	}
	public void setInstagram(double instagram) {
		this.instagram = instagram;
	}
	public double getInternet() {
		return internet;
	}
	public void setInternet(double internet) {
		this.internet = internet;
	}
	public String getModePaiement() {
		return modePaiement;
	}
	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}
	public Date getDateachat() {
		return dateachat;
	}
	public void setDateachat(Date dateachat) {
		this.dateachat = dateachat;
	}
	
	public double getSms() {
		return sms;
	}
	public void setSms(double sms) {
		this.sms = sms;
	}
	public ForfaitClient() {}
	public ForfaitClient(int idForfaitClient, int idClient, int idForfait, double appel, double sms, double facebook,
			double instagram, double internet, String modePaiement, Date dateachat) {
		super();
		this.idForfaitClient = idForfaitClient;
		this.idClient = idClient;
		this.idForfait = idForfait;
		this.appel = appel;
		this.sms = sms;
		this.facebook = facebook;
		this.instagram = instagram;
		this.internet = internet;
		this.modePaiement = modePaiement;
		this.dateachat = dateachat;
	}
	
	private void synchronize(ForfaitData fdata) {
		String nomData = fdata.getNomData();
	    double quantite = fdata.getQuantite();
		if(nomData.compareToIgnoreCase("appel") == 0)
			setAppel(quantite);
		if(nomData.compareToIgnoreCase("sms") == 0)
			setSms(quantite);
		if(nomData.compareToIgnoreCase("facebook") == 0)
			setFacebook(quantite);
		if(nomData.compareToIgnoreCase("instagram") ==0 )
			setInstagram(quantite);
		if(nomData.compareToIgnoreCase("internet") == 0)
			setInternet(quantite);
	}
	
	
	private void synchronize(List<ForfaitData> fdata) {
		for(int i = 0 ; i < fdata.size() ; i++ ) {
			synchronize( fdata .get(i) );
		}
	}
	
	public void synchronize( Forfait forfait) {
		List<ForfaitData> fdatas = forfait.getDatas();
		synchronize( fdatas );
	}
	
}
