package assignment_REST;

public class Student {
	private String stuID;
	private String firstName;
	private String lastName;

	public Student() {
	}

	public Student(String id, String firstName, String lastName) {
		super();
		this.stuID = id;
		this.lastName = lastName;
		this.lastName = lastName;
	}

	public String getStudentID() {
		return stuID;
	}

	public void setUserID(String stuID) {
		this.stuID = stuID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}