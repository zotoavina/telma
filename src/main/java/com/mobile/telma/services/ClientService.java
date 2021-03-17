package com.mobile.telma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobile.telma.domains.Client;
import com.mobile.telma.exceptions.EtAuthException;
import com.mobile.telma.exceptions.EtBadRequestException;
import com.mobile.telma.repositories.ClientRepository;

@Transactional
@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;
	
	public Client createClient(String nom, String prenom, String numero, String mdp)throws EtBadRequestException{
		int idClient =  clientRepository.insert(nom, prenom, numero, mdp);
		return clientRepository.getClientById(idClient);
	}
	
	public Client identifyClient(String numero, String mdp)throws EtAuthException{
		return clientRepository.getClientByNumero(numero, mdp);
	}
	
}
