package ca.mcgill.ecse321.projectgroup17.dto;

import ca.mcgill.ecse321.projectgroup17.model.*;

public class SpecificCourseDto {
	
	private double hourlyRate;
	private Tutor tutor;
	private Course course;
	private long specificCourseID;
	
	public SpecificCourseDto() {	
	}
	
	// there can really only be one possible constructor for this class to make sense...
	public SpecificCourseDto(double hourlyRate, Tutor tutor, Course course, long specificCourseID) {	
		this.hourlyRate = hourlyRate;
		this.tutor = tutor;
		this.course = course;
		this.specificCourseID = specificCourseID;
	}
	
	public double getHourlyRate() {
		return hourlyRate;
	}
	
	public Tutor getTutor() {
		return tutor;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public long getSpecificCourseID() {
		return specificCourseID;
	}

}
