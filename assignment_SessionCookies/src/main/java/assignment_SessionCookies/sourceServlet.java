package assignment_SessionCookies;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/sourceServlet")
public class sourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		HttpSession session = req.getSession();
		session.setMaxInactiveInterval(60);
		
		Cookie[] cookies = req.getCookies();
		for(int i = 0; i < cookies.length; i++) {
			System.out.println(cookies[i].getName());
			System.out.println(cookies[i].getValue());
		}
		
		res.addCookie(new Cookie("token", "sometoken"));
		session.setAttribute("username", username);
		session.setAttribute("password", password);
		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		
		// res.sendRedirect("targetServlet");
		
		out.println("<a href='targetServlet'> Click here to get the user name</a>");
	}
}