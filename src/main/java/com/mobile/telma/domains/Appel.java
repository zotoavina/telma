package com.mobile.telma.domains;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "appels")
public class Appel extends Communication{
	private double duree;
	
	
	

	public double getDuree() {
		return duree;
	}

	public void setDuree(double duree) {
		this.duree = duree;
	}
	
	public Appel() {
		super();
	}

	public Appel(String id, String envoyeur, String receveur, int activeEnvoyeur, int activeReceveur, Date date, double duree) {
		super(id, envoyeur, receveur, activeEnvoyeur, activeReceveur, date);
		setDuree(duree);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
