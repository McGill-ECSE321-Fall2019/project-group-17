package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Availability{
	private Date date;

	public void setDate(Date value) {
		this.date = value;
	}
	public Date getDate() {
		return this.date;
	}
	private boolean isRecurrent;

	public void setIsRecurrent(boolean value) {
		this.isRecurrent = value;
	}
	public boolean isIsRecurrent() {
		return this.isRecurrent;
	}
	private Time time;

	public void setTime(Time value) {
		this.time = value;
	}
	public Time getTime() {
		return this.time;
	}
	private Tutor tutor;

	@ManyToOne(optional=false)
	public Tutor getTutor() {
		return this.tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	private int availabilityID;

	public void setAvailabilityID(int value) {
		this.availabilityID = value;
	}
	@Id
	public int getAvailabilityID() {
		return this.availabilityID;
	}
}
