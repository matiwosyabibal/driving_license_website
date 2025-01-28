import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class tvfs
 */
@WebServlet("/tvfs")
public class tvfs extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dlms_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public tvfs() {
        super();
    }

    /**
     * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // HTML Form and CSS directly embedded
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Issue Traffic Violation</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }");
            out.println(".container { width: 50%; margin: auto; overflow: hidden; background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
            out.println("h2 { text-align: center; }");
            out.println("label { display: block; margin: 15px 0 5px; }");
            out.println("input[type='text'], input[type='date'], textarea { width: 100%; padding: 10px; margin: 5px 0 10px; border: 1px solid #ccc; border-radius: 5px; }");
            out.println("input[type='submit'] { background: #5cb85c; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer; }");
            out.println("input[type='submit']:hover { background: rgb(15,100,255); }");

            out.println("input[type='reset'] { background: red; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer; }");
            out.println("input[type='reset']:hover { background: rgb(15,100,255); }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body style='color:rgb(10,5,250);background-color:rgb(170,255,250);margin-top:20rem;';>");
            out.println("<div class='container'>");
            out.println("<h2>Issue Traffic Violation</h2>");
            out.println("<form action='tvfs' method='post'>");
            out.println("<label for='violationType'>Violation Type:</label>");
            out.println("<input type='text' id='violationType' name='violationType' required><br><br>");
            out.println("<label for='date'>Date:</label>");
            out.println("<input type='date' id='date' name='date' required><br><br>");
            out.println("<label for='LISENCE_ID'>LISENCE ID:</label>");
            out.println("<input type='text' id='licensePlate' name='LISENCE_ID' required><br><br>");
            out.println("<label for='description'>Description:</label><br>");
            out.println("<textarea id='description' name='description' rows='4' required></textarea><br><br>");
            out.println("<input type='reset' value='Reset'>");
            out.println("<input type='submit' value='Submit'>");
            out.print("<label><a href='traffic_home' >EXIT</a></label>");
            
            out.println("</form>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

            // Retrieving form data if submitted
            String violationType = request.getParameter("violationType");
            String date = request.getParameter("date");
            String LISENCE_ID = request.getParameter("LISENCE_ID");
            String description = request.getParameter("description");

            // Checking if form data is not null, meaning the form was submitted
            if (violationType != null && date != null && LISENCE_ID != null && description != null) {
                // Insert the data into the database
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    String sql = "INSERT INTO violations (violationType, date, LISENCE_ID, description) VALUES (?, ?, ?, ?)";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, violationType);
                    statement.setString(2, date);
                    statement.setString(3, LISENCE_ID);
                    statement.setString(4, description);
                    statement.executeUpdate();

                    out.println("<div class='container'>");
                    out.println("<h2>Traffic Violation Details</h2>");
                    out.println("<p><strong>Violation Type:</strong> " + violationType + "</p>");
                    out.println("<p><strong>Date:</strong> " + date + "</p>");
                    out.println("<p><strong>LISENCE_ID:</strong> " + LISENCE_ID + "</p>");
                    out.println("<p><strong>Description:</strong> " + description + "</p>");
                    //out.print("<input type=submit value=Close ");
                    out.print("<label><a href='tvfs' >Close</a></label>");
                    out.println("</div>");

                    conn.close();
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                    out.println("<p>There was an error processing your request. Please try again later.</p>");
                }
            }
        }
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
