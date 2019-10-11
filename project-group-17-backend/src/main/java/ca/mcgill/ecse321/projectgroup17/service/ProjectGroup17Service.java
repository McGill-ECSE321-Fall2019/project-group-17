package ca.mcgill.ecse321.projectgroup17.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jboss.logging.Logger.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup17.dao.*;
import ca.mcgill.ecse321.projectgroup17.model.*;


@Service
public class ProjectGroup17Service {


	@Autowired
	PersonRepository personRepository;
	
	
	/*--------------------------------------*/
	
	@Autowired
	CourseRepository courseRepository;

	@Transactional
	public Course createCourse(String courseID, String name, String level, String subject) {
		String error = "";
		if(courseID == null || courseID.equals("") || courseID.trim().length() == 0) {
			error += "Course ID must be specified (ie: ECSE321)!";
		}
		if(name == null || name.equals("")) {
			error += "Course name must be specified!";
		}
		if(level == null || level.equals("")) {
			error += "Course level must be specified!";
		}
		if(Level.valueOf(level) == null) {
			error += "Invalid course level specified (Highschool, Cegep, University)!";
		}
		if(subject == null || subject.equals("")) {
			error += "The course name must be specified!";
		}
		error = error.trim();
	    if(error.length() > 0) {
	        throw new IllegalArgumentException(error);
	    }
		Course course = new Course();
		course.setCourseID(courseID);
		course .setLevel(level);
		course.setName(name);
		course.setSubject(subject);
		courseRepository.save(course);
		return course;

	}
	
	@Transactional
	public Course getCourseByID(String courseID) {
		if(courseID == null || courseID.equals("") || courseID.trim().length() == 0) {
			throw new IllegalArgumentException("Course ID must be specified (ie: ECSE321)!");
		}
		Course course = courseRepository.findCourseByID(courseID);
		return course;
	}
	
	@Transactional
	public List<Course> getCourseBySubject(String subject) {
		if(subject == null || subject.equals("") || subject.trim().length() == 0) {
			throw new IllegalArgumentException("Course ID must be specified (ie: ECSE321)!");
		}
		List<Course> course = courseRepository.findCourseBySubject(subject);
		return course;
	}

	@Transactional
	public List<Course> getAllCourses() {
		return toList(courseRepository.findAll());
	}
	
	@Transactional
	public void deleteCourseByID(String courseID) {
		if(courseID == null || courseID.equals("") || courseID.trim().length() == 0) {
			throw new IllegalArgumentException("Course ID must be specified (ie: ECSE321)!");
		}
		if(courseExistsByID(courseID)) {
			courseRepository.deleteByID(courseID);;
		}
		
	}
	
	@Transactional
	public boolean courseExistsByID(String courseID) {
		if(courseID == null || courseID.equals("") || courseID.trim().length() == 0) {
			throw new IllegalArgumentException("Course ID must be specified (ie: ECSE321)!");
		}
		boolean exists = courseRepository.existsById(courseID);
		return exists;
	}

	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
		
	}
	
	
	/*--------------------------------------*/
	
	
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
}
