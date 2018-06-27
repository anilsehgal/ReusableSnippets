package com.victor.importer.ui;


import java.util.Date;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import com.victor.importer.schedule.ImportJob;
public class ImportScheduler {  
	public void scheduleImport(
		String jobName, String schedulerGroup, String triggerName, Date startDate, Date endDate,
		int repeatCount, long repeatInterval) throws Exception{  
		SchedulerFactory schedulerFactory=new StdSchedulerFactory();  
		Scheduler scheduler=schedulerFactory.getScheduler();  
		scheduler.start();  
		JobDetail jobDetail=new JobDetail(jobName,schedulerGroup,ImportJob.class);  
		SimpleTrigger simpleTrigger=new SimpleTrigger(triggerName,schedulerGroup,startDate,  
				endDate,repeatCount,repeatInterval*1000L);  
		scheduler.scheduleJob(jobDetail, simpleTrigger);  
	}  
}