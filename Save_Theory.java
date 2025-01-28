package com.dlms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Save_Theory
 */
@WebServlet("/Save_Theory")
public class Save_Theory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Save_Theory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
    private String driverName = "com.mysql.cj.jdbc.Driver";
    private String dbUrl = "jdbc:mysql://localhost:3306/dlms_db";
    private String dbUsername = "root";
    private String dbPassword = "root";
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 response.setHeader("Pragma","no-cache");
	      response.setHeader("Cache-Control","no-store");
	      response.setHeader("Expires","0");
	      response.setDateHeader("Expires",-1);
	        PrintWriter out = response.getWriter();
	        HttpSession sessionA = request.getSession(false); // Retrieve the session without creating a new one if it doesn't exist
	        HttpSession session=request.getSession(true);
	        if (session.getAttribute("session_username")==null||session.getAttribute("Autority")==null)
	        {
	        response.sendRedirect("Login");
	         return;
	        }
	        if(!session.getAttribute("Autority").equals("Registrar")) {
	         response.sendRedirect("Login"); //redirect to Home page

	        }
	        String Session_user_name=null;
	        session=request.getSession();
	         if(session!=null) {
	        	 Session_user_name=(String)session.getAttribute("session_username");
	         //user_type=(String)session.getAttribute("utype");
	         }        Connection conn = null;
    
        PreparedStatement ps3 = null;
      
       
        String  Save=request.getParameter("save");
		 

if(Save!=null) {
	 out.println("<html>");
     out.println("<head>");
     out.println("<title>Update Theory Result</title>");
     out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/style1.css' />");
     out.println("</head>");
     out.println("<body style='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:18rem;'>");
     String year = request.getParameter("year");
     String Tresult = request.getParameter("tresult");
     int pr = Integer.parseInt(Tresult);
     String driverId = request.getParameter("id");
           // Update the practical result in the database
     try {
    	Class.forName(driverName); 
    	conn=DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
    	
    	String sql2 = "update theoryresult SET year=?, thoery_result=? where Drivers_id=?";	
    	ps3 = conn.prepareStatement(sql2);
        ps3.setString(1, year);
        ps3.setInt(2, pr);
        ps3.setString(3, driverId);
        
        int rowsUpdated = ps3.executeUpdate();
      
        if (rowsUpdated > 0) {
            out.println("<h2>Theory result updated successfully!</h2>");
        } else {
            out.print("<h1>" + "Error on execution of update!" + "</h1>");
        }
    	
     }catch(Exception e) {
    	 out.print(e.getMessage());
     }
     

     out.println("</body>");
     out.println("</html>");
     
	}

}}
