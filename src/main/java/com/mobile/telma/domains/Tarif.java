package com.mobile.telma.domains;

public class Tarif {

	private double telma ;
	private double autres ;
	private double international;
	
	public double getTelma() {
		return telma;
	}
	public void setTelma(double telma) {
		this.telma = telma;
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
