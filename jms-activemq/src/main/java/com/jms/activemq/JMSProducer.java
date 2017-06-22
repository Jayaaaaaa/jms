package com.jms.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 
 * 消息的生产者
 * @author Jaya
 *
 */

public class JMSProducer {
	
	//默认的用户名
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	//默认的密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	//默认的url地址
	private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;
	//发送消息的数量
	private static final int SENDNUM = 10;
	
	public static void main(String[] args) {
		//连接工厂
		ConnectionFactory connectionFactory;
		//连接
		Connection connection = null;
		//会话
		Session session;
		//目的地
		Destination destination;
		//消息的生产者
		MessageProducer messageProducer;
		//实例化ConnectionFactory
		connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME,JMSProducer.PASSWORD,JMSProducer.URL);
		
		try {
			//通过工厂获取连接
			connection = connectionFactory.createConnection();
			//启动连接
			connection.start();
			//创建会话
			session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
			//创建一条'helloworld'消息
			destination = session.createQueue("helloworld");
			//创建消息生产者
			messageProducer = session.createProducer(destination);
			//发送消息
			sendMessage(session,messageProducer);
			
			session.commit();
			
		} catch (Exception e) {
			
		}finally {
			if (connection!=null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}

		
	}
	
	/**
	 * 发送消息
	 * @param session
	 * @param messageProducer
	 */
	private static void sendMessage(Session session, MessageProducer messageProducer) {
		for (int i = 0; i < JMSProducer.SENDNUM; i++) {
			try {
				TextMessage message = session.createTextMessage("发送消息的数量为"+i);
				System.out.println("发送消息"+i);
				messageProducer.send(message);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	

}
