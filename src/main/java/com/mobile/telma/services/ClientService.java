package com.mobile.telma.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobile.telma.domains.Action;
import com.mobile.telma.domains.Client;
import com.mobile.telma.domains.Appel;
import com.mobile.telma.exceptions.EtAuthException;
import com.mobile.telma.exceptions.EtBadRequestException;
import com.mobile.telma.repositories.ActionRepository;
import com.mobile.telma.repositories.AppelRepository;
import com.mobile.telma.repositories.ClientRepository;

import java.util.List;

@Transactional
@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;
	@Autowired
	ActionRepository actionRepository;
	@Autowired
	AppelRepository appelRepository;
	
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
		    if(action.isAchatCredit()) clientRepository.updateSolde(client);
			actionRepository.insertAction(action);
		}catch(Exception e) {
			throw e;
		}
	}
	
	
	public List<Appel> listeAppel(int idClient)throws EtBadRequestException{
		return appelRepository.getAppelClient(idClient);
	}
	
	public Client getClientById(int id)throws EtBadRequestException{
		return clientRepository.getClientById(id);
	}
	
	
	
}
