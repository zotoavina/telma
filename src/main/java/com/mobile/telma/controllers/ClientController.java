package com.mobile.telma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mobile.telma.Constants;
import com.mobile.telma.domains.Client;
import com.mobile.telma.services.ClientService;
import com.mobile.telma.utils.ResponseMaker;
import com.mobile.telma.domains.Action;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
		Client client = clientService.createClient(nom, prenom, numero, mdp);
		Map<String, Object> map = new HashMap<>();
		map.put("status", HttpStatus.OK.value());
		map.put("message", "Inscription reussi");
		map.put("data", generateToken(client));
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> clientMap){
		String numero  = (String) clientMap.get("numero");
		String mdp = (String) clientMap.get("mdp");
		Client client = clientService.identifyClient(numero, mdp);
		return ResponseMaker.makeResponse(generateToken(client), 200, "Login reussi", HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/action")
	public ResponseEntity<Map<String,Object>> faireAction(HttpServletRequest request,@RequestBody Action action){
		//System.out.println( request.getAttribute("idUtilisateur"));
		int idClient = Integer.parseInt((String) request.getAttribute("idUtilisateur"));
		Client client = clientService.getClientById(idClient);
		action.setIdClient(idClient);
		clientService.faireAction(client, action);
		return ResponseMaker.makeResponse(null, 200, action.getDescription(), HttpStatus.OK);
	}
	
	
	
	private String generateToken(Client client){
		long timestamp = System.currentTimeMillis(); 
		String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SERCRET_KEY)
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp + Constants.VALIDITY)) 
				.claim("idUtilisateur", client.getIdClient() )
				.claim("idOperateur", client.getIdOperateur())
				.claim("nom", client.getNom())
				.claim("prenom", client.getPrenom())
				.compact();
		return token;
	}
	
	
}
