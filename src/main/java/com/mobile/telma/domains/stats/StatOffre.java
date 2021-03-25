package com.mobile.telma.domains.stats;

public class StatOffre {
	private int id;
	private String nom;
	private double montant;
	private int nbrAchat;
	private int annee;
	private int mois;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public int getNbrAchat() {
		return nbrAchat;
	}
	public void setNbrAchat(int nbrAchat) {
		this.nbrAchat = nbrAchat;
	}
	public int getAnnee() {
		return annee;
	}
	public void setAnnee(int annee) {
		this.annee = annee;
	}
	public int getMois() {
		return mois;
	}
	public void setMois(int mois) {
		this.mois = mois;
	}
	
	public StatOffre() {}
}
