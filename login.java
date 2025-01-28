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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       String varibleCongra="INCORRECT ID OR User NAME";
       String varibleError="INCORRECT ID OR User NAME";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();   
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
    
        PrintWriter out= response.getWriter();
        String login = request.getParameter("login");
        String user_name = request.getParameter("user_name");
        String login_password = request.getParameter("password");
     if(login==null) {
        out.print("<html>");
        out.print("<head>");
        out.print("<title>User login Page</title>");
		out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/style.css' />");
		out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/mainstyle.css' />");

        out.print("</head>");
        out.print("<body style='color:rgb(255,90,250);background-color:rgb(0,255,255);'>");
       
        out.print("<header class=heading>");


		 // out.print("<img class='aa' src='images/Emblem_of_Ethiopia.svg.png'>");
      /*   out.print("<img class='aa' src='images/driving-school-steering-wheel-graduation-cap-d-rendering-car-licence-lessons-isolated-white-background-290971187.webp' style=display: block; margin: 20px auto; width: 5px; height: 10px; border-radius: 50%;>");

		  out.print(" <div class=head>");
		  out.print("<div class=\"head\">\r\n"
		  		+ "           \r\n"
		  		+ "                <h2> Driving License information\r\n"
		  		+ "                         Management System </h2>\r\n"
		  		+ "             </div> ");*/

         out.print("<img class='aa' src='images/driving-school-steering-wheel-graduation-cap-d-rendering-car-licence-lessons-isolated-white-background-290971187.webp' style='display: inline; margin: 0px auto; width: 350px; height: 190px; border-radius: 50%;'>");
		  out.print("<div class=\"head\">\r\n"
		  		+ "           \r\n"
		  	
		  	+"<h2 style='color: rgb(255,100,250); text-align: center; font-size: 40px;'>Driving License Information Management System</h2>"

		  		+ "             </div> ");
		  out.print(" </div>");
		  out.print("</div>");
		  out.print(" </header>"); 
		  out.print(" <img class='aa' src='images/best carss.JPG'>");	  
		  out.print(" <form class=\"loge\" action='Login'  method='POST'>");
		  out.print("  <h3>Login here!</h3>");
		  out.print(" <div class=lo>");
		  out.print("<input type=text name='user_name' id='uname' placeholder='USER_NAME' required>");
		  out.print("<img class=icon src=\"images/icons8-username-50.png\" alt=\"image\">");
		  out.print(" </div>");
		  out.print("<div class=lo>");        
		  out.print("<input type=password name='password' id='password' placeholder='PASSWORD' required>");
		  out.print("<img class=\"icon\" src=\"images/icons8-password-50.png\" alt=\"image\">");
		  out.print("</div>");
		  out.print("<button class=\"reset\" type='submit' name='login' value='LONIN'>Login</button>");
		  out.print("<button  class=\"reset\" type=\"reset\"  name=\"reset\">Reset</button>");
		  out.print("<div class=\"forget\"><a href=\"\">I forget password</a></div>");
		  out.print(" </form>");
		  
            out.println("<footer>");
            out.println("<p><marquee>&copy; 2024 Debre Markos Driving Licence Office."
            		+ " All rights reserved.Contact: info@drivinglicence.com</marquee></p>");
            out.println("</footer>");
   		  out.print("</body>");
   		  out.print("</html>");

     }
     else {
    	 try {
    		    String driverName = "com.mysql.cj.jdbc.Driver";
                String connectionUrl = "jdbc:mysql://localhost:3306/dlms_db";
                String userName = "root";
                String password = "root";
                Connection conn=null;
                PreparedStatement ps=null;
                ResultSet rs=null;
                Class.forName(driverName);
                conn=DriverManager.getConnection(connectionUrl,userName,password);
                String sql="select * from users WHERE user_name=?";
                ps=conn.prepareStatement(sql);
                ps.setString(1, user_name);

                rs=ps.executeQuery();
                if(rs.next()){
                	String username=rs.getString("fname");
                	String loginusername=rs.getString("user_name");
                	String user_status=rs.getString("user_status");
                	String user_type=rs.getString("user_type");
                	String mname=rs.getString("mname");
                	String loginpassword=rs.getString("user_password");
            		HttpSession session=request.getSession(true);

                	if(user_name.equals(loginusername) && login_password.equals(loginpassword) && user_status.equals("ACTIVE") && user_type.equals("Admin")) {
                		   // ALL ABOUT THE SESSION.
                 		 session.setAttribute("session_username",username);  
                 		 session.setAttribute("Autority", user_type);
                	    response.sendRedirect("AdminPage");
                	}   
                	else if(user_name.equals(loginusername) && login_password.equals(loginpassword) && user_status.equals("ACTIVE") && user_type.equals("Owner")) {
             		   // ALL ABOUT THE SESSION.
                		 session.setAttribute("session_username",username);  
                		 session.setAttribute("Autority", user_type);
             	    response.sendRedirect("OwnerPage");
             	}  
                	else if(user_name.equals(loginusername) && login_password.equals(loginpassword) && user_status.equals("ACTIVE") && user_type.equals("Registrar")) {
              		   // ALL ABOUT THE SESSION.
                 		 session.setAttribute("session_username",username);  
                 		 session.setAttribute("Autority", user_type);
              	    response.sendRedirect("Register_Personal_Information");
              	}
                	else if(user_name.equals(loginusername) && login_password.equals(loginpassword) && user_status.equals("ACTIVE") && user_type.equals("Traffic")) {
               		   // ALL ABOUT THE SESSION.
                  		 session.setAttribute("session_username",username);  
                  		 session.setAttribute("Autority", user_type);
               	    response.sendRedirect("traffic_home");
               	}
                	else{ 
                	response.sendRedirect("CongraPage");	
//RequestDispatcher p=request.getRequestDispatcher("/CongraPage");
//p.include(request, response);

                		
                	}

                }
                else {
                	response.sendRedirect("CongraPage");	
                	//RequestDispatcher p=request.getRequestDispatcher("/CongraPage");
                	//p.include(request, response);

                }
    	 }catch(SQLException | ClassNotFoundException e) {
    		 e.printStackTrace();
     		  out.print("<h2>"+e.getMessage()+" </h2>");

    	 }

     }
	}

}
