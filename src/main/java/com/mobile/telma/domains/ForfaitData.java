package com.mobile.telma.domains;

public class ForfaitData {
	private int idFdata;
	private int idForfait;
	private int idData;
	private double quantite;
	private String nomData;
	
	public int getIdFdata() {
		return idFdata;
	}
	public void setIdFdata(int idFdata) {
		this.idFdata = idFdata;
	}
	public int getIdForfait() {
		return idForfait;
	}
	public void setIdForfait(int idForfait) {
		this.idForfait = idForfait;
	}
	public String getNomData() {
		return nomData;
	}
	public void setNomData(String nomData) {
		this.nomData = nomData;
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
	
	
	public ForfaitData() {}
	public ForfaitData(int idFdata, int idForfait, int idData, double quantite, String nomData) {
		super();
		this.idFdata = idFdata;
		this.idForfait = idForfait;
		this.idData = idData;
		this.quantite = quantite;
		this.nomData = nomData;
	}
	
	
	
	
}
