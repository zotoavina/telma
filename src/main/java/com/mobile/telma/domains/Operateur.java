package com.mobile.telma.domains;

import java.sql.Date;

public class Operateur {
	private int idOperateur;
	private String nomOperateur;
	private Date dateCreation;
	private String predicat;
	public static String P_ORANGE = "032";
	public static String P_AIRTEL = "033";
	
	public int getIdOperateur() {
		return idOperateur;
	}
	public void setIdOperateur(int idOperateur) {
		this.idOperateur = idOperateur;
	}
	public String getNomOperateur() {
		return nomOperateur;
	}
	public void setNomOperateur(String nomOperateur) {
		this.nomOperateur = nomOperateur;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public String getPredicat() {
		return predicat;
	}
	public void setPredicat(String predicat) {
		this.predicat = predicat;
	}
	
	public static boolean autres(String numero) {
		return numero.startsWith(P_ORANGE) || numero.startsWith(P_AIRTEL);
	}
	
	
	
}
