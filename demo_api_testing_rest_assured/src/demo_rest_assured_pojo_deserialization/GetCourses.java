package demo_rest_assured_pojo_deserialization;

public class GetCourses {
	private String url;
	private String services;
	private String expertise;
	private CoursesList courses;
	private String Instructor;
	private String linkedIn;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getServices() {
		return services;
	}
	public void setServices(String services) {
		this.services = services;
	}
	public String getExpertise() {
		return expertise;
	}
	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	public CoursesList getCourses() {
		return courses;
	}
	public void setCourses(CoursesList courses) {
		this.courses = courses;
	}
	public String getInstructor() {
		return Instructor;
	}
	public void setInstructor(String instructor) {
		Instructor = instructor;
	}
	public String getLinkedIn() {
		return linkedIn;
	}
	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}
	

}
