package com.lgcns.ejb.Jms;

import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

@Stateless
public class Messages2 {
	public void sendMessage(String text, int priorityLevel) throws JMSException {
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			Context ctx = new InitialContext();
			ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup("openejb:Resource/connectionFactory2");
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, 1);
			Queue queue = session.createQueue("openejb:Resource/queue1");
			producer = session.createProducer((Destination) queue);
			TextMessage message = session.createTextMessage();
			message.setText("Hello ...This is a sample message..sending from FirstClient : " + text);
			producer.setPriority(priorityLevel);
			producer.send((Message) message);
		} catch (Exception exception) {

		} finally {
			if (producer != null)
				try {
					producer.close();
				} catch (Exception exception) {
				}
			if (session != null)
				try {
					session.close();
				} catch (Exception exception) {
				}
			if (connection != null)
				try {
					connection.close();
				} catch (Exception exception) {
				}
		}
	}

	public String receiveMessage() throws JMSException {
		String res = "";
		Connection connection = null;
		Session session = null;
		MessageConsumer consumer = null;
		try {
			Context ctx = new InitialContext();
			ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup("openejb:Resource/connectionFactory2");
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, 1);
			Queue queue = session.createQueue("openejb:Resource/queue1");
			consumer = session.createConsumer((Destination) queue);
			Message message = consumer.receive(1000L);
			String receiveMessage = "";
			if (message != null && message instanceof TextMessage) {
				TextMessage txtMsg = (TextMessage) message;
				System.out.println("Received: " + txtMsg.getText());
				res = "Received: " + txtMsg.getText();
			} else {
				System.out.println("Received nothing ");
				res = "Received nothing ";
			}
		} catch (Exception exception) {

		} finally {
			if (consumer != null)
				try {
					consumer.close();
				} catch (Exception exception) {
				}
			if (session != null)
				try {
					session.close();
				} catch (Exception exception) {
				}
			if (connection != null)
				try {
					connection.close();
				} catch (Exception exception) {
				}
		}
		return res;
	}
}
