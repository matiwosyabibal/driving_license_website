package com.dlms;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/Update_Personal_Info")
public class Update_Personal_Info extends HttpServlet {
    private static final long serialVersionUID = 1L;
    String driverName = "com.mysql.cj.jdbc.Driver";
    String connectionUrl = "jdbc:mysql://localhost:3306/dlms_db?useSSL=false";
    String userName = "root";
    String password = "root";
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Statement s=null;

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
 	         }
        String fileLocationPath = "D:/Eclipase file/DLMS_PROJECT/src/main/webapp/uploadfiles";
        String fileLocationForDB = null, writeFolderPath, fileName, fileContent = null;
        File writeFolder, savedFilePath = null;
        
        //db varibles
        

        String write = request.getParameter("write");
        String save = request.getParameter("save");

        try {
            if (write == null && save == null) {
            	out.print("<html>");
            	out.print("<head>");
            	out.print("<title> Register Personal Information</title>");
            	out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/style1.css' />");
            	out.print("</head>");
            	out.print("<bodystyle='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:18rem;'>");
            	out.print("<header>\r\n"
            			+ "    <nav class=\"Rnavbar\">\r\n"
            			+ "    <a href=\"Register_Personal_Information\"> Personal</a>\r\n"
            			+ "    <a href=\"HealthStatus\">Health Status</a>\r\n"
            			+ "    <a href=\"Practical_Result\">Practical Result</a>\r\n"
            			+ "    <a href=\"Theory_Result\">theory Result</a>\r\n"
            			+ "    <a href=\"Logout\">Logout</a>\r\n"
            			+ "</nav>\r\n"
            			+ "</header>  ");



                Class.forName(driverName);
                conn = DriverManager.getConnection(connectionUrl, userName, password);

                String driverId = request.getParameter("id");
                String sql = "SELECT * FROM driverpersonalinformation WHERE LISENCE_ID = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, driverId);
                rs = ps.executeQuery();

                if (rs.next()) {
                	String gender=rs.getString("gender");
                	out.print("<form class=reg action=Update_Personal_Info?save=1 method=POST enctype=\"multipart/form-data\"> \r\n"
                			+ "    <div class=\"Bform\">  \r\n"
                			+ "        <div class=\"Bfrom1\">\r\n"
                			+ "            <div class=\"lo\">\r\n"
                			+ "                <input type=\"text\" name=\"first_name\" id=\"fname\" placeholder=\"First Name\"  value='" + rs.getString("fname") + "' required>\r\n"
                			+ "            </div>\r\n"
                			+ "            <div class=\"lo\">\r\n"
                			+ "                <input type=\"text\" name=\"middle_name\" id=\"mname\" placeholder=\"Middle Name\"  value='" + rs.getString("mname") + "' required>\r\n"
                			+ "            </div>\r\n"
                			+ "            <div class=\"lo\">\r\n"
                			+ "                <input type=\"text\" name=\"last_name\" id=\"lname\"  placeholder=\"Last Name\"  value='" + rs.getString("lname") + "' required>\r\n"
                			+ "            </div>\r\n"
                			+ "            <div class=\"lg G\">\r\n"
                			+ "                <label>Gender:</label>\r\n"
                			
                			+ "                Female:<input class=\"r\" type=\"radio\" name=gender  value=Female>\r\n"
                			+ "                Male:<input class=\"r\" type=\"radio\"   name=gender    value=Male>\r\n"
                			+ "            </div>\r\n"
                			+ "            <div class=\"lg\">\r\n"
                			+ "                <label for=\"fname\">BirthDate</label>\r\n"
                			+ "                <input type=\"date\" name=\"birth_date\" placeholder=\"BirthDate\" value='" + rs.getString("brithdate") + "' required>\r\n"
                			+ "            </div>\r\n"
                			+ "        </div>\r\n"
                			+ "        <div class=\"Bform2\"> \r\n"
                			+ "            <div class=\"lo\">\r\n"
                			+ "                <input type=\"text\" name=\"zone\" id=\"zone\" placeholder=\"Zone\"  value='" + rs.getString("zone") + "' required>\r\n"
                			+ "            </div>\r\n"
                			+ "            <div class=\"lo\">\r\n"
                			+ "                <input type=\"text\" name=\"woreda\" id=\"worda\" placeholder=\"woreda\"  value='" + rs.getString("woreda") + "'  required>\r\n"
                			+ "            </div>\r\n"
                			+ "            <div class=\"lo\">\r\n"
                			+ "                <input type=\"number\" max=\"20\" name=\"kebela\" id=\"kabala\"  placeholder=\"Kebele\"  value='" + rs.getString("kebelie") + "'  required>\r\n"
                			+ "            </div>\r\n"
                			+ "            <div class=\"lo\">\r\n"
                			+ "                <input type=\"number\" name=\"phone\" id=\"phone\"  placeholder=\"Phone number\"  value='" + rs.getString("phone") + "'  required>\r\n"
                			+ "            </div>\r\n"
                			+ "            <div class=\"lo\">\r\n"
                			+ "                <input type=\"number\" name=\"license_id\" id=\"Dl\"  placeholder=\"Driving License Number\"   value='" + rs.getString("LISENCE_ID") + "' required>\r\n"
                			+ "            </div>\r\n"
                			+ "            <div class=\"lg\">\r\n"
                			+ "                <label for=\"img\">Image</label>\r\n"
                			+ "                <input type=\"file\" name=\"image\" id=\"image\" required>\r\n"
                			+ "            </div>\r\n"
                			+ "        </div>\r\n"
                			+ "    </div>\r\n"
                			+ "    <input class=\"reset R\" type=\"submit\" name=\"Register\" value=\"Update\">\r\n"
                			+ "    <a href=\"Display_Personal_Info\">See_Student</a>\r\n"
                			+ "</form>");
                } else {
                    out.println("<p>No record found for driver ID: " + driverId + "</p>");
                }



            } else if (save != null && write == null) {
                boolean isMultipart = ServletFileUpload.isMultipartContent(request);
                if (!isMultipart) {
                    out.println("No file uploaded");
                    return;
                }

                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = null;

                try {
                    items = upload.parseRequest(request);
                } catch (FileUploadException e) {
                    e.printStackTrace();
                }

                Iterator<FileItem> iterator = items.iterator();

                String idmain = null;

                String firstName = null;
                String middleName = null;
                String lastName = null;
                String gender = null;
                String birth_Date = null;
                String woreda= null;
                String zone = null;
                String kebela = null;
                String phone = null;

                while (iterator.hasNext()) {
                    FileItem item = iterator.next();
                    if (item.isFormField()) {
                        String fieldName = item.getFieldName();
                        String value = item.getString();

                        switch (fieldName) {

                            case "first_name":
                                firstName = value;
                                break;
                            case "middle_name":
                                middleName = value;
                                break;
                            case "last_name":
                                lastName = value;
                                break;
                            case "gender":
                                gender = value;
                                break;
                            case "birth_date":
                                birth_Date = value;
                                break;
                            case "woreda":
                                woreda = value;
                                break;
                            case "zone":
                                zone = value;
                                break;
                            case "kabela":
                                kebela = value;
                                break;
                            case "phone":
                                phone = value;
                                break;
                            case "license_id":
                            	idmain = value;
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
                                out.print(idmain);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Class.forName(driverName);
                            conn = DriverManager.getConnection(connectionUrl, userName, password);

                            String sql = "UPDATE driverpersonalinformation SET fname=?, mname=?, lname=?, gender=?, brithdate=?, woreda=?, zone=?, kebelie=?, phone=?, image=? WHERE LISENCE_ID=?";

                            ps = conn.prepareStatement(sql);
                                ps.setString(1, firstName);
                                ps.setString(2, middleName);
                                ps.setString(3, lastName);
                                ps.setString(4, gender);
                                ps.setString(5, birth_Date);
                                ps.setString(6, woreda);
                                ps.setString(7, zone);
                                ps.setString(8, kebela);
                                ps.setString(9, phone);
                                ps.setString(10, fileLocationForDB);
                                ps.setString(11, idmain);
                                int rowsAffected = ps.executeUpdate();


                                if (rowsAffected > 0) {
                                    out.println("<h1> Data is Succesfully Updated!<h1>");
                                } else {
                                    out.println("<h1>Failed to Update The Data!</h1>");
                                }
                            
                        }
                    }
                }
                out.print("</body>");
                out.print("</html>");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.getMessage();
        }
        finally {
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