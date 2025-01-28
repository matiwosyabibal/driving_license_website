package com.dlms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminViewUsersPage
 */
@WebServlet("/AdminViewUsersPage")
public class AdminViewUsersPage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminViewUsersPage() {
        super();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
	         }
            String submit = request.getParameter("submit");
            String userId = request.getParameter("user_id");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Header");
            dispatcher.include(request, response);
            out.print("<html>");
            out.print("<head>");
            out.print("<title>User Registration Page</title>");
            out.print("</head>");
            out.print("<body style='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:20rem;'>");

            if (submit == null) {
                out.print("<h3 id=Search_title style=text-align:auto;>Please Search the User you want to see by its ID</h3>");
                out.print("<form action='AdminViewUsersPage' method='POST' class='user_registration_form'>");
                out.print("<label>User ID</label>");
                out.print("<input type='text' name='user_id'>");
                out.print("<input type='submit' name='submit' value='Search'>");
                out.print("<input type='reset' name='reset' value='Reset'>");
                out.print("</form>");
            } else {
                try {
                    String driverName = "com.mysql.cj.jdbc.Driver";
                    String connectionUrl = "jdbc:mysql://localhost:3306/dlms_db";
                    String userName = "root";
                    String password = "root";                                  
                    Class.forName(driverName);
                    try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {
                        String sql = "SELECT * FROM users WHERE user_id=?";
                        try (PreparedStatement statement = conn.prepareStatement(sql)) {
                            statement.setString(1, userId);
                            try (ResultSet rs = statement.executeQuery()) {
                                if (!rs.next()) {
                                    out.print("No user registered with this id");
                                } else {
                                    out.print("<table border=1 class='view'>");
                                    out.print("<tr>");
                                    out.print("<th>id</th><th>password</th><th>user type</th><th>user status</th><th>fname</th><th>mname</th><th>lname</th><th>Image</th><th>user_name</th>");
                                    out.print("</tr>");
                                    out.print("<tr>");
                                    out.print("<td>" + rs.getString("user_id") + "</td>");
                                    out.print("<td>" + rs.getString("user_password") + "</td>");
                                    out.print("<td>" + rs.getString("user_type") + "</td>");
                                    out.print("<td>" + rs.getString("user_status") + "</td>");
                                    out.print("<td>" + rs.getString("fname") + "</td>");
                                    out.print("<td>" + rs.getString("mname") + "</td>");
                                    out.print("<td>" + rs.getString("lname") + "</td>");
                                    
                                    // Retrieve the image URL and format it properly
                                    String imageURL = rs.getString("image");
                                    int imagewidth=100;
                                    int imageheight=100;
                                    out.print("<td><img src='"+imageURL+"' width='"+imagewidth+ "' height='"+imageheight+  "'></td>");
                                    out.print("<td>" + rs.getString("user_name") + "</td>");
                                    out.print("</tr>");
                                    out.print("</table>");
                                }
                            }
                        }
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    out.print("<h1>Error: " + e.getMessage() + "</h1>");
                }
            }
            out.println("<footer style=margin-top:30rem;>");
            out.println("<div class=\"footer_container\">");
            out.println("<p>&copy; 2024 Debre Markos Driving Licence Office. All rights reserved.</p>");
            out.println("<p>Contact: info@drivinglicence.com</p>");
            out.println("</div>");
            out.println("</footer>");
            out.print("</body>");
            out.print("</html>");
      
        
    }
}
