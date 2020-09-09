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
@WebServlet("/JmsOrder")
public class JmsOrderServletClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JmsOrderServletClient() {
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
			Messages messages = (Messages) context.lookup("java:global/Messages");

			PrintWriter out = response.getWriter();

			receiveJmsOrderThread tc1 = new receiveJmsOrderThread("[Thread1] ", messages, out);
			tc1.start();
			
			Thread.sleep(200);
			out.println(" ==================================== ");
			out.println("           1. Send Message");
			out.println(" ------------------------------------ ");
			for (int i = 0; i < 20; i++) {
				messages.sendMessage("[idx : " + i + "] Message Order Test!");
			}
			Thread.sleep(2000);
			tc1.loop=false;
			tc1.join();
			
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

class receiveJmsOrderThread extends Thread { // 'Thread' Class를 상속받는다
	String thread_name;
	Messages messages;
	PrintWriter out;
	Boolean loop = true;

	public receiveJmsOrderThread(String name, Messages messages, PrintWriter out) {
		this.thread_name = name;
		this.messages = messages;
		this.out = out;
	}

	public void run() {
		out.println(" ==================================== ");
		out.println("           2. Receive Message");
		out.println(" ------------------------------------ ");
		try {
			while (loop) {
				out.println("["+thread_name+"] "+messages.receiveMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
