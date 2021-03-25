package com.mobile.telma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mobile.telma.Constants;
import com.mobile.telma.domains.Client;
import com.mobile.telma.domains.Consommation;
import com.mobile.telma.domains.Sms;
import com.mobile.telma.filter.GestionToken;
import com.mobile.telma.services.ClientService;
import com.mobile.telma.utils.DateUtils;
import com.mobile.telma.utils.ResponseMaker;
import com.mobile.telma.domains.Action;
import com.mobile.telma.domains.Appel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin
@RestController
@RequestMapping("/api/clients")
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
	
	@GetMapping("")
	public ResponseEntity<Map<String, Object>> getClientById(HttpServletRequest request)throws Exception {
		int idClient =  Integer.parseInt( GestionToken.gererTokenClient(request));
		return ResponseMaker.makeResponse(clientService.getClientById(idClient), 200, "Selection du client reussi",  HttpStatus.OK);
	}
		
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> clientMap){
		String numero  = (String) clientMap.get("numero");
		String mdp = (String) clientMap.get("mdp");
		Client client = clientService.identifyClient(numero, mdp);
		return ResponseMaker.makeResponse(generateToken(client), 200, "Login reussi", HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/action")
	public ResponseEntity<Map<String,Object>> faireAction(HttpServletRequest request,@RequestBody Action action) throws Exception{
		int idClient = Integer.parseInt( GestionToken.gererTokenClient(request));
		Client client = clientService.getClientById(idClient);
		action.setIdClient(idClient);
		clientService.faireAction(client, action);
		return ResponseMaker.makeResponse(null, 200, action.getDescriptionAttente(), HttpStatus.OK);
	}
	
	@PostMapping("/forfaits/{idForfait}/achats")
	public ResponseEntity<Map<String,Object>> achatForfait(HttpServletRequest request,
			@RequestBody Map<String, Object> map, @PathVariable("idForfait") int idForfait)throws Exception{
		int idClient = Integer.parseInt( GestionToken.gererTokenClient(request));
		String mode = (String) map.get("modepaiement");
		Date achat = DateUtils.parse( (String) map.get("dateachat") );
		clientService.acheterForfait(idClient, idForfait, mode, achat);
		return ResponseMaker.makeResponse(null, 200, "Achat du forfait reussi", HttpStatus.OK);
	}
	
	@PostMapping("/consommer")
	public ResponseEntity<Map<String,Object>> consommerData(HttpServletRequest request,@RequestBody Map<String, Object> map )
			throws Exception{
		int idClient = Integer.parseInt( GestionToken.gererTokenClient(request));
		Date date = DateUtils.parse( (String) map.get("dateconsommation"));
		double quantite = (Double) map.get("quantite");
		int idData = (Integer) map.get("iddata");
		Consommation client= clientService.consommerData(idClient, idData, quantite, date);
		return ResponseMaker.makeResponse(client, 200, "Achat du forfait reussi", HttpStatus.OK);
	}
		
	
	
	//------------------------ Appel et Sms
	
	   ////  APPEL 
	
	
	@PostMapping("/appels")
	public ResponseEntity<Map<String, Object>> appeler(HttpServletRequest request,
			@RequestBody Appel appel)throws Exception{
		int idClient =  Integer.parseInt( GestionToken.gererTokenClient(request));
		clientService.addAppel(appel, idClient);
		return ResponseMaker.makeResponse(null, 200, "Appel effectue",  HttpStatus.OK);
	}	
	
	@GetMapping("/appels")
	public ResponseEntity<Map<String, Object>> appels(HttpServletRequest request)throws Exception{
		int idClient =  Integer.parseInt( GestionToken.gererTokenClient(request));
		return ResponseMaker.makeResponse(clientService.listeAppels(idClient), 200, 
				"Selection des appels (effecutes et recus) terminee",  HttpStatus.OK);
	}	
	
	@PutMapping("/appels")
	public ResponseEntity<Map<String, Object>> supprimerHistoriqueAppel(HttpServletRequest request)throws Exception{
		int idClient =  Integer.parseInt( GestionToken.gererTokenClient(request));
		clientService.supprimerHistoriqueAppel(idClient);
		return ResponseMaker.makeResponse(null, 200, "Appel effectue",  HttpStatus.OK);
	}
	
	@PutMapping("/appels/sortants")
	public ResponseEntity<Map<String, Object>> supprimerAppelSortant(HttpServletRequest request)throws Exception{
		int idClient =  Integer.parseInt( GestionToken.gererTokenClient(request));
		clientService.supprimerHistoriqueSortant(idClient);
		return ResponseMaker.makeResponse(null, 200, "Suppression de l'historique des appels effectues reussie",  HttpStatus.OK);
	}
	
	@PutMapping("/appels/entrants")
	public ResponseEntity<Map<String, Object>> supprimerAppelEntrant(HttpServletRequest request)throws Exception{
		int idClient =  Integer.parseInt( GestionToken.gererTokenClient(request));
		clientService.supprimerHistoriqueEntrant(idClient);
		return ResponseMaker.makeResponse(null, 200, "Suppression des appels entrants effectuees",  HttpStatus.OK);
	}
	
	
	@GetMapping("/appels/entrants")
	public ResponseEntity<Map<String, Object>> appelsEntrants(HttpServletRequest request)throws Exception{
		int idClient =  Integer.parseInt( GestionToken.gererTokenClient(request));
		return ResponseMaker.makeResponse(clientService.listeAppelEntrant(idClient), 200, 
				"Selection des appels recus effectuee",  HttpStatus.OK);
	}	
	
	@GetMapping("/appels/sortants")
	public ResponseEntity<Map<String, Object>> appelsSortants(HttpServletRequest request)throws Exception{
		int idClient =  Integer.parseInt( GestionToken.gererTokenClient(request));
		return ResponseMaker.makeResponse(clientService.listeAppelSortant(idClient), 200, 
				"Selection des appels sortants effectuee",  HttpStatus.OK);
	}
	
	
		////// SMS	
	@PostMapping("/sms")
	public ResponseEntity<Map<String, Object>> envoyerSms(HttpServletRequest request,
			@RequestBody Sms sms)throws Exception{
		int idClient =  Integer.parseInt( GestionToken.gererTokenClient(request));
		clientService.addSms(sms, idClient);
		return ResponseMaker.makeResponse(null, 200, "Sms envoye",  HttpStatus.OK);
	}	
	
	@GetMapping("/sms")
	public ResponseEntity<Map<String, Object>> sms(HttpServletRequest request)throws Exception{
		int idClient =  Integer.parseInt( GestionToken.gererTokenClient(request));
		return ResponseMaker.makeResponse(clientService.listeSms(idClient), 200, 
				"Selection des sms (envoyees et recus) terminee",  HttpStatus.OK);
	}
	
	@PutMapping("/sms")
	public ResponseEntity<Map<String, Object>> supprimerHistoriqueSms(HttpServletRequest request)throws Exception{
		int idClient =  Integer.parseInt( GestionToken.gererTokenClient(request));
		clientService.supprimerSms(idClient);
		return ResponseMaker.makeResponse(null, 200, "Suppression sms (envoyees et recus) terminee",  HttpStatus.OK);
	}
	
	
	
	@PutMapping("/sms/sortants")
	public ResponseEntity<Map<String, Object>> supprimerSmsSortant(HttpServletRequest request)throws Exception{
		int idClient =  Integer.parseInt( GestionToken.gererTokenClient(request));
		clientService.supprimerSmsSortant(idClient);
		return ResponseMaker.makeResponse(null, 200, "Suppression de l'historique des sms sortants effectues reussie",  HttpStatus.OK);
	}
	
	@PutMapping("/sms/entrants")
	public ResponseEntity<Map<String, Object>> supprimerSmsEntrant(HttpServletRequest request)throws Exception{
		int idClient =  Integer.parseInt( GestionToken.gererTokenClient(request));
		clientService.supprimerSmsEntrant(idClient);
		return ResponseMaker.makeResponse(null, 200, "Suppression de l'historique des sms entrants effectues reussie",  HttpStatus.OK);
	}
	
	
	
	
	private String generateToken(Client client){
		long timestamp = System.currentTimeMillis(); 
		String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.CLIENT_API_SERCRET_KEY)
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp + Constants.VALIDITY)) 
				.claim("idClient", client.getIdClient() )
				.claim("idOperateur", client.getIdOperateur())
				.claim("nom", client.getNom())
				.claim("prenom", client.getPrenom())
				.compact();
		return token;
	}
	
	
}
