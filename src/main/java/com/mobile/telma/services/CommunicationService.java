package com.mobile.telma.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobile.telma.domains.Appel;
import com.mobile.telma.domains.Client;
import com.mobile.telma.exceptions.EtBadRequestException;
import com.mobile.telma.repositories.CommunicationRepository;

@Service
public class CommunicationService {

	@Autowired
	private CommunicationRepository comRepository;
	
	public void addAppel(Appel appel, String numero) {
		appel.setEnvoyeur(numero);
		comRepository.insertAppel(appel);
	}
	
	
	public List<Appel> listeAppels(Client client) throws EtBadRequestException{
		return comRepository.getAppelClient(client.getNumero());
	}
	
}
