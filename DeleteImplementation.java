package com.dlms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DeleteImplementation
 */
@WebServlet("/DeleteImplementation")
public class DeleteImplementation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteImplementation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        
		      response.setHeader("Pragma","no-cache");
		      response.setHeader("Cache-Control","no-store");
		      response.setHeader("Expires","0");
		      response.setDateHeader("Expires",-1);
		        HttpSession sessionA = request.getSession(false); // Retrieve the session without creating a new one if it doesn't exist
		        HttpSession session=request.getSession(true);
		        if (session.getAttribute("session_username")==null||session.getAttribute("Autority")==null)
		        {
		        response.sendRedirect("Login");
		         return;
		        }
		        if(!session.getAttribute("Autority").equals("Admin")) {
		         response.sendRedirect("Login"); //redirect to Home page

		        }
		        String Session_user_name=null;
		        session=request.getSession();
		         if(session!=null) {
		        	 Session_user_name=(String)session.getAttribute("session_username");
		         //user_type=(String)session.getAttribute("utype");
		         }        String id = request.getParameter("id");
        out.print("<html>");
        out.print("<head>");
        out.print("<title>User Registration Page</title>");
        out.print("</head>");
        out.print("<body>");
      
        try {
        	  String driverName = "com.mysql.cj.jdbc.Driver";
              String connectionUrl = "jdbc:mysql://localhost:3306/dlms_db";
              String userName = "root";
              String password = "root";
              Connection conn = null;
              PreparedStatement ps = null;
        	out.print("true");
            Class.forName(driverName);
            conn = DriverManager.getConnection(connectionUrl,userName,password);
        	out.print("true");

           
                String sql = "delete FROM users WHERE user_id=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, id);
              int i=  ps.executeUpdate();
              if(i>0) {
            	  RequestDispatcher dispatcher=request.getRequestDispatcher("CongraPage");
            	  dispatcher.include(request, response);
              }
              else {
               	  RequestDispatcher dispatchererror=request.getRequestDispatcher("ErrorPage");
            	  dispatchererror.include(request, response);
              }
        
	}catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        out.print("<h1>Error: " + e.getMessage() + "</h1>");
    }
        out.print("</body>");
        out.print("</html>");
}
}
