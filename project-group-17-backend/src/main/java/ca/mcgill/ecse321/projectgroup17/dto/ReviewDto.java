package ca.mcgill.ecse321.projectgroup17.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.projectgroup17.model.Appointment;
import ca.mcgill.ecse321.projectgroup17.model.Person;

public class ReviewDto {
	
	private String reviewText;
	private double rating;
	private String reviewee;
	private String reviewer;
	private Long appointment;
	private Date createdDate;
	private Time createdTime;
	
	public ReviewDto() {	
	}
	
	public ReviewDto(String reviewText, double rating) {
		this(reviewText, rating, null, null, null);
	}
	
	public ReviewDto(String reviewText, double rating, String reviewee, String reviewer,
			Long appointment) {		
		this(reviewText, rating, reviewee, reviewer, appointment, 
				new java.sql.Date(2019, 11, 11), new java.sql.Time(18, 0, 0));	
	}
		
	public ReviewDto(String reviewText, double rating, String reviewee, String reviewer,
			Long appointment, Date createdDate, Time createdTime) {
		
		this.reviewText = reviewText;
		this.rating = rating;
		this.reviewee = reviewee;
		this.reviewer = reviewer;
		this.appointment = appointment;
		this.createdDate = createdDate;
		this.createdTime = createdTime;			
	}
	
	//Getters and Setters for DTO
	
	public String getReviewText() {
		return this.reviewText;
	}

	public double getRating() {
		return rating;
	}

	public String getReviewee() {
		return reviewee;
	}

	public String getReviewer() {
		return reviewer;
	}

	public Long getAppointment() {
		return appointment;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Time getCreatedTime() {
		return createdTime;
	}
	
}
