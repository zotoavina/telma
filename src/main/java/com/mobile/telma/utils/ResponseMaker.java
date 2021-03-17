package com.mobile.telma.utils;

import java.util.Map;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMaker {

	public static ResponseEntity<Map<String , Object>> makeResponse(Object data, int status, String message, HttpStatus hStatus){
		Map<String, Object> map = new HashMap<>();
		map.put("status", status);
		map.put("message", message);
		if(data != null) map.put("data", data);
		return new ResponseEntity<Map<String, Object>>(map, hStatus);
	}
	
	
}
