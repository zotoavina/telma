package com.mobile.telma.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobile.telma.domains.Admin;
import com.mobile.telma.domains.Client;
import com.mobile.telma.domains.Forfait;
import com.mobile.telma.domains.Offre;
import com.mobile.telma.domains.Action;
import com.mobile.telma.domains.ActionClient;
import com.mobile.telma.exceptions.EtAuthException;
import com.mobile.telma.exceptions.EtBadRequestException;
import com.mobile.telma.repositories.ActionClientRepository;
import com.mobile.telma.repositories.ActionRepository;
import com.mobile.telma.repositories.AdminRepository;
import com.mobile.telma.repositories.ClientRepository;
import com.mobile.telma.repositories.OffreRepository;

@Transactional
@Service
public class AdminService {

	@Autowired 
	AdminRepository adminRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	ActionClientRepository actionClientRepository;
	
	@Autowired 
	ActionRepository actionRepository;
	
	@Autowired
	OffreRepository offreRepository;
	
	public Admin getByEmailAndMdp(String email, String mdp) throws EtAuthException{
		return adminRepository.findAdminByEmailAndMdp(email, mdp);
	}
	
	//public Admin validationAction(int idClient, int idAction)throws EtBadRequestException{
		
	//}
	
	public List<ActionClient> getActionNonValide()throws EtAuthException{
		return actionClientRepository.findAllNonValide();
	}
	
	public Action validerAction(int idAction) throws EtAuthException{
		Action action =  actionRepository.findById(idAction);
		action.valider();
		Client client = clientRepository.getClientById(action.getIdClient());
		client.faireAction(action);
		clientRepository.updateSolde(client);
		actionRepository.update(action);
	    return action;
	}
	
	
	// -------------------------------------- Gestion Offre
	public List<Offre> getListOffres(){
		return offreRepository.findAll();
	}
	
	public Offre getOffre(String idOffre) {
		return offreRepository.findById(idOffre); 
	}
	
	public void insertOffre(Offre offre) {
		offreRepository.insert(offre);
	}
	
	public Offre addForfaitToOffre(String idOffre, Forfait forfait) {
		return offreRepository.updateAddForfaits(idOffre, forfait);
	}
	
}
