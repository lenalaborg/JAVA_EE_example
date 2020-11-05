package com.lena;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * Servlet implementation class myTestServlet
 */
@WebServlet("/TransactionManagerServlet")
public class TransactionManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 15245263L;
       
	private int callCnt = 0;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("===== Servlet doGet start(" + ++callCnt +")");
		System.out.println("Thread id="+Thread.currentThread().getId());
		long doGetStart = System.currentTimeMillis();
		UserTransaction ut = null;
		TransactionManager tm = null;
		Connection conn1 = null;
		Connection conn2 = null;
		try {
			Context initContext = new InitialContext();
			ut = (UserTransaction)initContext.lookup("java:comp/UserTransaction");
			ut.begin();		

			tm = (TransactionManager)initContext.lookup("java:comp/TransactionManager");
			Transaction tx = tm.suspend();

			DataSource ds1 = (DataSource) initContext.lookup("java:openejb/Resource/jdbc/xatest1");
			DataSource ds2 = (DataSource) initContext.lookup("java:openejb/Resource/jdbc/xatest2");
			System.out.println("datasource.tomcat ds1 type : " + ds1.getClass().getName());
			System.out.println("datasource.tomcat ds2 type : " + ds2.getClass().getName());
			
			long start = System.currentTimeMillis();
			System.out.println("DataSource lookup 소요시간="+(start-doGetStart) + " ms");
			conn1 = ds1.getConnection();
			long end = System.currentTimeMillis();
			System.out.println("conn1 소요시간="+(end-start) + " ms");
			System.out.println("conn1="+conn1.hashCode());
			conn2 = ds2.getConnection();
			long end2 = System.currentTimeMillis();
			System.out.println("conn2 소요시간="+(end2-end) + " ms");
			System.out.println("conn2="+conn2.hashCode());
			
			PreparedStatement sel1 = conn1.prepareStatement("SELECT MAX(ID) FROM TB_XA_TEST");
			ResultSet rs1 = sel1.executeQuery();
			rs1.next();
			int Id1 = rs1.getInt(1);
			sel1.close();
			PreparedStatement sel2 = conn2.prepareStatement("SELECT MAX(ID) FROM TB_XA_TEST");
			ResultSet rs2 = sel2.executeQuery();
			rs2.next();
			int Id2 = rs2.getInt(1);
			sel2.close();
			PreparedStatement pstmt1 = conn1.prepareStatement("INSERT INTO TB_XA_TEST VALUES(?)");
			pstmt1.setInt(1, Id1+1);
			pstmt1.executeUpdate();
			pstmt1.close();
			PreparedStatement pstmt2 = conn2.prepareStatement("INSERT INTO TB_XA_TEST VALUES(?)");
			pstmt2.setInt(1, Id2+1);
			pstmt2.executeUpdate();
			pstmt2.close();
			
			tm.resume(tx);

			ut.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			if(ut != null){
				try{
					ut.rollback();
				} catch(Exception ignore){}
			}
		} finally {
			try {
				conn1.close();
				conn2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
						
			response.getWriter().print("finished8");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
