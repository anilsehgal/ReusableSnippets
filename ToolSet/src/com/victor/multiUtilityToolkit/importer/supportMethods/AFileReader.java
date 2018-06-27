package com.victor.multiUtilityToolkit.importer.supportMethods;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class AFileReader {
	Object[][] table = null;
	public Object[][] readExcelFile(String path, int sheetNo, int startRow) throws ArrayIndexOutOfBoundsException, Exception{
		InputStream inputStream = new FileInputStream(path);
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
		HSSFSheet sheet = workbook.getSheetAt(sheetNo);		
		HSSFRow row = sheet.getRow(0);		
		if(sheet.getLastRowNum()+1-startRow > 0){
			table = new Object[sheet.getLastRowNum()+1-startRow][row.getLastCellNum()];
		}else{
			table = new Object[1][0];
		}
		if(startRow <= sheet.getLastRowNum()){
			
			for(int i=startRow;i < sheet.getLastRowNum()+1;i++){
				row = sheet.getRow(i);
				for(int j=0;j < row.getLastCellNum();j++){
					table[i-startRow][j] = row.getCell((short)j);
				}
			}
		}
		return table;
	}
	public Object[][] readDelimitedFile(String path, String delimiter, int startRow){
		try{
			int i = 0,j = 0,total = 0;
			FileInputStream fstream = new FileInputStream(path);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader lineCounter = new BufferedReader(new InputStreamReader(in));
			StringTokenizer tokenizer = null;
			String line = null;
			List<String> fileContents = new ArrayList<String>();
			List<Integer> tokens = new ArrayList<Integer>();
			for(int skipLine=0;skipLine < startRow;skipLine++){
				lineCounter.readLine();
			}
			while((line = lineCounter.readLine())!=null){
				fileContents.add(line);
				tokenizer = new StringTokenizer(line, delimiter);
				tokens.add(tokenizer.countTokens());
				total++;
				
			}
			in.close();
			int size = Collections.max(tokens);
			table = new Object[total][size];
			for(String line1 : fileContents){
				tokenizer = new StringTokenizer(line1, delimiter);
				while(tokenizer.hasMoreTokens()){
					table[i][j] = tokenizer.nextToken();
					j++;
				}
				i++;
				j=0;
			}
		}catch(Exception e){
			System.out.println("exception: "+e);
			return new Object[0][0];
		}
		return table;
	}
	public Object[][] readFixedWidthFile(String path, int[] fieldSizes, int startRow){
		try{
			int i = 0,total = 0;
			int startIndex = 0;
			int endIndex = 0;
			String line = null;
			FileInputStream fstream = new FileInputStream(path);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
	
			BufferedReader lineCounter = new BufferedReader(new InputStreamReader(in));
			List<String> fileContents = new ArrayList<String>();
			for(int skipLine=0;skipLine < startRow;skipLine++){
				lineCounter.readLine();
			}
			while((line=lineCounter.readLine())!=null){
				total++;
				fileContents.add(line);
			}
			lineCounter.close();
			table = new Object[total][fieldSizes.length];
			for(String line1 : fileContents){
				for(int j=0;j < fieldSizes.length;j++){
					//calculate sizes here  2 4 7 
					if(j==0){
						startIndex = 0;
						endIndex = fieldSizes[j];
					}else{
						startIndex = startIndex+fieldSizes[j-1];
						endIndex = endIndex+fieldSizes[j];
					}
					table[i][j] = line1.substring(startIndex,endIndex);
					
				}
				i++;
			}
		}catch(Exception e){
			
			return new Object[0][0];
		}
		return table;
	}
}
