package com.victor.multiUtilityToolkit.importer.supportMethods;

import java.text.SimpleDateFormat;

public class FieldValidator{
	public boolean isFieldNumeric(String field){
		boolean isValidNumeric = false;
		try{	
			Double.parseDouble(field);
			isValidNumeric = true;
		}catch(Exception e){
			isValidNumeric = false;
		}
		return isValidNumeric;	
	}
	public boolean isFieldBit(String field){
		
		boolean isFieldBit = false;
		try{	
			int x = Integer.parseInt(field);
			if(x==0 || x==1){
				isFieldBit = true;
			}
		}catch(Exception e){
			isFieldBit = false;
		}
		return isFieldBit;	
	}
	public boolean isFieldDateTime(String field, String format){
		
		boolean isFieldDateTime = false;
		try{	
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			dateFormat.parse(field);
			System.out.println("field parsed: "+field);
			isFieldDateTime = true;
		}catch(Exception e){
			System.out.println("Exception in isFieldDateTime():");
			e.printStackTrace();
			isFieldDateTime = false;
		}
		return isFieldDateTime;	
	}
	public boolean isFieldNull(String field){
		
		boolean isFieldNull = false;
		try{	
			if(field==null || (field!=null && field.trim().length()==0)){
				System.out.println("field parsed: "+field);
				isFieldNull = true;
			}
		}catch(Exception e){
			System.out.println("Exception in isFieldDateTime():");
			e.printStackTrace();
			isFieldNull = false;
		}
		return isFieldNull;	
	}
}
