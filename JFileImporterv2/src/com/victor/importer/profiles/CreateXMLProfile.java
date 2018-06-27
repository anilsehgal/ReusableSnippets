package com.victor.importer.profiles;

import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
 
public class CreateXMLProfile {
	public void saveProfile(Object[][] fileColumnMapping, String delimiterOrSheetName, String startLine, String profileName, String flavor, String filePath,
			String tableName, String dburl, String driverName, String userName, String password,
			String dupRowAction, String dateFormat, String sheetNumber, String endRow, String defaultStrategy,
			String preImportCmd, String postImportCmd,String preImportCmdType, String postImportCmdType, String continueImportOnPreCmdFailure, boolean truncateOverflowingValues) throws Exception {

		int i=0;
		String root0 = "columnMapping",root1 = "profile",root2 = "delimiter",root3 = "flavor",root4 = "filePath",root5 = "startLine";
		String root6 = "tableName",root7 = "dburl",root8 = "driverName",root9 = "userName",root10 = "password";
		String root11 = "dupRowAction",root12 = "dateFormat",root13 = "sheetNo",root14 = "endRow", root15 = "defaultStrategy",
		root16 = "preImportCmd", root17 = "postImportCmd",root18 = "preImportCmdType", root19 = "postImportCmdType",root20="continueImportOnPreCmdFailure",root21="truncateOverflowingValues";
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
		Element rootElement = document.createElement(root1);
		
        document.appendChild(rootElement);
		for(Object[] columnMapping : fileColumnMapping){
			Element rootElement0 = document.createElement(root0);
			rootElement.appendChild(rootElement0);
			for(Object columndata : columnMapping){
				String element = null;
				if(i==0){
					element = "columnName";
				}else if(i==1){
					element = "columnDataType";
				}else if(i==2){
					element = "columnLength";
				}else if(i==3){
					element = "isNullable";
				}else if(i==4){
					element = "primaryKey";
				}else if(i==5){
					element = "fileColumn";
				}
				String data = "null";
				if(columndata!=null && !(columndata.toString().trim().length()==0)){	
					data=columndata.toString();
				}
				Element em = document.createElement(element);
				em.appendChild(document.createTextNode(data));
				rootElement0.appendChild(em);
				i++;
			}
			i=0;
		}
		Element rootElement2 = document.createElement(root2);
		rootElement.appendChild(rootElement2);
		rootElement2.appendChild(document.createTextNode(delimiterOrSheetName));

		Element rootElement3 = document.createElement(root3);
		rootElement.appendChild(rootElement3);
		rootElement3.appendChild(document.createTextNode(flavor));

		Element rootElement4 = document.createElement(root4);
		rootElement.appendChild(rootElement4);
		rootElement4.appendChild(document.createTextNode(filePath));

		Element rootElement5 = document.createElement(root5);
		rootElement.appendChild(rootElement5);
		rootElement5.appendChild(document.createTextNode(startLine));

		
		Element rootElement6 = document.createElement(root6);
		rootElement.appendChild(rootElement6);
		rootElement6.appendChild(document.createTextNode(tableName));

		Element rootElement7 = document.createElement(root7);
		rootElement.appendChild(rootElement7);
		rootElement7.appendChild(document.createTextNode(dburl));

		Element rootElement8 = document.createElement(root8);
		rootElement.appendChild(rootElement8);
		rootElement8.appendChild(document.createTextNode(driverName));

		Element rootElement9 = document.createElement(root9);
		rootElement.appendChild(rootElement9);
		rootElement9.appendChild(document.createTextNode(userName));

		Element rootElement10 = document.createElement(root10);
		rootElement.appendChild(rootElement10);
		rootElement10.appendChild(document.createTextNode(password));

		Element rootElement11 = document.createElement(root11);
		rootElement.appendChild(rootElement11);
		rootElement11.appendChild(document.createTextNode(dupRowAction));

		Element rootElement12 = document.createElement(root12);
		rootElement.appendChild(rootElement12);
		rootElement12.appendChild(document.createTextNode(dateFormat));

		Element rootElement13 = document.createElement(root13);
		rootElement.appendChild(rootElement13);
		rootElement13.appendChild(document.createTextNode(sheetNumber));
		
		Element rootElement14 = document.createElement(root14);
		rootElement.appendChild(rootElement14);
		rootElement14.appendChild(document.createTextNode(endRow));
		
		Element rootElement15 = document.createElement(root15);
		rootElement.appendChild(rootElement15);
		rootElement15.appendChild(document.createTextNode(defaultStrategy));
		
		
		
		
		Element rootElement16 = document.createElement(root16);
		rootElement.appendChild(rootElement16);
		rootElement16.appendChild(document.createTextNode(preImportCmd));

		Element rootElement17 = document.createElement(root17);
		rootElement.appendChild(rootElement17);
		rootElement17.appendChild(document.createTextNode(postImportCmd));
		
		Element rootElement18 = document.createElement(root18);
		rootElement.appendChild(rootElement18);
		rootElement18.appendChild(document.createTextNode(preImportCmdType));
		
		Element rootElement19 = document.createElement(root19);
		rootElement.appendChild(rootElement19);
		rootElement19.appendChild(document.createTextNode(postImportCmdType));
		
		Element rootElement20 = document.createElement(root20);
		rootElement.appendChild(rootElement20);
		rootElement20.appendChild(document.createTextNode(continueImportOnPreCmdFailure));		
		
		Element rootElement21 = document.createElement(root21);
		rootElement.appendChild(rootElement21);
		rootElement21.appendChild(document.createTextNode(truncateOverflowingValues==true?"true":"false"));			
//		truncateOverflowingValues
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        //this decides the output type
        //profileName+".xml"
        String fileName = (profileName.endsWith(".xml") || profileName.endsWith(".XML"))?profileName:profileName+".xml";
        StreamResult result =  new StreamResult(new FileOutputStream(fileName));
        //this writes the xml file
        transformer.transform(source, result);
	}
}