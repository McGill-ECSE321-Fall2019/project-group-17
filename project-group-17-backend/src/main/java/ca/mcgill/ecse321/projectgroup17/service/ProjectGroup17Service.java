package ca.mcgill.ecse321.projectgroup17.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup17.dao.AvailabilityRepository;
import ca.mcgill.ecse321.projectgroup17.dao.CourseRepository;
import ca.mcgill.ecse321.projectgroup17.model.Course;
import ca.mcgill.ecse321.projectgroup17.model.Person;
import ca.mcgill.ecse321.projectgroup17.model.Availability;
import ca.mcgill.ecse321.projectgroup17.model.Appointment;
import ca.mcgill.ecse321.projectgroup17.model.Course;
import ca.mcgill.ecse321.projectgroup17.model.Level;
import ca.mcgill.ecse321.projectgroup17.model.Review;
import ca.mcgill.ecse321.projectgroup17.model.Room;
import ca.mcgill.ecse321.projectgroup17.model.SpecificCourse;
import ca.mcgill.ecse321.projectgroup17.model.Student;
import ca.mcgill.ecse321.projectgroup17.model.Tutor;


@Service
public class ProjectGroup17Service {

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	AvailabilityRepository availableRepository;

	@Transactional 
	public Availability createAvailabilityForTutor(Tutor tutor, Date date, Time startTime, Time endTime) {
		Availability a = new Availability();
		Calendar c = Calendar.getInstance();
		
		a.setDate(date);
		//a.setAvailabilityID(value); ???
		a.setStartTime(startTime);
		a.setEndTime(endTime);

	}

	@Transactional
	public Course createCourse(String courseID, String name, Level level, String subject) {
		
		Course course = new Course();
		course.setCourseID(courseID);
		course .setLevel(level);
		course.setName(name);
		courseRepository.save(course);
		return course;

	}
	
	@Transactional
	public Course getCourseByID(String courseID) {
		Course course = courseRepository.findCourseByID(courseID);
		return course;
	}

	@Transactional
	public List<Course> getAllCourses() {
		return toList(courseRepository.findAll());
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}





}
