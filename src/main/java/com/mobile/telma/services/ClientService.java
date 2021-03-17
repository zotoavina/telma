package com.mobile.telma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobile.telma.exceptions.EtBadRequestException;
import com.mobile.telma.repositories.ClientRepository;

@Transactional
@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;
	
	public int createClient(String nom, String prenom, String numero, String mdp)throws EtBadRequestException{
		return clientRepository.insert(nom, prenom, numero, mdp);
	}
	
}
