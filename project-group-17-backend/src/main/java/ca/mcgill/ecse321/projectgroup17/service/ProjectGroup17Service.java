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
import ca.mcgill.ecse321.projectgroup17.dao.PersonRepository;
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
	PersonRepository personRepository;

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
	
	





}
