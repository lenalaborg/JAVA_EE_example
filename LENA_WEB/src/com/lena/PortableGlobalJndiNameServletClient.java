package com.lena;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lgcns.ejb.PortableGlobalJndiName.PortableGlobalJndiName;

/**
 * Servlet implementation class PortableGlobalJndiNameServletClient
 */
@WebServlet("/PortableGlobalJndiName")
public class PortableGlobalJndiNameServletClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();

		sb.append("These are greetings from the welcome servlet, " + "and it will now look up a session bean "
				+ "by its portable global JNDI name. <br>");

		try {
			Context context = new InitialContext();
			PortableGlobalJndiName bean = (PortableGlobalJndiName) context.lookup("java:global/PortableGlobalJndiName");
			String s = bean.sayHello();
			sb.append(s);

			PrintWriter out = response.getWriter();
			out.println(bean.sayHello());
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

}
