//package com.dlms;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class QueryDriverDetails
 */
@WebServlet("/QueryDriverDetails")
public class QueryDriverDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private String driverName;
    private String connectionUrl;
    private String userName;
    private String password;
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryDriverDetails() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init() throws ServletException {
		// TODO Auto-generated method stub
    	  driverName = "com.mysql.cj.jdbc.Driver";
          connectionUrl = "jdbc:mysql://localhost:3306/dlms_db";
         userName = "root";
         password = "root";
    }
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	      response.setHeader("Pragma","no-cache");
	      response.setHeader("Cache-Control","no-store");
	      response.setHeader("Expires","0");
	      response.setDateHeader("Expires",-1);
	        PrintWriter out = response.getWriter();
	        HttpSession sessionA = request.getSession(false); // Retrieve the session without creating a new one if it doesn't exist
	        HttpSession session=request.getSession(true);
//	        if (session.getAttribute("session_username")==null||session.getAttribute("Autority")==null)
//	        {
//	       response.sendRedirect("Login");
//	         return;
//	        }
	        if(!session.getAttribute("Autority").equals("Traffic")) {
	         response.sendRedirect("Login"); //redirect to Home page

	        }
	        String Session_user_name=null;
	        session=request.getSession();
	         if(session!=null) {
	        	 Session_user_name=(String)session.getAttribute("session_username");
	         //user_type=(String)session.getAttribute("utype");
	         }

		

	     //   RequestDispatcher dispatcher = request.getRequestDispatcher("/OwnerHeader");
	       // dispatcher.include(request, response);
	        out.print("<html>");
	        out.print("<head>");
	        out.print("<title>User Registration Page</title>");
			  out.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"mainstyle.css\">");
	        out.print("</head>");
	        out.print("<body style='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:20rem;';>");   


	        out.print("<form action=QueryDriverDetails?save=1 method='POST' class='user_registration_form'>");
	        out.print("<label>Please Enter Driver's License Id</label>");
	        out.print("<input type='text' name='user_id' required>");
	       // out.print("<label>Start Date</label>");
	       // out.print("<input type='date' name='sdate' required>");
	        out.print("<input type='submit' name='issue' value='See Driver Details'>");
	        out.print("<input type='reset' name='reset' value='Reset'>");
	        out.print("<label><a href='traffic_home' >Log out</a></label>");
	        out.print("</form>");
	       // String issue = request.getParameter("issue");
	        String id = request.getParameter("user_id");
	       // String start_date = request.getParameter("sdate");
	        String save = request.getParameter("save");
	        if (save != null && !save.isEmpty()) {
	            int saven = Integer.parseInt(save);
	            if (saven > 0) {
	                try {
	                    Class.forName(driverName);
	                    conn = DriverManager.getConnection(connectionUrl, userName, password);
	                    String sql = "SELECT * FROM driverpersonalinformation WHERE LISENCE_ID=?";
	                    ps = conn.prepareStatement(sql);
	                    ps.setString(1, id);
	                    rs = ps.executeQuery();
	                    if (!rs.next()) {
	                       // out.print("<p>No user found with this ID</p>");
	                        out.print("<h1 style=padding:3rem;position:absolute;top:6rem;left:26rem;>No user found with this ID</h1>");
	                    } else {
	                        String fname = rs.getString("fname");
	                        String sname = rs.getString("mname");
	                        String Did=rs.getString("Drivers_id");
	                        String DOB=rs.getString("brithdate");
	                        String gender=rs.getString("gender");

	                        String zone=rs.getString("zone");
	                        String woreda=rs.getString("woreda");

	                        String kebelie=rs.getString("kebelie");
	                        
	                        //the health table
	                        
	                        String sqlfh="select * from healthstatus where Drivers_id=?";
	                        
	                        PreparedStatement psfh=conn.prepareStatement(sqlfh);
	                        psfh.setString(1, Did);
	                        ResultSet refh=psfh.executeQuery();
                         if(refh.next()) {

	                        String bloodType=refh.getString("blood_type");
	                        String eye=refh.getString("eye");

	                      //  LocalDate start_date1=LocalDate.parse(start_date);
	                      //  LocalDate end_date=start_date1.plusMonths(2);
	                        
	                        //String Date=
	                        // inserting Data.
	                        

	                        String sqlResult="select * from practicalresult where Drivers_id=?";
	                        PreparedStatement psfp=conn.prepareStatement(sqlResult);
	                        psfp.setString(1,Did);
	                        ResultSet rsfp=psfp.executeQuery();
	                        if(rsfp.next()) {
	                            String pResult=rsfp.getString("practical_result");
	                            double Result=Double.parseDouble(pResult);
	                            if(Result >= 50 && Result <= 100) {
	                        
		                     //  String recordquery="insert into issuelicence(start_date,Drivers_id) values(?,?)";
			                    conn = DriverManager.getConnection(connectionUrl, userName, password);
			//                    ps=conn.prepareStatement(recordquery);
			            //      ps.setString(1, start_date);
			                   //ps.setString(2, Did);
			                    
			                   
			               //    int i= ps.executeUpdate();
			               
			                   
		                    out.println("<div id=\"print_button_div\">");		                   
	                        out.println("<div class=\"card\">");
	                        out.println("  <table class=\"Card_table\"style='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:1rem;'>");
	                        out.println("    <tr>");
                          int imagewidth=50;
                          int imageheight=50;
                          out.print("<td style=border:1px;><img src='myfiles/office_logo.png' width='"+imagewidth+ "' height='"+imageheight+  "'></td>");

	                        out.println("      <th style='margin-left:-13rem;color:rgb(255,170,50);'>DRIVING LICENCE CARD</th>");

	                        out.println("    </tr>");

	                        out.println("    <tr id=image_row>");
                          String imageURL = rs.getString("image");

                          out.print("<td style=border:1px;><img src='" + request.getContextPath() + "/" + imageURL + "' width='"+imagewidth+ "' height='"+imageheight+  "'></td>");
	                        out.println("<td>Full-Name<span id=card_attribute_rows> &nbsp"  +fname + "  " +sname+ "</span></td>");

	                        out.println("    </tr>");

	                        out.println("    <tr>");
	                        out.println("      <td>licence number:<span>"+id+ "</span></td>");
	                        out.println("      <td>Date of Birth:<span>"+DOB+ "</span></td>");

	                        out.println("    </tr>");
	                        
	                      
	                        
	                        out.println("    <tr>");
	                        out.println("      <td>Gender:<span>"+gender+ "</span></td>");
	                    //    out.println("      <td>Start Date :<span>"+start_date1+ "</span></td>");

	                        out.println("    </tr>");
	                        
	                        
	                    
	                        out.println("    <tr>");
	                    //    out.println("      <td>Expire Date :<span>"+end_date+ "</span></td>");
	                        out.println("      <td>Zone:<span>"+zone+ "</span></td>");

	                        out.println("    </tr>");
	                        

	                        out.println("    <tr>");
	                        out.println("      <td>Woreda:<span>"+woreda+ "</span></td>");
	                        out.println("      <td>kebelie:<span>"+kebelie+ "</span></td>");

	                        out.println("    </tr>");	                       
	                   	                       
	                        out.println("    <tr>");
	                        out.println("      <td>Blood Type:<span>"+bloodType+ "</span></td>");
	                        out.println("      <td>Eye Result:<span>"+eye+ "</span></td>");

	                        out.println("    </tr>");
	             
//	                        out.println("    <tr>");
//	                        out.println("<td><button onclick=\"printCard\">Print<button>"
//	                        	
//	                        		+ "</td>");
//	                        out.println(" </tr>");   
	                        out.println("  </table>");

	                        out.println("  </div>");
	                       // out.println("<button id=\"print_card\" onclick=\"printCard()\">Print</button>");
	                        out.println("<button id=\"card_cancel\" style=background-color:red;><a href=\"QueryDriverDetails\" style=text-decoration:none;color:white;>Cancel</a></button>");

	                        out.println("</div>");

	                        out.println("<script>");
	                        out.println("function printCard() {");
	                        out.println("  var elementsToHide = document.querySelectorAll('body > :not(#print_button_div)');");
	                        out.println("  var elementsArray = Array.prototype.slice.call(elementsToHide);");
	                        out.println("  for (var i = 0; i < elementsArray.length; i++) {");
	                        out.println("    elementsArray[i].style.display = 'none';");
	                        out.println("  }");
	                        out.println("  window.print();");
	                        out.println("}");
	                        out.println("</script>");

	                        
	                            }else {
		                        	out.print("<h1>The Result is Below The 50% you Can not Get a Card</h1>");

	                            }
	                        }else {
	                        	out.print("<h1>Please take Exam Frist</h1>");

	                        }
	                        // Print button
                          

                         }
                         else {
                      	   out.print("<h1>Please Register Health Result Before Giving a Licence!</h1>");
                         }

	                    }
	                } catch (SQLException | ClassNotFoundException e) {
	                    e.printStackTrace();
	                    out.print("<p>Error: " + e.getMessage() + "</p>");
	                } finally {
	                    try {
	                        if (rs != null) {
	                            rs.close();
	                        }
	                        if (ps != null) {
	                            ps.close();
	                        }
	                        if (conn != null) {
	                            conn.close();
	                        }
	                    } catch (SQLException ex) {
	                        ex.printStackTrace();
	                    }
	                }
	            }
	        }
	        out.print("<div style=margin-top:20rem;>");
	        RequestDispatcher dispatcher2 = request.getRequestDispatcher("/Footer");
	        dispatcher2.include(request, response);
	        out.print("</div>");

	        out.print("</body>");
	        out.print("</html>"); 
	        
   
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
