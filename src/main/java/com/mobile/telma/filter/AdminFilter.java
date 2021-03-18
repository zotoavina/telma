package com.mobile.telma.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.mobile.telma.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AdminFilter extends GenericFilterBean{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
				HttpServletRequest srequest = (HttpServletRequest) request;
				HttpServletResponse sresponse = (HttpServletResponse) response;
				String authorization = srequest.getHeader("Authorization");
				if(authorization != null) {
					String[] authArray = authorization.split("Bearer");
					if(authArray.length > 1 && authArray[1] != null) {
						String token = authArray[1];
						try {
							Claims claim = Jwts.parser().setSigningKey(Constants.ADMIN_API_SERCRET_KEY)
									.parseClaimsJws(token).getBody();
							srequest.setAttribute("idAdmin", claim.get("idAdmin").toString());
						    sresponse.setStatus(HttpServletResponse.SC_OK);
						       // return new AuthFilter() ; //whatever your token implementation class is - return an instance of it
						}catch(Exception e) {
							sresponse.sendError(HttpStatus.FORBIDDEN.value(), "Token invalide ou expire");
							return;
						}
					}else {
						sresponse.sendError(HttpStatus.FORBIDDEN.value(), "L' autorisation doit etre Bearer[token]");
						return;
					}	
				}else {
					sresponse.sendError(HttpStatus.FORBIDDEN.value(), "Veuillez vous connectes pour obtenir une authorisation");
					return;
				}
				chain.doFilter(request, response);
		
	}
	
	
	
	

}
