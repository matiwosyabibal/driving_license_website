package com.dlms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateUsersInformation
 */
@WebServlet("/UpdateUsersInformation")
public class UpdateUsersInformation extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateUsersInformation() {
        super();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	  // Include header
     
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
        String edit = request.getParameter("edit");
        String user_name = request.getParameter("user_name");
       
        RequestDispatcher dispatcher = request.getRequestDispatcher("/Header");
        dispatcher.include(request, response);
      
        out.print("<html>");
        out.print("<head>");
        out.print("<title>User Registration Page</title>");
        out.print("</head>");
        out.print("<body style='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:20rem;'>");     

        if (submit == null && edit == null) {
            out.print("<h3 id=Search_title style=text-align:center;color:red;>Please Search by user name</h3>");
            out.print("<form action='UpdateUsersInformation' method='POST' class='user_registration_form'>");
            out.print("<label>User Name</label>");
            out.print("<input type='text' name='user_name'>");
            out.print("<input type='submit' name='submit' value='Search'>");
            out.print("<input type='reset' name='reset' value='Reset'>");
            out.print("</form>");
        } else {
            try {
                String driverName = "com.mysql.cj.jdbc.Driver";
                String connectionUrl = "jdbc:mysql://localhost:3306/dlms_db";
                String userName = "root";
                String password = "root";
                
                if (user_name.equals("") && submit != null) {
                    out.print("Please enter user ID");
                } else {  
                    Class.forName(driverName);
                    try (Connection conn = DriverManager.getConnection(connectionUrl, userName, password)) {
                        String sql = "SELECT * FROM users WHERE user_name=?";
                        try (PreparedStatement statement = conn.prepareStatement(sql)) {
                            statement.setString(1, user_name);
                            try (ResultSet rs = statement.executeQuery()) {
                                if (!rs.next()) {
                                    out.print("No user found");
                                } else {
                                    out.print("<table border='1' class='view'>");
                                    out.print("<tr>");
                                    out.print("<th>id</th><th>password</th><th>user type</th><th>user status</th><th>fname</th><th>mname</th><th>lname</th><th>Image</th><th>user_name</th><th>Edit button</th><th>Delete button</th>");

                                    out.print("</tr>");
                                    do {
                                        // Retrieve the image URL and format it properly
                                        String imageURL = rs.getString("image");
                                        int imagewidth=100;
                                        int imageheight=100;
                                        String passedId= rs.getString("user_id");
                                        out.print("<tr>");
                                        out.print("<td>" + rs.getString("user_id") + "</td><td>" + rs.getString("user_password") + "</td><td>" + rs.getString("user_type") + "</td><td>" + rs.getString("user_status") + "</td><td>" + rs.getString("fname") + "</td><td>" + rs.getString("mname") + "</td><td>" + rs.getString("lname") + "</td><td><img src='"+imageURL+"' width='"+imagewidth+ "' height='"+imageheight+  "'></td><td>" + rs.getString("user_name") + "</td><td><form action=UserUpdateImplementationMain?id=" + passedId + " method='POST'><input type='submit' name='edit' value='Edit' id='update_button'></form></td><td><form action=\"DeleteImplementation?id=" + rs.getString("user_id") +"\" method='POST'><input type='submit' name='delete' value='Delete' class='delete_button'></form></td>");
                                        out.print("</tr>");
                                    } while (rs.next());
                                    out.print("</table>");
                                }
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
