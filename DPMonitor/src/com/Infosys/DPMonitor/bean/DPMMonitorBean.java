package com.Infosys.DPMonitor.bean;
/**
 * @author Anil_Sehgal01
 * Bean class to get and set the wage values from all the sources
 */
public class DPMMonitorBean {
	public static double wageFromWageHistoryTemp;
	public static double wageFromInputs;
	public static double wageFromFile;
	public static double getWageFromWageHistoryTemp() {
		return wageFromWageHistoryTemp;
	}
	public static void setWageFromWageHistoryTemp(double wageFromWageHistoryTemp) {
		DPMMonitorBean.wageFromWageHistoryTemp = wageFromWageHistoryTemp;
	}
	public static double getWageFromInputs() {
		return wageFromInputs;
	}
	public static void setWageFromInputs(double wageFromInputs) {
		DPMMonitorBean.wageFromInputs = wageFromInputs;
	}
	public static double getWageFromFile() {
		return wageFromFile;
	}
	public static void setWageFromFile(double wageFromFile) {
		DPMMonitorBean.wageFromFile = wageFromFile;
	}
	
}
