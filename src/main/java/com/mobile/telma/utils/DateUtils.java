package com.mobile.telma.utils;

import java.text.SimpleDateFormat;

import com.mobile.telma.exceptions.EtBadRequestException;

public class DateUtils {

	public static java.sql.Timestamp utilToSql( java.util.Date date){
		return new java.sql.Timestamp( date.getTime() );
	}
	

	public static java.sql.Timestamp parse(String date)throws EtBadRequestException{
		try {
			System.out.println("sgesghsa:  " + date);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date tmp = sdf.parse(date);
			System.out.println("Utils : " + tmp.toString());
			java.sql.Timestamp ret = new java.sql.Timestamp( tmp.getTime() );
			System.out.println("Sql : " + ret.toString());
			return ret;
		}catch(Exception e) {
			throw new EtBadRequestException("Format de date non valide pour l' achat de forfait");
		}
	}
	
	@SuppressWarnings("deprecation")
	public static java.sql.Timestamp addJour(java.sql.Timestamp date, int jour){
		java.sql.Timestamp  da= new java.sql.Timestamp(date.getTime());
		da.setDate(da.getDate() + jour );
		return da;
	}
	
}
