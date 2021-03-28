package com.mobile.telma.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobile.telma.domains.Admin;
import com.mobile.telma.domains.Client;
import com.mobile.telma.domains.Data;
import com.mobile.telma.domains.Forfait;
import com.mobile.telma.domains.Offre;
import com.mobile.telma.domains.stats.StatForfait;
import com.mobile.telma.domains.stats.Stat;
import com.mobile.telma.domains.Action;
import com.mobile.telma.domains.ActionClient;
import com.mobile.telma.exceptions.EtAuthException;
import com.mobile.telma.repositories.ActionClientRepository;
import com.mobile.telma.repositories.ActionRepository;
import com.mobile.telma.repositories.AdminRepository;
import com.mobile.telma.repositories.ClientRepository;
import com.mobile.telma.repositories.DataRepository;
import com.mobile.telma.repositories.ForfaitRepository;
import com.mobile.telma.repositories.OffreRepository;
import com.mobile.telma.repositories.StatRepository;

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
	
	@Autowired
	ForfaitRepository forfaitRepository;
	
	@Autowired
	DataRepository dataRepository;
	
	@Autowired
	StatRepository statRepository;
	
	public Admin getByEmailAndMdp(String email, String mdp) throws EtAuthException{
		return adminRepository.findAdminByEmailAndMdp(email, mdp);
	}
	
	
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
	
	
	// -------------------------------------- Gestion Offre et forfaits
	public List<Offre> getListOffres(){
		return offreRepository.findAll();
	}
	
	public Offre getOffre(int idOffre) {
		return offreRepository.findById(idOffre); 
	}
	
	public void updateOffre(Offre offre) {
		offreRepository.updateOffre(offre);
	}
	
	public Offre addOffre(Offre offre) {
		int idOffre = offreRepository.insertOffre(offre);
		return offreRepository.findById(idOffre);
	}
	
	public Forfait addForfait(Forfait forfait) {
		int idForfait = forfaitRepository.createForfait(forfait);
		return forfaitRepository.getForfaitBId(idForfait);
	}
	
	public List<Forfait> getOffreForfaits(int idOffre){
		return offreRepository.getForfaits(idOffre);
	}
	
	
	public List<Data> getDatas(){
		return dataRepository.findDatas();
	}
	

	//--------------------- Stat
	public List<Stat> getStatOffre(int annee, int mois){
		return statRepository.getStatOffre(annee, mois);
	}
	
	public List<Stat> getStatForfait(int idOffre, int annee, int mois){
		return statRepository.getStatForfait(idOffre, annee, mois);
	}
	
	public List<Stat> getStatConsommation(int annee, int mois){
		return statRepository.getStatConsommationParData(annee, mois);
	}
	
	public List<Stat> getStatConsommationData(int idData, int years){
		return statRepository.getStatConsommationDataParMois(idData, years);
	}
	
	
}
