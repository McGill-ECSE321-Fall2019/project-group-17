package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import java.sql.Date;
import java.sql.Time;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.Id;

@Entity
public class Appointment{
	public enum AppointmentStatus {
		REQUESTED, ACCEPTED, REFUSED, PAID, CANCELLED;
	}

	private Date date;

	public void setDate(Date value) {
		this.date = value;
	}
	public Date getDate() {
		return this.date;
	}
	private Time endTime;

	public void setEndTime(Time value) {
		this.endTime = value;
	}
	public Time getEndTime() {
		return this.endTime;
	}
	private Room room;

	@ManyToOne(optional=false)
	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	private Set<Review> review;

	@OneToMany(mappedBy="appointment" )
	public Set<Review> getReview() {
		return this.review;
	}

	public void setReview(Set<Review> reviews) {
		this.review = reviews;
	}

	private Tutor tutor;

	@ManyToOne
	public Tutor getTutor() {
		return this.tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	private Set<Student> student;

	@ManyToMany
	public Set<Student> getStudent() {
		return this.student;
	}

	public void setStudent(Set<Student> students) {
		this.student = students;
	}

	private long appointmentID;

	public void setAppointmentID(long value) {
		this.appointmentID = value;
	}
	@Id @GeneratedValue
	public long getAppointmentID() {
		return this.appointmentID;
	}
	private Date createdDate;

	public void setCreatedDate(Date value) {
		this.createdDate = value;
	}
	public Date getCreatedDate() {
		return this.createdDate;
	}

	private AppointmentStatus status;

	public void setStatus(AppointmentStatus value) {
		this.status = value;
	}
	public AppointmentStatus getStatus() {
		return this.status;
	}
	private Time startTime;

	public void setStartTime(Time value) {
		this.startTime = value;
	}
	public Time getStartTime() {
		return this.startTime;
	}
}
