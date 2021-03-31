package com.mobile.telma.domains;

import java.sql.Date;

public class DetailCons {
	private int idDetails;
	private int idConsommation;
	private int idDataClient;
	private double tarif;
	private double quantite = 0;
	private String modeConsommation;
	
	static final String FORFAIT = "forfait";
	static final String CREDIT = "credit";
	
	
	
	
	
	public double getTarif() {
		return tarif;
	}
	public void setTarif(double tarif) {
		this.tarif = tarif;
	}
	public int getIdDataClient() {
		return idDataClient;
	}
	public void setIdDataClient(int idDataClient) {
		this.idDataClient = idDataClient;
	}
	public int getIdDetails() {
		return idDetails;
	}
	public void setIdDetails(int idDetails) {
		this.idDetails = idDetails;
	}
	public int getIdConsommation() {
		return idConsommation;
	}
	public void setIdConsommation(int idConsommation) {
		this.idConsommation = idConsommation;
	}
	public double getQuantite() {
		return quantite;
	}
	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}
	public String getModeConsommation() {
		return modeConsommation;
	}
	public void setModeConsommation(String modeConsommation) {
		this.modeConsommation = modeConsommation;
	}
	public DetailCons() {}
	
	public boolean parCredit() {
		return idDataClient == 0;
	}
}
