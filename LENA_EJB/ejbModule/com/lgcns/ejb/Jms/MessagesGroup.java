package com.lgcns.ejb.Jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@Stateless
public class MessagesGroup {

	@Resource
	private ConnectionFactory connectionFactory;

	@Resource
	private Queue chatQueue;

//https://livebook.manning.com/book/activemq-in-action/chapter-12/26
	public void sendMessage(String text, String group) throws JMSException {

		Connection connection = null;
		Session session = null;

		try {
			connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(chatQueue);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			// Create a message
			TextMessage message = session.createTextMessage(text);

			// set Message Group
			 message.setStringProperty("JMSXGroupID", group);

			// Tell the producer to send the message
			producer.send(message);
		} finally {
			// Clean up
			if (session != null)
				session.close();
			if (connection != null)
				connection.close();
		}
	}

	public String receiveMessage() throws JMSException, InterruptedException {

		Connection connection = null;
		Session session = null;
		MessageConsumer consumer = null;

		Connection connection2 = null;
		Session session2 = null;
		MessageConsumer consumer2 = null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();

			connection2 = connectionFactory.createConnection();
			connection2.start();

			// Create a Session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			session2 = connection2.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Create a MessageConsumer from the Session to the Topic or Queue
			consumer = session.createConsumer(chatQueue);
			consumer2 = session2.createConsumer(chatQueue);

			// Wait for a message
			String res = "";
			for (int i = 0; i < 10; i++) {
				
				Thread.sleep(1000);
				
				TextMessage message = (TextMessage) consumer.receive(100);

				String newTxt = "";
				if (message != null && message.getText() != null) {
					newTxt = message.getText();
				}
				res = res + "[Receive con1] " + newTxt;
				res += System.lineSeparator();
				System.out.println("consumer1 : " + newTxt);

				TextMessage message2 = (TextMessage) consumer2.receive(100);

				String newTxt2 = "";
				if (message2 != null && message2.getText() != null) {
					newTxt2 = message2.getText();
				}
				res = res + "[Receive con2] " + newTxt2;
				res += System.lineSeparator();
				System.out.println("consumer2 : " + newTxt2);
			}
			
//			for (int i = 0; i < 6; i++) {
//				if (i == 2)
//					consumer.close();
//				if (i < 2) {
//					TextMessage message = (TextMessage) consumer.receive(1000);
//
//					String newTxt = "";
//					if (message != null && message.getText() != null) {
//						newTxt = message.getText();
//					}
//					res = res + "consumer1 : " + newTxt;
//					res += System.lineSeparator();
//				} else {
//
//					TextMessage message2 = (TextMessage) consumer2.receive(1000);
//
//					String newTxt2 = "";
//					if (message2 != null && message2.getText() != null) {
//						newTxt2 = message2.getText();
//					}
//					res = res + "consumer2 : " + newTxt2;
//					res += System.lineSeparator();
//				}
//			}
			return res;
		} finally {
			if (consumer != null)
				consumer.close();
			if (session != null)
				session.close();
			if (connection != null)
				connection.close();
			if (consumer2 != null)
				consumer2.close();
			if (session2 != null)
				session2.close();
			if (connection2 != null)
				connection2.close();
		}
	}
}