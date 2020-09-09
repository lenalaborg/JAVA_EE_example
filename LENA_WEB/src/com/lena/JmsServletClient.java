package com.lena;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.ejb.embeddable.EJBContainer;
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
@WebServlet("/Jms")
public class JmsServletClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JmsServletClient() {
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
			out.println(" ==================================== ");
			out.println("           1. Send Message");
			out.println(" ------------------------------------ ");
	        messages.sendMessage("Hello World!");
	        out.println("Hello World!");
	        messages.sendMessage("How are you?");
	        out.println("How are you?");
	        messages.sendMessage("Still spinning?");
	        out.println("Still spinning?");
	        out.println(" ==================================== ");
	        out.println("           2. Receive Message");
	        out.println(" ------------------------------------ ");
	        out.println(messages.receiveMessage());
	        out.println(messages.receiveMessage());
	        out.println(messages.receiveMessage());
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
