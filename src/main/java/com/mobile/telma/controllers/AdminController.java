package com.mobile.telma.controllers;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobile.telma.domains.Action;
import com.mobile.telma.domains.Admin;
import com.mobile.telma.domains.Forfait;
import com.mobile.telma.domains.Offre;
import com.mobile.telma.filter.GestionToken;
import com.mobile.telma.services.AdminService;
import com.mobile.telma.utils.ResponseMaker;
import com.mobile.telma.Constants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	
	
	//@CrossOrigin(origins = "https://telmaproject.herokuapp.com/")
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> adminMap) {
		String email = (String) adminMap.get("email");
		String mdp = (String) adminMap.get("mdp");
	    Admin admin = adminService.getByEmailAndMdp(email, mdp);
	    return ResponseMaker.makeResponse(generateToken(admin), 200, "Login admin reussi", HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/admin/validations")
	public ResponseEntity<Map<String , Object>> listeActions(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("validate");
		int idAdmin = Integer.parseInt( GestionToken.gererTokenAdmin(request, response) );
		System.out.println("IdAdmin : " + idAdmin);
		return ResponseMaker.makeResponse(adminService.getActionNonValide(), 200, 
				"selection des actions non valide reussi", HttpStatus.OK);
	}
	
	@GetMapping("/admin/validations/{idAction}")
	public ResponseEntity<Map<String, Object>> validerAction(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("idAction") int idAction)throws Exception{
		int idAdmin = Integer.parseInt( GestionToken.gererTokenAdmin(request, response) );
		Action action = adminService.validerAction(idAction);
		return ResponseMaker.makeResponse(action, 200, 
			 	"Validation de " + action.getDescription(), HttpStatus.OK);
	}
	
	
	
	// ----------------------------------- Gestion Offres
	@GetMapping("/admin/offres")
	public ResponseEntity <Map<String, Object>> getListOffres(){
			return ResponseMaker.makeResponse(adminService.getListOffres(), 200, 
					"selection de la liste des offres reussi " , HttpStatus.OK);
	}
	
	@GetMapping("/admin/offres/{idOffre}")
	public ResponseEntity<Map<String, Object>> getOffre(@PathVariable("idOffre") String idOffre){
		return ResponseMaker.makeResponse( adminService.getOffre(idOffre), 200, "selection offre reussi " , HttpStatus.OK);
	}
	
	@PostMapping("/admin/offres")
	public ResponseEntity<Map<String, Object>> ajoutOffre(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Offre offre)throws Exception{
		GestionToken.gererTokenAdmin(request, response);
		adminService.insertOffre(offre);
		return ResponseMaker.makeResponse(null, 200, "offre desormais valable", HttpStatus.CREATED);
	}
	
	@PutMapping("/admin/offres/{idOffre}")
	public ResponseEntity<Map<String, Object>> updateOffreAddForfait(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("idOffre") String idOffre, @RequestBody Forfait forfait)throws Exception{
		GestionToken.gererTokenAdmin(request, response);
		return ResponseMaker.makeResponse(adminService.addForfaitToOffre(idOffre, forfait), 200, "forfait desormais valable", HttpStatus.CREATED);
	}
	
	
	
	private String generateToken(Admin admin){
		long timestamp = System.currentTimeMillis();
		String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.ADMIN_API_SERCRET_KEY)
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp + Constants.VALIDITY)) 
				.claim("idAdmin", admin.getIdAdmin() )
				.claim("idoperateur", admin.getIdoperateur())
				.claim("nom", admin.getNom())
				.claim("prenom", admin.getPrenom())
				.claim("email", admin.getEmail())
				.compact();
		return token;
	}
	
}
