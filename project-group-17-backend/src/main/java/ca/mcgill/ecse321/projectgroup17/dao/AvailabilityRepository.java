package ca.mcgill.ecse321.projectgroup17.dao;

import java.sql.Date;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup17.model.Availability;
import ca.mcgill.ecse321.projectgroup17.model.Person;;

public interface AvailabilityRepository extends CrudRepository<Availability, Date> {

	Availability findAvailabilityByDate(Date date);
}
