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
	
	
	public static void gererToken(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		// TODO Auto-generated method stub
				System.out.println("Gestion Token");
				HttpServletRequest srequest = (HttpServletRequest) request;
				HttpServletResponse sresponse = (HttpServletResponse) response;
				String authorization = srequest.getHeader("Authorization");
				System.out.println("---- Authorization ::" + authorization);
				if(authorization != null) {
					String[] authArray = authorization.split("Bearer");
					if(authArray.length > 1 && authArray[1] != null) {
						String token = authArray[1];
						try {
							Claims claim = Jwts.parser().setSigningKey(Constants.ADMIN_API_SERCRET_KEY)
									.parseClaimsJws(token).getBody();
							srequest.setAttribute("idAdmin", claim.get("idAdmin").toString());
							if (CorsUtils.isPreFlightRequest(srequest)) {
						        sresponse.setStatus(HttpServletResponse.SC_OK);
						       // return new AuthFilter() ; //whatever your token implementation class is - return an instance of it
						    }
						}catch(Exception e) {
							throw new EtAuthException("Token invalide ou expire");
						}
					}
					else throw new EtAuthException("L' autorisation doit etre Bearer[token]");	
				}
				else throw new EtAuthException("Veuillez vous connectes pour obtenir une authorisation");
				
	}
	
	
}
