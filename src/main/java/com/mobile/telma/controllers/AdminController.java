package com.mobile.telma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobile.telma.services.AdminService;

@RestController
@RequestMapping("")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@PostMapping("")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
	public String getLogin() {
		return "Bonjour";
	}
	
}
