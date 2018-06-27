package com.victor.multiUtilityToolkit.jms.jmsMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.XAQueueConnection;
import javax.jms.XAQueueConnectionFactory;

import com.tibco.tibjms.admin.DestinationBridgeInfo;
import com.tibco.tibjms.admin.QueueInfo;
import com.tibco.tibjms.admin.TibjmsAdmin;
import com.victor.multiUtilityToolkit.bean.StaticBean;
import com.victor.multiUtilityToolkit.jms.constants.JMSConstants;
import com.victor.multiUtilityToolkit.jms.ui.MonitorMain;

public class GenericJMSMethods {
	public List<String> getQueuesOnProvider(String provider,String user,String pass) throws Exception{
		List<String> queueList = new ArrayList<String>();
		MonitorMain.logDetail+="\nConnecting to server: "+provider+" with "+user+"//"+pass+"\n";
		
		TibjmsAdmin tJAdmin = new TibjmsAdmin(provider,user,pass);
		MonitorMain.logDetail+="connected..\nFetching Queues..\n";
		
		QueueInfo[] qi= tJAdmin.getQueues();
		for(int i=0;i<qi.length;i++){
			MonitorMain.logDetail+=qi[i].getName().toString().trim()+"\n";
			queueList.add(qi[i].getName().toString().trim());
		}
		
		return queueList;
	}
	public List<String> getDestsOnProvider(String provider,String user,String pass) throws Exception{
		List<String> queueList = new ArrayList<String>();
		MonitorMain.logDetail+="\nConnecting to server: "+provider+" with "+user+"//"+pass+"\n";
		
		TibjmsAdmin tJAdmin = new TibjmsAdmin(provider,user,pass);
		MonitorMain.logDetail+="connected..\nFetching Destinations..\n";
		
		DestinationBridgeInfo[] di= tJAdmin.getDestinationBridges(0, null);
		for(int i=0;i<di.length;i++){
			MonitorMain.logDetail+=di[i].getSourceName().toString().trim()+"\n"+di[i].getTargetName().toString().trim()+"\n";
			queueList.add(di[i].getSourceName().toString().trim());
			queueList.add(di[i].getTargetName().toString().trim());
		}
		MonitorMain.logDetail+="\nDestinations fetched";
		
		return queueList;
	}
	public XAQueueConnection createConnection(String serverUrl,String userName,String password){
		XAQueueConnection connection = null;
		MonitorMain.logDetail+="\nConnecting to server: "+serverUrl+" with "+userName+"//"+password+"\n";
		
		XAQueueConnectionFactory factory;
		try{
			factory = new com.tibco.tibjms.TibjmsXAQueueConnectionFactory(serverUrl);			
			connection = factory.createXAQueueConnection(userName, password);	
			
		}catch(Exception e){
			MonitorMain.logDetail+="\nException Detail:\n"+e+"\n";
			
		}
		MonitorMain.logDetail+="connected";
		
		return connection;
	}
	public List<Message> browseEntireQueue(String serverUrl, String userId, String password, String inQueue) throws Exception{
		Enumeration msgs=null;
		QueueBrowser queueBrowser=null;
		List<Message> msList = null;
		XAQueueConnection connection = null;
		XAQueueConnectionFactory connectionFactory = null;
		MonitorMain.logDetail+="\nConnecting to server: "+serverUrl+" with "+userId+"//"+password+"\n";
		
		connectionFactory =  new com.tibco.tibjms.TibjmsXAQueueConnectionFactory(serverUrl);
		connection = connectionFactory.createXAQueueConnection(userId, password);
		MonitorMain.logDetail+="\nConnected to server: "+serverUrl+" with "+userId+"//"+password+"\n";
		
		connection.start();
		MonitorMain.logDetail+="\nconnected\n";
		
		QueueSession queueSession = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = queueSession.createQueue(inQueue);
		queueBrowser = queueSession.createBrowser(queue);
		MonitorMain.logDetail+="\nBrowser Created\n";
		
		msgs = queueBrowser.getEnumeration();
		MonitorMain.logDetail+="\nBrowser Enumeration received\n";
		
		if ( msgs.hasMoreElements() ) { 
			MonitorMain.logDetail+="\nMessages Found\n";
			
			msList = Collections.list(msgs);
		}else{
			MonitorMain.logDetail+="\nNo Messages Found\n";
			
		}
		if(connection!=null){
			MonitorMain.logDetail+="\nClosing Connection\n";
			
			connection.close();
			MonitorMain.logDetail+="\nClosed Connection\n";
		}
		
		return msList;
	}
	public List<Message> browseWithSelector(String serverUrl, String userId, String password, String inQueue, String selector) throws JMSException{
		Enumeration msgs=null;
		QueueBrowser queueBrowser=null;
		List<Message> msList = null;
		XAQueueConnection connection = null;
		XAQueueConnectionFactory connectionFactory = null;
		MonitorMain.logDetail+="\nConnecting to server: "+serverUrl+" with "+userId+"//"+password+"\n";
		
		connectionFactory =  new com.tibco.tibjms.TibjmsXAQueueConnectionFactory(serverUrl);
		connection = connectionFactory.createXAQueueConnection(userId, password);	
		MonitorMain.logDetail+="\nConnected to server: "+serverUrl+" with "+userId+"//"+password+"\n";
		
		connection.start();
		MonitorMain.logDetail+="\nconnected\n";
		
		QueueSession queueSession = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = queueSession.createQueue(inQueue);
		queueBrowser = queueSession.createBrowser(queue, selector);
		MonitorMain.logDetail+="\nBrowser Created with selector:"+selector+"on queue: "+queue+"\n";
		
		msgs = queueBrowser.getEnumeration();
		MonitorMain.logDetail+="\nBrowser Enumeration received\n";
		
		if ( msgs.hasMoreElements() ) { 
			MonitorMain.logDetail+="\nMessages Found\n";
			msList = Collections.list(msgs);
			
		}else{
			MonitorMain.logDetail+="\nNo Messages Found\n";
			
		}
		if(connection!=null){
			MonitorMain.logDetail+="\nClosing Connection\n";
			
			connection.close();
			MonitorMain.logDetail+="\nClosed Connection\n";
		}
		
		return msList;
	}
	public void sendToQueue(String serverUrl, String userId, String password, String queueName, String data) throws Exception{
		XAQueueConnection connection = null;
		XAQueueConnectionFactory connectionFactory = null;
		MonitorMain.logDetail+="\nConnecting to server: "+serverUrl+" with "+userId+"//"+password+"\n";
		
		connectionFactory =  new com.tibco.tibjms.TibjmsXAQueueConnectionFactory(serverUrl);
		connection = connectionFactory.createXAQueueConnection(userId, password);
		MonitorMain.logDetail+="\nConnected to server: "+serverUrl+" with "+userId+"//"+password+"\n";
		
		connection.start();
		MonitorMain.logDetail+="\nconnected\n";
		
		QueueSession session =connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue= session.createQueue(queueName);
		QueueSender sender = session.createSender(queue);
		MonitorMain.logDetail+="\nSender Created on queue: "+queue+"\n";
		
		TextMessage message = session.createTextMessage(data);
		MonitorMain.logDetail+="\nSending message: "+data+"\n";
		
		sender.send(message);
		MonitorMain.logDetail+="\nMessage Sent\n";
		
		if(connection!=null){
			MonitorMain.logDetail+="\nClosing Connection\n";
			
			connection.close();
			MonitorMain.logDetail+="\nClosed Connection\n";
		}
		
	}
	public void deleteMessage(String serverUrl, String userId, String password, String queueName, String messageID) throws Exception{
		XAQueueConnection connection = null;
		XAQueueConnectionFactory connectionFactory = null;
		MonitorMain.logDetail+="\nConnecting to server: "+serverUrl+" with "+userId+"//"+password+"\n";
		
		connectionFactory =  new com.tibco.tibjms.TibjmsXAQueueConnectionFactory(serverUrl);
		connection = connectionFactory.createXAQueueConnection(userId, password);
		MonitorMain.logDetail+="\nConnected to server: "+serverUrl+" with "+userId+"//"+password+"\n";
		
		connection.start();
		MonitorMain.logDetail+="\nconnected\n";
		
		QueueSession session =connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue= session.createQueue(queueName);
		String selector=JMSConstants.JMS_MESSAGE_ID+JMSConstants.EQUALS+JMSConstants.SINGLE_QUOTE+messageID+JMSConstants.SINGLE_QUOTE;
    	QueueReceiver queueReceiver = session.createReceiver(queue, selector);
    	MonitorMain.logDetail+="\nReceiver Created on queue: "+queue+" with selector: "+selector+"\n";
    	
    	Message message = queueReceiver.receive(2000);
    	MonitorMain.logDetail+="\nMessage Received..\n";
    	
    	/*2000 milliseconds are set for receiving a message*/
    	if (message != null) {
    		MonitorMain.logDetail+="\n"+message+"\n";
    		
    	}
		if(connection!=null){
			MonitorMain.logDetail+="\nClosing Connection\n";
			
			connection.close();
			MonitorMain.logDetail+="\nClosed Connection\n";
		}
		
	}
}
