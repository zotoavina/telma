package com.mobile.telma.domains;
import java.util.Date;

public class Appel {
	private int idappel;
	private int idclient;
	private String numero;
	private int duree;
	private Date dateappel;
	public int getIdappel() {
		return idappel;
	}
	public void setIdappel(int idappel) {
		this.idappel = idappel;
	}
	public int getIdclient() {
		return idclient;
	}
	public void setIdclient(int idclient) {
		this.idclient = idclient;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public int getDuree() {
		return duree;
	}
	public void setDuree(int duree) {
		this.duree = duree;
	}
	public Date getDateappel() {
		return dateappel;
	}
	public void setDateappel(Date dateappel) {
		this.dateappel = dateappel;
	}
	
	public Appel() {}
	
	public Appel(int idappel, int idclient, String numero, int duree, Date dateappel) {
		super();
		this.idappel = idappel;
		this.idclient = idclient;
		this.numero = numero;
		this.duree = duree;
		this.dateappel = dateappel;
	}
	
	
}
