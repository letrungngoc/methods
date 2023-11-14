package assignment_JDBC;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteStudentServlet")
public class deleteStudentServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private PreparedStatement ps;
	
	@Override
	public void init() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/e2101064_java", "e2101064", "F3BjznsEvGc");
			ps = conn.prepareStatement("DELETE FROM student WHERE number = ?");
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
       
		// Get the student number from the form
        int number = Integer.parseInt(req.getParameter("number"));

       

        try {
      
            ps.setInt(1, number);
            
            res.setContentType("text/html");

            int result = ps.executeUpdate();
            if (result > 0) {
                res.getWriter().println("Student with number " + number + " has been deleted.");
            } else {
                res.getWriter().println("No student found with number " + number + ".");
            }
    			
    		} catch (SQLException e) {
    			e.printStackTrace();
	}
}
    	
    	
    @Override
    	public void destroy() {
    		try {
    			ps.close();
    			conn.close();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		
    	}
    }
}
