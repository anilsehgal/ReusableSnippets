package com.victor.importer.supportMethods;

import java.util.ArrayList;
import java.util.List;

import com.victor.importer.constants.FileColumnDataTypeMapping;
import com.victor.importer.constants.ImporterBean;

public class ImportValidator {
	public static String validateImport(Object[][] colMapping, Object[][] data){
		
		String returnString = "Complete";
		FieldValidator fieldValidator = new FieldValidator();
		
		List<FileColumnDataTypeMapping> mappingList = getDataMapping(colMapping);
		
		for(FileColumnDataTypeMapping mapping : mappingList){
			for(int i=0;i < data.length;i++){
				String toTest= data[i][Integer.parseInt(mapping.getFileColumn())].toString();
				if( mapping.getDataType().equals(ColumnDataTypes.SQL_SERVER_NUMERIC) ||
					mapping.getDataType().equals(ColumnDataTypes.SQL_SERVER_DECIMAL) ||	
					mapping.getDataType().equals(ColumnDataTypes.SQL_SERVER_INT) ||
					mapping.getDataType().equals(ColumnDataTypes.ORACLE_LONG) ||
					mapping.getDataType().equals(ColumnDataTypes.ORACLE_NUMBER)
				){
					boolean isValid = fieldValidator.isFieldNumeric(toTest);
					if(!isValid){
						returnString = "The file column "+mapping.getFileColumn()+" in row "+(i+1)+" is not numeric!!";
						break;
					}
				}
				if(mapping.getDataType().equals(ColumnDataTypes.SQL_SERVER_BIT)
				){
					boolean isValid = fieldValidator.isFieldBit(toTest);
					if(!isValid){
						returnString = "The file column "+mapping.getFileColumn()+" in row "+(i+1)+" is not a valid BIT datatype!!";
						break;
					}
				}
				if(mapping.getDataType().equals(ColumnDataTypes.SQL_SERVER_DATETIME) ||
						mapping.getDataType().equals(ColumnDataTypes.SQL_SERVER_DATE) ||
						mapping.getDataType().equals(ColumnDataTypes.ORACLE_DATE)
				){
					boolean isValid = fieldValidator.isFieldDateTime(toTest, ImporterBean.getDateFormat());
					if(!isValid){
						returnString = "The file column "+mapping.getFileColumn()+" in row "+(i+1)+" is not a valid Date datatype!!";
						break;
					}
				}
				if(mapping.getIsNullable().equals("NO") || mapping.getIsNullable().equals("N")){
					boolean isValid = fieldValidator.isFieldNull(toTest);
					if(isValid){
						returnString = "The file column "+mapping.getFileColumn()+" in row "+(i+1)+" is Blank or NULL but the column is not nullable!!";
						break;
					}
				}
			}
		}		
		return returnString;		
	}
	public static List<FileColumnDataTypeMapping> getDataMapping(Object[][] colMapping){
		List<FileColumnDataTypeMapping> returnList = new ArrayList<FileColumnDataTypeMapping>();
		FileColumnDataTypeMapping columnDataTypeMapping;
		for(int i=0;i < colMapping.length;i++){
			if(colMapping[i][5]!=null){
				columnDataTypeMapping = new FileColumnDataTypeMapping();
				columnDataTypeMapping.setDataType((String) colMapping[i][1]);
				columnDataTypeMapping.setIsNullable((String) colMapping[i][3]);
				columnDataTypeMapping.setFileColumn((String) colMapping[i][5]);		
				returnList.add(columnDataTypeMapping);
			}
		}	
		return returnList;
	}
}
//_Test_Anil_05_20_2011
