package com.mobile.telma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobile.telma.domains.Appel;
import com.mobile.telma.repositories.CommunicationRepository;

@Service
public class CommunicationService {

	@Autowired
	private CommunicationRepository comRepository;
	
	public void addAppel(Appel appel, String numero) {
		appel.setEnvoyeur(numero);
		comRepository.insertAppel(appel);
	}
}
