package com.dlms;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class OwnerHeader
 */
@WebServlet("/OwnerHeader")
public class OwnerHeader extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OwnerHeader() {
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
		        if(!session.getAttribute("Autority").equals("Owner")) {
		         response.sendRedirect("Login"); //redirect to Home page

		        }
		        String Session_user_name=null;
		        session=request.getSession();
		         if(session!=null) {
		        	 Session_user_name=(String)session.getAttribute("session_username");
		         //user_type=(String)session.getAttribute("utype");
		         }		  
		         out.print("<html>");
				  out.print("<head>");
				  out.print("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
				  out.print("<title>header page</title>");
				  out.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">");
				  out.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"mainstyle.css\">");
				  out.print("</head>");
				  out.print("<body>");
			



		          out.print("<div class='HeadingPart' >");
		          out.print("<img class='aa' src='images/driving-school-steering-wheel-graduation-cap-d-rendering-car-licence-lessons-isolated-white-background-290971187.webp' style='display: inline; margin: 0px auto; width: 350px; height: 190px; border-radius: 50%;'>");
				  out.print("<div class=\"head\">\r\n"
				  		+ "           \r\n"
				  	
				  	+"<h2 style='color: rgb(255,100,250); text-align: center; font-size: 40px;'>Driving License Information Management System</h2>"

				  		+ "             </div> ");
				  out.print(" </div>");
				  out.print("</div>");
	    
	      out.print("<ul class=menu>"
	        		+ "<li><a href=\"OwnerPage\">Issue Licence</a></li>"
	        		+ "<li><a href=\"RenewLicence\">Renew Licence</a></li>"
	        		+ "<li><a href= \"ViewDrivers\" >View ViewDrivers</a></li>"
	        		+ "<li><a href=  'Logout?name=1'>logout</a></li>"
	        		+ "</ul>");

		  out.print("</body>");
		  out.print("</html>");
	}

}
