package com.mobile.telma.domains;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Consommation {
	 private int idConsommation;
	 private int idClient;
	 private int idData;
	 private double quantite;
	 private Timestamp dateConsommation;
	 private List<DetailCons> details = new ArrayList<>(3);
	 
	public int getIdConsommation() {
		return idConsommation;
	}
	public void setIdConsommation(int idConsommation) {
		this.idConsommation = idConsommation;
	}
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
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
	
	 
	 public Timestamp getDateConsommation() {
		return dateConsommation;
	}
	public void setDateConsommation(Timestamp dateConsommation) {
		this.dateConsommation = dateConsommation;
	}
	public List<DetailCons> getDetails() {
		return details;
	}
	 
	public void setDetails(List<DetailCons> details) {
		this.details = details;
	}
	
	public void addDetail(DetailCons detail) {
		detail.setIdConsommation(idConsommation);
		details.add(detail);
	}
	
	public double sommeDataDetail() {
		double somme = 0;
		for(int i = 0; i < details.size(); i++) {
			somme += details.get(i).getQuantite() * details.get(i).getTarif();
		}
		return somme;
	}
	
	
	public double dataRestantAConsommer() {
		return quantite - sommeDataDetail();
	}
	
	public Consommation() {}
	
	public Consommation(int idClient,int idData , double quantite,  Timestamp da) {
		setIdClient(idClient);
		setDateConsommation(da);
		setQuantite(quantite);
		setIdData(idData);
	}
	
	
	
}
