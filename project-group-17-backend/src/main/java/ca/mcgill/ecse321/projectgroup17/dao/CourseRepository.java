package ca.mcgill.ecse321.projectgroup17.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup17.model.Course;

public interface CourseRepository extends CrudRepository<Course, Date> {
	
	Course findCourseByID(String courseID);
	
	List<Course> findCourseBySubject(String subject);
	
	void deleteByID(String courseID);
	
	boolean existsById(String courseID);
}
