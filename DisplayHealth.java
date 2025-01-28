package com.dlms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DisplayHealth
 */
@WebServlet("/DisplayHealth")
public class DisplayHealth extends HttpServlet {
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
    public DisplayHealth() {
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
		         }
	        out.print("<!DOCTYPE html>");
	        out.print("<html lang=\"en\">");
	        out.print("<head>");
	        out.print("<meta charset=\"UTF-8\">");
	        out.print("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
	        out.print("<title>Display Health</title>");
	    	out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/style1.css' />");out.print("<link rel=\"stylesheet\" href=\"style1.css\">");
	        out.print("</head>");
	        out.print("<body>");
	        out.print("<div>");
	        out.print("<a class=\"other\" href=\"Register_Personal_Information\">Add student</a>");
	        out.print("</div>");
	        out.print("<div class=\"table\">");
            out.print("<table border=1>");
            out.print("<thead>");
            out.print("<tr>");
            out.print("<th>Driving Licence ID</th>");
            out.print("<th>Year of Recorded</th>");
            out.print("<th>Blood Type</th>");
            out.print("<th>Eye Result</th>");
            out.print("<th>General Health Result</th>");
            out.print("<th>Update Button</th>");
            out.print("<th>Delete Button</th>");
            out.print("</tr>");
            out.print("</thead>");
	        try {
	            Class.forName(driverName);
	            Connection conn = DriverManager.getConnection(dbUrl, dbusername, dbpassword);
	            Statement stmt = conn.createStatement();
	            ResultSet rsDriver = stmt.executeQuery("SELECT * FROM driverpersonalinformation");
	            while (rsDriver.next()) {
	                String licenceId = rsDriver.getString("LISENCE_ID");
	                if (licenceId != null) {
	                    ResultSet rsHealth = stmt.executeQuery("SELECT * FROM healthstatus");
	                    while (rsHealth.next()) {
	    	                String licenceIdh = rsHealth.getString("drivers_id");

	                        out.print("<tbody>");
	                        out.print("<tr>");
	                        out.print("<td>" + licenceIdh + "</td>");
	                        out.print("<td>" + rsHealth.getString("year") + "</td>");
	                        out.print("<td>" + rsHealth.getString("blood_type") + "</td>");
	                        out.print("<td>" + rsHealth.getString("eye") + "</td>");
	                        out.print("<td>" + rsHealth.getString("general_health_status") + "</td>");
	                        out.print("<td class=\"TB\"><form action=\"UpdateHealth?id=" +licenceIdh + "\" method=\"post\"><input type=\"submit\" name=\"update\" value=\"Update\"></form></td>");
	                        out.print("<td class=\"TB\"><form action=\"UpdateHealth?id=" + licenceId + "\" method=\"post\"><input type=\"submit\" name=\"delete\" value=\"Delete\"></form></td>");
	                        out.print("</tr>");

	                    }
	                }
	                else {
	                	out.print("no one with this Id");
	                }
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