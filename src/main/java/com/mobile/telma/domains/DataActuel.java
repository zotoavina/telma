package com.mobile.telma.domains;

import java.sql.Timestamp;

public class DataActuel {
	private int idDataClient;
	private int idForfait;
	private String nomForfait;
	private int idData;
	private double quantite;
	private String nomData;
	private double interne;
	private double autres;
	private double international;
	private Timestamp dateExpiration;
	
	
	
	
	
	
	public Timestamp getDateExpiration() {
		return dateExpiration;
	}
	public void setDateExpiration(Timestamp dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	
	public int getIdDataClient() {
		return idDataClient;
	}
	public void setIdDataClient(int idDataClient) {
		this.idDataClient = idDataClient;
	}
	public int getIdForfait() {
		return idForfait;
	}
	public void setIdForfait(int idForfait) {
		this.idForfait = idForfait;
	}
	
	public String getNomForfait() {
		return nomForfait;
	}
	public void setNomForfait(String nomForfait) {
		this.nomForfait = nomForfait;
	}
	public int getIdData() {
		return idData;
	}
	public void setIdData(int idData) {
		this.idData = idData;
	}
	public double getQuantite() {
		return quantite;
	}
	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}
	public String getNomData() {
		return nomData;
	}
	public void setNomData(String nomData) {
		this.nomData = nomData;
	}
	public double getInterne() {
		return interne;
	}
	public void setInterne(double interne) {
		this.interne = interne;
	}
	public double getAutres() {
		return autres;
	}
	public void setAutres(double autres) {
		this.autres = autres;
	}
	public double getInternational() {
		return international;
	}
	public void setInternational(double international) {
		this.international = international;
	}
	
	
	public DataActuel() {}
	
	
//	
//	public boolean consommer(Consommation consommation) {
//		DetailCons detail = new DetailCons();
//		System.out.println(quantite + " Vs " + consommation.dataRestantAConsommer() );
//		detail.setModeConsommation( DetailCons.FORFAIT);
//		detail.setIdDataClient(idDataClient);
//		if( quantite >= consommation.dataRestantAConsommer() ) {
//			detail.setQuantite( consommation.dataRestantAConsommer() );
//			consommation.addDetail(detail);
//			return false;
//		}
//		detail.setQuantite(quantite);
//		consommation.addDetail(detail);
//		return true;
//	}

	public boolean consommer(Consommation consommation,double tarif) {
		if(quantite == 0) return true;
		DetailCons detail = new DetailCons();
		double comp = quantite / tarif; 
		detail.setModeConsommation( DetailCons.FORFAIT);
		detail.setIdDataClient(idDataClient);
		detail.setTarif(tarif);
		if( comp >= consommation.dataRestantAConsommer() ) {
			detail.setQuantite( consommation.dataRestantAConsommer() * tarif );
			consommation.addDetail(detail);
			return false;
		}
		detail.setQuantite( quantite );
		consommation.addDetail(detail);
		return true;
	}
	
	
	
}
