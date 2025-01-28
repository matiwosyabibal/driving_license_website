package com.dlms;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class Displyay_register_personal
 */
@WebServlet("/Display_Personal_Info")
public class Display_Personal_Info extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Display_Personal_Info() {
        super();
        // TODO Auto-generated constructor stub
    }
    String driverName="com.mysql.cj.jdbc.Driver";
    String dbUrl="jdbc:mysql://localhost:3306/dlms_db";
	String dbusername="root";
	String dbpassword="root";
	 Connection conn=null;
	    Statement stmt=null;
	    ResultSet rs=null;
	
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		        if(!session.getAttribute("Autority").equals("Registrar")) {
		         response.sendRedirect("Login"); //redirect to Home page

		        }
		        String Session_user_name=null;
		        session=request.getSession();
		         if(session!=null) {
		        	 Session_user_name=(String)session.getAttribute("session_username");
		         //user_type=(String)session.getAttribute("utype");
		         }//         Licence_id="";  
		     	out.print("<html>");
		    	out.print("<head>");
		    	out.print("<title> Register Personal Information</title>");
		    	out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/style1.css' />");
		    	out.print("</head>");
		    	out.print("<body>");
		    	out.print("<header>\r\n"
		    			+ "    <nav class=\"Rnavbar\">\r\n"
		    			+ "    <a href=\"Register_Personal_Information\"> Personal</a>\r\n"
		    			+ "    <a href=\"HealthStatus\">Health Status</a>\r\n"
		    			+ "    <a href=\"Practical_Result\">Practical Result</a>\r\n"
		    			+ "    <a href=\"Theory_Result\">theory Result</a>\r\n"
		    			+ "    <a href=\"Logout\">Logout</a>\r\n"
		    			+ "</nav>\r\n"
		    			+ "</header>  ");
		out.print("<div>");
		out.print(" <a class=\"other\" href=\"Register_Personal_Information\">Add student</a>");
		out.print("	</div>");
		out.print("	<div class=\"table\">");
		

		
		
		try {
			Class.forName(driverName);
		}catch(ClassNotFoundException e) {
			out.print("<h1>"+e.getMessage()+"</h1>");
		}
		
		
		
		try {
			out.print("<table border=1>");
			out.print("  <thead>");
			out.print("<tr>");
			out.print(" <th>First_Name</th>");
			out.print("<th>Middle_Name</th>");
			out.print(" <th>Last_Name</th>");
			out.print(" <th>Gender</th>");
			out.print(" <th>Birth_Date</th>");
			out.print(" <th>worda</th>");
			out.print("<th>Zone</th>");
			out.print("<th>kabala</th>");
			out.print("<th>Phone_Number</th>");
			out.print(" <th>image</th>");
			out.print("<th>Driving Licence No</th>");
			out.print(" <th >Edit</th>");
			out.print(" </tr> ");
			out.print("</thead>");	
			
		 conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);	
		stmt=conn.createStatement();
	   rs=stmt.executeQuery("select * from driverpersonalinformation");
	   
		while(rs.next()) {	
			out.print("<tr> ");
			String Licence_id=rs.getString("LISENCE_ID");
			out.print("<td>"+rs.getString("fname") + "</td><td>" +rs.getString("mname")+"</td>");
			out.print("<td>"+rs.getString("lname") + "</td><td>" +rs.getString("gender")+"</td>");
			out.print("<td>"+rs.getString("brithdate") + "</td><td>" +rs.getString("woreda")+"</td>");
			out.print("<td>"+rs.getString("zone") + "</td><td>" +rs.getString("kebelie")+"</td>");
			out.print("<td>"+rs.getString("phone") + "</td>");
			out.print("<td>"+rs.getString("image") + "</td><td>" +rs.getString("Drivers_id")+"</td>") ;
			out.print("<td class=TB><form action=\"Update_Personal_Info?id=" + Licence_id + "\" method=\"post\" enctype=\"multipart/form-data\"><input type='submit' name='update' value='Update'></form></td><td></td>");

			out.print("</tr> ");	
		}		
		out.print("</table>");
	
		}catch(Exception e) {
			out.print("<h1>"+e.getMessage()+"</h1>");
		}		
		out.print("</body>");
		out.print("</html>");
	
	}

}
