package com.mobile.telma.domains;

public class DataActuel {
	private int idOffre;
	private String nomOffre;
	private int idData;
	private double quantite;
	private String nomData;
	
	public int getIdOffre() {
		return idOffre;
	}
	public void setIdOffre(int idOffre) {
		this.idOffre = idOffre;
	}
	public String getNomOffre() {
		return nomOffre;
	}
	public void setNomOffre(String nomOffre) {
		this.nomOffre = nomOffre;
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
	
	public DataActuel() {}
	
}
