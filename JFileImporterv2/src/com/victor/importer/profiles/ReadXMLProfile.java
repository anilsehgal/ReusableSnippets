package com.victor.importer.profiles;

import java.io.*;

import javax.swing.JOptionPane;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import com.victor.importer.constants.ProfileBean;


public class ReadXMLProfile{
   public static ProfileBean openProfile(String xmlFile){
	   ProfileBean profileBean = null;
	   try {
		   File file = new File(xmlFile);
		   if(file.exists()){
			   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			   // Use the factory to create a builder
			   DocumentBuilder builder = factory.newDocumentBuilder();
			   Document doc = builder.parse(xmlFile);
			   // Get a list of all elements in the document
			   profileBean = new ProfileBean();
			   NodeList columnNamelist = doc.getElementsByTagName("columnName");
			   NodeList columnDataTypelist = doc.getElementsByTagName("columnDataType");
			   NodeList columnLengthlist = doc.getElementsByTagName("columnLength");
			   NodeList isNullablelist = doc.getElementsByTagName("isNullable");
			   NodeList primaryKeylist = doc.getElementsByTagName("primaryKey");
			   NodeList fileColumnlist = doc.getElementsByTagName("fileColumn");
			   Object[][] columnMapping = new Object[columnNamelist.getLength()][6];
			   Object[][] optionMapping = new Object[20][2];
			   for (int i=0; i<columnNamelist.getLength(); i++) {
				   // Get element
				   Element element1 = (Element)columnNamelist.item(i);
				   Element element2 = (Element)columnDataTypelist.item(i);
				   Element element3 = (Element)columnLengthlist.item(i);
				   Element element4 = (Element)isNullablelist.item(i);
				   Element element5 = (Element)primaryKeylist.item(i);
				   Element element6 = (Element)fileColumnlist.item(i);

				   columnMapping[i][0]=element1.getTextContent();
				   columnMapping[i][1]=element2.getTextContent();
				   columnMapping[i][2]=element3.getTextContent();
				   columnMapping[i][3]=element4.getTextContent();
				   columnMapping[i][4]=element5.getTextContent();
				   columnMapping[i][5]=element6.getTextContent();
			   }
			   NodeList delimiterlist = doc.getElementsByTagName("delimiter");
			   NodeList flavorlist = doc.getElementsByTagName("flavor");
			   NodeList filePathlist = doc.getElementsByTagName("filePath");
			   NodeList startLinelist = doc.getElementsByTagName("startLine");
			   NodeList tableNamelist = doc.getElementsByTagName("tableName");
			   NodeList dburllist = doc.getElementsByTagName("dburl");
			   NodeList driverNamelist = doc.getElementsByTagName("driverName");
			   NodeList userNamelist = doc.getElementsByTagName("userName");
			   NodeList passwordlist = doc.getElementsByTagName("password");
			   NodeList dupRowActionlist = doc.getElementsByTagName("dupRowAction");
			   NodeList dateFormatlist = doc.getElementsByTagName("dateFormat");
			   NodeList sheetNolist = doc.getElementsByTagName("sheetNo");
			   NodeList endRowlist = doc.getElementsByTagName("endRow");
			   NodeList defaultStrategylist = doc.getElementsByTagName("defaultStrategy");
			   //String preImportCmd, String postImportCmd,String preImportCmdType, String postImportCmdType
			   NodeList preImportCmdlist = doc.getElementsByTagName("preImportCmd");
			   NodeList postImportCmdlist = doc.getElementsByTagName("postImportCmd");
			   NodeList preImportCmdTypelist = doc.getElementsByTagName("preImportCmdType");
			   NodeList postImportCmdTypelist = doc.getElementsByTagName("postImportCmdType");
			   NodeList continueImportOnPreCmdFailurelist = doc.getElementsByTagName("continueImportOnPreCmdFailure");
			   NodeList truncateOverflowingValueslist = doc.getElementsByTagName("truncateOverflowingValues");
			   
			   //truncateOverflowingValues
			   for (int i=0; i<delimiterlist.getLength(); i++) {
				   Element element1 = (Element)delimiterlist.item(i);
				   Element element2 = (Element)flavorlist.item(i);
				   Element element3 = (Element)filePathlist.item(i);
				   Element element4 = (Element)startLinelist.item(i);
				   Element element5 = (Element)tableNamelist.item(i);
				   Element element6 = (Element)dburllist.item(i);
				   Element element7 = (Element)driverNamelist.item(i);
				   Element element8 = (Element)userNamelist.item(i);
				   Element element9 = (Element)passwordlist.item(i);
				   Element element10 = (Element)dupRowActionlist.item(i);
				   Element element11 = (Element)dateFormatlist.item(i);
				   Element element12 = (Element)sheetNolist.item(i);
				   Element element13 = (Element)endRowlist.item(i);
				   Element element14 = (Element)defaultStrategylist.item(i);
				   
				   Element element15 = (Element)preImportCmdlist.item(i);
				   Element element16 = (Element)postImportCmdlist.item(i);
				   Element element17 = (Element)preImportCmdTypelist.item(i);
				   Element element18 = (Element)postImportCmdTypelist.item(i);
				   Element element19 = (Element)continueImportOnPreCmdFailurelist.item(i);
				   Element element20 = (Element)truncateOverflowingValueslist.item(i);
				   profileBean.setColumnMapping(columnMapping);
				   profileBean.setDelimiter(element1.getTextContent());
				   profileBean.setFlavor(element2.getTextContent());
				   profileBean.setFilePath(element3.getTextContent());
				   profileBean.setStartLine(element4.getTextContent());
				   profileBean.setTableName(element5.getTextContent());
				   profileBean.setDburl(element6.getTextContent());
				   profileBean.setDriverName(element7.getTextContent());
				   profileBean.setUserName(element8.getTextContent());
				   profileBean.setPassword(element9.getTextContent());
				   profileBean.setDupRowAction(element10.getTextContent());
				   profileBean.setDateFormat(element11.getTextContent());
				   profileBean.setSheetNo(element12.getTextContent());
				   profileBean.setEndRow(element13.getTextContent());
				   profileBean.setDefaultStrategy(element14.getTextContent());
				   
				   profileBean.setPreImportCmd(element15.getTextContent());
				   profileBean.setPostImportCmd(element16.getTextContent());
				   profileBean.setPreImportCmdType(element17.getTextContent());
				   profileBean.setPostImportCmdType(element18.getTextContent());
				   profileBean.setContinueImportOnPreCmdFailure(element19.getTextContent());
				   profileBean.setTruncateOverflowingValues(element20.getTextContent().equals("true")?true:false);
				   
				   optionMapping[0][0]="Delimiter";
				   optionMapping[0][1]=element1.getTextContent();
				   optionMapping[1][0]="Flavor";
				   optionMapping[1][1]=element2.getTextContent();
				   optionMapping[2][0]="FilePath";
				   optionMapping[2][1]=element3.getTextContent();
				   optionMapping[3][0]="StartLine";
				   optionMapping[3][1]=element4.getTextContent();
				   optionMapping[4][0]="TableName";
				   optionMapping[4][1]=element5.getTextContent();
				   optionMapping[5][0]="DBUrl";
				   optionMapping[5][1]=element6.getTextContent();
				   optionMapping[6][0]="DriverURI";
				   optionMapping[6][1]=element7.getTextContent();
				   optionMapping[7][0]="UserName";
				   optionMapping[7][1]=element8.getTextContent();
				   optionMapping[8][0]="Password";
				   optionMapping[8][1]=element9.getTextContent();
				   optionMapping[9][0]="DuplicateRowAction";
				   optionMapping[9][1]=element10.getTextContent();
				   optionMapping[10][0]="DateFormat";
				   optionMapping[10][1]=element11.getTextContent();		
				   optionMapping[11][0]="sheetNo";
				   optionMapping[11][1]=element12.getTextContent();	
				   optionMapping[12][0]="endRow";
				   optionMapping[12][1]=element13.getTextContent();	
				   optionMapping[13][0]="defaultStrategy";
				   optionMapping[13][1]=element14.getTextContent();	
				   
				   optionMapping[14][0]="preImportCmd";
				   optionMapping[14][1]=element15.getTextContent();		
				   optionMapping[15][0]="postImportCmd";
				   optionMapping[15][1]=element16.getTextContent();	
				   optionMapping[16][0]="preImportCmdType";
				   optionMapping[16][1]=element17.getTextContent();	
				   optionMapping[17][0]="postImportCmdType";
				   optionMapping[17][1]=element18.getTextContent();	
				   optionMapping[18][0]="continueImportOnPreCmdFailure";
				   optionMapping[18][1]=element19.getTextContent();	
				   optionMapping[19][0]="TruncateOverflowingValues";
				   optionMapping[19][1]=element20.getTextContent();	
				   
				   profileBean.setOptionMapping(optionMapping);
			   }
		   }
		   else{
			   JOptionPane.showMessageDialog(null, "The input file: "+xmlFile+" was not found!!", "Cannot Open Profile", JOptionPane.ERROR_MESSAGE);
		   }
	   }
	   catch (Exception e) {
		   JOptionPane.showMessageDialog(null, e, "Cannot Open Profile", JOptionPane.ERROR_MESSAGE);
	   }
	   return profileBean;
   }
   public static void main(String[] args) {
	   openProfile("D://Profiles//Tbl_Modules_Import.xml");
   }
}