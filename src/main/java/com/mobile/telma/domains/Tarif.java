package com.mobile.telma.domains;

public class Tarif {
	private int idTarif;
	private int idData;
	private double interne;
	private double autres ;
	private double international;
	private String nomData;
	
	
	
	
	public int getIdTarif() {
		return idTarif;
	}
	public void setIdTarif(int idTarif) {
		this.idTarif = idTarif;
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
	
	public Tarif() {}
}
