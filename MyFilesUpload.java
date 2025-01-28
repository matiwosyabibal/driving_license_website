package com.dlms;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

@WebServlet("/MyFilesUpload")
public class MyFilesUpload<RequestDispatcher> extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String driverName = "com.mysql.cj.jdbc.Driver";
    private String connectionUrl = "jdbc:mysql://localhost:3306/dlms_db";
    private String userName = "root";
    private String password = "root";
    private Connection conn = null;

    public void init() throws ServletException {
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(connectionUrl, userName, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	 response.setContentType("text/html");
         PrintWriter out = response.getWriter();
         
 	      response.setHeader("Pragma","no-cache");
 	      response.setHeader("Cache-Control","no-store");
 	      response.setHeader("Expires","0");
 	      response.setDateHeader("Expires",-1);
 	        HttpSession sessionA = request.getSession(true); // Retrieve the session without creating a new one if it doesn't exist
 	        HttpSession session=request.getSession(false);
 	        if (session.getAttribute("session_username")==null||session.getAttribute("Autority")==null)
 	        {
 	        response.sendRedirect("Login");
 	         return;
 	        }
 	        if(!session.getAttribute("Autority").equals("Admin")) {
 	         response.sendRedirect("Login"); //redirect to Home page

 	        }
 	        String Session_user_name=null;
 	        session=request.getSession();
 	         if(session!=null) {
 	        	 Session_user_name=(String)session.getAttribute("session_username");
 	         //user_type=(String)session.getAttribute("utype");
 	         }

        // Print HTML
        out.print("<html>");
        out.print("<head>");
        out.print("<title>User Registration Page</title>");
        out.print("</head>");
        out.print("<body style=margin-top:12rem;>");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/Header");
        dispatcher.include(request, response);
        try {
            String fileLocationPath = "D:/Eclipase file/DLMS_PROJECT/src/main/webapp/uploadfiles";
            String fileLocationForDB=null, writeFolderPath, fileName, fileContent = null;
            File writeFolder, savedFilePath = null;

            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (!isMultipart) {
                out.println("No file uploaded");
                return;
            }

            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
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
                        item.write(savedFilePath);
                    }
                }
            }

            String sql = "INSERT INTO users (user_id, user_password, user_type, user_status, fname, mname, lname, image,user_name) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, id);
                statement.setString(2,upassword);
                statement.setString(3, user_type);
                statement.setString(4, user_status);
                statement.setString(5, fname);
                statement.setString(6, mname);
                statement.setString(7, lname);
                statement.setString(8, fileLocationForDB);
                statement.setString(9, user_name);

                
                //new  part for hashpassword
             // Encrypt the password using MD5
//                String encryptedPassword = encryptMD5(upassword);
//                statement.setString(2, encryptedPassword);


                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                	out.println("<p style='color: rgb(2,100,0); >User registered successfully!'</p>");
              	  RequestDispatcher dispatcherCongra=request.getRequestDispatcher("CongraPage");
                	dispatcherCongra.include(request, response);  
                	} else {
                		  RequestDispatcher dispatcherCongra=request.getRequestDispatcher("ErrorPage");
                        	dispatcherCongra.include(request, response);                  }
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("An error occurred: " + e.getMessage());
        }
        out.println("<footer style=margin-top:30rem;>");
        out.println("<div class=\"footer_container\">");
        out.println("<p>&copy; 2024 Debre Markos Driving Licence Office. All rights reserved.</p>");
        out.println("<p>Contact: info@drivinglicence.com</p>");
        out.println("</div>");
        out.println("</footer>");

        out.print("</body>");
        out.print("</html>");

    }
 // Method to encrypt password using MD5
//    private String encryptMD5(String password) {
//        try {
//            // Create MD5 Hash
//            MessageDigest digest = MessageDigest.getInstance("MD5");
//            digest.update(password.getBytes());
//            byte[] bytes = digest.digest();
//
//            // Convert bytes to hexadecimal format
//            StringBuilder builder = new StringBuilder();
//            for (byte aByte : bytes) {
//                builder.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
//            }
//            return builder.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public void destroy() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
