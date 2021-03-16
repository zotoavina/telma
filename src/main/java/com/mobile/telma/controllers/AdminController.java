package com.mobile.telma.controllers;

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

@RestController
@RequestMapping("")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	//@CrossOrigin(origins = "https://telmaproject.herokuapp.com/")
	@PostMapping("")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> adminMap) {
		String email = (String) adminMap.get("email");
		String mdp = (String) adminMap.get("mdp");
	   // Admin admin = adminService.getByEmailAndMdp(email, mdp);
	    Map<String, Object> map = new HashMap<>();
	    map.put("status", HttpStatus.OK.value());
	    map.put("message", "Identification de l' admin reussi");
	    // map.put("data", admin);
	    return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public String getLogin() {
		return "Bonjour";
	}
	
}
