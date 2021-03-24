package com.mobile.telma.domains;

public class DataActuel {
	private int idForfait;
	private String nomForfait;
	private int idData;
	private double quantite;
	private String nomData;
	
	
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
	
	public DataActuel() {}
	
		
	
	public boolean consommer(Consommation consommation) {
		DetailCons detail = new DetailCons();
		System.out.println(quantite + " Vs " + consommation.dataRestantAConsommer() );
		detail.setModeConsommation( DetailCons.FORFAIT);
		detail.setIdForfait(idForfait);
		if( quantite >= consommation.dataRestantAConsommer() ) {
			detail.setQuantite( consommation.dataRestantAConsommer() );
			consommation.addDetail(detail);
			return false;
		}
		detail.setQuantite(quantite);
		consommation.addDetail(detail);
		return true;
	}

	
	
	
}
