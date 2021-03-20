package com.mobile.telma.domains;

import java.util.Date;

import org.springframework.data.annotation.Id;


public class Communication {
	@Id
	String id;
	
	
	private int idClient;
	private String receveur;
	private int activeEnvoyeur = 1;
	private int activeReceveur = 1;
	private Date date = new Date();
	
	
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	public String getReceveur() {
		return receveur;
	}
	public void setReceveur(String receveur) {
		this.receveur = receveur;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	
   public int getActiveEnvoyeur() {
		return activeEnvoyeur;
	}
	public void setActiveEnvoyeur(int activeEnvoyeur) {
		this.activeEnvoyeur = activeEnvoyeur;
	}
	public int getActiveReceveur() {
		return activeReceveur;
	}
	public void setActiveReceveur(int activeReceveur) {
		this.activeReceveur = activeReceveur;
	}
public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public Communication(String id, int idClient, String receveur, int activeEnvoyeur, int activeReceveur, Date date) {
			super();
			this.id = id;
			this.idClient = idClient;
			this.receveur = receveur;
			this.activeEnvoyeur = activeEnvoyeur;
			this.activeReceveur = activeReceveur;
			this.date = date;
	}

		public Communication() {}
		
		
		public static int activer() {
			return 1;
		}
		
		public static int desactiver() {
			return 0;
		}

}
