package ca.mcgill.ecse321.projectgroup17.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup17.model.Appointment;
import ca.mcgill.ecse321.projectgroup17.model.Tutor;

public interface AppointmentRepository extends CrudRepository<Appointment, Long>{

	ArrayList<Appointment> findAll();
	
	ArrayList<Appointment> findByDate(Date date);
	
	ArrayList<Appointment> findByTutorAndDate(Date date, Tutor tutor);
	
	Appointment findByTutorAndDateAndStartTime(Tutor tutor, Date date, Time startTime);
	
	
	
	
	
}
