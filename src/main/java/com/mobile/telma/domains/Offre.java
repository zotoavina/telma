package com.mobile.telma.domains;

import java.util.Date;


public class Offre {

	private int idOffre;
	private String nomOffre;
	private double interne;
	private double autres;
	private double international;
	private Date dateCreation;
	private int active = 1;
	private String description;
	
	public final static int ACTIVE = 1;
	public final static int INACTIVE = 0;
	
	
	
	
	


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




	public double getInterne() {
		return interne;
	}



	public void setInterne(double interne) {
		this.interne = interne;
	}



	public double getAutres() {
		return autres;
	}



	public void setAutres(double autres) {
		this.autres = autres;
	}



	public double getInternational() {
		return international;
	}



	public void setInternational(double international) {
		this.international = international;
	}



	public Date getDateCreation() {
		return dateCreation;
	}



	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}



	




	public int getActive() {
		return active;
	}



	public void setActive(int active) {
		this.active = active;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}

	public Offre() {}


	public Offre(int idOffre, String nomOffre,  double interne, double autres, double international,
			Date dateCreation, int active, String description) {
		super();
		this.idOffre = idOffre;
		this.nomOffre = nomOffre;
		this.interne = interne;
		this.autres = autres;
		this.international = international;
		this.dateCreation = dateCreation;
		this.active = active;
		this.description = description;
	}
		
	
	@Override
	public String toString() {
		return idOffre + " " + nomOffre + " " + interne + " " + autres + " " + international + " " + active; 
	}
	
	
	
	
	

}
