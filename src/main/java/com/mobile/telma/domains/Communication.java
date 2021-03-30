package com.mobile.telma.domains;

import java.sql.Date;

import org.springframework.data.annotation.Id;


/**
 * @author win
 *
 */
public class Communication {
	@Id
	String id;

	private String envoyeur;
	private String receveur;
	private int activeEnvoyeur = 1;
	private int activeReceveur = 1;
	private Date date = new Date( System.currentTimeMillis() );
	
	
	
	public String getEnvoyeur() {
		return envoyeur;
	}
	public void setEnvoyeur(String envoyeur) {
		this.envoyeur = envoyeur;
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
	
	
	

		public Communication(String id, String envoyeur, String receveur, int activeEnvoyeur, int activeReceveur,
			Date date) {
		super();
		this.id = id;
		this.envoyeur = envoyeur;
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
