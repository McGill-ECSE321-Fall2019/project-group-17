package ca.mcgill.ecse321.projectgroup17.dto;

import ca.mcgill.ecse321.projectgroup17.model.*;
import ca.mcgill.ecse321.projectgroup17.service.ProjectGroup17Service;

public class SpecificCourseDto {
		
	/*
	private double hourlyRate;
	private Tutor tutor;
	private Course course;
	private long specificCourseID;
	*/
	private double hourlyRate;
	private String tutorUsername;
	private String courseID;
	private long specificCourseID;
	
	ProjectGroup17Service service;
	
	public SpecificCourseDto() {	
	}
	
	// there can really only be one possible constructor for this class to make sense...
	public SpecificCourseDto(double hourlyRate, String tutor, String course, long specificCourseID) {	
		this.hourlyRate = hourlyRate;
		this.tutorUsername = tutor;
		this.courseID = course;
		this.specificCourseID = specificCourseID;
	}
	
	public double getHourlyRate() {
		return hourlyRate;
	}
	
	public String getTutorUsername() {
		return tutorUsername;
	}
	
	public String getCourseID() {
		return courseID;
	}
	
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	
	public long getSpecificCourseID() {
		return specificCourseID;
	}

}
