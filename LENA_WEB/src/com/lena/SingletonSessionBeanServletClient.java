package com.lena;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lgcns.ejb.SingletonSessionBean.Counter;

/**
 * Servlet implementation class SingletonSessionBeanServletClient
 */
@WebServlet("/SingletonSessionBean")
public class SingletonSessionBeanServletClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private Counter bean;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SingletonSessionBeanServletClient() {
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
			int cnt = bean.getHits();
			PrintWriter out = response.getWriter();
			out.println("Server call count : " + cnt);
		} catch (Exception e) {
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
