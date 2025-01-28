

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
/**
 * Servlet implementation class regg
 */
@WebServlet("/regg")
public class regg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public regg() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		 String submit = request.getParameter("submit");
		 
		out.print(" <html><head>");
		out.print("<title>Registration User</title></head>");
		out.print("<body bgcolor=#66CCFF>");
		//Creating an object of RequestDispatcher to include the content of another servlet named -Header
		//RequestDispatcher rd = request.getRequestDispatcher("Header");
		//Using RequestDispatcher include() method to include the content of Header Servlet in this Servlet.
		//rd.include(request,response);
		out.print("<h1 style=color:#cc6600>User Registration Page</h1>");
		if(submit==null)
		{
			out.println("<form action=\"regg\" method=\"post\" enctype=\"multipart/form-data\" class=\"regg\">");
	        
	        // User ID input field
	        out.println("<label>User ID:</label>");
	        out.println("<input type=\"text\" name=\"user_id\" required><br><br>");
	        
	        // First Name input field
	        out.println("<label>First Name:</label>");
	        out.println("<input type=\"text\" name=\"fname\" required><br><br>");
	        
	        // Middle Name input field
	        out.println("<label>Middle Name:</label>");
	        out.println("<input type=\"text\" name=\"mname\" required><br><br>");
	        
	        // Last Name input field
	        out.println("<label>Last Name:</label>");
	        out.println("<input type=\"text\" name=\"lname\" required><br><br>");
	        
	        // User Password input field
	        out.println("<label>User Password:</label>");
	        out.println("<input type=\"password\" name=\"password\" required><br><br>");
	        
	        // User Name input field
	        out.println("<label>User Name:</label>");
	        out.println("<input type=\"text\" name=\"user_name\" required><br><br>");
	        
	        // User Type select field
	        out.println("<label>User Type:</label>");
	        out.println("<select name=\"user_type\" required>");
	        out.println("<option value=\"Admin\">Select User Type</option>");
	        out.println("<option value=\"Admin\">Admin</option>");
	        out.println("<option value=\"Registrar\">Registrar</option>");
	        out.println("<option value=\"Owner\">Owner</option>");
	        out.println("</select><br><br>");
	        
	        // User Status select field
	        out.println("<label>User Status:</label>");
	        out.println("<select name=\"user_status\">");
	        out.println("<option value=\"\" required>Select Status</option>");
	        out.println("<option value=\"ACTIVE\" required>ACTIVE</option>");
	        out.println("<option value=\"BLOCK\" required>BLOCK</option>");
	        out.println("</select><br>");
	        
	        // User Image file input field
	      //  out.println("<label>User Image:</label>");
	      //  out.println("<input type=\"file\" name=\"file\" required><br><br>");
	        
	        // Submit and Reset buttons
	        out.println("<input type=\"submit\" value=\"Submit\" name=\"submit\">");
	        out.println("<input type=\"reset\" value=\"Reset\" name=\"reset\">");
	        
	        // End of form
	        out.println("</form>");
		}else { 
		String encrypted_password=null;
		String username = request.getParameter("user_name");
		 String password = request.getParameter("password");
		 String userestatus = request.getParameter("user_status");
		 String user_id = request.getParameter("user_id");
		 String fname = request.getParameter("fname");
		 String mname = request.getParameter("mname");
		 String lname = request.getParameter("lname");
		 String file = request.getParameter("file");
		 String usertype = request.getParameter("user_type");
		 try { 
		 int KEY_LENGTH = 256;
		 int ITERATION_COUNT = 65536;
		// Define your secret key and salt (keep these secure and don't hardcode in production)
		 String secretKey = "12_win";
		 String salt = "_gm+";
		//....Encryption 
		 SecureRandom secureRandom = new SecureRandom();
		 byte[] iv = new byte[16];
		 secureRandom.nextBytes(iv);
		 IvParameterSpec ivspec = new IvParameterSpec(iv);
		 SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		 KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
		 SecretKey tmp = factory.generateSecret(spec);
		 SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");
		 Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		 cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);
		 byte[] cipherText = cipher.doFinal(password.getBytes("UTF-8")); // password read from the form
		 byte[] encryptedData = new byte[iv.length + cipherText.length];
		 System.arraycopy(iv, 0, encryptedData, 0, iv.length);
		 System.arraycopy(cipherText, 0, encryptedData, iv.length, cipherText.length);
		 encrypted_password=Base64.getEncoder().encodeToString(encryptedData);
		 }catch (Exception e) {
		 // Handle the exception properly
		 e.printStackTrace();
		 
		 } String driverName="com.mysql.cj.jdbc.Driver";
		String dbUrl="jdbc:mysql://localhost:3306/dlms_db";
		String dbusername="root";
		String dbpassword="root";
		 
		Connection conn=null;
		 PreparedStatement ps=null;
		 try{
		 Class.forName(driverName);
		 }catch(ClassNotFoundException e){
		System.out.println(e.getMessage());
		}
	
		try {
		conn=DriverManager.getConnection(dbUrl,dbusername,dbpassword);
		String sql="insert into users (user_id,user_password,user_type,user_status,fname,mname,lname,User_Name) VALUES (?,?,?,?,?,?,?,?)";
		ps = conn.prepareStatement(sql);
		ps.setString(1,user_id);
		ps.setString(2,encrypted_password);
		ps.setString(3,usertype);
		ps.setString(4,userestatus);
	
		ps.setString(5,mname);
		ps.setString(6,lname);
		ps.setString(7,file);
		ps.setString(8,username);
		int i = ps.executeUpdate();
		 if(i>0) {
		out.println("<font color=green>The following user record has been inserted into user table</font>");
		out.print("<form action= regg method=post >");
		out.print(" User Name: <input type=text name=username value="+username+" readonly /><br>");
		out.print(" Password: <input type=text name=password value="+password+" readonly /><br>");
		out.print(" User Type: <input type=text name=usertype value='"+usertype+"' readonly /><br>");
		out.print("<input type=submit value=Close />");
		out.print("</form>");
		 }else
		 out.println("Failed to insert the data");
		out.println("</body></html>");
		}catch (SQLException e){
		 out.println(e.getMessage());
		 }
		}
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
