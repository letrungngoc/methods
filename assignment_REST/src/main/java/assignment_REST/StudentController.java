package assignment_REST;

	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.PrintWriter;
	import java.util.List;

	import com.google.gson.Gson;

	import jakarta.servlet.ServletException;
	import jakarta.servlet.annotation.WebServlet;
	import jakarta.servlet.http.HttpServlet;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;

	@WebServlet("/students/*")
	public class StudentController extends HttpServlet{

		private static final long serialVersionUID = 1L;
		
		private StudentDAO studentDAO;
		
		private Gson gson;
		
		public void init() {
			studentDAO = new StudentDAO();
			gson = new Gson();
		}
		
		private void sendAsJSON(HttpServletResponse response, Object obj) throws ServletException, IOException {
			response.setContentType("application/json");
			String result = gson.toJson(obj);
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
		}
		
		// Get users
		// GET/RestAPI/users/
		// GET/RestAPI/users/id 
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			String pathInfo = req.getPathInfo();
			
			res.addHeader("Access-Control-Allow-Origin", "//mariadb.vamk.fi/e2101064_java");
			
			// Return all users
			if(pathInfo == null || pathInfo.equals("/")) {
				List<Student> users = studentDAO.selectAllStudents();
				sendAsJSON(res, users);
				return;
			}
			
			String splits[] = pathInfo.split("/");
			if(splits.length != 2) {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			
			String stuID = splits[1];
			Student student = studentDAO.selectStudentByID(stuID);
			if(student == null) {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			} else {
				sendAsJSON(res, student);
				return;
			}
		}
		
		
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
			res.addHeader("Access-Control-Allow-Origin", "//mariadb.vamk.fi/e2101064_java");
			
			String pathInfo = req.getPathInfo();
			System.out.println(pathInfo);
			if(pathInfo == null | pathInfo == "/") {
				StringBuilder buffer = new StringBuilder();
				BufferedReader reader = req.getReader();
				
				String line;
				while((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				String payload = buffer.toString();
				Student student = gson.fromJson(payload, Student.class);
				studentDAO.addStudent(student);
			} else {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		}
		
		@Override	
			protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			    res.addHeader("Access-Control-Allow-Origin", "//mariadb.vamk.fi/e2101064_java");

			    String pathInfo = req.getPathInfo();
			    if (pathInfo == null || pathInfo.equals("/")) {
			        res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			        return;
			    }

			    // Extract student ID from the URL
			    String stuID = pathInfo.substring(1); // Remove the leading '/'
			    Student student = studentDAO.selectStudentByID(stuID);

			    if (student == null) {
			        res.sendError(HttpServletResponse.SC_NOT_FOUND);
			        return;
			    } else {
			        // Delete the student from the database
			        studentDAO.deleteStudent(stuID);
			        res.setStatus(HttpServletResponse.SC_OK);
			        return;
			    }
			}

			
			

}
