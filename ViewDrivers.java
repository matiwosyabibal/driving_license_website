package com.dlms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ViewDrivers
 */
@WebServlet("/ViewDrivers")
public class ViewDrivers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  String driverName="com.mysql.cj.jdbc.Driver";
	    String dbUrl="jdbc:mysql://localhost:3306/dlms_db";
		String dbusername="root";
		String dbpassword="root";
		 Connection conn=null;
		    Statement stmt=null;
		    ResultSet rs=null;
		    PreparedStatement ps=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewDrivers() {
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
		        if(!session.getAttribute("Autority").equals("Owner")) {
		         response.sendRedirect("Login"); //redirect to Home page

		        }
		        String Session_user_name=null;
		        session=request.getSession();
		         if(session!=null) {
		        	 Session_user_name=(String)session.getAttribute("session_username");
		         //user_type=(String)session.getAttribute("utype");
		         }	        out.print("<html>");
	        out.print("<head>");
	        out.print("<title>User Registration Page</title>");
			out.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"mainstyle.css\">");
	        out.print("</head>");
	        out.print("<body style='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:21rem;'>"); 
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/OwnerHeader");
	        dispatcher.include(request, response);
	        out.print("<table border=1 style=width:100%;>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Number</th>");
            out.print("<th>Start Date</th>");
            out.print("<th>Driver ID</th>");

            out.print("</tr>");
            out.print("</thead>");
	        try {
	            Class.forName(driverName);
	            Connection conn = DriverManager.getConnection(dbUrl, dbusername, dbpassword);
	            Statement stmt = conn.createStatement();
	            ResultSet rsDriver = stmt.executeQuery("SELECT * FROM issuelicence");
	            while (rsDriver.next()) {
	                        out.print("<tr>");
	                        out.print("<td>" + rsDriver.getString("id") + "</td>");
	                        out.print("<td>" + rsDriver.getString("start_date") + "</td>");
	                        out.print("<td>" + rsDriver.getString("Drivers_id") + "</td>");
	                        out.print("</tr>");

	                   
	                
	            }
	        } catch (SQLException | ClassNotFoundException e) {
	            e.getMessage();
	        }
            out.print("</tbody>");
            out.print("</table>");
	        out.print("</div>");
	        out.print("</body>");
	        out.print("</html>");

		
		
	}

}
