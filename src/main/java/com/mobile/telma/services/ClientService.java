package com.mobile.telma.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobile.telma.domains.Action;
import com.mobile.telma.domains.Client;
import com.mobile.telma.domains.Forfait;
import com.mobile.telma.domains.ForfaitClient;
import com.mobile.telma.domains.Sms;
import com.mobile.telma.domains.Appel;
import com.mobile.telma.exceptions.EtAuthException;
import com.mobile.telma.exceptions.EtBadRequestException;
import com.mobile.telma.repositories.ActionRepository;
import com.mobile.telma.repositories.CommunicationRepository;
import com.mobile.telma.repositories.ClientRepository;
import com.mobile.telma.repositories.ForfaitClientRepository;
import com.mobile.telma.repositories.ForfaitRepository;

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
	ForfaitClientRepository forfaitClientRepository;
	@Autowired
	ForfaitRepository forfaitRepository;
	
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
	
	
	public void acheterForfait(int idClient, int idForfait,String mode) {
		ForfaitClient fc = new ForfaitClient();
		fc.setIdClient(idClient);
		fc.setIdForfait(idForfait);
		fc.setModePaiement(mode);
		Client client = clientRepository.getClientById(idClient);
		Forfait forfait = forfaitRepository.getForfaitBId(idForfait);
		client.achatForfait(forfait, mode);
		fc.synchronize(forfait);
		forfaitClientRepository.insert(fc);
		clientRepository.updateSoldeEtCredit(client);
	}

	
	//-------------------------------------------  Appel et Sms
	
	public void addAppel(Appel appel) {
		communicationRepository.insertAppel(appel);
	}
	
	public List<Appel> listeAppels(int idClient) throws EtBadRequestException{
		Client client = clientRepository.getClientById(idClient);
		return communicationRepository.getAppelClient(client.getIdClient(), client.getNumero());
	}
	
	public List<Appel> listeAppelSortant(int idClient)throws EtBadRequestException{
		return communicationRepository.getAppelClientSortant(idClient);
	}
	
	public List<Appel> listeAppelEntrant(int idClient)throws EtBadRequestException{
		Client client = clientRepository.getClientById(idClient);
		return communicationRepository.getAppelClientEntrant(client.getNumero());
	}
	
	public void supprimerHistoriqueSortant(int idClient)throws EtBadRequestException{
		communicationRepository.SupprimerAppelSortant(idClient);
	}
	
	public void supprimerHistoriqueEntrant(int idClient)throws EtBadRequestException {
		Client client = clientRepository.getClientById(idClient);
		communicationRepository.SupprimerAppelEntrant(client.getNumero());
	}
	
	public void supprimerHistoriqueAppel(int idClient)throws EtBadRequestException {
		Client client = clientRepository.getClientById(idClient);
		communicationRepository.SupprimerAppel(client.getIdClient(), client.getNumero());
	}
	
	public void addSms(Sms sms) {
		communicationRepository.insertMessage(sms);
	}
	
	
}
