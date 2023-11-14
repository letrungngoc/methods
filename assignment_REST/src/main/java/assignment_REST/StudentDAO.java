package assignment_REST;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
	private String jdbcURL = "jdbc:mariadb://mariadb.vamk.fi/e2101064_java";
	private String jdbcUserName = "e2101064";
	private String jdbcPassword = "DuTWee4pXG5";

	// Constructors
	public StudentDAO(String url, String userName, String password) {
		this.jdbcURL = url;
		this.jdbcUserName = userName;
		this.jdbcPassword = password;
	}

	public StudentDAO() {
	}

	private static final String SELECT_ALL_USERS_QUERY = "SELECT * FROM students";
	private static final String SELECT_USER_BY_ID = "SELECT * FROM students WHERE stuID=?";
	private static final String INSERT_USER_QUERY = "INSERT INTO students (id, lastName, firstname) VALUES (?, ?, ?)";
	private static final String DELETE_USER_QUERY = "DELETE FROM students WHERE stuID=?";

	protected Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void addStudent(Student student) {
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_USER_QUERY);) {
			ps.setString(2, student.getLastName());
			ps.setString(3, student.getFirstName());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Student> selectAllStudents() {
		List<Student> students = new ArrayList<Student>();

		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_USERS_QUERY);) {

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String id = rs.getString(1);
				String lastName = rs.getString(2);
				String firstName = rs.getString(3);
		

				students.add(new Student(id, lastName, firstName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return students;
	}

	public Student selectStudentByID(String id) {
		Student student = null;

		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_ID);) {

			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String lastName = rs.getString("firstName");
				String firstName = rs.getString("lastName");
				

				student = new Student(id, lastName, firstName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return student;
	}

	public boolean deleteStudent(String id) {
		boolean rowDeleted = false;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_USER_QUERY);) {
			ps.setString(1, id);
			rowDeleted = ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowDeleted;
	}
}