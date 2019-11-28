package ca.mcgill.ecse321.projectgroup17.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.projectgroup17.model.Tutor;

public class AvailabilityDto {
	
	String tutor;
	Date date;
	Date createdDate;
	Time startTime;
	Time endTime;
	long availabilityId;
	
	public AvailabilityDto(){
		
	}
	
	public AvailabilityDto(long availabilityId, String tutor, Date date, Date createdDate, Time startTime, Time endTime){
		this.availabilityId=availabilityId;
		this.tutor=tutor;
		this.date=date;
		this.createdDate=createdDate;
		this.startTime=startTime;
		this.endTime=endTime;
		
	}
	
	//Getters and Setters for the DTO
	
	public String getTutor() {
		return this.tutor;
	}
	public void setTutor(String tutor) {
		this.tutor = tutor;
	}
	
	public Date getDate() {
		return this.date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getCreatedDate() {
		return this.createdDate;
	}
	public void setCreatedDate(Date date) {
		this.createdDate = date;
	}
	
	public Time getStartTime() {
		return this.startTime;
	}
	public void setStartTime(Time time) {
		this.startTime = time;
	}
	
	public Time getEndTime() {
		return this.endTime;
	}
	public void setEndTime(Time time) {
		this.endTime = time;
	}
	
	public long getAvailabilityId() {
		return this.availabilityId;
	}
	public void setAvailabilityId(long id) {
		this.availabilityId = id;
	}
	
	
}
