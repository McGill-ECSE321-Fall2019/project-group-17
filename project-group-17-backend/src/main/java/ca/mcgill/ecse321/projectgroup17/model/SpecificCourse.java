package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SpecificCourse{
	private double hourlyRate;

	public void setHourlyRate(double value) {
		this.hourlyRate = value;
	}
	public double getHourlyRate() {
		return this.hourlyRate;
	}
	
	private Tutor tutor;

	@ManyToOne(optional=false)
	public Tutor getTutor() {
		return this.tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	private Course course;

	@ManyToOne(optional=false)
	public Course getCourse() {
		return this.course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	private long specificCourseID;

	public void setSpecificCourseID(long specificCourseID) {
	    this.specificCourseID = specificCourseID;
	}
	
	@Id
	public long getSpecificCourseID() {
	    return this.specificCourseID;
	}


}
