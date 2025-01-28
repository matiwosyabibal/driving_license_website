
package com.dlms;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Theory_Result
 */
@WebServlet("/Theory_Result")
public class Theory_Result extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Theory_Result() {
        super();
        // TODO Auto-generated constructor stub
    }
    String driverName="com.mysql.cj.jdbc.Driver";
  	String dbUrl="jdbc:mysql://localhost:3306/dlms_db";
  	String dbpassword="root";
  	String dbusername="root";
  	
  	Connection conn=null;
  	PreparedStatement ps=null;
  	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	         }		try {
		out.print("<html>");
		out.print("<head>");
		out.print("<title> Register Personal Information</title>");
		out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/style1.css' />");
		out.print("</head>");
		out.print("<body style='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:18rem;'>");
		out.print("	<header>");
		out.print(" <nav class=Rnavbar>");
		out.print("<a href=Register_Personal_Information >Personal</a>");
		out.print("<a href=HealthStatus>Health Status</a>");
		out.print("<a href=Practical_Result>Practical Result</a>");
		out.print("<a href=Theory_Result>Theory Result</a>");
		out.print("<a href=Logout>Logout</a>");
		out.print("</nav>");
		out.print("</header> ");
		
		String Submit=request.getParameter("resultT");
		
		if(Submit==null) {
		out.print("	<form   action=Theory_Result class=H method=post>");
		out.print("<div class=Bform2>");
		out.print("<div class=lo p>");
		out.print("<label for=Dl>Driving License Number</label>");
		out.print(" <input type=\"number\" name=\"license_id\" id=\"Dl\" required>");
		out.print(" </div>");
		out.print("<div class=\"lo p\">");
		out.print("<label for=\"year\">Year</label>");
		out.print("<input type=date name=year id=\"year\" required>");
		out.print("</div>");
		out.print("<div class=\"lo p\">");
		out.print("<label for=\"tresult\"> Theory Result</label>");
		out.print("<input type=\"number\" max=\"50\" name=Tresult id=\"tresult\" required>");
		out.print("</div>");
		out.print("<br><br>");
		out.print("<input  class=\"stu\"  type=\"submit\" name=resultT value=\"Register\"> ");
		out.print("<a href=\"DisplayTheory\">Update</a> ");
		out.print("</div>");
		out.print("</form>");
		
		}else {
			
			Connection conn=null;
			PreparedStatement ps=null;
			PreparedStatement ps1=null;
		
			 ResultSet rs=null;
			
			 int Driver_id=0;
			 
			String License_id=request.getParameter("license_id");
			String Year=request.getParameter("year");
			
			String Tresult=request.getParameter("Tresult");
			int tr=Integer.parseInt(Tresult);
			try {
				Class.forName(driverName);
			}catch(ClassNotFoundException  e) {
				out.print("<h1>"+e.getMessage()+"</h1>");
			}
			conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);
			try {
				String sql1="select Drivers_id  from driverpersonalinformation where LISENCE_ID ='"+License_id+"' ";
				
				ps1=conn.prepareStatement(sql1);
				rs=ps1.executeQuery();
				if(rs.next()) {
					
					 Driver_id=rs.getInt("Drivers_id");
					 
						String sql="insert into theoryresult(Drivers_id,year,thoery_result)values(?,?,?)";
						 ps = conn.prepareStatement(sql);
						ps.setInt(1,Driver_id);
						ps.setString(2, Year);
						ps.setInt(3, tr);
						int i=ps.executeUpdate();
						if(i>0) {
						out.print("<h1 style='color: rgb(25,255,20); text-align: left; font-size: 30px;'>Data inserted sussfully! </h1>");	
						}else {
							out.print("<h1>"+ "Data not inserted!" +"</h1>");
						} 
					 
					 
					 				 	 
					 
				}else {
					out.print("<h1 style='color: rgb(255,10,25); text-align: center; font-size: 30px;'>These license_id is not registerd please register first!</h1>");
				}
					
			}catch(SQLException e) {
				out.print("<h1>"+" error on goto other database table "+e.getMessage()+"</h1>");
			}
			
		
			
			
		
			
		
		
			
			
			 
			 
			 
			
			
			
			
		}
		
		
	
		
	
        
    
        
       
   
   
        
        
  
    
        
       
  
   
   
     
   
  
		
		out.print("</body>");
		out.print("</html>");
		}catch(Exception e) {
			out.print("<h1>"+ e.getMessage() +"</h1>");
		}
	}

}
