package com.mobile.telma.domains;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobile.telma.utils.DateUtils;

public class DataClient {
	private int idDataclient;
	private int idClient;
	private int idForfait;
	private int  idData;
	private double quantite;
	private Timestamp dateAchat = new Timestamp(System.currentTimeMillis());
	private int validite;
	private Timestamp expiration;
	
	
	
	


	public int getIdDataclient() {
		return idDataclient;
	}

	public void setIdDataclient(int idDataclient) {
		this.idDataclient = idDataclient;
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

	public Timestamp getDateAchat() {
		return dateAchat;
	}

	public void setDateAchat(Timestamp dateAchat) {
		this.dateAchat = dateAchat;
	}

	public int getValidite() {
		return validite;
	}

	public void setValidite(int validite) {
		this.validite = validite;
	}

	public Timestamp getExpiration() {
		return expiration;
	}

	public void setExpiration(Timestamp expiration) {
		this.expiration = expiration;
	}

	public DataClient() {}
	
	public static List<DataClient> dataFromForfaitClient(Forfait forfait, Client client, Timestamp achat){
		List<DataClient> data = new ArrayList<>( forfait.getDatas().size());
		for(int i = 0 ; i < forfait.getDatas().size(); i++) {
			DataClient tmp = new DataClient();
			tmp.synchronize(client, forfait, forfait.getDatas().get(i));
			tmp.setDateAchat(achat);
			tmp.setExpiration( DateUtils.addJour(achat, tmp.getValidite() ));
			data.add(tmp);
		}
		return data;
	}
	
	private void synchronize(Forfait forfait) {
		setIdForfait(forfait.getIdForfait());
		setValidite(forfait.getValidite());
	}
	
	public void synchronize(ForfaitData fd) {
		setIdData(fd.getIdData());
		setQuantite(fd.getQuantite());
	}
	
	public void synchronize(Client client) {
		setIdClient(client.getIdClient());
	}
	
	public void synchronize(Client client, Forfait forfait, ForfaitData fd) {
		synchronize(client); synchronize(forfait); synchronize(fd);
	}
	
	
}
