package ca.mcgill.ecse321.projectgroup17.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.projectgroup17.model.Tutor;

public class AvailabilityDto {
	
	Tutor tutor;
	Date date;
	Date createdDate;
	Time startTime;
	Time endTime;
	
	public AvailabilityDto(){
		
	}
	
	public AvailabilityDto(Tutor tutor, Date date, Date createdDate, Time startTime, Time endTime){
		this.tutor=tutor;
		this.date=date;
		this.createdDate=createdDate;
		this.startTime=startTime;
		this.endTime=endTime;
	}
}
