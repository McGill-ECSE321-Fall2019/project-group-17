package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.Id;

@Entity
public class Appointment{
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

private Set<Tutor> tutor;

@ManyToMany
public Set<Tutor> getTutor() {
   return this.tutor;
}

public void setTutor(Set<Tutor> tutors) {
   this.tutor = tutors;
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
@Id
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
private String status;

public void setStatus(String value) {
    this.status = value;
}
public String getStatus() {
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
