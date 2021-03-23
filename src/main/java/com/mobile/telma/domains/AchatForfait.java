package com.mobile.telma.domains;

import java.sql.Date;

public class AchatForfait {
	private int idAchat;
	private int idClient;
	private int idForfait;
	private String modePaiement;
	private Date dateAchat = new Date(System.currentTimeMillis());
	public int getIdAchat() {
		return idAchat;
	}
	public void setIdAchat(int idAchat) {
		this.idAchat = idAchat;
	}
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	public int getIdForfait() {
		return idForfait;
	}
	public void setIdForfait(int idForfait) {
		this.idForfait = idForfait;
	}
	public Date getDateAchat() {
		return dateAchat;
	}
	public void setDateAchat(Date dateAchat) {
		this.dateAchat = dateAchat;
	}
	
	
	
	public String getModePaiement() {
		return modePaiement;
	}
	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}
	public AchatForfait() {}
	
	public void synchronize(Client client, Forfait forfait) {
		setIdClient(client.getIdClient());
		setIdForfait(forfait.getIdForfait());
	}
}
