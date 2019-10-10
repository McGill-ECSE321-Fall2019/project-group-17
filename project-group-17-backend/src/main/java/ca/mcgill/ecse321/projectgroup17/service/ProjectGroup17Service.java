package ca.mcgill.ecse321.projectgroup17.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup17.dao.*;
import ca.mcgill.ecse321.projectgroup17.model.*;


@Service
public class ProjectGroup17Service {

	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	AvailabilityRepository availabilityRepository;


	@Transactional
	public Course createCourse(String courseID, String name, String level, String subject) {
		
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
	
	
	@Transactional
	public Person createPerson(String personType, String firstName, String lastName, String username, String password, String email) {

		Person person;

		String error = "";
		if (personType == null || personType.trim().length() == 0 || ! (personType.equals("Tutor") || personType.equals("Student"))) {
			error = error + "Person type must be either 'Student' or 'Tutor'! ";
		}

		if (firstName == null || firstName.trim().length() == 0) {
			error = error + "First name cannot be empty! ";
		}

		if (lastName == null ||lastName.trim().length() == 0) {
			error = error + "Last name cannot be empty! ";
		}

		if (username == null || username.trim().length() == 0) {
			error = error + "Username cannot be empty! ";
		}

		if (password == null || password.trim().length() == 0) {
			error = error + "Password cannot be empty! ";
		}

		if (email == null || email.trim().length() == 0) {
			error = error + "Email cannot be empty! ";
		}

		error = error.trim();
		if (error.length() > 0) {
			throw new IllegalArgumentException(error);
		}

		if (personType.equals("Tutor")) {
			person = new Tutor();
		}

		else {
			person = new Student();
		}

		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setUsername(username);
		person.setPassword(password);
		person.setEmail(email);
		person.setCreated_date(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

		personRepository.save(person);
		return person;
	}

	@Transactional
	public Person getPersonByUsername(String username) {
		Person person = personRepository.findByUsername(username);
		return person;
	}

	@Transactional
	public List<Person> getPersonByFirstName(String firstName) {
		List<Person> persons = personRepository.findByFirstName(firstName);
		return persons;
	}

	@Transactional
	public List<Person> getPersonByFirstNameAndLastName(String firstName, String lastName) {
		List<Person> persons = personRepository.findByFirstNameAndLastName(firstName, lastName);
		return persons;
	}

	@Transactional
	public List<Person> getPersonByLastName(String lastName) {
		List<Person> persons = personRepository.findByLastName(lastName);
		return persons;
	}

	@Transactional
	public Person getPersonByEmail(String email) {
		Person person = personRepository.findByEmail(email);
		return person;
	}

	@Transactional
	public List<Person> getPersonByPersonType(String personType) {
		List<Person> persons = personRepository.findByPersonType(personType);
		return persons;
	}

	@Transactional
	public List<Person> getAllPersons() {
		return personRepository.findAll();
	}
	
	//Availability functions
	
	@Transactional
	public Availability createAvailability(Tutor tutor, Date date, Time startTime, Time endTime) {
		
		String error = "";
		
		Availability availability;

		if (tutor == null) {
			error += "Must specify a tutor! ";
		}
		if (date == null) {
			error += "Date cannot be empty! ";
		}
		if (startTime == null) {
			error += "Start time cannot be empty! ";
		}
		if (endTime == null) {
			error += "End time cannot be empty! ";
		}
		if ((endTime != null) && (startTime != null) && (startTime.compareTo(endTime) > 0)) {
			error += "End time cannot be before startTime! ";
		}
		
		error = error.trim();
	    if (error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
	    else {
	    	availability = new Availability();
	    }
	    
	    availability.setTutor(tutor);
		availability.setDate(date);
		availability.setStartTime(startTime);
		availability.setEndTime(endTime);
		
		availabilityRepository.save(availability);
		
		return availability;
	}
	
	@Transactional
	public List<Availability> getAvailabilityByDate(Date date) {
		List<Availability> availabilities = availabilityRepository.findByDate(date);
		return availabilities;
	}
	@Transactional
	public List<Availability> getAvailabilityByTutorUsername(String tutorUsername) {
		List<Availability> availabilities = availabilityRepository.findByTutor(tutorUsername);
		return availabilities;
	}

	public List<Availability> getAllAvailabilities() {
		return availabilityRepository.findAll();
	}
	
}
