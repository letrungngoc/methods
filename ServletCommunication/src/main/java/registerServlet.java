import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/registerServlet")
public class registerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Connection conn;
	private PreparedStatement ps;
	
	@Override
	public void init() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/e2101064_java", "e2101064", "VAh3Dpajzfd");
			ps = conn.prepareStatement("INSERT INTO login (email, password) VALUES (?, ?)");
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		try {
			
			ps.setString(1, email);
			ps.setString(2, password);
			RequestDispatcher reqDis;
			
			int result = ps.executeUpdate();
		
		if(result > 0) {
			// register success
			System.out.println("Demo");
			reqDis = req.getRequestDispatcher("homeServlet");
			req.setAttribute("message", "register successfully");
			reqDis.forward(req, res);
		} else {
			// register unsuccessful
			reqDis = req.getRequestDispatcher("login.html");
			reqDis.include(req, res);
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