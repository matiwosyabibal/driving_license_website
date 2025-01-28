
package com.dlms;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class HealthStatus
 */
@WebServlet("/HealthStatus")
public class HealthStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HealthStatus() {
        super();
        // TODO Auto-generated constructor stub
    }
   public String driverName="com.mysql.cj.jdbc.Driver";
	public String dbUrl="jdbc:mysql://localhost:3306/dlms_db";
	public String dbpassword="root";
	public String dbusername="root";
	
	Connection conn=null;
	PreparedStatement ps=null;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		        if(!session.getAttribute("Autority").equals("Registrar")) {
		         response.sendRedirect("Login"); //redirect to Home page

		        }
		        String Session_user_name=null;
		        session=request.getSession();
		         if(session!=null) {
		        	 Session_user_name=(String)session.getAttribute("session_username");
		         //user_type=(String)session.getAttribute("utype");
		         }		String Submit=request.getParameter("health");
		
			String drivers_id="";
		out.print("<html lang=\"en\">");
		out.print("<head>");
		out.print(" <meta charset=\"UTF-8\">");
		out.print(" <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
		out.print("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
		out.print("<title>Health Status</title>");
		out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/style1.css' />");
		out.print("	</head>");
		out.print("<body style='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:18rem;'>");
		
		out.print("	<header>");
		out.print(" <nav class=Rnavbar>");
		out.print("<a href=Register_Personal_Information >Personal</a>");
		out.print("<a href=HealthStatus>Health Status</a>");
		out.print("<a href=Practical_Result>Practical Result</a>");
		out.print("<a href=Theory_Result>Theory Result</a>");
		out.print("<a href=logout>Logout</a>");
		out.print("    </nav>");
		out.print("</header> ");
		
		
		if(Submit==null) {
			out.print("<form class=H action=HealthStatus method=post style='color:rgb(10,5,250);'>");
			out.print(" <div class=Bform2>");
			out.print("<div class=lo>");
			out.print("     <input type=number name=Dl id=Dl placeholder=Driving License Number required>");
			out.print(" </div>");
			out.print(" <div class=lo>");
			out.print("<input type=date name=year id=year placeholder=Year required>");
			out.print("  </div>");
			out.print("  <div class=lo H>");
			out.print("<div class=HL>");
			out.print("<select name=Blood id=blood>");
			out.print("<option value=''>Blood Group</option>");
			out.print("<option value=a>A</option>");
			out.print("<option value=b>B</option>");
			out.print("<option value=ab>AB</option>");
			out.print("<option value=o>O</option>");
			out.print("<option value=a+>A+</option>");
			out.print("<option value=b+>B+</option>");
			out.print("  </select>");
			out.print(" </div>");
			out.print("<div class=HL>");
			out.print("<label for=eye>Eye result</label>");
			out.print(" <select name=eye id=ey>");
			out.print("<option value=full>Full</option>");
			out.print("<option value=semi>Semi</option>");
			out.print(" <option value=null>Blind</option>");
			out.print("    </select>");
			out.print("  </div>");  
			out.print("<div class=HL>");
			out.print("<label for=ghr>General Health Result</label>");
			out.print("<select name=ghr id=\"ghr\">");
			out.print("<option value=excellent>Excellent</option>");
			out.print(" <option value=very_good>Very Good</option>");
			out.print("</select>");
			out.print("</div>");
			out.print("</div>");
			out.print("<input class=reset type=submit name=health value=Register> ");
			out.print("<a class=reset href=DisplayHealth?id='"+ drivers_id+ "'>Update</a>");
			out.print(" </div>");
			out.print("</form>");	
			
			
		}else {
			drivers_id=request.getParameter("Dl");
			String year=request.getParameter("year");
			String  Blood=request.getParameter("Blood");
			String Eye=request.getParameter("eye");
			String Ghr=request.getParameter("ghr");
			
			try {
				Class.forName(driverName);	
			}catch(ClassNotFoundException e) {
				out.print("<h1>"+e.getMessage()+"</h1>");
			}
			 
			try {
				conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);		

				String one="select * from driverpersonalinformation where LISENCE_ID=?";
				
				PreparedStatement psp=conn.prepareStatement(one);
				psp.setString(1, drivers_id);
				ResultSet rs=psp.executeQuery();
				if(rs.next()) {
					String id=rs.getString("Drivers_id");
				
				
		String sql="insert into healthstatus(drivers_id,year,blood_type,eye,general_health_status)values(?,?,?,?,?)";	
		ps=conn.prepareStatement(sql);
		ps.setString(1,id);
		ps.setString(2,year);
		ps.setString(3,Blood);
		ps.setString(4,Eye);
		ps.setString(5,Ghr);
	int i=ps.executeUpdate();
		
	if(i>0) {
		out.print("<h1 style='color: rgb(5,200,0); text-align: left; font-size: 40px;'>Health result is sussfully stored!</h1>");
	}else {
		out.print("<h1>Query problem!</h1>");
	}

			}
			else {
				out.print("<h1 style='color: rgb(255,10,0); text-align: left; font-size: 30px;'>Your Driver Licence Number Is Incorect Please Tray Again!");
			}
			}catch(SQLException e) {
				out.print("<h1>"+e.getMessage()+"</h1>");
			}
	
			out.print("</body>");
			out.print("</html>");	
		}
	
	}

}
