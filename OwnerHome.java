package com.dlms;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class OwnerHome
 */
@WebServlet("/OwnerHome")
public class OwnerHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OwnerHome() {
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
		         }		  out.print("<html>");
		  out.print("<head>");
		  out.print("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
		  out.print("<title>header page</title>");
		  out.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"mainstyle.css\">");
        
		  out.print("</head>");
		  out.print("<body style='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:18rem;' >");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/OwnerHeader");
	        dispatcher.include(request, response); 
	        out.print("<div class='content_container'>");

	        out.print("<h1 style='color:rgb(0,250,50);'>WELLCOME "+Session_user_name+" HAVING A NICE WORK DAY</h1>");
	        out.print("</div>");
		  out.print("</body>");
		  out.print("</html>");
	}

}
