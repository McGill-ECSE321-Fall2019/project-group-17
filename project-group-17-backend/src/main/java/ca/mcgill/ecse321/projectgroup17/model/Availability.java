package ca.mcgill.ecse321.projectgroup17.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

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
private Time startTime;

public void setStartTime(Time value) {
    this.startTime = value;
}
public Time getStartTime() {
    return this.startTime;
}
private Tutor tutor;

@ManyToOne(optional=false)
public Tutor getTutor() {
   return this.tutor;
}

public void setTutor(Tutor tutor) {
   this.tutor = tutor;
}

private long availabilityID;

public void setAvailabilityID(long value) {
    this.availabilityID = value;
}

@Id @GeneratedValue
public long getAvailabilityID() {
    return this.availabilityID;
}
private Time endTime;

public void setEndTime(Time value) {
    this.endTime = value;
}
public Time getEndTime() {
    return this.endTime;
}
private Date createdDate;

public void setCreatedDate(Date value) {
    this.createdDate = value;
}
public Date getCreatedDate() {
    return this.createdDate;
}
}
