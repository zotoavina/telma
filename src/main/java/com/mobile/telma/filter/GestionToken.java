package com.mobile.telma.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.cors.CorsUtils;
import com.mobile.telma.exceptions.*;
import com.mobile.telma.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class GestionToken {
	
	
	public static String gererTokenAdmin(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		// TODO Auto-generated method stub
				System.out.println("Gestion Token");
				HttpServletRequest srequest = (HttpServletRequest) request;
				HttpServletResponse sresponse = (HttpServletResponse) response;
				String authorization = srequest.getHeader("Authorization");
				System.out.println("---- Authorization ::" + authorization);
				return validerToken(validerAuthorization(authorization), Constants.ADMIN_API_SERCRET_KEY, "idAdmin");
	}
	
	public static String  gererTokenClient(HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("Gestion Token");
		HttpServletRequest srequest = (HttpServletRequest) request;
		HttpServletResponse sresponse = (HttpServletResponse) response;
		String authorization = srequest.getHeader("Authorization");
		System.out.println("---- Authorization ::" + authorization);
		return validerToken(validerAuthorization(authorization), Constants.CLIENT_API_SERCRET_KEY, "idClient");
	}
	
	
	private static String validerToken(String token, String secretKey, String user) throws Exception{
		try {
			Claims claim = Jwts.parser().setSigningKey(secretKey)
					.parseClaimsJws(token).getBody();
			 return claim.get(user).toString();
//			if (CorsUtils.isPreFlightRequest(srequest)) {
//		        sresponse.setStatus(HttpServletResponse.SC_OK);
//		    }
		}catch(Exception e) {
			throw new EtAuthException("Token invalide ou expire");
		}
	}
	
	private static String validerAuthorization(String authorization)throws Exception{
		if(authorization == null)
			throw new EtAuthException("Veuillez vous connectes pour obtenir une authorisation");
		String[] token = authorization.split(" ");
		if( token.length <= 1 || token[1] == null)
			throw new EtAuthException("L' autorisation doit etre Bearer [token]");
		return token[1];
	}
	
	
}
