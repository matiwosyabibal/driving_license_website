package com.dlms;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JavaScript
 */
@WebServlet("/JavaScript")
public class JavaScript extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JavaScript() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		 out.print("<html>");
	        out.print("<head>");
	        out.print("<title>User login Page</title>");
			out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/style.css' />");
			out.print("<style>  .error_container {\r\n"
					+ "    position: absolute;\r\n"
					+ "    margin-bottom: 10px;\r\n"
					+ "    top: 10rem;\r\n"
					+ "    left: 20rem;\r\n"
					+ "    color: red;\r\n"
					+ "    background-color: black;\r\n"
					+ "    border-radius: 1rem;\r\n"
					+ "    padding: 7rem 8rem;\r\n"
					+ "    animation-name: slideDown;\r\n"
					+ "  animation-duration: 0.5s;\r\n"
					+ "  display:block;\r\n"
					+ "  max-width: 50%;\r\n"
					+ "  max-height: 30%;\r\n"
					+ "  }\r\n"
					+ "  #errorcancel{\r\n"
					+ "    position: relative;\r\n"
					+ "    color: red;\r\n"
					+ "    padding: 0.5rem 3rem;\r\n"
					+ "    text-align: center;\r\n"
					+ "    background-color: red;\r\n"
					+ "    color: white;\r\n"
					+ "    border:none;"
					
					+ "    margin: auto;\r\n"
					+ "  }\r\n"
					+ "  #errorcancel:hover{\r\n"
					+ "    opacity: 0.6;\r\n"
					+ "  }</style>");
	        out.print("</head>");
	        out.print("<body>");
			  out.print("<div class=\"error_container\">");
			  out.print( "<h3>THE USER IS NOT REGISTERED, THERE IS SOME ERROR. TRY AGAIN</h3>");
			  out.print( "<button id=\"errorcancel\"> Hidden</button>");
			  out.print( "</div>");
      		 out.print("var errordiv = document.querySelector('.error_container');");
      		out.print("let h3=errordiv.querySelector('h3');");
      		out.print("hello>");

      	//	out.print("errordiv.style.display = \"block\";");
      		out.print(" var cancelbutton = document.querySelector(\"#errorcancel\");");
      		out.print("function cancel(){");
      		out.print("var errordiv = document.querySelector('.error_container');");
      		out.print("errordiv.style.display = \"none\";");
      		out.print(" }");
      		out.print("cancelbutton.addEventListener('click', cancel);");
      		out.print("</script>");

      		out.print("hello>");
     		  out.print("</body>");
       		  out.print("</html>");
              
	}

}
