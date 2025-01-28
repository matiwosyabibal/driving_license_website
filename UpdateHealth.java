package com.dlms;

import java.io.File;
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
 * Servlet implementation class UpdateHealth
 */
@WebServlet("/UpdateHealth")
public class UpdateHealth extends HttpServlet {
	private static final long serialVersionUID = 1L;
    String driverName = "com.mysql.cj.jdbc.Driver";
    String connectionUrl = "jdbc:mysql://localhost:3306/dlms_db?useSSL=false";
    String userName = "root";
    String password = "root";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateHealth() {
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
        
        //db varibles
        

        String driverIdpassed=request.getParameter("id");
        String write = request.getParameter("write");
        String save = request.getParameter("save");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        if (write == null && save == null) {

            
            try {
                Class.forName(driverName);
                conn = DriverManager.getConnection(connectionUrl, userName, password);

                String driverId = request.getParameter("id");

                String sql = "SELECT * FROM driverpersonalinformation WHERE Drivers_id = ?";
                PreparedStatement psp = conn.prepareStatement(sql);
                psp.setString(1, driverId);
              //  out.print(psp);
                ResultSet rsp = psp.executeQuery();

                if (rsp.next()) {
                    String licenceId = rsp.getString("LISENCE_ID");

                    String sql2 = "SELECT * FROM healthstatus WHERE Drivers_id = ?";
                  PreparedStatement  ps1 = conn.prepareStatement(sql2);
                    ps1.setString(1, driverId);
                    
                   ResultSet rs1 = ps1.executeQuery();
                     if(rs1.next()) {
             	        out.print("<!DOCTYPE html>");
            	        out.print("<html lang=\"en\">");
            	        out.print("<head>");
            	        out.print("<meta charset=\"UTF-8\">");
            	        out.print("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            	        out.print("<title>Display Health</title>");
        		    	out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/style1.css' />");
            	        out.print("</head>");
            	        out.print("<bodystyle='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:18rem;'>");
                    out.print("<form class=H action='Save?save="+driverId+"' method='post'>");
                    out.print(" <div class=Bform2>");
                    out.print("<div class=lo>");
                    out.print("<input type=number name=Dl id=Dl placeholder=Driving License Number value='" + licenceId + "' required>");
                    out.print(" </div>");
                    out.print(" <div class=lo>");
                    out.print("<input type=date name=year id=year placeholder=Year  value='" + rs1.getString("year") + "' required>");
                    out.print("  </div>");
                    out.print("  <div class=lo H>");
                    out.print("<div class=HL>");
                    out.print("<select name=Blood id=blood>");
                    out.print("<option>" + rs1.getString("blood_type") + "</option>");
                    out.print("<option value=a>A</option>");
                    out.print("<option value=b>B</option>");
                    out.print("<option value=ab>AB</option>");
                    out.print("<option value=o>O</option>");
                    out.print("<option value=a+>A+</option>");
                    out.print("<option value=b+>B+</option>");
                    out.print("  </select>");
                    out.print(" </div>");
                    out.print("<div class=HL>");
                    out.print("<label for=eye>Eye result</label>");
                    out.print(" <select name=eye id=ey>");
                    out.print("<option  value='" + rs1.getString("eye") + "'>Full</option>");
                    out.print("<option value=semi>Semi</option>");
                    out.print(" <option value=null>Blind</option>");
                    out.print("    </select>");
                    out.print("  </div>");  
                    out.print("<div class=HL>");
                    out.print("<label for=ghr>General Health Result</label>");
                    out.print("<select name=ghr id=\"ghr\">");
                    out.print("<option  value='" + rs1.getString("blood_type") + "'>Excellent</option>");
                    out.print(" <option value=very_good>Very Good</option>");
                    out.print("</select>");
                    out.print("</div>");
                    out.print("</div>");
                    out.print("<input class=reset type=submit name=write value=Update> ");
                    out.print(" </div>");
                    out.print("</form>");
                }
                } else {
                    out.println("<p>No S found for driver ID: " + driverId + "</p>");
                }
                out.print("</body>");
                out.print("</html>");
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace(); // Log the exception for debugging
            } finally {
                // Close resources in finally block to ensure they are always closed
                try {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
     

    }
}
