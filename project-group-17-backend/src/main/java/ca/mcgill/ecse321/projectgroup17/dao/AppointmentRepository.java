package ca.mcgill.ecse321.projectgroup17.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup17.model.Appointment;
import ca.mcgill.ecse321.projectgroup17.model.Tutor;

public interface AppointmentRepository extends CrudRepository<Appointment, Long>{

	List<Appointment> findAll();
	
	List<Appointment> findByDate(Date date);
	
	List<Appointment> findByTutorAndDate(Date date, Tutor tutor);
	
	List<Appointment> findByStartTime(Time startTime);
	
	List<Appointment> findByEndTime(Time endTime);
	
	List<Appointment> findByStartTimeAndEndTime(Time startTime, Time endTime);
	
	Appointment findByTutorAndDateAndStartTime(Tutor tutor, Date date, Time startTime);
	
	List<Appointment> findByAppointmentStatus(String appointmentStatus);
	
}
