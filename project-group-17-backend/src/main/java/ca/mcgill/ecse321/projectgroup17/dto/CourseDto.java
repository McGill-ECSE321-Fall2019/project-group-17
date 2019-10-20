package ca.mcgill.ecse321.projectgroup17.dto;

import java.util.Set;

import ca.mcgill.ecse321.projectgroup17.model.*;
import ca.mcgill.ecse321.projectgroup17.model.Course.Level;

public class CourseDto {
	
	private Level level;
	private String courseName;
	private Set<SpecificCourse> specificCourses;
	private String subject;
	private String courseID;
	
	public CourseDto() {
	}
	
	public CourseDto(String courseID, String courseName) {
		this(courseID, courseName, null, null, null);
	}
	
	public CourseDto(String courseID, String courseName, Level level) {
		this(courseID, courseName, level, null, null);
	}

	
	public CourseDto(String courseID, String courseName, Level level, String subject) {
		this(courseID, courseName, level, subject, null);
	}
	
	public CourseDto(String courseID, String courseName, Level level, String subject, Set<SpecificCourse> specificCourses) {
		this.courseID = courseID;
		this.courseName = courseName;
		this.level = level;
		this.subject = subject;
		this.specificCourses = specificCourses;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public Set<SpecificCourse> getSpecificCourses() {
		return specificCourses;
	}
	
	public void setSpecificCourses(Set<SpecificCourse> specificCourses) {
		this.specificCourses = specificCourses;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getCourseID() {
		return courseID;
	}
	

}
