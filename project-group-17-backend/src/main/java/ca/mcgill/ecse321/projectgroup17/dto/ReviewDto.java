package ca.mcgill.ecse321.projectgroup17.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.projectgroup17.model.Appointment;
import ca.mcgill.ecse321.projectgroup17.model.Person;

public class ReviewDto {
	
	private String reviewText;
	private int rating;
	private Person reviewee;
	private Person reviewer;
	private Appointment appointment;
	private Date createdDate;
	private Time createdTime;
	
	public ReviewDto() {	
	}
	
	public ReviewDto(String reviewText, int rating) {
		this(reviewText, rating, null, null, null);
	}
	
	public ReviewDto(String reviewText, int rating, Person reviewee, Person reviewer,
			Appointment appointment) {		
		this(reviewText, rating, reviewee, reviewer, appointment, 
				new java.sql.Date(2019, 11, 11), new java.sql.Time(18, 0, 0));	
	}
		
	public ReviewDto(String reviewText, int rating, Person reviewee, Person reviewer,
			Appointment appointment, Date createdDate, Time createdTime) {
		
		this.reviewText = reviewText;
		this.rating = rating;
		this.reviewee = reviewee;
		this.reviewer = reviewer;
		this.appointment = appointment;
		this.createdDate = createdDate;
		this.createdTime = createdTime;			
	}
	
	public String getReviewText() {
		return this.reviewText;
	}

	public int getRating() {
		return rating;
	}

	public Person getReviewee() {
		return reviewee;
	}

	public Person getReviewer() {
		return reviewer;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Time getCreatedTime() {
		return createdTime;
	}
	
}
