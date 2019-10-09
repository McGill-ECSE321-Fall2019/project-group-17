package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;

@Entity
public class Review{
   private String text;

public void setText(String value) {
    this.text = value;
}
public String getText() {
    return this.text;
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

private int reviewID;

public void setReviewID(int value) {
    this.reviewID = value;
}
@Id
public int getReviewID() {
    return this.reviewID;
}
private Date created_date;

public void setCreated_date(Date value) {
    this.created_date = value;
}
public Date getCreated_date() {
    return this.created_date;
}
private Time created_time;

public void setCreated_time(Time value) {
    this.created_time = value;
}
public Time getCreated_time() {
    return this.created_time;
}
}
