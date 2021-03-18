package com.mobile.telma.domains;

import java.util.Date;

import com.mobile.telma.exceptions.EtBadRequestException;

public class Action {
	private int idAction;
	private int idTypeAction;
	private int idClient;
	private double montant;
	private int etat = 0;
	private Date dateAction;
	
	public int getIdAction() {
		return idAction;
	}
	public void setIdAction(int idAction) {
		this.idAction = idAction;
	}
	public int getIdTypeAction() {
		return idTypeAction;
	}
	public void setIdTypeAction(int idTypeAction) {
		this.idTypeAction = idTypeAction;
	}
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	public double getMontant() throws EtBadRequestException{
		if(montant < 0) throw new EtBadRequestException("La valeur du solde doit etre positive");
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public int getEtat() {
		return etat;
	}
	public void setEtat(int etat) {
		this.etat = etat;
	}
	
	
	public Date getDateAction() {
		return dateAction;
	}
	public void setDateAction(Date dateAction) {
		this.dateAction = dateAction;
	}
	public Action() {}
	
	
	
	public Action(int idAction, int idTypeAction, int idClient, double montant, int etat, Date dateAction) {
		super();
		this.idAction = idAction;
		this.idTypeAction = idTypeAction;
		this.idClient = idClient;
		this.montant = montant;
		this.etat = etat;
		this.dateAction = dateAction;
	}
	public boolean isDepot() {
		return idTypeAction == 1;
	}
	
	public boolean isRetrait() {
		return idTypeAction == 2;
	}
	
	private String describe() {
		if(isDepot()) return "depot";
		if(isRetrait()) return "retrait";
		return "";
	}
	
	public String getDescription() {
		return describe() + " de " +  montant + " reussi ";
	}
}
