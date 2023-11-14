package assignment_SessionCookies;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/targetServlet")
public class targetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection conn;
	
	@Override
	public void init() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://mariadb.vamk.fi/e2101064_java", "e2101064", "3w3PthNwgPk");
			System.out.println(conn);
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {		
		HttpSession session = req.getSession(false);
		
		//String username = req.getParameter("username");
		
		res.setContentType("text/html");
		
		
		PrintWriter out = res.getWriter();
		Cookie[] cookies = req.getCookies();
		/*
		for(int i = 0; i < cookies.length; i++) {
			out.println(cookies[i].getName());
			out.println(cookies[i].getValue());
		} */
		
		if(cookies != null) {
			for(int i = 0; i < cookies.length; i++) {
				out.println("cookie name: " + cookies[i].getName() + "<br>");
				out.println("cookie value: " + cookies[i].getValue()+ "<br>");
			}
			}else {
				out.println("No cookies found");
			}
		
		if(session != null) {
			String username = (String) session.getAttribute("username");
			//String password = (String) session.getAttribute("password");

			out.println("Hello " + username);
		} else {
			res.sendRedirect("input.html");
			out.println("Session has ended");
		}
	}
	
	@Override
	public void destroy() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
