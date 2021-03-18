package com.mobile.telma.controllers;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.mobile.telma.domains.Admin;
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
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping("")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> adminMap) {
		String email = (String) adminMap.get("email");
		String mdp = (String) adminMap.get("mdp");
	    Admin admin = adminService.getByEmailAndMdp(email, mdp);
	    return ResponseMaker.makeResponse(generateToken(admin), 200, "Login admin reussi", HttpStatus.ACCEPTED);
	}
	
	
	private String generateToken(Admin admin){
		long timestamp = System.currentTimeMillis();
		String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SERCRET_KEY)
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp + Constants.VALIDITY)) 
				.claim("idUtilisateur", admin.getIdAdmin() )
				.claim("idoperateur", admin.getIdoperateur())
				.claim("nom", admin.getNom())
				.claim("prenom", admin.getPrenom())
				.claim("email", admin.getEmail())
				.compact();
		return token;
	}
	
	
}
