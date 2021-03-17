package com.mobile.telma.domains;
import java.util.Date;

public class Client {
	private int idClient;
	private int  idOperateur;
	private String nom;
	private String prenom;
	private String mdp;
	private String numero;
	private double solde;
	private double credit;
	private Date dateAdhesion;
	
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	public int getIdOperateur() {
		return idOperateur;
	}
	public void setIdOperateur(int idOperateur) {
		this.idOperateur = idOperateur;
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
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public double getSolde() {
		return solde;
	}
	public void setSolde(double solde) {
		this.solde = solde;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public Date getDateAdhesion() {
		return dateAdhesion;
	}
	public void setDateAdhesion(Date dateAdhesion) {
		this.dateAdhesion = dateAdhesion;
	}
	
	
	
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Client() {}
	public Client(int idClient, int idOperateur, String nom, String prenom, String numero, String mdp, double solde, double credit,
			Date dateAdhesion) {
		super();
		this.idClient = idClient;
		this.idOperateur = idOperateur;
		this.nom = nom;
		this.prenom = prenom;
		this.numero = numero;
		this.mdp = mdp;
		this.solde = solde;
		this.credit = credit;
		this.dateAdhesion = dateAdhesion;
	}
	
	public boolean validPassword(String password) {
		return this.mdp.compareTo(password) == 0;
	}
	
	
	
	
	
	

}
