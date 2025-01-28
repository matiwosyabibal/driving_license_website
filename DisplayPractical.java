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
 * Servlet implementation class DisplayPractical
 */
@WebServlet("/DisplayPractical")
public class DisplayPractical extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayPractical() {
        super();
        // TODO Auto-generated constructor stub
    }
    String driverName="com.mysql.cj.jdbc.Driver";
    String dbUrl="jdbc:mysql://localhost:3306/dlms_db";
	String dbusername="root";
	String dbpassword="root";
	 Connection conn=null;
	    Statement stmt=null;
	    Statement stmt1=null;
	    ResultSet rs=null;
	    ResultSet rs1=null;
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
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
	         }//      Licence_id="";  
     out.print("<html lang=\"en\">");
		out.print("<head>");
		out.print("  <meta charset=\"UTF-8\">");
		out.print(" <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
		out.print(" <title>display Register</title>");
		out.print("<link rel=\"stylesheet\" href=\"style1.css\">");
		out.print("</head>");
     out.print("<body>");
		//out.print("<div>");
	
		//out.print("	</div>");
		out.print("<div>\r\n"
				+ "        <button><a href=\"Practical_Result\">Add student</a></button>\r\n"
				+ "    </div>");
	
		try {
			Class.forName(driverName);
		}catch(ClassNotFoundException e) {
			out.print("<h1>"+e.getMessage()+"</h1>");
		}
		
		String Licence_id="";
		int Drivers_id=0;
		
		try {
		
			out.print(" </tr> ");
			out.print("</thead>");	
			out.print(" <table>\r\n"
					+ "        <thead>\r\n"
					+ "            <tr>\r\n"
					+ "                <!-- <th>No</th> -->\r\n"
					+ "                <th>Driving Licence No</th>\r\n"
					+ "                <th>Year_Number</th>\r\n"
					+ "                <th>Practical Result</th>\r\n"
					+ "            </tr> \r\n"
					+ "        </thead>\r\n"
					+ "        <tbody>");
			
			
		 conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);	
		stmt=conn.createStatement();
		stmt1=conn.createStatement();
		rs1=stmt1.executeQuery("select  LISENCE_ID , Drivers_id  from driverpersonalinformation");
		
		
		
	   rs=stmt.executeQuery("select * from practicalresult");
	  
		while(rs.next() && rs1.next())  {
			Licence_id=rs1.getString("LISENCE_ID");
//			Drivers_id=rs1.getInt(Drivers_id);
			Drivers_id = rs.getInt("Drivers_id");

				
			out.print("<tr> ");
			out.print("<td>"+Licence_id+"</td>");
			out.print("<td>"+rs.getString("year") + "</td><td>" +rs.getInt("practical_result")+"</td>");
			
			out.print("<td class=TB><form action=\"UpdatePractical?id=" + Drivers_id + "\" method=\"post\" enctype=\"multipart/form-data\"><input type='submit' name='update' value='Update'></form></td><td></td>");
			out.print("<td class=TB><form action=\"delete?id=" + Drivers_id + "\" method=\"post\" enctype=\"multipart/form-data\"><input type='submit' name='delate' value='Delate'></form></td><td></td>");
			out.print("</tr> ");	
		}
			
			
			
			
		}catch(Exception e) {
			out.print("<h1>"+e.getMessage()+"</h1>");
		}
		out.print(" </tbody>\r\n"
				+ "		    </table>");	

		  
		out.print("</body>");
		out.print("</html>");
	}

}
