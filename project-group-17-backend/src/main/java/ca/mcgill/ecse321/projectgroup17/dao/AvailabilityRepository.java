package ca.mcgill.ecse321.projectgroup17.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


import ca.mcgill.ecse321.projectgroup17.model.*;

public interface AvailabilityRepository extends CrudRepository<Availability, Long> {

	List<Availability> findByDate(Date date);
	
	List<Availability> findByTutor(String tutorUsername);
	
	
}
