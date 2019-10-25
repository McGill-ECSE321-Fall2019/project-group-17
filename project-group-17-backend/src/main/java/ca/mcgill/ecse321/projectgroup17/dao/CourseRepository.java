package ca.mcgill.ecse321.projectgroup17.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup17.model.Course;
import ca.mcgill.ecse321.projectgroup17.model.Course.Level;

public interface CourseRepository extends CrudRepository<Course, String> {
	
	Course findByCourseID(String courseID);
	
	List<Course> findBySubject(String subject);
	
	void deleteByCourseID(String courseID);
	
	List<Course> findByLevel(String level);
	
	boolean existsByCourseID(String courseID);
}
