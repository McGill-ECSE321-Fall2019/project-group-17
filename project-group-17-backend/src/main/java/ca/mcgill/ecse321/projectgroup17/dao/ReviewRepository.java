package ca.mcgill.ecse321.projectgroup17.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup17.model.Appointment;
import ca.mcgill.ecse321.projectgroup17.model.Person;
import ca.mcgill.ecse321.projectgroup17.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Long>{
	
	Review findByReviewID(long reviewID);
	
	List<Review> findByReviewee(Person reviewee);
	
	List<Review> findByReviewer(Person reviewer);
	
	List<Review> findByAppointment(Appointment appointment);

}
