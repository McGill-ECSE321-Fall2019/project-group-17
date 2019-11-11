package ca.mcgill.ecse321.projectgroup17.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup17.model.Course;
import ca.mcgill.ecse321.projectgroup17.model.SpecificCourse;

public interface SpecificCourseRepository extends CrudRepository<SpecificCourse, Long>{
	
	SpecificCourse findBySpecificCourseID(Long specificCourseID);

	List<SpecificCourse> findByTutorUsername(String tutorUsername);
	
	List<SpecificCourse> findByCourse(Course course);
		
	List<SpecificCourse> findAll();

}
