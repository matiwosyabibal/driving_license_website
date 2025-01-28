package com.dlms;

import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 * Servlet implementation class UserUpdateImplementationMain
 */
@WebServlet("/UserUpdateImplementationMain")
public class UserUpdateImplementationMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String driverName = "com.mysql.cj.jdbc.Driver";
    private String connectionUrl = "jdbc:mysql://localhost:3306/dlms_db?useSSL=false";
    private String userName = "root";
    private String password = "root";
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUpdateImplementationMain() {
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
            String fileLocationPath = "D:/Eclipase file/DLMS_PROJECT/src/main/webapp/uploadfiles";
            String fileLocationForDB=null, writeFolderPath, fileName, fileContent = null;
            File writeFolder, savedFilePath = null;
	        String write = request.getParameter("write");
	        String save = request.getParameter("save");

	        try {
	            if (write == null && save==null) {
	                out.print("<html>");
	                out.print("<head>");
	                out.print("<title>User Registration Page</title>");
	                out.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"mainstyle.css\">");
	                out.print("</head>");
	                out.print("<body style='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:18rem;' style>");

	                RequestDispatcher dispatcher = request.getRequestDispatcher("/Header");
	                dispatcher.include(request, response);

	                Class.forName(driverName);
	                conn = DriverManager.getConnection(connectionUrl, userName, password);

	                String passedid = request.getParameter("id"); // Moved this line inside the "write == null" block

	                String sqlOne = "SELECT * FROM users WHERE user_id=?";
	                ps = conn.prepareStatement(sqlOne);
	                ps.setString(1, passedid);
	                rs = ps.executeQuery();

	                if (rs.next()) {
	                    String image = rs.getString("image");
	                    out.println("<form action='UserUpdateImplementationMain?save=1' method='POST'  class='user_registration_form' enctype=\"multipart/form-data\">");
	                    out.println("<label>User ID:</label><input type='text' name='user_id' value='" + passedid + "' readonly><br>");
	                    out.println("<label>First Name:</label><input type='text' name='fname' value='" + rs.getString("fname") + "'><br>");
	                    out.println("<label>Middle Name:</label><input type='text' name='mname' value='" + rs.getString("mname") + "'><br>");
	                    out.println("<label>Last Name:</label><input type='text' name='lname' value='" + rs.getString("lname") + "'><br>");
	                    out.println("<label>Password:</label><input type='password' name='password' value='" + rs.getString("user_password") + "'><br>");
	                    out.println("<label>User Name:</label><input type='text' name='user_name' value='" + rs.getString("user_name") + "'><br>");
	                    out.println("<label>User Type:</label><input type='text' name='user_type' value='" + rs.getString("user_type") + "'><br>");
	                    out.println("<label>User Status:</label>");
	                    out.print("<select name='user_status'>"
	                            + "<option value='" + rs.getString("user_status")+ "'>" + rs.getString("user_status")+ "</option>"
	                            + "<option value='ACTIVE'>ACTIVE</option>"
	                            + "<option value='BLOCK'>BLOCK</option>"
	                            + "</select>");
	                    out.println("<label>User Image:</label><input type='file' name='file' value=\"" + image +"\"><br>");

	                    out.println("<input type='submit' name='write' value='Update User'><br>");
	                    out.println("</form>");
	                } 

	                out.print("</body>");
	                out.print("</html>");
	            } 
	            else if(write==null && save!=null){
	                // Handle form submission for updating user information
	                // Similar to your existing logic
	            	
	                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
	                if (!isMultipart) {
	                    out.println("No file uploaded");
	                    return;
	                }

	                FileItemFactory factory = new DiskFileItemFactory();
	                ServletFileUpload upload = new ServletFileUpload(factory);
	                List<FileItem> items=null;
					try {
						items = upload.parseRequest(request);
					} catch (FileUploadException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                Iterator<FileItem> iterator = items.iterator();

	                String id = null;
	                String upassword = null;
	                String user_type = null;
	                String user_status = null;
	                String fname = null;
	                String mname = null;
	                String lname = null;
	                String user_name=null;

	                while (iterator.hasNext()) {
	                    FileItem item = iterator.next();
	                    if (item.isFormField()) {
	                        String fieldName = item.getFieldName();
	                        String value = item.getString();

	                        switch (fieldName) {
	                            case "user_id":
	                                id = value;
	                                break;
	                            case "fname":
	                                fname = value;
	                                break;
	                            case "mname":
	                                mname = value;
	                                break;
	                            case "lname":
	                                lname = value;
	                                break;
	                            case "user_status":
	                                user_status = value;
	                                break;
	                            case "user_type":
	                                user_type = value;
	                                break;
	                            case "password":
	                                upassword = value;
	                                break;
	                            case "user_name":
	                                user_name = value;
	                                break;
	                        }
	                       
	                    } else {
	                        fileName = new File(item.getName()).getName();
	                        fileContent = item.getContentType();
	                        fileLocationForDB = "uploadfiles/" + fileName;

	                        writeFolder = new File(fileLocationPath);
	                        if (!writeFolder.exists()) {
	                            writeFolder.mkdir();
	                        }

	                        writeFolderPath = writeFolder + "/" + fileName;
	                        savedFilePath = new File(writeFolderPath);

	                        if (savedFilePath.exists() || !(fileContent.equals("image/png") || fileContent.equals("image/jpeg"))) {
	                            if (savedFilePath.exists()) {
	                                out.println("Error: The file already exists.");
	                            } else {
	                                out.println("Error: Please upload only PNG or JPEG images.");
	                            }
	                        } else {
	                            try {
									item.write(savedFilePath);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
	        	                Class.forName(driverName);
	        	                conn = DriverManager.getConnection(connectionUrl, userName, password);
	                         	String updateSql = "UPDATE users SET user_password=?, user_type=?, user_status=?, fname=?, mname=?, lname=?, image=?, user_name=? WHERE user_id=?";
	        	                try (PreparedStatement statement = conn.prepareStatement(updateSql)) {
	        	           
	        	                    statement.setString(1, upassword);
	        	                    statement.setString(2, user_type);
	        	                    statement.setString(3, user_status);
	        	                    statement.setString(4, fname);
	        	                    statement.setString(5, mname);
	        	                    statement.setString(6, lname);
	        	                    statement.setString(7, fileLocationForDB);
	        	                    statement.setString(8, user_name);
	        	                    statement.setString(9, id);


	        	                    int rowsAffected = statement.executeUpdate();
	        	                    if (rowsAffected > 0) {
	        	                  	  RequestDispatcher dispatcherCongra=request.getRequestDispatcher("CongraPage");
	                                	dispatcherCongra.include(request, response);  	        	                    } else {
	                                		  RequestDispatcher dispatcherE=request.getRequestDispatcher("ErrorPage");
	                                        	dispatcherE.include(request, response);  	        	                    }
	        	                } 
	                            
	                        }
	                    }
	                }
	      
	            }
	            
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (ps != null) ps.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}