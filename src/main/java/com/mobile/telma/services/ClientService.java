package com.mobile.telma.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobile.telma.domains.AchatForfait;
import com.mobile.telma.domains.Action;
import com.mobile.telma.domains.Client;
import com.mobile.telma.domains.Consommation;
import com.mobile.telma.domains.DataClient;
import com.mobile.telma.domains.Forfait;
import com.mobile.telma.domains.Sms;
import com.mobile.telma.domains.Appel;
import com.mobile.telma.exceptions.EtAuthException;
import com.mobile.telma.exceptions.EtBadRequestException;
import com.mobile.telma.repositories.ActionRepository;
import com.mobile.telma.repositories.CommunicationRepository;
import com.mobile.telma.repositories.ConsommationRepository;
import com.mobile.telma.repositories.DataClientRepository;
import com.mobile.telma.repositories.ClientRepository;
import com.mobile.telma.repositories.ForfaitRepository;
import com.mobile.telma.utils.DateUtils;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;
	@Autowired
	ActionRepository actionRepository;
	@Autowired
	CommunicationRepository communicationRepository;
	@Autowired
	ForfaitRepository forfaitRepository;
	@Autowired
	DataClientRepository dataClientRepository;
	@Autowired 
	ConsommationRepository consommationRepository;
	
	public Client createClient(String nom, String prenom, String numero, String mdp)throws EtBadRequestException{
		int idClient =  clientRepository.insert(nom, prenom, numero, mdp);
		return clientRepository.getClientById(idClient);
	}
	
	public Client identifyClient(String numero, String mdp)throws EtAuthException{
		return clientRepository.getClientByNumero(numero, mdp);
	}
	
	
	public void faireAction(Client client , Action action)throws EtBadRequestException{
		try {
		    client.faireAction(action);
		    if(action.isAchatCredit()) clientRepository.updateSoldeEtCredit(client);
			actionRepository.insertAction(action);
		}catch(Exception e) {
			throw e;
		}
	}
	

	public Client getClientById(int id)throws EtBadRequestException{
		return clientRepository.getClientById(id);
	}
	
	public Client getClientByNumero(String numero)throws EtBadRequestException{
		return clientRepository.getClientByNumero(numero);
	}
	
	public void setDataActuel(Client client, int idData, java.sql.Date date) {
		System.out.println("data : " +idData);  
		client.setDataActuel( dataClientRepository.getDataActuel(client.getIdClient(), idData, date));
	}
	
	public void setDataActuel(Client client, java.sql.Date date) {
		client.setDataActuel( dataClientRepository.getDatasActuel(client.getIdClient(), date));
	}
	
	public void acheterForfait(int idClient, int idForfait,String mode, Date achat) {
		AchatForfait af = new AchatForfait();
		java.sql.Date da = DateUtils.utilToSql(achat);
		Client client = clientRepository.getClientById(idClient);
		Forfait forfait = forfaitRepository.getForfaitBId(idForfait);
		af.synchronize(client, forfait);
		af.setModePaiement(mode);
		af.setDateAchat(da);
		client.achatForfait(forfait, mode);
		forfaitRepository.insertAchatForfait(af);
		clientRepository.updateSoldeEtCredit(client);
		List<DataClient> datas = DataClient.dataFromForfaitClient(forfait, client, da );
		dataClientRepository.insertDataClient(datas); 
	}
	
	public Consommation consommerData(Consommation consommation, String numero) {
		//java.sql.Date da = DateUtils.utilToSql(date);
		Client client = clientRepository.getClientById(consommation.getIdClient());
		setDataActuel(client, consommation.getIdData(), consommation.getDateConsommation());
		client.consommerData(consommation, numero);
//		if( client.consommerData(consommation, numero) ) clientRepository.updateSoldeEtCredit(client);
//		consommationRepository.insertionConsommation(consommation);
		return consommation;
	}

	
	//-------------------------------------------  Appel et Sms
	
	public void addAppel(Appel appel, int idClient) {
		Client client= clientRepository.getClientById(idClient);
		appel.setEnvoyeur(client.getNumero());
		communicationRepository.insertAppel(appel);
	}
	
	public List<Appel> listeAppels(int idClient) throws EtBadRequestException{
		Client client = clientRepository.getClientById(idClient);
		return communicationRepository.getAppelClient(client.getNumero());
	}
	
	public List<Appel> listeAppelSortant(int idClient)throws EtBadRequestException{
		Client client = clientRepository.getClientById(idClient);
		return communicationRepository.getAppelClientSortant(client.getNumero());
	}
	
	public List<Appel> listeAppelEntrant(int idClient)throws EtBadRequestException{
		Client client = clientRepository.getClientById(idClient);
		return communicationRepository.getAppelClientEntrant(client.getNumero());
	}
	
	public void supprimerHistoriqueSortant(int idClient)throws EtBadRequestException{
		Client client = clientRepository.getClientById(idClient);
		communicationRepository.SupprimerAppelSortant(client.getNumero());
	}
	
	public void supprimerHistoriqueEntrant(int idClient)throws EtBadRequestException {
		Client client = clientRepository.getClientById(idClient);
		communicationRepository.SupprimerAppelEntrant(client.getNumero());
	}
	
	public void supprimerHistoriqueAppel(int idClient)throws EtBadRequestException {
		Client client = clientRepository.getClientById(idClient);
		communicationRepository.SupprimerAppel(client.getNumero());
	}
	
	public void addSms(Sms sms, int idClient) {
		Client client = clientRepository.getClientById(idClient);
		sms.setEnvoyeur( client.getNumero() );
		communicationRepository.insertMessage(sms);
	}
	
	public List<Sms> listeSms(int idClient){
		Client client = clientRepository.getClientById(idClient);
		return communicationRepository.getSmsClient(client.getNumero());
	}
	
	public void supprimerSmsEntrant(int idClient) {
		Client client = clientRepository.getClientById(idClient);
		communicationRepository.SupprimerSmsEntrant(client.getNumero());
	}
	
	public void supprimerSmsSortant(int idClient) {
		Client client = clientRepository.getClientById(idClient);
		communicationRepository.SupprimerSmsSortant(client.getNumero());
	}
	
	public void supprimerSms(int idClient) {
		Client client = clientRepository.getClientById(idClient);
		communicationRepository.SupprimerSms(client.getNumero());
	}
	
	
}
