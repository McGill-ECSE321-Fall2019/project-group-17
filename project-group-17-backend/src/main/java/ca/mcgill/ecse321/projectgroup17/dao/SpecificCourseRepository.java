package ca.mcgill.ecse321.projectgroup17.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.projectgroup17.model.SpecificCourse;

public interface SpecificCourseRepository extends CrudRepository<SpecificCourse, Long>{
	
	SpecificCourse findBySpecificCourseID(Long specificCourseID);

	ArrayList<SpecificCourse> findByTutor(String tutorUsername);
	
	ArrayList<SpecificCourse> findByCourse(String courseName);
	
	ArrayList<SpecificCourse> findAll();

}
