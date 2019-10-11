package ca.mcgill.ecse321.projectgroup17.dao;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;


import ca.mcgill.ecse321.projectgroup17.model.*;

public interface AvailabilityRepository extends CrudRepository<Availability, Long> {

	ArrayList<Availability> findByDate(Date date);
	
	ArrayList<Availability> findByTutor(String tutorUsername);
	
	
}
