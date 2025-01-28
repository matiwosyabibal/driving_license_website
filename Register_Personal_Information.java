package com.dlms;





import java.io.File;
import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;





//import org.apache.tomcat.util.http.fileupload.FileItem; 

/**
 * Servlet implementation class Register_Personal_Information
 */
@WebServlet("/Register_Personal_Information")
public class Register_Personal_Information extends HttpServlet {
	private static final long serialVersionUID = 1L;
           
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register_Personal_Information() {
        super();
        // TODO Auto-generated constructor stub
     }

    String driverName="com.mysql.cj.jdbc.Driver";
	String dbUrl="jdbc:mysql://localhost:3306/dlms_db";
	String dbpassword="root";
	String dbusername="root";
	
	Connection conn=null;
	PreparedStatement ps=null;
	
//	Global variable for file upload 
	 String fileLocationPath="";
 
	 public void init() throws ServletException {
	        try {
	            Class.forName(driverName);
	            conn = DriverManager.getConnection(dbUrl, dbusername, dbpassword);
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	        }
	    }
 
 
 
	
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
		         if(session==null) {
		        	 Session_user_name=(String)session.getAttribute("session_username");
		         //user_type=(String)session.getAttribute("utype");
		         }try {
		        	 out.print("<html>");
		        		out.print("<head>");
		        		out.print("<title> Register Personal Information</title>");
		        		out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/style1.css' />");
		        		out.print("</head>");
		        		out.print("<body style='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:18rem;'>");
		        		out.print("<header>\r\n"
		        				+ "    <nav class=\"Rnavbar\">\r\n"
		        				+ "    <a href=\"Register_Personal_Information\"> Personal</a>\r\n"
		        				+ "    <a href=\"HealthStatus\">Health Status</a>\r\n"
		        				+ "    <a href=\"Practical_Result\">Practical Result</a>\r\n"
		        				+ "    <a href=\"Theory_Result\">theory Result</a>\r\n"
		        				+ "    <a href=\"Logout\">Logout</a>\r\n"
		        				+ "</nav>\r\n"
		        				+ "</header>  ");
		        	
		        	   String Submit=request.getParameter("save");
		        		out.print("<form class=reg action=Register_Personal_Information?save=1 method=POST enctype=\"multipart/form-data\" style='color:rgb(10,5,250);'> \r\n"
		        				+ "    <div class=\"Bform\">  \r\n"
		        				+ "        <div class=\"Bfrom1\">\r\n"
		        				+ "            <div class=\"lo\">\r\n"
		        				+ "                <input type=\"text\" name=\"first_name\" id=\"fname\" placeholder=\"First Name\" required>\r\n"
		        				+ "            </div>\r\n"
		        				+ "            <div class=\"lo\">\r\n"
		        				+ "                <input type=\"text\" name=\"middle_name\" id=\"mname\" placeholder=\"Middle Name\" required>\r\n"
		        				+ "            </div>\r\n"
		        				+ "            <div class=\"lo\">\r\n"
		        				+ "                <input type=\"text\" name=\"last_name\" id=\"lname\"  placeholder=\"Last Name\" required>\r\n"
		        				+ "            </div>\r\n"
		        				+ "            <div class=\"lg G\">\r\n"
		        				+ "                <label>Gender:</label>\r\n"
		        				+ "                Female:<input class=\"r\" type=\"radio\" name=gender  value=Female>\r\n"
		        				+ "                Male:<input class=\"r\" type=\"radio\"   name=gender    value=Male>\r\n"
		        				+ "            </div>\r\n"
		        				+ "            <div class=\"lg\">\r\n"
		        				+ "                <label for=\"fname\">BirthDate</label>\r\n"
		        				+ "                <input type=\"date\" name=\"birth_date\" placeholder=\"BirthDate\" required>\r\n"
		        				+ "            </div>\r\n"
		        				+ "        </div>\r\n"
		        				+ "        <div class=\"Bform2\"> \r\n"
		        				+ "            <div class=\"lo\">\r\n"
		        				+ "                <input type=\"text\" name=\"zone\" id=\"zone\" placeholder=\"Zone\" required>\r\n"
		        				+ "            </div>\r\n"
		        				+ "            <div class=\"lo\">\r\n"
		        				+ "                <input type=\"text\" name=\"woreda\" id=\"worda\" placeholder=\"woreda\"  required>\r\n"
		        				+ "            </div>\r\n"
		        				+ "            <div class=\"lo\">\r\n"
		        				+ "                <input type=\"number\" max=\"20\" name=\"kebela\" id=\"kabala\"  placeholder=\"Kebele\"  required>\r\n"
		        				+ "            </div>\r\n"
		        				+ "            <div class=\"lo\">\r\n"
		        				+ "                <input type=\"number\" name=\"phone\" id=\"phone\"  placeholder=\"Phone number\"  required>\r\n"
		        				+ "            </div>\r\n"
		        				+ "            <div class=\"lo\">\r\n"
		        				+ "                <input type=\"number\" name=\"license_id\" id=\"Dl\"  placeholder=\"Driving License Number\"  required>\r\n"
		        				+ "            </div>\r\n"
		        				+ "            <div class=\"lg\">\r\n"
		        				+ "                <label for=\"img\">Image</label>\r\n"
		        				+ "                <input type=\"file\" name=\"image\" id=\"image\" required>\r\n"
		        				+ "            </div>\r\n"
		        				+ "        </div>\r\n"
		        				+ "    </div>");
		        				out.print("<input class=reset R type=submit name='Register' value='Register'>"); 
		        				out.print(" <a href='Display_Personal_Info'>See_Student</a>"); 
		        				out.print("</form>"); 
	
	
//	image uploading 
	
	
if(Submit!=null) {

    try {
    	 String fileLocationPath = "D:/Eclipase file/DLMS_PROJECT/src/main/webapp/uploadfiles";
         String fileLocationForDB=null, writeFolderPath, fileName, fileContent = null;
         File writeFolder, savedFilePath = null;

         boolean isMultipart = ServletFileUpload.isMultipartContent(request);
         if (!isMultipart) {
             out.println("No file uploaded");
             return;
         }  
    
         DiskFileItemFactory factory = new DiskFileItemFactory();
         ServletFileUpload upload = new ServletFileUpload(factory);
         List<FileItem> items = upload.parseRequest(request);
         Iterator<FileItem> iterator = items.iterator();
         //Variable declaration.
        String firstName = null;
        String middleName = null;
        String lastName = null;
        String gender = null;
        String birthDate = null;
        String woreda = null;
        String zone = null;
        String kebela = null;
        String phone = null;
        String licenseId = null;
        
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
                    case "birth_date":
                    	birthDate = value;
                        break;
                    case "gender":
                    	gender = value;
                        break;
                    case "woreda":
                    	woreda = value;
                        break;
                    case "zone":
                    	zone = value;
                        break;
                    case "kebela":
                    	kebela = value;
                        break;
                    case "phone":
                    	phone = value;
                        break;
                    case "license_id":
                    	licenseId = value;
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
                        out.println("<h1>Sory The file already exists.</h1>");
                    } else {
                        out.println("<h1>Sory: Please upload only PNG or JPEG images.</h1>");
                    }
                } else {
                    item.write(savedFilePath);
                }
            }
        }

   

        String sql = "INSERT INTO driverpersonalinformation (fname, mname, lname, gender, brithdate, woreda, zone, kebelie, phone, image,LISENCE_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(sql);
        ps.setString(1, firstName);
        ps.setString(2, middleName);
        ps.setString(3, lastName);
        ps.setString(4, gender);
        ps.setString(5, birthDate);
        ps.setString(6, woreda);
        ps.setString(7, zone);
        ps.setString(8, kebela);
        ps.setString(9, phone);
        ps.setString(10, fileLocationForDB);
        ps.setString(11, licenseId);

        int i = ps.executeUpdate();
        if (i > 0) {
            out.print("<h1 style='color: rgb(0,255,0); text-align: left; font-size: 30px;'>Data Inserted Successfully !</h1>");
        } else {
            out.println("Failed to insert the data");
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("An error occurred: " + e.getMessage());
    }	
}

         


     
	out.print("</body>");
	out.print("</html>");		
	
	
}catch(Exception e) {
	out.print(e.getMessage());
}
	
		
		
		
		
		
		
		
	}

}

