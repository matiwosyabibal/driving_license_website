package com.dlms;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import javax.servlet.http.Part;

/**
 * Servlet implementation class UpdatePersonalInformationImplemenation
 */
@WebServlet("/UpdatePersonalInformationImplemenation")
public class UpdatePersonalInformationImplemenation extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String driverName = "com.mysql.cj.jdbc.Driver";
    private String dbUrl = "jdbc:mysql://localhost:3306/dlms_db";
    private String dbUsername = "root";
    private String dbPassword = "root";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePersonalInformationImplemenation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  PrintWriter out = response.getWriter();
	        Connection conn = null;
	        PreparedStatement ps = null;
	        InputStream inputStream = null;
	        String fileName="";
	        out.println("<html>");
	        out.println("<head>");
	        out.println("<title>Update Personal Information</title>");
	        out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/style.css' />");
	        out.println("</head>");
	        out.println("<body style='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:18rem;'>");

	        try {
	            Class.forName(driverName);
	            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
	            
	            // Get the form data
	        	String DriverId=request.getParameter("license_id");

	            String firstName = request.getParameter("first_name");
	            String middleName = request.getParameter("middle_name");
	            String lastName = request.getParameter("last_name");
	            String gender=request.getParameter("gender");
	        	String birth_Date=request.getParameter("birth_date");
	        	String woreda=request.getParameter("woreda");
	        	String zone=request.getParameter("zone");
	        	String kebela=request.getParameter("kebela");
	        	String phone=request.getParameter("phone");
//	        	String image=request.getParameter("image"); 
	        	int py = 0; // Default value in case phone is null
	        	if (phone != null) {
	        	    py = Integer.parseInt(phone);
	        	}

	            // Add other fields as necessary
	            // Handle file upload
	            Part filePart = request.getPart("image"); // Retrieves <input type="file" name="image">
	            if (filePart != null && filePart.getSize() > 0) {
	                // Get the input stream of the uploaded file
	                inputStream = filePart.getInputStream();
	                
	                // Determine the file name
	                fileName = DriverId + "_" + Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Generate a unique file name
	                // Specify the directory to save the file
	                String uploadPath = getServletContext().getRealPath("") + File.separator + "file_image_upload";
	                // Create the directory if it doesn't exist
	                File uploadDir = new File(uploadPath);
	                if (!uploadDir.exists()) {
	                    uploadDir.mkdir();
	                }
	                // Save the file to the specified directory
	                Files.copy(inputStream, Paths.get(uploadPath, fileName), StandardCopyOption.REPLACE_EXISTING);
	            }

	            // Update the personal information in the database
	            String sql = "UPDATE driverpersonalinformation SET fname=?, mnam=?, lname=?, gender=?, brithdate=?, woreda=?, zone=?, kebelie=?, phone=?, image=? WHERE LISENCE_ID=?";
	            ps = conn.prepareStatement(sql);
	            ps.setString(1, firstName);
	            ps.setString(2, middleName);
	            ps.setString(3, lastName);
	            ps.setString(4, gender);
	            ps.setString(5,birth_Date);
	            ps.setString(6,woreda);
	            ps.setString(7,zone);
	            ps.setString(8,kebela);
	            ps.setInt(9, py);
	            // Set the image path in the database
	            ps.setString(10, "uploadfiles/" + fileName); // Set the file path where the image is stored
	            ps.setString(11, DriverId);
	           // ps.setString(12, DriverId); // Set the 12th parameter

try {
	            int rowsUpdated = ps.executeUpdate();

	if (rowsUpdated> 0) {
		  RequestDispatcher dispatcherCongra=request.getRequestDispatcher("CongraPage");
        	dispatcherCongra.include(request, response);  
        	}else {
        		  RequestDispatcher dispatcherCongra=request.getRequestDispatcher("ErrorPage");
                	dispatcherCongra.include(request, response);  	 }

}catch(SQLException e) {
	e.printStackTrace();
}

	            	
	        } catch (Exception e) {
	            out.println("<h2>Error: " + e.getMessage() + "</h2>");
	        } 
	        out.print("</body>");
	        out.print("</html>");
	}

}
