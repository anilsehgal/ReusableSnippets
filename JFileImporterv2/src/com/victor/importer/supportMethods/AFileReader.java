package com.victor.importer.supportMethods;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class AFileReader {
	Object[][] table = null;
	int index = 0, blankRowCount = 0;
	public Object[][] readExcelFile(String path, int sheetNo, int startRow, int endRow) throws ArrayIndexOutOfBoundsException, Exception{
		InputStream inputStream = new FileInputStream(path);
		Workbook workbook = WorkbookFactory.create(inputStream);
		
		Sheet sheet = workbook.getSheetAt(sheetNo);		
		Row row = sheet.getRow(startRow);	
		if(endRow == 0){
			endRow = sheet.getPhysicalNumberOfRows();
		}
		if(endRow+1-startRow > 0){
			table = new Object[endRow-startRow][row.getPhysicalNumberOfCells()];
		}else{
			table = new Object[1][0];
		}
		if(startRow <= endRow){
			Cell cell = null;
			index = row.getPhysicalNumberOfCells();
			for(int rowNum=startRow;rowNum < endRow;rowNum++){
				row = sheet.getRow(rowNum);
				
				if(row!=null){
					for(int cellNum=0;cellNum < row.getPhysicalNumberOfCells();cellNum++){
						cell = row.getCell(cellNum);
						if(cell!=null){	
							if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
								
								if(  DateUtil.isCellDateFormatted(cell)){
									table[rowNum-startRow][cellNum] = cell.getDateCellValue();
								}else{
									table[rowNum-startRow][cellNum] = cell.getNumericCellValue();
								}
							}else if(cell.getCellType()==Cell.CELL_TYPE_STRING){
								table[rowNum-startRow][cellNum] = cell.getStringCellValue();
							}else if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
								table[rowNum-startRow][cellNum] = "";
							}else if(cell.getCellType()==Cell.CELL_TYPE_BOOLEAN){
								table[rowNum-startRow][cellNum] = cell.getBooleanCellValue();
							}
						}
					}
				}else{
					blankRowCount++;
				}
			}
		}
		if(blankRowCount > 0){
			JOptionPane.showMessageDialog(null, blankRowCount+" blank rows were found while reading through the file, which might cause failure in import!!","Blank Row Alert", JOptionPane.WARNING_MESSAGE);
		}
		return table;
	}
/*	public Object[][] readXlsxFile(String path, int sheetNo, int startRow, int endRow) throws Exception{
		InputStream inputStream = new FileInputStream(path);
		XSSFWorkbook workBook = new XSSFWorkbook(inputStream); 
		XSSFSheet sheet = workBook.getSheetAt(sheetNo); 
		int totalRows = sheet.getPhysicalNumberOfRows(); 
		//to calculate the number of columns
		XSSFRow row = sheet.getRow(sheetNo);
		int totalCols = row.getPhysicalNumberOfCells();
		System.out.println("total no of rows >>>>"+totalRows);
		System.out.println("total no of cols >>>>"+totalCols);
		table = new Object[totalRows][totalCols];
		for(int rowNum=0;rowNum < totalRows;rowNum++){
			row = sheet.getRow(rowNum);
			for(int colNum=0;colNum < totalCols;colNum++){
				XSSFCell cell = row.getCell(colNum);
				if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
					
					if(  DateUtil.isCellDateFormatted(cell)){
						table[rowNum][colNum] = cell.getDateCellValue();
					}else{
						table[rowNum][colNum] = cell.getNumericCellValue();
					}
				}else if(cell.getCellType()==XSSFCell.CELL_TYPE_STRING){
					table[rowNum][colNum] = cell.getStringCellValue();
				}else if(cell.getCellType()==XSSFCell.CELL_TYPE_BLANK){
					table[rowNum][colNum] = "";
				}else if(cell.getCellType()==XSSFCell.CELL_TYPE_BOOLEAN){
					table[rowNum][colNum] = cell.getBooleanCellValue();
				}
			}
		}
		return table;
	}*/
/*	public static void main(String[] args) {
		try {
			(new AFileReader()).readXlsxFile("D://abc.xlsx", 0, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	public Object[][] readDelimitedFile(String path, String delimiter, int startRow, int endRow) throws Exception{
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
		if(endRow==0){
			while((line = lineCounter.readLine())!=null){
				fileContents.add(line);
				tokenizer = new StringTokenizer(line, delimiter);
				tokens.add(tokenizer.countTokens());
				total++;
			}
		}else{
			int tillLastRow = 0;
			while(tillLastRow < endRow){
				line = lineCounter.readLine();
				fileContents.add(line);
				tokenizer = new StringTokenizer(line, delimiter);
				tokens.add(tokenizer.countTokens());
				total++;		
				tillLastRow++;
			}
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
		return table;
	}
	public Object[][] readFixedWidthFile(String path, int[] fieldSizes, int startRow, int endRow) throws Exception{
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
		if(endRow==0){
			while((line=lineCounter.readLine())!=null){
				total++;
				fileContents.add(line);
			}
		}else{
			int tillLastRow = 0;
			while(tillLastRow < endRow){
				line=lineCounter.readLine();
				total++;
				fileContents.add(line);
				tillLastRow++;
			}
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
		return table;
	}
}
