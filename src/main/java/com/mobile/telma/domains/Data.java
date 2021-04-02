package com.mobile.telma.domains;

import java.sql.Timestamp;

public class Data {
	private int idData;
	private String nomData;
	private int active = 1;
	private Timestamp dateCreation = new Timestamp(System.currentTimeMillis());
	
	public static final int APPEL = 1;

	
	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getIdData() {
		return idData;
	}

	public void setIdData(int idData) {
		this.idData = idData;
	}

	public String getNomData() {
		return nomData;
	}

	public void setNomData(String nomData) {
		this.nomData = nomData;
	}

	
	
	public Timestamp getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Timestamp dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Data() {}

	public static String appel(){
	    return "Appel";
	}
	
	public static String sms() {
		return "Sms";
	}
	
	public static String facebook() {
		return "Facebook";
	}
	
	public static String instagram() {
		return "Instagram";
	}
	
	public static String internet() {
		return "Internet";
		
	}
	
	public static boolean estAppel(String nom) {
		return nom.compareToIgnoreCase(appel()) == 0 ;
	}
	
	public static boolean estAppel(int id) {
		return id == 1;
	}
	
	public static boolean estSms(String nom) {
		return nom.compareToIgnoreCase(sms()) == 0;
	}
	
	public static boolean estSms(int id) {
		return id == 2;
	}
	
	public static boolean estFacebook(String nom) {
		return nom.compareToIgnoreCase(facebook()) == 0;
	}
	
	public static boolean estInstagram(String nom) {
		return nom.compareToIgnoreCase(instagram()) == 0;
	}
	
	public static boolean estInternet(String nom) {
		return nom.compareToIgnoreCase(internet()) == 0;
	}
	
	public static int active() {
		return 1;
	}
	
	public static int inactive() {
		return 0;
	}
	
}
