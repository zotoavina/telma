package com.mobile.telma.domains;

import java.util.Date;

public class ActionClient extends Client{
	private int idaction;
	private int idTypeAction;
	private double montantAction;
	private int etat; 
	private Date dateAction;
	private  String nomTypeAction;
	public int getIdaction() {
		return idaction;
	}
	public void setIdaction(int idaction) {
		this.idaction = idaction;
	}
	public int getIdTypeAction() {
		return idTypeAction;
	}
	public void setIdTypeAction(int idTypeAction) {
		this.idTypeAction = idTypeAction;
	}
	public double getMontantAction() {
		return montantAction;
	}
	public void setMontantAction(double montantAction) {
		this.montantAction = montantAction;
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
	public String getNomTypeAction() {
		return nomTypeAction;
	}
	public void setNomTypeAction(String nomTypeAction) {
		this.nomTypeAction = nomTypeAction;
	}
	public ActionClient() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ActionClient(int idaction, int idTypeAction, double montantAction, int etat, Date dateAction,
			String nomTypeAction) {
		super();
		this.idaction = idaction;
		this.idTypeAction = idTypeAction;
		this.montantAction = montantAction;
		this.etat = etat;
		this.dateAction = dateAction;
		this.nomTypeAction = nomTypeAction;
	}
	
	
	
	
	
	
}
