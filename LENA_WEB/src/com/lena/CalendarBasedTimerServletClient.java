package com.lena;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lgcns.ejb.CalendarBasedTimer.TimerBean;

/**
 * Servlet implementation class CalendarBasedTimerServletClient
 */
@WebServlet("/CalendarBasedTimer")
public class CalendarBasedTimerServletClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	@EJB
    private TimerBean bean;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalendarBasedTimerServletClient() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		  
	    // Get the execution record of the scheduled method 
	    List<String> list = bean.getList();
	    Iterator<String> iterator = list.iterator();
	    while (iterator.hasNext()) {
	      String message = iterator.next();
	      sb.append(message + System.lineSeparator());
	    }
	    
	    PrintWriter out = response.getWriter();
		out.println("Server call count : " + sb.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
