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
 * Servlet implementation class Save
 */
@WebServlet("/Save")
public class Save extends HttpServlet {
	private static final long serialVersionUID = 1L;
    String driverName = "com.mysql.cj.jdbc.Driver";
    String connectionUrl = "jdbc:mysql://localhost:3306/dlms_db?useSSL=false";
    String userName = "root";
    String password = "root";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Save() {
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
		        if(!session.getAttribute("Autority").equals("Registrar")) {
		         response.sendRedirect("Login"); //redirect to Home page

		        }
		        String Session_user_name=null;
		        session=request.getSession();
		         if(session!=null) {
		        	 Session_user_name=(String)session.getAttribute("session_username");
		         //user_type=(String)session.getAttribute("utype");
		         }        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String driverIdpassed=request.getParameter("save");
        //String write = request.getParameter("write");
        String save = request.getParameter("save");
        if(save!=null) {
            out.print("<html>");
            out.print("<head>");
            out.print("<title>User Registration Page</title>");
	    	out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/style.css' />");
            out.print("</head>");
            out.print("<body style='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:18rem;'>");
        	String drivers_id=request.getParameter("Dl");
			String year=request.getParameter("year");
			String  Blood=request.getParameter("Blood");
			String Eye=request.getParameter("eye");
			String Ghr=request.getParameter("ghr");
			
			try {
				Class.forName(driverName);	
			}catch(ClassNotFoundException e) {
				out.print("<h1>"+e.getMessage()+"</h1>");
			}
			 
			try {
		conn=DriverManager.getConnection(connectionUrl,userName,password);		
				
		String sql="UPDATE healthstatus SET year=?,blood_type=?,eye=?,general_health_status=? WHERE Drivers_id=?";	
		ps=conn.prepareStatement(sql);
		
		ps.setString(1,year);
		ps.setString(2,Blood);
		ps.setString(3,Eye);
		ps.setString(4,Ghr);
		ps.setString(5,driverIdpassed);
	int i=ps.executeUpdate();
	if(i>0) {
		out.print("<h1>your data is Succesfully Updated!</h1>");
	}else {
		out.print("<h1> Sorry the Data Is not Updated</h1>");
	}
			}catch(SQLException e) {
				out.print("<h1>"+e.getMessage()+"</h1>");
			}
            out.print("</body>");
            out.print("</html>");
        }
        
	}

}
