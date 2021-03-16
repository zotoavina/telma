package com.mobile.telma.domains;

public class Admin {
	private int idAdmin;
	private int idoperateur;
	private String nom;
	private String prenom; 
	private String email;
	private String mdp;
	public int getIdAdmin() {
		return idAdmin;
	}
	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}
	public int getIdoperateur() {
		return idoperateur;
	}
	public void setIdoperateur(int idoperateur) {
		this.idoperateur = idoperateur;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	public Admin() {}
	public Admin(int idAdmin, int idoperateur, String nom, String prenom, String email, String mdp) {
		super();
		this.idAdmin = idAdmin;
		this.idoperateur = idoperateur;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.mdp = mdp;
	}
	
	@Override
	public String toString(){
		return idAdmin + " " + idoperateur + " " + nom + " " + prenom + " " + email + " " + mdp; 
	}
	
}
