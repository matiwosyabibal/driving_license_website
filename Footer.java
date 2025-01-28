package com.dlms;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Footer
 */
@WebServlet("/Footer")
public class Footer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Footer() {
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
		//PrintWriter out= response.getWriter();
		  out.print("<html>");
		  out.print("<head>");
		  out.print("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
		  out.print("<title>header page</title>");
		  out.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"mainstyle.css\">");
        
		  out.print("</head>");
		  out.print("<body>");
        // Footer HTML
        out.println("<footer>");
        out.println("<div class=\"footer_container\">");
        out.println("<p>&copy; 2024 Debre Markos Driving Licence Office. All rights reserved.</p>");
        out.println("<p>Contact: info@drivinglicence.com</p>");
        out.println("</div>");
        out.println("</footer>");
        out.print("</body>");
        out.print("</html>");
        out.close();
	}

}
