package com.victor.multiUtilityToolkit.jms.supportMethods;

public class ValidationMethods {
	public static boolean isValidServerUrl(String server){
		boolean isValidServerUrl = true;
		if(server.trim().length() == 0){
			isValidServerUrl = false;
		}
		return isValidServerUrl;
	}
	public static boolean areValidFields(String server, String user, String pass){
		boolean areValidFields = true;
		if(server.trim().length()==0 || user.trim().length()==0 || pass.trim().length()==0){
			areValidFields = false;
		}
		return areValidFields;
	}
}
