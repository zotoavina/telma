package com.mobile.telma.utils;

import java.text.SimpleDateFormat;

import com.mobile.telma.exceptions.EtBadRequestException;

public class DateUtils {

	public static java.sql.Date utilToSql( java.util.Date date){
		return new java.sql.Date( date.getTime() );
	}
	
	public static java.util.Date parse(String date)throws EtBadRequestException{
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.parse(date);
		}catch(Exception e) {
			throw new EtBadRequestException("Format de date non valide pour l' achat de forfait");
		}
	}
	
}