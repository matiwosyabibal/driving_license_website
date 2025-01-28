package com.dlms;

import java.io.IOException;

import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CongraPage 
 */
@WebServlet("/CongraPage")
public class CongraPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CongraPage() {
        super();
        // TODO Auto-generated constructor stub
    }
   

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect the user back to UserRegistrationPage
        // response.sendRedirect("UserRegistrationPage"); // Commented out or removed

    	 response.setContentType("text/html");
         PrintWriter out = response.getWriter();
         out.println("<html>");
         out.println("<head>");
         out.println("<title>User login Page</title>");
         out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/mainstyle.css' />");
//        style part start 
         out.println("<style>");
         out.println(".error_container {");
         out.println("    position: absolute;");
         out.println("    margin-bottom: 10px;");
         out.println("    top: 10rem;");
         out.println("    left: 20rem;");
         out.println("    color: red;");
         out.println("    background-color: black;");
         out.println("    border-radius: 1rem;");
         out.println("    padding: 7rem 8rem;");
         out.println("    animation-name: slideDown;");
         out.println("    animation-duration: 0.5s;");
         out.println("    display: block;");
         out.println("    max-width: 50%;");
         out.println("    max-height: 30%;");
         out.println("}");
         out.println("#errorcancel {");
         out.println("    position: relative;");
         out.println("    color: red;");
         out.println("    padding: 0.5rem 3rem;");
         out.println("    text-align: center;");
         out.println("    background-color: red;");
         out.println("    color: white;");
         out.println("    border: none;");
         out.println("    margin: auto;");
         out.println("}");
         out.println("#errorcancel:hover {");
         out.println("    opacity: 0.6;");
         out.println("}");
         out.println("</style>");
//         style part end 
         out.println("</head>");
         out.println("<body>");
         
         out.println("<div class=\"error_container\">");
         out.println("<h3>THE USER IS NOT REGISTERED, THERE IS SOME ERROR. TRY AGAIN</h3>");
         out.println("<button id=\"errorcancel\" onclick=\"cancel()\"><a href='Login'>Cancel</a></button>");
        
         out.println("</div>");
        
//         javascript part
         out.println("<script>");
         out.println("function cancel() {");
         out.println("    var errordiv = document.querySelector('.error_container');");
         out.println("    errordiv.style.display = 'none';");
         out.println("}");
         out.println("</script>");
         out.println("</body>");
         out.println("</html>");
    }


}
