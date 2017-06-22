package com.jms.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSConsumer {
	
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	
	private static final String PASSWORLD = ActiveMQConnection.DEFAULT_PASSWORD;
	
	private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
	
	public static void main(String[] args) {
		ConnectionFactory connectionFactory ;
		Connection connection = null;
		Session session;
		Destination destination;
		MessageConsumer messageConsumer;
		
		connectionFactory = new ActiveMQConnectionFactory(JMSConsumer.USERNAME, JMSConsumer.PASSWORLD, JMSConsumer.URL);
		
		try {
			connection = connectionFactory.createConnection();
			connection.start();
//			createSession(paramA,paramB);
//			paramA是设置事务的，paramB设置acknowledgment mode
//			paramA设置为false时：paramB的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
//			paramA设置为true时：paramB的值忽略， acknowledgment mode被jms服务器设置为SESSION_TRANSACTED 。
//			Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。
//			Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会删除消息。
//			DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。在需要考虑资源使用时，这种模式非常有效。
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("helloworld");
			messageConsumer = session.createConsumer(destination);
			
			while (true) {
				TextMessage message = (TextMessage) messageConsumer.receive(10000);
				if(message!=null){
					System.out.println("收到的消息"+message.getText());
				}else{
					break;
				}
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
