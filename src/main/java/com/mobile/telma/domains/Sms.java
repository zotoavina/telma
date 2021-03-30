package com.mobile.telma.domains;

import java.sql.Timestamp;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sms")
public class Sms extends Communication{
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Sms() {
		super();
	}

	public Sms(String id, String envoyeur, String receveur, int activeEnvoyeur, int activeReceveur, Timestamp date,String message) {
		super(id, envoyeur, receveur, activeEnvoyeur, activeReceveur, date);
		// TODO Auto-generated constructor stub
		setMessage(message);
	}
	
	
	
}
