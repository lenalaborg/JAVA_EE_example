package com.lena;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lgcns.ejb.Jms.Messages2;

/**
 * Servlet implementation class SingletonSessionBeanServletClient
 */
@WebServlet("/Jms2")
public class JmsServletClient2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JmsServletClient2() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

//		try {
//			final Context context = new InitialContext();
//			Messages2 messages = (Messages2) context.lookup("java:global/Messages2");
//			
//			PrintWriter out = response.getWriter();
//			out.println(" ==================================== ");
//			out.println("           1. Send Message");
//			out.println(" ------------------------------------ ");
//	        messages.sendMessage("Hello World!",1);
//	        out.println("Hello World!");
//	        messages.sendMessage("How are you?",4);
//	        out.println("How are you?");
//	        messages.sendMessage("Still spinning?",9);
//	        out.println("Still spinning?");
//	        out.println(" ==================================== ");
//	        out.println("           2. Receive Message");
//	        out.println(" ------------------------------------ ");
//	        out.println(messages.receiveMessage());
//	        out.println(messages.receiveMessage());
//	        out.println(messages.receiveMessage());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

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
