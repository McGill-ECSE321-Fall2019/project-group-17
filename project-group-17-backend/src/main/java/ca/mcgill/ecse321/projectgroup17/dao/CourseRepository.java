package ca.mcgill.ecse321.projectgroup17.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup17.model.Course;
import ca.mcgill.ecse321.projectgroup17.model.Course.Level;

public interface CourseRepository extends CrudRepository<Course, String> {
	
	Course findCourseByCourseID(String courseID);
	
	List<Course> findCourseBySubject(String subject);
	
	void deleteByCourseID(String courseID);

	List<Course> findCoursesBySubject(String subject);
	
	List<Course> findCourseByLevel(String level);
	
	boolean existsByCourseID(String courseID);
}
