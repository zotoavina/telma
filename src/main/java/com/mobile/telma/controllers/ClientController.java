package com.mobile.telma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

import com.mobile.telma.services.ClientService;

@RequestMapping("/api/clients")
@Controller
public class ClientController {

	@Autowired
	ClientService clientService;
	
	@PostMapping("")
	public ResponseEntity<Map<String,Object>> inscription(@RequestBody Map<String, Object> clientMap){
		String nom = (String) clientMap.get("nom");
		String prenom = (String) clientMap.get("prenom");
		String numero = (String) clientMap.get("numero");
		String mdp = (String) clientMap.get("mdp");
		int idClient = clientService.createClient(nom, prenom, numero, mdp);
		Map<String, Object> map = new HashMap<>();
		map.put("status", HttpStatus.OK.value());
		map.put("message", "Inscription reussi");
		map.put("data", null);
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}
	
	
}
