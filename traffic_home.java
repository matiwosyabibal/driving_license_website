

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class traffic_home
 */
@WebServlet("/traffic_home")
public class traffic_home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public traffic_home() {
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
	        HttpSession sessionA = request.getSession(false);
	        HttpSession session=request.getSession(true);
	        if (session.getAttribute("session_username")==null||session.getAttribute("Autority")==null)
	        {
	        response.sendRedirect("Login");
	         return;
	        }
	        if(!session.getAttribute("Autority").equals("Traffic")) {
	         response.sendRedirect("Login"); //redirect to Home page

	        }
	        String Session_user_name=null;
	        session=request.getSession();
	         if(session!=null) {
	        	 Session_user_name=(String)session.getAttribute("session_username");
	         }

	        out.println("<!DOCTYPE html>");
	        out.println("<html>");
	        out.println("<head>");
	        out.println("<title>Traffic Police Home Page</title>");
	        out.println("<style>");
	        out.println("body {");
	        out.println("    font-family: Arial, sans-serif;");
	        out.println("    margin: 0;");
	        out.println("    padding: 0;");
	        out.println("   background-color:rgb(170,255,250);");
	        out.println("}");
	        out.println("header {");
	        out.println("    background-color:rgb(0,255,255);");
	        out.println("    color: rgb(255,100,250);");
	        out.println("    text-align: center;");
	        out.println("    padding: 1rem 0;");
	        out.println("}");
	        out.println("main {");
	        out.println("    margin: 2rem auto;");
	        out.println("    text-align: center;");
	        out.println("    color: rgb(255,100,250);");
	        out.println("}");
	        out.println("a {");
	        out.println("    display: inline-block;");
	        out.println("    margin: 1rem;");
	        out.println("    padding: 1rem 2rem;");
	        out.println("    text-decoration: none;");
	        out.println("    background-color: rgb(10,200,100);");
	        out.println("    color: white;");
	        out.println("    border-radius: 5px;");
	        out.println("    transition: 0.3s;");
	        out.println("}");
	        out.println("a:hover {");
	        out.println("    background-color:rgb(181,200,10);");
	        out.println("}");
	        out.println("</style>");
	        out.println("</head>");
	        out.println("<body>");
	        out.println("<header>");
	        out.println("<h1>Traffic Police Home Page</h1>");
	        out.println("</header>");
//	        out.print("<div class='content_container'>");
//
//	        out.print("<h1 style='color:rgb(0,250,50); align :center;'>WELLCOME "+Session_user_name+" HAVING A NICE WORK DAY</h1>");
//	        out.print("</div>");
	        out.println("<main>");
	        out.print("<h1 style='color:rgb(0,250,50); align :center;'>WELLCOME "+Session_user_name+" HAVING A NICE WORK DAY</h1>");

	        //out.println("<h2>Welcome, Traffic Police Officer Home Page</h2>");
	        out.println("<p>Choose an action below:</p>");
	        out.println("<a href='tvfs'>Issue Traffic Violations</a>");
	        out.println("<a href='QueryDriverDetails'>Query Driver Details</a>");
	        out.println("<a href='Login'>LOG OUT</a>");
	        out.println("</main>");
	        
	        out.println("</body>");
	        out.println("</html>");
	    
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
