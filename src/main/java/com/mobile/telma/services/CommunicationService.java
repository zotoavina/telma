package com.mobile.telma.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobile.telma.domains.Appel;
import com.mobile.telma.domains.Client;
import com.mobile.telma.domains.Sms;
import com.mobile.telma.exceptions.EtBadRequestException;
import com.mobile.telma.repositories.CommunicationRepository;

@Service
public class CommunicationService {

	@Autowired
	private CommunicationRepository communicationRepository;
	
	public void addAppel(Appel appel, String numero) {
		appel.setEnvoyeur(numero);
		communicationRepository.insertAppel(appel);
	}
	
	
	public List<Appel> listeAppels(Client client) throws EtBadRequestException{
		return communicationRepository.getAppelClient(client.getNumero());
	}
	
	public List<Appel> listeAppelSortant(Client client)throws EtBadRequestException{
		return communicationRepository.getAppelClientSortant(client.getNumero());
	}
	
	public List<Appel> listeAppelEntrant(Client client)throws EtBadRequestException{
		return communicationRepository.getAppelClientEntrant(client.getNumero());
	}
	
	public void supprimerHistoriqueSortant(Client client)throws EtBadRequestException{
		communicationRepository.SupprimerAppelSortant(client.getNumero());
	}
	
	public void supprimerHistoriqueEntrant(Client client)throws EtBadRequestException {
		communicationRepository.SupprimerAppelEntrant(client.getNumero());
	}
	
	public void supprimerHistoriqueAppel(Client client)throws EtBadRequestException {
		communicationRepository.SupprimerAppel(client.getNumero());
	}
	
	public void addSms(Sms sms, Client client) {
		sms.setEnvoyeur( client.getNumero() );
		communicationRepository.insertMessage(sms);
	}
	
	public List<Sms> listeSms(Client client){
		return communicationRepository.getSmsClient(client.getNumero());
	}
	
	public void supprimerSmsEntrant(Client client) {
		communicationRepository.SupprimerSmsEntrant(client.getNumero());
	}
	
	public void supprimerSmsSortant(Client client) {
		communicationRepository.SupprimerSmsSortant(client.getNumero());
	}
	
	public void supprimerSms(Client client) {
		communicationRepository.SupprimerSms(client.getNumero());
	}
	
	
}
