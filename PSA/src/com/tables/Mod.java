package com.tables;

import java.util.Calendar;

import com.psa.entities.base.Convertor;
/**
 * @version 1.0
 * @author sehgalan
 * @category class containing methods
 */
public class Mod implements Convertor {

	
	/**
	 * @category returns the current date
	 * @param inputObj
	 * @return Object - Date
	 */
	
	public Object getDate ( Object inputObj ) {
		
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * @category returns a random number
	 * @param inputObj
	 * @return Object - integer
	 */
	public Object getRandom ( Object inputObj ) {
		
		return ( int ) ( Math.random() * 100000 );
	}
}
