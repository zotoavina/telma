package com.mobile.telma.domains;

import java.sql.Timestamp;

public class AchatForfait {
	private int idAchat;
	private int idClient;
	private int idForfait;
	private String modePaiement;
	private Timestamp dateAchat = new Timestamp(System.currentTimeMillis());
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
	
	
	
	
	public Timestamp getDateAchat() {
		return dateAchat;
	}
	public void setDateAchat(Timestamp dateAchat) {
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
