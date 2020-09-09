package com.lena;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.lgcns.ejb.Jms.*;

/**
 * Servlet implementation class SingletonSessionBeanServletClient
 */
@WebServlet("/JmsGroup")
public class JmsGroupServletClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JmsGroupServletClient() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			
			final Context context = new InitialContext();
			MessagesGroup messages = (MessagesGroup) context.lookup("java:global/MessagesGroup");

			PrintWriter out = response.getWriter();
			
			receiveThread tc1 = new receiveThread("[Thread1] ",messages,out);
			tc1.start();
			Thread.sleep(1500);
			out.println(" ==================================== ");
			out.println("           1. Send Message");
			out.println(" ------------------------------------ ");
	        messages.sendMessage("[GRP-1] Hello World!","GRP-1");
	        out.println("[Send] [GRP-1] Hello World!");
	        messages.sendMessage("[GRP-1] How are you?","GRP-1");
	        out.println("[Send] [GRP-1] How are you?");
	        messages.sendMessage("[GRP-2] Still spinning?","GRP-2");
	        out.println("[Send] [GRP-2] Still spinning?");
	        messages.sendMessage("[GRP-1] Hello World!","GRP-1");
	        out.println("[Send] [GRP-1] Hello World!");
	        messages.sendMessage("[GRP-1] How are you?","GRP-1");
	        out.println("[Send] [GRP-1] How are you?");
	        messages.sendMessage("[GRP-2] Still spinning?","GRP-2");
	        out.println("[Send] [GRP-2] Still spinning?");
	        
	        tc1.join();
//			out.println(" ==================================== ");
//			out.println("           2. Receive Message");
//			out.println(" ------------------------------------ ");
//			out.println(messages.receiveMessage());
			//messages.sendMessage("Do you receive?", "GRP-2");
//	        out.println(messages.receiveMessage());
//	        out.println(messages.receiveMessage());
//	        out.println(messages.receiveMessage());
//	        out.println(messages.receiveMessage());
//	        out.println(messages.receiveMessage());
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
class receiveThread extends Thread { // 'Thread' Class를 상속받는다
	String thread_name;
	MessagesGroup messages;

	PrintWriter out;

	public receiveThread(String name, MessagesGroup messages, PrintWriter out) {
		this.thread_name = name;
		this.messages = messages;
		this.out = out;
	}

	public void run() {
		out.println(" ==================================== ");
		out.println("           2. Receive Message");
		out.println(" ------------------------------------ ");
		try {
			out.println(messages.receiveMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}

class sendThread extends Thread { // 'Thread' Class를 상속받는다
	String thread_name;
	MessagesGroup messages;

	PrintWriter out;

	public sendThread(String name, MessagesGroup messages, PrintWriter out) {
		this.thread_name = name;
		this.messages = messages;
		this.out = out;
	}

	
	
	public void run() {
		out.println(" ==================================== ");
		out.println("           1. Send Message");
		out.println(" ------------------------------------ ");
		try {
			messages.sendMessage("Hello World!", "GRP-1");
			out.println("Hello World!");
			messages.sendMessage("How are you?", "GRP-1");
			out.println("How are you?");
			messages.sendMessage("Still spinning?", "GRP-2");
			out.println("Still spinning?");
			messages.sendMessage("Hello World!", "GRP-1");
			out.println("Hello World!");
			messages.sendMessage("How are you?", "GRP-1");
			out.println("How are you?");
			messages.sendMessage("Still spinning?", "GRP-2");
			out.println("Still spinning?");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
