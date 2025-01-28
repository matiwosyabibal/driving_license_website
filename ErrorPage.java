package com.dlms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ErrorPage
 */
@WebServlet("/ErrorPage")
public class ErrorPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErrorPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//login1 L=new login1();
		
		PrintWriter out =response.getWriter();
		//MyFilesUpload b=new MyFilesUpload();
		   out.print("<html>");
	        out.print("<head>");
	        out.print("<title>Error page</title>");
			out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/mainstyle.css' />");

	        out.print("</head>");
	        out.print("<body>");

	        out.print("<div class=error_container style=margin-top:8rem>"
	        	//	+ "<h1>"+L.varibleError+" <h1/>"
	        		+ "<button><a href=\"/file.html\">ok </a></button>"
	        		+ ""
	        		+ ""
	        		+ "</div>");
	      

	        out.print("</body>");
	        out.print("</html>");
		
	}

}
