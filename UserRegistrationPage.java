package com.dlms;

import java.io.File;


import java.io.IOException;
import java.io.PrintWriter;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
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

@WebServlet("/UserRegistrationPage")
public class UserRegistrationPage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UserRegistrationPage() {
        super();
    }  


	protected void service(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
    	//clearin all sessions

    	//seting content type and printWriter object.
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        //seting session.
        //HttpSession session = request.getSession(false);// Retrieve the session without creating a new one if it doesn't exist
     // if(session==null) {
    	 //  response.sendRedirect("Login");
       //}
        
     RequestDispatcher dispatcher = request.getRequestDispatcher("/Header");
        dispatcher.include(request, response);
        // Print HTML
     
        out.print("<html>");
        out.print("<head>");
        out.print("<title>User Registration Page</title>");
        out.print("<link src='stylesheet' href='mainstyle.css'");
        out.print("</head>");
        out.print("<body  style='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:18rem;'>");

        
        // Form title
        out.println("<h1 id=\"form_title\">User Registration Form</h1>");
        
        // Start of form
        out.println("<form action=\"MyFilesUpload\" method=\"post\" enctype=\"multipart/form-data\" class=\"user_registration_form\">");
        
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
        out.println("<option value=\"Traffic\">Traffic</option>");
        out.println("</select><br><br>");
        
        // User Status select field
        out.println("<label>User Status:</label>");
        out.println("<select name=\"user_status\">");
        out.println("<option value=\"\" required>Select Status</option>");
        out.println("<option value=\"ACTIVE\" required>ACTIVE</option>");
        out.println("<option value=\"BLOCK\" required>BLOCK</option>");
        out.println("</select><br>");
        
        // User Image file input field
        out.println("<label>User Image:</label>");
        out.println("<input type=\"file\" name=\"file\" required><br><br>");
        
        // Submit and Reset buttons
        out.println("<input type=\"submit\" value=\"Submit\" name=\"submit\">");
        out.println("<input type=\"reset\" value=\"Reset\" name=\"reset\">");
        
        // End of form
        out.println("</form>");
        
        // Footer section
        out.println("<footer>");
        out.println("<div class=\"footer_container\">");
        out.println("<p>&copy; 2024 Debre Markos Driving Licence Office. All rights reserved.</p>");
        out.println("<p>Contact: info@drivinglicence.com</p>");
        out.println("</div>");
        out.println("</footer>");
        
        // End of HTML response
        out.println("</body>");
        out.println("</html>");
        out.close();
       }
    	 }
   
  

