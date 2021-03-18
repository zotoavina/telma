package com.mobile.telma.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobile.telma.domains.Admin;
import com.mobile.telma.domains.ActionClient;
import com.mobile.telma.exceptions.EtAuthException;
import com.mobile.telma.exceptions.EtBadRequestException;
import com.mobile.telma.repositories.ActionClientRepository;
import com.mobile.telma.repositories.AdminRepository;
import com.mobile.telma.repositories.ClientRepository;

@Transactional
@Service
public class AdminService {

	@Autowired 
	AdminRepository adminRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	ActionClientRepository actionClientRepository;
	
	public Admin getByEmailAndMdp(String email, String mdp) throws EtAuthException{
		return adminRepository.findAdminByEmailAndMdp(email, mdp);
	}
	
	//public Admin validationAction(int idClient, int idAction)throws EtBadRequestException{
		
	//}
	
	public List<ActionClient> getActionNonValide()throws EtAuthException{
		return actionClientRepository.findAllNonValide();
	}
}
