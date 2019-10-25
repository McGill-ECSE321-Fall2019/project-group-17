package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;

@Entity
public class Review{
	private String reviewText;

	public void setReviewText(String value) {
		this.reviewText = value;
	}
	public String getReviewText() {
		return this.reviewText;
	}
	private int rating;

	public void setRating(int value) {
		this.rating = value;
	}
	public int getRating() {
		return this.rating;
	}
	private Person reviewee;

	@ManyToOne(optional=false)
	public Person getReviewee() {
		return this.reviewee;
	}

	public void setReviewee(Person reviewee) {
		this.reviewee = reviewee;
	}

	private Person reviewer;

	@ManyToOne(optional=false)
	public Person getReviewer() {
		return this.reviewer;
	}

	public void setReviewer(Person reviewer) {
		this.reviewer = reviewer;
	}

	private Appointment appointment;

	@ManyToOne(optional=false)
	public Appointment getAppointment() {
		return this.appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	private long reviewID;

	// should be auto-generated, and immutable
	public void setReviewID(long value) {
		this.reviewID = value;
	}
	@Id @GeneratedValue
	public long getReviewID() {
		return this.reviewID;
	}
	private Date createdDate;

	public void setCreatedDate(Date value) {
		this.createdDate = value;
	}
	public Date getCreatedDate() {
		return this.createdDate;
	}
	private Time createdTime;

	public void setCreatedTime(Time value) {
		this.createdTime = value;
	}
	public Time getCreatedTime() {
		return this.createdTime;
	}
}
