package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SpecificCourse {
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

	private double rate;

	public void setRate(double value) {
		this.rate = value;
	}
	public double getRate() {
		return this.rate;
	}

	private int rateID;

	public void setRateID(int value) {
		this.rateID = value;
	}
	@Id
	public int getRateID() {
		return this.rateID;
	}


}
