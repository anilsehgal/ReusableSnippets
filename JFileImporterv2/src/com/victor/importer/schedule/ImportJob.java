package com.victor.importer.schedule;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.victor.importer.constants.DBTableColumnBean;
import com.victor.importer.constants.ImporterBean;
import com.victor.importer.constants.ProfileBean;
import com.victor.importer.constants.ScheduleSettings;
import com.victor.importer.supportMethods.AFileReader;
import com.victor.importer.supportMethods.DBImporter;
import com.victor.importer.supportMethods.RunCommand;
import com.victor.importer.ui.ImportSchedulerSplash;

public class ImportJob implements Job {  
	public void execute(JobExecutionContext arg0){  
		File file = null;
		
		if((new Date()).getTime() - ScheduleSettings.getJobRunDate().getTime() >= ((ScheduleSettings.getIntervalInSecs()-1)*1000)){
			ImportSchedulerSplash.secondsToNextImport.setText(Integer.toString((int)ScheduleSettings.getIntervalInSecs()));
			ScheduleSettings.setJobRunDate(new Date());
			RunCommand commandRunner = new RunCommand();
			String cmdReturned = null;
			try{
				ImportSchedulerSplash.importStatusArea.append("\n"+new Date()+":INFO:  Importing...");
				ProfileBean loadedProfile = ImporterBean.getLoadedProfile();
				
				if(loadedProfile.getPreImportCmd()!=null && loadedProfile.getPreImportCmd().trim().length()>0){
					ImportSchedulerSplash.importStatusArea.append("\n"+
							(new Date())+":INFO: Running Pre Session Command...");

					cmdReturned=commandRunner.executeCommand(loadedProfile.getPreImportCmdType(), loadedProfile.getDriverName(), loadedProfile.getDburl(), loadedProfile.getUserName(), 
							loadedProfile.getPassword(), loadedProfile.getPreImportCmd(), loadedProfile.getPreImportCmd());
					ImportSchedulerSplash.importStatusArea.append("\n"+
							(new Date())+":INFO: Pre Session Command exitCode: "+cmdReturned);
				}
				//getContinueImportOnPreCmdFailure
				ImportSchedulerSplash.importStatusArea.append("\n"+
						(new Date())+":INFO: Continue Import If Pre Session Command Fails: "+loadedProfile.getContinueImportOnPreCmdFailure());
				if(loadedProfile.getContinueImportOnPreCmdFailure().equals("N")){
					if(cmdReturned!=null && cmdReturned.equals("failed")){	
						throw new Exception("Stopping Import due to Pre Session Command Failure!!");
					}
				}
				
				
				file = new File(loadedProfile.getFilePath());
				
				if(!file.exists() || (file.exists() && file.length()==0)){
					ImportSchedulerSplash.importStatusArea.append("\n"+
							new Date()+":WARN:  No file detected! <"+loadedProfile.getFilePath()+">"
							+"\n"+(new Date())+":ERR:  Import Failed!");
					
				}else{
					Object[][] rows = null;
					String query = null;
					Object[][] columns = loadedProfile.getColumnMapping();
					List<DBTableColumnBean> columnsToBeInserted = new ArrayList<DBTableColumnBean>();
					DBTableColumnBean columnBean = null;
					
					for(Object[] column : columns){
						columnBean = new DBTableColumnBean();
						columnBean.setColumnName((String) column[0]);
						columnBean.setDataType((String) column[1]);
						columnBean.setCharacterMaximumLength(Integer.parseInt(column[2].toString()));
						columnBean.setIsNullable((String) column[3]);
						columnBean.setPrimaryKey((String) column[4]);
						columnBean.setFileColumn((String) column[5]);
						columnsToBeInserted.add(columnBean);
					}
					AFileReader fileReader = new AFileReader();
					if(loadedProfile.getFlavor().equals("DELIMITED")){
						rows = fileReader.readDelimitedFile(loadedProfile.getFilePath(), loadedProfile.getDelimiter(), Integer.parseInt(loadedProfile.getStartLine()), Integer.parseInt(loadedProfile.getEndRow()));
					}else if(loadedProfile.getFlavor().equals("EXCEL")){
						try {
							
							rows = fileReader.readExcelFile(loadedProfile.getFilePath(), Integer.parseInt(loadedProfile.getSheetNo()), Integer.parseInt(loadedProfile.getStartLine()), Integer.parseInt(loadedProfile.getEndRow()));
						} catch (Exception e) {
							ImportSchedulerSplash.importStatusArea.append("\n"+
									(new Date())+":ERROR:  Error in Reading file..."+e);
						}
					}
					try{
						DBImporter.connectToDatabase(loadedProfile.getDriverName(), loadedProfile.getDburl(), loadedProfile.getUserName(), 
									loadedProfile.getPassword());
					
						for(Object[] row : rows){
							
							if(loadedProfile.getDefaultStrategy().contains("1")){						
								query = DBImporter.createInsertQuery(loadedProfile.getTableName(), columnsToBeInserted, row, loadedProfile.getDateFormat(),loadedProfile.getDriverName(),loadedProfile.isTruncateOverflowingValues());
							}else if(loadedProfile.getDefaultStrategy().contains("2")){
								query = DBImporter.createUpdateQuery(loadedProfile.getTableName(), columnsToBeInserted, row, loadedProfile.getDateFormat(),loadedProfile.getDriverName(),loadedProfile.isTruncateOverflowingValues());
							}
								
							String returned = DBImporter.importRow(query, loadedProfile.getDupRowAction());
							if(returned.equals("Fire Second")){
								if(loadedProfile.getDefaultStrategy().contains("2")){						
									query = DBImporter.createInsertQuery(loadedProfile.getTableName(), columnsToBeInserted, row, loadedProfile.getDateFormat(),loadedProfile.getDriverName(),loadedProfile.isTruncateOverflowingValues());
								}else if(loadedProfile.getDefaultStrategy().contains("1")){
									query = DBImporter.createUpdateQuery(loadedProfile.getTableName(), columnsToBeInserted, row, loadedProfile.getDateFormat(),loadedProfile.getDriverName(),loadedProfile.isTruncateOverflowingValues());
								}							returned =
								DBImporter.importRow(query, loadedProfile.getDupRowAction());
							}
						}
					}catch(Exception e){
						ImportSchedulerSplash.importStatusArea.append("\n"+
								(new Date())+":ERR: Exception in Importing Row!!");
					}finally{
						DBImporter.closeDatabaseConnection();
					}
					ImportSchedulerSplash.importStatusArea.append("\n"+
							(new Date())+":INFO: File Imported Successfully!!");
					//job has completed now
					if(ScheduleSettings.isDeleteFileAfterImport()){
						
						boolean deleted = file.delete();
						if(deleted){				
							ImportSchedulerSplash.importStatusArea.append("\n"+
									(new Date())+":INFO: File Deleted Successfully!!");		
						}else{
							ImportSchedulerSplash.importStatusArea.append("\n"+
									(new Date())+":INFO: File could not be deleted!!");									
						}
					}
					if(loadedProfile.getPostImportCmd()!=null && loadedProfile.getPostImportCmd().trim().length()>0){
						ImportSchedulerSplash.importStatusArea.append("\n"+
								(new Date())+":INFO: Running Post Session Command..");
						cmdReturned=commandRunner.executeCommand(loadedProfile.getPostImportCmdType(), loadedProfile.getDriverName(), loadedProfile.getDburl(), loadedProfile.getUserName(), 
								loadedProfile.getPassword(), loadedProfile.getPostImportCmd(), loadedProfile.getPostImportCmd());
						ImportSchedulerSplash.importStatusArea.append("\n"+
									(new Date())+":INFO: Post Session Command exitCode: "+cmdReturned);							
					}
					
				}
			}catch(Exception e){			
					ImportSchedulerSplash.importStatusArea.append("\n"+
							(new Date())+":ERR: Exception in executing Job:\n"+e);				}
		}
	}

}
