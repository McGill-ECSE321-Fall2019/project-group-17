package ca.mcgill.ecse321.projectgroup17.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Set;

import ca.mcgill.ecse321.projectgroup17.model.Appointment.AppointmentStatus;

public class AppointmentDto {

	private Date date;
	private Time startTime;
	private Time endTime;
	private Date createdDate;
	private AppointmentStatus status;
	private PersonDto tutor;
	private Set<PersonDto> student;
	private RoomDto room;
	
	
	public AppointmentDto() {
	}
	
	public AppointmentDto(Date date, Time startTime) {
		this(date, startTime, new Time(startTime.getTime() + 3600000L));
	}
	
	public AppointmentDto(Date date, Time startTime, Time endTime) {
		this(date, startTime, endTime, AppointmentStatus.REQUESTED);
	}
	
	public AppointmentDto(Date date, Time startTime, Time endTime, AppointmentStatus status) {
		this(date, startTime, endTime, status, null);
	}
	
	public AppointmentDto(Date date, Time startTime, Time endTime, AppointmentStatus status, PersonDto tutor) {
		this(date, startTime, endTime, status, tutor, null);
	}
	
	public AppointmentDto(Date date, Time startTime, Time endTime, AppointmentStatus status, PersonDto tutor, Set<PersonDto> student) {
		this(date, startTime, endTime, status, tutor, student, new Date(Calendar.getInstance().getTime().getTime()));
	}

	
	public AppointmentDto(Date date, Time startTime, Time endTime, AppointmentStatus status, PersonDto tutor, Set<PersonDto> student, Date createdDate) {
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createdDate = createdDate;
		this.status = status;
		this.tutor = tutor;
		this.student = student;
	}
	
	public Date getDate() {
		return date;
	}
	
	public Time getStartTime() {
		return startTime;
	}
	
	public Time getEndTime() {
		return endTime;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	public AppointmentStatus getStatus() {
		return status;
	}
	
	public PersonDto getTutor() {
		return tutor;
	}
	
	public void setTutor(PersonDto tutor) {
		this.tutor = tutor;
	}
	
	public Set<PersonDto> getStudent() {
		return student;
	}
	
	public void setStudent(Set<PersonDto> student) {
		this.student = student;
	}
	
	public RoomDto getRoom() {
		return room;
	}
	
	public void setRoom(RoomDto room) {
		this.room = room;
	}
	
	
}